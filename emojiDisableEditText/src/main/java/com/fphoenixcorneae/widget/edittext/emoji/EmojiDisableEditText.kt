package com.fphoenixcorneae.widget.edittext.emoji

import android.content.Context
import android.text.InputFilter
import android.text.Spanned
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

/**
 * @desc：EmojiDisableEditText
 * @date：2022/07/14 14:30
 */
class EmojiDisableEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle,
) : AppCompatEditText(context, attrs, defStyleAttr) {

    /** disable emoji and some special symbol input */
    private var disableEmoji = false

    init {
        disableEmoji(true)
    }

    fun disableEmoji(disableEmoji: Boolean) {
        if (this.disableEmoji == disableEmoji) {
            return
        }
        this.disableEmoji = disableEmoji
        val oldFilters = filters
        val newFilters: Array<InputFilter?>
        if (disableEmoji) {
            newFilters = arrayOfNulls(oldFilters.size + 1)
            newFilters[oldFilters.size] = EmojiExcludeFilter()
            System.arraycopy(oldFilters, 0, newFilters, 0, oldFilters.size)
        } else {
            val list: MutableList<InputFilter> = ArrayList()
            for (filter in oldFilters) {
                if (filter !is EmojiExcludeFilter) {
                    list.add(filter)
                }
            }
            newFilters = list.toTypedArray()
        }
        filters = newFilters
    }

    /**
     * Disable emoji and other special symbol input.
     */
    private class EmojiExcludeFilter : InputFilter {
        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dstart: Int,
            dend: Int,
        ): CharSequence? {
            for (i in start until end) {
                val type = Character.getType(source[i])
                if (type.toByte() == Character.SURROGATE || type.toByte() == Character.OTHER_SYMBOL) {
                    return ""
                }
            }
            return null
        }
    }
}