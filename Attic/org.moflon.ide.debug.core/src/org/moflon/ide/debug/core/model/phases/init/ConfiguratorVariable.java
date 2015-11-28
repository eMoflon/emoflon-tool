package org.moflon.ide.debug.core.model.phases.init;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.jdt.debug.core.IJavaVariable;
import org.eclipse.jdt.internal.debug.core.model.JDIDebugTarget;
import org.eclipse.jdt.internal.debug.core.model.JDIFieldVariable;
import org.eclipse.jdt.internal.debug.core.model.JDIValue;
import org.moflon.ide.debug.core.launcher.JDIUtil;
import org.moflon.ide.debug.core.model.MoflonDebugElement;
import org.moflon.ide.debug.core.model.MoflonDebugTarget;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.Field;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.ObjectReference;

@SuppressWarnings("restriction")
public class ConfiguratorVariable extends MoflonDebugElement implements IVariable
{
   IValue value;

   private JDIValue configuratorJdiValue;

   public ConfiguratorVariable(IDebugTarget target) throws IncompatibleThreadStateException, AbsentInformationException
   {
      super(target);
      value = new ConfiguratorValue(getDebugTarget());
      // Value val = JDIUtil.getField(((MoflonDebugTarget) target).getVM(), "configurator");
      // configuratorJdiValue = JDIValue.createValue((JDIDebugTarget) target, val);
   }

   @Override
   public void setValue(String expression) throws DebugException
   {
      // TODO Auto-generated method stub
   }

   @Override
   public void setValue(IValue value) throws DebugException
   {
      // TODO Auto-generated method stub

   }

   @Override
   public boolean supportsValueModification()
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean verifyValue(String expression) throws DebugException
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean verifyValue(IValue value) throws DebugException
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public IValue getValue() throws DebugException
   {
      return value;
   }

   @Override
   public String getName() throws DebugException
   {
      return "Configurator";
   }

   @Override
   public String getReferenceTypeName() throws DebugException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public boolean hasValueChanged() throws DebugException
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public Object getAdapter(Class adapter)
   {
      if (adapter == IJavaVariable.class)
      {
         Field field = null;
         try
         {
            field = JDIUtil.getFieldField(((MoflonDebugTarget) getDebugTarget()).getVM(), "configurator");
         } catch (IncompatibleThreadStateException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         try
         {
            ObjectReference syncHelper = JDIUtil.getCurrentSynchronizationHelper(((MoflonDebugTarget) getDebugTarget()).getVM());
            JDIFieldVariable jdifieldvar = new JDIFieldVariable((JDIDebugTarget) getDebugTarget(), field, syncHelper, null);
            // ISourceLookupDirector sourceLocator = new JavaSourceLookupDirector();
            // sourceLocator.setSourcePathComputer(DebugPlugin.getDefault().getLaunchManager()
            //                  .getSourcePathComputer("org.eclipse.jdt.launching.sourceLookup.javaSourcePathComputer")); //$NON-NLS-1$
            // sourceLocator.initializeDefaults(configuration);
            // getLaunch().setSourceLocator(new Java);
            // jdifieldvar.getLaunch().setSourceLocator(sourceLocator);
            jdifieldvar.getLaunch().setSourceLocator(getLaunch().getSourceLocator());
            return jdifieldvar;
         } catch (IncompatibleThreadStateException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      return super.getAdapter(adapter);
   }
}
