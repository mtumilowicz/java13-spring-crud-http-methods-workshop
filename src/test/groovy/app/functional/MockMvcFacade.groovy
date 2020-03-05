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

    def post(url, body) {
        mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestMapper.asJsonString(body)
                        )
        )
    }

    def put(url, body) {
        mockMvc.perform(
                put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestMapper.asJsonString(body)
                        )
        )
    }

    def patch(url, body) {
        mockMvc.perform(
                patch(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestMapper.asJsonString(body)
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
