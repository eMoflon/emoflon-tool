package org.moflon.compiler.sdm.democles;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.gervarro.democles.codegen.Chain;
import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.codegen.ImportManager;
import org.gervarro.democles.codegen.OperationSequenceCompiler;
import org.gervarro.democles.codegen.TemplateInvocation;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.compiler.CompilerPatternBody;

/**
 * This class encapsulates a search plan (see {@link #getSearchPlan()}.
 * Subclasses provide an appropriate mapping to a {@link TemplateInvocation} for
 * generating code (see
 * {@link #prepareTemplateInvocation(OperationSequenceCompiler, ImportManager)}).
 * 
 * @author Gergely Varró - Initial implementation
 * @author Roland Kluge - Docu and refactoring
 *
 */
public abstract class SearchPlanAdapter extends AdapterImpl {
	private final String patternType;
	private final CompilerPatternBody body;
	private final Adornment adornment;
	private final Chain<GeneratorOperation> searchPlan;
	private final boolean multipleMatches;

	public SearchPlanAdapter(final String patternType, final CompilerPatternBody body, final Adornment adornment,
			final Chain<GeneratorOperation> searchPlan, final boolean multipleMatches) {
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

	/**
	 * Returns a {@link TemplateInvocation} for the contained search plan.
	 * 
	 * @param operationSequenceCompiler
	 *            the {@link OperationSequenceCompiler} to use for
	 * @param importManager
	 *            the {@link ImportManager} that manages the required Java imports
	 *            during the code generation
	 * @return the configured {@link TemplateInvocation}
	 */
	public abstract TemplateInvocation prepareTemplateInvocation(
			final OperationSequenceCompiler operationSequenceCompiler, final ImportManager importManager);

	/**
	 * This is an adapter for the type {@link SearchPlanAdapter}
	 */
	@Override
	public boolean isAdapterForType(Object type) {
		return SearchPlanAdapter.class == type;
	}

	/**
	 * Returns true if and only if all operations in the search plan
	 * ({@link #getSearchPlan()}) are always successful (cf.
	 * {@link GeneratorOperation#isAlwaysSuccessful()})
	 * 
	 * @return
	 */
	public boolean isAlwaysSuccessful() {
		boolean result = true;
		Chain<GeneratorOperation> operationChain = searchPlan;
		while (operationChain != null) {
			result = result && operationChain.getValue().isAlwaysSuccessful();
			if (!result)
				return false;

			operationChain = operationChain.getNext();
		}
		return result;
	}
}
