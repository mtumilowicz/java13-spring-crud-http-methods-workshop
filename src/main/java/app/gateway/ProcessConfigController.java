package app.gateway;

import app.domain.ProcessConfig;
import app.domain.ProcessConfigService;
import app.gateway.input.ProcessConfigCreationInput;
import app.gateway.input.ProcessConfigPartialUpdateInput;
import app.gateway.input.ProcessConfigUpdateInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("app")
public class ProcessConfigController {

    @Autowired
    ProcessConfigService processConfigService;

    @GetMapping("/{id}")
    public ResponseEntity<ProcessConfig> findById(@PathVariable String id) {
        return processConfigService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public String create(@RequestBody ProcessConfigCreationInput creationInput) {
        return processConfigService.create(creationInput.getProps()).getId();
    }

    @PatchMapping("/{id}")
    public String patch(@RequestBody ProcessConfigPartialUpdateInput partialUpdateInput, @PathVariable String id) {
        return String.valueOf(processConfigService.partialUpdate(partialUpdateInput.getProps(), id));
    }

    @PutMapping("/{id}")
    public String put(@RequestBody ProcessConfigUpdateInput updateInput, @PathVariable String id) {
        return String.valueOf(processConfigService.createOrUpdate(updateInput.getProps(), id));
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    ResponseEntity<?> collectionOptions() {
        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS, HttpMethod.PATCH)
                .build();
    }
}
