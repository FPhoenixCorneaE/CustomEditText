package com.fphoenixcorneae.widget.edittext.password

import android.graphics.drawable.Drawable
import android.text.InputType
import android.view.View
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter

/**
 * @param iconMarginEnd      图标外边距, dp value
 * @param iconPadding        图标内边距, dp value
 * @param clearIconVisible   清除图标可见性
 * @param clearIcon          清除图标, Drawable
 * @param clearIconSize      清除图标大小, dp value
 * @param clearIconTint      清除图标着色, [ColorInt]
 * @param onClear            清除输入内容监听
 * @param pwdIconVisible     密码显示/隐藏图标可见性
 * @param pwdShowIcon        密码显示图标, Drawable
 * @param pwdHideIcon        密码隐藏图标, Drawable
 * @param pwdIconSize        密码显示/隐藏图标大小, dp value
 * @param pwdIconTint        密码显示/隐藏图标着色, [ColorInt]
 * @param pwdMaskChar        密码掩码字符, 默认是DOT('\u2022')
 * @param pwdMaskCharSpacing 密码掩码字符间距, 0f - 标准字间距，>0f - 放大字间距
 * @param onFocusChanged     焦点改变监听
 * @param inputType          输入类型, [InputType]
 */
@BindingAdapter(value = [
    "iconMarginEnd", "iconPadding", "clearIconVisible", "clearIcon", "clearIconSize", "clearIconTint", "onClear",
    "pwdIconVisible", "pwdShowIcon", "pwdHideIcon", "pwdIconSize", "pwdIconTint", "pwdMaskChar", "pwdMaskCharSpacing",
    "onFocusChanged", "android:inputType",
], requireAll = false)
fun setPasswordEditTextAttrs(
    view: PasswordEditText,
    iconMarginEnd: Float?,
    iconPadding: Float?,
    clearIconVisible: Boolean?,
    clearIcon: Drawable?,
    clearIconSize: Float?,
    @ColorInt clearIconTint: Int?,
    onClear: View.OnClickListener?,
    pwdIconVisible: Boolean?,
    pwdShowIcon: Drawable?,
    pwdHideIcon: Drawable?,
    pwdIconSize: Float?,
    @ColorInt pwdIconTint: Int?,
    pwdMaskChar: Char?,
    pwdMaskCharSpacing: Float?,
    onFocusChanged: View.OnFocusChangeListener?,
    inputType: Int?,
) {
    iconMarginEnd?.let { view.setIconMarginEnd(it) }
    iconPadding?.let { view.setIconPadding(it) }
    clearIconVisible?.let { view.setClearIconVisible(it) }
    clearIcon?.let { view.setClearIcon(it) }
    clearIconSize?.let { view.setClearIconSize(it) }
    clearIconTint?.let { view.setClearIconTint(it) }
    onClear?.let { view.setOnClear { it.onClick(view) } }
    pwdIconVisible?.let { view.setPwdIconVisible(it) }
    pwdShowIcon?.let { view.setPwdShowIcon(it) }
    pwdHideIcon?.let { view.setPwdHideIcon(it) }
    pwdIconSize?.let { view.setPwdIconSize(it) }
    pwdIconTint?.let { view.setPwdIconTint(it) }
    pwdMaskChar?.let { view.setPwdMaskChar(it) }
    pwdMaskCharSpacing?.let { view.setPwdMaskCharSpacing(it) }
    onFocusChanged?.let { view.setOnFocusChanged { passwordEditText, b -> it.onFocusChange(passwordEditText, b) } }
    inputType?.let { view.inputType = it }
}