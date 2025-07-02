package tobyspring.splearn.application

import org.springframework.stereotype.Service
import tobyspring.splearn.application.provided.MemberRegister
import tobyspring.splearn.application.required.EmailSender
import tobyspring.splearn.application.required.MemberRepository
import tobyspring.splearn.domain.Member
import tobyspring.splearn.domain.MemberRegisterRequest
import tobyspring.splearn.domain.PasswordEncoder

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val emailSender: EmailSender
) : MemberRegister {
    override fun register(memberRegisterRequest: MemberRegisterRequest): Member {
        val member = Member.register(memberRegisterRequest, passwordEncoder)

        memberRepository.save(member)

        emailSender.send(member.email, "회원가입을 축하합니다.", "아래 링크를 클릭해서 등록을 완료해주세요.")

        return member
    }
}