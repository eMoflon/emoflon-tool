package org.moflon.backend.ecore2fujaba;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.MoflonUtilitiesActivator;

import MoflonPropertyContainer.MoflonPropertiesContainer;
import de.uni_paderborn.fujaba.metamodel.common.FCodeStyle;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.common.util.FProjectUtility;
import de.uni_paderborn.fujaba.metamodel.factories.FFactory;
import de.uni_paderborn.fujaba.metamodel.structure.FBaseType;
import de.uni_paderborn.fujaba.metamodel.structure.FPackage;
import de.uni_paderborn.fujaba.metamodel.structure.FType;
import de.uni_paderborn.fujaba.uml.common.UMLStereotype;
import de.uni_paderborn.fujaba.uml.structure.UMLAssoc;
import de.uni_paderborn.fujaba.uml.structure.UMLAttr;
import de.uni_paderborn.fujaba.uml.structure.UMLCardinality;
import de.uni_paderborn.fujaba.uml.structure.UMLClass;
import de.uni_paderborn.fujaba.uml.structure.UMLClassDiagram;
import de.uni_paderborn.fujaba.uml.structure.UMLGeneralization;
import de.uni_paderborn.fujaba.uml.structure.UMLMethod;
import de.uni_paderborn.fujaba.uml.structure.UMLPackage;
import de.uni_paderborn.fujaba.uml.structure.UMLParam;
import de.uni_paderborn.fujaba.uml.structure.UMLRole;

/**
 * Responsible for transforming an ecore package to a fujaba project. This entails class diagrams and SDMs, and is
 * necessary for enabling code generation using CodeGen2.
 * 
 * The transformation process is quite simple and consists of two clear steps: (1) all elements (nodes) are transformed
 * to their corresponding counterparts and (2) all relationships between elements are created.
 * 
 * @author anjorin
 * @author (last editor) $Author$
 * @version $Revision$ $Date$
 */
public class Ecore2Fujaba
{
   // "ID" used to access name feature in Ecore models
   private final String NAME_FEATURE = "name";

   // Fujaba Project to be populated
   private FProject project;

   private FCodeStyle codeStyle;

   private final List<Resource> resources;
   // Ecore resource containing the input for the transformation
   private Resource ecoreResource;

   /* Maps used for transformation */

   private HashMap<EClassifier, UMLClass> eClassifierToUMLClassMap;

   private HashMap<EAttribute, UMLAttr> eAttributeToUMLAttrMap;

   private HashMap<EReference, UMLAssoc> eReferenceToUMLAssocMap;

   private HashMap<EOperation, UMLMethod> eOperationToUMLMethod;
   
   private HashMap<String, UMLAssoc> uniqueNameToUMLAssoc;
   
   private Collection<EClass> eClassesInMainProject;
   
   // Helper
   SDMEcore2SDMFujaba sdmEcore2Fujaba;


   // :~

   public Ecore2Fujaba(final FProject project, final FCodeStyle codeStyle, final List<Resource> resources, final Resource ecoreResource, final MoflonPropertiesContainer properties)
   {
      // Initialize data members
      this.project = project;
      this.codeStyle = codeStyle;
      this.resources = resources;
      this.ecoreResource = ecoreResource;

      // Initialize maps
      eClassifierToUMLClassMap = new HashMap<EClassifier, UMLClass>();
      eAttributeToUMLAttrMap = new HashMap<EAttribute, UMLAttr>();
      eReferenceToUMLAssocMap = new HashMap<EReference, UMLAssoc>();
      eOperationToUMLMethod = new HashMap<EOperation, UMLMethod>();
      uniqueNameToUMLAssoc = new HashMap<String, UMLAssoc>();
      eClassesInMainProject = new ArrayList<EClass>();
      
      // Use helper to deal with SDMs
      sdmEcore2Fujaba = new SDMEcore2SDMFujaba(ecoreResource, project, properties);
   }

