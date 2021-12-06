package br.com.marquesapps.branchdownloader.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Base64.DEFAULT
import android.util.Base64.decode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.marquesapps.branchdownloader.databinding.RepositoryLayoutBinding
import br.com.marquesapps.branchdownloader.model.Repository
import br.com.marquesapps.branchdownloader.retrofit.model.HtmlForm
import br.com.marquesapps.branchdownloader.ui.databinding.RepositoryData
import br.com.marquesapps.branchdownloader.ui.viewModel.BottomNavigationViewModel
import br.com.marquesapps.branchdownloader.ui.viewModel.RepositoryReadMeViewModel
import br.com.marquesapps.branchdownloader.ui.viewModel.RepositoryViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class RepositoryFragment : Fragment() {

    private lateinit var viewDataBinding: RepositoryLayoutBinding
    private val bottomNavigationViewModel: BottomNavigationViewModel by sharedViewModel()
    private val repositoryViewModel: RepositoryViewModel by sharedViewModel()
    private val readMeRepositoryViewModel: RepositoryReadMeViewModel by viewModel()
    private val repositoryData: RepositoryData by lazy { RepositoryData() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = RepositoryLayoutBinding.inflate(inflater, container, false)
        viewDataBinding.repositoryData = repositoryData
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        bottomNavigationViewModel.setVisibility(true)
    }

    private fun configureViews(){
        val repository = repositoryViewModel.repository.value
        repository?.let {
            loadReadMe(it)
            repositoryData.updateRepositoryData(it)
        }
    }


    @SuppressLint("FragmentLiveDataObserve")
    private fun loadReadMe(repository: Repository) {
        readMeRepositoryViewModel.readMe(repository)
            .observe(this, { resource ->
                if(resource.error == null){
                    resource.data?.let { loadHtml(it.content) }
                }
            })
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun loadHtml(content: String) {
        val decode = decode(content.toByteArray(), DEFAULT)
        val out = String(decode)
        val htmlForm = HtmlForm(out)

        readMeRepositoryViewModel.html(htmlForm)
            .observe(this, { resource ->
                if(resource.error == null){
                    resource.data?.let{
                        repositoryData.readme.set(it)
                    }
                }
            })
    }


}