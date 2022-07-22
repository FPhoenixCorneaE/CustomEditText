package com.fphoenixcorneae.widget.edittext.password

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.util.AttributeSet
import android.util.TypedValue
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.widget.doAfterTextChanged
import com.fphoenixcorneae.widget.text.method.PasswordTransformationMethod
import kotlin.math.roundToInt

/**
 * @desc：PasswordEditText
 * @date：2022/07/18 15:57
 */
class PasswordEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle,
) : AppCompatEditText(context, attrs, defStyleAttr) {

    /** 是否是密码输入类型 */
    private val mIsPwdInputType
        get() = inputType == InputType.TYPE_NUMBER_VARIATION_PASSWORD
                || inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD
                || inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                || inputType == InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD

    /** 密码是否是显示状态 */
    private var mIsPwdShow = false

    /** 是否拥有输入焦点 */
    private var mHasFocus = false

    /** 单击监听 */
    private val mGestureDetector by lazy { GestureDetector(context, OnSingleTapConfirmedListener()) }

    /** 图标外边距 */
    private var mIconMarginEnd = DEFAULT_ICON_MARGIN_END

    /** 图标内边距 */
    private var mIconPadding = DEFAULT_ICON_PADDING

    /** 清除图标可见性 */
    private var mClearIconVisible = true

    /** 清除图标Drawable */
    private var mClearIcon: Drawable? = ContextCompat.getDrawable(context, R.drawable.svg_ic_clear)

    /** 清除图标大小 */
    private var mClearIconSize: Float = DEFAULT_ICON_SIZE

    /** 清除图标着色 */
    private var mClearIconTint = ColorStateList.valueOf(currentHintTextColor)

    /** 清除图标点击监听 */
    private var mOnClear: (() -> Unit)? = null

    /** 密码图标可见性 */
    private var mPwdIconVisible = true

    /** 密码显示图标Drawable */
    private var mPwdShowIcon: Drawable? = ContextCompat.getDrawable(context, R.drawable.svg_ic_password_show)

    /** 密码隐藏图标Drawable */
    private var mPwdHideIcon: Drawable? = ContextCompat.getDrawable(context, R.drawable.svg_ic_password_hide)

    /** 密码图标大小 */
    private var mPwdIconSize = DEFAULT_ICON_SIZE

    /** 密码图标着色 */
    private var mPwdIconTint = ColorStateList.valueOf(currentHintTextColor)

    /** 密码掩码字符 */
    private var mPwdMaskChar = DOT

    /** 密码掩码字符间距 */
    private var mPwdMaskCharSpacing = LETTER_SPACING_PASSWORD

    /** 内容字符间距 */
    private var mTextLetterSpacing = LETTER_SPACING_STANDARD

    /** 焦点改变监听 */
    private var mOnFocusChanged: ((PasswordEditText, Boolean) -> Unit)? = null

    /** 前缀图标Drawable */
    private var mPrefixIcon: Drawable? = null
    private var mPrefixIconSize = DEFAULT_ICON_SIZE
    private var mPrefixIconTint = ColorStateList.valueOf(currentHintTextColor)
    private var mPrefixIconFocusTint = ColorStateList.valueOf(currentHintTextColor)

    init {
        setOnFocusChangeListener { _, b ->
            mHasFocus = b
            setIconVisibility()
            mOnFocusChanged?.invoke(this, b)
        }
        doAfterTextChanged {
            setIconVisibility()
        }
        // 默认inputType为：android:inputType="textPassword"
        inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD

        // 设置paddingEnd
        setPaddingEnd()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        setIconVisibility()
    }

    override fun setInputType(type: Int) {
        super.setInputType(type)
        setPwdTransformationMethod()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (getPrefixIcon() != null) {
            // 绘制前缀图标
            val start = 0
            val top = (height - mPrefixIconSize).roundToInt().shr(1)
            // When the inputted content is too long, getScrollX()/getScrollY() can fix the offset.
            canvas?.drawBitmap(getPrefixIcon()!!, start.toFloat(), (top + scrollY).toFloat(), null)
        }
        if (!mHasFocus) {
            return
        }
        if (mIsPwdInputType && mPwdIconVisible && getPwdIcon() != null) {
            // 绘制密码显示/隐藏图标
            val start = (width - mIconMarginEnd - mPwdIconSize).roundToInt()
            val top = (height - mClearIconSize).roundToInt().shr(1)
            // When the inputted content is too long, getScrollX()/getScrollY() can fix the offset.
            canvas?.drawBitmap(getPwdIcon()!!, (start + scrollX).toFloat(), (top + scrollY).toFloat(), null)
        }
        if (mClearIconVisible && getClearIcon() != null && text.toString().isNotEmpty()) {
            // 绘制清除图标
            val start: Int
            val top: Int
            if (mIsPwdInputType && mPwdIconVisible) {
                start = (width - mIconMarginEnd - mPwdIconSize - mClearIconSize - mIconPadding).roundToInt()
                top = (height - mClearIconSize).roundToInt().shr(1)
            } else {
                start = (width - mIconMarginEnd - mClearIconSize).roundToInt()
                top = (height - mClearIconSize).roundToInt().shr(1)
            }
            // When the inputted content is too long, getScrollX()/getScrollY() can fix the offset.
            canvas?.drawBitmap(getClearIcon()!!, (start + scrollX).toFloat(), (top + scrollY).toFloat(), null)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isEnabled && mHasFocus) {
            mGestureDetector.onTouchEvent(event)
        }
        return super.onTouchEvent(event)
    }

    private fun setPaddingEnd() {
        var paddingEnd = mIconMarginEnd
        if (mIsPwdInputType) {
            if (mPwdIconVisible) {
                paddingEnd += mIconPadding + mPwdIconSize
            }
            if (mClearIconVisible) {
                paddingEnd += mIconPadding + mClearIconSize
            }
        } else if (mClearIconVisible) {
            paddingEnd += mIconPadding + mClearIconSize
        }
        setPadding(paddingStart, paddingTop, paddingEnd.roundToInt(), paddingBottom)
    }

    private fun setIconVisibility() {
        invalidate()
    }

    /**
     * 设置密码显示/隐藏状态
     */
    private fun setPwdTransformationMethod() {
        if (mIsPwdInputType) {
            // textVisiblePassword
            mIsPwdShow = inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            if (mIsPwdShow) {
                transformationMethod = HideReturnsTransformationMethod.getInstance()
                letterSpacing = mTextLetterSpacing
            } else {
                transformationMethod = PasswordTransformationMethod.getInstance(char = mPwdMaskChar, forceChange = true)
                letterSpacing = mPwdMaskCharSpacing
            }
        } else {
            transformationMethod = HideReturnsTransformationMethod.getInstance()
            letterSpacing = mTextLetterSpacing
        }
        setIconVisibility()
    }

    /**
     * 切换密码显示/隐藏状态
     */
    private fun togglePwdTransformationMethod() {
        mIsPwdShow = !mIsPwdShow
        if (mIsPwdShow) {
            transformationMethod = HideReturnsTransformationMethod.getInstance()
            letterSpacing = mTextLetterSpacing
        } else {
            transformationMethod = PasswordTransformationMethod.getInstance(char = mPwdMaskChar, forceChange = true)
            letterSpacing = mPwdMaskCharSpacing
        }
        setSelection(text.toString().length)
        setIconVisibility()
    }

    /**
     * 清除输入内容
     */
    private fun clearText() {
        error = null
        text?.clear()
        mOnClear?.invoke()
    }

    /**
     * 获取着色的清除图标图片
     */
    private fun getClearIcon() = mClearIcon?.apply {
        DrawableCompat.wrap(this).mutate().also {
            DrawableCompat.setTintList(it, mClearIconTint)
        }
    }?.toBitmap(mClearIconSize.roundToInt(), mClearIconSize.roundToInt())

    /**
     * 获取着色的密码图标图片
     */
    private fun getPwdIcon() = if (mIsPwdShow) getShowPwdIcon() else getHidePwdIcon()

    /**
     * 获取着色的显示密码图标图片
     */
    private fun getShowPwdIcon() = mPwdShowIcon?.apply {
        DrawableCompat.wrap(this).mutate().also {
            DrawableCompat.setTintList(it, mPwdIconTint)
        }
    }?.toBitmap(mPwdIconSize.roundToInt(), mPwdIconSize.roundToInt())

    /**
     * 获取着色的隐藏密码图片
     */
    private fun getHidePwdIcon() = mPwdHideIcon?.apply {
        DrawableCompat.wrap(this).mutate().also {
            DrawableCompat.setTintList(it, mPwdIconTint)
        }
    }?.toBitmap(mPwdIconSize.roundToInt(), mPwdIconSize.roundToInt())

    /**
     * 获取着色的前缀图标图片
     */
    private fun getPrefixIcon() = mPrefixIcon?.apply {
        DrawableCompat.wrap(this).mutate().also {
            DrawableCompat.setTintList(it, if (mHasFocus) mPrefixIconFocusTint else mPrefixIconTint)
        }
    }?.toBitmap(mPrefixIconSize.roundToInt(), mPrefixIconSize.roundToInt())

    /**
     * @desc：单击监听
     * @date：2022/07/21 15:56
     */
    inner class OnSingleTapConfirmedListener : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
            if (mIsPwdInputType) {
                val eventX = event.x
                val eventY = event.y
                val halfPadding = mIconPadding.roundToInt().shr(1)
                var top = (height - mPwdIconSize).roundToInt().shr(1)
                var end = width - mIconMarginEnd.roundToInt()
                var inAreaX = eventX <= end + halfPadding && eventX >= end - mPwdIconSize - halfPadding
                var inAreaY = eventY >= top - halfPadding && eventY <= top + mPwdIconSize + halfPadding
                if (inAreaX && inAreaY) {
                    if (mPwdIconVisible) {
                        // 切换密码显示/隐藏状态
                        togglePwdTransformationMethod()
                    } else if (mClearIconVisible) {
                        // 清除输入内容
                        clearText()
                    }
                }
                if (mPwdIconVisible && mClearIconVisible) {
                    top = (height - mClearIconSize).roundToInt().shr(1)
                    end -= mPwdIconSize.roundToInt() + mIconPadding.roundToInt()
                    inAreaX = eventX <= end + halfPadding && eventX >= end - mClearIconSize - halfPadding
                    inAreaY = eventY >= top - halfPadding && eventY <= top + mClearIconSize + halfPadding
                    if (inAreaX && inAreaY) {
                        // 清除输入内容
                        clearText()
                    }
                }
            } else if (mClearIconVisible) {
                val eventX = event.x
                val eventY = event.y
                val halfPadding = mIconPadding.roundToInt().shr(1)
                val top = (height - mClearIconSize).roundToInt().shr(1)
                val end = width - mIconMarginEnd
                val inAreaX = eventX <= end + halfPadding && eventX >= end - mClearIconSize - halfPadding
                val inAreaY = eventY >= top - halfPadding && eventY <= top + mClearIconSize + halfPadding
                if (inAreaX && inAreaY) {
                    // 清除输入内容
                    clearText()
                }
            }
            return super.onSingleTapConfirmed(event)
        }
    }

    // ========================================public api start=========================================================
    /**
     * 设置图标外边距
     * @param marginEnd dp value
     */
    fun setIconMarginEnd(marginEnd: Float) = apply {
        mIconMarginEnd =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginEnd, Resources.getSystem().displayMetrics)
        setPaddingEnd()
        invalidate()
    }

    /**
     * 设置图标内边距
     * @param padding dp value
     */
    fun setIconPadding(padding: Float) = apply {
        mIconPadding =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, padding, Resources.getSystem().displayMetrics)
        setPaddingEnd()
        invalidate()
    }

    /**
     * 设置清除图标可见性
     */
    fun setClearIconVisible(visible: Boolean) = apply {
        if (mClearIconVisible != visible) {
            mClearIconVisible = visible
            setIconVisibility()
        }
    }

    /**
     * 设置清除图标
     */
    fun setClearIcon(d: Drawable) = apply {
        mClearIcon = d
        invalidate()
    }

    /**
     * 设置清除图标大小
     * @param size dp value
     */
    fun setClearIconSize(size: Float) = apply {
        mClearIconSize =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, Resources.getSystem().displayMetrics)
        setPaddingEnd()
        invalidate()
    }

    /**
     * 清除图标着色
     */
    fun setClearIconTint(@ColorInt tint: Int) = apply {
        mClearIconTint = ColorStateList.valueOf(tint)
        invalidate()
    }

    /**
     * 清除输入内容监听
     */
    fun setOnClear(onClear: () -> Unit) = apply {
        mOnClear = onClear
    }

    /**
     * 设置密码显示/隐藏图标可见性
     */
    fun setPwdIconVisible(visible: Boolean) = apply {
        if (mPwdIconVisible != visible) {
            mPwdIconVisible = visible
            setIconVisibility()
        }
    }

    /**
     * 设置密码显示图标
     */
    fun setPwdShowIcon(d: Drawable) = apply {
        mPwdShowIcon = d
        invalidate()
    }

    /**
     * 设置密码显示图标
     */
    fun setPwdHideIcon(d: Drawable) = apply {
        mPwdHideIcon = d
        invalidate()
    }

    /**
     * 设置密码显示/隐藏图标大小
     * @param size dp value
     */
    fun setPwdIconSize(size: Float) = apply {
        mPwdIconSize =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, Resources.getSystem().displayMetrics)
        setPaddingEnd()
        invalidate()
    }

    /**
     * 密码显示/隐藏图标着色
     */
    fun setPwdIconTint(@ColorInt tint: Int) = apply {
        mPwdIconTint = ColorStateList.valueOf(tint)
        invalidate()
    }

    /**
     * 密码掩码字符
     */
    fun setPwdMaskChar(c: Char) = apply {
        mPwdMaskChar = c
        setPwdTransformationMethod()
    }

    /**
     * 密码掩码字符间距
     * @param spacing 0f - 标准字间距，>0f - 放大字间距
     */
    fun setPwdMaskCharSpacing(spacing: Float) = apply {
        mPwdMaskCharSpacing = spacing
        setPwdTransformationMethod()
    }

    /**
     * 内容字符间距
     * @param spacing 0f - 标准字间距，>0f - 放大字间距
     */
    fun setTextLetterSpacing(spacing: Float) = apply {
        mTextLetterSpacing = spacing
        setPwdTransformationMethod()
    }

    /**
     * 焦点改变监听
     */
    fun setOnFocusChanged(onFocusChanged: (PasswordEditText, Boolean) -> Unit) = apply {
        mOnFocusChanged = onFocusChanged
    }

    /**
     * 设置前缀图标Drawable
     */
    fun setPrefixIcon(d: Drawable) = apply {
        mPrefixIcon = d
        invalidate()
    }

    /**
     * 设置前缀图标大小
     * @param size dp value
     */
    fun setPrefixIconSize(size: Float) = apply {
        mPrefixIconSize =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, Resources.getSystem().displayMetrics)
        invalidate()
    }

    /**
     * 前缀图标着色
     */
    fun setPrefixIconTint(@ColorInt tint: Int) = apply {
        mPrefixIconTint = ColorStateList.valueOf(tint)
        invalidate()
    }

    /**
     * 前缀图标着色
     */
    fun setPrefixIconFocusTint(@ColorInt tint: Int) = apply {
        mPrefixIconFocusTint = ColorStateList.valueOf(tint)
        invalidate()
    }

    // ========================================public api end===========================================================

    companion object {
        private val DEFAULT_ICON_MARGIN_END =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, Resources.getSystem().displayMetrics)
        private val DEFAULT_ICON_PADDING =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, Resources.getSystem().displayMetrics)
        private val DEFAULT_ICON_SIZE =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20f, Resources.getSystem().displayMetrics)
        private const val DOT = '\u2022'

        // 密码掩码字符间距
        private const val LETTER_SPACING_PASSWORD = 0.2f

        // 标准字符间距
        private const val LETTER_SPACING_STANDARD = 0f
    }
}