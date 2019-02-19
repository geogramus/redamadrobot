package ru.geogram.data.model.converter


import ru.geogram.data.model.LoginModel
import ru.geogram.data.model.LoginResponseModel
import ru.geogram.data.model.db.user.UserEntity
import ru.geogram.data.model.network.user.UserResponse
import ru.geogram.domain.model.user.LoginPassword
import ru.geogram.domain.model.user.UserInfo

object UserConverter {

    fun convertToLoginModel(source: LoginPassword): LoginModel {
        return LoginModel(
            source.login,
            source.password
        )
    }

    fun fromNetwork(loginResponse: LoginResponseModel): UserInfo {
        val source = loginResponse.data?.user
        return UserInfo(
            id = source?.id!!,
            first_name = source.first_name,
            last_name = source.last_name,
            role = source.role,
            email = source.email,
            is_staff = source.is_staff
        )
    }

    fun toDatabase(source: UserInfo): UserEntity {
        return UserEntity(
            first_name = source.first_name,
            role = source.role,
            last_name = source.last_name,
            email = source.email,
            id = source.id,
            is_staff = source.is_staff
        )
    }

    fun fromDatabase(source: UserEntity): UserInfo {
        return UserInfo(
            first_name = source.first_name,
            role = source.role,
            last_name = source.last_name,
            email = source.email,
            id = source.id,
            is_staff = source.is_staff
        )
    }

}