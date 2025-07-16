package tobyspring.splearn.adapter.integration

import org.springframework.context.annotation.Fallback
import org.springframework.stereotype.Component
import tobyspring.splearn.application.member.required.EmailSender
import tobyspring.splearn.domain.shared.Email

@Component
@Fallback // 다른 빈 찾다가 없으면 사용
class DummyEmailSender : EmailSender {
    override fun send(email: Email, subject: String, body: String) {
        println("Dummy email sender")
    }
}