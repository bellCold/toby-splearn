package tobyspring.splearn.domain

class Member(
    val email: Email,
    var nickname: String,
    var passwordHash: String,
    var status: MemberStatus = MemberStatus.PENDING,
) {
    companion object {
        fun create(memberCreateRequest: MemberCreateRequest, passwordEncoder: PasswordEncoder): Member {
            return Member(
                Email(memberCreateRequest.email),
                memberCreateRequest.nickname,
                passwordEncoder.encode(memberCreateRequest.password)
            )
        }
    }

    fun activate() {
        require(status == MemberStatus.PENDING) { "PENDING 상태가 아닙니다." }

        this.status = MemberStatus.ACTIVE
    }

    fun deactivate() {
        require(status == MemberStatus.ACTIVE) { "ACTIVE 상태가 아닙니다." }

        this.status = MemberStatus.DEACTIVATED
    }

    fun verifyPassword(password: String, passwordEncoder: PasswordEncoder): Boolean {
        return passwordEncoder.matches(password, this.passwordHash)
    }

    fun changeNickname(newNickname: String) {
        this.nickname = newNickname
    }

    fun changePassword(password: String, passwordEncoder: PasswordEncoder) {
        this.passwordHash = passwordEncoder.encode(password)
    }

    fun isActive(): Boolean {
        return this.status == MemberStatus.ACTIVE
    }

}

enum class MemberStatus {
    PENDING, ACTIVE, DEACTIVATED
}