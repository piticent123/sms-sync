package dev.pitlor.sms

import dev.pitlor.sms.repositories.MessageRepository
import java.time.OffsetDateTime
import javax.inject.Inject
import kotlin.reflect.KSuspendFunction2

class Messages @Inject constructor(private val messageRepository: MessageRepository) {
    fun readAllAfter(minimumTime: OffsetDateTime?): List<Message> {
        val messageIds = messageRepository.getAllIdsAfter(minimumTime)
        return buildList(messageIds.mmsIds.size + messageIds.smsIds.size) {
            addAll(messageIds.smsIds
                .map(messageRepository::getSmsById)
                .map(Message::from)
            )
            addAll(messageIds.mmsIds
                .map(messageRepository::getMmsById)
                .map(Message::from)
            )
        }
    }
}