package ru.geogram.redmadrobottimetracker.app.presentation.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.presentation.fragment.FragmentAuthorization

class MainActivity : AppCompatActivity() {

    companion object {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showFragment(FragmentAuthorization())
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            if (!fragment.isAdded) {
                add(R.id.mainContainer, fragment)
            } else {
                replace(R.id.mainContainer, fragment)
            }
            addToBackStack(null)
            commit()
        }
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar_grey)
    }
}
