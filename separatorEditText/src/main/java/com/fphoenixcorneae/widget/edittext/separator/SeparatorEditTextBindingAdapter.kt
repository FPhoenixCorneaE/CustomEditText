package com.fphoenixcorneae.widget.edittext.separator

import androidx.databinding.BindingAdapter

/**
 * @param textSeparator 分隔符
 * @param textPattern   分隔样式
 */
@BindingAdapter(value = ["textSeparator", "textPattern"], requireAll = false)
fun setSeparatorEditTextAttrs(
    view: SeparatorEditText,
    textSeparator: String?,
    textPattern: IntArray?,
) {
    textSeparator?.let {
        view.setSeparator(it)
    }
    textPattern?.let {
        view.setPattern(it)
    }
}