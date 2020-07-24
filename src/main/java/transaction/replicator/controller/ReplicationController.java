package transaction.replicator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import transaction.replicator.config.TargetDBMap;
import transaction.replicator.config.databases.DriversMapping;
import transaction.replicator.config.databases.JDBCConnection;
import transaction.replicator.config.YAMLConfig;
import transaction.replicator.services.ReplicationService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Facilitates the replication of Data from a source database to multiple target database(s)
 */
@RestController
public class ReplicationController {

    @Autowired
    private YAMLConfig myConfig;
    private ReplicationService replicationService;

    @RequestMapping("/api/v1/replicate/{batchId}")
    public boolean replicateData(@PathVariable("batchId") int batchId) throws SQLException {
        replicationService = new ReplicationService();
        Connection connection = JDBCConnection.getConnection(myConfig.getSourceConnectionUrl(),
                myConfig.getSourceUsername(),
                myConfig.getSourcePassword(), DriversMapping.MYSQL.getDriverClass());

        //TO-DO SWITCH STATEMENTS - To Call out Proper Driver class based on myConfig.getSourceDatabaseType()
        for (TargetDBMap target: myConfig.getTarget()) {
            String sqlStatement = "SELECT * from " + myConfig.getSourceTableName() +
                    " WHERE " + myConfig.getBatchIdentifierColumn() + " = " + batchId;

            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            Connection targetConnection = JDBCConnection.getConnection(target.getTargetConnectionUrl(),
                    target.getTargetUsername(),
                    target.getTargetPassword(), DriversMapping.MYSQL.getDriverClass());
            replicateToTarget(target.getTargetTableName(), resultSet, targetConnection);
        }
        return true;
    }

    /**
     * Replicates the data from source to target database
     * @param targetTableName
     * @param sourceResultSet
     * @param targetConnection
     * @return
     * @throws SQLException
     */
    public boolean replicateToTarget(String targetTableName, ResultSet sourceResultSet, Connection targetConnection) throws SQLException {
        ResultSetMetaData resultSetMetaData = sourceResultSet.getMetaData();
        String createSqlStatement = getCreateTableSQL(targetTableName, resultSetMetaData);
        targetConnection.prepareStatement(createSqlStatement).executeUpdate();
        copyToTarget(targetTableName,sourceResultSet, targetConnection);
        return true;
    }

    /**
     * Formulates a create table SQL statement by using metadata of source resultset.
     * @param targetTableName
     * @param resultSetMetaData
     * @return
     * @throws SQLException
     */
    public String getCreateTableSQL(String targetTableName, ResultSetMetaData resultSetMetaData) throws SQLException {
        int columnCount = resultSetMetaData.getColumnCount();
        StringBuilder createTableSql = new StringBuilder(1024);
        if (columnCount > 0) {
            createTableSql.append("CREATE TABLE ").append(targetTableName).append("(");
        }
        for (int i = 1; i <= columnCount; i++) {
            if (i > 1) createTableSql.append(", ");
            String columnName = resultSetMetaData.getColumnLabel(i);
            String columnType = resultSetMetaData.getColumnTypeName(i);

            createTableSql.append(columnName).append(" ").append(columnType);

            int precision = resultSetMetaData.getPrecision(i);
            if (precision != 0) {
                createTableSql.append("(").append(precision).append(")");
            }
        }
        return createTableSql.append(")").toString();
    }

    /**
     * Performs a bulk copy to target database by providing the resultset filtered by the batch identifier
     * @param tableName
     * @param sourceResultset
     * @param targetConnection
     * @throws SQLException
     */
    public void copyToTarget(String tableName, ResultSet sourceResultset, Connection targetConnection) throws SQLException {
            ResultSetMetaData sourceResultsetMetaData = sourceResultset.getMetaData();

            List <String> columns = new ArrayList<> ();
            for (int i = 1; i <= sourceResultsetMetaData.getColumnCount(); i++)
                columns.add(sourceResultsetMetaData.getColumnName(i));

            try (PreparedStatement insertStatement = targetConnection.prepareStatement(
                 "INSERT INTO " + tableName + " (" + columns.stream().collect(Collectors.joining(", "))+
                     ") VALUES (" + columns.stream().map(c -> "?").collect(Collectors.joining(", ")) + ")"))
            {
             while (sourceResultset.next()) {
                    for (int i = 1; i <= sourceResultsetMetaData.getColumnCount(); i++)
                        insertStatement.setObject(i, sourceResultset.getObject(i));
                    insertStatement.addBatch();
                }
                insertStatement.executeBatch();
            }
            targetConnection.close();
        }
}