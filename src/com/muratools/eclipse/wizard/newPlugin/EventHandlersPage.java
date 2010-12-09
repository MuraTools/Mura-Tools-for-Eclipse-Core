package com.muratools.eclipse.wizard.newPlugin;

import java.util.ArrayList;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Group;

import com.muratools.eclipse.EventHandler;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;

public class EventHandlersPage extends WizardPage {
	ArrayList<EventHandler> eventHandlers = new ArrayList<EventHandler>();
	private Table table;
	private Text txtComponent;
	private CCombo eventSelect;
	private Button btnPersist;
	
	public EventHandlersPage(String pageName) {
		super(pageName);
		setTitle("Event Handlers");
		setDescription("Manage the new plugin's event handlers");
	}

	//@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		setControl(composite);
		composite.setLayout(new RowLayout(SWT.VERTICAL));
		
		Group grpEventHandlers = new Group(composite, SWT.NONE);
		grpEventHandlers.setLayoutData(new RowData(560, 171));
		grpEventHandlers.setText("Event Handlers");
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayoutData(new RowData(562, SWT.DEFAULT));
		composite_1.setLayout(null);
		
		Button btnRemoveEventHandler = new Button(composite_1, SWT.NONE);
		btnRemoveEventHandler.setBounds(303, 3, 132, 25);
		btnRemoveEventHandler.setText("Remove Event Handler");
		btnRemoveEventHandler.addSelectionListener(new SelectionListener() {	
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = table.getSelectionIndex();
				table.remove(selectionIndex);
				eventHandlers.remove(selectionIndex);
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		Button btnClearEventHandlers = new Button(composite_1, SWT.NONE);
		btnClearEventHandlers.setBounds(441, 3, 121, 25);
		btnClearEventHandlers.setText("Clear Event Handlers");
		btnClearEventHandlers.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				table.removeAll();
				eventHandlers.removeAll(eventHandlers);
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		Group grpAddNewEvent = new Group(composite, SWT.NONE);
		grpAddNewEvent.setLayoutData(new RowData(560, SWT.DEFAULT));
		grpAddNewEvent.setText("Add New Event Handler");
		grpAddNewEvent.setLayout(new GridLayout(6, false));
		
		Label lblEvent = new Label(grpAddNewEvent, SWT.NONE);
		lblEvent.setText("Event");
		
		eventSelect = new CCombo(grpAddNewEvent, SWT.BORDER);
		GridData gd_combo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_combo.widthHint = 115;
		eventSelect.setLayoutData(gd_combo);
		
		addEventHandlerItems(); // Add all the event handler hooks to the combo dropdown
		
		Label lblComponent = new Label(grpAddNewEvent, SWT.NONE);
		lblComponent.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblComponent.setText("Component");
		
		txtComponent = new Text(grpAddNewEvent, SWT.BORDER);
		txtComponent.setText("MainEventHandler");
		txtComponent.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btnPersist = new Button(grpAddNewEvent, SWT.CHECK);
		btnPersist.setSelection(true);
		btnPersist.setText("Persist");
		
		Button btnAddEventHandler = new Button(grpAddNewEvent, SWT.NONE);
		btnAddEventHandler.setText("Add Event Handler");
		grpEventHandlers.setLayout(new FillLayout(SWT.HORIZONTAL));
		btnAddEventHandler.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				TableItem newItem = new TableItem(table, SWT.NONE);
				newItem.setText(0,eventSelect.getText());
				newItem.setText(1,txtComponent.getText());
				newItem.setText(2,new Boolean(btnPersist.getSelection()).toString());
				
				eventHandlers.add(new EventHandler(eventSelect.getText(), txtComponent.getText(), new Boolean(btnPersist.getSelection())));				
				
				// reset the form fields
				txtComponent.setText("MainEventHandler");
				eventSelect.setText("");
				btnPersist.setSelection(true);
				eventSelect.setFocus();
			}
		});
		
		table = new Table(grpEventHandlers, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnEvent = new TableColumn(table, SWT.NONE);
		tblclmnEvent.setWidth(140);
		tblclmnEvent.setText("Event");
		
		TableColumn tblclmnComponent = new TableColumn(table, SWT.NONE);
		tblclmnComponent.setWidth(314);
		tblclmnComponent.setText("Component");
		
		TableColumn tblclmnPersist = new TableColumn(table, SWT.NONE);
		tblclmnPersist.setWidth(100);
		tblclmnPersist.setText("Persist");
	}

	
	private void addEventHandlerItems(){
		// User
		eventSelect.add("onBeforeUserUpdate");
		eventSelect.add("onBeforeUserCreate");
		eventSelect.add("onBeforeUserSave");
		eventSelect.add("onUserUpdate");
		eventSelect.add("onUserCreate");
		eventSelect.add("onUserSave");
		eventSelect.add("onAfterUserUpdate");
		eventSelect.add("onAfterUserCreate");
		eventSelect.add("onAfterUserSave");
		eventSelect.add("onUserEdit");
		
		// Group
		eventSelect.add("onBeforeGroupUpdate");
		eventSelect.add("onBeforeGroupCreate");
		eventSelect.add("onBeforeGroupSave");
		eventSelect.add("onGroupUpdate");
		eventSelect.add("onGroupCreate");
		eventSelect.add("onGroupSave");
		eventSelect.add("onAfterGroupUpdate");
		eventSelect.add("onAfterGroupCreate");
		eventSelect.add("onAfterGroupSave");
		eventSelect.add("onGroupEdit");
		
		// Login
		eventSelect.add("onSiteLogin");
		eventSelect.add("onGlobalLogin");
		eventSelect.add("onSiteLoginSuccess");
		eventSelect.add("onGlobalLoginSuccess");
		eventSelect.add("onSiteLoginBlocked");
		eventSelect.add("onGlobalLoginBlocked");
		
		// Content
		eventSelect.add("onBeforeContentSave");
		eventSelect.add("onContentSave");
		eventSelect.add("onAfterContentSave");
		eventSelect.add("onBeforeContentDelete");
		eventSelect.add("onContentDelete");
		eventSelect.add("onAfterContentDelete");
		eventSelect.add("onBeforeContentDeleteVersionHistory");
		eventSelect.add("onContentDeleteVersionHistory");
		eventSelect.add("onAfterContentDeleteVersionHistory");
		eventSelect.add("onBeforeContentDeleteVersion");
		eventSelect.add("onContentDeleteVersion");
		eventSelect.add("onAfterContentDeleteVersion");
		eventSelect.add("onContentEdit");
		
		// Category
	    eventSelect.add("onBeforeCategoryUpdate");
	    eventSelect.add("onBeforeCategoryCreate");
	    eventSelect.add("onBeforeCategorySave");
	    eventSelect.add("onBeforeCategoryDelete");
	    eventSelect.add("onCategoryUpdate");
	    eventSelect.add("onCategoryCreate");
	    eventSelect.add("onCategoryDelete");
	    eventSelect.add("onCategorySave");
	    eventSelect.add("onAfterCategoryUpdate");
	    eventSelect.add("onAfterCategoryCreate");
	    eventSelect.add("onAfterCategorySave");
	    eventSelect.add("onAfterCategoryDelete");
	    
	    // Feed
	    eventSelect.add("onBeforeFeedUpdate");
	    eventSelect.add("onBeforeFeedCreate");
	    eventSelect.add("onBeforeFeedSave");
	    eventSelect.add("onBeforeFeedDelete");
	    eventSelect.add("onFeedUpdate");
	    eventSelect.add("onFeedCreate");
	    eventSelect.add("onFeedDelete");
	    eventSelect.add("onFeedSave");
	    eventSelect.add("onAfterFeedUpdate");
	    eventSelect.add("onAfterFeedCreate");
	    eventSelect.add("onAfterFeedSave");
	    eventSelect.add("onAfterFeedDelete");
	    
	    // Rendering
	    eventSelect.add("onRenderStart");
	    eventSelect.add("onRenderEnd");
	    eventSelect.add("onSiteEditProfileRender");
	    eventSelect.add("onSiteSearchRender");
	    eventSelect.add("onSiteLoginPromptRender");
	    eventSelect.add("onContentOffLineRender");
	    eventSelect.add("onContentDenialRender");
	    //eventSelect.add("on{type}{subType}BodyRender");
	    //eventSelect.add("on{type}BodyRender");
	    eventSelect.add("onDashboardPrimaryTop");
	    eventSelect.add("onDashboardPrimaryBottom");
	    eventSelect.add("onDashboardSidebarTop");
	    eventSelect.add("onDashboardSidebarBottom");
	    eventSelect.add("onFEToolbarAdd");

	    // System
	    eventSelect.add("onApplicationLoad");
	    eventSelect.add("onSiteDeploy");
	    eventSelect.add("onBeforeSiteDeploy");
	    eventSelect.add("onAfterSiteDeploy");
	    eventSelect.add("onSiteRequestInit");
	    eventSelect.add("onSiteRequestStart");
	    eventSelect.add("onSiteRequestEnd");
	    eventSelect.add("onGlobalRequestStart");
	    eventSelect.add("onGlobalRequestEnd");
	    eventSelect.add("onGlobalSessionStart");
	    eventSelect.add("onSiteError");
	    eventSelect.add("onGlobalError");

	}
}
