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
class SuffixEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle,
) : AppCompatEditText(context, attrs, defStyleAttr), TextWatcher {

    /** 后缀 */
    var suffixText: String? = "元"

    /** 后缀颜色 */
    @ColorInt
    var suffixColor: Int = Color.BLACK

    /** 文本更改侦听 */
    private var mOnTextChanged: OnTextChanged? = null

    init {
        addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
        mOnTextChanged?.let { it ->
            if (suffixText.isNullOrBlank()) {
                it.onTextChanged(s)
            } else {
                it.onTextChanged(s.toString().replace(suffixText!!, ""))
            }
        }
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

    fun setOnTextChanged(onTextChanged: OnTextChanged) {
        mOnTextChanged = onTextChanged
    }

    interface OnTextChanged {
        fun onTextChanged(text: CharSequence?)
    }
}