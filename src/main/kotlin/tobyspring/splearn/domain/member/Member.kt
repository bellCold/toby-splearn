package tobyspring.splearn.domain.member

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.OneToOne
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.NaturalIdCache
import tobyspring.splearn.domain.AbstractEntity
import tobyspring.splearn.domain.shared.Email

@Entity
@NaturalIdCache
class Member(
    @NaturalId
    val email: Email,
    var nickname: String,
    var passwordHash: String,
    @Enumerated(EnumType.STRING)
    var status: MemberStatus = MemberStatus.PENDING,
    @OneToOne
    val detail: MemberDetail? = null,
) : AbstractEntity() {
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