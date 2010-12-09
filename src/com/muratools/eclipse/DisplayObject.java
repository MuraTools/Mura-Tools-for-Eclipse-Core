package com.muratools.eclipse;

public class DisplayObject {
	
	private String _name;
	private String _fileName;
	
	public DisplayObject(){
		
	}
	
	public DisplayObject(String _name, String _fileName){
		setName(_name);
		setFileName(_fileName);
	}
	
	// setters
	public void setName(String _name){
		this._name = _name;
	}
	
	public void setFileName(String _fileName){
		this._fileName = _fileName.replace(".cfm", "").replace("displayObjects/", "");
	}
	
	// getters
	public String getName(){
		return this._name;
	}
	
	public String getFileName(){
		return this._fileName.replace(".cfm", "").replace("displayObjects/", "");
	}
	
}
