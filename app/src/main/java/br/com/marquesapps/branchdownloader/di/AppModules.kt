package br.com.marquesapps.branchdownloader.di

import br.com.marquesapps.branchdownloader.downloader.BranchDownloader
import br.com.marquesapps.branchdownloader.repository.GitHubRepository
import br.com.marquesapps.branchdownloader.retrofit.AppRetrofit
import br.com.marquesapps.branchdownloader.retrofit.service.GitHubService
import br.com.marquesapps.branchdownloader.retrofit.webclient.GitHubWebClient
import br.com.marquesapps.branchdownloader.ui.viewModel.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single<BranchDownloader> { BranchDownloader(get()) }
    single<GitHubService> { AppRetrofit().gitHubService }
    single<GitHubWebClient> { GitHubWebClient(get()) }
    single<GitHubRepository> { GitHubRepository(get()) }
}

val viewModelModule = module {
    viewModel<BottomNavigationViewModel> { BottomNavigationViewModel() }
    viewModel<RepositoryViewModel> { RepositoryViewModel() }
    viewModel<RepositorySearchViewModel> { RepositorySearchViewModel(get()) }
    viewModel<RepositoryReadMeViewModel> { RepositoryReadMeViewModel(get()) }
    viewModel<BranchListViewModel> { BranchListViewModel(get()) }
}
