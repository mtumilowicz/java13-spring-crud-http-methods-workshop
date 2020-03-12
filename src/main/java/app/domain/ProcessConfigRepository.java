package app.domain;

import java.util.Optional;

public interface ProcessConfigRepository {
    Optional<ProcessConfig> findById(String id);

    Optional<String> deleteById(String id);

    ProcessConfig save(ProcessConfig pc);

    Optional<String> existsById(String id);
}
