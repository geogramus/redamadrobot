package ru.geogram.redmadrobottimetracker.app.presentation.fragments

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment: Fragment() {
    private lateinit var disposables: CompositeDisposable

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        disposables = CompositeDisposable()
    }

    fun Disposable.disposeOnDetach() {
        disposables.add(this)
    }

    override fun onDetach() {
        super.onDetach()

        if (!disposables.isDisposed) {
            disposables.dispose()
            disposables.clear()
        }
    }
}