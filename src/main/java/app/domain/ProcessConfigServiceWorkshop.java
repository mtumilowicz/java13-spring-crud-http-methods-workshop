package app.domain;

import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
public class ProcessConfigServiceWorkshop {

    private final ProcessConfigRepository processConfigRepository;

    public Optional<ProcessConfig> findById(String id) {
        return null;
    }

    @Transactional
    public Optional<ProcessConfig> replace(ProcessConfigReplaceInput configUpdateInput) {
        return null;
    }

    public Optional<ProcessConfig> partialUpdate(ProcessConfigUpdateInput partialUpdateInput) {
        return null;
    }

    public ProcessConfig create(ProcessConfigCreationInput creationInput) {
        return null;
    }

    public Optional<String> deleteById(String id) {
        return null;
    }

    public Optional<String> existsById(String id) {
        return null;
    }
}
