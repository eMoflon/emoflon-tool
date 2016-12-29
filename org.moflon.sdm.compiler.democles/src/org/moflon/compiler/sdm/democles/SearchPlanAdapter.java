package org.moflon.compiler.sdm.democles;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.gervarro.democles.codegen.Chain;
import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.codegen.ImportManager;
import org.gervarro.democles.codegen.OperationSequenceCompiler;
import org.gervarro.democles.codegen.TemplateInvocation;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.compiler.CompilerPatternBody;

abstract public class SearchPlanAdapter extends AdapterImpl {
	private final String patternType;
	protected final CompilerPatternBody body;
	protected final Adornment adornment;
	protected final Chain<GeneratorOperation> searchPlan;
	protected final boolean multipleMatches;
	
	public SearchPlanAdapter(String patternType,
			CompilerPatternBody body,
			Adornment adornment,
			Chain<GeneratorOperation> searchPlan,
			boolean multipleMatches) {
		this.patternType = patternType;
		this.body = body;
		this.adornment = adornment;
		this.searchPlan = searchPlan;
		this.multipleMatches = multipleMatches;
	}

	public final String getPatternType() {
		return patternType;
	}

	public final CompilerPatternBody getBody() {
		return body;
	}

	public final Adornment getAdornment() {
		return adornment;
	}

	public final Chain<GeneratorOperation> getSearchPlan() {
		return searchPlan;
	}

	public final boolean isMultiMatch() {
		return multipleMatches;
	}

	abstract public TemplateInvocation prepareTemplateInvocation(OperationSequenceCompiler operationSequenceCompiler,
			ImportManager importManager);
	
	public boolean isAdapterForType(Object type) {
		return SearchPlanAdapter.class == type;
	}
	
	protected boolean isAlwaysSuccessful() {
		boolean result = true;
		Chain<GeneratorOperation> operationChain = searchPlan;
		while (operationChain != null) {
			result = result && operationChain.getValue().isAlwaysSuccessful();
			operationChain = operationChain.getNext();
		}
		return result;
	}
}
