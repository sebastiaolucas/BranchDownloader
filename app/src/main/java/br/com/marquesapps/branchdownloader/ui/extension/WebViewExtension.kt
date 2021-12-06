package br.com.marquesapps.branchdownloader.ui.extension

import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import android.webkit.WebView
import androidx.databinding.BindingAdapter

private const val MIME_HTML : String = "text/html"
private const val ENCODING : String = "base64"

@BindingAdapter("android:loadHtml")
fun WebView.loadHtml(data: String?){
    data?.let {
        val htmlEncoded = encodeToString(data.toByteArray(), DEFAULT)
        this.loadData(htmlEncoded, MIME_HTML, ENCODING)
    }
}