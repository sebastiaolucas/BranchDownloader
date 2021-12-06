package br.com.marquesapps.branchdownloader.ui.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import br.com.marquesapps.branchdownloader.ui.extension.loadRoundImage
import br.com.marquesapps.branchdownloader.ui.extension.loadImage

@BindingAdapter("android:loadRoundImage")
fun ImageView.loadRoundImageAdapter(src: String?){
    src?.let{
        loadRoundImage(it)
    }
}

@BindingAdapter("android:loadImage")
fun ImageView.loadImageAdapter(src: String?){
    src?.let{
        loadImage(it)
    }
}