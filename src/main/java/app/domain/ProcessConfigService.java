package app.domain;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public class ProcessConfigService {

    private final ProcessConfigRepository processConfigRepository;

    public Optional<ProcessConfig> findById(String id) {
        return processConfigRepository.findById(id);
    }

    public Optional<ProcessConfig> replace(ReplaceProcessConfigCommand command) {
        return findById(command.getId())
                .map(drop -> createFrom(command))
                .map(this::save);
    }

    public Optional<ProcessConfig> update(UpdateProcessConfigCommand command) {
        return findById(command.getId())
                .map(updateFrom(command))
                .map(this::save);
    }

    public ProcessConfig create(NewProcessConfigCommand command) {
        return save(createFrom(command));
    }

    public Optional<String> deleteById(String id) {
        return existsById(id).flatMap(processConfigRepository::deleteById);
    }

    public Optional<String> existsById(String id) {
        return processConfigRepository.existsById(id);
    }

    private UnaryOperator<ProcessConfig> updateFrom(UpdateProcessConfigCommand command) {
        return config -> config.withProperties(command.getProps());
    }

    private ProcessConfig createFrom(NewProcessConfigCommand command) {
        return ProcessConfig.builder()
                .properties(command.getProps())
                .build();
    }

    private ProcessConfig createFrom(ReplaceProcessConfigCommand command) {
        return ProcessConfig.builder()
                .id(command.getId())
                .properties(command.getProps())
                .build();
    }

    private ProcessConfig save(ProcessConfig processConfig) {
        return processConfigRepository.save(processConfig);
    }
}
