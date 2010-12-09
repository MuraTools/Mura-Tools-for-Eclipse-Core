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
