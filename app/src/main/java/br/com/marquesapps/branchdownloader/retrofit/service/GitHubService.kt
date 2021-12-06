package br.com.marquesapps.branchdownloader.retrofit.service

import br.com.marquesapps.branchdownloader.retrofit.model.BranchDTO
import br.com.marquesapps.branchdownloader.retrofit.model.HtmlForm
import br.com.marquesapps.branchdownloader.retrofit.model.ReadMeDTO
import br.com.marquesapps.branchdownloader.retrofit.model.SearchDTO
import retrofit2.Call
import retrofit2.http.*

private const val HEADER_ACCEPT = "Accept: application/vnd.github.v3+json"

interface GitHubService {

    @Headers(HEADER_ACCEPT)
    @GET("search/repositories")
    fun getSearchResults(
        @Query("q") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Call<SearchDTO>


    @Headers(HEADER_ACCEPT)
    @GET("repos/{owner}/{repo}/readme")
    fun getReadMe(
        @Path("owner") repositoryOwner: String,
        @Path("repo") repositoryName: String
    ): Call<ReadMeDTO>

    @Headers(HEADER_ACCEPT)
    @POST("markdown")
    fun postHtml(@Body htmlForm: HtmlForm): Call<String>

    @Headers(HEADER_ACCEPT)
    @GET("repos/{owner}/{repo}/branches")
    fun getBranchList(
        @Path("owner") repositoryOwner: String,
        @Path("repo") repositoryName: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Call<List<BranchDTO>>

}