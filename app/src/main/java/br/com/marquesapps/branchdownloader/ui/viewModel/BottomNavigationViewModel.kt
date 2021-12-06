package br.com.marquesapps.branchdownloader.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BottomNavigationViewModel : ViewModel() {

    private val _visibility = MutableLiveData<Boolean>().apply {
        value = false
    }

    val visibility: LiveData<Boolean> = _visibility

    fun setVisibility(visibility: Boolean) {
        _visibility.postValue(visibility)
    }
}