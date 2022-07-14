package com.fphoenixcorneae.widget.edittext.prefix

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
 * @desc：PrefixEditText
 * @date：2022/07/13 17:51
 */
class PrefixEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle,
) : AppCompatEditText(context, attrs, defStyleAttr), TextWatcher {

    /** 前缀 */
    var prefixText: String? = "￥"

    /** 前缀颜色 */
    @ColorInt
    var prefixColor: Int = Color.RED

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
            if (prefixText.isNullOrBlank()) {
                it.onTextChanged(s)
            } else {
                it.onTextChanged(s.toString().replace(prefixText!!, ""))
            }
        }
        if (prefixText.isNullOrBlank()) {
            return
        }
        if (s.toString().isNotEmpty()) {
            // 移除输入监听
            removeTextChangedListener(this)
            if (s.toString() == prefixText) {
                setText("")
            } else {
                // 去重
                val str = prefixText + s.toString().replace(prefixText!!, "")

                if (prefixColor != 0) {
                    // 设置文字颜色
                    SpannableStringBuilder(str).apply {
                        setSpan(
                            ForegroundColorSpan(prefixColor),
                            0,
                            prefixText!!.length,
                            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
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
        if (prefixText.isNullOrBlank()) {
            return
        }
        if (text.toString().isNotEmpty()) {
            setSelection(text.toString().length)
        }
    }


    fun setOnTextChanged(onTextChanged: OnTextChanged) {
        mOnTextChanged = onTextChanged
    }

    interface OnTextChanged {
        fun onTextChanged(text: CharSequence?)
    }
}