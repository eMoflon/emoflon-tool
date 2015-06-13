package org.moflon.eclipse.genmodel;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenOperation;
import org.eclipse.emf.codegen.ecore.genmodel.impl.GenClassImpl;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class MoflonGenClass extends GenClassImpl {
	
	public List<GenOperation> getImplementedGenOperations() {
		EList<GenClass> implementedGenClasses = new UniqueEList<GenClass>(getImplementedGenClasses());
		ECollections.reverse(implementedGenClasses);
		if (needsRootImplementsInterfaceOperations()) {
			GenClass rootImplementsInterface = getGenModel().getRootImplementsInterfaceGenClass();
			if (rootImplementsInterface != null) {
				List<GenClass> allBaseClasses = new UniqueEList<GenClass>(rootImplementsInterface.getAllBaseGenClasses());
				for (Iterator<GenClass> i = allBaseClasses.iterator(); i.hasNext(); ) {
					GenClass genClass = i.next();
					if (genClass.isEObject()) {
						i.remove();
					}
				}
				allBaseClasses.add(rootImplementsInterface);
				implementedGenClasses.addAll(allBaseClasses);
			}
		}
		return collectGenOperations(this, implementedGenClasses, null, new OperationFilter());
	}

	public class OperationFilter extends CollidingGenOperationFilter {
		public boolean accept(GenOperation genOperation) {
			final MoflonClassGeneratorAdapter adapter =
					(MoflonClassGeneratorAdapter) EcoreUtil.getRegisteredAdapter(MoflonGenClass.this, MoflonClassGeneratorAdapter.class);
			return super.accept(genOperation) || adapter != null && adapter.hasGeneratedMethodBody(genOperation.getEcoreOperation());
		}
	}
}
