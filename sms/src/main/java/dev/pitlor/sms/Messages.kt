package dev.pitlor.sms

import dev.pitlor.sms.repositories.MessageRepository
import java.time.OffsetDateTime
import javax.inject.Inject
import kotlin.reflect.KSuspendFunction2

class Messages @Inject constructor(private val messageRepository: MessageRepository) {
    suspend fun readAllAfter(minimumTime: OffsetDateTime?, reportProgress: suspend (String) -> Unit): List<Message> {
        reportProgress("Reading message IDs")
        val messageIds = messageRepository.getAllIdsAfter(minimumTime)
        return buildList(messageIds.mmsIds.size + messageIds.smsIds.size) {
            addAll(messageIds.smsIds
                .map {
                    reportProgress("Reading SMS $it")
                    return@map messageRepository.getSmsById(it)
                }
                .map { Message.from(it) }
            )
            addAll(messageIds.mmsIds
                .map {
                    reportProgress("Reading MMS $it")
                    return@map messageRepository.getMmsById(it)
                }
                .map { Message.from(it) }
            )
        }
    }
}