package org.moflon.compiler.sdm.democles;

import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.codegen.GeneratorVariable;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.common.runtime.OperationBuilder;
import org.gervarro.democles.compiler.CompilerPattern;
import org.gervarro.democles.compiler.CompilerPatternBuilder;
import org.gervarro.democles.specification.emf.EMFPatternBuilder;
import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.impl.DefaultPattern;
import org.gervarro.democles.specification.impl.DefaultPatternBody;

public class BindingAndBlackPatternMatcherCompiler extends PatternMatcherCompiler {

	public BindingAndBlackPatternMatcherCompiler(EMFPatternBuilder<DefaultPattern, DefaultPatternBody> patternBuilder,
			CompilerPatternBuilder compilerPatternBuilder) {
		super(patternBuilder, compilerPatternBuilder);
	}

	protected CompilerPattern compilePattern(Pattern pattern, Adornment adornment) {
		org.gervarro.democles.specification.impl.DefaultPattern patternRuntime = patternBuilder.build(pattern);
		final OperationBuilder<GeneratorOperation, GeneratorVariable> builder = new BindingAndBlackOperationBuilder(
				pattern, adornment);
		compilablePatternBuilder.addOperationBuilder(builder);
		try {
			final CompilerPattern compilerPattern = compilablePatternBuilder.build(patternRuntime);
			compilerPattern.getBodies().get(0).getOperations();
			return compilerPattern;
		} finally {
			compilablePatternBuilder.removeOperationBuilder(builder);
		}
	}
}
