package tobyspring.splearn.domain.member

import java.util.regex.Pattern

@JvmInline
value class Profile(val address: String) {
    companion object {
        private val PROFILE_PATTERN = Pattern.compile(
            "[a-z0-9]+"
        )
    }

    init {
        if (!PROFILE_PATTERN.matcher(address).matches()) {
            throw IllegalArgumentException("프로필 주소 형식이 바르지 않습니다: $address")
        }

        if (address.length > 15) {
            throw IllegalArgumentException("프로필 길이는 15자 이상으로 할 수 없습니다.: ${address.length}")
        }
    }

    fun url(): String {
        return "@${address}"
    }
}