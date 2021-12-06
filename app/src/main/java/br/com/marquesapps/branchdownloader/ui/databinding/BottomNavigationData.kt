package br.com.marquesapps.branchdownloader.ui.databinding

import androidx.databinding.ObservableField

private const val VISIBILITY_INITIAL : Boolean = false

class BottomNavigationData {
    val navVisibility = ObservableField<Boolean>().also {
        it.set(VISIBILITY_INITIAL)
    }
}