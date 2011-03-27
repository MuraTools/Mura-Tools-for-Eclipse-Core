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
package com.muratools.eclipse.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

import com.muratools.eclipse.DisplayObject;
import com.muratools.eclipse.EventHandler;
import com.muratools.eclipse.GenericSetting;
import com.muratools.eclipse.GenericSettingAttribute;
import com.muratools.eclipse.PluginConfig;
import com.muratools.eclipse.SettingField;

/**
 * @author steve
 *
 */
public class PluginConfigUtility {
	
	private File pluginConfigFile;
	private PluginConfig pluginConfig;
	private ArrayList<String> nodeList = new ArrayList<String>();
	
	/**
	 * 
	 */
	public PluginConfigUtility(File pluginConfigFile) {
		this.pluginConfigFile = pluginConfigFile;
		setupNodeList();
	}
	
	public PluginConfig getPluginConfig(){
		if (pluginConfig == null){
			pluginConfig = new PluginConfig();
		}
		
		Document configDoc = parseConfigFile();
		if (configDoc != null){
			Element root = configDoc.getRootElement();
			parsePluginAttributes(root);
			parseSettings(root);
			parseEventHandlers(root);
			parseDisplayObjects(root);
			parseCustomElements(root);
			
		}
		
		return pluginConfig;
	}
	
