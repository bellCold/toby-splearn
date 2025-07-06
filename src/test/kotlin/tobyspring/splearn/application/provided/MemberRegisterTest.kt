package tobyspring.splearn.application.provided

import jakarta.validation.ConstraintViolationException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional
import tobyspring.splearn.SplearnTestConfiguration
import tobyspring.splearn.domain.DuplicateEmailException
import tobyspring.splearn.domain.MemberFixture.Companion.createMemberRegisterRequest
import tobyspring.splearn.domain.MemberRegisterRequest
import tobyspring.splearn.domain.MemberStatus
import kotlin.test.Test

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class MemberRegisterTest(private val memberRegister: MemberRegister) {
    @Test
    fun register() {
        val member = memberRegister.register(createMemberRegisterRequest())

        assertThat(member.id).isNotNull()
        assertThat(member.status).isEqualTo(MemberStatus.PENDING)
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
}