package app.gateway;

import lombok.experimental.UtilityClass;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Optional;
import java.util.function.Function;

@UtilityClass
class ResponseEntityBuilder {

    <T, Output> ResponseEntity<Output> okOrNotFound(Optional<T> domainObject, Function<T, Output> mapper) {
        return domainObject
                .map(mapper)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    <T, Output> ResponseEntity<Output> created(T domainObject, Function<T, Output> mapper, Function<T, URI> uriMapper) {
        var uri = uriMapper.apply(domainObject);
        var body = mapper.apply(domainObject);
        return ResponseEntity.created(uri)
                .body(body);
    }
}
