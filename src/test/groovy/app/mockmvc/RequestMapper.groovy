package app.mockmvc

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper

class RequestMapper {

    static ObjectMapper MAPPER = new ObjectMapper()

    static String asJsonString(Object request) {
        try {
            return MAPPER.writeValueAsString(request)
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e)
        }
    }

}
