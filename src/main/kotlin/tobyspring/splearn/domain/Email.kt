package tobyspring.splearn.domain

import java.util.regex.Pattern

@JvmInline
value class Email(val address: String) {
    companion object {
        private val EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        )
    }

    init {
        if (!EMAIL_PATTERN.matcher(address).matches()) {
            throw IllegalArgumentException("Invalid email address")
        }
    }
}