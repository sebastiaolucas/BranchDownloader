package br.com.marquesapps.branchdownloader.ui.extension

import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation

fun ImageView.loadRoundImage(src: String){
    val drawable = this.drawable
    this.load(src){
        transformations(CircleCropTransformation())
        placeholder(drawable)
        fallback(drawable)
        error(drawable)
    }
}

fun ImageView.loadImage(src: String){
    val drawable = this.drawable
    this.load(src){
        placeholder(drawable)
        fallback(drawable)
        error(drawable)
    }
}