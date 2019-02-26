package ru.geogram.redmadrobottimetracker.app.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.geogram.redmadrobottimetracker.app.presentation.fragments.DateItemFragment

class ViewPagerMonthAdapter(val fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager){

    override fun getItem(position: Int): Fragment {
        val weekPosition = position - getDefaultPosition()
        return DateItemFragment(weekPosition)
    }

    override fun getCount(): Int {
        return 20000
    }

    fun getDefaultPosition(): Int {
        return count / 2
    }
}