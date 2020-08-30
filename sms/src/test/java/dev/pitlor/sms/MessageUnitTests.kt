package dev.pitlor.sms

import dev.pitlor.sms.repositories.MessageRepository
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import java.time.OffsetDateTime
import java.time.ZoneOffset

class MessageUnitTests {
    private fun date(year: Int): OffsetDateTime {
        return OffsetDateTime.of(year, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
    }

    @Test
    fun readAll() {
        val mockMessageRepository = Mockito.mock(MessageRepository::class.java)
        val messages = Messages(mockMessageRepository)

        Mockito
            .`when`(mockMessageRepository.getAllIdsAfter(null))
            .thenReturn(MessagesDTO(
                smsIds = IntRange(1, 10).map { it.toString() },
                mmsIds = IntRange(11, 20).map { it.toString() },
            ))
        for (i in IntRange(1, 10)) {
            Mockito
                .`when`(mockMessageRepository.getSmsById(i.toString()))
                .thenReturn(Sms(dateReceived = date(i), address = "", threadId = 0, body = ""))
        }
        for (i in IntRange(11, 20)) {
            Mockito
                .`when`(mockMessageRepository.getMmsById(i.toString()))
                .thenReturn(Mms(dateReceived = date(i), address = "", threadId = 0))
        }

        val expected = buildList(20) {
            addAll(IntRange(1, 10).map { Message(date = date(it), sender = "", threadId = 0) })
            addAll(IntRange(11, 20).map { Message(date = date(it), sender = "", threadId = 0) })
        }
        val actual = messages.readAllAfter(null)

        Assert.assertArrayEquals(expected.toTypedArray(), actual.toTypedArray())
    }
}