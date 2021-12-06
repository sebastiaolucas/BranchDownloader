package br.com.marquesapps.branchdownloader.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.marquesapps.branchdownloader.constants.MESSAGE_THERE_WAS_AN_ERROR
import br.com.marquesapps.branchdownloader.constants.MESSAGE_TRY_AGAIN_LATER
import br.com.marquesapps.branchdownloader.databinding.BranchListLayoutBinding
import br.com.marquesapps.branchdownloader.downloader.BranchDownloader
import br.com.marquesapps.branchdownloader.model.Branch
import br.com.marquesapps.branchdownloader.model.Repository
import br.com.marquesapps.branchdownloader.repository.Resource
import br.com.marquesapps.branchdownloader.retrofit.model.BranchDTO
import br.com.marquesapps.branchdownloader.ui.adapter.BranchAdapter
import br.com.marquesapps.branchdownloader.ui.databinding.ListComponentsData
import br.com.marquesapps.branchdownloader.ui.extension.showMessageError
import br.com.marquesapps.branchdownloader.ui.viewModel.BottomNavigationViewModel
import br.com.marquesapps.branchdownloader.ui.viewModel.BranchListViewModel
import br.com.marquesapps.branchdownloader.ui.viewModel.RepositoryViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class BranchListFragment : Fragment() {

    private lateinit var viewDataBinding: BranchListLayoutBinding
    private val bottomNavigationViewModel: BottomNavigationViewModel by sharedViewModel()
    private val branchListViewModel: BranchListViewModel by viewModel()
    private val repositoryViewModel: RepositoryViewModel by sharedViewModel()
    private val listComponentsData: ListComponentsData by lazy { ListComponentsData() }
    private val adapter: BranchAdapter by lazy { BranchAdapter(context) }
    private val branchDownloader: BranchDownloader by inject()

    private var page = DEFAULT_PAGE + 1
    private var hasPage = true
    private var cantSearch = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repositoryViewModel.repository.observe(this, { repository ->
            repository?.let { searchFirstPage(it) }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = BranchListLayoutBinding.inflate(inflater, container, false)
        viewDataBinding.listComponentsData = listComponentsData.also {
            it.initialState(
                messageVisibility = false,
                progressVisibility = true
            )
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationViewModel.setVisibility(true)
        configureRecycleView()
        configureAdapter()
    }

    private fun configureAdapter() {
        adapter.onBranchClickListener = {
            downloadBranch(it)
        }
    }

    private fun configureRecycleView() {
        viewDataBinding.branchListLayoutBranchList.layoutManager = LinearLayoutManager(context)
        viewDataBinding.branchListLayoutBranchList.adapter = adapter
        viewDataBinding.branchListLayoutBranchList.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(DOWN_DIRECTION)) {
                    repositoryViewModel.repository.value?.let {
                        searchingForNewPages(it)
                    }
                }
            }
        })
    }

    private fun resourceReturn(
        resource: Resource<List<BranchDTO>?>,
        whenSuccess: (branchList: List<BranchDTO>) -> Unit,
        whenFailure: () -> Unit
    ) {
        cantSearch = false
        if (resource.error == null) {
            resource.data?.let { whenSuccess(it) } ?: whenFailure()
        } else {
            whenFailure()
        }
    }

    private fun addNewBranches(newList: List<BranchDTO>) {
        page++
        val list = newList.map { Branch(it) }
        hasPage = list.size == RESULTS_PER_PAGE
        adapter.addNewBranches(list)
        this.listComponentsData.searchPageEndState(
            messageVisibility = false
        )
    }

    private fun searchingForNewPages(repository: Repository) {
        if (!hasPage || cantSearch)
            return

        cantSearch = true
        listComponentsData.searchPagesState()
        branchListViewModel.listBranches(repository, RESULTS_PER_PAGE, page)
            .observe(this, { resource ->
                cantSearch = false
                resourceReturn(resource, {
                    addNewBranches(it)
                }, {
                    listComponentsData.searchPageEndState(
                        messageVisibility = false
                    )
                    showMessageError(MESSAGE_THERE_WAS_AN_ERROR)
                })
            })
    }

    private fun searchFirstPage(repository: Repository) {
        listComponentsData.searchState()
        branchListViewModel.listBranches(repository, RESULTS_PER_PAGE, DEFAULT_PAGE)
            .observe(this, { resource ->
                resourceReturn(resource, {
                    replaceBranchList(it)
                }, {
                    listComponentsData.searchEndState(
                        false,
                        MESSAGE_TRY_AGAIN_LATER
                    )
                    showMessageError(MESSAGE_THERE_WAS_AN_ERROR)
                })
            })
    }


    private fun replaceBranchList(newList: List<BranchDTO>) {
        page = DEFAULT_PAGE + 1
        val list = newList.map { Branch(it) }
        adapter.replaceBranchList(list)
        hasPage = list.size == RESULTS_PER_PAGE
        this.listComponentsData.searchEndState(
            list.isNotEmpty(),
            "",
            messageVisibility = false
        )
    }

    private fun downloadBranch(branch: Branch) {
        repositoryViewModel.repository.value?.let { repository ->
            branchDownloader.download(repository, branch)
        }
    }
}