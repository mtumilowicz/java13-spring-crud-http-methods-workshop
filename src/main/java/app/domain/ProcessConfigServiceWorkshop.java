package app.domain;

import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public class ProcessConfigServiceWorkshop {

    private final ProcessConfigRepository processConfigRepository;

    public Optional<ProcessConfig> findById(String id) {
        return processConfigRepository.findById(id);
    }

    @Transactional
    public Optional<ProcessConfig> replace(ProcessConfigReplaceInput configUpdateInput) {
        return findById(configUpdateInput.getId())
                .map(updateWith(configUpdateInput))
                .map(this::save);
    }

    public Optional<ProcessConfig> partialUpdate(ProcessConfigPartialUpdateInput partialUpdateInput) {
        return processConfigRepository.findById(partialUpdateInput.getId())
                .map(config -> config.putAll(partialUpdateInput.getProps()))
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

    private UnaryOperator<ProcessConfig> updateWith(ProcessConfigReplaceInput updateInput) {
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
