package br.com.marquesapps.branchdownloader.retrofit.model

data class RepositoryDTO(
    val id: Int,
    val name: String,
    val description: String?,
    val owner: OwnerDTO
)
