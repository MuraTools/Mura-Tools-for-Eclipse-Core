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


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.osgi.framework.Bundle;

import com.muratools.eclipse.MuraTheme;
import org.eclipse.swt.widgets.Label;

/**
 * @author steve
 *
 */
public class NewThemePage extends WizardPage {
	
	private Combo themeSelect;
	private ArrayList<MuraTheme> themes;
	
	
	/**
	 * @param selection
	 */
	public NewThemePage(ISelection selection) {
		super("New Mura CMS Theme");
		setTitle("New Mura CMS Theme");
		setDescription("Generate a new blank theme or use a prebuilt theme as a starting point.");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		themes = loadThemes();
		
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		layout.verticalSpacing = 9;
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Select base theme");
		
		themeSelect = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
		//gd_themeSelect.widthHint = 115;
		GridData gd_themeSelect = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_themeSelect.widthHint = 465;
		themeSelect.setLayoutData(gd_themeSelect);
		
		for (int i = 0; i < themes.size(); i++){
			MuraTheme theme = themes.get(i);
			themeSelect.add(theme.getName());
		}
		
		setControl(composite);
	}
	
	private ArrayList<MuraTheme> loadThemes(){
		ArrayList<MuraTheme> themes = new ArrayList<MuraTheme>();
		
		Bundle bundle = Platform.getBundle("com.muratools.eclipse");
		File themeXML = null;
		URL fileURL = bundle.getEntry("static/MuraThemes.xml");;
		
		try {
			themeXML = new File(FileLocator.resolve(fileURL).toURI());
		} catch (URISyntaxException e1) {
		    e1.printStackTrace();
		} catch (IOException e1) {
		    e1.printStackTrace();
		}
			
		Builder parser = new Builder();
		Document doc = null;
		
		try {
			doc = parser.build(themeXML);
		} catch (ParsingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (doc != null){
			Element root = doc.getRootElement();
			Elements themeElements = root.getChildElements("theme");
			
			for (int i = 0; i < themeElements.size(); i++){
				Element themeElement = themeElements.get(i);
				Attribute name = themeElement.getAttribute("name");
				Attribute fileName = themeElement.getAttribute("fileName");
				Attribute url = themeElement.getAttribute("url");
				
				MuraTheme theme = new MuraTheme(name.getValue(), url.getValue(), fileName.getValue());
				themes.add(theme);
			}
		}
		
		return themes;
	}
	
	public MuraTheme getSelectedMuraTheme(){
		int selectedIndex = themeSelect.getSelectionIndex();
		
		if (selectedIndex < 0){
			return null;
		}
		
		return themes.get(selectedIndex);
	}

}
