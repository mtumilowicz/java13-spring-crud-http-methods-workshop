package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

@Service
public class ProcessConfigService {
    @Autowired
    ProcessConfigRepository processConfigRepository;

    public Optional<ProcessConfig> findById(String id) {
        return processConfigRepository.findById(id);
    }

    @Transactional
    public ProcessConfig put(Map<String, String> props, String id) {
        var pc = new ProcessConfig();
        pc.setProperties(props);
        pc.setId(id);

        processConfigRepository.delete(pc);
        return processConfigRepository.save(pc);
    }

    public ProcessConfig patch(Map<String, String> props, String id) {
        processConfigRepository.findById(id)
                .map(config -> config.merge(props))
                .ifPresent(processConfigRepository::save);

        return processConfigRepository.findById(id).orElse(null);
    }

    public ProcessConfig create(Map<String, String> props) {
        ProcessConfig processConfig = new ProcessConfig();
        processConfig.setProperties(props);
        return processConfigRepository.save(processConfig);
    }
}
