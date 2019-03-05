package ru.geogram.redmadrobottimetracker.app.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.gc.materialdesign.widgets.SnackBar
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.geogram.domain.model.days.WeekDates
import ru.geogram.redmadrobottimetracker.app.R
import java.util.*
import java.text.SimpleDateFormat

fun Fragment.showSnackBar(activity: Context, text: String, buttonText: String) {
    val snack = SnackBar((activity as Activity?), text, buttonText, View.OnClickListener {
    })
    snack.show()
}

fun View.Visible() {
    visibility = View.VISIBLE
}

fun View.InVisible() {
    visibility = View.INVISIBLE
}

fun View.Gone() {
    visibility = View.GONE
}

fun Button.isEnable(isEnable: Boolean) {
    background = if (isEnable) context.getDrawable(R.drawable.buttong_bg_red_rounded_enabled)
    else context.getDrawable(R.drawable.buttong_bg_red_rounded_disabled)
    isClickable = isEnable
    isEnabled = isEnable
}
