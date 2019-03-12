package ru.geogram.data.dao.projects

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single
import ru.geogram.data.dao.base.BaseDao
import ru.geogram.data.model.db.projects.ProjectEntity
import ru.geogram.data.storage.db.Tables

@Dao
abstract class ProjectsEntityDao : BaseDao<ProjectEntity> {
    @Query("SELECT * FROM ${Tables.PROJECTS}")
    abstract fun getProjects(): Single<ArrayList<ProjectEntity>>
}