package com.hsbc.hsbcdemo.ui.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.AdaptScreenUtils
import com.hsbc.hsbcdemo.R

/**
 * 自定义组件，自带文本是否为空监测，并根据情况显示清除图标
 */
class ClearInputEditText : AppCompatEditText,
View.OnTouchListener, View.OnFocusChangeListener, TextWatcher {

    var count = 0
    var drawableClear: Drawable? = null
    var onFocusChangeListeners: MutableList<OnFocusChangeListener>? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {

        drawableClear = ContextCompat.getDrawable(context, R.drawable.cie_ic_clear)
        drawableClear?.setBounds(
            0, 0, AdaptScreenUtils.pt2Px(20f),
            AdaptScreenUtils.pt2Px(20f)
        )
        addTextChangedListener(this)
        setOnFocusChangeListener(this)
        setOnTouchListener(this)
        setSingleLine()
    }

     constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet,defStyle) {

    }

    override fun setText(text: CharSequence, type: BufferType?) {
        super.setText(text, type)
        setClearIconVisible(isFocused())
        if (this.count > 1 && getSelectionEnd() <= getText()!!.length) {
            setSelection(text.length)
        }
    }

    fun getTrimString(): String? {
        return getText().toString().trim { it <= ' ' }
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP &&
            getCompoundDrawablesRelative().get(2) != null
        ) if (event.x > getWidth() - getTotalPaddingEnd() &&
            event.x < getWidth() - getPaddingEnd()
        ) {
            setText("")
            setClearIconVisible(false)
        }
        return super.onTouchEvent(event)
    }


    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (hasFocus) {
            setClearIconVisible(!TextUtils.isEmpty(getText()))
        } else {
            setClearIconVisible(false)
        }
        if (onFocusChangeListeners != null) {
            for (listener in onFocusChangeListeners!!) {
                listener.onFocusChange(v, hasFocus)
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        setClearIconVisible(!TextUtils.isEmpty(getText()))
        this.count = count
    }

    override fun afterTextChanged(s: Editable?) {}

    open fun setClearIconVisible(visible: Boolean) {
        val temp = if (visible) drawableClear else null
        val drawables: Array<Drawable> = getCompoundDrawablesRelative()
        setCompoundDrawablesRelative(drawables[0], drawables[1], temp, drawables[3])
    }

    fun addOnFocusChangeListener(listener: OnFocusChangeListener) {
        if (onFocusChangeListeners == null) {
            onFocusChangeListeners = ArrayList()
        }
        onFocusChangeListeners!!.add(listener)
    }

    override fun clearFocus() {
        super.clearFocus()
        setClearIconVisible(false)
    }

}