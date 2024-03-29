package com.fphoenixcorneae.widget.edittext.password

import android.graphics.drawable.Drawable
import android.text.InputType
import android.view.View
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter

/**
 * @param iconMarginEnd        图标外边距, dp value
 * @param iconPadding          图标内边距, dp value
 * @param clearIconVisible     清除图标可见性
 * @param clearIcon            清除图标, Drawable
 * @param clearIconSize        清除图标大小, dp value
 * @param clearIconTint        清除图标着色, [ColorInt]
 * @param onClear              清除输入内容监听
 * @param pwdIconVisible       密码显示/隐藏图标可见性
 * @param pwdShowIcon          密码显示图标, Drawable
 * @param pwdHideIcon          密码隐藏图标, Drawable
 * @param pwdIconSize          密码显示/隐藏图标大小, dp value
 * @param pwdIconTint          密码显示/隐藏图标着色, [ColorInt]
 * @param pwdMaskChar          密码掩码字符, 默认是DOT('\u2022')
 * @param pwdMaskCharSpacing   密码掩码字符间距, 0f - 标准字间距，>0f - 放大字间距
 * @param textLetterSpacing    内容字符间距, 0f - 标准字间距，>0f - 放大字间距
 * @param onFocusChanged       焦点改变监听
 * @param inputType            输入类型, [InputType]
 * @param prefixIcon           前缀图标, Drawable
 * @param prefixIconSize       前缀图标大小, dp value
 * @param prefixIconTint       前缀图标着色, [ColorInt]
 * @param prefixIconFocusTint  前缀图标着色, [ColorInt]
 * @param cursorColor          光标颜色, [ColorInt]
 * @param isBottomLineShow     底部分割线显示与否
 * @param isBottomLineAnimated 底部分割线是否执行动画
 * @param bottomLineColor      底部分割线颜色, [ColorInt]
 * @param bottomLineFocusColor 底部分割线获焦后颜色, [ColorInt]
 * @param bottomLineHeight     底部分割线高度, dp value
 * @param bottomLineDuration   底部分割线动画时长, [Long]
 */
@BindingAdapter(value = [
    "iconMarginEnd", "iconPadding", "clearIconVisible", "clearIcon", "clearIconSize", "clearIconTint", "onClear",
    "pwdIconVisible", "pwdShowIcon", "pwdHideIcon", "pwdIconSize", "pwdIconTint", "pwdMaskChar", "pwdMaskCharSpacing",
    "textLetterSpacing", "onFocusChanged", "android:inputType", "prefixIcon", "prefixIconSize", "prefixIconTint",
    "prefixIconFocusTint", "cursorColor", "isBottomLineShow", "isBottomLineAnimated", "bottomLineColor", "bottomLineFocusColor",
    "bottomLineHeight", "bottomLineDuration"
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
    textLetterSpacing: Float?,
    onFocusChanged: View.OnFocusChangeListener?,
    inputType: Int?,
    prefixIcon: Drawable?,
    prefixIconSize: Float?,
    @ColorInt prefixIconTint: Int?,
    @ColorInt prefixIconFocusTint: Int?,
    @ColorInt cursorColor: Int?,
    isBottomLineShow: Boolean?,
    isBottomLineAnimated: Boolean?,
    @ColorInt bottomLineColor: Int?,
    @ColorInt bottomLineFocusColor: Int?,
    bottomLineHeight: Float?,
    bottomLineDuration: Long?,
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
    textLetterSpacing?.let { view.setTextLetterSpacing(it) }
    onFocusChanged?.let { view.setOnFocusChanged { passwordEditText, b -> it.onFocusChange(passwordEditText, b) } }
    inputType?.let { view.inputType = it }
    prefixIcon?.let { view.setPrefixIcon(it) }
    prefixIconSize?.let { view.setPrefixIconSize(it) }
    prefixIconTint?.let { view.setPrefixIconTint(it) }
    prefixIconFocusTint?.let { view.setPrefixIconFocusTint(it) }
    cursorColor?.let { view.setCursorColor(it) }
    isBottomLineShow?.let { view.setBottomLineShow(it) }
    isBottomLineAnimated?.let { view.setBottomLineAnimated(it) }
    bottomLineColor?.let { view.setBottomLineColor(it) }
    bottomLineFocusColor?.let { view.setBottomLineFocusColor(it) }
    bottomLineHeight?.let { view.setBottomLineHeight(it) }
    bottomLineDuration?.let { view.setBottomLineDuration(it) }
}