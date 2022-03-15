package com.urise.webapp.storage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlExecute<T> {
    T execute(PreparedStatement preparedStatement) throws SQLException;
}
