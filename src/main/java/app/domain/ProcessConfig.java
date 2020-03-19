package app.domain;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import lombok.experimental.FieldDefaults;

@Builder
@With
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProcessConfig {

    String id;

    ImmutableMap<String, String> properties;

    static ProcessConfig createFrom(NewProcessConfigCommand command) {
        return ProcessConfig.builder()
                .properties(command.getProps())
                .build();
    }

    static ProcessConfig createFrom(ReplaceProcessConfigCommand command) {
        return ProcessConfig.builder()
                .id(command.getId())
                .properties(command.getProps())
                .build();
    }

    ProcessConfig updateFrom(UpdateProcessConfigCommand command) {
        return this.withProperties(command.getProps());
    }
}
