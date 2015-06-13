package org.moflon.ide.debug.core.model.phases.init;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.moflon.ide.debug.core.model.MoflonDebugElement;
import org.moflon.ide.debug.core.model.MoflonVariable;

import TGGRuntime.TripleMatch;
import TGGRuntime.TripleMatchNodeMapping;

public class TripleMatchValue extends MoflonDebugElement implements IValue
{
   public static final String CORRESPONDENCE_ELEMENTS = "Correspondence Elements";

   TripleMatch value;

   IVariable[] variables;

   public TripleMatchValue(IDebugTarget target, TripleMatch tripleMatch)
   {
      super(target);
      value = tripleMatch;

      EList<EObject> sourceElements = value.getSourceElements();
      EList<EObject> targetElements = value.getTargetElements();
      EList<EObject> contextElements = value.getContextElements();

      EList<EObject> containedEdges = value.getContainedEdges();
      EList<EObject> correspondenceElements = value.getCorrespondenceElements();
      EList<TripleMatchNodeMapping> nodeMappings = value.getNodeMappings();
      EList<EObject> createdElements = value.getCreatedElements();

      variables = new IVariable[7];
      variables[0] = MoflonVariable.createVariable(sourceElements, "Source");
      variables[1] = MoflonVariable.createVariable(targetElements, "Target");
      variables[2] = MoflonVariable.createVariable(contextElements, "Context");

      variables[3] = MoflonVariable.createVariable(containedEdges, "Contained Edges");
      variables[4] = MoflonVariable.createVariable(correspondenceElements, CORRESPONDENCE_ELEMENTS);
      variables[5] = MoflonVariable.createVariable(nodeMappings, "Node Mappings");
      variables[6] = MoflonVariable.createVariable(createdElements, "Created Elements");
   }

   @Override
   public String getReferenceTypeName() throws DebugException
   {
      return null; // jdiProtocol.getReferenceTypeName();
   }

   @Override
   public String getValueString() throws DebugException
   {
      return "Triple Match #" + value.getNumber();
      // DebugSynchronizationProtocolHelper.convertEMFTripleMatchToInternalTripleMatch(value).toString();
   }

   @Override
   public boolean isAllocated() throws DebugException
   {
      return false;// jdiProtocol.isAllocated();
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
      // return jdiProtocol.getAdapter(adapter);
      return super.getAdapter(adapter);
   }

}
