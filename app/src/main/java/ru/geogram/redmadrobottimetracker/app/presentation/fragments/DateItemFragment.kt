package ru.geogram.redmadrobottimetracker.app.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.date_item.*
import kotlinx.android.synthetic.main.date_item.view.*
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.utils.Utils

class DateItemFragment : Fragment() {

    companion object {
        const val DATE_POSITION = "date_position"
        fun getInstance(): DateItemFragment = DateItemFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.date_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt(DATE_POSITION)?.let {
            val date = Utils.getDateAndMonth(it)
            val endMonth = getString(date.finishMonth)
            var startMonth = ""
            date.startMonth?.let {
                startMonth = getString(it)
            }
            val dateString = "${date.startDate} ${startMonth} - ${date.finishDate} ${endMonth}"
            date_item_tv.text = dateString
        }
    }
}