package org.moflon.ide.debug.ui.presentation;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.IValueDetailListener;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.pde.internal.ui.PDEPluginImages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.moflon.ide.debug.core.model.AttributeProxyVariable;
import org.moflon.ide.debug.core.model.DebugCorrespondenceVariable;
import org.moflon.ide.debug.core.model.DebugEObjectProxyVariable;
import org.moflon.ide.debug.core.model.EListVariable;
import org.moflon.ide.debug.core.model.EMoflonEdgeVariable;
import org.moflon.ide.debug.core.model.phases.addition.AdditonStateStackFrame;
import org.moflon.ide.debug.core.model.phases.deletion.DeletionStateStackFrame;
import org.moflon.ide.debug.core.model.phases.init.ConfiguratorVariable;
import org.moflon.ide.debug.core.model.phases.init.CorrespondenceValue;
import org.moflon.ide.debug.core.model.phases.init.CorrespondenceVariable;
import org.moflon.ide.debug.core.model.phases.init.DeltaValue;
import org.moflon.ide.debug.core.model.phases.init.DeltaVariable;
import org.moflon.ide.debug.core.model.phases.init.RuleVariable;
import org.moflon.ide.debug.core.model.phases.init.RulesVariable;
import org.moflon.ide.debug.core.model.phases.init.SynchronizationVariable;
import org.moflon.ide.debug.core.model.phases.init.TripleMatchValue;
import org.moflon.ide.debug.core.model.phases.init.TripleMatchVariable;
import org.moflon.ide.debug.core.model.phases.translation.MatchValue;
import org.moflon.ide.debug.core.model.phases.translation.MatchVariable;
import org.moflon.ide.debug.core.model.phases.translation.TranslationStateStackFrame;
import org.moflon.ide.debug.ui.Activator;
import org.moflon.ide.ui.UIActivator;

import DebugLanguage.ChangeMode;

@SuppressWarnings("restriction")
public class MoflonDebugModelPresentation extends LabelProvider implements IDebugModelPresentation
{

   private ComposedAdapterFactory reflectiveEcoreImageAdapterFactory;

   private AdapterFactoryLabelProvider adapterFactoryLabelProvider;

   public MoflonDebugModelPresentation()
   {
      // Create an adapter factory that yields item providers.
      reflectiveEcoreImageAdapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

      reflectiveEcoreImageAdapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
      reflectiveEcoreImageAdapterFactory.addAdapterFactory(new EcoreItemProviderAdapterFactory());
      reflectiveEcoreImageAdapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());

