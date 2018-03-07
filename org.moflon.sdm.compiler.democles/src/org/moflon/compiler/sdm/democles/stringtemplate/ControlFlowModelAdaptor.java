package org.moflon.compiler.sdm.democles.stringtemplate;

import java.util.ArrayList;
import java.util.List;

import org.moflon.sdm.runtime.democles.Action;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.CFVariable;
import org.moflon.sdm.runtime.democles.ContinueStatement;
import org.moflon.sdm.runtime.democles.PatternInvocation;
import org.moflon.sdm.runtime.democles.RepetitionNode;
import org.moflon.sdm.runtime.democles.VariableReference;
import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;
import org.stringtemplate.v4.misc.STNoSuchPropertyException;

public class ControlFlowModelAdaptor extends ObjectModelAdaptor {
	public static final ControlFlowModelAdaptor INSTANCE = new ControlFlowModelAdaptor();

	public Object getProperty(Interpreter interpreter, ST template, Object object, Object property, String propertyName)
			throws STNoSuchPropertyException {
		if (object instanceof VariableReference && "index".equals(propertyName)) {
			VariableReference varRef = (VariableReference) object;
			return varRef.getInvocation().getParameters().indexOf(varRef);
		}
		if (object instanceof CFVariable && "index".equals(propertyName)) {
			CFVariable variable = (CFVariable) object;
			Action action = variable.getConstructor();
			if (action instanceof PatternInvocation) {
				PatternInvocation invocation = (PatternInvocation) action;
				List<VariableReference> parameters = invocation.getParameters();
				for (int i = 0; i < parameters.size(); i++) {
					if (parameters.get(i).getFrom() == variable) {
						return i;
					}
				}
			}
		}
		if (object instanceof PatternInvocation) {
			PatternInvocation invocation = (PatternInvocation) object;
			if ("boundParameters".equals(propertyName)) {
				ArrayList<VariableReference> boundParameters = new ArrayList<VariableReference>(
						invocation.getParameters().size());
				for (VariableReference reference : invocation.getParameters()) {
					if (!reference.isFree()) {
						boundParameters.add(reference);
					}
				}
				return boundParameters;
			} else if ("freeParameters".equals(propertyName)) {
				ArrayList<VariableReference> freeParameters = new ArrayList<VariableReference>(
						invocation.getParameters().size());
				for (VariableReference reference : invocation.getParameters()) {
					if (reference.isFree()) {
						freeParameters.add(reference);
					}
				}
				return freeParameters;
			} else if ("id".equals(propertyName)) {
				return invocation.getCfNode().getId() + "_"
						+ invocation.getPattern().eResource().getURI().fileExtension();
			}
		}
		if (object instanceof RepetitionNode && "onlyShortcuts".equals(propertyName)) {
			RepetitionNode repetitionNode = (RepetitionNode) object;
			for (CFNode lastNode : repetitionNode.getLastNodes()) {
				if (!(lastNode instanceof ContinueStatement)) {
					return false;
				}
			}
			return true;
		}
		return super.getProperty(interpreter, template, object, property, propertyName);
	}

}
