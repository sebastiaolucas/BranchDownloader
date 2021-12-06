package br.com.marquesapps.branchdownloader.repository

data class Resource<T>(
    val data: T,
    val error: String? = null
)
