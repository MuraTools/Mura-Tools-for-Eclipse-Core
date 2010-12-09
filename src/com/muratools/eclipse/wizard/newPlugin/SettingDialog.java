package com.muratools.eclipse.wizard.newPlugin;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;

public class SettingDialog extends Dialog {
	private String strName = "";
	private String strLabel = "";
	private String strHint = "";
	private String strRegEx = "";
	private String strMessage = "";
	private String strDefault = "";
	private String strOptions = "";
	private String strOptionLabels = "";
	private String strType = "";
	private String strValidation = "";
	private Boolean isRequired = new Boolean(false);
	
	private Text txtName;
	private Text txtLabel;
	private Text txtHint;
	private Text txtRegEx;
	private Text txtMessage;
	private Text txtDefault;
	private Text txtOptions;
	private Text txtOptionLabels;
	private Combo cmbType;
	private Combo cmbValidation;
	private Button btnRequired;

	public SettingDialog(Shell parentShell) {
		super(parentShell);
		
	}
	
	/**
	 * @return the strName
	 */
	public String getName() {
		return strName;
	}

	/**
	 * @return the strLabel
	 */
	public String getLabel() {
		return strLabel;
	}

	/**
	 * @return the strHint
	 */
	public String getHint() {
		return strHint;
	}

	/**
	 * @return the strRegEx
	 */
	public String getRegEx() {
		return strRegEx;
	}

	/**
	 * @return the strMessage
	 */
	public String getMessage() {
		return strMessage;
	}

	/**
	 * @return the strDefault
	 */
	public String getDefault() {
		return strDefault;
	}

	/**
	 * @return the strOptions
	 */
	public String getOptions() {
		return strOptions;
	}

	/**
	 * @return the strOptionLabels
	 */
	public String getOptionLabels() {
		return strOptionLabels;
	}

	/**
	 * @return the strType
	 */
	public String getType() {
		return strType;
	}

	/**
	 * @return the strValidation
	 */
	public String getValidation() {
		return strValidation;
	}

	/**
	 * @return the isRequired
	 */
	public Boolean getIsRequired() {
		return isRequired;
	}
	
	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public String getStrLabel() {
		return strLabel;
	}

	public void setStrLabel(String strLabel) {
		this.strLabel = strLabel;
	}

	public String getStrHint() {
		return strHint;
	}

	public void setStrHint(String strHint) {
		this.strHint = strHint;
	}

	public String getStrRegEx() {
		return strRegEx;
	}

	public void setStrRegEx(String strRegEx) {
		this.strRegEx = strRegEx;
	}

