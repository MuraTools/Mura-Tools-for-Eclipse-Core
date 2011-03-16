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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.muratools.eclipse.MuraToolsWizard;

public class NewInstallWizard extends MuraToolsWizard {
	
	private InstallMuraPage page;
	private String zipLocation = System.getProperty("java.io.tmpdir") + "/mura-latest.zip";
	
	public void addPages(){
		page = new InstallMuraPage(getSelection());
		addPage(page);
	}
	
	@Override
	public boolean performFinish(){
		if (page.radios[0].getSelection()){
			// download is selected
			downloadMura();
		} else {
			// local zip is selected
			if (page.fileField.getText().length() == 0 || page.fileField.getText((page.fileField.getText().length() - 4), page.fileField.getText().length()).equals(".zip") == false){
				MessageDialog.openError(getShell(), "Invalid File Selected", "Please select a valid zip file or select another option");
				return false;
			}
			zipLocation = page.fileField.getText();
		}
		deployMuraZip();
		refreshContainer();
		return true;
	}
	
	private void downloadMura(){
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(getShell());
		try {
			dialog.run(true, true, new IRunnableWithProgress() {
				
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					//monitor.beginTask("Downloading Mura...", 100);
					
					try {
						URL url = new URL("http://www.getmura.com/currentversion/?source=muratools");
						int contentLength = url.openConnection().getContentLength();
						
						monitor.beginTask("Downloading Mura...", (int)Math.floor((double)contentLength / (double)44640));
						
						InputStream reader = url.openStream();
						
						FileOutputStream writer = new FileOutputStream(zipLocation);
						byte[] buffer = new byte[44640];
						int totalBytesRead = 0;
						int bytesRead = 0;
						
						int tp = 0;
						while ((bytesRead = reader.read(buffer)) > 0){
							writer.write(buffer, 0, bytesRead);
							buffer = new byte[44640];
							totalBytesRead += bytesRead;
							
							tp++;
							System.err.println(tp + " / " + Integer.toString((int)Math.ceil((double)contentLength / (double)44640)) + "[" + bytesRead + "]");
							
							monitor.worked(1);
						}
						
						writer.close();
						reader.close();
					} catch (MalformedURLException e){
						e.printStackTrace();
					} catch (IOException e){
						e.printStackTrace();
					}
					
					monitor.done();
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static final void copyInputStream(InputStream in, OutputStream out)
	  throws IOException
	  {
	    byte[] buffer = new byte[1024];
	    int len;

	    while((len = in.read(buffer)) >= 0)
	      out.write(buffer, 0, len);

	    in.close();
	    out.close();
	  }
	
	private void deployMuraZip(){
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(getShell());
		try {
			dialog.run(true, true, new IRunnableWithProgress() {
				
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					//monitor.beginTask("Unzipping Mura...", IProgressMonitor.UNKNOWN);
					
					try {
						Enumeration entries;
						ZipFile zipFile;

						zipFile = new ZipFile(zipLocation);

						entries = zipFile.entries();
						
						monitor.beginTask("Unzipping Mura...", zipFile.size());
						
						while(entries.hasMoreElements()) {
							ZipEntry entry = (ZipEntry)entries.nextElement();

							if (entry.getName().substring(0, 4).equals("www/")){
								if(entry.isDirectory()) {
									// Assume directories are stored parents first then children.
									//System.err.println("Extracting directory: " + getTargetDirectory() + "/" + entry.getName().replace("www/", ""));
									// This is not robust, just for demonstration purposes.
									(new File(getTargetDirectory() + "/" + entry.getName().replace("www/", ""))).mkdir();
									continue;
								}

								//System.err.println("Extracting file: " + getTargetDirectory() + "/" + entry.getName().replace("www/", ""));
								copyInputStream(zipFile.getInputStream(entry),
										new BufferedOutputStream(new FileOutputStream(getTargetDirectory() + "/" + entry.getName().replace("www/", ""))));
							}
							monitor.worked(1);
						}
						zipFile.close();

					} catch (IOException e){
						e.printStackTrace();
					}
					
					monitor.done();
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
