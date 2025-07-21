package tobyspring.splearn.adapter.webapi

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.assertj.MockMvcTester
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.transaction.annotation.Transactional
import tobyspring.splearn.AssertThatUtils.Companion.equalsTo
import tobyspring.splearn.AssertThatUtils.Companion.notNull
import tobyspring.splearn.adapter.webapi.dto.MemberRegisterResponse
import tobyspring.splearn.application.member.provided.MemberRegister
import tobyspring.splearn.application.member.required.MemberRepository
import tobyspring.splearn.domain.member.MemberFixture.Companion.createMemberRegisterRequest

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class MemberApiTest(private val memberRepository: MemberRepository) {
    @Autowired
    private lateinit var memberRegister: MemberRegister

    @Autowired
    private lateinit var mvcTester: MockMvcTester

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun register() {
        val request = createMemberRegisterRequest()
        val requestJson = objectMapper.writeValueAsString(request)

        val result = mvcTester.post().uri("/api/members").contentType(MediaType.APPLICATION_JSON).content(requestJson).exchange()

        assertThat(result)
            .hasStatusOk()
            .bodyJson()
            .hasPathSatisfying("$.memberId") { notNull() }
            .hasPathSatisfying("$.email") { equalsTo(request.email) }

        val response = objectMapper.readValue(result.response.contentAsString, MemberRegisterResponse::class.java)

        val member = memberRepository.findById(response.memberId) ?: throw AssertionError()

        assertThat(member.email.address).isEqualTo(request.email)
        assertThat(member.nickname).isEqualTo(request.nickname)
    }

    @Test
    fun duplicateEmail() {
        memberRegister.register(createMemberRegisterRequest())

        val request = createMemberRegisterRequest()
        val requestJson = objectMapper.writeValueAsString(request)

        val result = mvcTester.post().uri("/api/members").contentType(MediaType.APPLICATION_JSON).content(requestJson).exchange()

        assertThat(result)
            .apply(print())
            .hasStatus(HttpStatus.CONFLICT)
    }
}