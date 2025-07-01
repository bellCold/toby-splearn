package tobyspring.splearn.application.required

import tobyspring.splearn.domain.Email

interface EmailSender {
    fun send(email: Email, subject: String, body: String)
}