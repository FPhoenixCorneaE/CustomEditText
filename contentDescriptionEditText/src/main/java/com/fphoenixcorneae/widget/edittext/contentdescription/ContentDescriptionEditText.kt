package com.fphoenixcorneae.widget.edittext.contentdescription

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatEditText
import kotlin.math.roundToInt

/**
 * @desc：ContentDescriptionEditText
 * @date：2022/07/25 11:23
 */
class ContentDescriptionEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle,
) : AppCompatEditText(context, attrs, defStyleAttr) {

    /** 内容描述画笔 */
    private val mContentDescriptionPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG).apply { isAntiAlias = true } }

    /** 内容描述开始外边距 */
    private var mContentDescriptionMarginStart = DEFAULT_CONTENT_DESCRIPTION_MARGIN

    /** 内容描述结束外边距 */
    private var mContentDescriptionMarginEnd = DEFAULT_CONTENT_DESCRIPTION_MARGIN

    /** 内容描述文字大小 */
    private var mContentDescriptionTextSize = textSize

    /** 内容描述文字颜色 */
    private var mContentDescriptionTextColor = currentTextColor

    /** 内容描述文字是否粗体 */
    private var mIsContentDescriptionTextFakeBold = false

    init {
        initContentDescriptionPaint()
        setPaddingStart()
    }

    private fun initContentDescriptionPaint() {
        mContentDescriptionPaint.textSize = mContentDescriptionTextSize
        mContentDescriptionPaint.color = mContentDescriptionTextColor
        mContentDescriptionPaint.isFakeBoldText = mIsContentDescriptionTextFakeBold
    }

    private fun setPaddingStart() {
        val paddingStart =
            (mContentDescriptionMarginStart + getTextWidth(contentDescription) + mContentDescriptionMarginEnd).roundToInt()
        setPadding(paddingStart, paddingTop, paddingEnd, paddingBottom)
    }

    private fun getTextWidth(text: CharSequence) = run {
        mContentDescriptionPaint.measureText(text, 0, text.length)
    }

    private fun getTextHeight() = run {
        val fontMetrics = mContentDescriptionPaint.fontMetrics
        fontMetrics.bottom - fontMetrics.top
    }

    private fun getTextBaselineY() = run {
        height.shr(1) + getTextHeight().roundToInt().shr(1) - mContentDescriptionPaint.descent()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (contentDescription.isNotBlank()) {
            // 绘制内容描述文字, 文字垂直居中显示
            val startX = mContentDescriptionMarginStart
            val startY = getTextBaselineY()
            // When the inputted content is too long, getScrollX()/getScrollY() can fix the offset.
            canvas?.drawText(contentDescription.toString(), startX, startY + scrollY, mContentDescriptionPaint)
        }
    }

    /**
     * 无障碍描述
     */
    override fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo?) {
        super.onInitializeAccessibilityNodeInfo(info)
        // 对于EditText，系统无障碍朗读只读hint，需通过节点info覆盖自定义内容
        info?.text = contentDescription.toString() + hint
    }

    /**
     * 内容描述开始外边距
     * @param marginStart dp value
     */
    fun setContentDescriptionMarginStart(marginStart: Float) = apply {
        mContentDescriptionMarginStart =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginStart, Resources.getSystem().displayMetrics)
        setPaddingStart()
        invalidate()
    }

    /**
     * 内容描述结束外边距
     * @param marginEnd dp value
     */
    fun setContentDescriptionMarginEnd(marginEnd: Float) = apply {
        mContentDescriptionMarginEnd =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginEnd, Resources.getSystem().displayMetrics)
        setPaddingStart()
    }

    /**
     * 内容描述文字大小
     * @param textSize sp value
     */
    fun setContentDescriptionTextSize(textSize: Float) = apply {
        mContentDescriptionTextSize =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSize, Resources.getSystem().displayMetrics)
        initContentDescriptionPaint()
        setPaddingStart()
        invalidate()
    }

    /**
     * 内容描述文字颜色
     * @param textColor [ColorInt] value
     */
    fun setContentDescriptionTextColor(@ColorInt textColor: Int) = apply {
        mContentDescriptionTextColor = textColor
        initContentDescriptionPaint()
        invalidate()
    }

    /**
     * 内容描述文字是否粗体
     */
    fun setContentDescriptionTextFakeBold(isFakeBold: Boolean) = apply {
        mIsContentDescriptionTextFakeBold = isFakeBold
        initContentDescriptionPaint()
        invalidate()
    }

    companion object {
        private val DEFAULT_CONTENT_DESCRIPTION_MARGIN =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, Resources.getSystem().displayMetrics)
    }
}