	public String getStrMessage() {
		return strMessage;
	}

	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}

	public String getStrDefault() {
		return strDefault;
	}

	public void setStrDefault(String strDefault) {
		this.strDefault = strDefault;
	}

	public String getStrOptions() {
		return strOptions;
	}

	public void setStrOptions(String strOptions) {
		this.strOptions = strOptions;
	}

	public String getStrOptionLabels() {
		return strOptionLabels;
	}

	public void setStrOptionLabels(String strOptionLabels) {
		this.strOptionLabels = strOptionLabels;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public String getStrValidation() {
		return strValidation;
	}

	public void setStrValidation(String strValidation) {
		this.strValidation = strValidation;
	}

	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}

	protected void configureShell(Shell newShell){
		super.configureShell(newShell);
		newShell.setText("Setting Editor");
	}
	
	protected Control createDialogArea(Composite parent){
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(2, false));
		
		Label lblName = new Label(container, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText("Name *");
		
		txtName = new Text(container, SWT.BORDER);
		txtName.setText(getName());
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtName.addModifyListener(new ModifyListener() {
			
			// @Override
			public void modifyText(ModifyEvent e) {
				strName = txtName.getText();
			}
		});
		txtName.addFocusListener(new FocusListener() {
			
			public void focusLost(FocusEvent e) {
				txtName.setText(txtName.getText().replace(" ", "_"));
				strName = txtName.getText();
			}
			
			public void focusGained(FocusEvent e) {
				// Do nothing
			}
		});
		
		Label lblLabel = new Label(container, SWT.NONE);
		lblLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLabel.setText("Label *");
		
		txtLabel = new Text(container, SWT.BORDER);
		txtLabel.setText(getLabel());
		txtLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtLabel.addModifyListener(new ModifyListener() {
			
			// @Override
			public void modifyText(ModifyEvent e) {
				strLabel = txtLabel.getText();
			}
		});
		
		Label lblHint = new Label(container, SWT.NONE);
		lblHint.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblHint.setText("Hint");
		
		txtHint = new Text(container, SWT.BORDER);
		txtHint.setText(getHint());
		txtHint.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtHint.addModifyListener(new ModifyListener() {
			
			// @Override
			public void modifyText(ModifyEvent e) {
				strHint = txtHint.getText();
			}
		});
		
		Label lblType = new Label(container, SWT.NONE);
		lblType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblType.setText("Type");
		
		cmbType = new Combo(container, SWT.NONE);
		cmbType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbType.addModifyListener(new ModifyListener() {
			
			// @Override
			public void modifyText(ModifyEvent e) {
				strType = cmbType.getText();
			}
		});
		
		addTypeItems();
		if (getType().length() > 0){
			cmbType.setText(getType());
		} else {
			cmbType.select(0);
		}
		
		Label lblValidation = new Label(container, SWT.NONE);
		lblValidation.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblValidation.setText("Validation");
		
		cmbValidation = new Combo(container, SWT.NONE);
		cmbValidation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbValidation.addModifyListener(new ModifyListener() {
			
			// @Override
			public void modifyText(ModifyEvent e) {
				strValidation = cmbValidation.getText() == "None" ? "" : cmbValidation.getText();
			}
		});
		
		addValidationItems();
		if (getValidation().length() > 0){
			cmbValidation.setText(getValidation());
		} else {
			cmbValidation.select(0);
		}
		
		Label lblRegex = new Label(container, SWT.NONE);
		lblRegex.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblRegex.setText("RegEx");
		
		txtRegEx = new Text(container, SWT.BORDER);
		txtRegEx.setText(getRegEx());
		txtRegEx.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtRegEx.addModifyListener(new ModifyListener() {
			
			// @Override
			public void modifyText(ModifyEvent e) {
				strRegEx = txtRegEx.getText();
			}
		});
		
		Label lblMessage = new Label(container, SWT.NONE);
		lblMessage.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMessage.setText("Message");
		
		txtMessage = new Text(container, SWT.BORDER);
		txtMessage.setText(getMessage());
		txtMessage.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtMessage.addModifyListener(new ModifyListener() {
			
			// @Override
			public void modifyText(ModifyEvent e) {
				strMessage = txtMessage.getText();
			}
		});
		
		Label lblDefault = new Label(container, SWT.NONE);
		lblDefault.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDefault.setText("Default");
		
		txtDefault = new Text(container, SWT.BORDER);
		txtDefault.setText(getDefault());
		txtDefault.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtDefault.addModifyListener(new ModifyListener() {
			
			// @Override
			public void modifyText(ModifyEvent e) {
				strDefault = txtDefault.getText();
			}
		});
		
		Label lblOptions = new Label(container, SWT.NONE);
		lblOptions.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOptions.setText("Options");
		
		txtOptions = new Text(container, SWT.BORDER);
		txtOptions.setText(getOptions());
		txtOptions.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtOptions.addModifyListener(new ModifyListener() {
			
			// @Override
			public void modifyText(ModifyEvent e) {
				strOptions = txtOptions.getText();
			}
		});
		
		Label lblOptionLabels = new Label(container, SWT.NONE);
		lblOptionLabels.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOptionLabels.setText("Option Labels");
		
		txtOptionLabels = new Text(container, SWT.BORDER);
		txtOptionLabels.setText(getOptionLabels());
		txtOptionLabels.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtOptionLabels.addModifyListener(new ModifyListener() {
			
			// @Override
			public void modifyText(ModifyEvent e) {
				strOptionLabels = txtOptionLabels.getText();
			}
		});
		new Label(container, SWT.NONE);
		
		btnRequired = new Button(container, SWT.CHECK);
		btnRequired.setSelection(getIsRequired());
		btnRequired.setText("Required");
		new Label(container, SWT.NONE);
		
		Label lblRequiredField = new Label(container, SWT.NONE);
		lblRequiredField.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblRequiredField.setText("* Required Field");
		btnRequired.addSelectionListener(new SelectionAdapter() {
			// @Override
			public void widgetSelected(SelectionEvent e){
				isRequired = new Boolean(btnRequired.getSelection());
			}
		});
		
		return container;
	}
	
	private void addTypeItems(){
		cmbType.add("TextBox");
		cmbType.add("TextArea");
		cmbType.add("HTMLEditor");
		cmbType.add("SelectBox");
		cmbType.add("MultiSelectBox");
		cmbType.add("RadioGroup");
		cmbType.add("File");
		cmbType.add("Hidden");
	}
	
	private void addValidationItems(){
		cmbValidation.add("None");
		cmbValidation.add("Date");
		cmbValidation.add("Numeric");
		cmbValidation.add("Email");
		cmbValidation.add("Regex");

	}
}
