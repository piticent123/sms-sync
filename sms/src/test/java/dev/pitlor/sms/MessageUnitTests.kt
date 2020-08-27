package dev.pitlor.sms

import dev.pitlor.sms.repositories.MessageRepository
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock

class MessageUnitTests {
    @Mock
    private lateinit var mockMessageRepository: MessageRepository

    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2)
    }
}