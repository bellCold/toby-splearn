package tobyspring.splearn.domain

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size

data class MemberRegisterRequest(
    @field:Email
    val email: String,
    @field:Size(min = 5, max = 20)
    val nickname: String,
    @field:Size(min = 5, max = 100)
    val password: String
)
