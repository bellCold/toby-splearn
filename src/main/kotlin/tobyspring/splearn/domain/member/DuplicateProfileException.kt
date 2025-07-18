package tobyspring.splearn.domain.member

class DuplicateProfileException(override val message: String = "Duplicate profile address") : RuntimeException()