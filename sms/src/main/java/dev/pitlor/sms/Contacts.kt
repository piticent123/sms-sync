package dev.pitlor.sms

import dev.pitlor.sms.repositories.ContactRepository
import javax.inject.Inject

class Contacts @Inject constructor(private val contactRepository: ContactRepository) {
    fun readAll(limit: Int): List<Contact> {
        return listOf()
    }
}