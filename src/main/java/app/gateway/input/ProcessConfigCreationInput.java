package app.gateway.input;

import lombok.Getter;

import java.util.Map;

@Getter
public class ProcessConfigCreationInput {
    Map<String, String> props;
}
