package app.domain;

import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
public class ProcessConfigReplaceInput {
    String id;
    ImmutableMap<String, String> props;
}
