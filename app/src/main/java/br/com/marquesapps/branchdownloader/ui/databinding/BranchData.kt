package br.com.marquesapps.branchdownloader.ui.databinding

import androidx.databinding.ObservableField
import br.com.marquesapps.branchdownloader.model.Branch

class BranchData {

    val name = ObservableField<String>()
    val commit = ObservableField<String>()

    fun updateBranchData(changes: Branch){
        this.name.set(changes.name)
        this.commit.set(changes.commit)
    }

}