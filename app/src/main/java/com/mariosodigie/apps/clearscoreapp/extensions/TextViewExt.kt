package com.mariosodigie.apps.clearscoreapp.extensions

import android.widget.TextView

fun TextView.formatString(resId: Int, formatArgs: Int){
    text = context.getString(resId, formatArgs)
}

fun TextView.formatString(resId: Int, formatArgs: String){
    text = context.getString(resId, formatArgs)
}