package com.example.pqrs.utils

import android.text.Spanned
import androidx.core.text.HtmlCompat

fun String.toHtmlText() : Spanned {
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)
}