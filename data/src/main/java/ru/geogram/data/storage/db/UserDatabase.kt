package ru.geogram.data.storage.db

import android.content.Context
import io.objectbox.Box
import io.reactivex.Single
import ru.geogram.data.model.db.user.UserEntity
import javax.inject.Inject

class UserDatabase @Inject constructor(val context: Context) : UserDatabaseInterface {
    override fun getUser(): UserEntity {
        return  userEntitylBox.all.first()
    }

    val userEntitylBox: Box<UserEntity> by lazy{
        TimeTrackerBoxStore.getBoxStore(context).boxFor<UserEntity>(UserEntity::class.java)
    }

    override fun putUser(user: UserEntity) {
        userEntitylBox.removeAll()
        userEntitylBox.put(user)
    }

    override fun getUsers(): Single<UserEntity> {
        val single = Single.fromCallable { userEntitylBox.all.first() }
        return single
    }
}