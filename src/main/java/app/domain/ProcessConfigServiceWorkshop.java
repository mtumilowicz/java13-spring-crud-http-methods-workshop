package app.domain;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ProcessConfigServiceWorkshop {

    private final ProcessConfigRepository processConfigRepository;

    public Optional<ProcessConfig> findById(String id) {
        // processConfigRepository
        return null;
    }

    public Optional<ProcessConfig> replace(ReplaceProcessConfigCommand command) {
        // findById
        // create
        // save
        return null;
    }

    public Optional<ProcessConfig> update(UpdateProcessConfigCommand command) {
        // findById
        // update existing entity
        // save
        return null;
    }

    public ProcessConfig create(NewProcessConfigCommand command) {
        // create
        // save
        return null;
    }

    public Optional<String> deleteById(String id) {
        // if exists - delete, otherwise empty
        return null;
    }

    public Optional<String> existsById(String id) {
        // processConfigRepository
        return null;
    }
}
