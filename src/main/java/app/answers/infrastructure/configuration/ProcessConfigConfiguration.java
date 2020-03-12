package app.answers.infrastructure.configuration;

import app.answers.domain.ProcessConfigRepository;
import app.answers.domain.ProcessConfigService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessConfigConfiguration {

    @Bean
    public ProcessConfigService processConfigService(ProcessConfigRepository processConfigRepository) {
        return new ProcessConfigService(processConfigRepository);
    }
}
