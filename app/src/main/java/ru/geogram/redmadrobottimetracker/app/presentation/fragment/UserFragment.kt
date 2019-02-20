package ru.geogram.redmadrobottimetracker.app.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.geogram.redmadrobottimetracker.app.R
import kotlinx.android.synthetic.main.fragment_user_info.view.*
import ru.geogram.domain.model.auth.UserInfo

@SuppressLint("ValidFragment")
class UserFragment constructor(val userInfo: UserInfo?) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val fragmentView = inflater.inflate(R.layout.fragment_user_info, container, false)
        userInfo?.let {
            with(it) {
                fragmentView.user_fragment_name.text = first_name
                fragmentView.user_fragment_last_name.text = last_name
                fragmentView.user_fragment_email.text = email
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