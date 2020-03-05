package app.domain;

import java.util.Optional;

public interface ProcessConfigRepository {
    Optional<ProcessConfig> findById(String id);

    void deleteById(String id);

    ProcessConfig save(ProcessConfig pc);

    boolean existsById(String id);
}