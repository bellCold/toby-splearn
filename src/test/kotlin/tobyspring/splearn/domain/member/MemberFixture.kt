package tobyspring.splearn.domain.member

import org.springframework.test.util.ReflectionTestUtils

class MemberFixture {
    companion object {
        fun createMemberRegisterRequest(email: String): MemberRegisterRequest {
            return MemberRegisterRequest(email, "bellCold", "secret")
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

        fun createMemberUpdateRequest(nickname: String, profileAddress: String, introduction: String): MemberInfoUpdateRequest {
            return MemberInfoUpdateRequest(nickname, profileAddress, introduction)
        }

        fun createMember(id: Long) : Member{
            val member = Member.register(createMemberRegisterRequest(), createPasswordEncoder())
            ReflectionTestUtils.setField(member, "id", id)
            return member
        }

        fun createMember(email: String): Member {
            return Member.register(createMemberRegisterRequest(email), createPasswordEncoder())
        }

    }
}