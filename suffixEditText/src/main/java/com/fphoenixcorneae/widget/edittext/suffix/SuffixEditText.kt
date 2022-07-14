package com.fphoenixcorneae.widget.edittext.suffix

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatEditText

/**
 * @desc：SuffixEditText
 * @date：2022/07/13 17:14
 */
class SuffixEditText(
    context: Context,
    attrs: AttributeSet? = null,
) : AppCompatEditText(context, attrs), TextWatcher {

    /** 后缀 */
    var suffixText: String? = "元"

    /** 后缀颜色 */
    @ColorInt
    var suffixColor: Int = Color.BLACK

    init {
        addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        if (suffixText.isNullOrBlank()) {
            return
        }
        if (s.toString().isNotEmpty()) {
            // 移除输入监听
            removeTextChangedListener(this)
            if (s.toString() == suffixText) {
                setText("")
            } else {
                // 去重
                val str = s.toString().replace(suffixText!!, "") + suffixText

                if (suffixColor != 0) {
                    // 设置文字颜色
                    SpannableStringBuilder(str).apply {
                        setSpan(
                            ForegroundColorSpan(suffixColor),
                            str.length - suffixText!!.length,
                            str.length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }.also {
                        text = it
                    }
                } else {
                    setText(str)
                }
            }
            addTextChangedListener(this)
        }
    }

    /**
     * 设置光标位置
     */
    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        if (suffixText.isNullOrBlank()) {
            return
        }
        if (text.toString().isNotEmpty()) {
            setSelection(text.toString().length - suffixText!!.length)
        } else {
            setSelection(selStart)
        }
    }
}