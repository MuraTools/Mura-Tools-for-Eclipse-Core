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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.muratools.eclipse.MuraToolsWizard;
import com.muratools.mura.IniSetting;

public class NewInstallWizard extends MuraToolsWizard {
	
	private InstallMuraPage page;
	private InstallMuraSettingsPage settingsPage;
	private String zipLocation = System.getProperty("java.io.tmpdir") + "/mura-latest.zip";
	
	public void addPages(){
		page = new InstallMuraPage(getSelection());
		addPage(page);
		settingsPage = new InstallMuraSettingsPage(getSelection());
		addPage(settingsPage);
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
		// deploy the contents of the mura zip file
		deployMuraZip();
		
		// edit the settings.ini.cfm file with collected data
		setupIniFile();
		
		// refresh the container
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
						// int contentLength = url.openConnection().getContentLength();
						
						// monitor.beginTask("Downloading Mura...", (int)Math.floor((double)contentLength / (double)44640));
						monitor.beginTask("Downloading Mura...", 5000); // 5000 is just the number of iterations I noticed it was taking to grab Mura, this is not very accurate
						
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
	
	private void setupIniFile() {
		String mode = "production";
		
		// create an arraylist to store all the collected settings. This will help us avoid threading issues when we pass the settings off to the builder that writes them to file.
		ArrayList<IniSetting> settingsArrayList = new ArrayList<IniSetting>();
		
		// settings section
		settingsArrayList.add(new IniSetting("settings", "appname", settingsPage.textAppName.getText()));
		settingsArrayList.add(new IniSetting("settings", "appreloadkey", settingsPage.textReloadKey.getText()));
		
		// setup datasource
		settingsArrayList.add(new IniSetting(mode, "datasource", settingsPage.textDatasourceName.getText()));
		settingsArrayList.add(new IniSetting(mode,"dbtype", settingsPage.comboDatasourceType.getText().toLowerCase()));
		settingsArrayList.add(new IniSetting(mode, "dbusername", settingsPage.textDatasourceUsername.getText()));
		settingsArrayList.add(new IniSetting(mode, "dbpassword", settingsPage.textDatasourcePassword.getText()));
		
		// setup mail settings
		settingsArrayList.add(new IniSetting(mode, "mailserverip", settingsPage.textMailServer.getText()));
		settingsArrayList.add(new IniSetting(mode, "mailserversmtpport",settingsPage.spinnerMailSMTPPort.getText()));
		settingsArrayList.add(new IniSetting(mode, "mailserverpopport", settingsPage.spinnerMailPOPPort.getText()));
		settingsArrayList.add(new IniSetting(mode, "mailserverusername", settingsPage.textMailUsername.getText()));
		settingsArrayList.add(new IniSetting(mode, "mailserverpassword", settingsPage.textMailPassword.getText()));
		settingsArrayList.add(new IniSetting(mode, "mailservertls", settingsPage.btnUseTls.getSelection()));
		settingsArrayList.add(new IniSetting(mode, "mailserverssl", settingsPage.btnUseSsl.getSelection()));
		settingsArrayList.add(new IniSetting(mode, "usedefaultsmtpserver", settingsPage.btnUseDefaultSMTPServer.getSelection() ? 1 : 0));
		
		// other settings
		settingsArrayList.add(new IniSetting(mode, "title", settingsPage.textTitle.getText()));
		settingsArrayList.add(new IniSetting(mode, "adminemail", settingsPage.textAdminEmail.getText()));
		settingsArrayList.add(new IniSetting(mode, "filedir", settingsPage.textFileDir.getText()));
		settingsArrayList.add(new IniSetting(mode, "filestore", settingsPage.comboFileStore.getText()));
		settingsArrayList.add(new IniSetting(mode, "filestoreaccessinfo", settingsPage.comboFileStore.getText() == "s3" ? settingsPage.textS3AccessKey.getText() + "^" + settingsPage.textS3SecretKey.getText() + "^" + settingsPage.textS3Bucket.getText() : ""));
		settingsArrayList.add(new IniSetting(mode, "assetpath", settingsPage.textAssetPath.getText()));
		settingsArrayList.add(new IniSetting(mode, "context", settingsPage.textContext.getText()));
		settingsArrayList.add(new IniSetting(mode, "stub", settingsPage.comboStub.getText()));
		settingsArrayList.add(new IniSetting(mode, "admindomain", settingsPage.textAdminDomain.getText()));
		settingsArrayList.add(new IniSetting(mode, "adminssl", settingsPage.btnUseSslForAdmin.getSelection() ? 1 : 0));
		settingsArrayList.add(new IniSetting(mode, "logevents", settingsPage.btnLogEvents.getSelection() ? 1 : 0));
		settingsArrayList.add(new IniSetting(mode, "debuggingenabled", settingsPage.btnDebuggingEnabled.getSelection()));
		settingsArrayList.add(new IniSetting(mode, "port", settingsPage.spinnerPort.getText()));
		settingsArrayList.add(new IniSetting(mode, "sessionhistory", settingsPage.spinnerSessionHistory.getText()));
		settingsArrayList.add(new IniSetting(mode, "sharableremotesessions", settingsPage.btnSharableRemoteSessions.getSelection() ? 1 : 0));
		settingsArrayList.add(new IniSetting(mode, "dashboard", settingsPage.btnEnableDashboard.getSelection()));
		settingsArrayList.add(new IniSetting(mode, "locale", settingsPage.comboLocale.getText()));
		settingsArrayList.add(new IniSetting(mode, "ping", settingsPage.btnPing.getSelection() ? 1 : 0));
		settingsArrayList.add(new IniSetting(mode, "enablemuratag", settingsPage.btnEnableMuraTag.getSelection()));
		settingsArrayList.add(new IniSetting(mode, "proxyuser", settingsPage.textProxyUser.getText()));
		settingsArrayList.add(new IniSetting(mode, "proxypassword", settingsPage.textProxyPassword.getText()));
		settingsArrayList.add(new IniSetting(mode, "proxyserver", settingsPage.textProxyServer.getText()));
		settingsArrayList.add(new IniSetting(mode, "proxyport", settingsPage.spinnerProxyPort.getText()));
		settingsArrayList.add(new IniSetting(mode, "sortpermission", settingsPage.textSortPermission.getText()));
		settingsArrayList.add(new IniSetting(mode, "imageinterpolation", settingsPage.comboImageInterpolation.getText()));
		settingsArrayList.add(new IniSetting(mode, "clusterlist", settingsPage.textClusterList.getText()));
		settingsArrayList.add(new IniSetting(mode, "siteidinurls", settingsPage.btnSiteIdInURLs.getSelection() ? 1 : 0));
		settingsArrayList.add(new IniSetting(mode, "indexfileinurls", settingsPage.btnIndexInUrls.getSelection() ? 1 : 0));
		settingsArrayList.add(new IniSetting(mode, "strictExtendedData", settingsPage.btnStrictExtendedData.getSelection() ? 1 : 0));
		settingsArrayList.add(new IniSetting(mode, "loginStrikes", settingsPage.spinnerLoginStrikes.getText()));
		settingsArrayList.add(new IniSetting(mode, "tempDir", settingsPage.textTempDir.getText()));
		settingsArrayList.add(new IniSetting(mode, "purgeDrafts", settingsPage.btnPurgeDrafts.getSelection()));
		settingsArrayList.add(new IniSetting(mode, "confirmSaveAsDraft", settingsPage.btnConfirmSaveAsDraft.getSelection()));
		settingsArrayList.add(new IniSetting(mode, "notifyWithVersionLink", settingsPage.btnNotifyWithVersion.getSelection()));
		settingsArrayList.add(new IniSetting(mode, "autoresetpasswords", settingsPage.btnAutoResetPasswords.getSelection()));
		settingsArrayList.add(new IniSetting(mode, "htmleditortype", settingsPage.comboHTMLEditorType.getText()));
		
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(getShell());
		try {
			NewInstallIniEditor editor = new NewInstallIniEditor(getTargetDirectory(), settingsArrayList);
			dialog.run(true, true, editor);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
