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
/**
 * 
 */
package com.muratools.eclipse;

import java.util.ArrayList;

/**
 * @author steve
 *
 */
public class GenericSetting {
	
	private String name;
	private String value;
	private ArrayList<GenericSettingAttribute> attributes;
	private ArrayList<GenericSetting> settings;
	
	/**
	 * 
	 */
	public GenericSetting() {
		// Nothing to see here...
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the attributes
	 */
	public ArrayList<GenericSettingAttribute> getAttributes() {
		return attributes;
	}
	
	/**
	 * @return boolean
	 */
	public boolean hasAttributes(){
		return (attributes == null || attributes.isEmpty()) ? false : true;
	}
	
	/**
	 * @param attribute
	 */
	public void addAttribute(GenericSettingAttribute attribute){
		if (attributes == null){
			attributes = new ArrayList<GenericSettingAttribute>();
		}
		attributes.add(attribute);
	}

	/**
	 * @return the settings
	 */
	public ArrayList<GenericSetting> getSettings() {
		return settings;
	}
	
	/**
	 * @return boolean
	 */
	public boolean hasSettings(){
		return (settings == null || settings.isEmpty()) ? false : true;
	}
	
	/**
	 * @param setting
	 */
	public void addSetting(GenericSetting setting){
		if (settings == null){
			settings = new ArrayList<GenericSetting>();
		}
		settings.add(setting);
	}
}
