package app.gateway.output;

import app.domain.ProcessConfig;
import lombok.Builder;

import java.util.Map;

@Builder
public class ProcessConfigApiOutput {
    String id;
    Map<String, String> properties;

    public static ProcessConfigApiOutput from(ProcessConfig processConfig) {
        return ProcessConfigApiOutput.builder()
                .id(processConfig.getId())
                .properties(processConfig.getProperties())
                .build();
    }
}
