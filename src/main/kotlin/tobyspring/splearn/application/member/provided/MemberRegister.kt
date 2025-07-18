package tobyspring.splearn.application.member.provided

import jakarta.validation.Valid
import tobyspring.splearn.domain.member.Member
import tobyspring.splearn.domain.member.MemberInfoUpdateRequest
import tobyspring.splearn.domain.member.MemberRegisterRequest

interface MemberRegister {
    fun register(@Valid memberRegisterRequest: MemberRegisterRequest): Member
    fun activate(memberId: Long): Member
    fun deactivate(memberId: Long): Member
    fun updateInfo(memberId: Long, @Valid memberInfoUpdateRequest: MemberInfoUpdateRequest): Member
}