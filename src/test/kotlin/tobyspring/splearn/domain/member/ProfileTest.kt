package tobyspring.splearn.domain.member

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class ProfileTest {
    @Test
    fun profile() {
        Profile("test")
    }

    @Test
    fun profileConstructorFailTest() {
        assertThatThrownBy { Profile("aaaabbbbccccdddeeeedd") }.isInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { Profile("A") }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun url() {
        val profile = Profile("bellcold")
        assertThat(profile.url()).isEqualTo("@${profile.address}")
    }
}