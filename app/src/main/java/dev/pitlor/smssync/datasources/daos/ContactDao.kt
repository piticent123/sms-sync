package dev.pitlor.smssync.datasources.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import dev.pitlor.smssync.datasources.Contact

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact")
    fun getAll(): LiveData<List<Contact>>

    @Query("SELECT * FROM contact WHERE phoneNumbers LIKE :number")
    fun getByNumber(number: String): Contact?

    @Insert
    suspend fun insert(contact: Contact)

    @Update
    suspend fun update(contact: Contact)
}