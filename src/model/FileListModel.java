package model;

public class FileListModel {
	public static final int FILE_TYPE_IMAGE = 1;
	public static final int FILE_TYPE_VIDEO = 2;
	public static final int FILE_TYPE_OTHER = 9;

	private String filename;
	private int fileType;

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}
	
	public int getFileType() {
		return fileType;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}
}
