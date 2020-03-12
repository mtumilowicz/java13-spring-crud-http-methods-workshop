package app.domain;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;

@Builder
@With
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProcessConfig {
    String id;
    ImmutableMap<String, String> properties;

    public ProcessConfig putAll(Map<String, String> props) {
        HashMap<String, String> updatedProperties = new HashMap<>();
        updatedProperties.putAll(properties);
        updatedProperties.putAll(props);
        return withProperties(ImmutableMap.copyOf(updatedProperties));
    }
}
