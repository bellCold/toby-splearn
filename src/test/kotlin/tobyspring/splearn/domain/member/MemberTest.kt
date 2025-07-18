package tobyspring.splearn.domain.member

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tobyspring.splearn.domain.member.MemberFixture.Companion.createMemberRegisterRequest
import tobyspring.splearn.domain.member.MemberFixture.Companion.createMemberUpdateRequest
import tobyspring.splearn.domain.member.MemberFixture.Companion.createPasswordEncoder

class MemberTest {
    lateinit var member: Member
    lateinit var passwordEncoder: PasswordEncoder

    @BeforeEach
    fun setUp() {
        passwordEncoder = createPasswordEncoder()
        member = Member.register(createMemberRegisterRequest(), passwordEncoder)
    }

    @Test
    fun registerMember() {
        assertThat(member.status).isEqualTo(MemberStatus.PENDING)
    }

    @Test
    fun activate() {
        assertThat(member.detail.activatedAt).isNull()
        member.activate()

        assertThat(member.status).isEqualTo(MemberStatus.ACTIVE)
        assertThat(member.detail.activatedAt).isNotNull()
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
        assertThat(member.detail.deactivatedAt).isNotNull()
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
    fun changePassword() {
        member.changePassword("verySecret", passwordEncoder)

        assertThat(member.verifyPassword("verySecret", passwordEncoder)).isTrue()
    }

    @Test
    fun isActive() {
        assertThat(member.isActive()).isFalse()

        member.activate()

        assertThat(member.isActive()).isTrue()

        member.deactivate()

        assertThat(member.isActive()).isFalse()
    }

    @Test
    fun invalidEmail() {
        assertThatThrownBy {
            Member.register(createMemberRegisterRequest("invalidEmail"), passwordEncoder)
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun updateInfo() {
        member.activate()

        val request = createMemberUpdateRequest("changeNickname", "profileaddress", "introduction")
        member.updateInfo(request)

        assertThat(member.nickname).isEqualTo(request.nickname)
        assertThat(member.detail.profile!!.address).isEqualTo(request.profileAddress)
        assertThat(member.detail.introduction).isEqualTo(request.introduction)
    }
}