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

import java.util.ArrayList;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TreeColumn;

import com.muratools.eclipse.SettingField;

public class SettingsPage extends WizardPage {
	
	ArrayList<SettingField> settings = new ArrayList<SettingField>();
	
	private Button newSetting;
	private Button btnEditSetting;
	private Tree settingsTree;
	private TreeColumn trclmnSettings;
	private boolean editing = false;
	
	public SettingsPage(String pageName){
		super(pageName);
		setTitle("Add Settings");
		setDescription("Add settings fields to the plugin");
	}
	
	//@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		setControl(composite);
		
		settingsTree = new Tree(composite, SWT.BORDER);
		settingsTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		settingsTree.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				TreeItem item = (TreeItem)e.item;
				if (item.getParentItem() == null){
					btnEditSetting.setEnabled(true);
				} else {
					btnEditSetting.setEnabled(false);
				}
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		trclmnSettings = new TreeColumn(settingsTree, SWT.LEFT);
		trclmnSettings.setWidth(500);
		trclmnSettings.setText("Settings");;
		
		Composite compBtns = new Composite(composite, SWT.NONE);
		GridLayout glCompBtns = new GridLayout(4,false);
		compBtns.setLayout(glCompBtns);
		
		newSetting = new Button(compBtns, SWT.NONE);
		newSetting.setText("Add Setting");
		newSetting.setLayoutData(new GridData(SWT.RIGHT,SWT.CENTER,false,false,1,1));
		newSetting.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				editing = false;
				openNewSettingWizard(new SettingDialog(getShell()));
			}
		});
		
		Button removeSetting = new Button(compBtns, SWT.NONE);
		removeSetting.setText("Remove Setting");
		removeSetting.setLayoutData(new GridData(SWT.RIGHT,SWT.CENTER,false,false,1,1));
		removeSetting.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				removeSelectedTreeItem();
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		Button removeAllSettings = new Button(compBtns, SWT.NONE);
		removeAllSettings.setText("Remove All Settings");
		removeAllSettings.setLayoutData(new GridData(SWT.RIGHT,SWT.CENTER,false,false,1,1));
		removeAllSettings.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				settingsTree.removeAll();
				settings.removeAll(settings);
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		btnEditSetting = new Button(compBtns,SWT.NONE);
		btnEditSetting.setText("Edit Selected Setting");
		btnEditSetting.setEnabled(false);
		btnEditSetting.setLayoutData(new GridData(SWT.RIGHT,SWT.CENTER,false,false,1,1));
		btnEditSetting.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				if (settingsTree.getSelection().length == 1){
					SettingDialog dialog = new SettingDialog(getShell());
					TreeItem item = settingsTree.getSelection()[0];
					
					for (SettingField setting : settings){
						if (setting.getLabel() == item.getText()){
							dialog.setStrName(setting.getName());
							dialog.setStrLabel(setting.getLabel());
							dialog.setStrHint(setting.getHint());
							dialog.setStrType(setting.getType());
							dialog.setIsRequired(setting.getRequired());
							dialog.setStrValidation(setting.getValidation());
							dialog.setStrRegEx(setting.getRegEx());
							dialog.setStrMessage(setting.getMessage());
							dialog.setStrDefault(setting.getDefaultValue());
							dialog.setStrOptionLabels(setting.getOptionLabelList());
							dialog.setStrOptions(setting.getOptionList());
							break;
						}
					}
					if (dialog.getName().length() > 0){
						editing = true;
						openNewSettingWizard(dialog);
					}
				}
				
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}
	
	public void addTreeNode(SettingField setting){
		settings.add(setting);
	}
	
	private void openNewSettingWizard(SettingDialog dialog){
		dialog.setBlockOnOpen(true);
		
		if (dialog.open() != Dialog.OK){
			return;
		} else {
			if (dialog.getName().length() == 0 || dialog.getLabel().length() == 0){
				return;
			}
			
			if (editing){
				removeSelectedTreeItem();
				editing = false;
			}
			
			SettingField setting = new SettingField();
			setting.setName(dialog.getName());
			setting.setLabel(dialog.getLabel());
			setting.setHint(dialog.getHint());
			setting.setType(dialog.getType());
			setting.setRequired(dialog.getIsRequired());
			setting.setValidation(dialog.getValidation());
			setting.setRegEx(dialog.getRegEx());
			setting.setMessage(dialog.getMessage());
			setting.setDefaultValue(dialog.getDefault());
			setting.setOptionList(dialog.getOptions());
			setting.setOptionLabelList(dialog.getOptionLabels());
			
			settings.add(setting);
			updateTree(setting);
		}
	}
	
	private void updateTree(SettingField setting){
		TreeItem parentItem = new TreeItem(settingsTree, 0);
		parentItem.setText(setting.getLabel());
				
		newTreeItem(parentItem,"Name: " + setting.getName());
		newTreeItem(parentItem,"Label: " + setting.getLabel());
		newTreeItem(parentItem,"Hint: " + setting.getHint());
		newTreeItem(parentItem,"Type: " + setting.getType());
		newTreeItem(parentItem,"Required: " + setting.getRequired().toString());
		newTreeItem(parentItem,"Validation: " + setting.getValidation());
		newTreeItem(parentItem,"RegEx: " + setting.getRegEx());
		newTreeItem(parentItem,"Message: " + setting.getMessage());
		newTreeItem(parentItem,"Default Value: " + setting.getDefaultValue());
		TreeItem optionsItem = newTreeItem(parentItem,"Options");
		if (setting.getOptionList().split("\\^").length > 1){
			for (String option : setting.getOptionList().split("\\^")){
				newTreeItem(optionsItem,option);
			}
		}
		TreeItem optionLabelsItem = newTreeItem(parentItem,"Option Labels");
		if (setting.getOptionLabelList().split("\\^").length > 1){
			for (String option : setting.getOptionLabelList().split("\\^")){
				newTreeItem(optionLabelsItem,option);
			}
		}
	}
	
	private TreeItem newTreeItem(TreeItem parent, String label){
		TreeItem newItem = new TreeItem(parent,0);
		newItem.setText(label);
		return newItem;
	}
	
	private void removeSelectedTreeItem(){
		TreeItem[] selection = settingsTree.getSelection();
		for (int i=0; i < selection.length; i++){
			//settingsTree.clear(i, true);
			selection[i].removeAll();
			selection[i].dispose();
			settings.remove(i);
		}
	}
}
