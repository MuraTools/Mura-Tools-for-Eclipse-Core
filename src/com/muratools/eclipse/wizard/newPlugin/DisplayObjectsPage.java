package com.muratools.eclipse.wizard.newPlugin;

import java.util.ArrayList;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.FillLayout;

import com.muratools.eclipse.DisplayObject;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;

public class DisplayObjectsPage extends WizardPage {
	ArrayList<DisplayObject> displayObjects = new ArrayList<DisplayObject>();
	private Text txtName;
	private Text txtFileName;
	private Table table;
	
	public DisplayObjectsPage(String pageName) {
		super(pageName);
		setTitle("Display Objects");
		setDescription("Manage the new plugin's display objects");
	}
	
	//@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		setControl(composite);
		composite.setLayout(new RowLayout(SWT.VERTICAL));
		
		Group grpDisplayObjects = new Group(composite, SWT.NONE);
		grpDisplayObjects.setLayoutData(new RowData(560, 174));
		grpDisplayObjects.setText("Display Objects");
		grpDisplayObjects.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		table = new Table(grpDisplayObjects, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setWidth(195);
		tblclmnName.setText("Name");
		
		TableColumn tblclmnFileName = new TableColumn(table, SWT.NONE);
		tblclmnFileName.setWidth(360);
		tblclmnFileName.setText("File Name");
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(null);
		composite_1.setLayoutData(new RowData(564, 27));
		
		Button btnRemoveDisplayObject = new Button(composite_1, SWT.NONE);
		btnRemoveDisplayObject.setBounds(301, 3, 134, 25);
		btnRemoveDisplayObject.setText("Remove Display Object");
		btnRemoveDisplayObject.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = table.getSelectionIndex();
				table.remove(selectionIndex);
				displayObjects.remove(selectionIndex);
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		Button btnClearDisplayObject = new Button(composite_1, SWT.NONE);
		btnClearDisplayObject.setBounds(441, 3, 123, 25);
		btnClearDisplayObject.setText("Clear Display Objects");
		btnClearDisplayObject.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				table.removeAll();
				displayObjects.removeAll(displayObjects);
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		Group grpAddNewDisplay = new Group(composite, SWT.NONE);
		grpAddNewDisplay.setLayoutData(new RowData(560, SWT.DEFAULT));
		grpAddNewDisplay.setText("Add New Display Object");
		grpAddNewDisplay.setLayout(new GridLayout(5, false));
		
		Label lblName = new Label(grpAddNewDisplay, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText("Name");
		
		txtName = new Text(grpAddNewDisplay, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblFileName = new Label(grpAddNewDisplay, SWT.NONE);
		lblFileName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFileName.setText("File Name");
		
		txtFileName = new Text(grpAddNewDisplay, SWT.BORDER);
		txtFileName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnAddDisplayObject = new Button(grpAddNewDisplay, SWT.NONE);
		btnAddDisplayObject.setText("Add Display Object");
		btnAddDisplayObject.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(0,txtName.getText());
				item.setText(1,txtFileName.getText());
				
				displayObjects.add(new DisplayObject(txtName.getText(), txtFileName.getText()));
				
				txtName.setText("");
				txtFileName.setText("");
				txtName.setFocus();
			}
		});
	}
}
