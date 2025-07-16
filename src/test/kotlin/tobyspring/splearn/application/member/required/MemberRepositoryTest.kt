package tobyspring.splearn.application.member.required

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.hibernate.exception.ConstraintViolationException
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor
import tobyspring.splearn.domain.member.Member
import tobyspring.splearn.domain.member.MemberFixture.Companion.createMemberRegisterRequest
import tobyspring.splearn.domain.member.MemberFixture.Companion.createPasswordEncoder
import tobyspring.splearn.domain.member.MemberStatus

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class MemberRepositoryTest(private val memberRepository: MemberRepository, private val entityManager: EntityManager) {
    @Test
    fun createMember() {
        val member = Member.register(createMemberRegisterRequest(), createPasswordEncoder())

        assertThat(member.id).isEqualTo(0L)

        memberRepository.save(member)

        assertThat(member.id).isGreaterThan(0L)

        entityManager.flush()
        entityManager.clear()

        val found = memberRepository.findById(member.id)!!
        assertThat(found.status).isEqualTo(MemberStatus.PENDING)
        assertThat(found.detail.registeredAt).isNotNull()
    }

    @Test
    fun duplicateEmail() {
        val member1 = Member.register(createMemberRegisterRequest(), createPasswordEncoder())

        entityManager.persist(member1)

        val member2 = Member.register(createMemberRegisterRequest(), createPasswordEncoder())

        assertThatThrownBy { entityManager.persist(member2) }.isInstanceOf(ConstraintViolationException::class.java)
    }
}