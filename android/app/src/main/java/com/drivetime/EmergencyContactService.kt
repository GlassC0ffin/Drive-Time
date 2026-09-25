package com.drivetime

data class ContactModel(
    val name: String,
    val phoneNumber: String
)

class EmergencyContactService {
    fun getEmergencyContacts(): List<ContactModel> {
        return listOf(
            ContactModel("Mom", "+15550000001"),
            ContactModel("Dad", "+15550000002")
        )
    }

    fun callEmergencyContact(contact: ContactModel) {
        // Launch the Android dialer or system call flow.
    }
}
