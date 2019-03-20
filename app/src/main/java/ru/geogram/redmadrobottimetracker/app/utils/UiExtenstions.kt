package ru.geogram.redmadrobottimetracker.app.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.gc.materialdesign.widgets.SnackBar
import ru.geogram.redmadrobottimetracker.app.R


fun Fragment.showSnackBar(activity: Context, text: String, buttonText: String) {
    val snack = SnackBar((activity as Activity?), text, buttonText, View.OnClickListener {
    })
    snack.show()
}

fun Fragment.showThreatAlertDialog(context: Context, message: String? = null,
                                   messages: List<String>? = null) {
    var threatMessage = ""
    message?.let {
        threatMessage += it
    }
    messages?.let {
        it.forEach {
            threatMessage += "${it}\n"
        }
    }
    threatMessage += getString(R.string.problems_warning)
    val builder = AlertDialog.Builder(context)
    builder.setTitle(getString(R.string.threats_founded))
    builder.setMessage(threatMessage)
    builder.setPositiveButton(getString(R.string.ok_string), null)
    builder.show()
}

fun View.isVisible(isVisible:Boolean){
    visibility = if(isVisible) View.VISIBLE else View.INVISIBLE
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