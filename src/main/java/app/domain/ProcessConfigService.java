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
        ProcessConfig processConfig = findById(id)
                .map(config -> config.withProperties(props))
                .orElse(ProcessConfig.builder()
                        .id(id)
                        .properties(props)
                        .build()
                );
        return save(processConfig);
    }

    private ProcessConfig save(ProcessConfig processConfig) {
        return processConfigRepository.save(processConfig);
    }

    public ProcessConfig partialUpdate(Map<String, String> props, String id) {
        processConfigRepository.findById(id)
                .map(config -> config.putAll(props))
                .ifPresent(processConfigRepository::save);

        return processConfigRepository.findById(id).orElse(null);
    }

    public ProcessConfig create(Map<String, String> props) {
        ProcessConfig processConfig = ProcessConfig.builder()
                .properties(props)
                .build();
        return save(processConfig);
    }

    public void deleteById(String id) {
        processConfigRepository.deleteById(id);
    }

    public boolean existsById(String id) {
        return processConfigRepository.existsById(id);
    }
}
