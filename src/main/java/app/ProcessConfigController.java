package app;

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
    public ResponseEntity<ProcessConfigEntity> findById(@PathVariable String id) {
        return processConfigService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public String create(@RequestBody Map<String, String> props) {
        return processConfigService.create(props).getId();
    }

    @PatchMapping("/{id}")
    public String patch(@RequestBody Map<String, String> props, @PathVariable String id) {
        return String.valueOf(processConfigService.partialUpdate(props, id));
    }

    @PutMapping("/{id}")
    public String put(@RequestBody Map<String, String> props, @PathVariable String id) {
        return String.valueOf(processConfigService.createOrUpdate(props, id));
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    ResponseEntity<?> collectionOptions() {
        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS, HttpMethod.PATCH)
                .build();
    }
}
