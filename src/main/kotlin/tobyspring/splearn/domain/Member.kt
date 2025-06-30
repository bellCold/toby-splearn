package tobyspring.splearn.domain

class Member(
    val email: String,
    val nickname: String,
    val passwordHash: String,
    var status: MemberStatus = MemberStatus.PENDING,
) {
    fun activate() {
        require(status == MemberStatus.PENDING) { "PENDING 상태가 아닙니다." }

        this.status = MemberStatus.ACTIVE
    }

    fun deactivate() {
        require(status == MemberStatus.ACTIVE) { "ACTIVE 상태가 아닙니다." }

        this.status = MemberStatus.DEACTIVATED
    }

}

enum class MemberStatus {
    PENDING, ACTIVE, DEACTIVATED
}