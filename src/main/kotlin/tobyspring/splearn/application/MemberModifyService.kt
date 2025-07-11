package tobyspring.splearn.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import tobyspring.splearn.application.provided.MemberFinder
import tobyspring.splearn.application.provided.MemberRegister
import tobyspring.splearn.application.required.EmailSender
import tobyspring.splearn.application.required.MemberRepository
import tobyspring.splearn.domain.*

@Service
@Transactional
@Validated
class MemberModifyService(
    private val memberFinder: MemberFinder,
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val emailSender: EmailSender
) : MemberRegister {
    override fun register(memberRegisterRequest: MemberRegisterRequest): Member {
        checkDuplicateEmail(memberRegisterRequest)

        val member = Member.register(memberRegisterRequest, passwordEncoder)

        memberRepository.save(member)

        sendWelcomeEmail(member)

        return member
    }

    override fun activate(memberId: Long): Member {
        val member = memberFinder.find(memberId)

        member.activate()

        return memberRepository.save(member)
    }

    private fun sendWelcomeEmail(member: Member) {
        emailSender.send(member.email, "등록을 완료해주세요.", "아래 링크를 클릭해서 등록을 완료해주세요.")
    }

    private fun checkDuplicateEmail(memberRegisterRequest: MemberRegisterRequest) {
        if (memberRepository.findByEmail(Email(memberRegisterRequest.email)) != null) {
            throw DuplicateEmailException("이미 사용중인 이메일입니다: ${memberRegisterRequest.email}")
        }
    }
}