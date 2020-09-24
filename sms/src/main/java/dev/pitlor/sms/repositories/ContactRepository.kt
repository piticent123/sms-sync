package dev.pitlor.sms.repositories

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.ContactsContract
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.pitlor.sms.*
import javax.inject.Inject

class ContactRepository @Inject constructor(@ApplicationContext private val context: Context) {
    private val contentResolver = context.contentResolver!!

    fun getContact(phoneNumberSearchQuery: String): Contact {
        return Contact().apply {
            phoneNumber = phoneNumberSearchQuery

            var contactId = -1L
            contentResolver.queryOnce(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection = arrayOf(ContactsContract.CommonDataKinds.Phone.CONTACT_ID),
                selection = "${ContactsContract.CommonDataKinds.Phone.NUMBER} = ?",
                selectionArgs = arrayOf(phoneNumberSearchQuery)
            ) {
                contactId = getLong(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            }
            if (contactId == -1L) return@apply

            contentResolver.queryOnce(
                ContactsContract.Contacts.CONTENT_URI,
                projection = arrayOf(
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
                    ContactsContract.Contacts.PHOTO_URI
                ),
                selection = "${ContactsContract.Contacts._ID} = ?",
                selectionArgs = arrayOf(contactId.toString())
            ) {
                name = getString(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)

                val photoUri = getString(ContactsContract.Contacts.PHOTO_URI)
                if (photoUri == "") return@queryOnce
                contentResolver.useInputStream(Uri.parse(photoUri)) {
                    val buffer = ByteArray(available())
                    read(buffer)
                    picture = BitmapFactory.decodeByteArray(buffer, 0, available())
                }
            }
        }
    }
}