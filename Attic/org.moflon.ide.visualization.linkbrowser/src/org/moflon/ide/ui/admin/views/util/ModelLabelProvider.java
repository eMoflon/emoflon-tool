package org.moflon.ide.ui.admin.views.util;

import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef4.zest.core.viewers.IEntityConnectionStyleProvider;
import org.eclipse.gef4.zest.core.viewers.IEntityStyleProvider;
import org.eclipse.gef4.zest.core.widgets.ZestStyles;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.debug.ui.presentation.MoflonDebugModelPresentation;

import org.moflon.tgg.debug.language.*;
import org.moflon.tgg.runtime.CorrespondenceModel;

public class ModelLabelProvider extends MoflonDebugModelPresentation implements IEntityStyleProvider, IEntityConnectionStyleProvider
{

   @Override
   public String getText(Object element)
   {
      if (element instanceof DebugCorrespondenceModel)
      {
         return CorrespondenceModel.class.getSimpleName();
      } else if (element instanceof DebugCorrespondence)
      {
         DebugCorrespondence dc = (DebugCorrespondence) element;
         DebugEObjectProxy source = dc.getSource();
         DebugEObjectProxy target = dc.getTarget();
         return (source != null ? source.getName() : "null") + " <-> " + (target != null ? target.getName() : "null");
      } else if (element instanceof DebugEObjectProxy)
      {
         DebugEObjectProxy proxy = (DebugEObjectProxy) element;
         return proxy.getName() + " : " + proxy.getClassName();
      } else if (element instanceof EObject)
      {
         return eMoflonEMFUtil.getIdentifier((EObject) element);
      } else if (element instanceof ModelConnection)
      {
         return ((ModelConnection) element).getName();
      } else
      {
         throw new RuntimeException("Type not supported: " + element.getClass().toString());
      }
   }

   @Override
   public Color getNodeHighlightColor(Object entity)
   {
      return Display.getDefault().getSystemColor(SWT.COLOR_RED);
   }

   @Override
   public Color getBorderColor(Object entity)
   {
      return Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
   }

   @Override
   public Color getBorderHighlightColor(Object entity)
   {
      return Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
   }

   @Override
   public int getBorderWidth(Object entity)
   {
      return 1;
   }

   @Override
   public Color getBackgroundColour(Object entity)
   {
      if (entity instanceof DebugEObjectProxy)
      {
         DebugEObjectProxy proxy = (DebugEObjectProxy) entity;
         switch (proxy.getChangeMode())
         {
         case ADDED:
            return Display.getDefault().getSystemColor(SWT.COLOR_GREEN);
         default:
            break;
         }
      } else if (entity instanceof DebugTripleMatch)
      {
         DebugTripleMatch dtm = (DebugTripleMatch) entity;
         switch (dtm.getChangeMode())
         {
         case ADDED:
            return Display.getDefault().getSystemColor(SWT.COLOR_GREEN);
         default:
            break;
         }
      }
      return Display.getDefault().getSystemColor(SWT.COLOR_TITLE_BACKGROUND);
   }

   @Override
   public Color getForegroundColour(Object entity)
   {
      return Display.getDefault().getSystemColor(SWT.COLOR_WHITE);
   }

   @Override
   public IFigure getTooltip(Object entity)
   {
      if (entity instanceof EObject)
      {
         String content = generateToolTipContent((EObject) entity);
         return new Label(content);
      } else
         throw new RuntimeException("Unsupported type: " + entity.getClass().toString());
   }

   @Override
   public boolean fisheyeNode(Object entity)
   {
      return false;
   }

   private String generateToolTipContent(EObject eObject)
   {
      StringBuilder result = new StringBuilder();
      for (EAttribute feature : eObject.eClass().getEAllAttributes())
      {
         String name = feature.getName();
         result.append(name);
         result.append(" : ");
         if (eObject.eGet(feature) == null)
         {
            result.append("null");
         } else
         {
            result.append(eObject.eGet(feature).toString());
         }
         result.append("\n");
      }
      if (result.length() > 0)
         result.deleteCharAt(result.length() - 1);
      return result.toString();
   }

   @Override
   public int getConnectionStyle(Object src, Object dest)
   {
      return ZestStyles.CONNECTIONS_DIRECTED;
   }

   @Override
   public Color getColor(Object src, Object dest)
   {
      return Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
   }

   @Override
   public Color getHighlightColor(Object src, Object dest)
   {
      return Display.getDefault().getSystemColor(SWT.COLOR_RED);
   }

   @Override
   public int getLineWidth(Object src, Object dest)
   {
      return 1;
   }

   @Override
   public IFigure getTooltip(Object src, Object dest)
   {
      return null;
   }

   @Override
   public ConnectionRouter getRouter(Object src, Object dest)
   {
      return null;
   }

   @Override
   public Image getImage(Object element)
   {
      if (element instanceof DebugCorrespondence)
      {
         return getCorrespondenceImage();
      } else if (element instanceof DebugTripleMatch)
      {
         DebugTripleMatch dtm = (DebugTripleMatch) element;
         return getTripleMatchImage(dtm.getChangeMode());
      } else if (element instanceof DebugSynchronizationProtocol)
      {
         return getSynchronizationImage();
      } else if (element instanceof DebugMatch)
      {
         return getMatchImage();
      }
      return super.getImage(element);
   }

}
