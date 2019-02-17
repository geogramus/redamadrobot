package ru.geogram.entity.entity

data class User(
    var first_name: String,
    var role: String,
    var last_name: String,
    var email: String,
    var id : Int
//    var is_staff: String
)