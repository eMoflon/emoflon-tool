package org.moflon.tutorial.validation;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.cheatsheets.AbstractItemExtensionElement;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.moflon.tutorial.helper.MyWorkspaceHelper;

public class ItemExtension extends AbstractItemExtensionElement {
	
	Validator validator;
	
	public ItemExtension(final String attributeName) {
		super(attributeName);
	}

	@Override
	public void handleAttribute(final String attributeValue) {
		String [] args = attributeValue.split(";");
		
		if (args.length != 2) {
			throw new RuntimeException("Invalid Value for validation attribute! Usage: \"chapter;step\"");
		}
		
		validator = new Validator(args[0], Integer.parseInt(args[1]));
	}

	@Override
	public void createControl(final Composite composite) {
			
		
		
		ImageHyperlink link = new ImageHyperlink(composite, 0);
		
		Image img = new Image(Display.getCurrent(), MyWorkspaceHelper.getResourceInputStream("resources/icons/tick.png"));

		link.setImage(img);
		
		link.addHyperlinkListener(new IHyperlinkListener() {
			
			@Override
			public void linkExited(final HyperlinkEvent e) {
				
			}
			
			@Override
			public void linkEntered(final HyperlinkEvent e) {
				
			}
			
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				validator.validate();
			}
		});

		
	}

	@Override
	public void dispose() {
		
	}

}
