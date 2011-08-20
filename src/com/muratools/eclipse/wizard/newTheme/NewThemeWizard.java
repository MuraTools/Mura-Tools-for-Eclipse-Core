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
package com.muratools.eclipse.wizard.newTheme;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import com.muratools.eclipse.MuraTheme;
import com.muratools.eclipse.MuraToolsWizard;

/**
 * @author steve
 *
 */
public class NewThemeWizard extends MuraToolsWizard {
	
	private static String THEME_ZIPS_FOLDER_PATH = "static/themeZips";
	
	private NewThemePage page;
	
	public void addPages(){
		page = new NewThemePage(getSelection());
		addPage(page);
	}
	
	@Override
	public boolean performFinish(){
		MuraTheme theme = page.getSelectedMuraTheme();
		
		Bundle bundle = Platform.getBundle("com.muratools.eclipse");
		File themeZip = null;
		URL fileURL = bundle.getEntry(THEME_ZIPS_FOLDER_PATH + "/" + theme.getFileName());;
		
		try {
			themeZip = new File(FileLocator.resolve(fileURL).toURI());
		} catch (URISyntaxException e1) {
		    e1.printStackTrace();
		} catch (IOException e1) {
		    e1.printStackTrace();
		}
		
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(themeZip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Enumeration entries = zipFile.entries();
		while (entries.hasMoreElements()){
			ZipEntry entry = (ZipEntry)entries.nextElement();
			if (!entry.getName().contains("__MACOSX")){
				String fullEntryPath = getTargetDirectory() + "/" + entry.getName();
				
				if (entry.isDirectory()){
					(new File(fullEntryPath)).mkdir();
					continue;
				}
				
				try {
					copyInputStream(zipFile.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(fullEntryPath)));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		try {
			zipFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		refreshContainer();
		
		return true;
	}
	
	public static final void copyInputStream(InputStream in, OutputStream out) throws IOException {
	    byte[] buffer = new byte[1024];
	    int len;
	
	    while((len = in.read(buffer)) >= 0){
	    	out.write(buffer, 0, len);
	    }
	
	    in.close();
	    out.close();
	}

}
