package br.com.marquesapps.branchdownloader.retrofit.model

import com.google.gson.annotations.SerializedName

data class SearchDTO(
    @SerializedName("total_count")
    val totalCount: Int,
    val items: List<RepositoryDTO>
)