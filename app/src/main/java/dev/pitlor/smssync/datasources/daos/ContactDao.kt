package dev.pitlor.smssync.datasources.daos

import androidx.room.Dao
import androidx.room.Query
import dev.pitlor.smssync.datasources.Contact

@Dao
interface ContactDao: BaseDao<Contact> {
    @Query("SELECT * FROM contact")
    fun getAll(): List<Contact>

    @Query("SELECT * FROM contact WHERE phoneNumber = :number")
    fun getByNumber(number: String): Contact?
}