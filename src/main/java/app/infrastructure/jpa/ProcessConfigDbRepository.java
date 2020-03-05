package app.infrastructure.jpa;

import app.domain.ProcessConfig;
import app.domain.ProcessConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;

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
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public ProcessConfig save(ProcessConfig pc) {
        return repository.save(ProcessConfigEntity.from(pc)).toDomain();
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}