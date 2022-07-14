package com.fphoenixcorneae.widget.edittext.prefix

import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["prefixText", "prefixColor"], requireAll = false)
fun setPrefix(view: PrefixEditText, text: String?, color: Int) {
    view.prefixText = text
    view.prefixColor = color
}

@BindingAdapter("onTextChanged")
fun onTextChanged(view: PrefixEditText, onTextChanged: PrefixEditText.OnTextChanged) {
    view.setOnTextChanged(onTextChanged)
}