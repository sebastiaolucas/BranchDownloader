package br.com.marquesapps.branchdownloader.model

import br.com.marquesapps.branchdownloader.retrofit.model.RepositoryDTO

data class Repository(
    val name: String,
    val description: String,
    val image: String,
    val owner: String
){

    constructor(dto: RepositoryDTO) : this(
        name = dto.name,
        description = dto.description ?: "",
        image = dto.owner.avatarUrl ?: "",
        owner = dto.owner.login
    )

}