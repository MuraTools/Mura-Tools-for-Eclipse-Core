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
package com.muratools.eclipse;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author Steve
 *
 */
public class MuraToolsAction implements IObjectActionDelegate {

	Shell shell;
	IWorkbenchPart part;
	ISelection selection;
	
	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

	public IWorkbenchPart getPart() {
		return part;
	}

	public void setPart(IWorkbenchPart part) {
		this.part = part;
	}

	public ISelection getSelection() {
		return selection;
	}

	public void setSelection(ISelection selection) {
		this.selection = selection;
	}

	public void run(IAction action){
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		setSelection(selection);
	}
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		setShell(targetPart.getSite().getShell());
		setPart(targetPart);
	}
	
	public String getTargetDirectory(){
		String targetPath = "";
		String containerPath = "";
		if (getSelection() != null && getSelection().isEmpty() == false && getSelection() instanceof IStructuredSelection){
			IStructuredSelection ssel = (IStructuredSelection)getSelection();
			
			if (ssel.size() > 1){
				return targetPath;
			}
			
			Object obj = ssel.getFirstElement();
			
			if (obj instanceof IResource){
				IContainer container = (IContainer)obj;
				containerPath = container.getFullPath().toString();
				
				IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
				IResource resource = root.findMember(new Path(containerPath));
				if (resource != null){
					IPath path = container.getLocation();
					targetPath = path.toOSString();
				}
			}
		}
		return targetPath;
	}
}
