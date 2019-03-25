package ru.geogram.data.model.converter


import ru.geogram.data.model.network.user.LoginModel
import ru.geogram.data.model.network.user.LoginResponseModel
import ru.geogram.data.model.network.user.RegistrationModel
import ru.geogram.domain.model.auth.*

object AuthConverter {

    fun convertToLoginModel(source: LoginPassword): LoginModel {
        return LoginModel(
                source.login,
                source.password
        )
    }

    fun convertToRegistrationModel(registraionInfo: RegistraionInfo): RegistrationModel {
        return RegistrationModel(
                registraionInfo.firstName,
                registraionInfo.password,
                registraionInfo.lastName,
                registraionInfo.email
        )
    }

    fun fromNetwork(loginResponse: LoginResponseModel): AuthInfo {
        val user = loginResponse.data?.user
        val error = loginResponse.error
        val authInfo = user?.let {
            UserInfo(
                    id = it.id,
                    first_name = it.first_name,
                    last_name = it.last_name,
                    role = it.role,
                    email = it.email,
                    is_staff = it.is_staff
            )
        }
        val errorInfo = error?.let {
            ErrorInfo(it.code, error.description)
        }
        return AuthInfo(authInfo, errorInfo)
    }

}