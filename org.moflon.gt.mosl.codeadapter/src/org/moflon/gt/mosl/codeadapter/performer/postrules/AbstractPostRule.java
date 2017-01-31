package org.moflon.gt.mosl.codeadapter.performer.postrules;

import org.moflon.gt.mosl.codeadapter.performer.CodeadpterPerformer;

import org.moflon.tgg.runtime.AbstractCorrespondence;

public abstract class AbstractPostRule <C extends AbstractCorrespondence>{
	public AbstractPostRule (){
		CodeadpterPerformer.setPostprocessingRule(getCorrespondenceClass(), this::secretPostProcess);
	}
	
	protected abstract Class<C> getCorrespondenceClass();
	protected abstract void postProcess(C corr);
	
	private void secretPostProcess(AbstractCorrespondence corr){
		postProcess(getCorrespondenceClass().cast(corr));
	}
}
