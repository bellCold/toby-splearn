package tobyspring.splearn.adapter.integration

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junitpioneer.jupiter.StdIo
import org.junitpioneer.jupiter.StdOut
import tobyspring.splearn.domain.shared.Email

class DummyEmailSenderTest {
    @Test
    @StdIo
    fun `dummy email sender is created`(out: StdOut) {
        val dummyEmailSender = DummyEmailSender()

        dummyEmailSender.send(Email("bellCold@naver.com"), "subject", " body")

        assertThat(out.capturedLines()[0]).isEqualTo("Dummy email sender")
    }
}