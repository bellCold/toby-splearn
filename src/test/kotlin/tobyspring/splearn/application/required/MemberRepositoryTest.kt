package tobyspring.splearn.application.required

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor
import tobyspring.splearn.domain.Member
import tobyspring.splearn.domain.MemberFixture.Companion.createMemberRegisterRequest
import tobyspring.splearn.domain.MemberFixture.Companion.createPasswordEncoder

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
    }
}