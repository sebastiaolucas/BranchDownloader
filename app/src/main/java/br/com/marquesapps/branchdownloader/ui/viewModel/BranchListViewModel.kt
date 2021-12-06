package br.com.marquesapps.branchdownloader.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.marquesapps.branchdownloader.model.Repository
import br.com.marquesapps.branchdownloader.repository.GitHubRepository
import br.com.marquesapps.branchdownloader.repository.Resource
import br.com.marquesapps.branchdownloader.retrofit.model.BranchDTO

class BranchListViewModel(
    private val gitHubRepository: GitHubRepository
): ViewModel() {

    fun listBranches(
        repository: Repository,
        perPage: Int,
        page: Int
    ): LiveData<Resource<List<BranchDTO>?>> = gitHubRepository.listBranches(repository, perPage, page)


}