package br.com.marquesapps.branchdownloader.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.marquesapps.branchdownloader.model.Repository
import br.com.marquesapps.branchdownloader.repository.GitHubRepository
import br.com.marquesapps.branchdownloader.repository.Resource
import br.com.marquesapps.branchdownloader.retrofit.model.HtmlForm
import br.com.marquesapps.branchdownloader.retrofit.model.ReadMeDTO

class RepositoryReadMeViewModel(
    private val gitHubRepository: GitHubRepository
): ViewModel() {

    fun readMe(
        repository: Repository
    ): LiveData<Resource<ReadMeDTO?>> = gitHubRepository.readMe(repository)

    fun html(
        htmlForm: HtmlForm
    ): LiveData<Resource<String?>> = gitHubRepository.html(htmlForm)

}