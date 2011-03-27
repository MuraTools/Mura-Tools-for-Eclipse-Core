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

package com.muratools.eclipse;

import java.util.ArrayList;
import java.util.Collections;

public class PluginConfig {
	
	private String _name;
	private String _version;
	private String _provider;
	private String _providerURL;
	private String _package;
	private String _category;
	private String _loadPriority;
	private ArrayList<SettingField> _settings = new ArrayList<SettingField>();
	private ArrayList<EventHandler> _eventHandlers = new ArrayList<EventHandler>();
	private String _displayObjectsLocation;
	private ArrayList<DisplayObject> _displayObjects = new ArrayList<DisplayObject>();
	private License license = new License();
	private ArrayList<GenericSetting> _customSettings = new ArrayList<GenericSetting>();
	
	public PluginConfig(){
		
	}
	
	public PluginConfig(String _name, String _version, String _provider, String _providerURL, String _package, String _category){
		setName(_name);
		setVersion(_version);
		setProvider(_provider);
		setProviderURL(_providerURL);
		setPackage(_package);
		setCategory(_category);
	}
	
	// Setters
	public void setName(String _name){
		this._name = _name;
	}
	
	public void setVersion(String _version){
		this._version = _version;
	}
	
	public void setProvider(String _provider){
		this._provider = _provider;
	}
	
	public void setProviderURL(String _providerURL){
		this._providerURL = _providerURL;
	}
	
	public void setPackage(String _package){
		this._package = _package;
	}
	
	public void setCategory(String _category){
		this._category = _category;
	}
	
	public void setSettings(ArrayList<SettingField> _settings){
		this._settings = _settings;
	}
	
	public void addSettingField(SettingField _settingField){
		this._settings.add(_settingField);
	}
	
	public void setEventHandlers(ArrayList<EventHandler> _eventHandlers){
		this._eventHandlers = _eventHandlers;
	}
	
	public void addEventHandler(EventHandler _eventHandler){
		this._eventHandlers.add(_eventHandler);
	}
	
	public void setDisplayObjects(ArrayList<DisplayObject> _displayObjects){
		this._displayObjects = _displayObjects;
	}
	
	public void addDisplayObject(DisplayObject _displayObject){
		this._displayObjects.add(_displayObject);
	}
	
	// Getters
	public String getName(){
		return this._name;
	}
	
	public String getVersion(){
		return this._version;
	}
	
	public String getProvider(){
		return this._provider;
	}
	
	public String getProviderURL(){
		return this._providerURL;
	}
	
	public String getPackage(){
		return this._package;
	}
	
	public String getCategory(){
		return this._category;
	}
	
	public ArrayList<SettingField> getSettingFields(){
		return this._settings;
	}
	
	public ArrayList<EventHandler> getEventHandlers(){
		Collections.sort(this._eventHandlers, new EventHandlerComparator());
		return this._eventHandlers;
	}
	
	public ArrayList<DisplayObject> getDisplayObjects(){
		return this._displayObjects;
	}
	
	public String getLoadPriority() {
		return _loadPriority;
	}

	public void setLoadPriority(String _loadPriority) {
		this._loadPriority = _loadPriority;
	}

	public License getLicense() {
		return license;
	}

	public void setLicense(License license) {
		this.license = license;
	}
	
	/**
	 * @return the displayObjectsLocation
	 */
	public String getDisplayObjectsLocation() {
		return _displayObjectsLocation;
	}

	/**
	 * @param displayObjectsLocation the displayObjectsLocation to set
	 */
	public void setDisplayObjectsLocation(String displayObjectsLocation) {
		this._displayObjectsLocation = displayObjectsLocation;
	}
	
	public void addCustomSetting(GenericSetting customSetting){
		_customSettings.add(customSetting);
	}
	
	public ArrayList<GenericSetting> getCustomSettings(){
		return _customSettings;
	}
}
