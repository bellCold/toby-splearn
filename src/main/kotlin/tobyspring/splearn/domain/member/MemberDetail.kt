package tobyspring.splearn.domain.member

import jakarta.persistence.Entity
import tobyspring.splearn.domain.AbstractEntity
import java.time.LocalDateTime

@Entity
class MemberDetail(
    val profile: String,
    val introduction: String,
    val registeredAt: LocalDateTime,
    val activatedAt: LocalDateTime,
    val deActivatedAt: LocalDateTime,
) : AbstractEntity() {
}