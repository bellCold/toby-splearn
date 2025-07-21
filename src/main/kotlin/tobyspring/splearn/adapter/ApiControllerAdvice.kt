package tobyspring.splearn.adapter

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import tobyspring.splearn.domain.member.DuplicateEmailException
import tobyspring.splearn.domain.member.DuplicateProfileException
import java.time.LocalDateTime

@ControllerAdvice
class ApiControllerAdvice : ResponseEntityExceptionHandler() {
    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ProblemDetail {
        return getProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception)
    }

    @ExceptionHandler(value = [DuplicateEmailException::class, DuplicateProfileException::class])
    fun handleDuplicateEmailException(exception: DuplicateEmailException): ProblemDetail {
        return getProblemDetail(HttpStatus.CONFLICT, exception)
    }

    private fun getProblemDetail(status: HttpStatus, exception: Exception): ProblemDetail {
        return ProblemDetail
            .forStatusAndDetail(status, exception.message)
            .apply {
                setProperty("timeStamp", LocalDateTime.now())
                setProperty("exception", exception::class.simpleName)
            }
    }
}