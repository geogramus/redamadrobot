package ru.geogram.redmadrobottimetracker.app.presentation.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.geogram.redmadrobottimetracker.app.presentation.fragments.DateItemFragment

class ViewPagerWeekAdapter(val fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val middleDelimeter = 2

    override fun getItem(position: Int): Fragment {
        val args = Bundle()
        val weekPosition = position - getDefaultPosition()
        args.putInt(DateItemFragment.DATE_POSITION, weekPosition)
        val dateItemFragment = DateItemFragment.getInstance()
        dateItemFragment.arguments = args
        return dateItemFragment
    }

    override fun getCount(): Int {
        return Int.MAX_VALUE
    }

    fun getDefaultPosition(): Int {
        return count / middleDelimeter
    }

}