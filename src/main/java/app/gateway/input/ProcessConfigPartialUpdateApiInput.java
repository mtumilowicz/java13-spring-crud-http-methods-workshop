package app.gateway.input;

import app.domain.ProcessConfigCreationInput;
import app.domain.ProcessConfigPartialUpdateInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessConfigPartialUpdateApiInput {
    Map<String, String> props;

    public ProcessConfigPartialUpdateInput toDomain() {
        return ProcessConfigPartialUpdateInput.builder()
                .props(props)
                .build();
    }
}
