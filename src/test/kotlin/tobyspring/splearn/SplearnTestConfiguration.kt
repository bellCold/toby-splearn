package tobyspring.splearn

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import tobyspring.splearn.application.member.required.EmailSender
import tobyspring.splearn.domain.member.MemberFixture
import tobyspring.splearn.domain.member.PasswordEncoder
import tobyspring.splearn.domain.shared.Email

@TestConfiguration
class SplearnTestConfiguration {
    @Bean
    fun emailSender(): EmailSender {
        return object : EmailSender {
            override fun send(email: Email, subject: String, body: String) {
                println("send email: $email, $subject, $body")
            }
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return MemberFixture.createPasswordEncoder()
    }
}