   /**
    * Perform actual transformation. Input and output are set in the constructor.
    * @throws CoreException 
    */
   public void createFProject() throws CoreException
   {
	   FFactory<UMLClassDiagram> factory = project.getFromFactories(UMLClassDiagram.class);
      
     ArrayList<String> translatedPackages = new ArrayList<String>();
      for (Resource ecoreProject : resources)
      {  
         // Skip resource if it is empty
         if(ecoreProject == null || ecoreProject.getContents().isEmpty())
            continue;
            
         EObject eObject = ecoreProject.getContents().get(0);
         if (eObject instanceof EPackage)
         {
            EPackage rootPackage = (EPackage) eObject;
            
            // Skip if package has already been translated
            if(translatedPackages.contains(rootPackage.getNsURI()))
               continue;
            else
               translatedPackages.add(rootPackage.getNsURI());

            // Create new class diagram in the target project [root package => class diagram]
            UMLClassDiagram classDiagram = factory.create();
            classDiagram.setCodeStyle(codeStyle);

            // Derive diagram name from current rootPackage
            String diagramName = (String) rootPackage.eGet(rootPackage.eClass().getEStructuralFeature(NAME_FEATURE));
            classDiagram.setName(diagramName);

            // Create corresponding fujaba constructs for elements in the root package
            // This call recurses for subpackages in rootPackage
            processEObjectsInPackage(classDiagram, rootPackage, (String) rootPackage.eGet(rootPackage.eClass().getEStructuralFeature("name")), 
                  ecoreProject == ecoreResource);
         }
      }

      // Create attributes and references, as well as generalizations 
      postProcessFujabaElements();
      
      // Create SDMs for main file
      for (EOperation eOperation : eOperationToUMLMethod.keySet())
         sdmEcore2Fujaba.processSDM(eOperationToUMLMethod.get(eOperation), eOperation, eClassifierToUMLClassMap, eReferenceToUMLAssocMap, uniqueNameToUMLAssoc);
   }

   public HashMap<EOperation, UMLMethod> geteOperationToUMLMethod()
   {
      return eOperationToUMLMethod;
   }

   private void processEObjectsInPackage(final UMLClassDiagram classDiagram, final EObject parentPackage, final String fullyQualifiedPackageName, final boolean isMainProject)
   {
      EList<EObject> containedElements = parentPackage.eContents();

      for (EObject containedElement : containedElements)
      {
         if (containedElement instanceof EClass)
         {
            EClass eClass = (EClass) containedElement;

            // Create the class in the class diagram
            FFactory<UMLClass> factory = project.getFromFactories(UMLClass.class);

            UMLClass umlClass = factory.create();
            umlClass.setName((String) eClass.eGet(eClass.eClass().getEStructuralFeature(NAME_FEATURE)));
            umlClass.setCodeStyle(codeStyle);
            umlClass.setAbstract(eClass.isAbstract());
            umlClass.setVisibility(UMLClass.PUBLIC);

            // Set package
            UMLPackage rootPackage = (UMLPackage) project.getRootPackage();
            UMLPackage umlPackage = rootPackage.providePackage(fullyQualifiedPackageName);
            umlClass.setDeclaredInPackage(umlPackage);

            // Add the class to the class diagram
            classDiagram.addToElements(umlClass);
            eClassifierToUMLClassMap.put(eClass, umlClass);
            
            // If class is in main project add to extra collection
            if(isMainProject)
               eClassesInMainProject.add(eClass);

            // If this is an interface, add an <<interface>> stereotype
            if (eClass.isInterface())
            {
               FFactory<UMLStereotype> stereoTypeFactory = project.getFromFactories(UMLStereotype.class);
               UMLStereotype stereotype = stereoTypeFactory.getFromProducts(UMLStereotype.INTERFACE);
               umlClass.addToStereotypes(stereotype);
            }
         } else if (containedElement instanceof EPackage)
         {
            processEObjectsInPackage(classDiagram, containedElement,
                  fullyQualifiedPackageName + "." + containedElement.eGet(containedElement.eClass().getEStructuralFeature("name")), isMainProject);
         } else if (containedElement instanceof EEnum)
         {
            // Create a class for enumerations
            EEnum eEnum = (EEnum) containedElement;
            FFactory<UMLClass> factory = project.getFromFactories(UMLClass.class);
            UMLClass umlClass = factory.create();
            umlClass.setName((String) eEnum.eGet(eEnum.eClass().getEStructuralFeature(NAME_FEATURE)));
            umlClass.setCodeStyle(codeStyle);
            umlClass.setAbstract(false);
            umlClass.setVisibility(UMLClass.PUBLIC);

            // Set package
            UMLPackage rootPackage = (UMLPackage) project.getRootPackage();
            UMLPackage umlPackage = rootPackage.providePackage(fullyQualifiedPackageName);
            umlClass.setDeclaredInPackage(umlPackage);

            // Add the class to the class diagram
            classDiagram.addToElements(umlClass);
            eClassifierToUMLClassMap.put(eEnum, umlClass);

            // Add <<enumeration>> stereotype
            FFactory<UMLStereotype> stereoTypeFactory = project.getFromFactories(UMLStereotype.class);
            UMLStereotype stereotype = stereoTypeFactory.getFromProducts("enumeration");
            umlClass.addToStereotypes(stereotype);
         }
      }
   }

