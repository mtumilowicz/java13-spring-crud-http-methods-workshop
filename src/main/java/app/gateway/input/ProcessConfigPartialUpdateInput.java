package app.gateway.input;

import lombok.Getter;

import java.util.Map;

@Getter
public class ProcessConfigPartialUpdateInput {
    Map<String, String> props;
}
