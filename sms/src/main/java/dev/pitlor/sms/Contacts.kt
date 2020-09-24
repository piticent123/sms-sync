package dev.pitlor.sms

import dev.pitlor.sms.repositories.ContactRepository
import javax.inject.Inject

class Contacts @Inject constructor(private val contactRepository: ContactRepository) {
    suspend fun readAll(numbers: List<String>, reportProgress: suspend (String) -> Unit): List<Contact> {
        return numbers.map {
            reportProgress("Getting contact $it")
            return@map contactRepository.getContact(it)
        }
    }
}