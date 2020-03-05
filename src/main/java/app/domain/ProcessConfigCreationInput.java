package app.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
public class ProcessConfigCreationInput {
    Map<String, String> props;
}
