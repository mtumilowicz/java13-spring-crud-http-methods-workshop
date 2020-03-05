package app.functional

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

@Component
class MockMvcFacade {

    @Autowired
    MockMvc mockMvc

    def post(Map request) {
        mockMvc.perform(
                MockMvcRequestBuilders.post(request.url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestMapper.asJsonString(request.body)
                        )
        )
    }

    def put(Map request) {
        mockMvc.perform(
                MockMvcRequestBuilders.put(request.url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestMapper.asJsonString(request.body)
                        )
        )
    }

    def patch(Map request) {
        mockMvc.perform(
                MockMvcRequestBuilders.patch(request.url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestMapper.asJsonString(request.body)
                        )
        )
    }

    def get(url) {
        mockMvc.perform(MockMvcRequestBuilders.get(url))
    }

    def delete(url) {
        mockMvc.perform(MockMvcRequestBuilders.delete(url))
    }

    def options(url) {
        mockMvc.perform(MockMvcRequestBuilders.options(url))
    }
}
