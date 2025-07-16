package tobyspring.splearn.application.member.provided

import tobyspring.splearn.domain.member.Member
import tobyspring.splearn.domain.member.MemberRegisterRequest

interface MemberRegister {
    fun register(memberRegisterRequest: MemberRegisterRequest): Member
    fun activate(memberId: Long): Member
}