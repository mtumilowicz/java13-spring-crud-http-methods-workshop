package app.answers.domain;

import com.google.common.collect.ImmutableMap;
import lombok.Builder;

@Builder
public class ProcessConfigCreationInput {
    ImmutableMap<String, String> props;
}
