package tobyspring.splearn.domain.member

import jakarta.persistence.*
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
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val detail: MemberDetail,
) : AbstractEntity() {
    companion object {
        fun register(memberRegisterRequest: MemberRegisterRequest, passwordEncoder: PasswordEncoder): Member {
            return Member(
                email = Email(memberRegisterRequest.email),
                nickname = memberRegisterRequest.nickname,
                passwordHash = passwordEncoder.encode(memberRegisterRequest.password),
                detail = MemberDetail.create(),
            )
        }
    }

    fun activate() {
        require(status == MemberStatus.PENDING) { "PENDING 상태가 아닙니다." }

        this.status = MemberStatus.ACTIVE
        this.detail.activate()
    }

    fun deactivate() {
        require(status == MemberStatus.ACTIVE) { "ACTIVE 상태가 아닙니다." }

        this.status = MemberStatus.DEACTIVATED
        this.detail.deactivate()
    }

    fun verifyPassword(password: String, passwordEncoder: PasswordEncoder): Boolean {
        return passwordEncoder.matches(password, this.passwordHash)
    }

    fun updateInfo(memberInfoUpdateRequest: MemberInfoUpdateRequest) {
        require(status == MemberStatus.ACTIVE) { "ACTIVE 상태가 아닙니다." }

        this.nickname = memberInfoUpdateRequest.nickname
        this.detail.updateInfo(memberInfoUpdateRequest)
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