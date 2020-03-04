package app.gateway;

import app.domain.ProcessConfig;
import app.domain.ProcessConfigService;
import app.gateway.input.ProcessConfigCreationInput;
import app.gateway.input.ProcessConfigPartialUpdateInput;
import app.gateway.input.ProcessConfigUpdateInput;
import app.gateway.output.ProcessConfigOutput;
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
    public ResponseEntity<ProcessConfigOutput> findById(@PathVariable String id) {
        return ResponseEntityBuilder.found(processConfigService.findById(id), ProcessConfigOutput::from);
    }

    @PostMapping
    public ResponseEntity<ProcessConfigOutput> create(@RequestBody ProcessConfigCreationInput creationInput) {
        return ResponseEntityBuilder.ok(processConfigService.create(creationInput.getProps()), ProcessConfigOutput::from);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProcessConfigOutput> patch(@RequestBody ProcessConfigPartialUpdateInput partialUpdateInput, @PathVariable String id) {
        return ResponseEntityBuilder.ok(processConfigService.partialUpdate(partialUpdateInput.getProps(), id), ProcessConfigOutput::from);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcessConfigOutput> put(@RequestBody ProcessConfigUpdateInput updateInput, @PathVariable String id) {
        return ResponseEntityBuilder.ok(processConfigService.createOrUpdate(updateInput.getProps(), id), ProcessConfigOutput::from);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    ResponseEntity<?> collectionOptions() {
        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS, HttpMethod.PATCH)
                .build();
    }
}
