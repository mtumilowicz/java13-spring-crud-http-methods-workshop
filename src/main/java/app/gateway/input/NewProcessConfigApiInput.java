package app.gateway.input;

import app.domain.NewProcessConfigCommand;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import java.util.Map;

@Getter
public class NewProcessConfigApiInput {
    Map<String, String> props;

    public NewProcessConfigCommand toDomain() {
        return NewProcessConfigCommand.builder()
                .props(ImmutableMap.copyOf(props))
                .build();
    }
}
