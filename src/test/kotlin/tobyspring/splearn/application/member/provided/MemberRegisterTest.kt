package tobyspring.splearn.application.member.provided

import jakarta.persistence.EntityManager
import jakarta.validation.ConstraintViolationException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional
import tobyspring.splearn.SplearnTestConfiguration
import tobyspring.splearn.domain.member.*
import tobyspring.splearn.domain.member.MemberFixture.Companion.createMemberRegisterRequest
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
        }.isInstanceOf(ConstraintViolationException::class.java)
    }

    @Test
    fun activate() {
        var member = memberRegister.register(createMemberRegisterRequest())

        entityManager.flush()
        entityManager.clear()

        member = memberRegister.activate(member.id)

        entityManager.flush()

        assertThat(member.status).isEqualTo(MemberStatus.ACTIVE)
        assertThat(member.detail.activatedAt).isNotNull()
    }

    @Test
    fun deactivate() {
        var member = memberRegister.register(createMemberRegisterRequest())
        entityManager.flush()
        entityManager.clear()

        memberRegister.activate(member.id)
        entityManager.flush()
        entityManager.clear()

        member = memberRegister.deactivate(member.id)

        assertThat(member.status).isEqualTo(MemberStatus.DEACTIVATED)
        assertThat(member.detail.deactivatedAt).isNotNull()
    }

    @Test
    fun updateInfo() {
        var member = memberRegister.register(createMemberRegisterRequest())

        memberRegister.activate(member.id)
        entityManager.flush()
        entityManager.clear()

        member = memberRegister.updateInfo(
            member.id,
            MemberFixture.createMemberUpdateRequest("bellcold", "address", "introduction")
        )

        assertThat(member.status).isEqualTo(MemberStatus.ACTIVE)
        assertThat(member.detail.activatedAt).isNotNull()
        assertThat(member.nickname).isEqualTo("bellcold")
        assertThat(member.detail.profile?.address).isEqualTo("address")
        assertThat(member.detail.introduction).isEqualTo("introduction")
    }

    @Test
    fun updateInfoFail() {
        val member1 = memberRegister.register(createMemberRegisterRequest())
        memberRegister.activate(member1.id)
        memberRegister.updateInfo(
            member1.id,
            MemberFixture.createMemberUpdateRequest("bellcold", "address", "introduction")
        )

        val member2 = memberRegister.register(createMemberRegisterRequest("newEmail@naver.com"))
        entityManager.flush()
        entityManager.clear()

        assertThatThrownBy {
            memberRegister.updateInfo(
                member2.id,
                MemberFixture.createMemberUpdateRequest("bellcold", "address", "introduction")
            )
        }.isInstanceOf(DuplicateProfileException::class.java)
    }
}