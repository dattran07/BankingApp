package com.revature.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	private static Connection connection;

	public static Connection getConnection() throws SQLException {
		String url = System.getenv("AWS_URL");
		String username = System.getenv("AWS_Username");
		String password = System.getenv("AWS_password");

		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(url, username, password);
		}
		return connection;
	}

}
