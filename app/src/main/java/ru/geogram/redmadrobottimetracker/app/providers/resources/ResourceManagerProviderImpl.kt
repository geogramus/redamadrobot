package ru.geogram.redmadrobottimetracker.app.providers.resources

import android.content.Context
import ru.geogram.domain.providers.resources.ResourceManagerProvider
import javax.inject.Inject

class ResourceManagerProviderImpl @Inject constructor(private val context: Context) : ResourceManagerProvider {
    override fun getString(id: Int): String {
        return context.getString(id)
    }
}