	private Document parseConfigFile() {
		Builder parser = new Builder();
		Document doc = null;
		try {
			doc = parser.build(pluginConfigFile);
		}
		catch (ParsingException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	private void parsePluginAttributes(Element root){
		Element name = root.getFirstChildElement("name");
		Element pkg = root.getFirstChildElement("package");
		Element loadPriority = root.getFirstChildElement("loadPriority");
		if (loadPriority == null){
			loadPriority = findElement(root, new String[]{"LoadPriority","loadpriority","Loadpriority"});
		}
		Element version = root.getFirstChildElement("version");
		Element provider = root.getFirstChildElement("provider");
		Element providerURL = root.getFirstChildElement("providerURL");
		if (providerURL == null){
			providerURL = findElement(root, new String[]{"ProviderURL","ProviderUrl","providerUrl","providerurl"});
		}
		Element category = root.getFirstChildElement("category");
		
		pluginConfig.setName(name.getValue());
		pluginConfig.setPackage(pkg.getValue());
		pluginConfig.setLoadPriority(loadPriority.getValue());
		pluginConfig.setVersion(version.getValue());
		pluginConfig.setProvider(provider.getValue());
		pluginConfig.setProviderURL(providerURL.getValue());
		pluginConfig.setCategory(category.getValue());
	}
	
	private void parseSettings(Element root){
		Element settingsRoot = root.getFirstChildElement("settings");
		if (settingsRoot == null){
			settingsRoot = findElement(root, new String[]{"Settings"});
		}
		
		if (settingsRoot != null){
			Elements settings = settingsRoot.getChildElements();
			for (int i=0; i < settings.size(); i++){
				Element setting = settings.get(i);
				Element name = setting.getFirstChildElement("name");
				Element label = setting.getFirstChildElement("label");
				Element hint = setting.getFirstChildElement("hint");
				Element type = setting.getFirstChildElement("type");
				Element required = setting.getFirstChildElement("required");
				Element validation = setting.getFirstChildElement("validation");
				Element regex = setting.getFirstChildElement("regex");
				Element message = setting.getFirstChildElement("message");
				Element defaultValue = setting.getFirstChildElement("defaultValue");
				if (defaultValue == null){
					defaultValue = findElement(setting, new String[] {"DefaultValue","Defaultvalue","defaultvalue"});
				}
				Element optionList = setting.getFirstChildElement("optionList");
				if (optionList == null){
					optionList = findElement(setting, new String[] {"OptionList","Optionlist","optionlist"});
				}
				Element optionLabelList = setting.getFirstChildElement("optionLabelList");
				if (optionLabelList == null){
					optionLabelList = findElement(setting, new String[] {"OptionLabelList","optionlabelList","optionlabellist","Optionlabellist","OptionLabellist","OptionlabelList","optionLabellist"});
				}
				
				SettingField sf = new SettingField();
				sf.setName(name.getValue());
				sf.setLabel(label.getValue());
				sf.setHint(hint.getValue());
				sf.setType(type.getValue());
				sf.setRequired(new Boolean(required.getValue()));
				sf.setValidation(validation.getValue());
				sf.setRegEx(regex.getValue());
				sf.setMessage(message.getValue());
				sf.setDefaultValue(defaultValue.getValue());
				sf.setOptionList(optionList.getValue());
				sf.setOptionLabelList(optionLabelList.getValue());
				
				pluginConfig.addSettingField(sf);
			}
		}
	}
	
	private void parseEventHandlers(Element root){
		Element eventHandlersRoot = root.getFirstChildElement("EventHandlers");
		if (eventHandlersRoot == null){
			eventHandlersRoot = findElement(root, new String[] {"eventHandlers","eventhandlers","Eventhandlers"});
		}
		
		if (eventHandlersRoot != null){
			for (String nodeName : new String[] {"eventHandler","EventHandler","eventhandler","Eventhandler"}){
				Elements eventHandlers = eventHandlersRoot.getChildElements(nodeName);
				for (int i=0; i < eventHandlers.size(); i++){
					Element eventHandler = eventHandlers.get(i);
					String event = eventHandler.getAttributeValue("event");
					String component = eventHandler.getAttributeValue("component");
					Boolean persist = new Boolean(eventHandler.getAttributeValue("persist"));
					
					EventHandler eh = new EventHandler();
					eh.setEvent(event);
					eh.setComponent(component);
					eh.setPersist(persist);
					
					pluginConfig.addEventHandler(eh);
				}
			}
		}
	}
	
	private void parseDisplayObjects(Element root){
		// Get the location attribute from DisplayObjects
		Element displayObjectsRoot = root.getFirstChildElement("DisplayObjects");
		if (displayObjectsRoot == null){
			displayObjectsRoot = findElement(root, new String[] {"displayObjects","displayobjects","Displayobjects"});
		}
		
		if (displayObjectsRoot != null){
			if (displayObjectsRoot.getAttributeValue("location").length() > 0){
				pluginConfig.setDisplayObjectsLocation(displayObjectsRoot.getAttributeValue("location"));
			} else {
				pluginConfig.setDisplayObjectsLocation("global");
			}
			
			for (String nodeName : new String[] {"displayObject","DisplayObject","displayobject","Displayobject"}){
				Elements displayObjects = displayObjectsRoot.getChildElements(nodeName);
				for (int i=0; i < displayObjects.size(); i++){
					Element displayObject = displayObjects.get(i);
					String name = displayObject.getAttributeValue("name");
					String fileName = displayObject.getAttributeValue("displayobjectfile");
					
					DisplayObject dspObj = new DisplayObject();
					dspObj.setName(name);
					dspObj.setFileName(fileName);
					
					pluginConfig.addDisplayObject(dspObj);
				}
			}
		}
	}
	
	private void parseCustomElements(Element root){
		Elements els = root.getChildElements();
		for (int i=0; i < els.size(); i++){
			if (!nodeList.contains(els.get(i).getQualifiedName().toLowerCase())){
				parseCustomElement(els.get(i), null);
			}
		}
	}
	
	private void parseCustomElement(Element el, GenericSetting parentSetting){
		
		GenericSetting setting = new GenericSetting();		
		setting.setName(el.getQualifiedName());
		setting.setValue(el.getValue());
		
		for (int i=0; i < el.getAttributeCount(); i++){
			Attribute attribute = el.getAttribute(i);
			GenericSettingAttribute sAttribute = new GenericSettingAttribute();
			sAttribute.setName(attribute.getQualifiedName());
			sAttribute.setValue(attribute.getValue());
			setting.addAttribute(sAttribute);
		}
		
		Elements kids = el.getChildElements();
		for (int i=0; i < kids.size(); i++){
			parseCustomElement(kids.get(i),setting);
		}
		
		if (parentSetting == null){
			pluginConfig.addCustomSetting(setting);
		} else {
			parentSetting.addSetting(setting);
		}
	}
	
	private void setupNodeList(){
		ArrayList<String> _nodeList = new ArrayList<String>();
		_nodeList.add("name");
		_nodeList.add("package");
		_nodeList.add("loadpriority");
		_nodeList.add("version");
		_nodeList.add("provider");
		_nodeList.add("providerurl");
		_nodeList.add("category");
		_nodeList.add("settings");
		_nodeList.add("eventhandlers");
		_nodeList.add("displayobjects");
		
		for (String name : _nodeList){
			nodeList.add(name.toLowerCase());
		}
	}
	
	private Element findElement(Element parent, String[] variants){
		for (String elementName : variants){
			Element attempt = parent.getFirstChildElement(elementName);
			if (attempt != null){
				return attempt;
			}
		}
		return new Element(variants[0]);
	}
}
