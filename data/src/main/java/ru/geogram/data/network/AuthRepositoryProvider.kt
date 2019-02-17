package ru.geogram.data.network

object AuthRepositoryProvider {
    fun provideAuthRepository(): AuthRepository{
        return AuthRepository(AuthService.create())
    }
}