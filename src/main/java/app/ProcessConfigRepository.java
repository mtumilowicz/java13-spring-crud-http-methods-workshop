package app;

import java.util.Optional;

public interface ProcessConfigRepository {
    Optional<ProcessConfigEntity> findById(String id);

    void deleteById(String id);

    ProcessConfigEntity save(ProcessConfigEntity pc);
}
