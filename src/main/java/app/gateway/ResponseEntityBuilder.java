package app.gateway;

import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.function.Function;

public class ResponseEntityBuilder {
    public static <T, Output> ResponseEntity<Output> okOrNotFound(Optional<T> domainObject, Function<T, Output> mapper) {
        return domainObject
                .map(mapper::apply)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public static <T, Output> ResponseEntity<Output> ok(T domainObject, Function<T, Output> mapper) {
        return ResponseEntity.ok(mapper.apply(domainObject));
    }
}
