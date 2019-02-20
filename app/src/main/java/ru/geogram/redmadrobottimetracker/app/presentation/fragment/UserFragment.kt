package ru.geogram.redmadrobottimetracker.app.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redmadrobot.lib.sd.LoadingStateDelegate
import kotlinx.android.synthetic.main.fragment_user_info.*
import ru.geogram.redmadrobottimetracker.app.R
import kotlinx.android.synthetic.main.fragment_user_info.view.*
import ru.geogram.domain.model.auth.UserInfo
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.presenter.*
import ru.geogram.redmadrobottimetracker.app.utils.getViewModel
import ru.geogram.redmadrobottimetracker.app.utils.observe
import ru.geogram.redmadrobottimetracker.app.utils.showSnackBar
import ru.geogram.redmadrobottimetracker.app.utils.viewModelFactory

class UserFragment : Fragment() {

    private val okString = "Ок"
    private lateinit var screenState: LoadingStateDelegate
    private lateinit var viewModel: UserFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        screenState = LoadingStateDelegate(fragment_user_content)
        val fragmentView = inflater.inflate(R.layout.fragment_user_info, container, false)
        val viewModelFactory = viewModelFactory { DI.user.get().userFragmentViewModel() }
        viewModel = getViewModel(viewModelFactory)
        observe(viewModel.userCheck, this::onUserChanged)

        return fragmentView
    }

    private fun onUserChanged(viewState: UserViewState) {
        when (viewState) {
            is Data -> {
                val data = viewState as Data
                data.user?.userInfo?.let {
                    with(it) {
                        view?.user_fragment_name?.text = first_name
                        view?.user_fragment_last_name?.text = last_name
                        view?.user_fragment_email?.text = email
                        is_staff?.let {
                            if (it.toBoolean()) {
                                view?.is_stuff_text_view?.text = getText(R.string.is_stuff)
                            } else {
                                view?.is_stuff_text_view?.text = getText(R.string.not_stuff)
                            }
                        }

                    }
                } ?: {
                    showSnackBar(context!!, getString(R.string.fragment_authorization_error), okString)
                }()
            }
            is Error -> {
                showSnackBar(context!!, getString(R.string.fragment_authorization_error), okString)
            }
        }
    }

}