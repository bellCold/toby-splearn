package tobyspring.splearn.application.member.provided

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.test.util.ReflectionTestUtils
import tobyspring.splearn.application.member.MemberModifyService
import tobyspring.splearn.application.member.required.EmailSender
import tobyspring.splearn.application.member.required.MemberRepository
import tobyspring.splearn.domain.shared.Email
import tobyspring.splearn.domain.member.Member
import tobyspring.splearn.domain.member.MemberFixture
import tobyspring.splearn.domain.member.MemberStatus
import tobyspring.splearn.domain.member.Profile

class MemberRegisterManualTest {
    @Test
    fun registerTestStub() {
        val register = MemberModifyService(
            memberRepository = MemberRepositoryStub(),
            emailSender = EmailSenderStub(),
            memberFinder = MemberFinderStub(),
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
            memberFinder = MemberFinderStub(),
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
        // Use a custom EmailSender implementation instead of Mockito mock
        val emailSenderCaptor = EmailSenderCaptor()
        val register = MemberModifyService(
            memberRepository = MemberRepositoryStub(),
            emailSender = emailSenderCaptor,
            passwordEncoder = MemberFixture.createPasswordEncoder(),
            memberFinder = MemberFinderStub()
        )

        val member = register.register(MemberFixture.createMemberRegisterRequest())

        assertThat(member.id).isNotNull()
        assertThat(member.status).isEqualTo(MemberStatus.PENDING)

        // Verify that send was called
        assertThat(emailSenderCaptor.wasCalled).isTrue()
        assertThat(emailSenderCaptor.lastEmail).isEqualTo(member.email)
    }

    class MemberFinderStub : MemberFinder {
        override fun find(memberId: Long): Member {
            return Member.register(
                MemberFixture.createMemberRegisterRequest(),
                passwordEncoder = MemberFixture.createPasswordEncoder()
            )
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

        override fun findByProfile(profile: Profile): Member? {
            return null
        }
    }

    class EmailSenderStub : EmailSender {
        override fun send(email: Email, subject: String, body: String) {
            // Do nothing - stub implementation
        }
    }

    class EmailSenderMock : EmailSender {
        val tos = mutableListOf<Email>()

        override fun send(email: Email, subject: String, body: String) {
            tos.add(email)
        }
    }

    class EmailSenderCaptor : EmailSender {
        var wasCalled = false
        var lastEmail: Email? = null
        var lastSubject: String? = null
        var lastBody: String? = null

        override fun send(email: Email, subject: String, body: String) {
            wasCalled = true
            lastEmail = email
            lastSubject = subject
            lastBody = body
        }
    }
}
