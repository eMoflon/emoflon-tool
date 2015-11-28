package org.moflon.ide.debug.core.model.phases.init;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.impl.EReferenceImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.emf.ecore.xmi.util.XMLProcessor;
import org.eclipse.jdt.internal.debug.core.model.JDIValue;
import org.eclipse.xsd.ecore.XSDEcoreBuilder;
import org.moflon.ide.debug.core.model.MoflonDebugElement;
import org.xml.sax.InputSource;

import org.moflon.tgg.debug.language.DebugRules;
import org.moflon.tgg.language.analysis.StaticAnalysis;
import org.moflon.tgg.runtime.CorrespondenceModel;
import org.moflon.tgg.runtime.Delta;
import org.moflon.tgg.runtime.RuntimeFactory;
import org.moflon.tgg.runtime.RuntimePackage;

@SuppressWarnings("restriction")
public class RulesValue extends MoflonDebugElement implements IValue
{

   String ecore;

   String value;

   DebugRules rules;

   JDIValue jdiDelta;

   /**
    * The rules contained {@link StaticAnalysis}.
    */
   IVariable[] ruleVariables;

   public static EObject convertToEObject(String xmlString) throws IOException
   {
      XMLResourceImpl resource = new XMLResourceImpl();
      resource.setEncoding("UTF-8");
      resource.load(new InputSource(new StringReader(xmlString)), null);

      return resource.getContents().get(0);
   }

   /**
    * Register a given Ecore in string representation to the global package registry as well as to the given
    * resourceSet.
    * 
    * @param ecore
    * @param resourceSet
    * @throws IOException
    */
   public static void registerEcore(String ecore, ResourceSet resourceSet) throws IOException
   {
      XMLResourceImpl resource = (XMLResourceImpl) resourceSet.createResource(URI.createURI("test"));
      resource.setEncoding("UTF-8");
      resource.load(new InputSource(new StringReader(ecore)), null);
      EcoreUtil.resolveAll(resource);
      EcoreUtil.resolveAll(resourceSet);
      Collection<EObject> eCorePackages = resource.getContents();
      for (Iterator<EObject> iter = eCorePackages.iterator(); iter.hasNext();)
      {
         EPackage element = (EPackage) iter.next();
         ((EReferenceImpl) ((EClassImpl) element.getEClassifiers().get(0)).getEStructuralFeatures().get(0)).getEReferenceType().getEPackage();
         resourceSet.getPackageRegistry().put(element.getNsURI(), element);
         EPackageRegistryImpl.createGlobalRegistry().put(element.getNsURI(), element);
      }
      System.out.println(resource);
   }

   public static String convertToXml(EObject eObject) throws IOException
   {
      XMLResourceImpl resource = new XMLResourceImpl();
      XMLProcessor processor = new XMLProcessor();
      resource.setEncoding("UTF-8");
      resource.getContents().add(eObject);
      return processor.saveToString(resource, null);
   }

   public static EObject convertToEObject2(String ecore, String ecore2, String xmlString) throws IOException
   {
      ResourceSet metaResourceSet = new ResourceSetImpl();
      Resource resourceA = metaResourceSet.createResource(URI.createURI("test"));
      XMLResourceImpl resource = (XMLResourceImpl) resourceA;
      // XMLResourceImpl resource = new XMLResourceImpl();

      metaResourceSet.getPackageRegistry().put(RuntimePackage.eINSTANCE.getNsURI(), RuntimePackage.eINSTANCE);
      resource.setEncoding("UTF-8");
      registerEcore(ecore2, resource.getResourceSet());
      registerEcore(ecore, resource.getResourceSet());
      EcoreUtil.resolveAll(resource.getResourceSet());
      // resource.getContents().add(RuntimeFactory.eINSTANCE.createDelta());
      Delta delta = RuntimeFactory.eINSTANCE.createDelta();
      delta.getAddedNodes().add(delta);
      xmlString = convertToXml(delta);
      resource.load(new InputSource(new StringReader(xmlString)), null);
      return resource.getContents().size() == 0 ? null : resource.getContents().get(0);
   }