      adapterFactoryLabelProvider = new AdapterFactoryLabelProvider(reflectiveEcoreImageAdapterFactory);
   }

   @Override
   public void addListener(ILabelProviderListener listener)
   {
      // TODO Auto-generated method stub
   }

   @Override
   public void dispose()
   {
      arrow.dispose();
      arrow = null;
      rule.dispose();
      rule = null;
      configurator.dispose();
      configurator = null;
      sync.dispose();
      sync = null;
      delta.dispose();
      delta = null;
      corr.dispose();
      corr = null;
      nodeAdded.dispose();
      nodeAdded = null;
      nodeDeleted.dispose();
      nodeDeleted = null;
      edgeAdded.dispose();
      edgeAdded = null;
      edgeDeleted.dispose();
      edgeDeleted = null;
      attributeChange.dispose();
      attributeChange = null;
      addedElements.dispose();
      addedElements = null;
      deletedElements.dispose();
      deletedElements = null;
      tripleMatchAdded.dispose();
      tripleMatchAdded = null;
      tripleMatchDeleted.dispose();
      tripleMatchDeleted = null;
      precedenceGraph.dispose();
      precedenceGraph = null;
      edge.dispose();
      edge = null;
      correspondences.dispose();
      correspondences = null;
      correspondence.dispose();
      correspondence = null;
      correspondenceElements.dispose();
      correspondenceElements = null;
      edgeTranslated.dispose();
      edgeTranslated = null;
      itemTranslated.dispose();
      itemTranslated = null;
      toBeTranslated.dispose();
      toBeTranslated = null;
      match.dispose();
      match = null;
      context.dispose();
      context = null;

      if (adapterFactoryLabelProvider != null)
      {
         adapterFactoryLabelProvider.dispose();
      }
      if (reflectiveEcoreImageAdapterFactory != null)
      {
         reflectiveEcoreImageAdapterFactory.dispose();
      }
   }

   @Override
   public boolean isLabelProperty(Object element, String property)
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public void removeListener(ILabelProviderListener listener)
   {
      // TODO Auto-generated method stub

   }

   @Override
   public IEditorInput getEditorInput(Object element)
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public String getEditorId(IEditorInput input, Object element)
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public void setAttribute(String attribute, Object value)
   {
      // TODO Auto-generated method stub
   }

   Image arrow = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_FORWARD);

   Image rule = UIActivator.getImage("resources/icons/link.png").createImage();

   Image configurator = Activator.getImage("icons/full/obj16/configurator.png").createImage();

   Image sync = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ELCL_SYNCED);

   Image delta = Activator.getImage("icons/full/obj16/delta.png").createImage();

   Image nodeAdded = Activator.getImage("icons/full/obj16/nodeAdd.png").createImage();

   Image nodeDeleted = Activator.getImage("icons/full/obj16/nodeDelete.png").createImage();

   Image edgeAdded = Activator.getImage("icons/full/obj16/edgeAdd.png").createImage();

   Image edgeDeleted = Activator.getImage("icons/full/obj16/edgeDelete.png").createImage();

   Image attributeChange = Activator.getImage("icons/full/obj16/attributeChanged.png").createImage();

   Image corr = PDEPluginImages.DESC_SEQ_SC_OBJ.createImage();

   Image correspondences = Activator.getImage("icons/full/obj16/correspondences.png").createImage();

   Image correspondence = Activator.getImage("icons/full/obj16/correspondence.png").createImage();

   Image correspondenceElements = Activator.getImage("icons/full/obj16/correspondenceElements.png").createImage();

   Image addedElements = Activator.getImage("icons/full/obj16/addedElements.png").createImage();

   Image deletedElements = Activator.getImage("icons/full/obj16/deletedElements.png").createImage();

   Image revokedElements = Activator.getImage("icons/full/obj16/revokedElements.png").createImage();

   Image tripleMatchAdded = Activator.getImage("icons/full/obj16/triplematch_added.png").createImage();

   Image tripleMatchDeleted = Activator.getImage("icons/full/obj16/triplematch_deleted.png").createImage();

   Image precedenceGraph = Activator.getImage("icons/full/obj16/precedenceGraph.png").createImage();

   Image edge = Activator.getImage("icons/full/obj16/edge.png").createImage();

   Image edgeTranslated = Activator.getImage("icons/full/obj16/edgeTranslated.png").createImage();

   Image itemTranslated = Activator.getImage("icons/full/obj16/itemTranslated.png").createImage();

   Image toBeTranslated = Activator.getImage("icons/full/obj16/toBeTranslated.png").createImage();

   Image match = Activator.getImage("icons/full/obj16/match.png").createImage();

   Image context = Activator.getImage("icons/full/obj16/context.png").createImage();

   @Override
   public Image getImage(Object element)
   {
      if (element instanceof TripleMatchVariable)
      {
         TripleMatchVariable variable = (TripleMatchVariable) element;
         switch (((ChangeMode) variable.getAdapter(ChangeMode.class)))
         {
         case ADDED:
            return tripleMatchAdded;
         case DELETED:
            return tripleMatchDeleted;
         case NONE:
            return arrow;
         default:
            break;
         }
      } else if (element instanceof MatchVariable)
      {
         return match;
      } else if (element instanceof RuleVariable || element instanceof RulesVariable)
      {
         return rule;
      } else if (element instanceof ConfiguratorVariable)
      {
         return configurator;
      } else if (element instanceof SynchronizationVariable)
      {
         return sync;
      } else if (element instanceof DeltaVariable)
      {
         return delta;
      } else if (element instanceof CorrespondenceVariable)
      {
         return corr;
      } else if (element instanceof EMoflonEdgeVariable)
      {
         EMoflonEdgeVariable var = (EMoflonEdgeVariable) element;
         switch (((ChangeMode) var.getAdapter(ChangeMode.class)))
         {
         case TRANSLATED:
            return edgeTranslated;
         case NONE:
            return edge;
         default:
            return edge;
            // break;
         }
      } else if (element instanceof DebugCorrespondenceVariable)
      {
         return correspondence;
      } else if (element instanceof EListVariable)
      {
         EListVariable eListVar = (EListVariable) element;
         try
         {
            switch (eListVar.getName())
            {
            case DeltaValue.ADDED_EDGES:
               return edgeAdded;
            case DeltaValue.ADDED_NODES:
               return nodeAdded;
            case DeltaValue.DELETED_NODES:
               return nodeDeleted;
            case DeltaValue.DELETED_EDGES:
               return edgeDeleted;
            case DeltaValue.ATTRIBUTE_CHANGE:
               return attributeChange;
            case AdditonStateStackFrame.ADDED_ELEMENTS:
               return addedElements;
            case AdditonStateStackFrame.REVOKED_ELEMENTS:
               return revokedElements;
            case DeletionStateStackFrame.DELETED_ELEMENTS:
               return deletedElements;
            case TranslationStateStackFrame.INPUT_MATCHES:
               return precedenceGraph;
            case CorrespondenceValue.CORRESPONDENCES:
               return correspondences;
            case TripleMatchValue.CORRESPONDENCE_ELEMENTS:
               return correspondenceElements;
            case TranslationStateStackFrame.TO_BE_TRANSLATED:
               return toBeTranslated;
            case MatchValue.CONTEXT:
               return context;
            default:
               // Do nothing
            }
         } catch (DebugException e)
         {
            throw new RuntimeException(e);
         }
      } else if (element instanceof DebugEObjectProxyVariable || element instanceof AttributeProxyVariable)
      {
         IAdaptable adaptable = (IAdaptable) element;
         switch (((ChangeMode) adaptable.getAdapter(ChangeMode.class)))
         {
         case TRANSLATED:
            return itemTranslated;
         case NONE:
            return adapterFactoryLabelProvider.getImage(adaptable.getAdapter(EObject.class));
         default:
            break;
         }
      }
      return null;
   }

   @Override
   public String getText(Object element)
   {
      try
      {
         if (element instanceof IThread)
         {
            IThread thread = (IThread) element;
            return thread.getName();
         } else if (element instanceof IStackFrame)
         {
            IStackFrame frame = (IStackFrame) element;
            return frame.getName();
         }
      } catch (DebugException e)
      {
         e.printStackTrace();
      }
      System.out.println("get text for " + element);
      return "Not yet Implemented";
   }

   /**
    * Displays content to the textbox below the variables view.
    * 
    * @see org.eclipse.debug.ui.IDebugModelPresentation#computeDetail(org.eclipse.debug.core.model.IValue,
    *      org.eclipse.debug.ui.IValueDetailListener)
    */
   @Override
   public void computeDetail(IValue value, IValueDetailListener listener)
   {
      String detail = "";
      try
      {
         detail = value.getValueString();
      } catch (DebugException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      listener.detailComputed(value, detail);
   }

}
