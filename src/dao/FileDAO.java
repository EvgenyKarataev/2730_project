package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FileDAO extends BaseDAO{

	private static FileDAO instance = new FileDAO();
	public static FileDAO getInstance(){
		return instance;
	}
	
	private FileDAO(){}
			
	// Check if file is sharable.
	public boolean isFileSharable(int userId, String filename) throws Exception{
		Connection conn = getConnection();	
		try {
			// get questions.
			PreparedStatement ps = conn.prepareStatement("select * from sharable_file where userId = ? and filename = ?");
			ps.setInt(1, userId);
			ps.setString(2, filename);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())return true;
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw sqle;
		} finally {
			closeConnection(conn);			
		}		
		return false;
	}
	
	// Share a file.
	// Add a sharable record into DB.
	public void shareFile(int userId, String filename) throws Exception{
		Connection conn = getConnection();	
		try {
			// get questions.
			PreparedStatement ps = conn.prepareStatement("insert into sharable_file(userId, filename) values(?, ?)");
			ps.setInt(1, userId);
			ps.setString(2, filename);
			ps.executeUpdate();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw sqle;
		} finally {
			closeConnection(conn);
		}		
	}
} 
