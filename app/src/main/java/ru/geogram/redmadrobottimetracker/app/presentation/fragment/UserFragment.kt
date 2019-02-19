package ru.geogram.redmadrobottimetracker.app.presentation.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.geogram.redmadrobottimetracker.app.R
import com.gc.materialdesign.widgets.SnackBar
import kotlinx.android.synthetic.main.fragment_authorization.view.*
import kotlinx.android.synthetic.main.fragment_user_info.view.*
import ru.geogram.domain.model.auth.LoginPassword
import ru.geogram.domain.model.auth.UserInfo
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.presenter.AuthoriztionViewModel
import ru.geogram.redmadrobottimetracker.app.utils.getViewModel
import ru.geogram.redmadrobottimetracker.app.utils.viewModelFactory

@SuppressLint("ValidFragment")
class UserFragment constructor(val userInfo: UserInfo?) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val fragmentView = inflater.inflate(R.layout.fragment_user_info, container, false)
        userInfo?.let {
            with(it) {
                fragmentView.name.text = first_name
                fragmentView.last_name.text = last_name
                fragmentView.email.text = email
                is_staff?.let {
                    if (it.toBoolean()){
                        fragmentView.is_stuff_text_view.text = getText(R.string.is_stuff)
                    }else{
                        fragmentView.is_stuff_text_view.text = getText(R.string.not_stuff)
                    }
                }

            }
        }
        return fragmentView
    }

}