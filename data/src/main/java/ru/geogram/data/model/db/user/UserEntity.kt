package ru.geogram.data.model.db.user

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

data class UserEntity(
    var idForDb: Long = 0,
    var first_name: String? = "",
    var role: String? = "",
    var last_name: String? = "",
    var email: String? = "",
    var id: Int,
    var is_staff: String? = ""
)
