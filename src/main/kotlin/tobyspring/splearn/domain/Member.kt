package tobyspring.splearn.domain

import jakarta.persistence.*
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.NaturalIdCache

@Entity
@NaturalIdCache
class Member(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val email: Email,
    @NaturalId
    var nickname: String,
    var passwordHash: String,
    @Enumerated(EnumType.STRING)
    var status: MemberStatus = MemberStatus.PENDING,
) {
    companion object {
        fun register(memberRegisterRequest: MemberRegisterRequest, passwordEncoder: PasswordEncoder): Member {
            return Member(
                email = Email(memberRegisterRequest.email),
                nickname = memberRegisterRequest.nickname,
                passwordHash = passwordEncoder.encode(memberRegisterRequest.password)
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