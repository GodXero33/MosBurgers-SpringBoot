package edu.icet.ecom.util;

import java.sql.*;

public class CrudUtil {
	private CrudUtil () {}

	@SuppressWarnings("unchecked")
	public static <T> T execute (final String query, Object ...binds) throws SQLException {
		final PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(query);
		final int dataLength = binds.length;

		for (int a = 0; a < dataLength; a++) {
			final Object data = binds[a];

			if (data == null) {
				preparedStatement.setNull(a + 1, Types.NULL);
			} else {
				preparedStatement.setObject(a + 1, data);
			}
		}

		if (query.matches("(?i)^select.*")) return (T) preparedStatement.executeQuery();

		return (T) ((Integer) preparedStatement.executeUpdate());
	}

	public static int executeWithGeneratedKeys (String query, Object... binds) throws SQLException {
		final PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		final int dataLength = binds.length;

		for (int a = 0; a < dataLength; a++) {
			final Object data = binds[a];

			if (data == null) {
				preparedStatement.setNull(a + 1, Types.NULL);
			} else {
				preparedStatement.setObject(a + 1, data);
			}
		}

		final int affectedRows = preparedStatement.executeUpdate();

		if (affectedRows == 0) throw new SQLException("No rows affected.");

		final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

		if (!generatedKeys.next()) throw new SQLException("No ID obtained.");

		return generatedKeys.getInt(1);
	}
}
