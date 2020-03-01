package app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ProcessConfigRepository extends JpaRepository<ProcessConfig, String> {
}
