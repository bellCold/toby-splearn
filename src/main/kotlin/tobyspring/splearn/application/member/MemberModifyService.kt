package tobyspring.splearn.application.member

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import tobyspring.splearn.application.member.provided.MemberFinder
import tobyspring.splearn.application.member.provided.MemberRegister
import tobyspring.splearn.application.member.required.EmailSender
import tobyspring.splearn.application.member.required.MemberRepository
import tobyspring.splearn.domain.member.*
import tobyspring.splearn.domain.shared.Email

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

    override fun deactivate(memberId: Long): Member {
        val member = memberFinder.find(memberId)

        member.deactivate()

        return memberRepository.save(member)
    }

    override fun updateInfo(memberId: Long, memberInfoUpdateRequest: MemberInfoUpdateRequest): Member {
        val member = memberFinder.find(memberId)

        checkDuplicateProfile(member, memberInfoUpdateRequest.profileAddress)

        member.updateInfo(memberInfoUpdateRequest)

        return memberRepository.save(member)
    }

    private fun checkDuplicateProfile(member: Member, profileAddress: String) {
        if (profileAddress.isEmpty()) {
            return
        }

        if (member.detail.profile != null && member.detail.profile?.address == profileAddress) {
            return
        }

        if (memberRepository.findByProfile(Profile(profileAddress)) != null) {
            throw DuplicateProfileException("이미 존재하는 프로필 주소입니다.: $profileAddress")
        }
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