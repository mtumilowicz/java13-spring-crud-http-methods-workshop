package app.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
public class ProcessConfigUpdateInput {
    Map<String, String> props;
}
