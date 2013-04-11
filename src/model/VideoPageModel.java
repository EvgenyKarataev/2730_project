package model;

public class VideoPageModel {

	private String videoFilename;
	private int width;
	private int height;
	
	// Used on type attr of source tag.
	private String extension;
	
	public void setVideoFilename(String videoFilename){
		this.videoFilename = videoFilename;
		extension = videoFilename.substring(videoFilename.lastIndexOf(".") + 1);
	}
	
	public void setWidth(int width){
		this.width = width;
	}
	
	public void setHeight(int height){
		this.height = height;
	}
	
	public String getVideoFilename() { return videoFilename; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public String getExtension(){ return extension;}
    
}
