package app.gateway;

import app.domain.ProcessConfigServiceWorkshop;
import app.gateway.input.ProcessConfigCreationApiInput;
import app.gateway.input.ProcessConfigReplaceApiInput;
import app.gateway.input.ProcessConfigUpdateApiInput;
import app.gateway.output.ProcessConfigApiOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("workshop")
public class ProcessConfigControllerWorkshop {

    @Autowired
    ProcessConfigServiceWorkshop processConfigService;

    @GetMapping("/{id}")
    public ResponseEntity<ProcessConfigApiOutput> findById(@PathVariable String id) {
        // ResponseEntityBuilder.okOrNotFound, service, ProcessConfigApiOutput::from
        return null;
    }

    @PostMapping
    public ResponseEntity<ProcessConfigApiOutput> create(@RequestBody ProcessConfigCreationApiInput creationInput, UriComponentsBuilder builder) {
        // ResponseEntityBuilder.created, ProcessConfigApiOutput::from, URI: workshop/{id}
        return null;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProcessConfigApiOutput> update(@RequestBody ProcessConfigUpdateApiInput updateInput, @PathVariable String id) {
        // ResponseEntityBuilder.okOrNotFound, service, ProcessConfigApiOutput::from
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcessConfigApiOutput> replace(@RequestBody ProcessConfigReplaceApiInput replaceInput, @PathVariable String id) {
        // ResponseEntityBuilder.okOrNotFound, service, ProcessConfigApiOutput::from
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        // ResponseEntityBuilder.okOrNotFound, service, Function.identity()
        return null;
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    ResponseEntity<?> options() {
        // allow: all supported methods
        return null;
    }
}
