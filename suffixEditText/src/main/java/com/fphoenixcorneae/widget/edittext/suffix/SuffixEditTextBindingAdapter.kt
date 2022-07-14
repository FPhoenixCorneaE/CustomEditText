package com.fphoenixcorneae.widget.edittext.suffix

import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["suffixText", "suffixColor"], requireAll = false)
fun setSuffix(view: SuffixEditText, text: String?, color: Int) {
    view.suffixText = text
    view.suffixColor = color
}