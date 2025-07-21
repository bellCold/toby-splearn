package tobyspring.splearn.adapter.webapi

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import tobyspring.splearn.adapter.webapi.dto.MemberRegisterResponse
import tobyspring.splearn.application.member.provided.MemberRegister
import tobyspring.splearn.domain.member.MemberRegisterRequest

@RestController
class MemberApi(private val memberRegister: MemberRegister) {
    @PostMapping("/api/members")
    fun register(@RequestBody @Valid request: MemberRegisterRequest): MemberRegisterResponse {
        val member = memberRegister.register(request)

        return MemberRegisterResponse.of(member)
    }
}
