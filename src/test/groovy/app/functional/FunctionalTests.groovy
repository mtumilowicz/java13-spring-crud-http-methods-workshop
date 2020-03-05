package app.functional

import app.gateway.output.ProcessConfigApiOutput
import org.apache.coyote.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
class FunctionalTests extends Specification {

    @Autowired
    MockMvc mockMvc

    def 'get'() {
        expect:
        mockMvc.perform(get('/app/1'))
                .andExpect(status().isNotFound())
    }

    def 'post'() {
        expect:
        mockMvc.perform(
                post('/app')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestMapper.asJsonString([
                                props: [a: 'a']
                        ]))
        )
        .andExpect(status().isOk())
    }

    def 'put'() {
        given:
       def xxx = mockMvc.perform(
                post('/app')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestMapper.asJsonString([
                                props: [a: 'a']
                        ]))
        )
                .andExpect(status().isOk())
        .andReturn()

        ProcessConfigApiOutput yyy = ResponseMapper.parseResponse(xxx, ProcessConfigApiOutput)

        expect:
        mockMvc.perform(
                put("/app/$yyy.id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestMapper.asJsonString([
                                props: [a: 'b']
                        ]))
        )
                .andExpect(status().isOk())
    }

    def 'patch'() {
        given:
        def xxx = mockMvc.perform(
                post('/app')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestMapper.asJsonString([
                                props: [a: 'a']
                        ]))
        )
                .andExpect(status().isOk())
                .andReturn()

        ProcessConfigApiOutput yyy = ResponseMapper.parseResponse(xxx, ProcessConfigApiOutput)

        expect:
        mockMvc.perform(
                patch("/app/$yyy.id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestMapper.asJsonString([
                                props: [a: 'b']
                        ]))
        )
                .andExpect(status().isOk())
    }

    def 'delete'() {
        given:
        def xxx = mockMvc.perform(
                post('/app')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestMapper.asJsonString([
                                props: [a: 'a']
                        ]))
        )
                .andExpect(status().isOk())
                .andReturn()

        ProcessConfigApiOutput yyy = ResponseMapper.parseResponse(xxx, ProcessConfigApiOutput)

        expect:
        mockMvc.perform(
                delete("/app/$yyy.id"))
                .andExpect(status().isOk())
    }

    def 'options'() {
        expect:
        mockMvc.perform(
                options('/app'))
                .andExpect(status().isOk())
    }
}