package app.domain;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ProcessConfigService {

    private final ProcessConfigRepository processConfigRepository;

    public Optional<ProcessConfig> findById(String id) {
        return processConfigRepository.findById(id);
    }

    public Optional<ProcessConfig> replace(ReplaceProcessConfigCommand command) {
        return findById(command.getId())
                .map(drop -> ProcessConfig.createFrom(command))
                .map(this::save);
    }

    public Optional<ProcessConfig> update(UpdateProcessConfigCommand command) {
        return findById(command.getId())
                .map(config -> config.updateFrom(command))
                .map(this::save);
    }

    public ProcessConfig create(NewProcessConfigCommand command) {
        return save(ProcessConfig.createFrom(command));
    }

    public Optional<String> deleteById(String id) {
        return existsById(id).flatMap(processConfigRepository::deleteById);
    }

    public Optional<String> existsById(String id) {
        return processConfigRepository.existsById(id);
    }

    private ProcessConfig save(ProcessConfig processConfig) {
        return processConfigRepository.save(processConfig);
    }
}
