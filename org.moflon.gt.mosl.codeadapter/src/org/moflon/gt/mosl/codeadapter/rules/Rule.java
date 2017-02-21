package org.moflon.gt.mosl.codeadapter.rules;

public interface Rule<Input>{
	<Output, Context> Output transform(Input inpu, Context context, Class<? extends Output> outputClassContext, Class<? extends Context> contextClassContext);
	Class<? extends Input> getInputClassContext();
}
