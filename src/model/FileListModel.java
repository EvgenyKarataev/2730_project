package model;

import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

public class FileListModel {
	public static final int FILE_TYPE_IMAGE = 1;
	public static final int FILE_TYPE_VIDEO = 2;
	public static final int FILE_TYPE_CODE = 3;
	public static final int FILE_TYPE_OTHER = 9;

	private String filename;
	private int fileType;

	public int getFileType() {
		return fileType;
	}

	public void setFilename(String filename) {
		this.filename = filename;
		
		 String extension = FilenameUtils.getExtension(filename);       
         if (Pattern.matches("(?i)(jpg|png|jpeg|tiff|bmp|gif)", extension)) fileType = FILE_TYPE_IMAGE;
         else if (Pattern.matches("(?i)(mpg|mpeg|mp4|avi|wmv|ogg)", extension)) fileType = FILE_TYPE_VIDEO;
         else if (Pattern.matches("(?i)(java|py|cpp|c|js)", extension)) fileType = FILE_TYPE_CODE;
         else fileType = FILE_TYPE_OTHER;
	}

	public String getFilename() {
		return filename;
	}
}
