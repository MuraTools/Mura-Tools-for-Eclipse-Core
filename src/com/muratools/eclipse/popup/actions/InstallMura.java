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
