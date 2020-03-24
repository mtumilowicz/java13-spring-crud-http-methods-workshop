package app.mockmvc


import com.fasterxml.jackson.databind.ObjectMapper

class RequestMapper {

    static ObjectMapper MAPPER = new ObjectMapper()

    static String asJsonString(Object request) {
        MAPPER.writeValueAsString(request)
    }

}
