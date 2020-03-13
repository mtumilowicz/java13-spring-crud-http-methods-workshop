package app.domain;

import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public class ProcessConfigService {

    private final ProcessConfigRepository processConfigRepository;

    public Optional<ProcessConfig> findById(String id) {
        return processConfigRepository.findById(id);
    }

    @Transactional
    public Optional<ProcessConfig> replace(ProcessConfigReplaceInput configUpdateInput) {
        return findById(configUpdateInput.getId())
                .map(drop -> recreateFrom(configUpdateInput))
                .map(this::save);
    }

    public Optional<ProcessConfig> update(ProcessConfigUpdateInput partialUpdateInput) {
        return processConfigRepository.findById(partialUpdateInput.getId())
                .map(updateFrom(partialUpdateInput))
                .map(processConfigRepository::save);
    }

    public ProcessConfig create(ProcessConfigCreationInput creationInput) {
        return save(createFrom(creationInput));
    }

    public Optional<String> deleteById(String id) {
        return existsById(id).flatMap(processConfigRepository::deleteById);
    }

    public Optional<String> existsById(String id) {
        return processConfigRepository.existsById(id);
    }

    private ProcessConfig recreateFrom(ProcessConfigReplaceInput updateInput) {
        return ProcessConfig.builder()
                .id(updateInput.getId())
                .properties(updateInput.getProps())
                .build();
    }

    private UnaryOperator<ProcessConfig> updateFrom(ProcessConfigUpdateInput updateInput) {
        return config -> config.withProperties(updateInput.getProps());
    }

    private ProcessConfig createFrom(ProcessConfigCreationInput creationInput) {
        return ProcessConfig.builder()
                .properties(creationInput.getProps())
                .build();
    }

    private ProcessConfig save(ProcessConfig processConfig) {
        return processConfigRepository.save(processConfig);
    }
}
