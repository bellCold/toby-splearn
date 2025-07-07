/*
package tobyspring.splearn.application.provided

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.springframework.test.util.ReflectionTestUtils
import tobyspring.splearn.application.MemberModifyService
import tobyspring.splearn.application.required.EmailSender
import tobyspring.splearn.application.required.MemberRepository
import tobyspring.splearn.domain.Email
import tobyspring.splearn.domain.Member
import tobyspring.splearn.domain.MemberFixture
import tobyspring.splearn.domain.MemberStatus

class MemberRegisterManualTest {
    @Test
    fun registerTestStub() {
        val register = MemberModifyService(
            memberRepository = MemberRepositoryStub(),
            emailSender = EmailSenderStub(),
            memberFinder = MemberFinder(),
            passwordEncoder = MemberFixture.createPasswordEncoder()
        )

        val member = register.register(MemberFixture.createMemberRegisterRequest())

        assertThat(member.id).isNotNull()
        assertThat(member.status).isEqualTo(MemberStatus.PENDING)
    }

    @Test
    fun registerTestMock() {
        val emailSenderMock = EmailSenderMock()
        val register = MemberModifyService(
            memberRepository = MemberRepositoryStub(),
            emailSender = emailSenderMock,
            passwordEncoder = MemberFixture.createPasswordEncoder()
        )

        val member = register.register(MemberFixture.createMemberRegisterRequest())

        assertThat(member.id).isNotNull()
        assertThat(member.status).isEqualTo(MemberStatus.PENDING)

        assertThat(emailSenderMock.tos).hasSize(1)
        assertThat(emailSenderMock.tos.first()).isEqualTo(member.email)
    }

    @Test
    fun registerTestMockito() {
        val emailSenderMock = mock(EmailSender::class.java)
        val register = MemberModifyService(
            memberRepository = MemberRepositoryStub(),
            emailSender = emailSenderMock,
            passwordEncoder = MemberFixture.createPasswordEncoder()
        )

        val member = register.register(MemberFixture.createMemberRegisterRequest())

        assertThat(member.id).isNotNull()
        assertThat(member.status).isEqualTo(MemberStatus.PENDING)

        Mockito.verify(emailSenderMock).send(eq(member.email), anyString(), anyString())
    }

    class MemberFinderStub : MemberFinder {
        override fun find(memberId: Long): Member {
            return MemberFixture.createMemberRegisterRequest()
        }
    }

    class MemberRepositoryStub : MemberRepository {
        override fun save(member: Member): Member {
            ReflectionTestUtils.setField(member, "id", 1L)
            return member
        }

        override fun findByEmail(email: Email): Member? {
            return null
        }

        override fun findById(memberId: Long): Member? {
            return null
        }
    }

    class EmailSenderStub : EmailSender {
        override fun send(email: Email, subject: String, body: String) {
            TODO("Not yet implemented")
        }
    }

    class EmailSenderMock : EmailSender {
        val tos = mutableListOf<Email>()

        override fun send(email: Email, subject: String, body: String) {
            tos.add(email)
        }
    }
}*/
