package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    public final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void execute(String query) {
        execute(query, preparedStatement -> {
            preparedStatement.execute();
            return null;
        });
    }

    public <T> T execute(String query, SqlExecute<T> sqlExecute) {
        try (final Connection connection = connectionFactory.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            return sqlExecute.execute(prepareStatement);
        } catch (SQLException exception) {
            if (exception.getSQLState().equals("23505")) {
                throw new ExistStorageException(exception);
            } else {
                throw new StorageException(exception);
            }
        }
    }
}
