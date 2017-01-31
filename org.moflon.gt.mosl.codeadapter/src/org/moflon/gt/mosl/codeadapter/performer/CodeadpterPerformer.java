package org.moflon.gt.mosl.codeadapter.performer;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.gt.mosl.codeadapter.tie.CodeadapterTrafo;
import org.moflon.gt.mosl.moslgt.GraphTransformationFile;
import org.moflon.tgg.runtime.AbstractCorrespondence;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CodeadpterPerformer {
	private CodeadapterTrafo trafo;
	
	private static Map<Class<? extends AbstractCorrespondence>, Consumer<AbstractCorrespondence>> postProcessorCache = new HashMap<Class<? extends AbstractCorrespondence>, Consumer<AbstractCorrespondence>>();
	
	public CodeadpterPerformer(final URI rulesUri, final ResourceSet resourceSet) {
		trafo = new CodeadapterTrafo(rulesUri,resourceSet);
		postProcessorCache.clear();
		new CodeadpterPerformerAutofactory();
	}
	
	public static void setPostprocessingRule(final Class<? extends AbstractCorrespondence> clazz, Consumer<AbstractCorrespondence> consumer){
		postProcessorCache.put(clazz, consumer);
	}
	
	public static void deletePostproccessingRule(final Class<? extends AbstractCorrespondence> clazz){
		postProcessorCache.remove(clazz);
	}
	
	public void performConvertion(final GraphTransformationFile gtf){
		trafo.setSrc(gtf);
		trafo.setVerbose(false);
		trafo.integrateForward();
		performPostprocessing(trafo.getCorr().getCorrespondences());
	}
	
	private void performPostprocessing(final Collection<EObject> corrs){
		for(EObject obj : corrs){
			for(Class<? extends AbstractCorrespondence> postProcessorClass : postProcessorCache.keySet()){
				if(postProcessorClass.isInstance(obj)){
					Consumer<AbstractCorrespondence> consumer =postProcessorCache.get(postProcessorClass);
					AbstractCorrespondence corr = AbstractCorrespondence.class.cast(obj);
					consumer.accept(corr);
				}
			}
		}
	}
	
	
}