   public RulesValue(IDebugTarget target, DebugRules rules)
   {
      super(target);
      // VirtualMachine vm = ((JDIDebugTarget) target).getVM();
      this.rules = rules;

      // try
      // {
      // ecore = JDIUtil.getEcore(vm,
      // "LearningBoxToDictionaryIntegration.LearningBoxToDictionaryIntegrationPackage");
      // String ecore2 = JDIUtil.getEcore(vm, "LearningBoxLanguage.LearningBoxLanguagePackage");
      // value = JDIUtil.getStringValue(vm, "determineLookupMethods");
      // rules = convertToEObject(value);
      // // delta = convertToEObject2(ecore, ecore2, value);
      // if (rules == null)
      // {
      // rules = RuntimeFactory.eINSTANCE.createDelta();
      // }
      // Value oref = JDIUtil.getObjectReference(vm);
      //
      // jdiDelta = JDIValue.createValue((JDIDebugTarget) target, oref);
      //
      // StaticAnalysis sa = (StaticAnalysis) rules;
      // System.out.println("source rules");
      ruleVariables = new IVariable[2];
      // sa.getSourceRules().getRules().stream().forEach(r -> System.out.println(r.getRuleName()));
      // ruleVariables[0] = new RuleVariable(target, RuleVariable.Mode.SOURCES, sa.getSourceRules().getRules());
      // ruleVariables[1] = new RuleVariable(target, RuleVariable.Mode.TARGETS, sa.getTargetRules().getRules());
      ruleVariables[0] = new RuleVariable(target, RuleVariable.Mode.SOURCES, rules.getSourceRules());
      ruleVariables[1] = new RuleVariable(target, RuleVariable.Mode.TARGETS, rules.getTargetRules());
      // for (int i = 0; i < ruleVariables.length; i++)
      // {
      // ruleVariables[i] = new SourceRuleVariable(target, sa.getSourceRules().getRules().get(i));
      // }

      // System.out.println("target rules");
      // sa.getTargetRules().getRules().stream().forEach(r -> System.out.println(r.getRuleName()));
      // System.out.println();
      // sa.getSourceRules().

      // } catch (IOException | IllegalConnectorArgumentsException | IncompatibleThreadStateException |
      // AbsentInformationException | InterruptedException e)
      // {
      // e.printStackTrace();
      // } catch (Exception e)
      // {
      // e.printStackTrace();
      // }
   }

   public static void registerXSD()
   {
      XSDEcoreBuilder xsdEcoreBuilder = new XSDEcoreBuilder();
      ResourceSet resourceSet = new ResourceSetImpl();
      Collection<EObject> eCorePackages = xsdEcoreBuilder.generate(URI.createFileURI("D:/model/example.xsd"));
      for (Iterator<EObject> iter = eCorePackages.iterator(); iter.hasNext();)
      {
         EPackage element = (EPackage) iter.next();
         resourceSet.getPackageRegistry().put(element.getNsURI(), element);
      }
   }

   @Override
   public String getReferenceTypeName() throws DebugException
   {
      return jdiDelta.getReferenceTypeName();
   }

   @Override
   public String getValueString() throws DebugException
   {
      return value;
   }

   @Override
   public boolean isAllocated() throws DebugException
   {
      return jdiDelta.isAllocated();
   }

   @Override
   public IVariable[] getVariables() throws DebugException
   {
      return ruleVariables;
   }

   @Override
   public boolean hasVariables() throws DebugException
   {
      return ruleVariables.length > 0;
   }

   @SuppressWarnings("rawtypes")
   @Override
   public Object getAdapter(Class adapter)
   {
      if (adapter == CorrespondenceModel.class)
      {
         // return delta;
      }
      return jdiDelta.getAdapter(adapter);
   }

}
