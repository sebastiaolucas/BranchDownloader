package br.com.marquesapps.branchdownloader.ui.databinding

import androidx.databinding.ObservableField

class ListComponentsData {
    val messageText = ObservableField<String>()
    val messageCenter = ObservableField<Boolean>()
    val messageVisibility = ObservableField<Boolean>()
    val listVisibility = ObservableField<Boolean>()
    val progressVisibility = ObservableField<Boolean>()

    private fun changeState(
        messageText: String,
        messageCenter: Boolean,
        messageVisibility: Boolean,
        listVisibility: Boolean,
        progressVisibility: Boolean
    ) {
        this.messageText.set(messageText)
        this.listVisibility.set(listVisibility)
        this.progressVisibility.set(progressVisibility)
        this.messageVisibility.set(messageVisibility)
        this.messageCenter.set(messageCenter)
    }

    fun initialState(
        messageText: String = this.messageText.get() ?: "",
        messageCenter: Boolean = true,
        messageVisibility: Boolean = true,
        listVisibility: Boolean = false,
        progressVisibility: Boolean = false
    ) = changeState(
        messageText,
        messageCenter,
        messageVisibility,
        listVisibility,
        progressVisibility
    )

    fun searchState(
        messageText: String = this.messageText.get() ?: "",
        messageCenter: Boolean = false,
        messageVisibility: Boolean = false,
        listVisibility: Boolean = false,
        progressVisibility: Boolean = true
    ) = changeState(
        messageText,
        messageCenter,
        messageVisibility,
        listVisibility,
        progressVisibility
    )

    fun searchEndState(
        hasResults: Boolean,
        emptyMessage: String,
        messageText: String = this.messageText.get() ?: "",
        messageCenter: Boolean = !hasResults,
        listVisibility: Boolean = hasResults,
        messageVisibility: Boolean = true,
        progressVisibility: Boolean = false
    ) {
        val message = if(hasResults) {
            messageText
        }else{
            emptyMessage
        }
        changeState(
            messageText = message,
            messageCenter,
            messageVisibility,
            listVisibility,
            progressVisibility
        )
    }

    fun searchPagesState(
        messageText: String = this.messageText.get() ?: "",
        messageCenter: Boolean = false,
        messageVisibility: Boolean = true,
        listVisibility: Boolean = true,
        progressVisibility: Boolean = true
    ) = changeState(
        messageText,
        messageCenter,
        messageVisibility,
        listVisibility,
        progressVisibility
    )

    fun searchPageEndState(
        messageText: String = this.messageText.get() ?: "",
        messageCenter: Boolean = false,
        messageVisibility: Boolean = true,
        listVisibility: Boolean = true,
        progressVisibility: Boolean = false
    ) = changeState(
        messageText,
        messageCenter,
        messageVisibility,
        listVisibility,
        progressVisibility
    )
}


