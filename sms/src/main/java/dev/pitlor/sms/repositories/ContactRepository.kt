package dev.pitlor.sms.repositories

import android.content.Context
import android.provider.ContactsContract
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.pitlor.sms.*
import javax.inject.Inject

class ContactRepository @Inject constructor(@ApplicationContext private val context: Context) {
    private val contentResolver = context.contentResolver!!

    fun getAllLookupPairs(): List<ContactLookupDTO> {
        val result = mutableListOf<ContactLookupDTO>()
        contentResolver.queryLoop(
            ContactsContract.Contacts.CONTENT_URI,
            arrayOf(ContactsContract.Contacts._ID, ContactsContract.Contacts.LOOKUP_KEY)
        ) {
            result.add(ContactLookupDTO(
                id = getLong(ContactsContract.Contacts._ID),
                key = getString(ContactsContract.Contacts.LOOKUP_KEY)
            ))
        }

        return result
    }

    fun getContact(lookupPair: ContactLookupDTO): Contact {
        val projection = arrayOf(
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.PHOTO_URI
        )

        val p2 = arrayOf(
            ContactsContract.Data.
        )
        return Contact().apply {
            contentResolver.queryOnce(
                ContactsContract.Contacts.getLookupUri(lookupPair.id, lookupPair.key)
            ) {
                name = getString(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)

            }
        }
    }
}