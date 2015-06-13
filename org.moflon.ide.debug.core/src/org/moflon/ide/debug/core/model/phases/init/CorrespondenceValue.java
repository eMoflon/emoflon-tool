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
import org.moflon.ide.debug.core.model.MoflonDebugElement;
import org.moflon.ide.debug.core.model.MoflonVariable;
import org.moflon.tgg.algorithm.synchronization.EmfXmlConverter;
import org.xml.sax.InputSource;

import DebugLanguage.DebugCorrespondenceModel;
import TGGRuntime.CorrespondenceModel;

public class CorrespondenceValue extends MoflonDebugElement implements IValue
{
   private static final String TARGET = "Target Model";

   private static final String SOURCE = "Source Model";

   public static final String CORRESPONDENCES = "Correspondences";

   IVariable[] variables;

   CorrespondenceModel corr;

   /**
    * The XML representation for debug purpose.
    */
   String valueString;

   public static EObject convertToEObject(String xmlString) throws IOException
   {
      XMLResourceImpl resource = new XMLResourceImpl();
      resource.setEncoding("UTF-8");
      resource.load(new InputSource(new StringReader(xmlString)), null);

      return resource.getContents().get(0);
   }

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
         // EcoreUtil.resolveAll(element); System.out.println(ecore);
         // EPackageRegistryImpl.createGlobalRegistry()
         ((EReferenceImpl) ((EClassImpl) element.getEClassifiers().get(0)).getEStructuralFeatures().get(0)).getEReferenceType().getEPackage();
         resourceSet.getPackageRegistry().put(element.getNsURI(), element);
         EPackageRegistryImpl.createGlobalRegistry().put(element.getNsURI(), element);
      }
      System.out.println(resource);
   }

   public static EObject convertToEObject2(String ecore, String ecore2, String xmlString) throws IOException
   {
      ResourceSet metaResourceSet = new ResourceSetImpl();
      Resource resourceA = metaResourceSet.createResource(URI.createURI("test"));
      XMLResourceImpl resource = (XMLResourceImpl) resourceA;
      // XMLResourceImpl resource = new XMLResourceImpl();

      resource.setEncoding("UTF-8");
      // metaResourceSet.getPackageRegistry()
      registerEcore(ecore2, resource.getResourceSet());
      registerEcore(ecore, resource.getResourceSet());
      EcoreUtil.resolveAll(resource.getResourceSet());
      resource.load(new InputSource(new StringReader(xmlString)), null);

      return resource.getContents().get(0);
   }

   public CorrespondenceValue(IDebugTarget target, DebugCorrespondenceModel dcm)
   {
      super(target);

      corr = dcm;// dcm.getCorrespondenceModel();

      this.variables = new IVariable[3];
      this.variables[0] = MoflonVariable.createVariable(corr.getSource(), SOURCE);
      this.variables[1] = MoflonVariable.createVariable(corr.getTarget(), TARGET);
      this.variables[2] = MoflonVariable.createVariable(corr.getCorrespondences(), CORRESPONDENCES);
   }

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

   @Override
   public String getReferenceTypeName() throws DebugException
   {
      return null;// jdiCorr.getReferenceTypeName();
   }

   @Override
   public String getValueString() throws DebugException
   {
      if (valueString == null)
      {
         valueString = "Count Correspondences: " + corr.getCorrespondences().size();
         // try
         // {
         // return EmfXmlConverter.convertToXml(corr);
         // } catch (IOException e)
         // {
         // e.printStackTrace();
         // }
      }
      return valueString;
   }

   @Override
   public boolean isAllocated() throws DebugException
   {
      return false;// jdiCorr.isAllocated();
   }

   @Override
   public IVariable[] getVariables() throws DebugException
   {
      return variables;
   }

   @Override
   public boolean hasVariables() throws DebugException
   {
      return variables.length > 0;
   }

   @SuppressWarnings("rawtypes")
   @Override
   public Object getAdapter(Class adapter)
   {
      if (adapter == CorrespondenceModel.class)
      {
         return corr;
      }
      // return jdiCorr.getAdapter(adapter);
      return super.getAdapter(adapter);
   }

}
