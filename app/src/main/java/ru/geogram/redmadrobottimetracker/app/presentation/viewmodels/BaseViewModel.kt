package ru.geogram.redmadrobottimetracker.app.presentation.viewmodels

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    protected fun safeSubscribe(block: () -> Disposable) {
        compositeDisposable.add(block.invoke())
    }
}