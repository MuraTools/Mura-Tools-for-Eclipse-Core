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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.muratools.eclipse.utils.PluginConfigUtility;

public class MuraPlugin {
	
	private PluginConfig config;
	private String targetDirectory;
	private String downloadLocation = "http://getmura.com/currentversion/";
	private ArrayList<String> nodeList;
	
	public MuraPlugin(){
		setupNodeList();
	}
	
	public MuraPlugin(String targetDirectory){
		setupNodeList();
		setTargetDirectory(targetDirectory);
	}
	
	public void setTargetDirectory(String targetDirectory){
		this.targetDirectory = targetDirectory;
	}
	
	public String getTargetDirectory(){
		return this.targetDirectory;
	}
	
	public PluginConfig getConfig(){
		if (this.config == null){
			this.config = new PluginConfig();
		}
		return this.config;
	}
	
	public String getDownloadLocation() {
		return downloadLocation;
	}

	public void setDownloadLocation(String downloadLocation) {
		this.downloadLocation = downloadLocation;
	}

	public PluginConfig getConfigFromXML(){
		PluginConfig config = getConfig();
		try {
			File pluginXMLFile = new File(getTargetDirectory());
			if (!pluginXMLFile.exists()){
				pluginXMLFile = new File(getTargetDirectory() + ".cfm");
			}
			
			PluginConfigUtility configUtil = new PluginConfigUtility(pluginXMLFile);
			config = configUtil.getPluginConfig();
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return config;
	}
	
	
	
	public boolean createPlugin(PluginConfig config){
		this.config = config;
		
		createConfigXML(); // create the /plugin/config.xml file
		createEventHandlerBuffer();
		createDisplayObjects();
		createIndexFile();
		createConfigFile();
		createPluginCFC();
		
		if (getConfig().getLicense().getText().length() > 0){
			//if (getConfig().getLicense().isStandAloneFile()){
				createLicenseFile("LICENSE.TXT");
			/*} else {
				createLicenseFile(".license");
			}*/
		}
		
		return true;
	}
	
	private void createConfigXML(){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("\n<plugin>");
		sb.append("\n\t<name>" + getConfig().getName() + "</name>");
		sb.append("\n\t<package>" + getConfig().getPackage() + "</package>");
		sb.append("\n\t<loadPriority>" + getConfig().getLoadPriority() + "</loadPriority>");
		sb.append("\n\t<version>" + getConfig().getVersion() + "</version>");
		sb.append("\n\t<provider>" + getConfig().getProvider() + "</provider>");
		sb.append("\n\t<providerURL>" + getConfig().getProviderURL() + "</providerURL>");
		sb.append("\n\t<category>" + getConfig().getCategory() + "</category>");
		sb.append(getSettingsBuffer()); // Settings XML
		sb.append(getEventHandlersBuffer()); // Event Handlers XML
		sb.append(getDisplayObjectsBuffer()); // Display Objects XML
		sb.append(getCustomSettingsBuffer());
		sb.append("\n</plugin>");
		
		writeToFile(new File(targetDirectory + "/plugin/config.xml").exists() ? "config.xml" : "config.xml.cfm", targetDirectory + "/plugin", sb);
	}
	
	public void saveConfigXML(PluginConfig config){
		this.config = config;
		createConfigXML();
	}
	
	private StringBuffer getSettingsBuffer(){
		StringBuffer sb = new StringBuffer();
		
		if (getConfig().getSettingFields().size() == 0){
			sb.append("\n\t<settings />");
		} else {
			sb.append("\n\t<settings>");
			for (SettingField setting : getConfig().getSettingFields()){
				sb.append("\n\t\t<setting>");
				sb.append("\n\t\t\t<name>" + setting.getName() + "</name>");
				sb.append("\n\t\t\t<label>" + setting.getLabel() + "</label>");
				sb.append("\n\t\t\t<hint>" + setting.getHint() + "</hint>");
				sb.append("\n\t\t\t<type>" + setting.getType() + "</type>");
				sb.append("\n\t\t\t<required>" + setting.getRequired().toString() + "</required>");
				sb.append("\n\t\t\t<validation>" + setting.getValidation() + "</validation>");
				sb.append("\n\t\t\t<regex>" + setting.getRegEx() + "</regex>");
				sb.append("\n\t\t\t<message>" + setting.getMessage() + "</message>");
				sb.append("\n\t\t\t<defaultValue>" + setting.getDefaultValue() + "</defaultValue>");
				sb.append("\n\t\t\t<optionList>" + setting.getOptionList() + "</optionList>");
				sb.append("\n\t\t\t<optionLabelList>" + setting.getOptionLabelList() + "</optionLabelList>");
				sb.append("\n\t\t</setting>");
			}
			sb.append("\n\t</settings>");
		}
		return sb;
	}
	
	private StringBuffer getEventHandlersBuffer(){
		StringBuffer sb = new StringBuffer();
		if (getConfig().getEventHandlers().size() == 0){
			sb.append("\n\t<EventHandlers />");
		} else {
			sb.append("\n\t<EventHandlers>");
			for (EventHandler eventHandler : getConfig().getEventHandlers()){
				sb.append("\n\t\t<eventHandler event=\"" + eventHandler.getEvent() + "\" component=\"eventHandlers." + eventHandler.getComponent() + "\" persist=\"" + eventHandler.getPersist().toString() + "\" />");
			}
			sb.append("\n\t</EventHandlers>");
		}
		return sb;
	}
	
	private StringBuffer getDisplayObjectsBuffer(){
		StringBuffer sb = new StringBuffer();
		if (getConfig().getDisplayObjects().size() == 0){
			sb.append("\n\t<DisplayObjects />");
		} else {
			sb.append("\n\t<DisplayObjects location=\"" + getConfig().getDisplayObjectsLocation() + "\">");
			for (DisplayObject displayObject : getConfig().getDisplayObjects()){
				sb.append("\n\t\t<displayObject name=\"" + displayObject.getName() + "\" displayobjectfile=\"displayObjects/" + displayObject.getFileName() + ".cfm\" />");
			}
			sb.append("\n\t</DisplayObjects>");
		}
		return sb;
	}
	
	private StringBuffer getCustomSettingsBuffer(){
		StringBuffer sb = new StringBuffer();
		
		ArrayList<GenericSetting> customSettings = getConfig().getCustomSettings();
		if (customSettings.size() > 0){
			for (int i=0; i < customSettings.size(); i++){
				appendCustomSettingsBuffer(sb, customSettings.get(i),"\t");
			}
		}
		
		return sb;
	}
	
	private void appendCustomSettingsBuffer(StringBuffer sb, GenericSetting setting,String tabbing){
		sb.append("\n" + tabbing + "<" + setting.getName());
		if (setting.hasAttributes()){
			for (GenericSettingAttribute attribute : setting.getAttributes()){
				sb.append(" " + attribute.getName() + "=\"" + attribute.getValue() + "\"");
			}
		}
		
		if (setting.hasSettings()){
			sb.append(">");
			for (int i=0; i < setting.getSettings().size(); i++){
				appendCustomSettingsBuffer(sb, setting.getSettings().get(i), tabbing + "\t");
			}
			sb.append("\n" + tabbing + "</" + setting.getName() + ">");
		} else {
			if (setting.getValue() != null && setting.getValue().length() > 0){
				sb.append(">");
				sb.append(setting.getValue());
				sb.append("</" + setting.getName() + ">");
			} else {
				sb.append(" />");
			}
		}
	}
	
	private void writeToFile(String fileName, String targetDirectory, StringBuffer content){
		File dir = new File(targetDirectory);
		if (!dir.exists()){
			dir.mkdir();
		}
		
		File file = new File(targetDirectory + "/" + fileName);
		try{
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(content.toString());
			fileWriter.close();
		} catch (IOException e){
			e.printStackTrace(System.err);
		}
	}
	
	private void createEventHandlerBuffer(){
		// Thanks to Adam Presley for helping me with this part of the code
		ArrayList<EventHandler> eventHandlers = getConfig().getEventHandlers();
		
		if (eventHandlers.size() > 0){
			String currentGroup = eventHandlers.get(0).getComponent();
			StringBuffer sb = new StringBuffer();
			
			sb.append(writeEventHandlerHeader());
			
			for (EventHandler e : eventHandlers){
				if (e.getComponent().compareTo(currentGroup) != 0){
					sb.append(writeEventHandlerFooter());
					writeToFile(currentGroup + ".cfc",targetDirectory + "/eventHandlers",sb); // Write the file!
					sb = new StringBuffer();
					sb.append(writeEventHandlerHeader());
					currentGroup = e.getComponent();
				}
				sb.append(writeEventHandlerMethod(e.getEvent()));
			}
			if (sb.length() > 0){
				sb.append(writeEventHandlerFooter());
				writeToFile(currentGroup + ".cfc",targetDirectory + "/eventHandlers",sb); // Write the file!
			}
		}
	}
	
	private String writeEventHandlerHeader(){
		String header = "";
		header += "<cfcomponent extends=\"mura.plugin.pluginGenericEventHandler\">";
		return header;
	}
	
	private String writeEventHandlerFooter(){
		return "\n\n</cfcomponent>";
	}
	
	private String writeEventHandlerMethod(String methodName){
		return "\n\n\t<cffunction name=\"" + methodName + "\" access=\"public\" returntype=\"void\" output=\"false\">\n\t\t<cfargument name=\"event\" />\n\t\t<!--- TODO: Auto-Generated method stub --->\n\t</cffunction>";
	}
	
	private void createDisplayObjects(){
		for (DisplayObject displayObject : getConfig().getDisplayObjects()){
			StringBuffer sb = new StringBuffer();
			sb.append("<!--- \n\tPlugin: " + getConfig().getName() + "\n\tDisplay Object: " + displayObject.getName() + "\n --->\n\n<cfsilent>\n\t<!--- TODO: Implement code --->\n</cfsilent>\n\n<!--- TODO: Implement code --->");
			writeToFile(displayObject.getFileName() + ".cfm", targetDirectory + "/displayObjects", sb);
		}
	}
	
	private void createIndexFile(){
		StringBuffer sb = new StringBuffer();
		sb.append("<!--- \n\tPlugin: " + getConfig().getName() + "\n --->");
		sb.append("\n\n<cfinclude template=\"plugin/config.cfm\" />");
		sb.append("\n\n<cfsilent>\n\t<!--- TODO: Implement code... --->\n</cfsilent>");
		sb.append("\n\n<cfsavecontent variable=\"variables.body\">\n\t<cfoutput>\n\t<h2>#request.pluginConfig.getName()#</h2>\n\t<!--- TODO: Implement code... --->\n\t</cfoutput>\n</cfsavecontent>");
		sb.append("\n\n<cfoutput>#application.pluginManager.renderAdminTemplate(body=variables.body,pageTitle=request.pluginConfig.getName())#</cfoutput>");
		writeToFile("index.cfm",targetDirectory,sb);
	}
	
	private void createConfigFile(){
		StringBuffer sb = new StringBuffer();
		sb.append("<!--- \n\tPlugin: " + getConfig().getName() + "\n --->");
		sb.append("\n\n<cfsilent>");
		sb.append("\n<cfif not structKeyExists(request,\"pluginConfig\")>");
		sb.append("\n\t<cfset pluginID=listLast(listGetat(getDirectoryFromPath(getCurrentTemplatePath()),listLen(getDirectoryFromPath(getCurrentTemplatePath()),application.configBean.getFileDelim())-1,application.configBean.getFileDelim()),\"_\")>");
		sb.append("\n\t<cfset request.pluginConfig=application.pluginManager.getConfig(pluginID)>");
		sb.append("\n\t<cfset request.pluginConfig.setSetting(\"pluginMode\",\"Admin\")/>");
		sb.append("\n</cfif>");
		
		sb.append("\n\n<cfif request.pluginConfig.getSetting(\"pluginMode\") eq \"Admin\" and not isUserInRole('S2')>");
		sb.append("\n\t<cfif not structKeyExists(session,\"siteID\") or not application.permUtility.getModulePerm(request.pluginConfig.getValue('moduleID'),session.siteid)>");
		sb.append("\n\t\t<cflocation url=\"#application.configBean.getContext()#/admin/\" addtoken=\"false\">");
		sb.append("\n\t</cfif>");
		sb.append("\n</cfif>");
		sb.append("\n</cfsilent>");
		writeToFile("config.cfm", targetDirectory + "/plugin", sb);
	}
	
	private void createPluginCFC(){
		StringBuffer sb = new StringBuffer();
		sb.append("<cfcomponent extends=\"mura.plugin.plugincfc\">");
		
		sb.append("\n\n\t<cffunction name=\"init\" returntype=\"any\" access=\"public\" output=\"false\">");
		sb.append("\n\t\t<cfargument name=\"pluginConfig\" type=\"any\" default=\"\" />");
		sb.append("\n\n\t\t<cfset variables.pluginConfig = arguments.pluginConfig />");
		sb.append("\n\n\t\t<cfreturn this />");
		sb.append("\n\t</cffunction>");
		
		sb.append("\n\n\t<cffunction name=\"install\" returntype=\"void\" access=\"public\" output=\"false\">");
		sb.append("\n\t\t<!--- TODO: Auto-generated method stub --->");
		sb.append("\n\t</cffunction>");
		
		sb.append("\n\n\t<cffunction name=\"update\" returntype=\"void\" access=\"public\" output=\"false\">");
		sb.append("\n\t\t<!--- TODO: Auto-generated method stub --->");
		sb.append("\n\t</cffunction>");
		
		sb.append("\n\n\t<cffunction name=\"delete\" returntype=\"void\" access=\"public\" output=\"false\">");
		sb.append("\n\t\t<!--- TODO: Auto-generated method stub --->");
		sb.append("\n\t</cffunction>");
		
		sb.append("\n\n</cfcomponent>");
		writeToFile("plugin.cfc",targetDirectory + "/plugin", sb);
	}
	
	private void createLicenseFile(String fileName){
		writeToFile(fileName, targetDirectory, new StringBuffer(getConfig().getLicense().getText()));
	}
	
	private void setupNodeList(){
		nodeList = new ArrayList<String>();
		nodeList.add("name");
		nodeList.add("package");
		nodeList.add("loadpriority");
		nodeList.add("version");
		nodeList.add("provider");
		nodeList.add("providerurl");
		nodeList.add("category");
		nodeList.add("settings");
		nodeList.add("eventhandlers");
		nodeList.add("displayobjects");
	}
	
}
