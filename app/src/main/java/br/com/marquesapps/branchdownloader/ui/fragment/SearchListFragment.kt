package br.com.marquesapps.branchdownloader.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.marquesapps.branchdownloader.R
import br.com.marquesapps.branchdownloader.constants.MESSAGE_THERE_WAS_AN_ERROR
import br.com.marquesapps.branchdownloader.constants.MESSAGE_TRY_AGAIN_LATER
import br.com.marquesapps.branchdownloader.databinding.SearchListLayoutBinding
import br.com.marquesapps.branchdownloader.model.Repository
import br.com.marquesapps.branchdownloader.repository.Resource
import br.com.marquesapps.branchdownloader.retrofit.model.SearchDTO
import br.com.marquesapps.branchdownloader.ui.adapter.SearchAdapter
import br.com.marquesapps.branchdownloader.ui.databinding.ListComponentsData
import br.com.marquesapps.branchdownloader.ui.extension.showMessageError
import br.com.marquesapps.branchdownloader.ui.viewModel.BottomNavigationViewModel
import br.com.marquesapps.branchdownloader.ui.viewModel.RepositorySearchViewModel
import br.com.marquesapps.branchdownloader.ui.viewModel.RepositoryViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

private const val MESSAGE_INITIAL = "No search"
private const val QUERY_HINT = "Search repository"
private const val MESSAGE_RESULTS = "Results"
private const val MESSAGE_ZERO_RESULTS = "0 $MESSAGE_RESULTS"

class SearchListFragment : Fragment() {

    private lateinit var viewDataBinding: SearchListLayoutBinding
    private val bottomNavigationViewModel: BottomNavigationViewModel by sharedViewModel()
    private val repositoryViewModel: RepositoryViewModel by sharedViewModel()
    private val repositorySearchViewModel: RepositorySearchViewModel by viewModel()
    private val adapter: SearchAdapter by lazy { SearchAdapter(context) }

    private var query: String = ""
    private val controller by lazy { findNavController() }
    private val listComponentsData by lazy {
        ListComponentsData().also { it.initialState(messageText = MESSAGE_INITIAL) }
    }

    private var page = DEFAULT_PAGE + 1
    private var hasPage = true
    private var cantSearch = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = SearchListLayoutBinding
            .inflate(inflater, container, false)
        viewDataBinding.listComponentsData = listComponentsData
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationViewModel.setVisibility(false)
        configureRecyclerView()
        configureAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val searchView = menu.findItem(R.id.menu_search_action).actionView as SearchView
        configureRepositorySearchView(searchView)

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun configureAdapter() {
        adapter.onRepositoryClickListener = {
            repositoryViewModel.setValue(it)
            goToRepositoryFragment()
        }
    }

    private fun configureRecyclerView() {
        viewDataBinding.searchListLayoutRepositoryList
            .addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (!recyclerView.canScrollVertically(DOWN_DIRECTION)) {
                        searchingForNewPages()
                    }
                }
            })
        viewDataBinding.searchListLayoutRepositoryList.layoutManager = LinearLayoutManager(context)
        viewDataBinding.searchListLayoutRepositoryList.adapter = adapter
    }

    private fun configureRepositorySearchView(searchView: SearchView) {
        searchView.apply {
            queryHint = QUERY_HINT
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(newQuery: String?): Boolean {
                    newQuery?.let { q ->
                        if (q.isNotEmpty()) {
                            this@SearchListFragment.query = q
                            searchFirstPage(q)
                        }
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean = true
            })
        }
    }


    private fun searchFirstPage(query: String) {
        listComponentsData.searchState()
        repositorySearchViewModel
            .searchRepositories(query, RESULTS_PER_PAGE, DEFAULT_PAGE)
            .observe(this, { resource ->
                resourceReturn(resource, {
                    replaceRepositoryList(it)
                }, {
                    listComponentsData.searchEndState(
                        false,
                        MESSAGE_TRY_AGAIN_LATER
                    )
                    showMessageError(MESSAGE_THERE_WAS_AN_ERROR)
                })
            })
    }

    private fun searchingForNewPages() {
        if (!hasPage || cantSearch)
            return

        cantSearch = true
        listComponentsData.searchPagesState()
        repositorySearchViewModel
            .searchRepositories(query, RESULTS_PER_PAGE, page)
            .observe(this, { resource ->
                resourceReturn(resource, {
                    addNewRepositories(it)
                }, {
                    listComponentsData.searchPageEndState()
                    showMessageError(MESSAGE_THERE_WAS_AN_ERROR)
                })
            })
    }

    private fun resourceReturn(
        resource: Resource<SearchDTO?>,
        whenSuccess: (searchDTO: SearchDTO) -> Unit,
        whenFailure: () -> Unit
    ) {
        cantSearch = false
        if (resource.error == null) {
            resource.data?.let { whenSuccess(it) } ?: whenFailure()
        } else {
            whenFailure()
        }
    }

    private fun replaceRepositoryList(searchDTO: SearchDTO) {
        page = DEFAULT_PAGE + 1
        val items = searchDTO.items
        val list = items.map { Repository(it) }
        hasPage = list.size == RESULTS_PER_PAGE
        adapter.replaceRepositoryList(list)
        listComponentsData.searchEndState(
            list.isNotEmpty(),
            MESSAGE_ZERO_RESULTS,
            messageText = "${searchDTO.totalCount} $MESSAGE_RESULTS"
        )
    }

    private fun addNewRepositories(searchDTO: SearchDTO) {
        page++
        val list = searchDTO.items.map { Repository(it) }
        hasPage = list.size == RESULTS_PER_PAGE
        adapter.addNewRepositories(list)
        listComponentsData.searchPageEndState()
    }

    private fun goToRepositoryFragment() {
        val action =
            SearchListFragmentDirections
                .actionSearchListFragmentToRepositoryFragment()
        controller.navigate(action)
    }
}