package com.fphoenixcorneae.widget.edittext.regex

import androidx.databinding.BindingAdapter

@BindingAdapter("regex")
fun setRegex(view: RegexEditText, regex: List<String>) {
    view.setRegex(*regex.toTypedArray())
}