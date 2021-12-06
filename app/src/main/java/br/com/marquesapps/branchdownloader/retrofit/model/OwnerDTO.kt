package br.com.marquesapps.branchdownloader.retrofit.model

import com.google.gson.annotations.SerializedName

data class OwnerDTO(
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String?
)
