package com.fphoenixcorneae.widget.edittext.regex

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputConnectionWrapper
import androidx.appcompat.widget.AppCompatEditText

/**
 * @desc：RegexEditText
 * @date：2022/07/18 11:40
 */
class RegexEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle,
) : AppCompatEditText(context, attrs, defStyleAttr) {

    /** 正则表达式 */
    private val mRegex = REGEX_NAME

    override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection {
        return RegexInputConnection(mRegex, super.onCreateInputConnection(outAttrs), false)
    }

    /**
     * 设置正则
     */
    fun setRegex(vararg regex: String) {
        mRegex.clear()
        mRegex.addAll(regex)
    }

    /**
     * @desc：RegexInputConnection
     * @date：2022/07/18 11:48
     */
    class RegexInputConnection(
        val regex: List<String>,
        target: InputConnection?,
        mutable: Boolean,
    ) : InputConnectionWrapper(target, mutable) {

        /**
         * 拦截内容
         */
        override fun commitText(text: CharSequence?, newCursorPosition: Int): Boolean {
            var commit = false
            run breaking@{
                regex.forEach {
                    if (text.toString().matches(Regex(it))) {
                        commit = true
                        return@breaking
                    }
                }
            }
            // 执行父类的 commitText（即super.commitText(text, newCursorPosition)）那么表示不拦截，如果返回false则表示拦截，
            return commit && super.commitText(text, newCursorPosition)
        }

        override fun sendKeyEvent(event: KeyEvent?): Boolean {
            return super.sendKeyEvent(event)
        }

        override fun setSelection(start: Int, end: Int): Boolean {
            return super.setSelection(start, end)
        }
    }

    companion object {
        /** 只支持输入汉字、字母、数字，不支持输入特殊字符和空格 */
        private val REGEX_NAME = mutableListOf(
            // 汉字
            "[\u4e00-\u9fa5]+",
            // 数字和字母
            "[a-zA-Z0-9]+"
        )
    }
}