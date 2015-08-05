package org.moflon.ide.debug.core.model.phases.init;

import java.io.IOException;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.jdt.debug.core.IJavaObject;
import org.eclipse.jdt.debug.core.IJavaVariable;
import org.eclipse.jdt.internal.debug.core.model.JDIDebugTarget;
import org.eclipse.jdt.internal.debug.core.model.JDIThread;
import org.eclipse.jdt.internal.debug.core.model.JDIValue;
import org.moflon.ide.debug.core.launcher.JDIUtil;
import org.moflon.ide.debug.core.model.MoflonStackFrame;
import org.moflon.ide.debug.core.model.MoflonVariable;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.InvalidTypeException;
import com.sun.jdi.InvocationException;
import com.sun.jdi.NativeMethodException;
import com.sun.jdi.StackFrame;
import com.sun.jdi.VirtualMachine;

import DebugLanguage.DebugModel;
import DebugLanguage.InitializationPhase;

@SuppressWarnings({ "restriction" })
public class InitialStateStackFrame extends MoflonStackFrame
{
   IThread thread;

   StackFrame sf;

   private IVariable[] variables;

   // private Method method;

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

   public InitialStateStackFrame(IDebugTarget target, JDIThread thread, StackFrame sf) throws IncompatibleThreadStateException, AbsentInformationException,
         InvalidTypeException, ClassNotLoadedException, InvocationException, IOException
   {
      super((JDIThread) thread, sf, 0);
      // super(target);
      this.thread = thread;
      this.sf = sf;
      // this.method = sf.location().method();

      // Obtain debug model
      DebugModel dm = computeDebugModel();

      InitializationPhase ip = (InitializationPhase) dm.getPhases().get(0);
      this.variables = new IVariable[] { MoflonVariable.createVariable(ip.getSourceProxy(), SOURCE_MODEL),
            MoflonVariable.createVariable(ip.getTargetProxy(), TARGET_MODEL), new CorrespondenceVariable(target, ip.getCorrespondenceModel()),
            new RulesVariable(target, ip.getRules()), new DeltaVariable(target, ip.getDelta()), new ConfiguratorVariable(target),
            new SynchronizationVariable(target, ip.getSynchronizationProtocol()) };
   }

   @Override
   protected DebugModel computeDebugModel() throws IncompatibleThreadStateException, AbsentInformationException, InvalidTypeException, ClassNotLoadedException,
         InvocationException, IOException
   {
      VirtualMachine vm = getVM();
      String value = JDIUtil.getStringValue(vm, JDIUtil.DEBUG_SYNCHRONIZATION_HELPER, JDIUtil.METHOD_TO_INIT_PHASE);
      return convertToEObject(value, DebugModel.class);
   }

