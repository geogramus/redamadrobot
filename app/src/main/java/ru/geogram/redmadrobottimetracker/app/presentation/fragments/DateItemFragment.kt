package ru.geogram.redmadrobottimetracker.app.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.date_item.view.*
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.utils.Utils

@SuppressLint("ValidFragment")
class DateItemFragment(val position: Int) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val fragmentView = inflater.inflate(R.layout.date_item, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val date = Utils.getDateAndMonth(position)
        date.startMonth?.let {
            val startMonth = getString(it)
            val endMonth = getString(date.finishMonth)
            val dateString = "${date.startDate} ${startMonth} - ${date.finishDate} ${endMonth}"
            view.date_item_tv.text = dateString
        }?:{
            val endMonth = getString(date.finishMonth)
            val dateString = "${date.startDate} - ${date.finishDate} ${endMonth}"
            view.date_item_tv.text = dateString
        }()

    }
}