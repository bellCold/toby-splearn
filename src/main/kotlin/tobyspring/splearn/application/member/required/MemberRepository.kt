package tobyspring.splearn.application.member.required

import org.springframework.data.repository.Repository
import tobyspring.splearn.domain.shared.Email
import tobyspring.splearn.domain.member.Member

interface MemberRepository : Repository<Member, Long> {
    fun save(member: Member): Member
    fun findByEmail(email: Email): Member?
    fun findById(memberId: Long): Member?
}