   private void postProcessFujabaElements() throws CoreException
   {
      for (EClassifier eClass : eClassifierToUMLClassMap.keySet())
      {
         EList<EObject> contents = eClass.eContents();

         // Determine class diagram
         UMLClassDiagram classDiagram = (UMLClassDiagram) eClassifierToUMLClassMap.get(eClass).getFirstFromDiagrams();
         
         for (EObject eObject : contents)
         {
            // Create attributes
            if (eObject instanceof EAttribute)
            {
               EAttribute eAttribute = (EAttribute) eObject;

               createAttribute((EClass) eClass, eAttribute);
            }
            // Create associations
            else if (eObject instanceof EReference)
            {
               EReference eReference = (EReference) eObject;

               createAssociation(classDiagram, (EClass) eClass, eReference);
            }
            // Methods (only if in main project)
            else if (eObject instanceof EOperation && eClassesInMainProject.contains(eClass))
            {
               EOperation eOperation = (EOperation) eObject;

               createMethod((EClass) eClass, eOperation);
            }
         }

         // Create generalizations
         if (eClass instanceof EClass)
         {
            EList<EClass> superTypes = ((EClass) eClass).getESuperTypes();

            if (!superTypes.isEmpty())
            {
               for (EClass superType : superTypes)
               {
                  // Look for the superclass
                  UMLClass superClass = eClassifierToUMLClassMap.get(superType);

                  if (superClass == null)
                  {
                     // The superclass is not part of this model,
                     // check if it is part of the project
                     superClass = getFujabaClass(superType);
                     classDiagram.addToElements(superClass);
                  }

                  // Superclass does not exist in this project
                  if (superClass == null)
                  {
                     // Create referenced superclass
                     FFactory<UMLClass> factory = project.getFromFactories(UMLClass.class);

                     superClass = factory.create();
                     superClass.setName(superType.getName());
                     superClass.setCodeStyle(codeStyle);
                     superClass.setAbstract(superType.isAbstract());
                     superClass.setVisibility(UMLClass.PUBLIC);

                     String fqn = MoflonUtil.getFQN(superType);

                     // Set package
                     UMLPackage rootPackage = (UMLPackage) project.getRootPackage();

                     UMLPackage umlPackage = rootPackage.providePackage(fqn);

                     superClass.setDeclaredInPackage(umlPackage);

                     // Add the class to the class diagram
                     classDiagram.addToElements(superClass);
                  }

                  FFactory<UMLGeneralization> generalizationFactory = project.getFromFactories(UMLGeneralization.class);
                  UMLGeneralization generalization = generalizationFactory.create();
                  generalization.setSubclass(eClassifierToUMLClassMap.get(eClass));
                  generalization.setSuperclass(superClass);

                  classDiagram.addToElements(generalization);
               }
            }
         }
      }
   }

   private UMLClass getFujabaClass(final EClass superType) throws CoreException
   {
      try
      {
         // Build fully qualified name as list of strings, i.e. each part is an element of the list
         String fqn = MoflonUtil.getFQN(superType);

         FPackage pack = project.getRootPackage();
         UMLClass c = (UMLClass) pack.findClass(fqn);
         return c;
      } catch (Exception e)
      {
         MoflonUtil.throwCoreExceptionAsError("Unable to resolve " + superType + ".  Are all project dependencies fulfilled?", MoflonUtilitiesActivator.PLUGIN_ID, e);
      }
      return null;
   }

