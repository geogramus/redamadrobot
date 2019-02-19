package ru.geogram.data.storage.db

import android.content.Context
import io.objectbox.Box
import io.reactivex.Single
import ru.geogram.data.model.db.user.UserEntity

class UserDatabase(context: Context) : UserDatabaseInterface {
    override fun putUser(user: UserEntity) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUsers(): Single<UserEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
//    val userEntitylBox: Box<UserEntity> by lazy{
//        TimeTrackerBoxStore.getBoxStore(context).boxFor<UserEntity>(UserEntity::class.java)
//    }
//
//    override fun putUser(user: UserEntity) {
//        userEntitylBox.put(user)
//    }
//
//    override fun getUsers(): Single<UserEntity> {
//        val single = Single.fromCallable { userEntitylBox.all.get(0) }
//        return single
//    }
}