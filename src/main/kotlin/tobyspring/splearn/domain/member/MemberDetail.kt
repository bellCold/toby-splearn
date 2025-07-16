package tobyspring.splearn.domain.member

import jakarta.persistence.Entity
import org.springframework.util.Assert
import tobyspring.splearn.domain.AbstractEntity
import java.time.LocalDateTime

@Entity
class MemberDetail(
    var profile: Profile? = null,
    var introduction: String? = null,
    val registeredAt: LocalDateTime,
    var activatedAt: LocalDateTime? = null,
    var deactivatedAt: LocalDateTime? = null,
) : AbstractEntity() {
    companion object {
        fun create(): MemberDetail {
            return MemberDetail(registeredAt = LocalDateTime.now())
        }
    }

    fun activate() {
        Assert.isTrue(this.activatedAt == null, "이미 activatedAt은 설정 되었습니다.")

        this.activatedAt = LocalDateTime.now()
    }

    fun deactivate() {
        Assert.isTrue(this.deactivatedAt == null, "이미 deactivatedAt은 설정 되었습니다.")

        this.deactivatedAt = LocalDateTime.now()
    }

    fun updateInfo(memberInfoUpdateRequest: MemberInfoUpdateRequest) {
        this.profile = Profile(memberInfoUpdateRequest.profileAddress)
        this.introduction = memberInfoUpdateRequest.introduction
    }
}