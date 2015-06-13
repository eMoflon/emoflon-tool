package org.moflon.ide.ui.decorators;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.moflon.ide.core.CoreActivator;

public class MoflonDirtyProjectDecorator extends LabelProvider implements ILightweightLabelDecorator
{
   private static final String IS_DIRTY_PREFIX = "*** ";
   public static final String DECORATOR_ID = "org.moflon.ide.ui.decorators.MoflonDirtyProjectDecorator";
   
   /**
    * Prefixes the project name of a MOSL project with  {@link IS_DIRTY_PREFIX}.
    * @see org.eclipse.jface.viewers.ILightweightLabelDecorator#decorate(java.lang.Object, org.eclipse.jface.viewers.IDecoration)
    */
   @Override
   public void decorate(final Object element, final IDecoration decoration)
   {
      if (!(element instanceof IProject))
         return;
      
      if (CoreActivator.getDefault().isDirty(((IProject) element).getName()))
         decoration.addPrefix(IS_DIRTY_PREFIX);
   }

   public void projectStateChanged(final IProject project)
   {
      this.fireLabelProviderChanged(new LabelProviderChangedEvent(this, project));
   }
}
