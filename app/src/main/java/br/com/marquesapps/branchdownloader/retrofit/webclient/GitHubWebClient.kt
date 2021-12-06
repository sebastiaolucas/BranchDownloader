package br.com.marquesapps.branchdownloader.retrofit.webclient

import br.com.marquesapps.branchdownloader.constants.MESSAGE_REQUEST_WAS_UNSUCCESSFUL
import br.com.marquesapps.branchdownloader.model.Repository
import br.com.marquesapps.branchdownloader.retrofit.model.BranchDTO
import br.com.marquesapps.branchdownloader.retrofit.model.HtmlForm
import br.com.marquesapps.branchdownloader.retrofit.model.ReadMeDTO
import br.com.marquesapps.branchdownloader.retrofit.model.SearchDTO
import br.com.marquesapps.branchdownloader.retrofit.service.GitHubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GitHubWebClient(
    private val gitHubService: GitHubService
) {

    private fun <T> executeRequisition(
        call: Call<T>,
        whenSuccessful: (any: T?) -> Unit,
        whenFailure: (e: Throwable) -> Unit
    ){
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if(response.isSuccessful){
                    whenSuccessful(response.body())
                }else{
                    whenFailure(Throwable(MESSAGE_REQUEST_WAS_UNSUCCESSFUL))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                whenFailure(t)
            }
        })
    }

    fun searchRepositories(
        query: String,
        perPage: Int,
        page: Int,
        whenSuccessful: (searchDTO: SearchDTO?) -> Unit,
        whenFailure: (e: Throwable) -> Unit
    ){
        executeRequisition(
            gitHubService.getSearchResults(query, perPage, page),
            whenSuccessful,
            whenFailure
        )
    }

    fun readMe(
        repository: Repository,
        whenSuccessful: (readMeDTO: ReadMeDTO?) -> Unit,
        whenFailure: (e: Throwable) -> Unit
    ){
        executeRequisition(
            gitHubService.getReadMe(repository.owner, repository.name),
            whenSuccessful,
            whenFailure
        )
    }

    fun html(
        htmlForm: HtmlForm,
        whenSuccessful: (html: String?) -> Unit,
        whenFailure: (e: Throwable) -> Unit
    ){
        executeRequisition(
            gitHubService.postHtml(htmlForm),
            whenSuccessful,
            whenFailure
        )
    }

    fun listBranches(
        repository: Repository,
        perPage: Int,
        page: Int,
        whenSuccessful: (branchesDTO: List<BranchDTO>?) -> Unit,
        whenFailure: (e: Throwable) -> Unit
    ){
        executeRequisition(
            gitHubService.getBranchList(repository.owner, repository.name, perPage, page),
            whenSuccessful,
            whenFailure
        )
    }

}