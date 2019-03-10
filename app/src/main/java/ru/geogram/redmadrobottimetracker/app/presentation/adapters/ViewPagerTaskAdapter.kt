package ru.geogram.redmadrobottimetracker.app.presentation.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.gson.Gson
import ru.geogram.domain.model.days.SingleDayInfo
import ru.geogram.redmadrobottimetracker.app.presentation.fragments.DaysTasksFragment


class ViewPagerTaskAdapter(val fragmentManager: FragmentManager, val daysList: ArrayList<SingleDayInfo>) :
    FragmentStatePagerAdapter(fragmentManager) {


    override fun getItem(position: Int): Fragment {
        val args = Bundle()
        val singleDayInfo = Gson().toJson(daysList.get(position))
        args.putString(DaysTasksFragment.DAY_TASKS, singleDayInfo)
        val dayTaskFragment = DaysTasksFragment.getInstance()
        dayTaskFragment.arguments = args
        return dayTaskFragment
    }

    override fun getCount(): Int {
        return daysList.size
    }

    fun addDays(days: ArrayList<SingleDayInfo>) {
        daysList.clear()
        daysList.addAll(days)
        notifyDataSetChanged()
    }
}