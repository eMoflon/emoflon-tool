package org.moflon.ide.debug.core.launcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdi.Bootstrap;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.ClassType;
import com.sun.jdi.Field;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.InvalidTypeException;
import com.sun.jdi.InvocationException;
import com.sun.jdi.Method;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.StackFrame;
import com.sun.jdi.StringReference;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.Value;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector.Argument;
import com.sun.jdi.connect.Connector.IntegerArgument;
import com.sun.jdi.connect.Connector.StringArgument;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;

/**
 * This class provides helper methods that simplify the usage of JDI.
 *
 * @author Marco Ballhausen
 * @author (last editor) $Author$
 * @version $Revision$ $Date$
 */
@SuppressWarnings("restriction")
public class JDIUtil
{
   private final static String SYNCHRONIZATION_HELPER = "org.moflon.tgg.algorithm.synchronization.SynchronizationHelper";
   
   private final static String SYNCHRONIZER = "org.moflon.tgg.algorithm.synchronization.Synchronizer";

   public final static String DEBUG_SYNCHRONIZATION_HELPER = "org.moflon.ide.debug.DebugSynchronizationHelper";

   public final static String METHOD_TO_INIT_PHASE = "toInitPhase";

   public final static String METHOD_TO_DELETION_PHASE = "toDeletionPhase";

   public static final String METHOD_TO_ADDITION_PHASE = "toAdditionPhase";

   public static final String METHOD_TO_TRANSLATION_PHASE = "toTranslationPhase";

   private final static String EMFXMLCONVERTER = "org.moflon.tgg.algorithm.synchronization.EmfXmlConverter";

   private final static String MAIN_THREAD = "main";

   /**
    * Reacts on VMStart -> sets than a {@link ClassPrepareRequest}, on {@link ClassPrepareEvent} -> sets than a
    * {@link BreakpointRequest}
    * 
    * @param vm
    * @throws InterruptedException
    */
   // public static void setBreakpointRequest(VirtualMachine vm) throws InterruptedException
   // {
   // boolean terminated = false;
   // EventQueue evtQueue = vm.eventQueue();
   // while (!terminated)
   // {
   // EventSet evtSet = evtQueue.remove();
   // EventIterator evtIter = evtSet.eventIterator();
   //
   // while (!terminated && evtIter.hasNext())
   // {
   // try
   // {
   // Event evt = evtIter.next();
   // System.out.println("Receive Event: " + evt);
   // if (evt instanceof VMStartEvent)
   // {
   // // createClassPrepareRequest
   // createClassPrepareRequest(vm);
   // // continue;
   // } else if (evt instanceof ClassPrepareEvent)
   // {
   // // createBreakpointRequest
   // setBreakpoint(vm);
   // } else if (evt instanceof BreakpointEvent)
   // {
   // terminated = true;
   // return;
   // // BreakpointEvent brEvt = (BreakpointEvent) evt;
   // // ThreadReference threadRef = brEvt.thread();
   // // StackFrame stackFrame = threadRef.frame(0);
   // // List<LocalVariable> visVars = stackFrame.visibleVariables();
   // // for (LocalVariable visibleVar : visVars)
   // // {
   // // Value val = stackFrame.getValue(visibleVar);
   // // if (val instanceof StringReference)
   // // {
   // // StringReference sref = (StringReference) val;
   // // String varNameValue = sref.value();
   // // System.out.println(visibleVar.name() + " = '" + varNameValue + "'");
   // // } else if (val instanceof ObjectReference)
   // // {
   // // ObjectReference oref = (ObjectReference) val;
   // // System.out.println(visibleVar.name() + " = '" + oref.referenceType().name() + "'");
   // // }
   // // }
   // // ArrayList<Value> list = new ArrayList<Value>();
   // // list.add(stackFrame.getValue(stackFrame.visibleVariableByName("p")));
   // // getEMFString(vm, threadRef, list);
   // // terminated = true;
   // }
   // } catch (Exception exc)
   // {
   // // } catch (AbsentInformationException aie)
   // // System.out.println("AbsentInformationException: did you compile your target application with -g option?");
   // exc.printStackTrace();
   // } finally
   // {
   // if (!terminated)
   // evtSet.resume();
   // }
   // }
   // }
   // // Disconnect
   // vm.dispose();
   // }

