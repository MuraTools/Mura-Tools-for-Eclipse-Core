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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.osgi.framework.Bundle;

import com.muratools.eclipse.MuraTheme;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;

/**
 * @author steve
 *
 */
public class NewThemePage extends WizardPage {
	private DataBindingContext m_bindingContext;
	
	private Combo themeSelect;
	private ArrayList<MuraTheme> themes;
	private Text themeNameText;
	private Button btnUseExistingTheme;
	
	
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
		layout.numColumns = 3;
		composite.setLayout(layout);
		layout.verticalSpacing = 9;
		setControl(composite);
		
		Label lblThemeName = new Label(composite, SWT.NONE);
		lblThemeName.setToolTipText("Theme name.  Spaces will be replaced with underscores and special characters will be removed.");
		lblThemeName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblThemeName.setAlignment(SWT.RIGHT);
		lblThemeName.setText("Theme Name");
		
		themeNameText = new Text(composite, SWT.BORDER);
		themeNameText.setToolTipText("Theme name.  Spaces will be replaced with underscores and special characters will be removed.");
		themeNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		themeNameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkFields();
			}
		});
		themeNameText.setFocus();
		themeNameText.selectAll();
		
		Button btnRadioButton = new Button(composite, SWT.RADIO);
		btnRadioButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnRadioButton.setSelection(true);
		btnRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				themeSelect.deselectAll();
				checkFields();
			}
		});
		btnRadioButton.setText("Empty Theme Scaffold");
		new Label(composite, SWT.NONE);
		
		btnUseExistingTheme = new Button(composite, SWT.RADIO);
		btnUseExistingTheme.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnUseExistingTheme.setText("Use Existing Theme");
		btnUseExistingTheme.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				checkFields();
			}
		});
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setEnabled(false);
		lblNewLabel.setAlignment(SWT.RIGHT);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Select base theme");
		
		themeSelect = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
		themeSelect.setEnabled(false);
		GridData gd_themeSelect = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_themeSelect.widthHint = 465;
		themeSelect.setLayoutData(gd_themeSelect);
		
		for (int i = 0; i < themes.size(); i++){
			MuraTheme theme = themes.get(i);
			themeSelect.add(theme.getName());
		}
		
		themeSelect.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkFields();
			}
		});
		
		m_bindingContext = initDataBindings();
		checkFields();
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
	
	public String getThemeName(){
		return themeNameText.getText().replaceAll("[^a-zA-Z0-9\\s]", "").replaceAll(" ", "_").replaceAll("[_]+", "_").replaceAll("[_]$", "");
	}
	
	public boolean useExistingTheme(){
		return btnUseExistingTheme.getSelection();
	}
	
	private void checkFields(){
		boolean canComplete = false;
		
		if (getThemeName().length() > 0){
			canComplete = true;
			
			if (btnUseExistingTheme.getSelection()){
				if (getSelectedMuraTheme() != null){
					canComplete = true;
				} else {
					canComplete = false;
				}
			} else {
				canComplete = true;
			}
		}
		
		setPageComplete(canComplete);
	}
	
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue themeSelectObserveEnabledObserveWidget = SWTObservables.observeEnabled(themeSelect);
		IObservableValue btnUseExistingThemeObserveSelectionObserveWidget = PojoObservables.observeValue(btnUseExistingTheme, "selection");//SWTObservables.observeSelection(btnUseExistingTheme);
		bindingContext.bindValue(themeSelectObserveEnabledObserveWidget, btnUseExistingThemeObserveSelectionObserveWidget, null, null);
		//
		return bindingContext;
	}
}
