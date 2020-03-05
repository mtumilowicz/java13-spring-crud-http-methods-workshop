package app.functional

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
}
