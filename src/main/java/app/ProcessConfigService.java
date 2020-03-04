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

    public Optional<ProcessConfigEntity> findById(String id) {
        return processConfigRepository.findById(id);
    }

    @Transactional
    public ProcessConfigEntity createOrUpdate(Map<String, String> props, String id) {
        var pc = new ProcessConfigEntity();
        pc.setProperties(props);
        pc.setId(id);

        processConfigRepository.deleteById(pc.getId());
        return processConfigRepository.save(pc);
    }

    public ProcessConfigEntity partialUpdate(Map<String, String> props, String id) {
        processConfigRepository.findById(id)
                .map(config -> config.putAll(props))
                .ifPresent(processConfigRepository::save);

        return processConfigRepository.findById(id).orElse(null);
    }

    public ProcessConfigEntity create(Map<String, String> props) {
        ProcessConfigEntity processConfig = new ProcessConfigEntity();
        processConfig.setProperties(props);
        return processConfigRepository.save(processConfig);
    }
}
