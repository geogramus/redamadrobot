package ru.geogram.data.dao.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.geogram.data.model.db.projects.Project

class ProjectsListConverter {
    companion object {

        @JvmStatic
        @TypeConverter
        fun toProjects(list: List<Project>?): String? {
            return Gson().toJson(list)
        }

        @JvmStatic
        @TypeConverter
        fun fromProjects(verifyOptions: String?): List<Project>? {
            return Gson().fromJson<List<Project>>(verifyOptions, object : TypeToken<List<Project>>() {}.type)
        }
    }
}