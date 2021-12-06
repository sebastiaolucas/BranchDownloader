package br.com.marquesapps.branchdownloader.retrofit.model

data class ReadMeDTO(
    val type: String,
    val encoding: String,
    val size: Int,
    val content: String
)
