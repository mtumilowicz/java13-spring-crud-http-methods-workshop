package app.functional

import app.gateway.output.ProcessConfigApiOutput
import app.mockmvc.MockMvcFacade
import app.mockmvc.ResponseMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
class FunctionalTests extends Specification {

    @Autowired
    MockMvcFacade mockMvcFacade

    def root = '/app'

    def 'HEAD: if resource not exists - 404'() {
        expect:
        mockMvcFacade.head([url: "$root/1"])
                .andExpect(status().isNotFound())
    }

    def 'HEAD: if resource exists - 201'() {
        given: 'prepare resource to be further get'
        def responseOfCreate = mockMvcFacade.post([
                url : root,
                body: [props: [a: 'a']]
        ])
                .andExpect(status().isCreated())
                .andReturn()
        ProcessConfigApiOutput createdProcessConfig = ResponseMapper.parseResponse(responseOfCreate, ProcessConfigApiOutput)

        expect:
        mockMvcFacade.head([url: "$root/$createdProcessConfig.id"])
                .andExpect(status().isOk())
                .andReturn()
    }

    def 'GET: if resource not exists - 404'() {
        expect:
        mockMvcFacade.get([url: "$root/1"])
                .andExpect(status().isNotFound())
    }

    def 'GET: if resource exists - 200 and get it'() {
        given: 'prepare resource to be further get'
        def responseOfCreate = mockMvcFacade.post([
                url : root,
                body: [props: [a: 'a']]
        ])
                .andExpect(status().isCreated())
                .andReturn()
        ProcessConfigApiOutput createdProcessConfig = ResponseMapper.parseResponse(responseOfCreate, ProcessConfigApiOutput)

        when:
        def responseOfGet = mockMvcFacade.get([url: "$root/$createdProcessConfig.id"])
                .andExpect(status().isOk())
                .andReturn()
        ProcessConfigApiOutput getProcessConfig = ResponseMapper.parseResponse(responseOfGet, ProcessConfigApiOutput)

        then:
        getProcessConfig.id == createdProcessConfig.id
        getProcessConfig.properties == createdProcessConfig.properties
    }

    def 'POST: create resource'() {
        when: 'prepare resource to be further get'
        def responseOfCreate = mockMvcFacade.post([
                url : root,
                body: [props: [a: 'a']]
        ])
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern("**$root/*"))
                .andReturn()
        ProcessConfigApiOutput createdProcessConfig = ResponseMapper.parseResponse(responseOfCreate, ProcessConfigApiOutput)

        then: 'verify response'
        createdProcessConfig.id
        createdProcessConfig.properties == [a: 'a']
        responseOfCreate.getResponse().getHeader('location').endsWith(createdProcessConfig.id)

        and: 'resource was added'
        def responseOfGet = mockMvcFacade.get([url: "$root/$createdProcessConfig.id"])
                .andExpect(status().isOk())
                .andReturn()
        ProcessConfigApiOutput getProcessConfig = ResponseMapper.parseResponse(responseOfGet, ProcessConfigApiOutput)

