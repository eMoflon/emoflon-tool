package org.moflon.ide.debug.core.model.phases.init;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.jdt.debug.core.IJavaClassType;
import org.eclipse.jdt.debug.core.IJavaObject;
import org.eclipse.jdt.debug.core.IJavaReferenceType;
import org.eclipse.jdt.debug.core.IJavaStackFrame;
import org.eclipse.jdt.debug.core.IJavaValue;
import org.eclipse.jdt.debug.core.IJavaVariable;
import org.eclipse.jdt.internal.debug.core.model.JDIDebugTarget;
import org.eclipse.jdt.internal.debug.core.model.JDIThread;
import org.eclipse.jdt.internal.debug.core.model.JDIValue;
import org.moflon.ide.debug.core.launcher.JDIUtil;
import org.moflon.ide.debug.core.model.MoflonDebugElement;
import org.xml.sax.InputSource;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.Method;
import com.sun.jdi.NativeMethodException;
import com.sun.jdi.StackFrame;
import com.sun.jdi.VirtualMachine;

import org.moflon.tgg.debug.language.LanguagePackage;
import org.moflon.tgg.debug.language.DebugModel;
import org.moflon.tgg.debug.language.InitializationPhase;
import org.moflon.tgg.runtime.RuntimePackage;

@SuppressWarnings({ "restriction" })
public class InitialConfigurationStackFrame extends MoflonDebugElement implements IJavaStackFrame// IStackFrame //
// JDIStackFrame//
{
   IThread thread;

   StackFrame sf;

   private IVariable[] variables;

   private Method method;

   // public static void registerEcore(String ecore, ResourceSet resourceSet) throws IOException
   // {
   // XMLResourceImpl resource = (XMLResourceImpl) resourceSet.createResource(URI.createURI("test"));
   // resource.setEncoding("UTF-8");
   // resource.load(new InputSource(new StringReader(ecore)), null);
   // EcoreUtil.resolveAll(resource);
   // EcoreUtil.resolveAll(resourceSet);
   // Collection<EObject> eCorePackages = resource.getContents();
   // for (Iterator<EObject> iter = eCorePackages.iterator(); iter.hasNext();)
   // {
   // EPackage element = (EPackage) iter.next();
   // ((EReferenceImpl) ((EClassImpl)
   // element.getEClassifiers().get(0)).getEStructuralFeatures().get(0)).getEReferenceType().getEPackage();
   // resourceSet.getPackageRegistry().put(element.getNsURI(), element);
   // EPackageRegistryImpl.createGlobalRegistry().put(element.getNsURI(), element);
   // }
   // System.out.println(resource);
   // }

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

   public InitialConfigurationStackFrame(IDebugTarget target, JDIThread thread, StackFrame sf) throws IncompatibleThreadStateException,
         AbsentInformationException
   {
      // super((JDIThread) thread, sf, 0);
      super(target);
      this.thread = thread;
      this.sf = sf;
      this.method = sf.location().method();

      // Obtain debug model
      DebugModel dm = null;
      VirtualMachine vm = ((JDIDebugTarget) target).getVM();
      try
      {
         String value = JDIUtil.getStringValue(vm, JDIUtil.DEBUG_SYNCHRONIZATION_HELPER, JDIUtil.METHOD_TO_INIT_PHASE);
         dm = convertToEObject(value, DebugModel.class);
      } catch (IncompatibleThreadStateException | AbsentInformationException e)
      {
         e.printStackTrace();
      } catch (Exception e)
      {
         e.printStackTrace();
      }

      InitializationPhase ip = (InitializationPhase) dm.getPhases().get(0);
      this.variables = new IVariable[] { new CorrespondenceVariable(target, ip.getCorrespondenceModel()),
            new SynchronizationVariable(target, ip.getSynchronizationProtocol()), new RulesVariable(target, ip.getRules()),
            new DeltaVariable(target, ip.getDelta()), new ConfiguratorVariable(target) };
   }

   @Override
   public boolean canStepInto()
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean canStepOver()
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean canStepReturn()
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean isStepping()
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public void stepInto() throws DebugException
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void stepOver() throws DebugException
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void stepReturn() throws DebugException
   {
      // TODO Auto-generated method stub
   }

   @Override
   public boolean canResume()
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean canSuspend()
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean isSuspended()
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public void resume() throws DebugException
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void suspend() throws DebugException
   {
      // TODO Auto-generated method stub

   }

   @Override
   public boolean canTerminate()
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean isTerminated()
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public void terminate() throws DebugException
   {
      // TODO Auto-generated method stub

   }

   @Override
   public IThread getThread()
   {
      return thread;
   }

   @Override
   public IVariable[] getVariables() throws DebugException
   {
      return variables;
   }

   @Override
   public boolean hasVariables() throws DebugException
   {
      System.out.println("hasVariables: " + (variables.length > 0));
      return variables.length > 0;
   }

   @Override
   public int getLineNumber() throws DebugException
   {
      // TODO Auto-generated method stub
      // return 0;
      return -1;
   }

   @Override
   public int getCharStart() throws DebugException
   {
      // TODO Auto-generated method stub
      // return 0;
      return -1;
   }

   @Override
   public int getCharEnd() throws DebugException
   {
      // TODO Auto-generated method stub
      // return 0;
      return -1;
   }

   @Override
   public String getName() throws DebugException
   {
      // TODO Auto-generated method stub
      return "Initial Configuration";
   }

   @Override
   public IRegisterGroup[] getRegisterGroups() throws DebugException
   {
      // TODO Auto-generated method stub
      // return null;
      return new IRegisterGroup[0];
   }

   @Override
   public boolean hasRegisterGroups() throws DebugException
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean isPublic() throws DebugException
   {
      // TODO Auto-generated method stub
      // return false;
      return method.isPublic();
   }

   @Override
   public boolean isPrivate() throws DebugException
   {
      // TODO Auto-generated method stub
      // return false;
      return method.isPrivate();
   }

   @Override
   public boolean isProtected() throws DebugException
   {
      // TODO Auto-generated method stub
      // return false;
      return method.isProtected();
   }

   @Override
   public boolean isPackagePrivate() throws DebugException
   {
      // TODO Auto-generated method stub
      // return false;
      return method.isPackagePrivate();
   }

   @Override
   public boolean isFinal() throws DebugException
   {
      // TODO Auto-generated method stub
      // return false;
      return method.isFinal();
   }

   @Override
   public boolean isStatic() throws DebugException
   {
      // TODO Auto-generated method stub
      // return false;
      return method.isStatic();
   }

   @Override
   public boolean isSynthetic() throws DebugException
   {
      // TODO Auto-generated method stub
      // return false;
      return method.isSynthetic();
   }

   @Override
   public boolean canStepWithFilters()
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public void stepWithFilters() throws DebugException
   {
      // TODO Auto-generated method stub
      System.out.println("stepWithFilters");
   }

   @Override
   public boolean canDropToFrame()
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public void dropToFrame() throws DebugException
   {
      // TODO Auto-generated method stub
      System.out.println("dropToFrame");
   }

   @Override
   public boolean supportsDropToFrame()
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean isConstructor() throws DebugException
   {
      // TODO Auto-generated method stub
      // return false;
      return method.isConstructor();
   }

   @Override
   public boolean isNative() throws DebugException
   {
      // TODO Auto-generated method stub
      // return false;
      return method.isNative();
   }

   @Override
   public boolean isStaticInitializer() throws DebugException
   {
      // TODO Auto-generated method stub
      // return false;
      return method.isStaticInitializer();
   }

   @Override
   public boolean isSynchronized() throws DebugException
   {
      // TODO Auto-generated method stub
      // return false;
      return method.isSynchronized();
   }

   @Override
   public boolean isOutOfSynch() throws DebugException
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean isObsolete() throws DebugException
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public String getDeclaringTypeName() throws DebugException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public String getReceivingTypeName() throws DebugException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public String getSignature() throws DebugException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public List<String> getArgumentTypeNames() throws DebugException
   {
      // TODO Auto-generated method stub
      // return null;
      return method.argumentTypeNames();
   }

   @Override
   public String getMethodName() throws DebugException
   {
      // TODO Auto-generated method stub
      // return null;
      return method.name();
   }

   @Override
   public IJavaVariable findVariable(String variableName) throws DebugException
   {
      System.out.println("findVariable");
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public int getLineNumber(String stratum) throws DebugException
   {
      // TODO Auto-generated method stub
      // return 0;
      return -1;
   }

   @Override
   public String getSourceName() throws DebugException
   {
      try
      {
         return sf.location().sourceName();
      } catch (AbsentInformationException e)
      {
         return null;
      } catch (NativeMethodException e)
      {
         return null;
      } catch (RuntimeException e)
      {
         e.printStackTrace();
      }
      return null;
   }

   @Override
   public String getSourceName(String stratum) throws DebugException
   {
      // TODO Auto-generated method stub
      // return null;
      return getSourceName();
   }

   @Override
   public String getSourcePath(String stratum) throws DebugException
   {
      // TODO Auto-generated method stub
      // return null;
      return getSourcePath();
   }

   @Override
   public String getSourcePath() throws DebugException
   {
      // TODO Auto-generated method stub
      // return null;
      try
      {
         return sf.location().sourcePath();
      } catch (AbsentInformationException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return null;
   }

   @Override
   public IJavaVariable[] getLocalVariables() throws DebugException
   {
      // return null;
      try
      {
         return sf.visibleVariables().toArray(new IJavaVariable[sf.visibleVariables().size()]);
      } catch (AbsentInformationException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return null;
   }

   @Override
   public IJavaObject getThis() throws DebugException
   {
      // TODO Auto-generated method stub
      // return null;
      return (IJavaObject) JDIValue.createValue((JDIDebugTarget) getDebugTarget(), sf.thisObject());
   }

   @Override
   public IJavaClassType getDeclaringType() throws DebugException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public IJavaReferenceType getReferenceType() throws DebugException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public boolean wereLocalsAvailable()
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean isVarArgs() throws DebugException
   {
      // TODO Auto-generated method stub
      // return false;
      return method.isVarArgs();
   }

   @Override
   public boolean canForceReturn()
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public void forceReturn(IJavaValue value) throws DebugException
   {
      // TODO Auto-generated method stub
      System.out.println("forceReturn");
   }

}
