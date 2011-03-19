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
import org.eclipse.swt.events.SelectionAdapter;

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
	public Text textAppName;
	public Text textTitle;
	public Text textReloadKey;
	public Combo comboFileStore;
	public Text textFileDir;
	public Text textS3AccessKey;
	public Text textS3SecretKey;
	public Text textS3Bucket;
	public Text textAssetPath;
	public Text textContext;
	public Combo comboStub;
	public Text textAdminDomain;
	public Button btnUseSslForAdmin;
	public Button btnLogEvents;
	public Button btnDebuggingEnabled;
	public Spinner spinnerPort;
	public Spinner spinnerSessionHistory;
	public Button btnSharableRemoteSessions;
	public Button btnEnableDashboard;
	public Button btnPing;
	public Button btnEnableMuraTag;
	public Text textProxyUser;
	public Text textProxyServer;
	public Text textProxyPassword;
	public Text textSortPermission;
	public Text textClusterList;
	public Text textTempDir;
	public Text textEncryptionKey;
	public Combo comboLocale;
	public Spinner spinnerProxyPort;
	public Combo comboHTMLEditorType;
	public Spinner spinnerSessionTimeout;
	public Button btnAutoResetPasswords;
	public Combo comboImageInterpolation;
	public Button btnNotifyWithVersion;
	public Button btnSiteIdInURLs;
	public Button btnUseDefaultSMTPServer;
	public Button btnIndexInUrls;
	public Button btnStrictExtendedData;
	public Spinner spinnerLoginStrikes;
	public Button btnPurgeDrafts;
	public Button btnConfirmSaveAsDraft;
	
	
	private Button btnRequiresCredentials;
	private Label lblDatasourceUsername;
	private Label lblDatasourcePassword;
	private Label lblTitle;
	private Label lblAppName;
	private Label lblReloadKey;
	private Label lblFileStore;
	private Label lblFileDir;
	private Label lblS3AccessKey;
	private Label lblS3SecretKey;
	private Label lblS3Bucket;
	private Label lblAssetPath;
	private Label lblContext;
	private Label lblStub;
	private Label lblAdminDomain;
	private Label lblPort;
	private TabItem tbtmAdvanced_1;
	private Composite compositeAdvanced2;
	private Label lblSessionHistory;
	private Label lblLocale;
	private TabItem tbtmProxy;
	private Composite compositeProxy;
	private Label lblPopPort;
	private Label lblMailPassword;
	private Label lblMailServer;
	private Label lblMailUsername;
	private Label lblSmtpPort;
	
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
		textDatasourceName.setToolTipText("This the Data Source Name (DSN) created for Mura. This is usually done in the ColdFusion or Railo administrator, or in the control panel of your host if you are installing Mura in a shared environment. Please note that if you are installing Mura in a shared environment, this will likely need to be changed to something other than \"muradb\" to make sure it is unique to the server.");
		textDatasourceName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblType = new Label(compositeDatasource, SWT.NONE);
		lblType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblType.setText("Datasource Type *");
		
		comboDatasourceType = new Combo(compositeDatasource, SWT.READ_ONLY);
		comboDatasourceType.setToolTipText("This sets the type of database you are using.");
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
		textDatasourceUsername.setToolTipText("This is the username used to log into your database.");
		textDatasourceUsername.setEnabled(false);
		textDatasourceUsername.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(compositeDatasource, SWT.NONE);
		
		lblDatasourcePassword = new Label(compositeDatasource, SWT.NONE);
		lblDatasourcePassword.setEnabled(false);
		lblDatasourcePassword.setText("Datasource Password");
		
		textDatasourcePassword = new Text(compositeDatasource, SWT.BORDER | SWT.PASSWORD);
		textDatasourcePassword.setToolTipText("This is the password used to log into your database.");
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
		textAdminEmail.setToolTipText("Secondary email settings for emails that need to be sent that are not associated with a specific site such as the Mura admin.\nExample: user@domain.com");
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
		
		lblMailServer = new Label(compositeMail, SWT.NONE);
		lblMailServer.setEnabled(false);
		lblMailServer.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMailServer.setText("Server");
		
		textMailServer = new Text(compositeMail, SWT.BORDER);
		textMailServer.setEnabled(false);
		textMailServer.setToolTipText("The server used to send emails from Mura.\nExample: mail.domain.com, 267.11.98.23");
		textMailServer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 10, 1));
		
		lblMailUsername = new Label(compositeMail, SWT.NONE);
		lblMailUsername.setEnabled(false);
		lblMailUsername.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMailUsername.setText("Username");
		
		textMailUsername = new Text(compositeMail, SWT.BORDER);
		textMailUsername.setEnabled(false);
		textMailUsername.setToolTipText("This is the username used to log into or send emails from the adminemail account.");
		textMailUsername.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 10, 1));
		
		lblMailPassword = new Label(compositeMail, SWT.NONE);
		lblMailPassword.setEnabled(false);
		lblMailPassword.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMailPassword.setText("Password");
		
		textMailPassword = new Text(compositeMail, SWT.BORDER | SWT.PASSWORD);
		textMailPassword.setEnabled(false);
		textMailPassword.setToolTipText("This is the password used to log into the adminemail account.");
		textMailPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 10, 1));
		new Label(compositeMail, SWT.NONE);
		
		lblSmtpPort = new Label(compositeMail, SWT.NONE);
		lblSmtpPort.setEnabled(false);
		lblSmtpPort.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSmtpPort.setText("SMTP Port");
		
		spinnerMailSMTPPort = new Spinner(compositeMail, SWT.BORDER);
		spinnerMailSMTPPort.setEnabled(false);
		spinnerMailSMTPPort.setToolTipText("Port of the mail server you'd like Mura to use to send emails such as review notifications, login/password resets, Email Broadcaster email, etc. Defaults to 25.");
		spinnerMailSMTPPort.setMaximum(99999);
		spinnerMailSMTPPort.setMinimum(1);
		spinnerMailSMTPPort.setSelection(25);
		spinnerMailSMTPPort.setPageIncrement(1);
		
		lblPopPort = new Label(compositeMail, SWT.NONE);
		lblPopPort.setEnabled(false);
		lblPopPort.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPopPort.setText("POP Port");
		
		spinnerMailPOPPort = new Spinner(compositeMail, SWT.BORDER);
		spinnerMailPOPPort.setEnabled(false);
		spinnerMailPOPPort.setToolTipText("Port of the mail server you'd like Mura to check for bounced emails from the Email Broadcaster. Defaults to 110.");
		spinnerMailPOPPort.setMaximum(999999);
		spinnerMailPOPPort.setMinimum(1);
		spinnerMailPOPPort.setSelection(110);
		spinnerMailPOPPort.setPageIncrement(1);
		new Label(compositeMail, SWT.NONE);
		
		btnUseTls = new Button(compositeMail, SWT.CHECK);
		btnUseTls.setEnabled(false);
		btnUseTls.setText("Use TLS");
		new Label(compositeMail, SWT.NONE);
		
		btnUseSsl = new Button(compositeMail, SWT.CHECK);
		btnUseSsl.setEnabled(false);
		btnUseSsl.setText("Use SSL");
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		
		btnUseDefaultSMTPServer = new Button(compositeMail, SWT.CHECK);
		btnUseDefaultSMTPServer.setSelection(true);
		btnUseDefaultSMTPServer.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnUseDefaultSMTPServer.setToolTipText("Enabling this will allow Mura to use the default SMTP server specified in your application server. Alternatively, you can use a different email server by specifying the mailserverip, mailserverusername and mailserverpassword below.");
		btnUseDefaultSMTPServer.setEnabled(true);
		btnUseDefaultSMTPServer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean enableFields = !btnUseDefaultSMTPServer.getSelection();
				
				lblMailServer.setEnabled(enableFields);
				textMailServer.setText("");
				textMailServer.setEnabled(enableFields);
				
				lblMailUsername.setEnabled(enableFields);
				textMailUsername.setText("");
				textMailUsername.setEnabled(enableFields);
				
				lblMailPassword.setEnabled(enableFields);
				textMailPassword.setText("");
				textMailPassword.setEnabled(enableFields);
				
				btnUseSsl.setEnabled(enableFields);
				btnUseSsl.setSelection(false);
				
				btnUseTls.setEnabled(enableFields);
				btnUseTls.setSelection(false);
				
				spinnerMailPOPPort.setEnabled(enableFields);
				spinnerMailPOPPort.setSelection(110);
				
				spinnerMailSMTPPort.setEnabled(enableFields);
				spinnerMailSMTPPort.setSelection(25);
			}
		});
		btnUseDefaultSMTPServer.setText("Use Default SMTP Server");
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		new Label(compositeMail, SWT.NONE);
		
		TabItem tbtmAdvanced = new TabItem(tabFolder, SWT.NONE);
		tbtmAdvanced.setText("Advanced");
		
		Composite compositeAdvanced = new Composite(tabFolder, SWT.NONE);
		tbtmAdvanced.setControl(compositeAdvanced);
		compositeAdvanced.setLayout(new GridLayout(4, false));
		
		lblAppName = new Label(compositeAdvanced, SWT.NONE);
		lblAppName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAppName.setText("App Name");
		
		textAppName = new Text(compositeAdvanced, SWT.BORDER);
		textAppName.setText("Mura CMS");
		textAppName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblAssetPath = new Label(compositeAdvanced, SWT.NONE);
		lblAssetPath.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAssetPath.setText("Asset Path");
		
		textAssetPath = new Text(compositeAdvanced, SWT.BORDER);
		textAssetPath.setToolTipText("The URL to where assets are stored. Can be a domain or root relative path.");
		textAssetPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblTitle = new Label(compositeAdvanced, SWT.NONE);
		lblTitle.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTitle.setText("Title");
		
		textTitle = new Text(compositeAdvanced, SWT.BORDER);
		textTitle.setToolTipText("This prepends the Site Name in the <title> tag of the Mura Admin.");
		textTitle.setText("Mura CMS");
		textTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblContext = new Label(compositeAdvanced, SWT.NONE);
		lblContext.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblContext.setText("Context");
		
		textContext = new Text(compositeAdvanced, SWT.BORDER);
		textContext.setToolTipText("If you are installing Mura into a sub-directory, you will need to set the context to that directory.\nExample: /nameofdirectory (no trailing slash)");
		textContext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblReloadKey = new Label(compositeAdvanced, SWT.NONE);
		lblReloadKey.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblReloadKey.setText("Reload Key");
		
		textReloadKey = new Text(compositeAdvanced, SWT.BORDER);
		textReloadKey.setToolTipText("Name of the url variable used to reload Mura. Best practice suggests changing this for security through obscurity.");
		textReloadKey.setText("appreload");
		textReloadKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblStub = new Label(compositeAdvanced, SWT.NONE);
		lblStub.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblStub.setText("Stub");
		
		comboStub = new Combo(compositeAdvanced, SWT.NONE);
		comboStub.setToolTipText("Used to define the friendly URL servlet for application servers that do not support index.cfm/about-us, or if you just prefer taht format for URLs.");
		comboStub.setItems(new String[] {"go"});
		comboStub.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblFileStore = new Label(compositeAdvanced, SWT.NONE);
		lblFileStore.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFileStore.setText("File Store");
		
		comboFileStore = new Combo(compositeAdvanced, SWT.READ_ONLY);
		comboFileStore.setToolTipText("Defines where you store content-node associated files such as a gallery item, or file.");
		comboFileStore.setItems(new String[] {"database", "fileDir", "s3"});
		comboFileStore.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboFileStore.setText("fileDir");
		comboFileStore.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				handleSelection();
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				handleSelection();
			}
			
			private void handleSelection(){
				String selectedText = comboFileStore.getText();
				
				lblFileDir.setEnabled(selectedText == "fileDir");
				textFileDir.setEnabled(selectedText == "fileDir");
				textFileDir.setText("");
				
				lblS3AccessKey.setEnabled(selectedText == "s3");
				textS3AccessKey.setEnabled(selectedText == "s3");
				textS3AccessKey.setText("");
				
				lblS3SecretKey.setEnabled(selectedText == "s3");
				textS3SecretKey.setEnabled(selectedText == "s3");
				textS3SecretKey.setText("");
				
				lblS3Bucket.setEnabled(selectedText == "s3");
				textS3Bucket.setEnabled(selectedText == "s3");
				textS3Bucket.setText("");
			}
		});
		
		lblAdminDomain = new Label(compositeAdvanced, SWT.NONE);
		lblAdminDomain.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAdminDomain.setText("Admin Domain");
		
		textAdminDomain = new Text(compositeAdvanced, SWT.BORDER);
		textAdminDomain.setToolTipText("Optional. Binds the Mura admin to a specific domain especially for SSL or obscuring access to the Mura admin.\nExample: admin.yoursite.com");
		textAdminDomain.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblFileDir = new Label(compositeAdvanced, SWT.NONE);
		lblFileDir.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFileDir.setText("File Dir");
		
		textFileDir = new Text(compositeAdvanced, SWT.BORDER);
		textFileDir.setToolTipText("Optional. This redefines the file system path for storing site assets that are uploaded through the editor, component cacheing, files associated with a specific content node. If this is not set, it will place all assets into the local site ID directory. Defaults to the Mura webroot (not the webrootmap set here)+assetpath-the context.");
		textFileDir.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(compositeAdvanced, SWT.NONE);
		
		btnUseSslForAdmin = new Button(compositeAdvanced, SWT.CHECK);
		btnUseSslForAdmin.setToolTipText("If you have an SSL certificate on your domain.");
		btnUseSslForAdmin.setText("Use SSL for Admin");
		
		lblS3AccessKey = new Label(compositeAdvanced, SWT.NONE);
		lblS3AccessKey.setEnabled(false);
		lblS3AccessKey.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblS3AccessKey.setText("S3 Access Key");
		
		textS3AccessKey = new Text(compositeAdvanced, SWT.BORDER);
		textS3AccessKey.setEnabled(false);
		textS3AccessKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(compositeAdvanced, SWT.NONE);
		
		btnLogEvents = new Button(compositeAdvanced, SWT.CHECK);
		btnLogEvents.setToolTipText("Creates log files for tracking events in Mura.");
		btnLogEvents.setText("Log Events");
		
		lblS3SecretKey = new Label(compositeAdvanced, SWT.NONE);
		lblS3SecretKey.setEnabled(false);
		lblS3SecretKey.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblS3SecretKey.setText("S3 Secret Key");
		
		textS3SecretKey = new Text(compositeAdvanced, SWT.BORDER);
		textS3SecretKey.setEnabled(false);
		textS3SecretKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(compositeAdvanced, SWT.NONE);
		
		btnDebuggingEnabled = new Button(compositeAdvanced, SWT.CHECK);
		btnDebuggingEnabled.setToolTipText("Toggles debugging on errors between the default app server debugging output or the error.html file found in /config.");
		btnDebuggingEnabled.setSelection(true);
		btnDebuggingEnabled.setText("Debugging Enabled");
		
		lblS3Bucket = new Label(compositeAdvanced, SWT.NONE);
		lblS3Bucket.setEnabled(false);
		lblS3Bucket.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblS3Bucket.setText("S3 Bucket");
		
		textS3Bucket = new Text(compositeAdvanced, SWT.BORDER);
		textS3Bucket.setEnabled(false);
		textS3Bucket.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblPort = new Label(compositeAdvanced, SWT.NONE);
		lblPort.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPort.setText("Port");
		
		spinnerPort = new Spinner(compositeAdvanced, SWT.BORDER);
		spinnerPort.setToolTipText("This is the default http port for your webserver. If you need to use another such as 8500 (for a local ColdFusion deployment), set it here. Defaults to 80.");
		spinnerPort.setPageIncrement(1);
		spinnerPort.setMaximum(999999);
		spinnerPort.setMinimum(1);
		spinnerPort.setSelection(80);
		
		tbtmAdvanced_1 = new TabItem(tabFolder, SWT.NONE);
		tbtmAdvanced_1.setText("Advanced 2");
		
		compositeAdvanced2 = new Composite(tabFolder, SWT.NONE);
		tbtmAdvanced_1.setControl(compositeAdvanced2);
		compositeAdvanced2.setLayout(new GridLayout(4, false));
		
		lblSessionHistory = new Label(compositeAdvanced2, SWT.NONE);
		lblSessionHistory.setText("Session History");
		
		spinnerSessionHistory = new Spinner(compositeAdvanced2, SWT.BORDER);
		spinnerSessionHistory.setToolTipText("This sets the number of days that will be stored for session tracking stats in the Mura Dashboard and associated reports. This has the potential to impact database storage significantly so for sites with more traffic, it should be adjusted as necessary. Defaults to 1 day.");
		spinnerSessionHistory.setPageIncrement(1);
		spinnerSessionHistory.setMaximum(365);
		spinnerSessionHistory.setMinimum(1);
		spinnerSessionHistory.setSelection(1);
		new Label(compositeAdvanced2, SWT.NONE);
		
		btnSiteIdInURLs = new Button(compositeAdvanced2, SWT.CHECK);
		btnSiteIdInURLs.setToolTipText("Toggles the siteID (i.e. default, mynewsite, etc.) in URLS to give you a URL of http://www.yoursite.com/index.cfm/about/ instead of http://www.yoursite.com/default/index.cfm/about/. Important: If siteID's are toggled off, each site MUST have a unique URL assigned to it in \"Site Settings\". To remove the index.cfm from URLs, you must use URL rewriting on your webserver via ISAPI rewrite (IIS) or mod_rewrite (apache).");
		btnSiteIdInURLs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnSiteIdInURLs.setSelection(true);
		btnSiteIdInURLs.setText("Site ID in URLs");
		new Label(compositeAdvanced2, SWT.NONE);
		
		btnSharableRemoteSessions = new Button(compositeAdvanced2, SWT.CHECK);
		btnSharableRemoteSessions.setSelection(true);
		btnSharableRemoteSessions.setText("Sharable Remote Sessions");
		new Label(compositeAdvanced2, SWT.NONE);
		
		btnIndexInUrls = new Button(compositeAdvanced2, SWT.CHECK);
		btnIndexInUrls.setToolTipText("Toggles the beginning \"/index.cfm\" from the in URLS to give you a URL of http://www.yoursite.com/about/ instead of http://www.yoursite.com/index.cfm/about/. Important: This must be used in conjunction with setting the above siteidinurls attribute to 0. You must also use URL rewriting on your webserver via ISAPI rewrite (IIS) or mod_rewrite (apache) to write requested URLs back to http://www.yoursite.com/index.cfm/about/.");
		btnIndexInUrls.setSelection(true);
		btnIndexInUrls.setText("Index File in URLs");
		new Label(compositeAdvanced2, SWT.NONE);
		
		btnEnableDashboard = new Button(compositeAdvanced2, SWT.CHECK);
		btnEnableDashboard.setToolTipText("Toggles the Mura Dashboard on or off. If you 'd like to not use the Mura Dashboard and have users directed to the Mura Site Manager instead, set this to false.");
		btnEnableDashboard.setSelection(true);
		btnEnableDashboard.setText("Enable Dashboard");
		new Label(compositeAdvanced2, SWT.NONE);
		
		btnStrictExtendedData = new Button(compositeAdvanced2, SWT.CHECK);
		btnStrictExtendedData.setSelection(true);
		btnStrictExtendedData.setToolTipText("Toggles whether extended data is strictly typed.");
		btnStrictExtendedData.setText("Strict Extended Data");
		
		lblLocale = new Label(compositeAdvanced2, SWT.NONE);
		lblLocale.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLocale.setText("Locale");
		
		comboLocale = new Combo(compositeAdvanced2, SWT.READ_ONLY);
		comboLocale.setToolTipText(" Renders Dates and Currency per the servers locale or clients locale. Defaults to server.");
		comboLocale.setItems(new String[] {"server", "client"});
		comboLocale.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboLocale.setText("server");
		
		Label lblLoginStrikes = new Label(compositeAdvanced2, SWT.NONE);
		lblLoginStrikes.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLoginStrikes.setText("Login Strikes");
		
		spinnerLoginStrikes = new Spinner(compositeAdvanced2, SWT.BORDER);
		spinnerLoginStrikes.setToolTipText("Sets the number of attempts that a username can attempt to login before it is blocked for 10 minutes.");
		spinnerLoginStrikes.setMinimum(1);
		spinnerLoginStrikes.setSelection(4);
		spinnerLoginStrikes.setPageIncrement(1);
		new Label(compositeAdvanced2, SWT.NONE);
		
		btnPing = new Button(compositeAdvanced2, SWT.CHECK);
		btnPing.setToolTipText("This controls the scheduled task Mura creates for publishing scheduled content, sending scheduled emails, etc. If this functionality is important to you, leave it disabled.");
		btnPing.setText("Ping");
		new Label(compositeAdvanced2, SWT.NONE);
		
		btnPurgeDrafts = new Button(compositeAdvanced2, SWT.CHECK);
		btnPurgeDrafts.setToolTipText("Tells Mura whether to purge drafts when a new active version is published.");
		btnPurgeDrafts.setSelection(true);
		btnPurgeDrafts.setText("Purge Drafts");
		new Label(compositeAdvanced2, SWT.NONE);
		
		btnEnableMuraTag = new Button(compositeAdvanced2, SWT.CHECK);
		btnEnableMuraTag.setToolTipText("Toggles rendering of the [mura] tag. Defaults to true.");
		btnEnableMuraTag.setSelection(true);
		btnEnableMuraTag.setText("Enable Mura Tag");
		new Label(compositeAdvanced2, SWT.NONE);
		
		btnConfirmSaveAsDraft = new Button(compositeAdvanced2, SWT.CHECK);
		btnConfirmSaveAsDraft.setToolTipText("Tells Mura whether ask users whether to save changes as a draft when leaving the main content edtiing page.");
		btnConfirmSaveAsDraft.setSelection(true);
		btnConfirmSaveAsDraft.setText("Confirm Save as Draft");
		
		Label lblSortPermission = new Label(compositeAdvanced2, SWT.NONE);
		lblSortPermission.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSortPermission.setText("Sort Permission");
		
		textSortPermission = new Text(compositeAdvanced2, SWT.BORDER);
		textSortPermission.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textSortPermission.setText("editor");
		new Label(compositeAdvanced2, SWT.NONE);
		
		btnNotifyWithVersion = new Button(compositeAdvanced2, SWT.CHECK);
		btnNotifyWithVersion.setToolTipText("Tells Mura when notifications for review are sent whether to send return link to the version history page or directly to the version edit form.");
		btnNotifyWithVersion.setSelection(true);
		btnNotifyWithVersion.setText("Notify with Version Link");
		
		Label lblImageInterpolation = new Label(compositeAdvanced2, SWT.NONE);
		lblImageInterpolation.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblImageInterpolation.setText("Image Interpolation");
		
		comboImageInterpolation = new Combo(compositeAdvanced2, SWT.READ_ONLY);
		comboImageInterpolation.setToolTipText("This set the image quality when uploading images as content in Mura. For example, images associated with specific content and image galleries. Does not apply to any images uploaed via the WYSIWYG editor.");
		comboImageInterpolation.setItems(new String[] {"highestquality", "lanczos", "highquality", "mitchell", "mediumPerformance", "quadratic", "mediumquality", "hamming", "hanning", "hermite", "highPerformance", "blackman", "bessel", "highestPerformance", "nearest", "bicubic", "bilinear"});
		comboImageInterpolation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(compositeAdvanced2, SWT.NONE);
		
		btnAutoResetPasswords = new Button(compositeAdvanced2, SWT.CHECK);
		btnAutoResetPasswords.setToolTipText("Tells Mura whether to reset a users password or just send an email with a auto login link.");
		btnAutoResetPasswords.setText("Auto Reset Passwords");
		
		Label lblClusterList = new Label(compositeAdvanced2, SWT.NONE);
		lblClusterList.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblClusterList.setText("Cluster List");
		
		textClusterList = new Text(compositeAdvanced2, SWT.BORDER);
		textClusterList.setToolTipText("A comma separated list of complete URLS that point to the web roots of this Mura instance's cluster peers.\nExample: http://example.com:8081,http://example.com:8082");
		textClusterList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblTempDir = new Label(compositeAdvanced2, SWT.NONE);
		lblTempDir.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTempDir.setText("Temp Dir");
		
		textTempDir = new Text(compositeAdvanced2, SWT.BORDER);
		textTempDir.setToolTipText("Sets the temp directory that Mura will use for uploading files. Defaults to getTempDir().");
		textTempDir.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		TabItem tbtmAdvanced3 = new TabItem(tabFolder, SWT.NONE);
		tbtmAdvanced3.setText("Advanced 3");
		
		Composite compositeAdvanced3 = new Composite(tabFolder, SWT.NONE);
		tbtmAdvanced3.setControl(compositeAdvanced3);
		compositeAdvanced3.setLayout(new GridLayout(2, false));
		
		Label lblEncryptionKey = new Label(compositeAdvanced3, SWT.NONE);
		lblEncryptionKey.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEncryptionKey.setText("Encryption Key");
		
		textEncryptionKey = new Text(compositeAdvanced3, SWT.BORDER);
		textEncryptionKey.setToolTipText("The encryption key used for the Mura remember me functionality. Can be used for custom development.");
		textEncryptionKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblSessionTimeout = new Label(compositeAdvanced3, SWT.NONE);
		lblSessionTimeout.setText("Session Timeout");
		
		spinnerSessionTimeout = new Spinner(compositeAdvanced3, SWT.BORDER);
		spinnerSessionTimeout.setToolTipText("Set the amount of time a user can leave an admin screen open before being automatically logged out. If set to 0 then the auto logout functionily will not execute.");
		spinnerSessionTimeout.setPageIncrement(1);
		spinnerSessionTimeout.setMaximum(300);
		spinnerSessionTimeout.setSelection(180);
		
		Label lblHtmlEditor = new Label(compositeAdvanced3, SWT.NONE);
		lblHtmlEditor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblHtmlEditor.setText("HTML Editor");
		
		comboHTMLEditorType = new Combo(compositeAdvanced3, SWT.READ_ONLY);
		comboHTMLEditorType.setToolTipText("Tells Mura whether use the newer CKEditor or the previous FCKEditor. ");
		comboHTMLEditorType.setItems(new String[] {"CKEditor", "FCKEditor"});
		comboHTMLEditorType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboHTMLEditorType.setText("CKEditor");
		
		tbtmProxy = new TabItem(tabFolder, SWT.NONE);
		tbtmProxy.setText("Proxy");
		
		compositeProxy = new Composite(tabFolder, SWT.NONE);
		tbtmProxy.setControl(compositeProxy);
		compositeProxy.setLayout(new GridLayout(4, false));
		
		Label lblProxyServer = new Label(compositeProxy, SWT.NONE);
		lblProxyServer.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblProxyServer.setText("Proxy Server");
		
		textProxyServer = new Text(compositeProxy, SWT.BORDER);
		textProxyServer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblProxyPort = new Label(compositeProxy, SWT.NONE);
		lblProxyPort.setText("Proxy Port");
		
		spinnerProxyPort = new Spinner(compositeProxy, SWT.BORDER);
		spinnerProxyPort.setPageIncrement(1);
		spinnerProxyPort.setMaximum(999999);
		spinnerProxyPort.setMinimum(1);
		spinnerProxyPort.setSelection(80);
		
		Label lblProxyUser = new Label(compositeProxy, SWT.NONE);
		lblProxyUser.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblProxyUser.setText("Proxy User");
		
		textProxyUser = new Text(compositeProxy, SWT.BORDER);
		textProxyUser.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Label lblProxyPassword = new Label(compositeProxy, SWT.NONE);
		lblProxyPassword.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblProxyPassword.setText("Proxy Password");
		
		textProxyPassword = new Text(compositeProxy, SWT.BORDER);
		textProxyPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		// These should be the last lines. Don't put display elements below this comment
		setControl(composite);
	}
}
