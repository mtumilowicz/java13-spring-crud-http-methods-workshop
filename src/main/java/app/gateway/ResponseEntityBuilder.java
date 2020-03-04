package app.gateway;

import org.springframework.http.ResponseEntity;

import java.util.function.Function;

public class ResponseEntityBuilder {
    public static <T, Output> ResponseEntity<Output> ok(T domainObject, Function<T, Output> mapper) {
        return ResponseEntity.ok(mapper.apply(domainObject));
    }
}
