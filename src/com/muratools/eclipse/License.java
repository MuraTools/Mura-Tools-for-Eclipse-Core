/**
 * 
 */
package com.muratools.eclipse;

/**
 * @author Steve
 *
 */
public class License {
	
	private String text = "";
	private boolean isStandAloneFile = false;
	
	public boolean isStandAloneFile() {
		return isStandAloneFile;
	}

	public void setStandAloneFile(boolean isStandAloneFile) {
		this.isStandAloneFile = isStandAloneFile;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public License(String text){
		setText(text);
	}
	
	public License(){
		
	}
	
}
