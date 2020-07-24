package transaction.replicator.config;

/**
 * POJO Class used to Map different attributes of the the Target Database Information from YAML
 */
public class TargetDBMap {

    String targetDatabaseType;
    String targetConnectionUrl;
    String targetTableName;
    String targetUsername;
    String targetPassword;
    String targetName;

    public String getTargetDatabaseType() {
        return targetDatabaseType;
    }

    public void setTargetDatabaseType(String targetDatabaseType) {
        this.targetDatabaseType = targetDatabaseType;
    }

    public String getTargetConnectionUrl() {
        return targetConnectionUrl;
    }

    public void setTargetConnectionUrl(String targetConnectionUrl) {
        this.targetConnectionUrl = targetConnectionUrl;
    }

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public String getTargetUsername() {
        return targetUsername;
    }

    public void setTargetUsername(String targetUsername) {
        this.targetUsername = targetUsername;
    }

    public String getTargetPassword() {
        return targetPassword;
    }

    public void setTargetPassword(String targetPassword) {
        this.targetPassword = targetPassword;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }
}
