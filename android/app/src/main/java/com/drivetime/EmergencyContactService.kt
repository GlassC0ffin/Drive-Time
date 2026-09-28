package com.drivetime

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract

data class ContactModel(
    val name: String,
    val phoneNumber: String
)

class EmergencyContactService {
    fun getEmergencyContacts(context: Context): List<ContactModel> {
        if (context.checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            val contacts = readContacts(context.contentResolver)
            if (contacts.isNotEmpty()) return contacts
        }

        return listOf(
            ContactModel("Mom", "+15550000001"),
            ContactModel("Dad", "+15550000002"),
            ContactModel("Family", "+15550000003"),
            ContactModel("Aunt", "+15550000004"),
            ContactModel("Uncle", "+15550000005"),
            ContactModel("Emergency", "+15550000006")
        )
    }

    private fun readContacts(contentResolver: ContentResolver): List<ContactModel> {
        val result = mutableListOf<ContactModel>()
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
            ),
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        cursor?.use { c ->
            val nameIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            while (c.moveToNext()) {
                val name = c.getString(nameIndex) ?: continue
                val number = c.getString(numberIndex) ?: continue
                if (name.isNotBlank() && number.isNotBlank()) {
                    result.add(ContactModel(name, number))
                }
            }
        }

        return result.take(12)
    }

    fun callEmergencyContact(context: Context, contact: ContactModel) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${contact.phoneNumber}"))
        context.startActivity(intent)
    }
}
