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

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;

public class PluginTypePage extends WizardPage {
		
	private Button btnNoFramework;
	private Button btnYesFramework;
	
	public PluginTypePage(String pageName) {
		super(pageName);
		setTitle("Plugin Type");
		setDescription("Select what type of plugin you want to create");
	}

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		composite.setLayout(layout);
		setControl(composite);
		
		Label lblDoYouWant = new Label(composite, SWT.NONE);
		lblDoYouWant.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
		lblDoYouWant.setText("Do you want to use FW/1 for this plugin?");
		new Label(composite, SWT.NONE);
		
		btnNoFramework = new Button(composite, SWT.RADIO);
		btnNoFramework.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		btnNoFramework.setSelection(true);
		btnNoFramework.setText("No");
		new Label(composite, SWT.NONE);
		
		btnYesFramework = new Button(composite, SWT.RADIO);
		btnYesFramework.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		btnYesFramework.setText("Yes");
	}
	
	public Boolean useFWOne(){
		return btnYesFramework.getSelection();
	}
}
