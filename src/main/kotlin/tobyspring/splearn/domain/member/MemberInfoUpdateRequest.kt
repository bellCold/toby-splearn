package tobyspring.splearn.domain.member

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class MemberInfoUpdateRequest(
    @field:Size(min = 5, max = 20)
    val nickname: String,
    @field:NotNull @field:Size(max = 15)
    val profileAddress: String,
    @field:NotNull
    val introduction: String
)