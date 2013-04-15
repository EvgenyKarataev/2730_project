package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDAO {

	private static final BaseDAO DAO = new BaseDAO();
	private static final String connURL = "jdbc:mysql://localhost:3306/2730_project";
	private static final String dbuser = "root";
	private static final String dbpassword = "root";

	public static BaseDAO getInstance() {
		return DAO;
	}

	protected BaseDAO() {
		try {
			// Class.forName("org.postgresql.Driver");
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected Connection getConnection() {

		try {
			
			Connection conn = DriverManager.getConnection(connURL, dbuser,
					dbpassword);
			return conn;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return null;
	}

	protected void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
}
