package ru.geogram.data.model.db.user

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class UserEntity(
    @Id
    var idForDb: Long = 0,
    var first_name: String? = "",
    var role: String? = "",
    var last_name: String? = "",
    var email: String? = "",
    var id: Int? = 0,
    var stuff: Boolean? = false
)
