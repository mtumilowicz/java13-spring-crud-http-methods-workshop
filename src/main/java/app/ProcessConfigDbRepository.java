package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProcessConfigDbRepository implements ProcessConfigRepository {

    @Autowired
    ProcessConfigJpaRepository repository;

    @Override
    public Optional<ProcessConfig> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public ProcessConfig save(ProcessConfig pc) {
        return repository.save(pc);
    }
}
