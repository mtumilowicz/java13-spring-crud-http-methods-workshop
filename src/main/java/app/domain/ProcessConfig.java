package app.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.With;

import java.util.Map;

@Getter
@Builder
@With
public class ProcessConfig {
    String id;
    Map<String, String> properties;

    public ProcessConfig putAll(Map<String, String> props) {
        properties.putAll(props);

        return this;
    }
}
