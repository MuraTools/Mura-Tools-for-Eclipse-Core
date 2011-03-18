/**
 * 
 */
package com.muratools.eclipse.wizard.newInstall;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;

/**
 * @author CHASXG
 *
 */
public class InstallMuraSettingsPage extends WizardPage {
	public Text textDatasourceName;
	public Text textDatasourceUsername;
	public Text textDatasourcePassword;
	public Text textMailServer;
	public Text textMailUsername;
	public Text textMailPassword;
	public Text textMailSMTPPort;
	public Text textMailPOPPort;
	public Combo comboDatasourceType;
	public Button btnUseTls;
	public Button btnUseSsl;
	
	private Button btnDatasourceRequiresCredentials;
	private Label lblUsername;
	private Label lblPassword;
	
	protected InstallMuraSettingsPage(ISelection selection){
		super("New Mura CMS Install");
		setTitle("Installation Settings");
		setDescription("Define the basic settings for the new Mura CMS install.");
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new FillLayout(SWT.VERTICAL));
		
		// Datasource inputs
		Group grpDatasource = new Group(composite, SWT.NONE);
		grpDatasource.setText("Datasource");
		grpDatasource.setLayout(new GridLayout(3, false));
		
		Label lblName = new Label(grpDatasource, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText("Name");
		
		textDatasourceName = new Text(grpDatasource, SWT.BORDER);
		textDatasourceName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblType = new Label(grpDatasource, SWT.NONE);
		lblType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblType.setText("Type");
		
		comboDatasourceType = new Combo(grpDatasource, SWT.READ_ONLY);
		comboDatasourceType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		comboDatasourceType.setItems(new String [] {"MSSQL","MySQL","Oracle","H2"});
		new Label(grpDatasource, SWT.NONE);
		
		btnDatasourceRequiresCredentials = new Button(grpDatasource, SWT.CHECK);
		btnDatasourceRequiresCredentials.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnDatasourceRequiresCredentials.setText("Datasource requires credentials");
		btnDatasourceRequiresCredentials.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				enableDisableDatasourceCredentialFields();
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				enableDisableDatasourceCredentialFields();
			}
			
			private void enableDisableDatasourceCredentialFields(){
				boolean enabled = btnDatasourceRequiresCredentials.getSelection();
				textDatasourceUsername.setEnabled(enabled);
				textDatasourcePassword.setEnabled(enabled);
				lblUsername.setEnabled(enabled);
				lblPassword.setEnabled(enabled);
				
				if (!enabled){
					textDatasourceUsername.setText("");
					textDatasourcePassword.setText("");
				}
			}
		});
		new Label(grpDatasource, SWT.NONE);
		
		lblUsername = new Label(grpDatasource, SWT.NONE);
		lblUsername.setEnabled(false);
		lblUsername.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblUsername.setText("Username");
		
		textDatasourceUsername = new Text(grpDatasource, SWT.BORDER);
		textDatasourceUsername.setEnabled(false);
		textDatasourceUsername.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(grpDatasource, SWT.NONE);
		
		lblPassword = new Label(grpDatasource, SWT.NONE);
		lblPassword.setEnabled(false);
		lblPassword.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPassword.setText("Password");
		
		textDatasourcePassword = new Text(grpDatasource, SWT.BORDER | SWT.PASSWORD);
		textDatasourcePassword.setEnabled(false);
		textDatasourcePassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		// Mail inputs
		Group grpMail = new Group(composite, SWT.NONE);
		grpMail.setText("Mail (optional)");
		grpMail.setLayout(new GridLayout(11, false));
		
		Label lblServer = new Label(grpMail, SWT.NONE);
		lblServer.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblServer.setText("Server");
		
		textMailServer = new Text(grpMail, SWT.BORDER);
		GridData gd_textMailServer = new GridData(SWT.FILL, SWT.CENTER, true, false, 10, 1);
		gd_textMailServer.widthHint = 448;
		textMailServer.setLayoutData(gd_textMailServer);
		
		Label lblUsername_1 = new Label(grpMail, SWT.NONE);
		lblUsername_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblUsername_1.setText("Username");
		
		textMailUsername = new Text(grpMail, SWT.BORDER);
		textMailUsername.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 10, 1));
		
		Label lblPassword_1 = new Label(grpMail, SWT.NONE);
		lblPassword_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPassword_1.setText("Password");
		
		textMailPassword = new Text(grpMail, SWT.BORDER | SWT.PASSWORD);
		textMailPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 10, 1));
		new Label(grpMail, SWT.NONE);
		
		btnUseTls = new Button(grpMail, SWT.CHECK);
		btnUseTls.setText("Use TLS");
		
		btnUseSsl = new Button(grpMail, SWT.CHECK);
		btnUseSsl.setText("Use SSL");
		new Label(grpMail, SWT.NONE);
		new Label(grpMail, SWT.NONE);
		new Label(grpMail, SWT.NONE);
		
		Label lblSmtpPort = new Label(grpMail, SWT.NONE);
		GridData gd_lblSmtpPort = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblSmtpPort.heightHint = 13;
		lblSmtpPort.setLayoutData(gd_lblSmtpPort);
		lblSmtpPort.setText("SMTP Port");
		
		textMailSMTPPort = new Text(grpMail, SWT.BORDER);
		textMailSMTPPort.setText("25");
		textMailSMTPPort.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(grpMail, SWT.NONE);
		
		Label lblPopPort = new Label(grpMail, SWT.NONE);
		lblPopPort.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPopPort.setText("POP Port");
		
		textMailPOPPort = new Text(grpMail, SWT.BORDER);
		textMailPOPPort.setText("110");
		textMailPOPPort.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		// These should be the last lines. Don't put display elements below this comment
		setControl(composite);
	}
	
}
