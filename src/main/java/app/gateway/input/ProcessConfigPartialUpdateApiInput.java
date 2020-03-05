package app.gateway.input;

import lombok.Getter;

import java.util.Map;

@Getter
public class ProcessConfigPartialUpdateApiInput {
    Map<String, String> props;
}
