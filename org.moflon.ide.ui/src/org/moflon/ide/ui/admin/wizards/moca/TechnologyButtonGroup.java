package org.moflon.ide.ui.admin.wizards.moca;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class TechnologyButtonGroup {

	private Technology technology = Technology.ANTLR;

	private Button[] buttons;

	public TechnologyButtonGroup(Composite container) {
		Composite buttonGroup = new Composite(container, SWT.NULL);
		RowLayout rowLayout = new RowLayout();
		rowLayout.type = SWT.VERTICAL;
		buttonGroup.setLayout(rowLayout);

		buttons = new Button[3];
		buttons[0] = new Button(buttonGroup, SWT.RADIO);
		buttons[0].setSelection(true);
		buttons[0].setText("ANTLR");
		buttons[0].setBounds(new Rectangle(0, 0, 100, 100));
		buttons[0].addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setTechnology(Technology.ANTLR);
			}
		});

		buttons[1] = new Button(buttonGroup, SWT.RADIO);
		buttons[1].setText("XML");
		buttons[1].addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setTechnology(Technology.XML);
			}
		});

		buttons[2] = new Button(buttonGroup, SWT.RADIO);
		buttons[2].setText("Custom");
		buttons[2].addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setTechnology(Technology.CUSTOM);
			}
		});
	}

	public void setTechnology(Technology technology) {
		this.technology = technology;
	}

	public Technology getTechnology() {
		return technology;
	}

	public void setEnabled(boolean enabled) {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setEnabled(enabled);
		}
	}

}
