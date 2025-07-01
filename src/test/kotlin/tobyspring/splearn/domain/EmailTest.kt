package tobyspring.splearn.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EmailTest {
    @Test
    fun equality() {
        val email1 = Email("tobyspring@gmailcom")
        val email2 = Email("tobyspring@gmailcom")

        assertThat(email1).isEqualTo(email2)
    }
}