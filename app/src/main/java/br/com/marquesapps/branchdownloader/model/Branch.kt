package br.com.marquesapps.branchdownloader.model

import br.com.marquesapps.branchdownloader.retrofit.model.BranchDTO

data class Branch(
    val name: String,
    val commit: String
){
    constructor(dto: BranchDTO): this(
        name = dto.name,
        commit = dto.commit.sha
    )
}
