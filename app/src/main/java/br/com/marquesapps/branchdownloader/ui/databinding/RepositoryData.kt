package br.com.marquesapps.branchdownloader.ui.databinding

import androidx.databinding.ObservableField
import br.com.marquesapps.branchdownloader.model.Repository

class RepositoryData {

    val name = ObservableField<String>()
    val description = ObservableField<String>()
    val image = ObservableField<String>()
    val owner = ObservableField<String>()
    val readme = ObservableField<String>()

    fun updateRepositoryData(changes: Repository){
        this.name.set(changes.name)
        this.description.set(changes.description)
        this.image.set(changes.image)
        this.owner.set(changes.owner)
    }

}