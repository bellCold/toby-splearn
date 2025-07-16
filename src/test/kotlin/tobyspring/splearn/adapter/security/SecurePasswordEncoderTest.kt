package tobyspring.splearn.adapter.security

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class SecurePasswordEncoderTest {
    @Test
    fun securePasswordEncoder() {
        val securePasswordEncoder = SecurePasswordEncoder()

        val passwordHash = securePasswordEncoder.encode("secret")

        securePasswordEncoder.matches("secret", passwordHash)

        assertThat(securePasswordEncoder.matches("secret", passwordHash)).isTrue()
        assertThat(securePasswordEncoder.matches("wrong", passwordHash)).isFalse()
    }
}