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
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Spinner;

/**
 * @author CHASXG
 *
 */
public class InstallMuraSettingsPage extends WizardPage {
	public TabFolder tabFolder;
	public Text textDatasourceName;
	public Text textDatasourceUsername;
	public Text textDatasourcePassword;
	public Text textMailServer;
	public Text textAdminEmail;
	public Text textMailUsername;
	public Text textMailPassword;
	public Combo comboDatasourceType;
	public Spinner spinnerMailSMTPPort;
	public Spinner spinnerMailPOPPort;
	public Button btnUseTls;
	public Button btnUseSsl;
	
	private Button btnRequiresCredentials;
	private Label lblDatasourceUsername;
	private Label lblDatasourcePassword;
	
	protected InstallMuraSettingsPage(ISelection selection){
		super("New Mura CMS Install");
		setTitle("Installation Settings");
		setDescription("Define the settings for the new Mura CMS install.");
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new FillLayout(SWT.VERTICAL));
		
		tabFolder = new TabFolder(composite, SWT.NONE);
		
		TabItem tabDatasource = new TabItem(tabFolder, SWT.NONE);
		tabDatasource.setText("Required");
		
		Composite compositeDatasource = new Composite(tabFolder, SWT.NONE);
		tabDatasource.setControl(compositeDatasource);
		compositeDatasource.setLayout(new GridLayout(3, false));
		
		Label lblName = new Label(compositeDatasource, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText("Datasource Name *");
		
		textDatasourceName = new Text(compositeDatasource, SWT.BORDER);
		textDatasourceName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblType = new Label(compositeDatasource, SWT.NONE);
		lblType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblType.setText("Datasource Type *");
		
		comboDatasourceType = new Combo(compositeDatasource, SWT.READ_ONLY);
		comboDatasourceType.setItems(new String[] {"MSSQL", "MySQL", "Oracle", "H2"});
		comboDatasourceType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		new Label(compositeDatasource, SWT.NONE);
		new Label(compositeDatasource, SWT.NONE);
		new Label(compositeDatasource, SWT.NONE);
		new Label(compositeDatasource, SWT.NONE);
		
		btnRequiresCredentials = new Button(compositeDatasource, SWT.CHECK);
		btnRequiresCredentials.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnRequiresCredentials.setText("Datasource requires credentials");
		btnRequiresCredentials.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				handleSelection();
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				handleSelection();
			}
			
