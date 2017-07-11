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
            .filter(var -> var.getName().equals(PatternUtil.getSafeName(name)) && var.getType().getName().equals(type.getName())).findAny();
      if (opt.isPresent())
         return opt.get();
      else
      {
         CFVariable cfVar = DemoclesFactory.eINSTANCE.createCFVariable();
         cfVar.setScope(scope);
         cfVar.setName(PatternUtil.getSafeName(name));
         cfVar.setType(CodeadapterTrafo.getInstance().getTypeContext(type));
         return cfVar;
      }
   }
}
