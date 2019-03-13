package ru.geogram.data.delegate

import android.content.Context
import androidx.room.Room
import ru.geogram.data.storage.db.AppDatabase
import ru.geogram.data.storage.db.AppDatabaseImpl

fun provideDataAppDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context, AppDatabaseImpl::class.java,
        AppDatabaseImpl.DATABASE_NAME
    ).build()
}