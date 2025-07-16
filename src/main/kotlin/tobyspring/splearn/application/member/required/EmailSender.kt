package tobyspring.splearn.application.member.required

import tobyspring.splearn.domain.shared.Email

interface EmailSender {
    fun send(email: Email, subject: String, body: String)
}