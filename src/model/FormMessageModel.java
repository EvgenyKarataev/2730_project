package model;

public class FormMessageModel {
	public boolean isSuccess;
	public String message;
	
	public FormMessageModel(boolean isSuccess, String message){
		this.isSuccess = isSuccess;
		this.message = message;
	}
}
