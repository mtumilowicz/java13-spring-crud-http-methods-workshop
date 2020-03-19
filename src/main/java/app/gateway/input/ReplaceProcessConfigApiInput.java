package app.gateway.input;

import app.domain.ReplaceProcessConfigCommand;
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
public class ReplaceProcessConfigApiInput {
    Map<String, String> props;

    public ReplaceProcessConfigCommand toDomain(String id) {
        return ReplaceProcessConfigCommand.builder()
                .id(id)
                .props(ImmutableMap.copyOf(props))
                .build();
    }
}
