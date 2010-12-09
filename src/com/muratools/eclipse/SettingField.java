package com.muratools.eclipse;

public class SettingField {
	
	private String _name;
	private String _label;
	private String _hint;
	private String _type;
	private Boolean _required;
	private String _validation;
	private String _regEx;
	private String _message;
	private String _defaultValue;
	private String _optionList;
	private String _optionLabelList;
	
	
	public String getName() {
		return _name;
	}
	public void setName(String _name) {
		this._name = _name == null ? "" : _name;
	}
	public String getLabel() {
		return _label;
	}
	public void setLabel(String _label) {
		this._label = _label == null ? "" : _label;
	}
	public String getHint() {
		return _hint;
	}
	public void setHint(String _hint) {
		this._hint = _hint == null ? "" : _hint;
	}
	public String getType() {
		return _type;
	}
	public void setType(String _type) {
		this._type = _type == null ? "" : _type;
	}
	public Boolean getRequired() {
		return _required;
	}
	public void setRequired(Boolean _required) {
		this._required = _required == null ? new Boolean(false) : _required;
	}
	public String getValidation() {
		return _validation;
	}
	public void setValidation(String _validation) {
		this._validation = _validation == null ? "" : _validation;
	}
	public String getRegEx() {
		return _regEx;
	}
	public void setRegEx(String _regEx) {
		this._regEx = _regEx == null ? "" : _regEx;
	}
	public String getMessage() {
		return _message;
	}
	public void setMessage(String _message) {
		this._message = _message == null ? "" : _message;
	}
	public String getDefaultValue() {
		return _defaultValue;
	}
	public void setDefaultValue(String _defaultValue) {
		this._defaultValue = _defaultValue == null ? "" : _defaultValue;
	}
	public String getOptionList() {
		return _optionList;
	}
	public void setOptionList(String _optionList) {
		this._optionList = _optionList == null ? "" : _optionList;
	}
	public String getOptionLabelList() {
		return _optionLabelList;
	}
	public void setOptionLabelList(String _optionLabelList) {
		this._optionLabelList = _optionLabelList == null ? "" : _optionLabelList;
	}
	
}
