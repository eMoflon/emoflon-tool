package org.moflon.ide.ui.admin.views.util;

import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef4.zest.core.viewers.IEntityConnectionStyleProvider;
import org.eclipse.gef4.zest.core.viewers.IEntityStyleProvider;
import org.eclipse.gef4.zest.core.widgets.ZestStyles;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.moflon.core.utilities.eMoflonEMFUtil;

public class ModelLabelProvider extends LabelProvider implements IEntityStyleProvider, IEntityConnectionStyleProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof EObject) {
			return eMoflonEMFUtil.getIdentifier((EObject) element);
		} else if (element instanceof ModelConnection) {
			return ((ModelConnection) element).getName();
		} else {
			throw new RuntimeException("Type not supported: " + element.getClass().toString());
		}
	}

	@Override
	public Color getNodeHighlightColor(Object entity) {
		return Display.getDefault().getSystemColor(SWT.COLOR_RED);
	}

	@Override
	public Color getBorderColor(Object entity) {
		return Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
	}

	@Override
	public Color getBorderHighlightColor(Object entity) {
		return Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
	}

	@Override
	public int getBorderWidth(Object entity) {
		return 1;
	}

	@Override
	public Color getBackgroundColour(Object entity) {
		return Display.getDefault().getSystemColor(SWT.COLOR_TITLE_BACKGROUND);
	}

	@Override
	public Color getForegroundColour(Object entity) {
		return Display.getDefault().getSystemColor(SWT.COLOR_WHITE);
	}

	@Override
	public IFigure getTooltip(Object entity) {
		if (entity instanceof EObject) {
			String content = generateToolTipContent((EObject) entity);
			return new Label(content);
		} else
			throw new RuntimeException("Unsupported type: " + entity.getClass().toString());
	}

	@Override
	public boolean fisheyeNode(Object entity) {
		return false;
	}

	private String generateToolTipContent(EObject eObject) {
		StringBuilder result = new StringBuilder();
		for (EAttribute feature : eObject.eClass().getEAllAttributes()) {
			String name = feature.getName();
			result.append(name);
			result.append(" : ");
			if (eObject.eGet(feature) == null) {
				result.append("null");
			} else {
				result.append(eObject.eGet(feature).toString());
			}
			result.append("\n");
		}
		if (result.length() > 0)
			result.deleteCharAt(result.length() - 1);
		return result.toString();
	}

	@Override
	public int getConnectionStyle(Object src, Object dest) {
		return ZestStyles.CONNECTIONS_DIRECTED;
	}

	@Override
	public Color getColor(Object src, Object dest) {
		return Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
	}

	@Override
	public Color getHighlightColor(Object src, Object dest) {
		return Display.getDefault().getSystemColor(SWT.COLOR_RED);
	}

	@Override
	public int getLineWidth(Object src, Object dest) {
		return 1;
	}

	@Override
	public IFigure getTooltip(Object src, Object dest) {
		return null;
	}

	@Override
	public ConnectionRouter getRouter(Object src, Object dest) {
		return null;
	}

	@Override
	public Image getImage(Object element) {
		return super.getImage(element);
	}

}
