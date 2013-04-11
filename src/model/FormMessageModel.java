package model;

public class FormMessageModel {
	private boolean isSuccess;
	private String message;
	
	public FormMessageModel(boolean isSuccess, String message){
		this.isSuccess = isSuccess;
		this.message = message;
	}
	
	public boolean getIsSuccess(){
		return isSuccess;
	}
	
	public String getMessage(){
		return message;
	}
}