   // public static void createClassPrepareRequest(com.sun.jdi.VirtualMachine vm)
   // {
   // ClassPrepareRequest cpr = vm.eventRequestManager().createClassPrepareRequest();
   // cpr.addClassFilter(SYNCHRONIZATION_HELPER);
   // cpr.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
   // cpr.setEnabled(true);
   // }

   public static BreakpointRequest setBreakpoint(com.sun.jdi.VirtualMachine vm, String clazz, int line) throws AbsentInformationException
   {
      ReferenceType classRef = vm.classesByName(clazz).get(0);
      BreakpointRequest bpr = vm.eventRequestManager().createBreakpointRequest(classRef.locationsOfLine(line).get(0));
      bpr.setSuspendPolicy(BreakpointRequest.SUSPEND_ALL);
      bpr.enable();
      return bpr;
   }

   private static String getEMFString(com.sun.jdi.VirtualMachine vm, ThreadReference threadRef, List<Value> list) throws InvalidTypeException,
         ClassNotLoadedException, IncompatibleThreadStateException, InvocationException
   {
      System.out.println("Receive XML Representation of remote EObject: ");
      ReferenceType classRef = vm.classesByName(EMFXMLCONVERTER).get(0);
      Method convertToXml = classRef.methodsByName("convertToXml").get(0);

      try
      {
         Value val = ((ClassType) classRef).invokeMethod(threadRef, convertToXml, list, 0);
         if (val instanceof StringReference)
         {
            StringReference sref = (StringReference) val;
            System.out.println(sref.value());
            return sref.value();
         }
      } catch (InvalidTypeException | ClassNotLoadedException | IncompatibleThreadStateException | InvocationException e)
      {
         printJDIStackTrace(e, list, threadRef);
      }
      return null;
   }

   // public static void connect(String host, int port) throws InterruptedException, IOException,
   // IllegalConnectorArgumentsException
   // {
   // VirtualMachine vm = connecting(host, port);
   // setBreakpointRequest(vm);
   // }

   // public static void main(String[] args) throws IOException, IllegalConnectorArgumentsException,
   // InterruptedException, AbsentInformationException
   // {
   // // connect("127.0.0.1", 7896);
   // connect("127.0.0.1", 59504);
   // }

   public static VirtualMachine connecting(String host, int port) throws IOException, IllegalConnectorArgumentsException
   {
      AttachingConnector ac = Bootstrap.virtualMachineManager().attachingConnectors().get(0);
      Map<String, Argument> prm = ac.defaultArguments();

      IntegerArgument portArgument = (IntegerArgument) prm.get("port");
      portArgument.setValue(port);
      StringArgument hostArgument = (StringArgument) prm.get("hostname");
      hostArgument.setValue(host);
      return ac.attach(prm);
   }

   /**
    * Prints the stack trace of an JDI exception directly to {@link System#out} of the Debuggee. This is necessary as
    * the Debugger does not know about the original exception.
    * 
    * @param e
    * @param list
    * @param mainThread
    * @throws InvalidTypeException
    * @throws ClassNotLoadedException
    * @throws IncompatibleThreadStateException
    * @throws InvocationException
    */
   private static void printJDIStackTrace(Exception e, List<Value> list, ThreadReference mainThread) throws InvalidTypeException, ClassNotLoadedException,
         IncompatibleThreadStateException, InvocationException
   {
      ObjectReference ref = ((InvocationException) e).exception();
      Method getMessage = ref.referenceType().methodsByName("printStackTrace").get(0);
      list.clear();
      ref.invokeMethod(mainThread, getMessage, list, 0);
      e.printStackTrace();
   }

