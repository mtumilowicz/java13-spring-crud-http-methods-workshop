package app.infrastructure.configuration;

import app.domain.ProcessConfigRepository;
import app.domain.ProcessConfigService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessConfigConfiguration {

    @Bean
    public ProcessConfigService processConfigService(ProcessConfigRepository processConfigRepository) {
        return new ProcessConfigService(processConfigRepository);
    }
}
