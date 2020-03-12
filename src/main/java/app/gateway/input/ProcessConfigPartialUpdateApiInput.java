package app.gateway.input;

import app.domain.ProcessConfigCreationInput;
import app.domain.ProcessConfigPartialUpdateInput;
import com.google.common.collect.ImmutableMap;
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
    String id;
    Map<String, String> props;

    public ProcessConfigPartialUpdateInput toDomain(String id) {
        return ProcessConfigPartialUpdateInput.builder()
                .id(id)
                .props(ImmutableMap.copyOf(props))
                .build();
    }
}
