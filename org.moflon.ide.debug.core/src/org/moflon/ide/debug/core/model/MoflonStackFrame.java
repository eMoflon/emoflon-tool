package org.moflon.ide.debug.core.model;

import java.io.IOException;
import java.io.StringReader;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.jdt.debug.core.IJavaStackFrame;
import org.eclipse.jdt.internal.debug.core.model.JDIStackFrame;
import org.eclipse.jdt.internal.debug.core.model.JDIThread;
import org.moflon.ide.debug.core.Activator;
import org.moflon.tgg.debug.language.DebugModel;
import org.moflon.tgg.debug.language.LanguagePackage;
import org.moflon.tgg.runtime.RuntimePackage;
import org.xml.sax.InputSource;

import com.sun.jdi.StackFrame;

@SuppressWarnings("restriction")
public abstract class MoflonStackFrame extends JDIStackFrame implements IJavaStackFrame
{
   protected static final String SOURCE_MODEL = "Source Model";

   protected static final String TARGET_MODEL = "Target Model";
   
   abstract protected DebugModel computeDebugModel() throws Exception;

   @Override
   public String getModelIdentifier()
   {
      return Activator.ID_MOFLON_DEBUG_MODEL;
   }

   public MoflonStackFrame(JDIThread thread, StackFrame frame, int depth)
   {
      super(thread, frame, depth);
   }

   /**
    * Converts a given xml string to an instance of <i>returnType</i> that is considered to be an EObject.
    * 
    * @param xmlString
    * @param returnType
    * @return
    * @throws IOException
    */
   public static <T> T convertToEObject(String xmlString, Class<T> returnType) throws IOException
   {
      ResourceSet metaResourceSet = new ResourceSetImpl();
      Resource resourceA = metaResourceSet.createResource(URI.createURI("test"));
      XMIResourceImpl resource = (XMIResourceImpl) resourceA;

      resource.setEncoding("UTF-8");
      resource.getResourceSet().getPackageRegistry().put(LanguagePackage.eNS_URI, LanguagePackage.eINSTANCE);
      resource.getResourceSet().getPackageRegistry().put(RuntimePackage.eNS_URI, RuntimePackage.eINSTANCE);
      EcoreUtil.resolveAll(resource.getResourceSet());
      resource.load(new InputSource(new StringReader(xmlString)), null);
      //
      // Resource.Factory binary = new Resource.Factory(){
      // public Resource createResource(URI uri) {
      // return new BinaryResourceImpl(uri);
      // };
      // };
      // ResourceSet metaResourceSet = new ResourceSetImpl();
      // metaResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION,
      // binary);
      //
      // Resource resourceA = metaResourceSet.createResource(URI.createURI("test"));
      //// Resource resourceA = binary.createResource(URI.createURI("test"));
      //// XMIResourceImpl resource = (XMIResourceImpl) resourceA;
      // BinaryResourceImpl resource = (BinaryResourceImpl) resourceA;
      //
      //// resource.setEncoding("UTF-8");
      // resource.getResourceSet().getPackageRegistry().put(LanguagePackage.eNS_URI, LanguagePackage.eINSTANCE);
      // resource.getResourceSet().getPackageRegistry().put(RuntimePackage.eNS_URI, RuntimePackage.eINSTANCE);
      // EcoreUtil.resolveAll(resource.getResourceSet());
      //// HashMap<String, String> options = new HashMap<String, String>();
      //// options.put(XMLResource.OPTION_PROCESS_DANGLING_HREF, Boolean.FALSE.toString());
      //// options.put(XMLResource.OPTION_BINARY, Boolean.TRUE.toString());
      //// options.put(XMLResource.OPTION_ENCODING, "UTF-8");
      //
      // resource.load(new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(xmlString)),null);
      //// resource.load(new ByteArrayInputStream(xmlString.getBytes()),options);
      //// resource.load(new InputSource(new StringReader(xmlString)), null);

      EObject eobj = resource.getContents().stream().filter(c -> c instanceof DebugModel).findFirst().get();

      if (returnType.isInstance(eobj))
      {
         return returnType.cast(eobj);
      } else
      {
         throw new ClassCastException("The call expected traversal to return a '" + returnType.getName() + "' but instead a '"
               + (eobj == null ? null : eobj.getClass().getName()) + "' was computed. ");
      }
   }

   /**
    * Forbid step into
    * 
    * @see org.eclipse.jdt.internal.debug.core.model.JDIStackFrame#canStepInto()
    */
   @Override
   public boolean canStepInto()
   {
      return false;
   }

   @Override
   public boolean canStepOver()
   {
      return false;
   }

   @Override
   public boolean canStepWithFilters()
   {
      return false;
   }

   @Override
   public boolean canForceReturn()
   {
      return false;
   }

   @Override
   public boolean canStepReturn()
   {
      return false;
   }
}
