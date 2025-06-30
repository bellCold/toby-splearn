package tobyspring.splearn.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class MemberTest {
    @Test
    fun createMember() {
        val member = Member("toby@splearn.app", "Toby", "secret")

        assertThat(member.status).isEqualTo(MemberStatus.PENDING)
    }

    @Test
    fun activate() {
        val member = Member("toby@splearn.app", "Toby", "secret")

        member.activate()

        assertThat(member.status).isEqualTo(MemberStatus.ACTIVE)
    }

    @Test
    fun activateFail() {
        val member = Member("toby@splearn.app", "Toby", "secret")

        member.activate()

        assertThatThrownBy { member.activate() }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun deactivate() {
        val member = Member("toby@splearn.app", "Toby", "secret")
        member.activate()

        member.deactivate()

        assertThat(member.status).isEqualTo(MemberStatus.DEACTIVATED)
    }

    @Test
    fun deactivateFail() {
        val member = Member("toby@splearn.app", "Toby", "secret")

        assertThatThrownBy { member.deactivate() }.isInstanceOf(IllegalArgumentException::class.java)

        member.activate()
        member.deactivate()

        assertThatThrownBy { member.deactivate() }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
