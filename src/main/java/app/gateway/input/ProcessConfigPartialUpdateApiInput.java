package app.gateway.input;

import app.domain.ProcessConfigCreationInput;
import app.domain.ProcessConfigPartialUpdateInput;
import lombok.Getter;

import java.util.Map;

@Getter
public class ProcessConfigPartialUpdateApiInput {
    Map<String, String> props;

    public ProcessConfigPartialUpdateInput toDomain() {
        return ProcessConfigPartialUpdateInput.builder()
                .props(props)
                .build();
    }
}
