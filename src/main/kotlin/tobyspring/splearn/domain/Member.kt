package tobyspring.splearn.domain

class Member(
    val email: String,
    var nickname: String,
    val passwordHash: String,
    var status: MemberStatus = MemberStatus.PENDING,
) {
    companion object {
        fun create(email: String, nickname: String, password: String, passwordEncoder: PasswordEncoder): Member {
            return Member(email, nickname, passwordEncoder.encode(password))
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

}

enum class MemberStatus {
    PENDING, ACTIVE, DEACTIVATED
}