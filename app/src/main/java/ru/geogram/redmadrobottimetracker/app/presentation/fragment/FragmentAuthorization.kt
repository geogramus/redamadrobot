package ru.geogram.redmadrobottimetracker.app.presentation.fragment

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
import ru.geogram.domain.model.user.LoginPassword
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.presenter.FragmentAuthoriztionViewModel
import ru.geogram.redmadrobottimetracker.app.utils.getViewModel
import ru.geogram.redmadrobottimetracker.app.utils.viewModelFactory


class FragmentAuthorization : Fragment() {
    companion object {

    }

    init {

    }

    private lateinit var viewModel: FragmentAuthoriztionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val fragmentView = inflater.inflate(R.layout.fragment_authorization, container, false)


        val viewModelFactory = viewModelFactory { DI.user.get().userViewModel() }
        viewModel = getViewModel(viewModelFactory)
        val data = viewModel.getData()
        data.observe(this, Observer<String>() {

            val snack = SnackBar(activity!!, it, "asd", View.OnClickListener {

            })
            snack.show()

        })
        fragmentView.button.setOnClickListener {
            viewModel.auth(LoginPassword(fragmentView.editText2.text.toString(), fragmentView.editText.text.toString()))
        }
        return fragmentView

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}