        then:
        getProcessConfig.id == createdProcessConfig.id
        getProcessConfig.properties == createdProcessConfig.properties
    }

    def 'PUT: if resource already exists - replace it'() {
        given: 'prepare resource that would be replaced'
        def postResponse = mockMvcFacade.post([
                url : root,
                body: [props: [a: 'a']]
        ])
                .andExpect(status().isCreated())
                .andReturn()
        ProcessConfigApiOutput postProcessConfig = ResponseMapper.parseResponse(postResponse, ProcessConfigApiOutput)

        when: 'put resource'
        def responseOfPut = mockMvcFacade.put([
                url : "$root/$postProcessConfig.id",
                body: [props: [a: 'b']]
        ])
                .andExpect(status().isOk())
                .andReturn()
        ProcessConfigApiOutput putProcessConfig = ResponseMapper.parseResponse(responseOfPut, ProcessConfigApiOutput)

        then: 'verify response'
        putProcessConfig.id == postProcessConfig.id
        putProcessConfig.properties == [a: 'b']

        and: 'resource was replaced'
        def responseOfGet = mockMvcFacade.get([url: "$root/$postProcessConfig.id"])
                .andExpect(status().isOk())
                .andReturn()
        ProcessConfigApiOutput getProcessConfig = ResponseMapper.parseResponse(responseOfGet, ProcessConfigApiOutput)

        then:
        getProcessConfig.id == postProcessConfig.id
        getProcessConfig.properties == [a: 'b']
    }

    def 'PUT: if resource not exists - 404'() {
        expect: 'put resource'
        mockMvcFacade.put([
                url : "$root/1",
                body: [props: [a: 'b']]
        ])
                .andExpect(status().isNotFound())
                .andReturn()
    }

    def 'PATCH: if resource not exists - 404'() {
        expect:
        mockMvcFacade.patch([
                url : "$root/1",
                body: [props: [a: 'b']]
        ])
                .andExpect(status().isNotFound())
    }

    def 'PATCH: not empty case: if resource exists - patch it'() {
        given: 'prepare process config to be further deleted'
        def responseOfCreate = mockMvcFacade.post([
                url : root,
                body: [props: [a: 'a', b: 'b']]
        ])
                .andExpect(status().isCreated())
                .andReturn()
        ProcessConfigApiOutput createdProcessConfig = ResponseMapper.parseResponse(responseOfCreate, ProcessConfigApiOutput)

        and: 'verify that it was successfully added'
        mockMvcFacade.get([url: "$root/$createdProcessConfig.id"])
                .andExpect(status().isOk())
        when:
        def responseOfPatch = mockMvcFacade.patch([
                url : "$root/$createdProcessConfig.id",
                body: [props: [a: 'b', c: 'c']]
        ])
                .andExpect(status().isOk())
                .andReturn()
        ProcessConfigApiOutput patchedProcessConfig = ResponseMapper.parseResponse(responseOfPatch, ProcessConfigApiOutput)

        then: 'verify message of patch'
        patchedProcessConfig.properties == [a: 'b', c: 'c']

        and: 'verify that it was successfully patched'
        def responseOfGetAfterPatch = mockMvcFacade.get([url: "$root/$createdProcessConfig.id"])
                .andExpect(status().isOk())
                .andReturn()
        ProcessConfigApiOutput afterPatchProcessConfig = ResponseMapper.parseResponse(responseOfGetAfterPatch, ProcessConfigApiOutput)
        afterPatchProcessConfig.properties == [a: 'b', c: 'c']
    }

    def 'DELETE: if resource not exists - 404'() {
        expect:
        mockMvcFacade.delete([url: "$root/1"])
                .andExpect(status().isNotFound())
    }

    def 'DELETE: if resource exists - delete it'() {
        given: 'prepare process config to be further deleted'
        def responseOfCreate = mockMvcFacade.post([
                url : "$root",
                body: [props: [a: 'a']]
        ])
                .andExpect(status().isCreated())
                .andReturn()
        ProcessConfigApiOutput createdProcessConfig = ResponseMapper.parseResponse(responseOfCreate, ProcessConfigApiOutput)

        and: 'verify that it was successfully added'
        mockMvcFacade.get([url: "$root/$createdProcessConfig.id"])
                .andExpect(status().isOk())

        when: 'delete the resource'
        def responseOfDelete = mockMvcFacade.delete([url: "$root/$createdProcessConfig.id"])
                .andExpect(status().isOk())
                .andReturn()
                .response

        then: 'verify message of delete'
        responseOfDelete.contentAsString == "$createdProcessConfig.id"

        and: 'verify that it was successfully deleted'
        mockMvcFacade.get([url: "$root/$createdProcessConfig.id"])
                .andExpect(status().isNotFound())
    }

    def 'OPTIONS: check'() {
        given: 'http methods that should be supported'
        def methods = ['GET', 'HEAD', 'POST', 'PATCH', 'PUT', 'DELETE', 'OPTIONS']

        when: 'get options'
        def mockMvcResponse = mockMvcFacade.options([url: root])
                .andExpect(status().isOk())
                .andReturn()
        and: 'parse allow header'
        def allowedMethods = mockMvcResponse.getResponse().getHeader('allow')

        then: 'all methods should be supported'
        methods.every { allowedMethods.contains it }
    }
}
