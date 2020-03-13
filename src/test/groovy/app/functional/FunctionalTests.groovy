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
        given: 'prepare resource for further head'
        def responseOfCreate = mockMvcFacade.post([
                url : root,
                body: [props: [a: 'a']]
        ])
                .andExpect(status().isCreated())
                .andReturn()
        ProcessConfigApiOutput createdProcessConfig = ResponseMapper.parseResponse(responseOfCreate, ProcessConfigApiOutput)

        expect: 'head previously prepared resource'
        mockMvcFacade.head([url: "$root/$createdProcessConfig.id"])
                .andExpect(status().isOk())
                .andReturn()
    }

    def 'GET: if resource not exists - 404'() {
        expect:
        mockMvcFacade.get([url: "$root/1"])
                .andExpect(status().isNotFound())
    }

    def 'POST &  GET: create resource and get it'() {
        when: 'prepare resource to be further get'
        def responseOfCreate = mockMvcFacade.post([
                url : root,
                body: [props: [a: 'a']]
        ])
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern("**$root/*"))
                .andReturn()
        ProcessConfigApiOutput createdProcessConfig = ResponseMapper.parseResponse(responseOfCreate, ProcessConfigApiOutput)

        then: 'verify response of create'
        createdProcessConfig.id
        createdProcessConfig.properties == [a: 'a']
        responseOfCreate.getResponse().getHeader('location').endsWith(createdProcessConfig.id)

        and: 'get previously created resource'
        def responseOfGet = mockMvcFacade.get([url: "$root/$createdProcessConfig.id"])
                .andExpect(status().isOk())
                .andReturn()
        ProcessConfigApiOutput getProcessConfig = ResponseMapper.parseResponse(responseOfGet, ProcessConfigApiOutput)

        then: 'verify response of get'
        getProcessConfig.id == createdProcessConfig.id
        getProcessConfig.properties == createdProcessConfig.properties
    }

    def 'PUT: if resource already exists - replace it'() {
        given: 'prepare resource that will be replaced'
        def responseOfCreate = mockMvcFacade.post([
                url : root,
                body: [props: [a: 'a']]
        ])
                .andExpect(status().isCreated())
                .andReturn()
        ProcessConfigApiOutput postProcessConfig = ResponseMapper.parseResponse(responseOfCreate, ProcessConfigApiOutput)

        when: 'put resource'
        def responseOfPut = mockMvcFacade.put([
                url : "$root/$postProcessConfig.id",
                body: [props: [a: 'b']]
        ])
                .andExpect(status().isOk())
                .andReturn()
        ProcessConfigApiOutput putProcessConfig = ResponseMapper.parseResponse(responseOfPut, ProcessConfigApiOutput)

        then: 'verify response of put'
        putProcessConfig.id == postProcessConfig.id
        putProcessConfig.properties == [a: 'b']

        and: 'get the replaced resource'
        def responseOfGet = mockMvcFacade.get([url: "$root/$postProcessConfig.id"])
                .andExpect(status().isOk())
                .andReturn()
        ProcessConfigApiOutput getProcessConfig = ResponseMapper.parseResponse(responseOfGet, ProcessConfigApiOutput)

        then: 'verify response of get'
        getProcessConfig.id == postProcessConfig.id
        getProcessConfig.properties == [a: 'b']
    }

    def 'PUT: if resource not exists - 404'() {
        expect:
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

    def 'PATCH: if resource exists - patch it'() {
        given: 'prepare resource that will be patched'
        def responseOfCreate = mockMvcFacade.post([
                url : root,
                body: [props: [a: 'a', b: 'b']]
        ])
                .andExpect(status().isCreated())
                .andReturn()
        ProcessConfigApiOutput createdProcessConfig = ResponseMapper.parseResponse(responseOfCreate, ProcessConfigApiOutput)

        when: 'patch previously added resource'
        def responseOfPatch = mockMvcFacade.patch([
                url : "$root/$createdProcessConfig.id",
                body: [props: [a: 'b', c: 'c']]
        ])
                .andExpect(status().isOk())
                .andReturn()
        ProcessConfigApiOutput patchedProcessConfig = ResponseMapper.parseResponse(responseOfPatch, ProcessConfigApiOutput)

        then: 'verify response of patch'
        patchedProcessConfig.id == createdProcessConfig.id
        patchedProcessConfig.properties == [a: 'b', c: 'c']

        and: 'get patched resource'
        def responseOfGetAfterPatch = mockMvcFacade.get([url: "$root/$createdProcessConfig.id"])
                .andExpect(status().isOk())
                .andReturn()
        ProcessConfigApiOutput afterPatchProcessConfig = ResponseMapper.parseResponse(responseOfGetAfterPatch, ProcessConfigApiOutput)

        and: 'verify response of get'
        afterPatchProcessConfig.properties == [a: 'b', c: 'c']
    }

    def 'DELETE: if resource not exists - 404'() {
        expect:
        mockMvcFacade.delete([url: "$root/1"])
                .andExpect(status().isNotFound())
    }

    def 'DELETE: if resource exists - delete it'() {
        given: 'prepare resource to be deleted'
        def responseOfCreate = mockMvcFacade.post([
                url : "$root",
                body: [props: [a: 'a']]
        ])
                .andExpect(status().isCreated())
                .andReturn()
        ProcessConfigApiOutput createdProcessConfig = ResponseMapper.parseResponse(responseOfCreate, ProcessConfigApiOutput)

        when: 'delete the resource'
        def responseOfDelete = mockMvcFacade.delete([url: "$root/$createdProcessConfig.id"])
                .andExpect(status().isOk())
                .andReturn()
                .response

        then: 'verify message of delete'
        responseOfDelete.contentAsString == "$createdProcessConfig.id"

        and: 'verify that it was successfully deleted using get'
        mockMvcFacade.get([url: "$root/$createdProcessConfig.id"])
                .andExpect(status().isNotFound())
    }

    def 'OPTIONS: check'() {
        given: 'http methods that should be supported'
        def methods = ['GET', 'HEAD', 'POST', 'PATCH', 'PUT', 'DELETE', 'OPTIONS']

        when: 'get options'
        def optionsResponse = mockMvcFacade.options([url: root])
                .andExpect(status().isOk())
                .andReturn()

        and: 'parse allow header'
        def allowedMethods = optionsResponse.getResponse().getHeader('allow')

        then: 'all methods should be supported'
        methods.every { allowedMethods.contains it }
    }
}
