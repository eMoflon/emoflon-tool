package org.moflon.gt.mosl.codeadapter.statementrules;

import java.util.Optional;

import org.eclipse.emf.ecore.EClassifier;
import org.moflon.gt.mosl.codeadapter.CodeadapterTrafo;
import org.moflon.gt.mosl.codeadapter.utils.PatternUtil;
import org.moflon.sdm.runtime.democles.CFVariable;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.Scope;

public interface IHandleCFVariable
{
   default CFVariable getOrCreateVariable(Scope scope, String name, EClassifier type)
   {
      Optional<CFVariable> opt = scope.getVariables().stream()
            .filter(var -> var.getName().compareTo(PatternUtil.getSaveName(name)) == 0 && var.getType().getName().compareTo(type.getName()) == 0).findAny();
      if (opt.isPresent())
         return opt.get();
      else
      {
         CFVariable cfVar = DemoclesFactory.eINSTANCE.createCFVariable();
         cfVar.setScope(scope);
         cfVar.setName(PatternUtil.getSaveName(name));
         cfVar.setType(CodeadapterTrafo.getInstance().getTypeContext(type));
         return cfVar;
      }
   }
}
