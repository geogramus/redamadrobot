package ru.geogram.data.model.db.projects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.geogram.data.dao.converters.ProjectsListConverter
import ru.geogram.data.storage.db.Tables

@Entity(tableName = Tables.PROJECTS)
data class ProjectsEntity constructor(
    @PrimaryKey
    @ColumnInfo(name = "insert_date")
    val date: String,

    @ColumnInfo(name = "projects")
    @TypeConverters(ProjectsListConverter::class)
    val projects: List<Project>

)