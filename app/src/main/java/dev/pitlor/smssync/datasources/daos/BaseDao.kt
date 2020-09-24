package dev.pitlor.smssync.datasources.daos

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface BaseDao<T> {
    @Insert
    suspend fun insert(t: T): Long

    @Insert
    suspend fun insert(vararg t: T): List<Long>

    @Update
    suspend fun update(t: T)

    @Update
    suspend fun update(vararg t: T)

    @Delete
    suspend fun delete(t: T)

    @Delete
    suspend fun delete(vararg t: T)
}