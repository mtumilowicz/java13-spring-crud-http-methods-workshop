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

    @GetMapping
    public String hehe() {
        return "hehe";
    }

    @PostMapping
    public String yyy(@RequestBody Map<String, String> props) {
        return processConfigService.save(props).getId();
    }

    @PatchMapping("/{id}")
    public String xxx(@RequestBody Map<String, String> props, @PathVariable String id) {
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
