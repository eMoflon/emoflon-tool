package org.moflon.ide.debug.core.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EReference;

import DebugLanguage.AttributeProxy;
import DebugLanguage.DebugEObjectProxy;

public class DebugEObjectProxyValue implements IValue
{
   DebugEObjectProxy proxy;

   IVariable[] variables;

   public DebugEObjectProxyValue(DebugEObjectProxy value)
   {
      this.proxy = value;
   }

   @Override
   public String getModelIdentifier()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public IDebugTarget getDebugTarget()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ILaunch getLaunch()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @SuppressWarnings("rawtypes")
   @Override
   public Object getAdapter(Class adapter)
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public String getReferenceTypeName() throws DebugException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public String getValueString() throws DebugException
   {
      if (proxy.getPackageName() != null && proxy.getClassName() != null)
      {
         if (isEReference(proxy))
         {
            if (isSingleReference(proxy))
            {
               return getSingleReferenceTypeName(proxy);
            } else
            {
               return "Count:" + getVariables().length;// proxy.getReferenceChildren().size() +
                                                       // proxy.getChildren().size();
               // return "Count: " + (proxy.getChildren().size() == 0 ? proxy.getReferenceChildren().size() :
               // proxy.getChildren().size());
            }
         } else
         {
            return proxy.getPackageName() + "." + proxy.getClassName();
         }
      } else if (proxy.getAttributeProxy().size() == 0 && proxy.getChildren().size() > 0)
      { // is EList
         return "Count: " + getVariables().length;// + (proxy.getChildren().size() == 0 ?
                                                  // proxy.getReferenceChildren().size() : proxy.getChildren().size());
      } else
      {
         return "null";// EObjectValue.getValueString(proxy);
      }
   }

   private String getSingleReferenceTypeName(DebugEObjectProxy proxy)
   {
      if (proxy.getReferenceChildren().size() == 0)
      {
         return "null";
      } else
      {
         return proxy.getReferenceChildren().get(0).getPackageName() + "." + proxy.getReferenceChildren().get(0).getClassName();
      }
   }

   private boolean isEReference(DebugEObjectProxy proxy)
   {
      return proxy.getClassName() != null && proxy.getClassName().equals(EReference.class.getSimpleName());
   }

   /**
    * Checks if an EReference is a list (upperbound = -1 or >1) or a single reference (upperbound = 1).
    * 
    * @param proxy
    * @return
    */
   private boolean isSingleReference(DebugEObjectProxy proxy)
   {
      if (isEReference(proxy))
      {
         AttributeProxy upperBound = proxy.getAttributeProxy().get(0);
         if (upperBound != null && upperBound.getValue().equals(1))
         {
            return true;
         } else
         {
            return false;
         }
      }
      return false;
   }

   @Override
   public boolean isAllocated() throws DebugException
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public IVariable[] getVariables() throws DebugException
   {
      if (variables == null)
      {
         EList<AttributeProxy> attributes = proxy.getAttributeProxy();
         EList<DebugEObjectProxy> children = proxy.getChildren();
         EList<DebugEObjectProxy> referenceChildren = proxy.getReferenceChildren();

         if (isEReference(proxy))
         {
            if (isSingleReference(proxy))
            {
               if (referenceChildren.size() == 1)
               {
                  this.variables = MoflonValue.createValue(referenceChildren.get(0)).getVariables();
               } else
                  this.variables = MoflonValue.createValue(referenceChildren).getVariables();
            } else
            {
               this.variables = new IVariable[children.size() + referenceChildren.size()];
               for (int i = 0; i < children.size(); i++)
               {
                  variables[i] = MoflonVariable.createVariable(children.get(i));
               }
               for (int i = 0; i < referenceChildren.size(); i++)
               {
                  variables[i + children.size()] = MoflonVariable.createVariable(referenceChildren.get(i));
               }
            }
         } else
         {
            this.variables = new IVariable[attributes.size() + children.size() + referenceChildren.size()];

            for (int i = 0; i < attributes.size(); i++)
            {
               variables[i] = MoflonVariable.createVariable(attributes.get(i));
            }

            for (int i = 0; i < children.size(); i++)
            {
               variables[i + attributes.size()] = MoflonVariable.createVariable(children.get(i));
            }

            for (int i = 0; i < referenceChildren.size(); i++)
            {
               variables[i + attributes.size() + children.size()] = MoflonVariable.createVariable(referenceChildren.get(i));
            }
         }
      }
      return variables;
   }

   @Override
   public boolean hasVariables() throws DebugException
   {
      return getVariables().length > 0;
   }

}
