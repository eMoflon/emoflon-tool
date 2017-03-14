package org.moflon.gt.mosl.codeadapter.statementrules;

import java.util.Optional;

import org.eclipse.emf.ecore.EClassifier;
import org.moflon.sdm.runtime.democles.CFVariable;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.Scope;

public interface IHandleCFVariable {
   //TODO@rkluge: Using a default method here is not necessary to my opinion. This logic represents a purely functional behavior. Why not put it in a MOSLGTUtilities class?
	default CFVariable getOrCreateVariable(Scope scope, String name, EClassifier type){
		Optional<CFVariable> opt= scope.getVariables().stream().filter(
				var -> var.getName().compareTo(name)==0 
				&& var.getType().getName().compareTo(type.getName())==0).findAny();
		if(opt.isPresent())
			return opt.get();
		else{
			CFVariable cfVar = DemoclesFactory.eINSTANCE.createCFVariable();
			cfVar.setScope(scope);
			cfVar.setName(name);
			cfVar.setType(type);
			return cfVar;
		}
	}
}
