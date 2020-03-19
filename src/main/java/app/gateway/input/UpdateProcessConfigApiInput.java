package app.gateway.input;

import app.domain.UpdateProcessConfigCommand;
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
public class UpdateProcessConfigApiInput {
    String id;
    Map<String, String> props;

    public UpdateProcessConfigCommand toDomain(String id) {
        return UpdateProcessConfigCommand.builder()
                .id(id)
                .props(ImmutableMap.copyOf(props))
                .build();
    }
}
