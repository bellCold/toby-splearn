package tobyspring.splearn.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MemberTest {
    lateinit var member: Member
    lateinit var passwordEncoder: PasswordEncoder

    @BeforeEach
    fun setUp() {
        passwordEncoder = object : PasswordEncoder {
            override fun encode(password: String): String {
                return password.uppercase()
            }

            override fun matches(password: String, passwordHash: String): Boolean {
                return encode(password) == passwordHash
            }
        }
        member = Member.create("toby@splearn.app", "Toby", "secret", passwordEncoder)
    }

    @Test
    fun createMember() {
        assertThat(member.status).isEqualTo(MemberStatus.PENDING)
    }

    @Test
    fun activate() {
        member.activate()

        assertThat(member.status).isEqualTo(MemberStatus.ACTIVE)
    }

    @Test
    fun activateFail() {
        member.activate()

        assertThatThrownBy { member.activate() }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun deactivate() {
        member.activate()

        member.deactivate()

        assertThat(member.status).isEqualTo(MemberStatus.DEACTIVATED)
    }

    @Test
    fun deactivateFail() {
        assertThatThrownBy { member.deactivate() }.isInstanceOf(IllegalArgumentException::class.java)

        member.activate()
        member.deactivate()

        assertThatThrownBy { member.deactivate() }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun verifyPassword() {
        assertThat(member.verifyPassword("secret", passwordEncoder)).isTrue()
    }

    @Test
    fun changeNickname() {
        assertThat(member.nickname).isEqualTo("Toby")
        member.changeNickname("change")
    }
}
