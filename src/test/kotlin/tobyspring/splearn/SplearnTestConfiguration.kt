package tobyspring.splearn

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import tobyspring.splearn.application.required.EmailSender
import tobyspring.splearn.domain.Email
import tobyspring.splearn.domain.MemberFixture
import tobyspring.splearn.domain.PasswordEncoder

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