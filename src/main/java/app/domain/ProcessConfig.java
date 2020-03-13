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
}
