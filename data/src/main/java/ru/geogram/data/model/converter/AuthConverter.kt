package ru.geogram.data.model.converter


import ru.geogram.data.model.LoginModel
import ru.geogram.data.model.LoginResponseModel
import ru.geogram.data.model.db.user.UserEntity
import ru.geogram.domain.model.auth.AuthInfo
import ru.geogram.domain.model.auth.ErrorInfo
import ru.geogram.domain.model.auth.LoginPassword
import ru.geogram.domain.model.auth.UserInfo

object AuthConverter {

    fun convertToLoginModel(source: LoginPassword): LoginModel {
        return LoginModel(
            source.login,
            source.password
        )
    }

    fun fromNetwork(loginResponse: LoginResponseModel): AuthInfo {
        val user = loginResponse.data?.user
        val error = loginResponse.error
        return AuthInfo(
            user?.let {
                UserInfo(
                    id = it.id,
                    first_name = it.first_name,
                    last_name = it.last_name,
                    role = it.role,
                    email = it.email,
                    is_staff = it.is_staff
                )
            },
            error?.let {
                ErrorInfo(it.code, error.description)
            }
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