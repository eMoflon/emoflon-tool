package org.moflon.ide.texteditor.modules.content;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateCompletionProcessor;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.swt.graphics.Image;

/**
 * implementation of TemplateCompletionProcessor
 * 
 * @author mdavid
 * @author (last editor) $Author$
 * @version $Revision$ $Date$
 * 
 */
public class MoflonTextCompletionProcessor extends TemplateCompletionProcessor
{

	private Template[] templates;

	public MoflonTextCompletionProcessor()
	{
		templates = new Template[] {};
	}

	/**
	 * Simply return all templates.
	 * 
	 * @param contextTypeId
	 *            the context type, ignored in this implementation
	 * @return all templates
	 */
	@Override
	protected Template[] getTemplates(String contextTypeId)
	{
		return templates;
	}

	/**
	 * set all templates to be returned for the next call
	 */
	public void setTemplates(Template[] templates)
	{
		this.templates = templates;
	}

	/**
	 * Return the IDocument.DEFAULT_CONTENT_TYPE as context type.
	 * 
	 * @param viewer
	 *            the viewer, ignored in this implementation
	 * @param region
	 *            the region, ignored in this implementation
	 * @return IDocument.DEFAULT_CONTENT_TYPE as context type
	 */
	@Override
	protected TemplateContextType getContextType(ITextViewer viewer, IRegion region)
	{
		// reimplement if using context sensitive templates
		return new TemplateContextType(IDocument.DEFAULT_CONTENT_TYPE);
	}

	/**
	 * Always return null.
	 * 
	 * @param template
	 *            the template, ignored in this implementation
	 * @return null
	 */
	@Override
	protected Image getImage(Template template)
	{
		// needs to be extended for handling images
		// returns null for no image is available
		return null;
	}

}
