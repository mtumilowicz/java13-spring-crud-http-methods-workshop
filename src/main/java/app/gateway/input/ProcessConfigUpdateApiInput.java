package app.gateway.input;

import lombok.Getter;

import java.util.Map;

@Getter
public class ProcessConfigUpdateApiInput {
    Map<String, String> props;
}
