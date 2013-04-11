package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO extends BaseDAO{

	private static AccountDAO instance = new AccountDAO();
	public static AccountDAO getInstance(){
		return instance;
	}
	
	private AccountDAO(){}

	// Register user, 
	public void Register(String userName, String password, String confirmPassword) throws Exception{
		
		if(!password.equals(confirmPassword)){
			throw new Exception("Confrim password is not same as password.");
		}
		
		Connection conn = getConnection();	
		try {

			// get questions.
			PreparedStatement ps = conn.prepareStatement("insert into User(userName, password) values(?, ?)");
			ps.setString(1, userName);
			ps.setString(2, password);
			ps.executeUpdate();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw sqle;
		} finally {
			closeConnection(conn);
		}		
	}
	
	// Return userId if login successfully, otherwise -1.
	public int Login(String userName, String password) throws Exception{
		
		Connection conn = getConnection();	
		try {
			int userId = -1;
			
			// get questions.
			PreparedStatement ps = conn
					.prepareStatement("SELECT userId, password FROM User where userName = ?");
			ps.setString(1, userName);
			
			ResultSet result = ps.executeQuery();
			while(result.next()){
				if(password.equals(result.getString("password"))){
					userId = result.getInt("userId");
				}
			}

			return userId;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw sqle;
		} finally {
			closeConnection(conn);
		}
	}
}
