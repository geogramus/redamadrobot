package ru.geogram.data.storage.db

import android.content.Context
import io.objectbox.BoxStore
import ru.geogram.data.model.db.user.MyObjectBox


object TimeTrackerBoxStore {

    var boxStore: BoxStore? = null

    @JvmStatic
    public fun getBoxStore(context: Context): BoxStore {
        if (boxStore == null) {
            boxStore = MyObjectBox.builder().androidContext(context).build()
        }
        return boxStore!!
    }

}