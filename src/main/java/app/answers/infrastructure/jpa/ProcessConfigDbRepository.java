package app.answers.infrastructure.jpa;

import app.answers.domain.ProcessConfig;
import app.answers.domain.ProcessConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProcessConfigDbRepository implements ProcessConfigRepository {

    @Autowired
    ProcessConfigJpaRepository repository;

    @Override
    public Optional<ProcessConfig> findById(String id) {
        return repository.findById(id).map(ProcessConfigEntity::toDomain);
    }

    @Override
    public Optional<String> deleteById(String id) {
        repository.deleteById(id);
        return Optional.of(id);
    }

    @Override
    public ProcessConfig save(ProcessConfig pc) {
        return repository.save(ProcessConfigEntity.from(pc)).toDomain();
    }

    @Override
    public Optional<String> existsById(String id) {
        return repository.existsById(id) ? Optional.of(id) : Optional.empty();
    }
}
