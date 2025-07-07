package tobyspring.splearn.application.provided

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional
import tobyspring.splearn.SplearnTestConfiguration
import tobyspring.splearn.domain.MemberFixture

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class MemberFinderTest(private val memberFinder: MemberFinder, private val memberRegister: MemberRegister, private val entityManager: EntityManager) {
    @Test
    fun find() {
        val member = memberRegister.register(MemberFixture.createMemberRegisterRequest())
        entityManager.flush()
        entityManager.clear()

        val foundMember = memberFinder.find(member.id)

        assertThat(member.id).isEqualTo(foundMember.id)
    }

    @Test
    fun findFail() {
        assertThatThrownBy { memberFinder.find(0) }.isInstanceOf(IllegalArgumentException::class.java)
    }
}