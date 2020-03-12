package app.infrastructure.configuration;

import app.domain.ProcessConfigRepository;
import app.domain.ProcessConfigServiceWorkshop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessConfigConfigurationWorkshop {

    @Bean
    public ProcessConfigServiceWorkshop processConfigServiceWorkshop(ProcessConfigRepository processConfigRepository) {
        return new ProcessConfigServiceWorkshop(processConfigRepository);
    }
}
