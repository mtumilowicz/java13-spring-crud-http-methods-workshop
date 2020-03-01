package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProcessConfigService {
    @Autowired
    ProcessConfigRepository processConfigRepository;

    public ProcessConfig update(Map<String, String> props, String id) {
        processConfigRepository.findById(id)
                .map(config -> config.merge(props))
                .ifPresent(processConfigRepository::save);

        return processConfigRepository.findById(id).orElse(null);
    }

    public ProcessConfig save(Map<String, String> props) {
        ProcessConfig processConfig = new ProcessConfig();
        processConfig.setProperties(props);
        return processConfigRepository.save(processConfig);
    }
}
