package app.gateway;

import app.domain.ProcessConfigServiceWorkshop;
import app.gateway.input.ProcessConfigCreationApiInput;
import app.gateway.input.ProcessConfigUpdateApiInput;
import app.gateway.input.ProcessConfigReplaceApiInput;
import app.gateway.output.ProcessConfigApiOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("workshop")
public class ProcessConfigControllerWorkshop {

    @Autowired
    ProcessConfigServiceWorkshop processConfigService;

    @GetMapping("/{id}")
    public ResponseEntity<ProcessConfigApiOutput> findById(@PathVariable String id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<ProcessConfigApiOutput> create(@RequestBody ProcessConfigCreationApiInput creationInput) {
        return null;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProcessConfigApiOutput> partialUpdate(@RequestBody ProcessConfigUpdateApiInput partialUpdateInput, @PathVariable String id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcessConfigApiOutput> replace(@RequestBody ProcessConfigReplaceApiInput updateInput, @PathVariable String id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        return null;
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    ResponseEntity<?> options() {
        return null;
    }
}
