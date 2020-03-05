package app.gateway.input;

import lombok.Getter;

import java.util.Map;

@Getter
public class ProcessConfigCreationApiInput {
    Map<String, String> props;
}
