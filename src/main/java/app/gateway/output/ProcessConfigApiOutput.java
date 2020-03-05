package app.gateway.output;

import app.domain.ProcessConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessConfigApiOutput {
    private String id;
    private Map<String, String> properties;

    public static ProcessConfigApiOutput from(ProcessConfig processConfig) {
        return ProcessConfigApiOutput.builder()
                .id(processConfig.getId())
                .properties(processConfig.getProperties())
                .build();
    }
}
