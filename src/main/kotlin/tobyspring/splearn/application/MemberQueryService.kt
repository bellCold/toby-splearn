package tobyspring.splearn.application

import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import tobyspring.splearn.application.provided.MemberFinder
import tobyspring.splearn.application.required.MemberRepository
import tobyspring.splearn.domain.Member

@Service
@Validated
class MemberQueryService(private val memberRepository: MemberRepository) : MemberFinder {
    override fun find(memberId: Long): Member {
        return memberRepository.findById(memberId) ?: throw IllegalArgumentException("회원을 찾을 수 없습니다. ${memberId}")
    }
}