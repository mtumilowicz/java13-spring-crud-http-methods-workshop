package app.gateway;

import app.domain.ProcessConfigServiceWorkshop;
import app.gateway.input.NewProcessConfigApiInput;
import app.gateway.input.ReplaceProcessConfigApiInput;
import app.gateway.input.UpdateProcessConfigApiInput;
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
    public ResponseEntity<ProcessConfigApiOutput> create(@RequestBody NewProcessConfigApiInput creationInput, UriComponentsBuilder builder) {
        // ResponseEntityBuilder.created, ProcessConfigApiOutput::from, URI: workshop/{id}
        return null;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProcessConfigApiOutput> update(@RequestBody UpdateProcessConfigApiInput updateInput, @PathVariable String id) {
        // ResponseEntityBuilder.okOrNotFound, service, ProcessConfigApiOutput::from
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcessConfigApiOutput> replace(@RequestBody ReplaceProcessConfigApiInput replaceInput, @PathVariable String id) {
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
