package app.domain;

import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class ProcessConfigService {

    private final ProcessConfigRepository processConfigRepository;

    public Optional<ProcessConfig> findById(String id) {
        return processConfigRepository.findById(id);
    }

    @Transactional
    public ProcessConfig createOrUpdate(Map<String, String> props, String id) {
        var pc = new ProcessConfig();
        pc.setProperties(props);
        pc.setId(id);

        processConfigRepository.deleteById(id);
        return processConfigRepository.save(pc);
    }

    public ProcessConfig partialUpdate(Map<String, String> props, String id) {
        processConfigRepository.findById(id)
                .map(config -> config.putAll(props))
                .ifPresent(processConfigRepository::save);

        return processConfigRepository.findById(id).orElse(null);
    }

    public ProcessConfig create(Map<String, String> props) {
        ProcessConfig processConfig = new ProcessConfig();
        processConfig.setProperties(props);
        return processConfigRepository.save(processConfig);
    }

    public void deleteById(String id) {
        processConfigRepository.deleteById(id);
    }

    public boolean existsById(String id) {
        return processConfigRepository.existsById(id);
    }
}
