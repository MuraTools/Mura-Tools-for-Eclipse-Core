package com.muratools.eclipse.popup.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;

import com.muratools.eclipse.MuraToolsAction;
import com.muratools.eclipse.wizard.newPlugin.NewPluginWizard;

public class NewMuraPlugin extends MuraToolsAction {
	
	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		NewPluginWizard newPluginWizard = new NewPluginWizard();
		
		if ((getSelection() instanceof IStructuredSelection) || (getSelection() == null)){
			newPluginWizard.init(
				getPart().getSite().getWorkbenchWindow().getWorkbench(),(IStructuredSelection)getSelection()
			);
		}
		
		WizardDialog dialog = new WizardDialog(getShell(),newPluginWizard);
		dialog.create();
		dialog.open();
	}
}