   public static String getEcore(VirtualMachine vm, String ePackage) throws Exception
   {
      ThreadReference mainThread = vm.allThreads().stream().filter(t -> t.name().equals("main")).findFirst().get();
      ArrayList<Value> list = new ArrayList<Value>();
      ReferenceType syncHelper = vm.classesByName(EMFXMLCONVERTER).get(0);

      ReferenceType remotePackage = vm.classesByName(ePackage).get(0);
      Field remotePackageInstance = remotePackage.fieldByName("eINSTANCE");
      Value remotePackageValue = remotePackage.getValue(remotePackageInstance);
      list.add(remotePackageValue);
      Method getCorr = syncHelper.methodsByName("convertToXSD").get(0);
      try
      {
         Value val = mainThread.frame(0).thisObject().invokeMethod(mainThread, getCorr, list, 0);
         if (val instanceof StringReference)
         {
            StringReference sref = (StringReference) val;
            return sref.value();
         } else
         {
            throw new Exception();
         }
      } catch (InvalidTypeException | ClassNotLoadedException | InvocationException e)
      {
         printJDIStackTrace(e, list, mainThread);
      }
      return null;
   }

   public static String getStringValue(VirtualMachine vm, String getterMethod) throws IncompatibleThreadStateException, AbsentInformationException,
         InvalidTypeException, ClassNotLoadedException, InvocationException
   {
      ThreadReference mainThread = vm.allThreads().stream().filter(t -> t.name().equals("main")).findFirst().get();

      ArrayList<Value> list = new ArrayList<Value>();
      ReferenceType syncHelper = vm.classesByName(SYNCHRONIZATION_HELPER).get(0);
      Method getCorr = syncHelper.methodsByName(getterMethod).get(0);
      try
      {
         Value val = mainThread.frame(0).thisObject().invokeMethod(mainThread, getCorr, list, 0);
         list.add(val);
      } catch (InvalidTypeException | ClassNotLoadedException | InvocationException e)
      {
         printJDIStackTrace(e, list, mainThread);
      }
      return getEMFString(vm, mainThread, list);
   }

   public static String getStringValue(VirtualMachine vm, String clazz, String getterMethod) throws IncompatibleThreadStateException,
         AbsentInformationException, InvalidTypeException, ClassNotLoadedException, InvocationException
   {
      ThreadReference mainThread = vm.allThreads().stream().filter(t -> t.name().equals("main")).findFirst().get();
      ArrayList<Value> list = new ArrayList<Value>();
      ReferenceType syncHelper = vm.classesByName(clazz).get(0);
      List<Method> methods = syncHelper.methodsByName(getterMethod);
      Method getCorr = methods.stream().filter(m -> {
         try
         {
            return m.arguments().size() == list.size();
         } catch (AbsentInformationException e)
         {
            e.printStackTrace();
         }
         return false;
      }).findFirst().get();
      try
      {
         // mainThread.frame(0) -> current breakpoint/suspended location
         // mainThread.frame(1...n) -> stack frames on top (calling classes)
         synchronized (vm)
         {
            ClassType ct = (ClassType) syncHelper;
            Optional<StackFrame> frame = mainThread.frames().stream()
                  .filter(f -> (f.thisObject() != null && f.thisObject().referenceType() instanceof ClassType))
                  .filter(f -> ((ClassType) f.thisObject().referenceType()).superclass().equals(ct)).findFirst();

            // Value val = mainThread.frame(0).thisObject().invokeMethod(mainThread, getCorr, list, 0);

            if (frame.isPresent())
            {
               Value val = frame.get().thisObject().invokeMethod(mainThread, getCorr, list, 0);
               list.add(val);
            }
         }
      } catch (InvalidTypeException | ClassNotLoadedException | InvocationException e)
      {
         printJDIStackTrace(e, list, mainThread);
      }
      if (list.get(0) instanceof StringReference)
      {
         StringReference sref = (StringReference) list.get(0);
         // System.out.println(sref.value());
         return sref.value();

      } else
      {
         return getEMFString(vm, mainThread, list);
      }
   }

   public static ObjectReference getCurrentSynchronizationHelper(VirtualMachine vm) throws IncompatibleThreadStateException
   {
      ThreadReference mainThread = vm.allThreads().stream().filter(t -> t.name().equals(MAIN_THREAD)).findFirst().get();
      return mainThread.frame(0).thisObject();
   }

