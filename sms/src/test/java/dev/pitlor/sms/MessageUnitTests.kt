package dev.pitlor.sms

import dev.pitlor.sms.models.Mms
import dev.pitlor.sms.models.Sms
import dev.pitlor.sms.repositories.MessageRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mock
import org.mockito.Mockito
import java.time.OffsetDateTime
import java.time.ZoneOffset

class MessageUnitTests {
    @Mock
    private lateinit var mockMessageRepository: MessageRepository

    fun date(year: Int, month: Int, day: Int): OffsetDateTime {
        return OffsetDateTime.of(year, month, day, 0, 0, 0, 0, ZoneOffset.UTC)
    }

    @Test
    fun readAll() {
        Mockito
            .`when`(mockMessageRepository.getAllIdsAfter(null))
            .thenReturn(listOf(IntRange(1, 10).map { it.toString() }))
        for (i in IntRange(1, 10)) {
            Mockito
                .`when`(mockMessageRepository.getSmsById(i.toString()))
                .thenReturn(Sms.builder().dateReceived(date(2015 + i, 1, 1)).build())
        }

    }

    @Test
    fun readAllAfter() {
        val cutoff = date(2020, 1, 1)

        Mockito
            .`when`(mockMessageRepository.getAllIdsAfter(null))
            .thenReturn(listOf(
                IntRange(1, 10).map { it.toString() },
                IntRange(11, 20).map { it.toString() }
            ))
        for (i in IntRange(1, 10)) {
            Mockito
                .`when`(mockMessageRepository.getSmsById(i.toString()))
                .thenReturn(Sms.builder().dateReceived(date(2015 + i, 1, 1)).build())
        }
        for (i in IntRange(11, 20)) {
            Mockito
                .`when`(mockMessageRepository.getMmsById(i.toString()))
                .thenReturn(Mms.builder().dateReceived(date(2015 + i, 1, 1)).build())
        }
    }
}