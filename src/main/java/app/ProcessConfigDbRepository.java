package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProcessConfigDbRepository implements ProcessConfigRepository {

    @Autowired
    ProcessConfigJpaRepository repository;

    @Override
    public Optional<ProcessConfigEntity> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public ProcessConfigEntity save(ProcessConfigEntity pc) {
        return repository.save(pc);
    }
}
