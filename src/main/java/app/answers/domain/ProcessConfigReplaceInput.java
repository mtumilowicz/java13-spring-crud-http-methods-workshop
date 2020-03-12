package app.answers.domain;

import com.google.common.collect.ImmutableMap;
import lombok.Builder;

@Builder
public class ProcessConfigReplaceInput {
    String id;
    ImmutableMap<String, String> props;
}
