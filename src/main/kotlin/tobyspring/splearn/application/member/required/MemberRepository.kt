package tobyspring.splearn.application.member.required

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository
import tobyspring.splearn.domain.member.Member
import tobyspring.splearn.domain.member.Profile
import tobyspring.splearn.domain.shared.Email

interface MemberRepository : Repository<Member, Long> {
    fun save(member: Member): Member
    fun findByEmail(email: Email): Member?
    fun findById(memberId: Long): Member?

    @Query("select m from Member m where m.detail.profile = :profile")
    fun findByProfile(profile: Profile): Member?
}