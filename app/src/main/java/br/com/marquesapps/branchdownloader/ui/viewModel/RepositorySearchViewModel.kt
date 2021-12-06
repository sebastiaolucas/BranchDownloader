package br.com.marquesapps.branchdownloader.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.marquesapps.branchdownloader.repository.GitHubRepository
import br.com.marquesapps.branchdownloader.repository.Resource
import br.com.marquesapps.branchdownloader.retrofit.model.SearchDTO

class RepositorySearchViewModel(
    private val gitHubRepository: GitHubRepository
): ViewModel() {

    fun searchRepositories(
        query: String,
        perPage: Int,
        page: Int
    ): LiveData<Resource<SearchDTO?>> = gitHubRepository.searchRepositories(query, perPage, page)

}