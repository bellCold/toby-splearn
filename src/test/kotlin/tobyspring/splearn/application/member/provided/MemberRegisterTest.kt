package tobyspring.splearn.application.member.provided

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional
import tobyspring.splearn.SplearnTestConfiguration
import tobyspring.splearn.domain.member.DuplicateEmailException
import tobyspring.splearn.domain.member.MemberFixture.Companion.createMemberRegisterRequest
import tobyspring.splearn.domain.member.MemberRegisterRequest
import tobyspring.splearn.domain.member.MemberStatus
import kotlin.test.Test

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class MemberRegisterTest(private val memberRegister: MemberRegister, private val entityManager: EntityManager) {
    @Test
    fun register() {
        val member = memberRegister.register(createMemberRegisterRequest())

        assertThat(member.id).isNotNull()
        assertThat(member.status).isEqualTo(MemberStatus.PENDING)
        assertThat(member.detail.registeredAt).isNotNull()
    }

    @Test
    fun duplicateEmailFail() {
        memberRegister.register(createMemberRegisterRequest())

        assertThatThrownBy {
            memberRegister.register(createMemberRegisterRequest())
        }.isInstanceOf(DuplicateEmailException::class.java)
    }

    @Test
    fun memberRegisterRequestFail() {
        val member = MemberRegisterRequest("invalidEmail", "bellCold", "secret")

        assertThatThrownBy {
            memberRegister.register(member)
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun activate() {
        var member = memberRegister.register(createMemberRegisterRequest())

        entityManager.flush()
        entityManager.clear()

        member = memberRegister.activate(member.id)

        entityManager.flush()

        assertThat(member.status).isEqualTo(MemberStatus.ACTIVE)
    }
}