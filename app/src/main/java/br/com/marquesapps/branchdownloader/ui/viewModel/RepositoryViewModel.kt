package br.com.marquesapps.branchdownloader.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.marquesapps.branchdownloader.model.Repository

class RepositoryViewModel: ViewModel() {

    private val _repository : MutableLiveData<Repository?> = MutableLiveData<Repository?>()
        .apply { value = null }

    val repository : LiveData<Repository?> = _repository

    fun setValue(repository: Repository){
        _repository.postValue(repository)
    }

}