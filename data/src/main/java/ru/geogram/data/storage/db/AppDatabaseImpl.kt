package ru.geogram.data.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.geogram.data.dao.projects.ProjectsEntityDao
import ru.geogram.data.model.db.projects.ProjectEntity

@Database(entities = [
    ProjectEntity::class
], version = 1)
abstract class AppDatabaseImpl  : RoomDatabase(), AppDatabase {

    companion object {
        const val DATABASE_NAME = "data"
    }

    abstract override fun projectsEntityDao(): ProjectsEntityDao
}