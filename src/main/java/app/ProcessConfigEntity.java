package app;

import lombok.Data;
import org.apache.tomcat.jni.Proc;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Data
public class ProcessConfigEntity {
    @Id
    private String id = UUID.randomUUID().toString();

    @ElementCollection
    @CollectionTable(name = "process_config_properties",
            joinColumns = {@JoinColumn(name = "process_config_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    private Map<String, String> properties = new HashMap<>();

    public static ProcessConfigEntity from(ProcessConfig processConfig) {
        ProcessConfigEntity processConfigEntity = new ProcessConfigEntity();
        processConfigEntity.id = processConfig.id;
        processConfigEntity.properties = processConfig.properties;

        return processConfigEntity;
    }

    public ProcessConfig toDomain() {
        ProcessConfig processConfig = new ProcessConfig();
        processConfig.id = id;
        processConfig.properties = properties;

        return processConfig;
    }

    public ProcessConfigEntity putAll(Map<String, String> props) {
        properties.putAll(props);

        return this;
    }

    public ProcessConfigEntity setProperties(Map<String, String> props) {
        properties = props;

        return this;
    }

    public ProcessConfigEntity setId(String id) {
        this.id = id;

        return this;
    }
}
