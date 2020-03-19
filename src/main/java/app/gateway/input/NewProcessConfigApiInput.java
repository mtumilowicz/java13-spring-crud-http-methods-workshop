package app.gateway.input;

import app.domain.NewProcessConfigCommand;
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
public class NewProcessConfigApiInput {
    Map<String, String> props;

    public NewProcessConfigCommand toDomain() {
        return NewProcessConfigCommand.builder()
                .props(ImmutableMap.copyOf(props))
                .build();
    }
}
