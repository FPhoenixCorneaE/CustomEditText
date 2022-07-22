package com.fphoenixcorneae.widget.edittext.separator

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

/**
 * @desc：SeparatorEditText
 * @date：2022/07/22 15:36
 */
class SeparatorEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle,
) : AppCompatEditText(context, attrs, defStyleAttr), TextWatcher {

    /** 分隔样式, e.g.：138 8888 8888 */
    private lateinit var mPattern: IntArray

    /** 分隔符, 默认为空格符“ ” */
    private lateinit var mSeparator: String

    /** 分隔符下标 */
    private lateinit var mSeparatorIndexes: IntArray

    init {
        setPattern(DEFAULT_PATTERN)
        setSeparator(DEFAULT_SEPARATOR)
        addTextChangedListener(this)
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
    }

    override fun afterTextChanged(p0: Editable?) {
        removeTextChangedListener(this)
        setTextWithSeparator(p0.toString().replace(mSeparator, "").trim())
        addTextChangedListener(this)
    }

    /**
     * 利用分隔符设置文本
     */
    private fun setTextWithSeparator(s: CharSequence) {
        if (s.isEmpty() || !::mSeparatorIndexes.isInitialized || mSeparatorIndexes.isEmpty()) {
            return
        }

        val builder: StringBuilder = StringBuilder()
        s.forEachIndexed { i, c ->
            builder.append(s.subSequence(i, i + 1))
            mSeparatorIndexes.forEachIndexed { j, index ->
                if (i == index && j < mSeparatorIndexes.size - 1) {
                    builder.insert(builder.length - 1, mSeparator)
                }
            }
        }
        val text = builder.toString()
        setText(text)
        setSelection(text.length)
    }

    /**
     * 设置分隔符
     */
    fun setSeparator(separator: String) {
        if (!::mSeparator.isInitialized || mSeparator != separator) {
            mSeparator = separator
            if (separator.isNotEmpty()) {
                if (inputType == InputType.TYPE_CLASS_NUMBER
                    || inputType == InputType.TYPE_NUMBER_FLAG_DECIMAL
                    || inputType == InputType.TYPE_NUMBER_FLAG_SIGNED
                    || inputType == InputType.TYPE_NUMBER_VARIATION_NORMAL
                ) {
                    // 如果inputType是number，分隔符就不能插入，所以要改为InputType.TYPE_CLASS_PHONE
                    inputType = InputType.TYPE_CLASS_PHONE
                }
            }
            setTextWithSeparator(text.toString().replace(mSeparator, "").trim())
        }
    }

    /**
     * 设置分隔样式
     * e.g. {3, 4, 4}    - 138 8888 8888
     *      {4, 4, 4, 4} - xxxx-xxxx-xxxx-xxxx
     */
    fun setPattern(pattern: IntArray) {
        if (!::mPattern.isInitialized || !mPattern.contentEquals(pattern)) {
            mPattern = pattern
            var index = 0
            mSeparatorIndexes = mPattern.flatMap {
                index += it
                listOf(index)
            }.toIntArray()

            // 设置最大输入长度
            if (mSeparatorIndexes.isNotEmpty()) {
                filters = filters.filter { it !is InputFilter.LengthFilter }
                    .toMutableList()
                    .apply {
                        add(InputFilter.LengthFilter(mSeparatorIndexes.last() + mPattern.size - 1))
                    }.toTypedArray()
            }
        }
    }

    companion object {
        private val DEFAULT_PATTERN = intArrayOf(3, 4, 4)
        private const val DEFAULT_SEPARATOR = " "
    }
}