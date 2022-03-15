package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    public <T> T execute(String query, SqlExecute<T> sqlExecute) {
        try (final Connection connection = connectionFactory.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            return sqlExecute.execute(prepareStatement);
        } catch (SQLException exception) {
            throw new StorageException(exception);
        }
    }
}
