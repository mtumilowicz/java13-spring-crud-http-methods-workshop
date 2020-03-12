package app.gateway;

import app.gateway.input.ProcessConfigCreationApiInput;
import app.gateway.input.ProcessConfigPartialUpdateApiInput;
import app.gateway.input.ProcessConfigReplaceApiInput;
import app.gateway.output.ProcessConfigApiOutput;
import app.domain.ProcessConfigServiceWorkshop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.function.Function;

@RestController
@RequestMapping("app/workshop")
public class ProcessConfigControllerWorkshop {

    @Autowired
    ProcessConfigServiceWorkshop processConfigService;

    @GetMapping("/{id}")
    public ResponseEntity<ProcessConfigApiOutput> findById(@PathVariable String id) {
        return ResponseEntityBuilder.okOrNotFound(processConfigService.findById(id), ProcessConfigApiOutput::from);
    }

    @PostMapping
    public ResponseEntity<ProcessConfigApiOutput> create(@RequestBody ProcessConfigCreationApiInput creationInput) {
        return ResponseEntityBuilder.ok(processConfigService.create(creationInput.toDomain()), ProcessConfigApiOutput::from);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProcessConfigApiOutput> partialUpdate(@RequestBody ProcessConfigPartialUpdateApiInput partialUpdateInput, @PathVariable String id) {
        return ResponseEntityBuilder.okOrNotFound(processConfigService.partialUpdate(partialUpdateInput.toDomain(id)), ProcessConfigApiOutput::from);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcessConfigApiOutput> replace(@RequestBody ProcessConfigReplaceApiInput updateInput, @PathVariable String id) {
        return ResponseEntityBuilder.okOrNotFound(processConfigService.replace(updateInput.toDomain(id)), ProcessConfigApiOutput::from);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        return ResponseEntityBuilder.okOrNotFound(processConfigService.deleteById(id), Function.identity());
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    ResponseEntity<?> options() {
        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET,
                        HttpMethod.POST,
                        HttpMethod.PATCH,
                        HttpMethod.PUT,
                        HttpMethod.DELETE,
                        HttpMethod.OPTIONS)
                .build();
    }
}