package com.fphoenixcorneae.widget.edittext.emoji

import androidx.databinding.BindingAdapter

@BindingAdapter("disableEmoji")
fun disableEmoji(view: EmojiDisableEditText, disable: Boolean) {
    view.disableEmoji(disableEmoji = disable)
}