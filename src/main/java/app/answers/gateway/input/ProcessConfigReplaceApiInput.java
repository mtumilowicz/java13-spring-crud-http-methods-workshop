package app.answers.gateway.input;

import app.answers.domain.ProcessConfigReplaceInput;
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
public class ProcessConfigReplaceApiInput {
    Map<String, String> props;

    public ProcessConfigReplaceInput toDomain(String id) {
        return ProcessConfigReplaceInput.builder()
                .id(id)
                .props(ImmutableMap.copyOf(props))
                .build();
    }
}
