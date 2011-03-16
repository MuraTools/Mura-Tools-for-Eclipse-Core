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
