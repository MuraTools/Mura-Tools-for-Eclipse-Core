/**
 * 
 */
package com.muratools.eclipse;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;

/**
 * @author Steve
 *
 */
public class MuraToolsWizard extends Wizard {

	private IStructuredSelection selection;
	
	public IStructuredSelection getSelection() {
		return selection;
	}

	public void setSelection(IStructuredSelection selection) {
		this.selection = selection;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		return false;
	}
	
	public void init(IWorkbench workbench, IStructuredSelection selection){
		setSelection(selection);
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
	
	public void refreshContainer(){
		if (getSelection() != null && getSelection().isEmpty() == false && getSelection() instanceof IStructuredSelection){
			IStructuredSelection ssel = (IStructuredSelection)getSelection();
			
			if (ssel.size() > 1){
				return;
			} else {
				Object obj = ssel.getFirstElement();
				
				if (obj instanceof IResource){
					IContainer container = (IContainer)obj;
					try {
						container.refreshLocal(IResource.DEPTH_INFINITE, null);
					} catch (CoreException e){
						e.printStackTrace();
					}
				}
			}
		}
	}
}