   private void createMethod(final EClass eClass, final EOperation eOperation)
   {
      FFactory<UMLMethod> methodFactory = project.getFromFactories(UMLMethod.class);
      UMLMethod method = methodFactory.create();
      method.setName(eOperation.getName());
      method.setParent(eClassifierToUMLClassMap.get(eClass));
      method.setVisibility(UMLMethod.PUBLIC);

      // Set return value
      if (eOperation.getEType() == null)
      {
         // Method has no return value
         method.setResultType(project.getFromFactories(FBaseType.class).getFromProducts(FBaseType.VOID));
      } else
      {
         try{
         FType resultType = getFType(eOperation.getEType());
         method.setResultType(resultType);
         }catch (Exception e) {
            throw new IllegalArgumentException("Unable to determine the Fujaba type of " + eOperation.getEType() + ", of the return value of " + eOperation.getName() + " in " + eClass.getName() + ".  Please check that all required Projects are on the buildpath!");
         }
      }

      // Set method parameters
      FFactory<UMLParam> paramFactory = project.getFromFactories(UMLParam.class);

      for (EParameter eParameter : eOperation.getEParameters())
      {
         UMLParam parameter = paramFactory.create();

         parameter.setName(eParameter.getName());

         if(eParameter.getEType() == null)
            throw new IllegalArgumentException("Unable to determine the type of the parameter " + eParameter.getName() + ", of " + eOperation.getName() + " in " + eClass.getName());
         
         try{
            FType ftype = getFType(eParameter.getEType());
            parameter.setParamType(ftype);
         }catch (Exception e) {
            throw new IllegalArgumentException("Unable to determine the Fujaba type of " + eParameter.getName() + ", of " + eOperation.getName() + " in " + eClass.getName() + ".  Please check that all required Projects are on the buildpath!");
         }

         method.addToParam(parameter);
      }

      method.setParent(eClassifierToUMLClassMap.get(eClass));

      // Add to map for further processing
      eOperationToUMLMethod.put(eOperation, method);
   }

