package app.domain;

import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
public class ProcessConfigService {

    private final ProcessConfigRepository processConfigRepository;

    public Optional<ProcessConfig> findById(String id) {
        return processConfigRepository.findById(id);
    }

    @Transactional
    public ProcessConfig createOrUpdate(ProcessConfigUpdateInput configUpdateInput, String id) {
        ProcessConfig processConfig = findById(id)
                .map(config -> config.withProperties(configUpdateInput.props))
                .orElse(ProcessConfig.builder()
                        .id(id)
                        .properties(configUpdateInput.props)
                        .build()
                );
        return save(processConfig);
    }

    private ProcessConfig save(ProcessConfig processConfig) {
        return processConfigRepository.save(processConfig);
    }

    public Optional<ProcessConfig> partialUpdate(ProcessConfigPartialUpdateInput partialUpdateInput, String id) {
        return processConfigRepository.findById(id)
                .map(config -> config.putAll(partialUpdateInput.props))
                .map(processConfigRepository::save);
    }

    public ProcessConfig create(ProcessConfigCreationInput creationInput) {
        ProcessConfig processConfig = ProcessConfig.builder()
                .properties(creationInput.props)
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
