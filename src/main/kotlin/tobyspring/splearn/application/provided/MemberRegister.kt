package tobyspring.splearn.application.provided

import tobyspring.splearn.domain.Member
import tobyspring.splearn.domain.MemberRegisterRequest

interface MemberRegister {
    fun register(memberRegisterRequest: MemberRegisterRequest): Member
}