package transaction.replicator.config.databases;

/**
 * Enum which contains driver class mapping for various databases
 */
public enum DriversMapping {
    MYSQL, DB2, ORACLE;

    public String getDriverClass() {
        switch (this) {
            case MYSQL:
                return "com.mysql.jdbc.Driver";
            case DB2:
                return "com.ibm.db2.jcc.DB2Driver";
            case ORACLE:
                return "oracle.jdbc.driver.OracleDrive";
            default:
                return null;
        }
    }
}