package ru.geogram.data.storage.db

import ru.geogram.data.dao.projects.ProjectsEntityDao

interface AppDatabase {
    fun projectsEntityDao(): ProjectsEntityDao
}