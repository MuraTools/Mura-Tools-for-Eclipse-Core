package com.muratools.eclipse.popup.actions;

import java.io.File;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import com.muratools.eclipse.MuraToolsAction;
import com.muratools.eclipse.wizard.buildPlugin.BuildPluginWizard;

public class BuildMuraPlugin extends MuraToolsAction {

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		if (isValidPlugin()){
			BuildPluginWizard wizard = new BuildPluginWizard();
			
			if ((getSelection() instanceof IStructuredSelection) || (getSelection() == null)){
				wizard.init(
					getPart().getSite().getWorkbenchWindow().getWorkbench(),
					(IStructuredSelection)getSelection()
				);
			}
			
			WizardDialog dialog = new WizardDialog(getShell(), wizard);
			dialog.create();
			dialog.open();
		} else {
			MessageDialog.openError(getShell(), "Invalid Plugin Structure", "Your selection does not contain a valid Mura CMS plugin!");
		}
	}
	
	private boolean isValidPlugin(){
		if (new File(getTargetDirectory() + "/plugin").exists() == false){
			return false;
		}
		
		if (new File(getTargetDirectory() + "/plugin/config.cfm").exists() == false){
			return false;
		}
		
		if (new File(getTargetDirectory() + "/plugin/config.xml").exists() == false){
			return false;
		}
		
		if (new File(getTargetDirectory() + "/plugin/plugin.cfc").exists() == false){
			return false;
		}
		
		if (new File(getTargetDirectory() + "/index.cfm").exists() == false){
			return false;
		}
		
		return true;
	}
}