    private void createAssociation(final UMLClassDiagram classDiagram, final EClass eClass, final EReference eReference) throws CoreException
   {
       if(eReference.getEType() == null){
          throw new IllegalArgumentException("Type of eReference " + eClass.getName() + "::" + eReference.getName() + " is null! " +
          		"Please check if all required projects are on the buildpath.");
       } 
       
      // First check if this eReference has an opposite or if the opposite has already been handled or if this eReference is not changeable
      if (eReference.getEOpposite() == null || eReferenceToUMLAssocMap.get(eReference.getEOpposite()) == null || eReference.isChangeable() == true)
      {
         // Create UMLAssoc
         FFactory<UMLAssoc> assocFactory = project.getFromFactories(UMLAssoc.class);
         UMLAssoc umlAssoc = assocFactory.create();
         umlAssoc.setDirection(UMLAssoc.LEFTRIGHT);
         umlAssoc.setName(eReference.getName());

         // Create source UMLRole
         FFactory<UMLRole> roleFactory = project.getFromFactories(UMLRole.class);
         UMLRole sourceRole = roleFactory.create();
         sourceRole.setName(eClass.getName());

         // Create source UMLCard
         FFactory<UMLCardinality> cardFactory = project.getFromFactories(UMLCardinality.class);
         UMLCardinality sourceCard = cardFactory.getFromProducts(UMLCardinality.CARD_0_1);

         // Create target UMLRole
         UMLRole targetRole = roleFactory.create();
         targetRole.setName(eReference.getName());

         // Create target UMLCard
         UMLCardinality targetCard;
         if (eReference.getUpperBound() != -1)
         {
            targetCard = cardFactory.getFromProducts(eReference.getLowerBound() + ".." + eReference.getUpperBound());
         } else
         {
            targetCard = cardFactory.getFromProducts(eReference.getLowerBound() + "..*");
         }

         sourceRole.setCard(sourceCard);
         targetRole.setCard(targetCard);
         umlAssoc.setLeftRole(sourceRole);
         umlAssoc.setRightRole(targetRole);
         
         // Make sure there is a class in the class diagram for the target
         if (eClassifierToUMLClassMap.get(eReference.getEType()) == null
               && (eReference.getEType() instanceof EClass && getFujabaClass((EClass) eReference.getEType()) == null))
         {
            EClass c = (EClass) eReference.getEType();
            
            // Create a class for this type
            FFactory<UMLClass> factory = project.getFromFactories(UMLClass.class);

            UMLClass umlClass = factory.create();
            umlClass.setName((String) c.eGet(c.eClass().getEStructuralFeature(NAME_FEATURE)));
            umlClass.setCodeStyle(codeStyle);
            umlClass.setAbstract(c.isAbstract());
            umlClass.setVisibility(UMLClass.PUBLIC);

            // Set package
            UMLPackage rootPackage = (UMLPackage) project.getRootPackage();
            UMLPackage umlPackage = rootPackage.providePackage(MoflonUtil.getFQN(c));
            umlClass.setDeclaredInPackage(umlPackage);

            // Add the class to the class diagram
            classDiagram.addToElements(umlClass);
         }
         
         if (hasUnchangeableOrNoOpposite(eReference))
         {
            if (isNonCompositeReference(eReference) || eReference.isContainer())
            {
               sourceRole.setAdornment(UMLRole.NONE);
               sourceRole.setTarget(eClassifierToUMLClassMap.get(eClass));
               targetRole.setAdornment(UMLRole.NONE);

               if (eClassifierToUMLClassMap.get(eReference.getEType()) != null)
               {
                  targetRole.setTarget(eClassifierToUMLClassMap.get(eReference.getEType()));
               } else
               {
                  targetRole.setTarget(getFujabaClass((EClass) eReference.getEType()));
               }

               if (eReference.isTransient())
                  targetRole.setAdornment(UMLRole.REFERENCE);

               setSourceRole(classDiagram, eReference, umlAssoc, sourceRole, cardFactory.getFromProducts("0..*"), getReverseRoleName(eClass, eReference), eClass);
            } else if (eReference.isContainment())
            {
               // This is a composition without an opposite reference, or with an opposite that is unchangeable
               sourceRole.setAdornment(UMLRole.COMPOSITION);
               sourceRole.setTarget(eClassifierToUMLClassMap.get(eClass));
               targetRole.setAdornment(UMLRole.NONE);

               if (eClassifierToUMLClassMap.get(eReference.getEType()) != null)
               {
                  targetRole.setTarget(eClassifierToUMLClassMap.get(eReference.getEType()));
               } else
               {
                  targetRole.setTarget(getFujabaClass((EClass) eReference.getEType()));
               }

               setSourceRole(classDiagram, eReference, umlAssoc, sourceRole, cardFactory.getFromProducts("1"), "$eContainer", eClass);

            }
         } else if (hasOpposite(eReference))
         {
            // This is a reference with an opposite reference
            EReference oppositeReference = eReference.getEOpposite();

            // If this reference points to the container, the sides have to be switched
            if (eReference.isContainer() && oppositeReference.isContainment())
            {
               umlAssoc.setLeftRole(targetRole);
               umlAssoc.setRightRole(sourceRole);
               umlAssoc.setName(oppositeReference.getName());
               
               targetRole.setAdornment(UMLRole.COMPOSITION);
            } else if (eReference.isContainment())
            {
               sourceRole.setAdornment(UMLRole.COMPOSITION);
            } else
            {
               sourceRole.setAdornment(UMLRole.NONE);
               targetRole.setAdornment(UMLRole.NONE);
            }

            if (eClassifierToUMLClassMap.get(eReference.getEType()) != null)
            {
               targetRole.setTarget(eClassifierToUMLClassMap.get(eReference.getEType()));
            } else
            {
               targetRole.setTarget(getFujabaClass((EClass) eReference.getEType()));
            }

            // Set cardinality of opposite reference
            if (oppositeReference.getUpperBound() != -1)
            {
               sourceCard = cardFactory.getFromProducts(oppositeReference.getLowerBound() + ".." + oppositeReference.getUpperBound());
            } else
            {
               sourceCard = cardFactory.getFromProducts(oppositeReference.getLowerBound() + "..*");
            }
            setSourceRole(classDiagram, eReference, umlAssoc, sourceRole, sourceCard, oppositeReference.getName(), eClass);
            eReferenceToUMLAssocMap.put(oppositeReference, umlAssoc);
         }
         
         // Update map of unique names to associations and determine correct direction of association
         uniqueNameToUMLAssoc.put(SDMEcore2SDMFujaba.getFullyQualName(eReference.getName(), eClass, eReference.getEType()), umlAssoc);
         if (hasOpposite(eReference))
            uniqueNameToUMLAssoc.put(SDMEcore2SDMFujaba.getFullyQualName(eReference.getEOpposite().getName(), eReference.getEType(), eReference.getEOpposite().getEType()), umlAssoc);
      }
   }

   private boolean hasUnchangeableOrNoOpposite(final EReference eReference)
   {
      return (hasOpposite(eReference) && hasUnchangeableOpposite(eReference)) || hasNoOpposite(eReference);
   }

   private boolean hasUnchangeableOpposite(final EReference eReference)
   {
      return eReference.getEOpposite().isChangeable() == false;
   }

   private boolean hasOpposite(final EReference eReference)
   {
      return eReference.getEOpposite() != null;
   }

