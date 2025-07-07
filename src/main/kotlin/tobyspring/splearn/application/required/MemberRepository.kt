package tobyspring.splearn.application.required

import org.springframework.data.repository.Repository
import tobyspring.splearn.domain.Email
import tobyspring.splearn.domain.Member

interface MemberRepository : Repository<Member, Long> {
    fun save(member: Member): Member
    fun findByEmail(email: Email): Member?
    fun findById(memberId: Long): Member?
}