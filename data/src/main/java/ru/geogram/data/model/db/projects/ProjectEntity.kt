package ru.geogram.data.model.db.projects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.geogram.data.storage.db.Tables

@Entity(tableName = Tables.PROJECTS)
data class ProjectEntity constructor(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String

)