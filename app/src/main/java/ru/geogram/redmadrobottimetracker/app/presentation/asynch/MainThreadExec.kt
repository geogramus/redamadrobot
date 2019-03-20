package ru.geogram.redmadrobottimetracker.app.presentation.asynch

import android.os.Handler
import android.os.Looper
import androidx.annotation.NonNull
import java.util.concurrent.Executor


class MainThreadExec : Executor {

    private val handler = Handler(Looper.getMainLooper())

    override fun execute(@NonNull runnable: Runnable) {
        handler.post(runnable)
    }
}