			private void handleSelection(){
				boolean enabled = btnRequiresCredentials.getSelection();
				lblDatasourceUsername.setEnabled(enabled);
				lblDatasourcePassword.setEnabled(enabled);
				
				textDatasourceUsername.setEnabled(enabled);
				textDatasourceUsername.setText("");
				
				textDatasourcePassword.setEnabled(enabled);
				textDatasourcePassword.setText("");
			}
		});
		new Label(compositeDatasource, SWT.NONE);
		
		lblDatasourceUsername = new Label(compositeDatasource, SWT.NONE);
		lblDatasourceUsername.setEnabled(false);
		lblDatasourceUsername.setText("Datasource Username");
		
		textDatasourceUsername = new Text(compositeDatasource, SWT.BORDER);
		textDatasourceUsername.setEnabled(false);
		textDatasourceUsername.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(compositeDatasource, SWT.NONE);
		
		lblDatasourcePassword = new Label(compositeDatasource, SWT.NONE);
		lblDatasourcePassword.setEnabled(false);
		lblDatasourcePassword.setText("Datasource Password");
		
		textDatasourcePassword = new Text(compositeDatasource, SWT.BORDER | SWT.PASSWORD);
		textDatasourcePassword.setEnabled(false);
		textDatasourcePassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(compositeDatasource, SWT.NONE);
		new Label(compositeDatasource, SWT.NONE);
		new Label(compositeDatasource, SWT.NONE);
		new Label(compositeDatasource, SWT.NONE);
		new Label(compositeDatasource, SWT.NONE);
		new Label(compositeDatasource, SWT.NONE);
		
		Label lblAdminEmail = new Label(compositeDatasource, SWT.NONE);
		lblAdminEmail.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAdminEmail.setText("Admin Email *");
		
		textAdminEmail = new Text(compositeDatasource, SWT.BORDER);
		textAdminEmail.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		new Label(compositeDatasource, SWT.NONE);
		new Label(compositeDatasource, SWT.NONE);
		new Label(compositeDatasource, SWT.NONE);
		new Label(compositeDatasource, SWT.NONE);
		new Label(compositeDatasource, SWT.NONE);
		new Label(compositeDatasource, SWT.NONE);
		new Label(compositeDatasource, SWT.NONE);
		new Label(compositeDatasource, SWT.NONE);
		new Label(compositeDatasource, SWT.NONE);
		new Label(compositeDatasource, SWT.NONE);
		new Label(compositeDatasource, SWT.NONE);
		
		Label lblRequired = new Label(compositeDatasource, SWT.NONE);
		lblRequired.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblRequired.setText("* = Required Field");
		
		TabItem tabMail = new TabItem(tabFolder, SWT.NONE);
		tabMail.setText("Mail (optional)");
		
		Composite compositeMail = new Composite(tabFolder, SWT.NONE);
		tabMail.setControl(compositeMail);
		compositeMail.setLayout(new GridLayout(11, false));
		
		Label lblServer = new Label(compositeMail, SWT.NONE);
		lblServer.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblServer.setText("Server");
		
		textMailServer = new Text(compositeMail, SWT.BORDER);
		textMailServer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 10, 1));
		
		Label lblUsername_1 = new Label(compositeMail, SWT.NONE);
		lblUsername_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblUsername_1.setText("Username");
		
		textMailUsername = new Text(compositeMail, SWT.BORDER);
		textMailUsername.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 10, 1));
		
		Label lblPassword_1 = new Label(compositeMail, SWT.NONE);
		lblPassword_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPassword_1.setText("Password");
		
		textMailPassword = new Text(compositeMail, SWT.BORDER | SWT.PASSWORD);
		textMailPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 10, 1));
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		
		Label lblSmtpPort = new Label(compositeMail, SWT.NONE);
		lblSmtpPort.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSmtpPort.setText("SMTP Port");
		
		spinnerMailSMTPPort = new Spinner(compositeMail, SWT.BORDER);
		spinnerMailSMTPPort.setMaximum(99999);
		spinnerMailSMTPPort.setMinimum(1);
		spinnerMailSMTPPort.setSelection(25);
		spinnerMailSMTPPort.setPageIncrement(1);
		new Label(compositeMail, SWT.NONE);
		
		Label lblPopPort = new Label(compositeMail, SWT.NONE);
		lblPopPort.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPopPort.setText("POP Port");
		
		spinnerMailPOPPort = new Spinner(compositeMail, SWT.BORDER);
		spinnerMailPOPPort.setMaximum(999999);
		spinnerMailPOPPort.setMinimum(1);
		spinnerMailPOPPort.setSelection(110);
		spinnerMailPOPPort.setPageIncrement(1);
		new Label(compositeMail, SWT.NONE);
		
		btnUseTls = new Button(compositeMail, SWT.CHECK);
		btnUseTls.setText("Use TLS");
		new Label(compositeMail, SWT.NONE);
		
		btnUseSsl = new Button(compositeMail, SWT.CHECK);
		btnUseSsl.setText("Use SSL");
		
		TabItem tbtmAdvanced = new TabItem(tabFolder, SWT.NONE);
		tbtmAdvanced.setText("Advanced");
		
		Composite compositeAdvanced = new Composite(tabFolder, SWT.NONE);
		tbtmAdvanced.setControl(compositeAdvanced);
		compositeAdvanced.setLayout(new GridLayout(1, false));
		
		// These should be the last lines. Don't put display elements below this comment
		setControl(composite);
	}
	
}
