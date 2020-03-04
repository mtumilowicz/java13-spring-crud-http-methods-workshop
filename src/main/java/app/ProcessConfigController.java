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
    public ResponseEntity<ProcessConfig> hehe(@PathVariable String id) {
        return processConfigService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public String yyy(@RequestBody Map<String, String> props) {
        return processConfigService.save(props).getId();
    }

    @PatchMapping("/{id}")
    public String xxx(@RequestBody Map<String, String> props, @PathVariable String id) {
        return String.valueOf(processConfigService.patch(props, id));
    }

    @PutMapping("/{id}")
    public String xxx2(@RequestBody Map<String, String> props, @PathVariable String id) {
        return String.valueOf(processConfigService.update(props, id));
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    ResponseEntity<?> collectionOptions() {
        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS, HttpMethod.PATCH)
                .build();
    }
}
