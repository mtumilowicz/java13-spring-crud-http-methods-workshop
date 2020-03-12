package app.infrastructure.configuration;

import app.domain.ProcessConfigRepository;
import app.domain.ProcessConfigService;
import app.domain.ProcessConfigServiceWorkshop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessConfigConfiguration {

    @Bean
    public ProcessConfigService processConfigService(ProcessConfigRepository processConfigRepository) {
        return new ProcessConfigService(processConfigRepository);
    }

    @Bean
    public ProcessConfigServiceWorkshop processConfigServiceWorkshop(ProcessConfigRepository processConfigRepository) {
        return new ProcessConfigServiceWorkshop(processConfigRepository);
    }
}
