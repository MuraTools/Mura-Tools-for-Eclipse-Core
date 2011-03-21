/**
 * 
 */
package com.muratools.mura;

/**
 * @author steve
 *
 */
public class IniSetting {
	
	private String section;
	private String name;
	private String value;
	
	
	/**
	 * @param section
	 * @param name
	 * @param value
	 */
	public IniSetting(String section, String name, String value){
		setSection(section);
		setName(name);
		setValue(value);
	}
	
	
	/**
	 * @param section
	 * @param name
	 * @param value
	 */
	public IniSetting(String section, String name, boolean value){
		setSection(section);
		setName(name);
		setValue(new Boolean(value).toString());
	}
	
	
	/**
	 * @param section
	 * @param name
	 * @param value
	 */
	public IniSetting(String section, String name, int value){
		setSection(section);
		setName(name);
		setValue(Integer.toString(value));
	}
	
	/**
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * @return the setting
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}


	/**
	 * @param section the section to set
	 */
	private void setSection(String section) {
		this.section = section;
	}


	/**
	 * @param setting the setting to set
	 */
	private void setName(String name) {
		this.name = name;
	}


	/**
	 * @param value the value to set
	 */
	private void setValue(String value) {
		this.value = value;
	}

}
