package app.mockmvc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

@Component
class MockMvcFacade {

    @Autowired
    MockMvc mockMvc

    def post(Map request) {
        fireWithBody(MockMvcRequestBuilders.post(request.url), request)
    }

    def put(Map request) {
        fireWithBody(MockMvcRequestBuilders.put(request.url), request)
    }

    def patch(Map request) {
        fireWithBody(MockMvcRequestBuilders.patch(request.url), request)
    }

    def get(Map request) {
        fireWithoutBody(MockMvcRequestBuilders.get(request.url), request)
    }

    def head(Map request) {
        fireWithoutBody(MockMvcRequestBuilders.head(request.url), request)
    }

    def delete(Map request) {
        fireWithoutBody(MockMvcRequestBuilders.delete(request.url), request)
    }

    def options(Map request) {
        fireWithoutBody(MockMvcRequestBuilders.options(request.url), request)
    }

    private def fireWithoutBody(MockHttpServletRequestBuilder httpMethod, Map request) {
        mockMvc.perform(httpMethod.headers(prepareHeaders(request.headers)))
    }

    private def fireWithBody(MockHttpServletRequestBuilder httpMethod, Map request) {
        mockMvc.perform(
                httpMethod
                        .headers(prepareHeaders(request.headers))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestMapper.asJsonString(request.body)
                        )
        )
    }

    private def prepareHeaders(inputHeaders) {
        HttpHeaders headers = new HttpHeaders()
        !inputHeaders.each { headers.add(it.headerName, it.headerValue) }
        headers
    }
}
