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
package com.muratools.eclipse.wizard.newInstall;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.ini4j.Wini;

import com.muratools.mura.IniSetting;

/**
 * @author steve
 *
 */
public class NewInstallIniEditor implements IRunnableWithProgress {

	private String targetDirectory;
	private ArrayList<IniSetting> settingsArrayList;
	
	/**
	 * 
	 */
	public NewInstallIniEditor(String targetDirectory, ArrayList<IniSetting> settingsArrayList) {
		this.targetDirectory = targetDirectory;
		this.settingsArrayList = settingsArrayList;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		try {
			File settingsFile = new File(targetDirectory + "/config/settings.ini.cfm");
			Wini ini = new Wini(settingsFile);
			
			monitor.beginTask("Configuring Mura CMS", settingsArrayList.size()+1);
			
			for (int i=0; i < settingsArrayList.size(); i++){
				IniSetting setting = settingsArrayList.get(i);
				ini.put(setting.getSection(), setting.getName(), setting.getValue());
				monitor.worked(1);
			}
			
			// write the values to the settings.ini.cfm file
			ini.store();
			monitor.worked(1);
		
		} catch (IOException e){
			e.printStackTrace();
		}
		
		monitor.done();
	}

}
