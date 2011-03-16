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

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

/**
 * @author Steve
 *
 */
public class InstallMuraPage extends WizardPage {
	Text fileField;
	Button[] radios = new Button[2];
	Label lblZipFile;
	Button btnBrowse;
	
	protected InstallMuraPage(ISelection selection){
		super("New Mura CMS Install");
		setTitle("New Mura CMS Install");
		setDescription("Deploy a new Mura CMS install");
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);
		layout.verticalSpacing = 9;
		
		Button btnDownload = new Button(composite, SWT.RADIO);
		btnDownload.setSelection(true);
		btnDownload.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnDownload.setText("Download latest release version");
		btnDownload.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				lblZipFile.setEnabled(false);
				fileField.setEnabled(false);
				btnBrowse.setEnabled(false);
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		radios[0] = btnDownload;
		new Label(composite, SWT.NONE);
		
		Button btnUseAPreviously = new Button(composite, SWT.RADIO);
		btnUseAPreviously.setText("Use a previously downloaded zip file");
		btnUseAPreviously.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnUseAPreviously.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				lblZipFile.setEnabled(true);
				fileField.setEnabled(true);
				btnBrowse.setEnabled(true);
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		radios[1] = btnUseAPreviously;
		new Label(composite, SWT.NONE);
		
		lblZipFile = new Label(composite, SWT.NONE);
		lblZipFile.setEnabled(false);
		lblZipFile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblZipFile.setText("ZIP File");
		
		fileField = new Text(composite, SWT.BORDER);
		fileField.setEnabled(false);
		fileField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btnBrowse = new Button(composite, SWT.NONE);
		btnBrowse.setEnabled(false);
		btnBrowse.setText("Browse...");
		btnBrowse.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		setControl(composite);
	}
	
	public void handleBrowse(){
		FileDialog dialog = new FileDialog(getShell());
		String[] filters = {"*.zip"};
		dialog.setFilterExtensions(filters);
		fileField.setText(dialog.open());
	}
	
}
