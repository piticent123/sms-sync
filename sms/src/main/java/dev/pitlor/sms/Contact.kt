package dev.pitlor.sms

import java.io.File

data class Contact (
    val name: String,
    val phoneNumber: List<String>,
    val picture: File? = null
)