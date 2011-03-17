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
package com.muratools.eclipse.wizard.buildPlugin;

import java.io.FileOutputStream;
import java.util.zip.ZipOutputStream;
import com.muratools.eclipse.MuraPlugin;
import com.muratools.eclipse.MuraToolsWizard;
import com.muratools.eclipse.PluginConfig;
import com.muratools.eclipse.ZipUtil;

/**
 * @author Steve
 * 
 */
public class BuildPluginWizard extends MuraToolsWizard {

	private BuildPluginPage page;
	private MuraPlugin plugin;
	private PluginConfig config;

	public void addPages() {
		page = new BuildPluginPage(getSelection());
		page.setTargetDirectory(getTargetDirectory());

		getConfig().setVersion(incrementBuildNumber(getConfig().getVersion()));
		page.setVersion(getConfig().getVersion());
		page.setFileName(getConfig().getName().replace(" ",	"-"));
		addPage(page);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		ZipUtil zip = new ZipUtil(getTargetDirectory());
		plugin.setTargetDirectory(getTargetDirectory());
		// set the build number equal to the value of the build number from the BuildPluginPage
		getConfig().setVersion(page.getVersion());
		plugin.saveConfigXML(getConfig());
		try {
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(page.getContainerPath() + "/" + page.getFileName()));
			zip.zipDirectory(getTargetDirectory(), zos);
			zos.close();
		} catch (Exception e) {
			getConfig().setVersion(decrementBuildNumber(getConfig().getVersion()));
			plugin.saveConfigXML(getConfig());
		}
		
		refreshContainer();
		return true;
	}
	
	private PluginConfig getConfig() {
		if (this.config == null) {
			plugin = new MuraPlugin(getTargetDirectory() + "/plugin/config.xml");
			config = plugin.getConfigFromXML();
		}
		return config;
	}

	private String incrementBuildNumber(String buildNumber) {
		String version = buildNumber;
		String[] versionParts = version.split("\\.");
		String newVersion = "";
		for (int i = 0; i < versionParts.length; i++) {
			String part = versionParts[i];
			if (i + 1 == versionParts.length) {
				int buildNum = Integer.parseInt(part) + 1;
				part = Integer.toString(buildNum);
			}
			newVersion += part + ".";
		}
		return newVersion.substring(0, newVersion.length() - 1);
	}

	private String decrementBuildNumber(String buildNumber) {
		String version = buildNumber;
		String[] versionParts = version.split("\\.");
		String newVersion = "";
		for (int i = 0; i < versionParts.length; i++) {
			String part = versionParts[i];
			if (i + 1 == versionParts.length) {
				int buildNum = Integer.parseInt(part) - 1;
				part = Integer.toString(buildNum);
			}
			newVersion += part + ".";
		}
		return newVersion.substring(0, newVersion.length() - 1);
	}

}
