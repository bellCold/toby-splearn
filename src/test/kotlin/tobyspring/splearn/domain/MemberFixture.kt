package tobyspring.splearn.domain

class MemberFixture {
    companion object {
        fun createMemberRegisterRequest(email: String): MemberRegisterRequest {
            return MemberRegisterRequest(email, "Toby", "secret")
        }

        fun createMemberRegisterRequest() = createMemberRegisterRequest("toby@splearn.com")

        fun createPasswordEncoder() = object : PasswordEncoder {
            override fun encode(password: String): String {
                return password.uppercase()
            }

            override fun matches(password: String, passwordHash: String): Boolean {
                return encode(password) == passwordHash
            }
        }
    }
}