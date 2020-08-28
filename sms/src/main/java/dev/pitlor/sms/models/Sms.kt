package dev.pitlor.sms.models

import java.time.OffsetDateTime

data class Sms (
    val address: String,
    val dateReceived: OffsetDateTime,
    val threadId: Long,
    val body: String,
    val subject: String? = null
)