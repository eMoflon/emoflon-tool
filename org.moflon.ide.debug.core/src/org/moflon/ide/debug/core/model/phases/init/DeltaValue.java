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
import org.eclipse.jdt.internal.debug.core.model.JDIDebugTarget;
import org.eclipse.jdt.internal.debug.core.model.JDIValue;
import org.moflon.ide.debug.core.launcher.JDIUtil;
import org.moflon.ide.debug.core.model.MoflonDebugElement;
import org.moflon.ide.debug.core.model.MoflonVariable;
import org.xml.sax.InputSource;

import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.Value;
import com.sun.jdi.VirtualMachine;

import DebugLanguage.DebugDelta;
import TGGRuntime.CorrespondenceModel;
import TGGRuntime.TGGRuntimePackage;

@SuppressWarnings("restriction")
public class DeltaValue extends MoflonDebugElement implements IValue
{
   public static final String ATTRIBUTE_CHANGE = "Attribute change";

   public static final String DELETED_NODES = "Deleted nodes";

   public static final String DELETED_EDGES = "Deleted edges";

   public static final String ADDED_NODES = "Added nodes";

   public static final String ADDED_EDGES = "Added edges";

   String ecore;

   String value;

   DebugDelta delta;

   JDIValue jdiDelta;

   IVariable[] variables;

   /**
    * The XML representation for debug purpose.
    */
   String valueString;

   // public static String convertToXml(EObject eObject) throws IOException
   // {
   // XMLResourceImpl resource = new XMLResourceImpl();
   // XMLProcessor processor = new XMLProcessor();
   // resource.setEncoding("UTF-8");
   // resource.getContents().add(eObject);
   // return processor.saveToString(resource, null);
   // }

   // public static EObject convertToEObject(String xmlString) throws IOException
   // {
   // XMLResourceImpl resource = new XMLResourceImpl();
   // resource.setEncoding("UTF-8");
   // resource.load(new InputSource(new StringReader(xmlString)), null);
   //
   // return resource.getContents().get(0);
   // }

   // public static void registerXSD()
   // {
   // XSDEcoreBuilder xsdEcoreBuilder = new XSDEcoreBuilder();
   // ResourceSet resourceSet = new ResourceSetImpl();
   // Collection<EObject> eCorePackages = xsdEcoreBuilder.generate(URI.createFileURI("D:/model/example.xsd"));
   // for (Iterator<EObject> iter = eCorePackages.iterator(); iter.hasNext();)
   // {
   // EPackage element = (EPackage) iter.next();
   // resourceSet.getPackageRegistry().put(element.getNsURI(), element);
   // }
   // }

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

   @SuppressWarnings("unchecked")
   public static <eObj extends EObject> eObj convertToEObject(String ecore, String ecore2, String xmlString) throws IOException
   {
      ResourceSet metaResourceSet = new ResourceSetImpl();
      metaResourceSet.getPackageRegistry().put(TGGRuntimePackage.eNS_URI, TGGRuntimePackage.eINSTANCE);
      registerEcore(ecore, metaResourceSet);
      registerEcore(ecore2, metaResourceSet);
      Resource resourceA = metaResourceSet.createResource(URI.createURI("test"));
      XMLResourceImpl resource = (XMLResourceImpl) resourceA;

      resource.setEncoding("UTF-8");
      resource.load(new InputSource(new StringReader(xmlString)), null);
      return resource.getContents().size() == 0 ? null : (eObj) resource.getContents().get(0);
   }

   public DeltaValue(IDebugTarget target, DebugDelta delta)
   {
      super(target);
      this.delta = delta;
      VirtualMachine vm = ((JDIDebugTarget) target).getVM();

      try
      {
         // //ecore = JDIUtil.getEcore(vm,
         // // "LearningBoxToDictionaryIntegration.LearningBoxToDictionaryIntegrationPackage");
         // ecore = JDIUtil.getEcore(vm, "DictionaryLanguage.DictionaryLanguagePackage");
         // String ecore2 = JDIUtil.getEcore(vm, "LearningBoxLanguage.LearningBoxLanguagePackage");
         // value = JDIUtil.getFieldString(vm, "delta");
         // delta = convertToEObject(ecore, ecore2, value);

         this.variables = new IVariable[5];
         // this.variables[0] = new SingleDeltaVariable(target, Mode.ADDED_EDGES, delta.getAddedEdges());
         this.variables[0] = MoflonVariable.createVariable(delta.getAddedEdges(), ADDED_EDGES);
         this.variables[1] = MoflonVariable.createVariable(delta.getAddedNodes(), ADDED_NODES);
         // this.variables[1] = new SingleDeltaVariable(target, Mode.ADDED_NODES, delta.getAddedNodes());
         // this.variables[2] = new SingleDeltaVariable(target, Mode.DELETED_EDGES, delta.getDeletedEdges());
         this.variables[2] = MoflonVariable.createVariable(delta.getDeletedEdges(), DELETED_EDGES);
         // this.variables[3] = new SingleDeltaVariable(target, Mode.DELETED_NODES, delta.getDeletedNodes());
         this.variables[3] = MoflonVariable.createVariable(delta.getDeletedNodes(), DELETED_NODES);
         // this.variables[4] = new SingleDeltaVariable(target, Mode.ATTRIBUTE_CHANGES, delta.getAttributeChanges());
         this.variables[4] = MoflonVariable.createVariable(delta.getAttributeChanges(), ATTRIBUTE_CHANGE);

         Value oref = JDIUtil.getObjectReference(vm);
         jdiDelta = JDIValue.createValue((JDIDebugTarget) target, oref);

      } catch (IncompatibleThreadStateException e)
      {
         e.printStackTrace();
      } catch (Exception e)
      {
         e.printStackTrace();
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
      // org.moflon.tgg.algorithm.delta.Delta algoDelta = org.moflon.tgg.algorithm.delta.Delta.fromEMF(delta);
      // return algoDelta.toString();
      // if (valueString == null)
      // {
      // try
      // {
      // valueString = EmfXmlConverter.convertToXml(delta);
      // } catch (IOException e)
      // {
      // e.printStackTrace();
      // }
      // }
      return valueString;
   }

   @Override
   public boolean isAllocated() throws DebugException
   {
      return jdiDelta.isAllocated();
   }

   @Override
   public IVariable[] getVariables() throws DebugException
   {
      return variables;
   }

   @Override
   public boolean hasVariables() throws DebugException
   {
      return variables != null;
   }

   @SuppressWarnings("rawtypes")
   @Override
   public Object getAdapter(Class adapter)
   {
      if (adapter == CorrespondenceModel.class)
      {
         return delta;
      }
      // return jdiDelta.getAdapter(adapter);
      return super.getAdapter(adapter);
   }

}
