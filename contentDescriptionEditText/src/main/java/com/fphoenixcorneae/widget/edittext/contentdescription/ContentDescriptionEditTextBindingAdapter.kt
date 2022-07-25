package com.fphoenixcorneae.widget.edittext.contentdescription

import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter

/**
 * @param contentDescriptionMarginStart    内容描述开始外边距, dp value
 * @param contentDescriptionMarginEnd      内容描述结束外边距, dp value
 * @param contentDescriptionTextSize       内容描述文字大小, sp value
 * @param contentDescriptionTextColor      内容描述文字颜色, [ColorInt]
 * @param isContentDescriptionTextFakeBold 内容描述文字是否粗体
 */
@BindingAdapter(value = [
    "contentDescriptionMarginStart", "contentDescriptionMarginEnd", "contentDescriptionTextSize",
    "contentDescriptionTextColor", "isContentDescriptionTextFakeBold",
], requireAll = false)
fun setContentDescriptionEditTextAttrs(
    view: ContentDescriptionEditText,
    contentDescriptionMarginStart: Float?,
    contentDescriptionMarginEnd: Float?,
    contentDescriptionTextSize: Float?,
    @ColorInt contentDescriptionTextColor: Int?,
    isContentDescriptionTextFakeBold: Boolean?,
) {
    contentDescriptionMarginStart?.let {
        view.setContentDescriptionMarginStart(it)
    }
    contentDescriptionMarginEnd?.let {
        view.setContentDescriptionMarginEnd(it)
    }
    contentDescriptionTextSize?.let {
        view.setContentDescriptionTextSize(it)
    }
    contentDescriptionTextColor?.let {
        view.setContentDescriptionTextColor(it)
    }
    isContentDescriptionTextFakeBold?.let {
        view.setContentDescriptionTextFakeBold(it)
    }
}