   // @Override
   // public boolean canStepInto()
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }

   // @Override
   // public boolean canStepOver()
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }
   //
   // @Override
   // public boolean canStepReturn()
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }
   //
   // @Override
   // public boolean isStepping()
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }
   //
   // @Override
   // public void stepInto() throws DebugException
   // {
   // // TODO Auto-generated method stub
   //
   // }
   //
   // @Override
   // public void stepOver() throws DebugException
   // {
   // // TODO Auto-generated method stub
   //
   // }
   //
   // @Override
   // public void stepReturn() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // }
   //
   // @Override
   // public boolean canResume()
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }
   //
   // @Override
   // public boolean canSuspend()
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }
   //
   // @Override
   // public boolean isSuspended()
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }
   //
   // @Override
   // public void resume() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // System.out.println();
   // }
   //
   // @Override
   // public void suspend() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // System.out.println();
   // }
   //
   // @Override
   // public boolean canTerminate()
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }
   //
   // @Override
   // public boolean isTerminated()
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }
   //
   // @Override
   // public void terminate() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // System.out.println();
   // }

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

   // @Override
   // public int getLineNumber() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // // return 0;
   // return -1;
   // }
   //
   // @Override
   // public int getCharStart()
   // {
   // // TODO Auto-generated method stub
   // // return 0;
   // return -1;
   // }
   //
   // @Override
   // public int getCharEnd()
   // {
   // // TODO Auto-generated method stub
   // // return 0;
   // return -1;
   // }

   @Override
   public String getName() throws DebugException
   {
      return "Initial State";
   }

   // @Override
   // public IRegisterGroup[] getRegisterGroups()
   // {
   // // TODO Auto-generated method stub
   // // return null;
   // return new IRegisterGroup[0];
   // }
   //
   // @Override
   // public boolean hasRegisterGroups()
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }
   //
   // @Override
   // public boolean isPublic() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // // return false;
   // return method.isPublic();
   // }
   //
   // @Override
   // public boolean isPrivate() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // // return false;
   // return method.isPrivate();
   // }
   //
   // @Override
   // public boolean isProtected() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // // return false;
   // return method.isProtected();
   // }
   //
   // @Override
   // public boolean isPackagePrivate() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // // return false;
   // return method.isPackagePrivate();
   // }
   //
   // @Override
   // public boolean isFinal() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // // return false;
   // return method.isFinal();
   // }
   //
   // @Override
   // public boolean isStatic() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // // return false;
   // return method.isStatic();
   // }
   //
   // @Override
   // public boolean isSynthetic() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // // return false;
   // return method.isSynthetic();
   // }
   //
   // @Override
   // public boolean canStepWithFilters()
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }
   //
   // @Override
   // public void stepWithFilters() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // System.out.println("stepWithFilters");
   // }
   //
   // @Override
   // public boolean canDropToFrame()
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }
   //
   // @Override
   // public void dropToFrame() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // System.out.println("dropToFrame");
   // }
   //
   // @Override
   // public boolean supportsDropToFrame()
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }
   //
   // @Override
   // public boolean isConstructor() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // // return false;
   // return method.isConstructor();
   // }
   //
   // @Override
   // public boolean isNative() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // // return false;
   // return method.isNative();
   // }
   //
   // @Override
   // public boolean isStaticInitializer() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // // return false;
   // return method.isStaticInitializer();
   // }
   //
   // @Override
   // public boolean isSynchronized() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // // return false;
   // return method.isSynchronized();
   // }
   //
   // @Override
   // public boolean isOutOfSynch() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }
   //
   // @Override
   // public boolean isObsolete()
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }
   //
   // @Override
   // public String getDeclaringTypeName() throws DebugException
   // {
   // return super.getDeclaringTypeName();
   // // return null;
   // }
   //
   // @Override
   // public String getReceivingTypeName() throws DebugException
   // {
   // return super.getReceivingTypeName();
   // // return null;
   // }
   //
   // @Override
   // public String getSignature() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // return null;
   // }
   //
   // @Override
   // public List<String> getArgumentTypeNames() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // // return null;
   // return method.argumentTypeNames();
   // }
   //
   // @Override
   // public String getMethodName() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // // return null;
   // return method.name();
   // }
   //
   // @Override
   // public IJavaVariable findVariable(String variableName) throws DebugException
   // {
   // System.out.println("findVariable");
   // // TODO Auto-generated method stub
   // return null;
   // }
   //
   // @Override
   // public int getLineNumber(String stratum) throws DebugException
   // {
   // // TODO Auto-generated method stub
   // // return 0;
   // return -1;
   // }

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

   // @Override
   // public IJavaClassType getDeclaringType() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // return null;
   // }
   //
   // @Override
   // public IJavaReferenceType getReferenceType() throws DebugException
   // {
   // return super.getReferenceType();
   // }
   //
   // @Override
   // public boolean wereLocalsAvailable()
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }
   //
   // @Override
   // public boolean isVarArgs() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // // return false;
   // return method.isVarArgs();
   // }
   //
   // @Override
   // public boolean canForceReturn()
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }
   //
   // @Override
   // public void forceReturn(IJavaValue value) throws DebugException
   // {
   // // TODO Auto-generated method stub
   // System.out.println("forceReturn");
   // }

}
