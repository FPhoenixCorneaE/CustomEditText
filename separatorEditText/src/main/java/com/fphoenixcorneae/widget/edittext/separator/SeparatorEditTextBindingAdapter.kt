package com.fphoenixcorneae.widget.edittext.separator

import androidx.databinding.BindingAdapter

/**
 * @param textSeparator 分隔符
 * @param textPattern   分隔样式, e.g. phone number: "3, 4, 4", bank card: "4, 4, 4, 4, 3"
 */
@BindingAdapter(value = ["textSeparator", "textPattern"], requireAll = false)
fun setSeparatorEditTextAttrs(
    view: SeparatorEditText,
    textSeparator: String?,
    textPattern: String?,
) {
    textSeparator?.let {
        view.setSeparator(it)
    }
    textPattern?.let {
        runCatching {
            it.splitToSequence(",").flatMap {
                listOf(it.trim().toInt())
            }.toMutableList().toIntArray().let {
                view.setPattern(it)
            }
        }.onFailure {
            it.printStackTrace()
        }
    }
}