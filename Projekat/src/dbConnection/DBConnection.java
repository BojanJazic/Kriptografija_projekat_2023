package dbConnection;

import java.sql.*;

public class DBConnection {

	public static Connection conn = null;

	public static Connection getConnection() {
		String url = "jdbc:mysql://localhost:3306/db_projekat_krz?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String username = "root";
		String password = "student";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return conn;
	}

	public static void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
