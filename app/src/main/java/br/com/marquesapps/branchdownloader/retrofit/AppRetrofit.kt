package br.com.marquesapps.branchdownloader.retrofit

import br.com.marquesapps.branchdownloader.retrofit.service.GitHubService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val BASE_URL = "https://api.github.com/"

class AppRetrofit {

    private val retrofit: Retrofit by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    val gitHubService: GitHubService
        get() = retrofit.create(GitHubService::class.java)
}