package app;

import lombok.Data;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Data
public class ProcessConfig {
    @Id
    private String id = UUID.randomUUID().toString();

    @ElementCollection
    @CollectionTable(name = "process_config_properties",
            joinColumns = {@JoinColumn(name = "process_config_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    private Map<String, String> properties = new HashMap<>();

    public ProcessConfig merge(Map<String, String> props) {
        properties.putAll(props);

        return this;
    }

    public ProcessConfig setProperties(Map<String, String> props) {
        properties = props;

        return this;
    }

    public ProcessConfig setId(String id) {
        this.id = id;

        return this;
    }
}
