package com.candybytes.taco.ui.util

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("goneUnless")
fun goneUnless(view: View?, visible: Boolean?) {
    view?.visibility = if (visible == true) View.VISIBLE else View.GONE
}



//Extension function to bind image from Coil library
@BindingAdapter("imageUri")
fun loadImage(imageView: ImageView, uri: Uri?) {
    if (uri != null) {
        imageView.load(uri)
    }
}





