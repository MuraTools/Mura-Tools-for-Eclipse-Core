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
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.FillLayout;
import com.muratools.eclipse.License;

/**
 * @author Steve
 *
 */
public class LicensePage extends WizardPage {
	private Text text;
	private License license = new License();
	
	
	public License getLicense() {
		return license;
	}

	public void setLicense(License license) {
		this.license = license;
	}

	public LicensePage(String pageName){
		super(pageName);
		setTitle("License Page");
		setDescription("Enter a license to accompany your plugin.");
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		setControl(composite);
		composite.setLayout(new FillLayout(SWT.VERTICAL));
		
		text = new Text(composite, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		text.addFocusListener(new FocusListener() {
			
			public void focusLost(FocusEvent e) {
				license.setText(text.getText());
			}
			
			public void focusGained(FocusEvent e) {
				// nothing to do here for now
			}
		});
	}

}
