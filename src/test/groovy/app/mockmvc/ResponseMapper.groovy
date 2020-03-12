package app.mockmvc

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.test.web.servlet.MvcResult

class ResponseMapper {

    static ObjectMapper MAPPER = new ObjectMapper()

    static <T> T parseResponse(MvcResult result, Class<T> responseClass) {
        try {
            String contentAsString = result.getResponse().getContentAsString()
            return MAPPER.readValue(contentAsString, responseClass)
        } catch (IOException e) {
            throw new RuntimeException(e)
        }
    }
}
