package tobyspring.splearn.adapter.webapi.dto

import tobyspring.splearn.domain.member.Member

data class MemberRegisterResponse(val memberId: Long, val email: String) {
    companion object {
        fun of(member: Member): MemberRegisterResponse {
            return MemberRegisterResponse(member.id, member.email.address)
        }
    }
}