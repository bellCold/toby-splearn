package tobyspring.splearn.application.provided

import jakarta.validation.Valid
import tobyspring.splearn.domain.Member
import tobyspring.splearn.domain.MemberRegisterRequest

interface MemberRegister {
    fun register(@Valid memberRegisterRequest: MemberRegisterRequest): Member
}