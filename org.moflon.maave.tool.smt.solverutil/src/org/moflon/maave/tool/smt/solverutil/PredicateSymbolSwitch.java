package org.moflon.maave.tool.smt.solverutil;

import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Predicate;

public abstract class PredicateSymbolSwitch {

	protected static final String SUBTRACTION_OP="-";
	protected static final String ADDITION_OP="+";
	protected static final String LARGER_EQUAL_OP=">=";
	protected static final String LARGER_OP=">";
	protected static final String SMALLER_EQUAL_OP="<=";
	protected static final String SMALLER_OP="<";
	protected static final String EQUAL_OP="=";
	protected static final String CONST_TRUE="#T";
	protected static final String CONST_FALSE="#F";
	protected static final String IMPL_CH_FUN="IMPL?";
	protected static final String LARGER_EQUAL_CH_FUN=">=?";
	protected static final String SUBTRACTION_CH_FUN="-?";
	protected static final String ADDITION_CH_FUN="+?";
	protected static final String LARGER_CH_FUN=">?";
	protected static final String SMALLER_EQUAL_CH_FUN="<=?";
	protected static final String SMALLER_CH_FUN="<?";
	protected static final String EQUAL_CH_FUN="=?";
	
	protected String doSwitch(Predicate predicate) {
		switch (predicate.getSymbol()) {
		case IMPL_CH_FUN: {
			String result = caseImplChFun(predicate);
			if (result == null)
				result = defaultCase(predicate);
			return result;
		}
		case EQUAL_CH_FUN: {
			String result = caseEqualChFun(predicate);
			if (result == null)
				result = defaultCase(predicate);
			return result;
		}
		case SMALLER_CH_FUN: {
			String result = caseSmallerChFun(predicate);
			if (result == null)
				result = defaultCase(predicate);
			return result;
		}
		case LARGER_CH_FUN: {
			String result = caseLargerChFun(predicate);
			if (result == null)
				result = defaultCase(predicate);
			return result;
		}
		case SMALLER_EQUAL_CH_FUN: {
			String result = caseSmallerEqualChFun(predicate);
			if (result == null)
				result = defaultCase(predicate);
			return result;
		}
		case LARGER_EQUAL_CH_FUN: {
			String result = caseLargerEqualChFun(predicate);
			if (result == null)
				result = defaultCase(predicate);
			return result;
		}
		case ADDITION_CH_FUN: {
			String result = caseAddChFun(predicate);
			if (result == null)
				result = defaultCase(predicate);
			return result;
		}
		case SUBTRACTION_CH_FUN: {
			String result = caseSubChFun(predicate);
			if (result == null)
				result = defaultCase(predicate);
			return result;
		}
		case EQUAL_OP: {
			String result = caseEqual(predicate);
			if (result == null)
				result = defaultCase(predicate);
			return result;
		}
		case SMALLER_OP: {
			String result = caseSmaller(predicate);
			if (result == null)
				result = defaultCase(predicate);
			return result;
		}
		case LARGER_OP: {
			String result = caseLarger(predicate);
			if (result == null)
				result = defaultCase(predicate);
			return result;
		}
		case SMALLER_EQUAL_OP: {
			String result = caseSmallerEqual(predicate);
			if (result == null)
				result = defaultCase(predicate);
			return result;
		}
		case LARGER_EQUAL_OP: {
			String result = caseLargerEqual(predicate);
			if (result == null)
				result = defaultCase(predicate);
			return result;
		}
		case ADDITION_OP: {
			String result = caseAdd(predicate);
			if (result == null)
				result = defaultCase(predicate);
			return result;
		}
		case SUBTRACTION_OP: {
			String result = caseSub(predicate);
			if (result == null)
				result = defaultCase(predicate);
			return result;
		}
		case CONST_TRUE: {
			String result = caseConstTrue(predicate);
			if (result == null)
				result = defaultCase(predicate);
			return result;
		}
		case CONST_FALSE: {
			String result = caseConstFalse(predicate);
			if (result == null)
				result = defaultCase(predicate);
			return result;
		}
		default:
			return defaultCase(predicate);
		}
	}

	
	
	protected abstract String caseImplChFun(Predicate predicate);
	
	protected abstract String caseSubChFun(Predicate predicate);

	protected abstract String caseAddChFun(Predicate predicate);

	protected abstract String caseLargerEqualChFun(Predicate predicate);

	protected abstract String caseSmallerEqualChFun(Predicate predicate);

	protected abstract String caseLargerChFun(Predicate predicate);

	protected abstract String caseSmallerChFun(Predicate predicate);

	protected abstract String caseEqualChFun(Predicate predicate);
	
	protected abstract String caseConstFalse(Predicate predicate);

	protected abstract String caseConstTrue(Predicate predicate);

	protected abstract String caseSub(Predicate predicate);

	protected abstract String caseAdd(Predicate predicate);

	protected abstract String caseLargerEqual(Predicate predicate);

	protected abstract String caseSmallerEqual(Predicate predicate);

	protected abstract String caseLarger(Predicate predicate);

	protected abstract String caseSmaller(Predicate predicate);

	protected abstract String defaultCase(Predicate predicate);

	protected abstract String caseEqual(Predicate predicate);

}
