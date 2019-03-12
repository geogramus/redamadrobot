package ru.geogram.data.dao.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.geogram.data.model.db.projects.Project

class ProjectsListConverter {
    companion object {

        @JvmStatic
        @TypeConverter
        fun toProjects(list: ArrayList<Project>?): String? {
            return Gson().toJson(list)
        }

        @JvmStatic
        @TypeConverter
        fun fromProjects(verifyOptions: String?): ArrayList<Project>? {
            return Gson().fromJson<ArrayList<Project>>(verifyOptions, object : TypeToken<ArrayList<Project>>() {}.type)
        }
    }
}