   public static Value getField(VirtualMachine vm, String fieldName) throws IncompatibleThreadStateException
   {
      ThreadReference mainThread = vm.allThreads().stream().filter(t -> t.name().equals(MAIN_THREAD)).findFirst().get();

      ReferenceType syncHelper = vm.classesByName(SYNCHRONIZATION_HELPER).get(0);
      Field field = syncHelper.fieldByName(fieldName);
      Value val = mainThread.frame(0).thisObject().getValue(field);
      return val;
   }

   public static Field getFieldField(VirtualMachine vm, String fieldName) throws IncompatibleThreadStateException
   {
      // ThreadReference mainThread = vm.allThreads().stream().filter(t ->
      // t.name().equals(MAIN_THREAD)).findFirst().get();
      ReferenceType syncHelper = vm.classesByName(SYNCHRONIZATION_HELPER).get(0);
      Field field = syncHelper.fieldByName(fieldName);
      return field;
   }

   /**
    * Receives the value of a field as String. The field must be of type {@link EObject}.
    * 
    * @param vm
    * @param fieldName
    * @return XML representation of the field
    * @throws IncompatibleThreadStateException
    * @throws AbsentInformationException
    * @throws InvocationException
    * @throws ClassNotLoadedException
    * @throws InvalidTypeException
    */
   public static String getFieldString(VirtualMachine vm, String fieldName) throws IncompatibleThreadStateException, AbsentInformationException,
         InvalidTypeException, ClassNotLoadedException, InvocationException
   {
      ThreadReference mainThread = vm.allThreads().stream().filter(t -> t.name().equals(MAIN_THREAD)).findFirst().get();

      ArrayList<Value> list = new ArrayList<Value>();
      ReferenceType syncHelper = vm.classesByName(SYNCHRONIZATION_HELPER).get(0);
      Field field = syncHelper.fieldByName(fieldName);
      Value val = mainThread.frame(0).thisObject().getValue(field);
      ReferenceType delta = vm.classesByName("org.moflon.tgg.algorithm.delta.Delta").get(0);
      Method toEMF = delta.methodsByName("toEMF").get(0);
      try
      {
         Value eobj = ((ObjectReference) val).invokeMethod(mainThread, toEMF, list, 0);
         list.add(eobj);
      } catch (InvalidTypeException | ClassNotLoadedException | InvocationException e)
      {
         printJDIStackTrace(e, list, mainThread);
      }
      return getEMFString(vm, mainThread, list);
   }

   public static Value getValue(VirtualMachine vm, String getterMethod) throws IncompatibleThreadStateException, AbsentInformationException,
         InvalidTypeException, ClassNotLoadedException, InvocationException
   {
      ThreadReference mainThread = vm.allThreads().stream().filter(t -> t.name().equals("main")).findFirst().get();

      ArrayList<Value> list = new ArrayList<Value>();
      ReferenceType syncHelper = vm.classesByName(SYNCHRONIZATION_HELPER).get(0);
      Method getCorr = syncHelper.methodsByName(getterMethod).get(0);
      try
      {
         return mainThread.frame(0).thisObject().invokeMethod(mainThread, getCorr, list, 0);
      } catch (InvalidTypeException | ClassNotLoadedException | InvocationException e)
      {
         printJDIStackTrace(e, list, mainThread);
      }
      return mainThread;
   }

   public static ObjectReference getObjectReference(VirtualMachine vm) throws IncompatibleThreadStateException, InvalidTypeException, ClassNotLoadedException,
         InvocationException
   {
      ThreadReference mainThread = vm.allThreads().stream().filter(t -> t.name().equals("main")).findFirst().get();

      ArrayList<Value> list = new ArrayList<Value>();
      ReferenceType syncHelper = vm.classesByName(SYNCHRONIZATION_HELPER).get(0);
      Method getCorr = syncHelper.methodsByName("getCorr").get(0);
      Value val = null;
      try
      {
         val = mainThread.frame(0).thisObject().invokeMethod(mainThread, getCorr, list, 0);
      } catch (InvalidTypeException | ClassNotLoadedException | InvocationException e)
      {
         printJDIStackTrace(e, list, mainThread);
      }

      return (ObjectReference) val;
   }
}
