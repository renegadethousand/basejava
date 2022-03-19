package com.urise.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlExecute<T> {
    T execute(PreparedStatement preparedStatement) throws SQLException;
}