   private boolean hasNoOpposite(final EReference eReference)
   {
      return eReference.getEOpposite() == null;
   }

   private boolean isNonCompositeReference(final EReference eReference)
   {
      return !eReference.isContainment() && !eReference.isContainer();
   }

   private String getReverseRoleName(final EClass eClass, final EReference eReference)
   {
      return "$rev__" + eReference.getName() + "__" + eClass.getName() + "$";
   }

   private void setSourceRole(final UMLClassDiagram classDiagram, final EReference eReference, final UMLAssoc umlAssoc, final UMLRole sourceRole, final UMLCardinality sourceCard,
         final String name, final EClass eClass)
   {
      sourceRole.setTarget(eClassifierToUMLClassMap.get(eClass));

      sourceRole.setCard(sourceCard);
      sourceRole.setName(name);

      classDiagram.addToElements(umlAssoc);
      eReferenceToUMLAssocMap.put(eReference, umlAssoc);
   }

   private void createAttribute(final EClass eClass, final EAttribute eAttribute)
   {
      // Create the attribute
      FFactory<UMLAttr> attrFactory = project.getFromFactories(UMLAttr.class);
      UMLAttr attr = attrFactory.create();
      attr.setName(eAttribute.getName());
      attr.setInitialValue(eAttribute.getDefaultValueLiteral());
      attr.setParent(eClassifierToUMLClassMap.get(eClass));
      attr.setReadOnly(!eAttribute.isChangeable());
      attr.setVisibility(UMLAttr.PUBLIC);

      // Set the attribute type Primitive types
      if(eAttribute.getEType() == null)
         throw new IllegalArgumentException("Unable to determine the type of the attribute: " + eAttribute.getName() + ", in " + eClass.getName());
      
      attr.setAttrType(getFType(eAttribute.getEType()));

      eAttributeToUMLAttrMap.put(eAttribute, attr);
   }

   private FType getFType(final EClassifier eClassifier)
   {
      String typeName = "";

      // Check if an instance name is given
      if (eClassifier.getInstanceTypeName() == null || "".equals(eClassifier.getInstanceTypeName()))
      {
         EObject c = eClassifier;
         typeName = (String) c.eGet(c.eClass().getEStructuralFeature(NAME_FEATURE));

         while (c.eContainer() != null)
         {
            c = c.eContainer();
            typeName = (String) c.eGet(c.eClass().getEStructuralFeature(NAME_FEATURE)) + "." + typeName;
         }
      } else
      {
         typeName = eClassifier.getInstanceTypeName();
      }

      if ("java.lang.Boolean".equals(typeName) || "boolean".equals(typeName))
      {
         return project.getFromFactories(FBaseType.class).getFromProducts(FBaseType.BOOLEAN);
      } else if ("java.lang.Character".equals(typeName) || "char".equals(typeName))
      {
         return project.getFromFactories(FBaseType.class).getFromProducts(FBaseType.CHARACTER);
      } else if ("java.lang.String".equals(typeName))
      {
         return project.getFromFactories(FBaseType.class).getFromProducts(FBaseType.STRING);
      } else if ("java.lang.Integer".equals(typeName) || "int".equals(typeName))
      {
         return project.getFromFactories(FBaseType.class).getFromProducts(FBaseType.INTEGER);
      } else if ("java.lang.Byte".equals(typeName) || "byte".equals(typeName))
      {
         return project.getFromFactories(FBaseType.class).getFromProducts(FBaseType.BYTE);
      } else if ("java.lang.Short".equals(typeName) || "short".equals(typeName))
      {
         return project.getFromFactories(FBaseType.class).getFromProducts(FBaseType.SHORT_INTEGER);
      } else if ("java.lang.Long".equals(typeName) || "long".equals(typeName))
      {
         return project.getFromFactories(FBaseType.class).getFromProducts(FBaseType.LONG_INTEGER);
      } else if ("java.lang.Float".equals(typeName) || "float".equals(typeName))
      {
         return project.getFromFactories(FBaseType.class).getFromProducts(FBaseType.FLOAT);
      } else if ("java.lang.Double".equals(typeName) || "double".equals(typeName))
      {
         return project.getFromFactories(FBaseType.class).getFromProducts(FBaseType.DOUBLE);
      } else
      {
         // Complex types
         return FProjectUtility.provideClass(project, typeName);
      }
   }

   public HashMap<String, String> getSDMs()
   {
     return sdmEcore2Fujaba.getSDMs();
   }
}
