package app.gateway;

import app.domain.ProcessConfigService;
import app.gateway.input.ProcessConfigCreationApiInput;
import app.gateway.input.ProcessConfigPartialUpdateApiInput;
import app.gateway.input.ProcessConfigUpdateApiInput;
import app.gateway.output.ProcessConfigApiOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("app")
public class ProcessConfigController {

    @Autowired
    ProcessConfigService processConfigService;

    @GetMapping("/{id}")
    public ResponseEntity<ProcessConfigApiOutput> findById(@PathVariable String id) {
        return ResponseEntityBuilder.found(processConfigService.findById(id), ProcessConfigApiOutput::from);
    }

    @PostMapping
    public ResponseEntity<ProcessConfigApiOutput> create(@RequestBody ProcessConfigCreationApiInput creationInput) {
        return ResponseEntityBuilder.ok(processConfigService.create(creationInput.toDomain()), ProcessConfigApiOutput::from);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProcessConfigApiOutput> patch(@RequestBody ProcessConfigPartialUpdateApiInput partialUpdateInput, @PathVariable String id) {
        return ResponseEntityBuilder.ok(processConfigService.partialUpdate(partialUpdateInput.toDomain(), id), ProcessConfigApiOutput::from);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcessConfigApiOutput> put(@RequestBody ProcessConfigUpdateApiInput updateInput, @PathVariable String id) {
        return ResponseEntityBuilder.ok(processConfigService.createOrUpdate(updateInput.toDomain(), id), ProcessConfigApiOutput::from);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        if (processConfigService.existsById(id)) {
            processConfigService.deleteById(id);
            return ResponseEntity.ok(id);
        } else {
            return ResponseEntity.notFound().build();
        }
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
