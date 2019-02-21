package ru.geogram.data.storage.db

import io.reactivex.Single
import ru.geogram.data.model.db.user.UserEntity

interface UserDatabaseInterface {
    fun putUser(user: UserEntity)
    fun getUsers(): Single<UserEntity>
    fun getUser():UserEntity
}