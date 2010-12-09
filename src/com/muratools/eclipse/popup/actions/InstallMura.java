package com.muratools.eclipse.popup.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;

import com.muratools.eclipse.MuraToolsAction;
import com.muratools.eclipse.wizard.newInstall.NewInstallWizard;

public class InstallMura extends MuraToolsAction {

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		NewInstallWizard wizard = new NewInstallWizard();
		
		if ((getSelection() instanceof IStructuredSelection) || (getSelection() == null)){
			wizard.init(
				getPart().getSite().getWorkbenchWindow().getWorkbench(),
				(IStructuredSelection)getSelection()
			);
		}
		
		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.create();
		dialog.open();
	}
}
