package dev.pitlor.sms

import dev.pitlor.sms.repositories.ContactRepository
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock

class ContactUnitTests {
    @Mock
    private lateinit var contacts: ContactRepository

    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2)
    }
}