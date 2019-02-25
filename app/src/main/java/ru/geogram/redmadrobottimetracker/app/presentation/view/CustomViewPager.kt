package ru.geogram.redmadrobottimetracker.app.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class CustomViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {


    internal var mStartDragX: Float = 0.toFloat()
    internal lateinit var mListener: OnSwipeOutListener


    fun setOnSwipeOutListener(listener: OnSwipeOutListener) {
        mListener = listener
    }

    override fun onPageScrolled(position: Int, offset: Float, offsetPixels: Int) {
        super.onPageScrolled(position, offset, offsetPixels)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val x = ev.x
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> mStartDragX = x
            MotionEvent.ACTION_MOVE -> if (mStartDragX < x && currentItem == 0) {
                mListener.swipeRight()
            } else if (mStartDragX > x && currentItem == adapter!!.count - 1) {
                mListener.swipeLeft()
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    interface OnSwipeOutListener {
        fun swipeRight()
        fun swipeLeft()
    }

}