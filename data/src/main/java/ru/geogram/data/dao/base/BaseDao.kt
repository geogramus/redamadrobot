package ru.geogram.data.dao.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<in T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(someObject: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg someObject: T)

    @Update
    fun update(someObject: T)

    @Delete
    fun delete(someObject: T)
}