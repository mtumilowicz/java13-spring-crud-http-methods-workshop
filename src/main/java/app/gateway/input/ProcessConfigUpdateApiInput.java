package app.gateway.input;

import app.domain.ProcessConfigUpdateInput;
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
public class ProcessConfigUpdateApiInput {
    String id;
    Map<String, String> props;

    public ProcessConfigUpdateInput toDomain(String id) {
        return ProcessConfigUpdateInput.builder()
                .id(id)
                .props(ImmutableMap.copyOf(props))
                .build();
    }
}
