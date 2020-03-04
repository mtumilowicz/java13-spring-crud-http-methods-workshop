package app.gateway.input;

import lombok.Getter;

import java.util.Map;

@Getter
public class ProcessConfigUpdateInput {
    Map<String, String> props;
}
