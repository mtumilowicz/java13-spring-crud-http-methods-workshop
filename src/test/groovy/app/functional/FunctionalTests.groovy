package app.functional

import app.gateway.output.ProcessConfigApiOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

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

    def 'if resource that you want to delete cannot be found - 404'() {
        expect:
        mockMvc.perform(
                delete('/app/1'))
                .andExpect(status().isNotFound())
    }

    def 'if resource that you want to delete exists - delete it'() {
        given: 'prepare process config to be further deleted'
        def responseOfCreate = mockMvc.perform(
                post('/app')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestMapper.asJsonString([
                                props: [a: 'a']
                        ]))
        )
                .andExpect(status().isOk())
                .andReturn()
        ProcessConfigApiOutput createdProcessConfig = ResponseMapper.parseResponse(responseOfCreate, ProcessConfigApiOutput)

        and: 'verify that it was successfully added'
        mockMvc.perform(get("/app/$createdProcessConfig.id"))
                .andExpect(status().isOk())

        when: 'delete the resource'
        def responseOfDelete = mockMvc.perform(
                delete("/app/$createdProcessConfig.id"))
                .andExpect(status().isOk())
                .andReturn()
                .response

        then: 'verify message of delete'
        responseOfDelete.contentAsString == "$createdProcessConfig.id"

        and: 'verify that it was successfully deleted'
        mockMvc.perform(get("/app/$createdProcessConfig.id"))
                .andExpect(status().isNotFound())
    }

    def 'http options check'() {
        given: 'http methods that should be supported'
        def methods = ['GET', 'POST', 'PATCH', 'PUT', 'DELETE', 'OPTIONS']

        when: 'get options'
        def mockMvcResponse = mockMvc.perform(
                options('/app'))
                .andExpect(status().isOk())
                .andReturn()
        and: 'parse allow header'
        def allowedMethods = mockMvcResponse.getResponse().getHeader('allow')

        then: 'all methods should be supported'
        methods.every { allowedMethods.contains it }
    }
}
