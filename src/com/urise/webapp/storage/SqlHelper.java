package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlHelper {

    public final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        this.connectionFactory = new ConnectionFactory() {
            @Override
            public Connection getConnection() throws SQLException {
                return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            }
        };
    }

    public void execute(String query, String[] args) {
        try (final Connection connection = connectionFactory.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            fillStatementParam(args, prepareStatement);
            prepareStatement.execute();
        } catch (SQLException exception) {
            throw new StorageException(exception);
        }
    }

    public List<Map<String, String>> executeQuery(String query, String[] args) {
        List<Map<String, String>> result = new ArrayList<>();
        try (final Connection connection = connectionFactory.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            fillStatementParam(args, prepareStatement);
            final ResultSet resultSet = prepareStatement.executeQuery();
            final int columnCount = resultSet.getMetaData().getColumnCount();
            List<String> columns = new ArrayList<>();
            for (int i = 0; i < columnCount; i++) {
                columns.add(resultSet.getMetaData().getColumnName(i + 1));
            }
            while (resultSet.next()) {
                Map<String, String> rowResult = new HashMap<>();
                for (String column : columns) {
                    rowResult.put(column, resultSet.getString(column));
                }
                result.add(rowResult);
            }
            return result;
        } catch (SQLException exception) {
            throw new StorageException(exception);
        }
    }

    private void fillStatementParam(String[] args, PreparedStatement prepareStatement) throws SQLException {
        for (int i = 0; i < args.length; i++) {
            prepareStatement.setString(i + 1, args[i]);
        }
    }
}
