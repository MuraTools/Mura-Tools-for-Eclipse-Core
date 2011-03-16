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

public class EventHandler {
	
	private String _event;
	private String _component;
	private Boolean _persist = true;
	
	public EventHandler(){
		
	}
	
	public EventHandler(String _event, String _component, Boolean _persist){
		setEvent(_event);
		setComponent(_component);
		setPersist(_persist);
	}
	
	// setters
	public void setEvent(String _event){
		this._event = _event;
	}
	
	public void setComponent(String _component){
		this._component = _component.replace("eventHandlers.", "");
	}
	
	public void setPersist(Boolean _persist){
		if (_persist == null){
			_persist = false;
		}
		this._persist = _persist;
	}
	
	// getters
	public String getEvent(){
		return this._event;
	}
	
	public String getComponent(){
		return this._component.replace("eventHandlers.", "");
	}
	
	public Boolean getPersist(){
		return this._persist;
	}
	
}
