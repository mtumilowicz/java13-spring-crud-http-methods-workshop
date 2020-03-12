package app.domain;

import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public class ProcessConfigService {

    private final ProcessConfigRepository processConfigRepository;

    public Optional<ProcessConfig> findById(String id) {
        return processConfigRepository.findById(id);
    }

    @Transactional
    public ProcessConfig createOrUpdate(ProcessConfigUpdateInput configUpdateInput) {
        ProcessConfig processConfig = findById(configUpdateInput.id)
                .map(updateWith(configUpdateInput))
                .orElseGet(createFrom(configUpdateInput));
        return save(processConfig);
    }

    public Optional<ProcessConfig> partialUpdate(ProcessConfigPartialUpdateInput partialUpdateInput) {
        return processConfigRepository.findById(partialUpdateInput.id)
                .map(config -> config.putAll(partialUpdateInput.props))
                .map(processConfigRepository::save);
    }

    public ProcessConfig create(ProcessConfigCreationInput creationInput) {
        return save(createFrom(creationInput));
    }

    public Optional<String> deleteById(String id) {
        if (existsById(id)) {
            processConfigRepository.deleteById(id);
            return Optional.of(id);
        } else {
            return Optional.empty();
        }
    }

    public boolean existsById(String id) {
        return processConfigRepository.existsById(id);
    }

    private UnaryOperator<ProcessConfig> updateWith(ProcessConfigUpdateInput updateInput) {
        return config -> config.withProperties(updateInput.props);
    }

    private Supplier<ProcessConfig> createFrom(ProcessConfigUpdateInput updateInput) {
        return () -> ProcessConfig.builder()
                .id(updateInput.id)
                .properties(updateInput.props)
                .build();
    }

    private ProcessConfig createFrom(ProcessConfigCreationInput creationInput) {
        return ProcessConfig.builder()
                .properties(creationInput.props)
                .build();
    }

    private ProcessConfig save(ProcessConfig processConfig) {
        return processConfigRepository.save(processConfig);
    }
}
