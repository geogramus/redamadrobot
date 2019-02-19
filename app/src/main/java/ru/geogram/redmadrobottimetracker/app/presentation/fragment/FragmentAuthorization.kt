package ru.geogram.redmadrobottimetracker.app.presentation.fragment

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.geogram.redmadrobottimetracker.app.R
import com.gc.materialdesign.widgets.SnackBar
import kotlinx.android.synthetic.main.fragment_authorization.view.*
import ru.geogram.domain.model.auth.LoginPassword
import ru.geogram.domain.model.auth.UserInfo
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.presenter.AuthoriztionViewModel
import ru.geogram.redmadrobottimetracker.app.utils.getViewModel
import ru.geogram.redmadrobottimetracker.app.utils.viewModelFactory


class FragmentAuthorization : Fragment() {


    private lateinit var viewModel: AuthoriztionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val fragmentView = inflater.inflate(R.layout.fragment_authorization, container, false)
        val viewModelFactory = viewModelFactory { DI.user.get().userViewModel() }
        viewModel = getViewModel(viewModelFactory)
        val error = viewModel.getError()
        error.observe(this, Observer<String>() {
            val snack = SnackBar((activity as Activity?), it, "Ок", View.OnClickListener {
            })
            snack.show()
        })
        viewModel = getViewModel(viewModelFactory)
        viewModel.getData().observe(this, Observer<UserInfo>() {
            it?.let {
                fragmentAuthorization.showUserFragment(it)
            }
        })
        fragmentView.button.setOnClickListener {
            viewModel.auth(LoginPassword(fragmentView.email_edit_text.text.toString(), fragmentView.editText.text.toString()))
        }
        return fragmentView
    }

    lateinit var fragmentAuthorization: FragmentAuthorizationInterface

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (activity is FragmentAuthorizationInterface) {
            fragmentAuthorization = context as FragmentAuthorizationInterface
        } else {
            throw UnsupportedOperationException("Activity must implement FragmentAuthorizationInterface")
        }
    }

    interface FragmentAuthorizationInterface {
        fun showUserFragment(userInfo: UserInfo)
    }

}