package app.gateway.input;

import app.domain.ProcessConfigUpdateInput;
import lombok.Getter;

import java.util.Map;

@Getter
public class ProcessConfigUpdateApiInput {
    Map<String, String> props;

    public ProcessConfigUpdateInput toDomain() {
        return ProcessConfigUpdateInput.builder()
                .props(props)
                .build();
    }
}
