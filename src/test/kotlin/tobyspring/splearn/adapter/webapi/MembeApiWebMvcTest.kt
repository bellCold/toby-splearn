package tobyspring.splearn.adapter.webapi

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.assertj.MockMvcTester
import tobyspring.splearn.application.member.provided.MemberRegister
import tobyspring.splearn.domain.member.MemberFixture
import tobyspring.splearn.domain.member.MemberFixture.Companion.createMemberRegisterRequest

@WebMvcTest(MemberApi::class)
class MembeApiWebMvcTest {
    @MockitoBean
    private lateinit var memberRegister: MemberRegister
    @Autowired private lateinit var mvcTester: MockMvcTester
    @Autowired private lateinit var objectMapper: ObjectMapper

    @Test
    fun register() {
        val member = MemberFixture.createMember(1L)
        `when`(memberRegister.register(createMemberRegisterRequest())).thenReturn(member)

        val request = createMemberRegisterRequest()
        val requestJson = objectMapper.writeValueAsString(request)

        assertThat(mvcTester.post().uri("/api/members").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .hasStatusOk()
            .bodyJson()
            .extractingPath("$.memberId").asNumber().isEqualTo(1)

        verify(memberRegister).register(request)
    }

    @Test
    fun registerFail() {
        val request = createMemberRegisterRequest("invalid Email")
        val requestJson = objectMapper.writeValueAsString(request)

        assertThat(mvcTester.post().uri("/api/members").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .hasStatus4xxClientError()
    }
}
