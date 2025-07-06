package tobyspring.splearn.domain

class DuplicateEmailException(override val message: String = "Duplicate email") : RuntimeException()