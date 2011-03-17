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

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @author Steve
 *
 */
public class BuildPluginPage extends WizardPage {
	
	private Text containerText;
	private Text fileText;
	private String fileName = "";
	private String containerPath = "";
	private String version;
	private String targetDirectory;
	private Text buildNumberText;
	
	/**
	 * @param selection
	 */
	protected BuildPluginPage(ISelection selection) {
		super("Build Mura Plugin");
		setTitle("Build and Package Mura Plugin");
		setDescription("Compress plugin contents into a deployable zip file.");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	//@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		layout.numColumns = 4;
		layout.verticalSpacing = 9;
		
		Label label = new Label(composite, SWT.NULL);
		label.setText("Path:");
		
		containerText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		containerText.setLayoutData(gd);
		containerText.addModifyListener(new ModifyListener() {	
			public void modifyText(ModifyEvent e) {
				setContainerPath(containerText.getText());
				dialogChanged();	
			}
		});
		
		Button button = new Button(composite, SWT.PUSH);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				handleBrowse();
			}
		});
		
		label = new Label(composite, SWT.NULL);
		label.setText("File name:");
		
		fileText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		fileText.setLayoutData(gd);
		fileText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setFileName(fileText.getText());
				dialogChanged();
			}
		});
		
		new Label(composite, SWT.NONE);
		
		buildNumberText = new Text(composite, SWT.BORDER);
		buildNumberText.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setText("Build Number");
		
		Button btnRefreshBuildNumber = new Button(composite, SWT.NONE);
		btnRefreshBuildNumber.setText("Refresh");
		btnRefreshBuildNumber.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent arg0) {
				refreshBuildNumber();
				
			}
			
			public void widgetDefaultSelected(SelectionEvent arg0) {
				refreshBuildNumber();
				
			}
		});		
		
		// These always need to be called last
		initialize();
		dialogChanged();
		setControl(composite);
		// Nothing should go below these
	}
	
	private void dialogChanged(){
		if (getContainerPath().length() == 0){
			updateStatus("File container must be specified");
			return;
		}
		
		if (getFileName().length() == 0){
			updateStatus("File name must be specified");
			return;
		}
		
		updateStatus(null);
	}
	
	private void handleBrowse(){
		DirectoryDialog dialog = new DirectoryDialog(getShell());
		dialog.setFilterPath(getTargetDirectory());
		dialog.setMessage("Select directory to save zip file");
		dialog.open();
		if (dialog != null){
			containerText.setText(dialog.getFilterPath());
		}
	}
	
	private void initialize(){
		containerText.setText(getTargetDirectory());
		buildNumberText.setText(getVersion());
		fileText.setText(getFileName() + "-" + getVersion() + ".zip");
		
		setFileName(fileText.getText());
		setContainerPath(containerText.getText());
	}
	
	private void updateStatus(String message){
		setErrorMessage(message);
		setPageComplete(message == null);
	}
	
	private void refreshBuildNumber(){
		fileText.setText(getFileName().replaceAll(getVersion(), buildNumberText.getText()));
		setVersion(buildNumberText.getText());
	}
	
	private void setContainerPath(String containerPath){
		this.containerPath = containerPath;
	}
	
	public String getContainerPath(){
		return containerPath;
	}
	
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	
	public String getFileName(){
		return fileName;
	}
	
	public void setTargetDirectory(String targetDirectory){
		this.targetDirectory = targetDirectory;
	}
	
	private String getTargetDirectory(){
		return this.targetDirectory;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
