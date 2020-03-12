package app.answers.domain;

import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.Getter;
import lombok.With;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@With
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
