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

    def 'if resource that you want to get not exists - 404'() {
        expect:
        mockMvc.perform(get('/app/1'))
                .andExpect(status().isNotFound())
    }

    def 'if resource that you want to get exists - get it'() {
        given: 'prepare resource to be further get'
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

        when:
        def responseOfGet = mockMvc.perform(get("/app/$createdProcessConfig.id"))
                .andExpect(status().isOk())
                .andReturn()
        ProcessConfigApiOutput getProcessConfig = ResponseMapper.parseResponse(responseOfGet, ProcessConfigApiOutput)

        then:
        getProcessConfig.id == createdProcessConfig.id
        getProcessConfig.properties == createdProcessConfig.properties
    }

    def 'create resource'() {
        when: 'prepare resource to be further get'
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

        then: 'verify response'
        createdProcessConfig.id
        createdProcessConfig.properties == [a: 'a']

        and: 'resource was added'
        def responseOfGet = mockMvc.perform(get("/app/$createdProcessConfig.id"))
                .andExpect(status().isOk())
                .andReturn()
        ProcessConfigApiOutput getProcessConfig = ResponseMapper.parseResponse(responseOfGet, ProcessConfigApiOutput)

        then:
        getProcessConfig.id == createdProcessConfig.id
        getProcessConfig.properties == createdProcessConfig.properties
    }

    def 'if resource that you want to put not exists - put it'() {
        given:
        def id = UUID.randomUUID().toString()

        when: 'resource is put'
        def responseOfPut = mockMvc.perform(
                put('/app/' + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestMapper.asJsonString([
                                props: [a: 'b']
                        ]))
        )
                .andExpect(status().isOk())
                .andReturn()
        ProcessConfigApiOutput putProcessConfig = ResponseMapper.parseResponse(responseOfPut, ProcessConfigApiOutput)

        then: 'verify response'
        putProcessConfig.id == id
        putProcessConfig.properties == [a: 'b']

        and: 'check if resource was added'
        mockMvc.perform(get("/app/$id"))
                .andExpect(status().isOk())
    }

    def 'if resource that you want to put exists - replace it'() {
        given: 'prepare resource that would be replaced'
        def postResponse = mockMvc.perform(
                post('/app')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestMapper.asJsonString([
                                props: [a: 'a']
                        ]))
        )
                .andExpect(status().isOk())
                .andReturn()
        ProcessConfigApiOutput postProcessConfig = ResponseMapper.parseResponse(postResponse, ProcessConfigApiOutput)

        when: 'put resource'
        def responseOfPut = mockMvc.perform(
                put("/app/$postProcessConfig.id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestMapper.asJsonString([
                                props: [a: 'b']
                        ]))
        )
                .andExpect(status().isOk())
                .andReturn()
        ProcessConfigApiOutput putProcessConfig = ResponseMapper.parseResponse(responseOfPut, ProcessConfigApiOutput)

        then: 'verify response'
        putProcessConfig.id == postProcessConfig.id
        putProcessConfig.properties == [a: 'b']

        and: 'resource was replaced'
        def responseOfGet = mockMvc.perform(get("/app/$postProcessConfig.id"))
                .andExpect(status().isOk())
                .andReturn()
        ProcessConfigApiOutput getProcessConfig = ResponseMapper.parseResponse(responseOfGet, ProcessConfigApiOutput)

        then:
        getProcessConfig.id == postProcessConfig.id
        getProcessConfig.properties == [a: 'b']
    }

    def 'if resource that you want to patch cannot be found - 404'() {
        expect:
        mockMvc.perform(
                patch("/app/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestMapper.asJsonString([
                                props: [a: 'b']
                        ]))
        )
                .andExpect(status().isNotFound())
    }

    def 'if resource that you want to patch exists - patch it'() {
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
        when:
        def responseOfPatch = mockMvc.perform(
                patch("/app/$createdProcessConfig.id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestMapper.asJsonString([
                                props: [a: 'b']
                        ]))
        )
                .andExpect(status().isOk())
                .andReturn()
        ProcessConfigApiOutput patchedProcessConfig = ResponseMapper.parseResponse(responseOfPatch, ProcessConfigApiOutput)

        then: 'verify message of patch'
        patchedProcessConfig.properties == [a: 'b']

        and: 'verify that it was successfully patched'
        def responseOfGetAfterPatch = mockMvc.perform(get("/app/$createdProcessConfig.id"))
                .andExpect(status().isOk())
                .andReturn()
        ProcessConfigApiOutput afterPatchProcessConfig = ResponseMapper.parseResponse(responseOfGetAfterPatch, ProcessConfigApiOutput)
        afterPatchProcessConfig.properties == [a: 'b']
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
