/*Copyright*/
package de.uni_paderborn.fujaba.versioning;

import de.uni_kassel.features.ClassHandler;
import de.uni_kassel.features.FeatureAccessModule;
import de.uni_paderborn.fujaba.metamodel.common.FDiagram;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FElementRef;
import de.uni_paderborn.fujaba.metamodel.common.FFile;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.common.FStereotype;
import de.uni_paderborn.fujaba.metamodel.common.util.FProjectUtility;
import de.uni_paderborn.fujaba.metamodel.structure.FAssoc;
import de.uni_paderborn.fujaba.metamodel.structure.FAttr;
import de.uni_paderborn.fujaba.metamodel.structure.FBaseType;
import de.uni_paderborn.fujaba.metamodel.structure.FCardinality;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FPackage;
import de.uni_paderborn.fujaba.metamodel.structure.FRole;
import de.uni_paderborn.fujaba.metamodel.structure.util.FPackageUtility;
import de.uni_paderborn.fujaba.project.ProjectManager;
import de.uni_paderborn.fujaba.uml.behavior.UMLObject;
import de.uni_paderborn.fujaba.uml.behavior.UMLStoryPattern;
import de.uni_paderborn.fujaba.uml.structure.UMLPackage;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This manager is used to resolve context IDs. It maintains a list of {@link NamespaceManager.NamespaceHandler}s as
 * a chain of responsibility.
 * @see NamespaceHandler 
 */
public class NamespaceManager
{
   private static final NamespaceManager instance = new NamespaceManager();
   private final DefaultNamespaceHandler defaultNamespaceHandler;

   public static NamespaceManager get()
   {
      return instance;
   }

   private NamespaceManager()
   {
      defaultNamespaceHandler = new DefaultNamespaceHandler();
      getHandlers().add(0, new ProjectNamespaceHandler());
      getHandlers().add(0, new UMLPackageNamespaceHandler());
      getHandlers().add(0, new UMLStoryPatternNamespaceHandler());
   }

   /**
    * Called by FujabaCopyManager to find an object by context identifier.
    *
    * @param context    elements from the current context
    * @param identifier context identifier
    * @param type       class handler for the element to be found
    * @param isContext  true, if the identifier itself represents a context object
    * @return the element, not null
    * @throws de.uni_paderborn.fujaba.versioning.ElementNotFoundByContextIdentifier
    *          when element could not be found
    * @see de.uni_paderborn.fujaba.asg.ASGElement#getContextIdentifier(java.util.Collection
    */
   FElement findByContextIdentifier(Collection<FElement> context, String identifier, ClassHandler type,
                                    boolean isContext) throws ElementNotFoundByContextIdentifier
   {
      ElementNotFoundByContextIdentifier lastException = null;
      for (FElement contextElement : context)
      {
         for (NamespaceHandler handler : getHandlers())
         {
            try
            {
               final FElement element = handler.findByContextIdentifier(contextElement, identifier, type, isContext);
               if (element != null)
               {
                  return element;
               }
            } catch (ElementNotFoundByContextIdentifier e)
            {
               lastException = e;
            }
         }
      }
      for (FElement contextElement : context)
      {
         try
         {
            final FElement element = defaultNamespaceHandler.findByContextIdentifier(contextElement, identifier, type, isContext);
            if (element != null)
            {
               return element;
            }
         } catch (ElementNotFoundByContextIdentifier e)
         {
            lastException = e;
         }
      }
      throw new ElementNotFoundByContextIdentifier("None of the handlers was responsible for finding the element!",
            identifier, lastException);
   }

   /**
    * A namespace handler implements a method named {@link #findByContextIdentifier} to find elements via context
    * identifier in a chain of responsibility.
    */
   public interface NamespaceHandler
   {

