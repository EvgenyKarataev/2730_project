package dao;

import java.io.File;
import java.util.List;

public class FileDAO extends BaseDAO{

	private static FileDAO instance = new FileDAO();
	public static FileDAO getInstance(){
		return instance;
	}
	
	private FileDAO(){}
	
	// Get user's files.
	public List<File> fileList(int userId){
		return null;
	}
	
	// If user saves codes which were not saved before, create a new code file.
	public void createCodeFile(int userId, String filename, String code){
		
	}
	
	// Create any type of file.
	public void createFile(int userId, File file){
		
	}
	
	// Saves code to existing code file.
	public void editCodeFile(int userId, int fileId, String code){
		
	}
	
	// Delete a file.
	public void deleteFile(int userId, int fileId){
		
	}
	
	// Share a file.
	// Returns a file share link.
	public String shareFile(int userId, int fileId){
		return null;
	}
} 
