/**
 * Copyright 2011 Steve Good [steve@stevegood.org]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
