package app.gateway.input;

import app.domain.ProcessConfigCreationInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessConfigCreationApiInput {
    Map<String, String> props;

    public ProcessConfigCreationInput toDomain() {
        return ProcessConfigCreationInput.builder()
                .props(props)
                .build();
    }
}
