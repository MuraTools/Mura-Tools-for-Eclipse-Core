package com.muratools.eclipse.wizard.newPlugin;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Spinner;

public class InformationPage extends WizardPage {
	
	Text pluginName;
	Text pluginPackage;
	Text pluginVersion;
	Text pluginProvider;
	Text pluginProviderURL;
	Text pluginCategory;
	Spinner spnrLoadPriority;
	
	protected InformationPage(String pageName){
		super(pageName);
		setTitle("New Mura CMS Plugin");
		setDescription("Create a new Mura CMS plugin.");
	}
	
	//@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		setControl(composite);
		
		Label lblName = new Label(composite, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText("Name *");
		
		pluginName = new Text(composite, SWT.BORDER);
		GridData gd_pluginName = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_pluginName.widthHint = 228;
		pluginName.setLayoutData(gd_pluginName);
		pluginName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkFields();
			}
		});
		
		Label lblPackage = new Label(composite, SWT.NONE);
		lblPackage.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPackage.setText("Package *");
		
		pluginPackage = new Text(composite, SWT.BORDER);
		pluginPackage.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		pluginPackage.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkFields();
			}
		});
		
		Label lblLoadPriority = new Label(composite, SWT.NONE);
		lblLoadPriority.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLoadPriority.setText("Load Priority");
		
		spnrLoadPriority = new Spinner(composite, SWT.BORDER);
		spnrLoadPriority.setMaximum(10);
		spnrLoadPriority.setMinimum(1);
		spnrLoadPriority.setSelection(5);
		
		Label lblVersion = new Label(composite, SWT.NONE);
		lblVersion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblVersion.setText("Version *");
		
		pluginVersion = new Text(composite, SWT.BORDER);
		pluginVersion.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		pluginVersion.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkFields();
			}
		});
		
		Label lblProvider = new Label(composite, SWT.NONE);
		lblProvider.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblProvider.setText("Provider");
		
		pluginProvider = new Text(composite, SWT.BORDER);
		pluginProvider.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblProviderUrl = new Label(composite, SWT.NONE);
		lblProviderUrl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblProviderUrl.setText("Provider URL");
		
		pluginProviderURL = new Text(composite, SWT.BORDER);
		pluginProviderURL.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		pluginProviderURL.setText("http://");
		
		Label lblCategory = new Label(composite, SWT.NONE);
		lblCategory.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCategory.setText("Category");
		
		pluginCategory = new Text(composite, SWT.BORDER);
		pluginCategory.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite, SWT.NONE);
		
		Label lblRequiredField = new Label(composite, SWT.NONE);
		lblRequiredField.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblRequiredField.setText("* Required Field");
		
		initialize();
	}
	
	private void initialize(){
		setPageComplete(false);
	}
	
	private void checkFields(){
		boolean canComplete = false;
		
		if (pluginName.getText().length() != 0 && pluginPackage.getText().length() != 0 && pluginVersion.getText().length() != 0){
			canComplete = true;
		}
		
		setPageComplete(canComplete);
	}
	
}
