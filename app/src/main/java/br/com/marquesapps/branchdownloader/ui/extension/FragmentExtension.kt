package br.com.marquesapps.branchdownloader.ui.extension

import androidx.fragment.app.Fragment
import br.com.marquesapps.branchdownloader.R
import com.google.android.material.snackbar.Snackbar

fun Fragment.showMessageError(message: String) {
    this.view?.let {
        Snackbar.make(it, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(resources.getColor(R.color.red_A400))
            .setTextColor(resources.getColor(R.color.white))
            .show()
    }
}
