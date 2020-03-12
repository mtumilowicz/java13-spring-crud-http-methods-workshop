package app.gateway.input;

import app.domain.ProcessConfigUpdateInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessConfigUpdateApiInput {
    Map<String, String> props;

    public ProcessConfigUpdateInput toDomain(String id) {
        return ProcessConfigUpdateInput.builder()
                .id(id)
                .props(props)
                .build();
    }
}
