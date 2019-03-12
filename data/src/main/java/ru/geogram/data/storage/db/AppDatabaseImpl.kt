package ru.geogram.data.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.geogram.data.dao.converters.ProjectsListConverter
import ru.geogram.data.dao.projects.ProjectsEntityDao
import ru.geogram.data.model.db.projects.ProjectsEntity

@Database(entities = [
    ProjectsEntity::class
], version = 1)
@TypeConverters(ProjectsListConverter::class)
abstract class AppDatabaseImpl  : RoomDatabase(), AppDatabase {

    companion object {
        const val DATABASE_NAME = "data"
    }

    abstract override fun projectsEntityDao(): ProjectsEntityDao
}