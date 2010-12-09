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
