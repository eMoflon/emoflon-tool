package org.moflon.sdm.compiler.democles.derivedfeatures;

import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.codegen.eclipse.ui.LoggingSTErrorListener;
import org.moflon.eclipse.genmodel.MoflonGenClass;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class DerivedFeatureProcessor
{

   private Logger logger = Logger.getLogger(DerivedFeatureProcessor.class);

   private STGroup derivedAttributesGroup;

   private final String DERIVED_ATTRIBUTES_TEMPLATE_GROUP = "derivedAttributes";

   public String generateDerivatedFeatureGetterCode(GenFeature genFeature, String calcMethodName)
   {
      initializeTemplates();

      String derivedFeatureTemplateName = "";
      
      if (!calculationMethodExists(genFeature))
      {
         derivedFeatureTemplateName = "/preGetGenFeatureNoOperation";
      } else if (genFeature.isPrimitiveType())
      {
         derivedFeatureTemplateName = "/preGetGenFeaturePrimitiveType";
      } else if (genFeature.isReferenceType() || genFeature.isStringType() || isUserDefinedType(genFeature))
      {
         derivedFeatureTemplateName = "/preGetGenFeatureReferenceType";
      } else
      {
         derivedFeatureTemplateName = "/preGetGenFeatureUnknownType";
      }

      ST derivedFeatureTemplate = derivedAttributesGroup.getInstanceOf("/" + DERIVED_ATTRIBUTES_TEMPLATE_GROUP + derivedFeatureTemplateName);
      derivedFeatureTemplate.add("genFeature", genFeature);
      derivedFeatureTemplate.add("genFeatureType", genFeature.getImportedType(genFeature.getGenClass()));
      derivedFeatureTemplate.add("calculationMethodName", calcMethodName);
      String derivedFeatureCode = derivedFeatureTemplate.render();

      return derivedFeatureCode;
   }
   
   public String generateDerivatedFeatureConstructorCode(GenFeature genFeature, String calcMethodName, Set<EStructuralFeature> dependentFeatures)
   {
      initializeTemplates();

      String derivedFeatureTemplateName = "";
      if (!calculationMethodExists(genFeature))
      {
         derivedFeatureTemplateName = "/preConstructorGenFeatureNoOperation";
      } else if (genFeature.isPrimitiveType())
      {
         derivedFeatureTemplateName = "/preConstructorGenFeaturePrimitiveType";
      } else if (genFeature.isReferenceType() || genFeature.isStringType() || isUserDefinedType(genFeature))
      {
         derivedFeatureTemplateName = "/preConstructorGenFeatureReferenceType";
      } else
      {
         derivedFeatureTemplateName = "/preConstructorGenFeatureUnknownType";
      }

      ST derivedFeatureTemplate = derivedAttributesGroup.getInstanceOf("/" + DERIVED_ATTRIBUTES_TEMPLATE_GROUP + derivedFeatureTemplateName);
      derivedFeatureTemplate.add("genFeature", genFeature);
      derivedFeatureTemplate.add("genFeatureType", genFeature.getImportedType(genFeature.getGenClass()));
      derivedFeatureTemplate.add("calculationMethodName", calcMethodName);
      derivedFeatureTemplate.add("dependentFeatures", dependentFeatures);
      String derivedFeatureCode = derivedFeatureTemplate.render();

      return derivedFeatureCode;
   }

   /**
    * Checks if the type of the genFeature is defined in the model created by the user.
    * 
    * @param genFeature
    *           The GenFeature to check.
    * @return True, if the type is defined by the user, otherwise false.
    */
   private boolean isUserDefinedType(final GenFeature genFeature)
   {
      boolean isUserDefinedType = false;

      if (genFeature.eContainer() instanceof GenClass)
      {
         GenClass featureGenClass = (GenClass) genFeature.eContainer();

         if (featureGenClass.eContainer() instanceof GenPackage)
         {
            GenPackage genPackage = (GenPackage) featureGenClass.eContainer();

            for (GenClass genClass : genPackage.getAllSwitchGenClasses())
            {
               if (genClass instanceof MoflonGenClass && genClass.getEcoreModelElement() instanceof EClassImpl)
               {

                  EClassImpl eClassImpl = (EClassImpl) genClass.getEcoreModelElement();
                  String userDefinedClass = eClassImpl.getName();
                  String genFeatureClass = genFeature.getObjectType(featureGenClass);

                  if (genFeatureClass.equals(userDefinedClass))
                  {
                     isUserDefinedType = true;
                     break;
                  }
               }
            }
         }
      }

      return isUserDefinedType;
   }

   /**
    * Checks if there is a corresponding calculation method to a {@link GenFeature} representing a derived attribute.
    * 
    * @param genFeature
    *           Representation of a derived attribute.
    * @return True, if a calculation method exists, otherwise false.
    */
   private boolean calculationMethodExists(final GenFeature genFeature)
   {

      final String methodName = "_get" + genFeature.getCapName();
      final String returnType = DerivedFeatureExtractor.getAttributeType(genFeature);
      final EOperation eOperation = DerivedFeatureExtractor.getEOperation(genFeature, methodName, returnType, true);
      final boolean methodExists = eOperation != null;

      return methodExists;
   }

   /**
    * Initializes code templates for derived features lazily.
    */
   private void initializeTemplates()
   {
      if (this.derivedAttributesGroup == null)
      {
         this.derivedAttributesGroup = new STGroup();
         this.derivedAttributesGroup.setListener(new LoggingSTErrorListener(logger));
         this.derivedAttributesGroup.loadGroupFile("/" + DERIVED_ATTRIBUTES_TEMPLATE_GROUP + "/",
               "platform:/plugin/" + CodeGeneratorPlugin.getModuleID() + "/templates/stringtemplate/derivedAttributes.stg");
      }
   }
}