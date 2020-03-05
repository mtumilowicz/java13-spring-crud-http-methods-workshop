package app.gateway.input;

import app.domain.ProcessConfigCreationInput;
import lombok.Getter;

import java.util.Map;

@Getter
public class ProcessConfigCreationApiInput {
    Map<String, String> props;

    public ProcessConfigCreationInput toDomain() {
        return ProcessConfigCreationInput.builder()
                .props(props)
                .build();
    }
}
