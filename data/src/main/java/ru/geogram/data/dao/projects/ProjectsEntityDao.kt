package ru.geogram.data.dao.projects

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single
import ru.geogram.data.dao.base.BaseDao
import ru.geogram.data.model.db.projects.ProjectsEntity
import ru.geogram.data.storage.db.Tables

@Dao
abstract class ProjectsEntityDao : BaseDao<ProjectsEntity> {
    @Query("SELECT * FROM ${Tables.PROJECTS}")
    abstract fun getProjects(): Single<ProjectsEntity>

    @Query("DELETE FROM ${Tables.PROJECTS}")
    abstract fun deleteAll()
}