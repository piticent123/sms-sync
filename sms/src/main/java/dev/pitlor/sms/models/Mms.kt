package dev.pitlor.sms.models

import java.io.File
import java.time.OffsetDateTime

data class Mms (
    val address: String,
    val dateReceived: OffsetDateTime,
    val threadId: Long,
    val picture: File? = null,
    val subject: String? = null,
    val body: String? = null
)