      /**
       * This method is implemented to find elements via context identifiers, returned by
       * {@link de.uni_paderborn.fujaba.asg.ASGElement#getContextIdentifier(java.util.Collection)}.
       *
       * @param context    an element from the current context
       * @param identifier context identifier
       * @param type       class handler for the element to be found
       * @param isContext  true, if the identifier itself represents a context object
       * @return the element, null if the handler is not responsible for finding the element
       * @throws ElementNotFoundByContextIdentifier
       *          when element could not be found
       * @see de.uni_paderborn.fujaba.asg.ASGElement#getContextIdentifier(java.util.Collection)
       */
      public FElement findByContextIdentifier(FElement context, String identifier, ClassHandler type,
                                              boolean isContext) throws ElementNotFoundByContextIdentifier;
   }

   private List<NamespaceHandler> handlers = new LinkedList<NamespaceHandler>();

   /**
    * @return the list of handlers currently used to resolve context identifiers
    */
   public List<NamespaceHandler> getHandlers()
   {
      return handlers;
   }

   private class DefaultNamespaceHandler implements NamespaceHandler
   {
      public FElement findByContextIdentifier(FElement contextElement, String identifier, ClassHandler type, boolean isContext) throws ElementNotFoundByContextIdentifier
      {
         final FeatureAccessModule module = contextElement.getProject().getRepository().getFeatureAccessModule();
         try
         {
            if (isContext && module.getClassHandler(contextElement).isAssignableFrom(type))
            {
               return contextElement;
            }
         } catch (Exception e)
         {
            e.printStackTrace();
         }
         /*if ( "/".equals(identifier) ) {
            return this;
         }*/

         FElement parent;
         try
         {
            parent = contextElement.getParentElement();
         } catch (UnsupportedOperationException e)
         {
            parent = null;
         }
         if ( parent != null )
         {
            FElement foundByParent = NamespaceManager.this.findByContextIdentifier(Collections.singleton(parent), 
                  identifier, type, isContext);
            if (foundByParent != null)
            {
               return foundByParent;
            }
         }
         throw new ElementNotFoundByContextIdentifier("Context of " + getClass() + " could not find element '" +
               identifier + "' with type '" + (type != null ? type.getName() : null) + "'", identifier);
      }
   }

