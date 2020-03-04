package app;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
public class ProcessConfig {
    String id;
    Map<String, String> properties;

    public ProcessConfig putAll(Map<String, String> props) {
        properties.putAll(props);

        return this;
    }

    public ProcessConfig setProperties(Map<String, String> props) {
        properties = props;

        return this;
    }

    public ProcessConfig setId(String id) {
        this.id = id;

        return this;
    }
}
