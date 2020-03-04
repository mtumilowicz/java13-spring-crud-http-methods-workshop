package app.gateway.output;

import app.domain.ProcessConfig;
import lombok.Builder;

import java.util.Map;

@Builder
public class ProcessConfigOutput {
    String id;
    Map<String, String> properties;

    public static ProcessConfigOutput from(ProcessConfig processConfig) {
        return ProcessConfigOutput.builder()
                .id(processConfig.getId())
                .properties(processConfig.getProperties())
                .build();
    }
}