   private static class ProjectNamespaceHandler implements NamespaceHandler
   {
      public FElement findByContextIdentifier(FElement context, String identifier, ClassHandler type, boolean isContext) throws ElementNotFoundByContextIdentifier
      {
         if (!(context instanceof FProject))
         {
            return null; // not responsible
         }

         final FProject project = (FProject) context;
         final FeatureAccessModule module = project.getRepository().getFeatureAccessModule();
         try
         {
            if (module.getClassHandler(FDiagram.class.getName()).isAssignableFrom(type))
            {
               return (FElement) project.getFromModelRootNodes(identifier);
            } else if (module.getClassHandler(FElementRef.class.getName()).isAssignableFrom(type))
            {
               return project.getFromElementReferences(identifier);
            } else if (module.getClassHandler(FClass.class.getName()).isAssignableFrom(type))
            {
               return project.getFromFactories(FClass.class).getFromProducts(identifier);
            } else if (module.getClassHandler(FAttr.class.getName()).isAssignableFrom(type))
            {
               final int dotIndex = identifier.lastIndexOf(".");
               String clsIdentifier = identifier.substring( 0, dotIndex );
               String attrName = identifier.substring(dotIndex+1);
               final FClass cls = project.getFromFactories(FClass.class).getFromProducts(clsIdentifier);
               return cls == null ? null : cls.getFromAttrs(attrName);
            } else if (module.getClassHandler(FRole.class.getName()).isAssignableFrom(type))
            {
               final int dotIndex = identifier.lastIndexOf(".");
               String clsIdentifier = identifier.substring( 0, dotIndex );
               String roleName = identifier.substring(dotIndex+1);
               final FClass cls = project.getFromFactories(FClass.class).getFromProducts(clsIdentifier);
               for (Iterator<? extends FRole> it = cls.iteratorOfRoles(); it.hasNext();)
               {
                  FRole role = it.next();
                  final FRole partnerRole = role.getPartnerRole();
                  if ( roleName.equals(partnerRole.getName()) )
                  {
                     return partnerRole;
                  }
               }
            } else if (module.getClassHandler(FAssoc.class.getName()).isAssignableFrom(type))
            {
               FRole role = (FRole) findByContextIdentifier(context, identifier,
                     module.getClassHandler(FRole.class.getName()), isContext);
               return role != null ? role.getParentElement() : null;
            } else if (module.getClassHandler(FPackage.class.getName()).isAssignableFrom(type))
            {
               if (FPackage.ROOT_PACKAGE_CONTEXT_IDENTIFIER.equals(identifier))
               {
                  return project.getRootPackage();
               }
               return FProjectUtility.getFromPackages(project, identifier);
            } else if (module.getClassHandler(FBaseType.class.getName()).isAssignableFrom(type))
            {
               return project.getFromFactories(FBaseType.class).getFromProducts(identifier);
            } else if (module.getClassHandler(FFile.class.getName()).isAssignableFrom(type))
            {
               int index = identifier.lastIndexOf(".");
               FPackage umlPackage;
               if (index > 0)
               {
                  umlPackage = FProjectUtility.getFromPackages(project, identifier.substring(0, index - 1));
               } else
               {
                  umlPackage = project.getRootPackage();
               }
               return FPackageUtility.findFile(umlPackage, identifier.substring(index + 1));
            } else if (module.getClassHandler(FProject.class.getName()).isAssignableFrom(type))
            {
               return ProjectManager.get().getFromProjects(identifier);
            } else if (module.getClassHandler(FStereotype.class.getName()).isAssignableFrom(type))
            {
               return project.getFromFactories(FStereotype.class).getFromProducts(identifier);
            } else if (module.getClassHandler(FCardinality.class.getName()).isAssignableFrom(type))
            {
               return project.getFromFactories(FCardinality.class).getFromProducts(identifier);
            }
         }
         catch (ClassNotFoundException e)
         {
            e.printStackTrace();
         }

         return null;
      }
   }

   private static class UMLPackageNamespaceHandler implements NamespaceHandler
   {
      public FElement findByContextIdentifier(FElement context, String identifier, ClassHandler type, boolean isContext) throws ElementNotFoundByContextIdentifier
      {
         if ( !(context instanceof UMLPackage) ) return null;
         UMLPackage pkg = (UMLPackage) context;

         final FeatureAccessModule module = pkg.getProject().getRepository().getFeatureAccessModule();
         try
         {
            if (module.getClassHandler(FFile.class.getName()).isAssignableFrom(type))
            {
               return FPackageUtility.findFile(pkg, identifier);
            } else if (module.getClassHandler(FPackage.class.getName()).isAssignableFrom(type))
            {
               if (isContext)
               {
                  return pkg;
               }
               if (identifier.indexOf(".") < 0)
               {
                  UMLPackage child = pkg.getFromPackages(identifier);
                  if (child != null)
                  {
                     return child;
                  }
               }
            }
         }
         catch (ClassNotFoundException e)
         {
            e.printStackTrace();
         }

         return null;
      }
   }

   private static class UMLStoryPatternNamespaceHandler implements NamespaceHandler
   {
      public FElement findByContextIdentifier(FElement context, String identifier, ClassHandler type, boolean isContext) throws ElementNotFoundByContextIdentifier
         {
            if (!(context instanceof UMLStoryPattern))
            {
               return null;
            }
            UMLStoryPattern pattern = (UMLStoryPattern) context;
            try
            {
               if (context.getProject().getRepository().getFeatureAccessModule().getClassHandler(UMLObject.class.getName()).isAssignableFrom(type))
               {
                  return pattern.getFromObjects(identifier);
               }
            } catch (ClassNotFoundException e)
            {
               e.printStackTrace();
            }
            return null;
         }
   }
}

/*
 * $Log$
 */

