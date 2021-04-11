package com.example.movielist.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.example.movielist.R
import java.io.InputStream

fun ImageView.setPosterImage(posterName: String, context: Context) {

    try {
        val ims: InputStream = context.assets.open(posterName)
        val res = Drawable.createFromStream(ims, null)
        this.setImageDrawable(res)

        res?.let {

            setImageDrawable(res)
        }
    } catch (e: Exception) {
        setImageDrawable(context.getDrawable(R.drawable.placeholder_for_missing_posters))
    }
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

