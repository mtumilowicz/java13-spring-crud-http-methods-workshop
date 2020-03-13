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

    public Optional<ProcessConfig> replace(ProcessConfigReplaceInput replaceInput) {
        // findById
        // recreate
        // save
        return null;
    }

    public Optional<ProcessConfig> update(ProcessConfigUpdateInput updateInput) {
        // findById
        // update existing entity
        // save
        return null;
    }

    public ProcessConfig create(ProcessConfigCreationInput creationInput) {
        // create from
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
