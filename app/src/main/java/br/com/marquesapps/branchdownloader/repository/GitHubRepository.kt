package br.com.marquesapps.branchdownloader.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.marquesapps.branchdownloader.model.Repository
import br.com.marquesapps.branchdownloader.retrofit.model.BranchDTO
import br.com.marquesapps.branchdownloader.retrofit.model.HtmlForm
import br.com.marquesapps.branchdownloader.retrofit.model.ReadMeDTO
import br.com.marquesapps.branchdownloader.retrofit.model.SearchDTO
import br.com.marquesapps.branchdownloader.retrofit.webclient.GitHubWebClient

class GitHubRepository(
    private val gitHubWebClient: GitHubWebClient
) {

    fun searchRepositories(
        query: String,
        perPage: Int,
        page: Int
    ) : LiveData<Resource<SearchDTO?>>{
        val liveData = MutableLiveData<Resource<SearchDTO?>>()
        gitHubWebClient.searchRepositories(query, perPage, page, whenSuccessful = {
            liveData.postValue(Resource(it))
        }, whenFailure = {
            liveData.postValue(
                Resource(data = null, error = it.message)
            )
        })
        return liveData
    }

    fun readMe(
        repository: Repository
    ): LiveData<Resource<ReadMeDTO?>>{
        val liveData = MutableLiveData<Resource<ReadMeDTO?>>()
        gitHubWebClient.readMe(repository, whenSuccessful = {
            liveData.postValue(Resource(it))
        }, whenFailure = {
            liveData.postValue(
                Resource(data = null, error = it.message)
            )
        })
        return liveData
    }

    fun html(
        htmlForm: HtmlForm
    ): LiveData<Resource<String?>> {
        val liveData = MutableLiveData<Resource<String?>>()
        gitHubWebClient.html(htmlForm, whenSuccessful = {
            liveData.postValue(Resource(it))
        }, whenFailure = {
            liveData.postValue(
                Resource(data = null, error = it.message)
            )
        })
        return liveData
    }

    fun listBranches(
        repository: Repository,
        perPage: Int,
        page: Int
    ): LiveData<Resource<List<BranchDTO>?>>{
        val liveData = MutableLiveData<Resource<List<BranchDTO>?>>()
        gitHubWebClient.listBranches(repository,perPage,page, whenSuccessful = {
            liveData.postValue(Resource(it))
        }, whenFailure = {
            liveData.postValue(
                Resource(data = null, error = it.message)
            )
        })
        return liveData
    }

}