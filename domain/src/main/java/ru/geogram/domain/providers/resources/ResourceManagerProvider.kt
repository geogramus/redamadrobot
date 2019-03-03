package ru.geogram.domain.providers.resources

import android.content.Context

interface ResourceManagerProvider {
    fun getString(id: Int): String
    fun setToken(preferencesValue: String)
    fun getToken(): String
}