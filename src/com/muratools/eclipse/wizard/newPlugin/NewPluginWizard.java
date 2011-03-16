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

package com.muratools.eclipse.wizard.newPlugin;

import com.muratools.eclipse.MuraPlugin;
import com.muratools.eclipse.MuraToolsWizard;
import com.muratools.eclipse.PluginConfig;

public class NewPluginWizard extends MuraToolsWizard {
	
	private InformationPage informationPage;
	private SettingsPage settingsPage;
	private DisplayObjectsPage displayObjectsPage;
	private EventHandlersPage eventHandlersPage;
	private MuraPlugin muraPlugin;
	private LicensePage licensePage;
	
	public void addPages(){
		informationPage = new InformationPage("General Plugin Information");
		addPage(informationPage);
		settingsPage = new SettingsPage("Plugin Settings Fields");
		addPage(settingsPage);
		eventHandlersPage = new EventHandlersPage("Event Handlers");
		addPage(eventHandlersPage);
		displayObjectsPage = new DisplayObjectsPage("Display Objects");
		addPage(displayObjectsPage);
		licensePage = new LicensePage("Plugin License");
		addPage(licensePage);
	}
	
	@Override
	public boolean performFinish() {
		muraPlugin = new MuraPlugin();
		muraPlugin.setTargetDirectory(getTargetDirectory());
		
		PluginConfig pluginConfig = muraPlugin.getConfig();
		
		pluginConfig.setName(informationPage.pluginName.getText());
		pluginConfig.setVersion(informationPage.pluginVersion.getText() + ".0");
		pluginConfig.setProvider(informationPage.pluginProvider.getText());
		pluginConfig.setProviderURL(informationPage.pluginProviderURL.getText());
		pluginConfig.setPackage(informationPage.pluginPackage.getText());
		pluginConfig.setCategory(informationPage.pluginCategory.getText());
		pluginConfig.setLoadPriority(informationPage.spnrLoadPriority.getText());
		
		pluginConfig.setEventHandlers(eventHandlersPage.eventHandlers);
		pluginConfig.setDisplayObjects(displayObjectsPage.displayObjects);
		pluginConfig.setSettings(settingsPage.settings);
		pluginConfig.setLicense(licensePage.getLicense());
		
		muraPlugin.createPlugin(pluginConfig);
		
		refreshContainer();
		
		return true;
	}
	
}
