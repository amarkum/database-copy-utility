package transaction.replicator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class YAMLConfig {

    private String sourceName;
    private String sourceDatabaseType;
    private String sourceConnectionUrl;
    private String sourceUsername;
    private String sourcePassword;

    public String getSourceTableName() {
        return sourceTableName;
    }

    public void setSourceTableName(String sourceTableName) {
        this.sourceTableName = sourceTableName;
    }

    public String getBatchIdentifierColumn() {
        return batchIdentifierColumn;
    }

    public void setBatchIdentifierColumn(String batchIdentifierColumn) {
        this.batchIdentifierColumn = batchIdentifierColumn;
    }

    private String sourceTableName;
    private String batchIdentifierColumn;
    private List<TargetDBMap> target = new ArrayList<>();

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceDatabaseType() {
        return sourceDatabaseType;
    }

    public void setSourceDatabaseType(String sourceDatabaseType) {
        this.sourceDatabaseType = sourceDatabaseType;
    }

    public String getSourceConnectionUrl() {
        return sourceConnectionUrl;
    }

    public void setSourceConnectionUrl(String sourceConnectionUrl) {
        this.sourceConnectionUrl = sourceConnectionUrl;
    }

    public String getSourceUsername() {
        return sourceUsername;
    }

    public void setSourceUsername(String sourceUsername) {
        this.sourceUsername = sourceUsername;
    }

    public String getSourcePassword() {
        return sourcePassword;
    }

    public void setSourcePassword(String sourcePassword) {
        this.sourcePassword = sourcePassword;
    }

    public List<TargetDBMap> getTarget() {
        return target;
    }

    public void setTarget(List<TargetDBMap> target) {
        this.target = target;
    }
}