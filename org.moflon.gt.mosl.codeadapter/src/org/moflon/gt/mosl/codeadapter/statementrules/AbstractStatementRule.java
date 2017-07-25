package org.moflon.gt.mosl.codeadapter.statementrules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.gervarro.democles.common.Adornment;
import org.moflon.gt.mosl.codeadapter.config.PatternBuilder;
import org.moflon.gt.mosl.codeadapter.config.PatternNameGenerator;
import org.moflon.gt.mosl.codeadapter.config.TransformationConfiguration;
import org.moflon.gt.mosl.codeadapter.utils.PatternKind;
import org.moflon.gt.mosl.codeadapter.utils.PatternUtil;
import org.moflon.gt.mosl.exceptions.PatternParameterSizeIsNotMatching;
import org.moflon.gt.mosl.moslgt.CalledPatternParameter;
import org.moflon.gt.mosl.moslgt.EClassDef;
import org.moflon.gt.mosl.moslgt.MethodParameter;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.gt.mosl.moslgt.PatternParameter;
import org.moflon.gt.mosl.moslgt.Statement;
import org.moflon.sdm.compiler.democles.validation.result.ResultFactory;
import org.moflon.sdm.compiler.democles.validation.result.ValidationReport;
import org.moflon.sdm.runtime.democles.Action;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.CFVariable;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.NodeDeletion;
import org.moflon.sdm.runtime.democles.PatternInvocation;
import org.moflon.sdm.runtime.democles.Scope;
import org.moflon.sdm.runtime.democles.VariableReference;

public abstract class AbstractStatementRule<S extends Statement> implements IStatementRule
{
   protected final TransformationConfiguration transformationConfiguration;
   
   public AbstractStatementRule(TransformationConfiguration trafoConfig)
   {
      transformationConfiguration=trafoConfig;
   }
   
   @Override
   public void invoke(Statement statement, Scope scope, CFNode previosCFNode)
   {
      castAndInvokeTransformation(statement, scope, previosCFNode);
   }

   @Override
   public boolean canHandle(Statement statement)
   {
      return getStatementClass().isInstance(statement);
   }

   /**
    * Returns the subtype of Statement that this transformation rule can transform
    */
   protected abstract Class<S> getStatementClass();

   /**
    * This method implements the transformation logic of this rule
    * 
    * @param statement
    *           the statement that has been transformed
    * @param scope
    *           the surrounding scope of the statement
    * @param previosCFNode
    *           the most recently generated control flow node
    */
   protected abstract ValidationReport transformStatement(S stmnt, Scope scope, CFNode previosCFNode);

   /**
    * This method is called after the transformation of 'statement' has completed
    * 
    * @param statement
    *           the statement that has been transformed
    * @param scope
    *           the surrounding scope of the statement
    * @param previosCFNode
    *           the most recently generated control flow node
    */
   protected abstract void invokeNextRule(S statement, Scope scope, CFNode previosCFNode);

   protected void transformAndInvokeNext(S statement, Scope scope, CFNode previosCFNode)
   {
      transformStatement(statement, scope, previosCFNode);
      invokeNextRule(statement, scope, previosCFNode);
   }

   private void castAndInvokeTransformation(final Statement statement, Scope scope, CFNode previosCFNode)
   {
      transformAndInvokeNext(getStatementClass().cast(statement), scope, previosCFNode);
   }

   protected ValidationReport handlePattern(List<CalledPatternParameter> patternInvocationStatementParamters, PatternDef patternDef, CFNode cfNode, Scope scope)
   {
      final ValidationReport validationReport = ResultFactory.eINSTANCE.createValidationReport();
      Map<String, Boolean> bindings = new HashMap<>();
      Map<String, CFVariable> env = new HashMap<>();
      List<CFVariable> cfVariables = new ArrayList<>();
      String patternName = patternDef.getName();
      List<MethodParameter> methodParameters = transformationConfiguration.getStatementCreationController().getCurrentMethod().getParameters();
      EClass eClass = EClassDef.class.cast(transformationConfiguration.getStatementCreationController().getCurrentMethod().eContainer()).getName();

      List<PatternParameter> patternParameters = patternDef.getParameters();
      // TODO@rkluge: Either create an Xtext validation rule or add an ErrorMessage to the validationReport
      if (patternParameters.size() != patternInvocationStatementParamters.size())
         throw new PatternParameterSizeIsNotMatching();

      final Set<ObjectVariableDefinition> ovs = new HashSet<>();
      ovs.addAll(patternDef.getVariables().stream().filter(var -> var instanceof ObjectVariableDefinition).map(ObjectVariableDefinition.class::cast).collect(Collectors.toList()));
      ovs.addAll(patternDef.getParameters().stream().map(pp -> PatternUtil.getCorrespondingOV(pp, patternDef)).collect(Collectors.toSet()));

      // Binding Handling
      for (final ObjectVariableDefinition ovRef : ovs)
      {
         // TODO@rkluge: I am wondering whether the normalization of variables names is used consistentlty... If in
         // doubt, better remove all invocations now.
         final CFVariable cfVar = getOrCreateVariable(scope, PatternUtil.getNormalizedVariableName(ovRef.getName()), ovRef.getType());
         final Action constructor = cfVar.getConstructor();

         if (constructor == null)
         {
            cfVariables.add(cfVar);
            bindings.put(cfVar.getName(), false);
         } else
         {
            bindings.put(cfVar.getName(), true);
         }

         env.put(cfVar.getName(), cfVar);
      }

      // Pattern Handling
      final PatternNameGenerator patternNameGenerator = transformationConfiguration.getPatternCreationController().getPatternNameGenerator();
      patternNameGenerator.setCFNode(cfNode);
      patternNameGenerator.setPatternDefinition(patternDef);
      final PatternBuilder patternBuilder = transformationConfiguration.getPatternCreationController();
      patternBuilder.createPattern(patternDef, bindings, env, patternNameGenerator, eClass);

      final SortedMap<PatternKind, PatternInvocation> invocations = patternBuilder.getPatternInvocations(patternName);

      invocations.values().stream().forEach(invocation -> invocation.setCfNode(cfNode));
      final List<PatternKind> kindOrder = new ArrayList<PatternKind>(invocations.keySet());
      for (int invocationIndex = invocations.size() - 1; invocationIndex >= 0; invocationIndex--)
      {
         final PatternKind patternKind = kindOrder.get(invocationIndex);
         final PatternInvocation invocation = invocations.get(patternKind);
         if (invocationIndex > 0)
         {
            cfNode.getActions().get(invocationIndex - 1).setNext(invocation);
         }
         cfNode.setMainAction(invocation);

         cfVariables.stream().filter(cfVar -> patternBuilder.isConstructorPattern(cfNode, cfVar, invocation, methodParameters, patternName))
               .forEach(cfVar -> cfVar.setConstructor(invocation));

         final Adornment adornment = calculateAdornment(invocation);
         // TODO@rkluge: Here, we escape from a stateless function to the very statefull CodeAdapterTrafo singleton
         validationReport.merge(transformationConfiguration.getPatternMatchingController().generateSearchPlan(invocation.getPattern(), adornment,
               invocation.isMultipleMatch(), patternKind.getSuffix()));
      }

      final NodeDeletion nodeDeletion = patternBuilder.getNodeDeletion(patternName, cfVariables);
      if (nodeDeletion != null)
      {
         PatternInvocation lastInvocation = invocations.get(kindOrder.get(invocations.size() - 1));
         lastInvocation.setNext(nodeDeletion);
         nodeDeletion.setPrev(lastInvocation);
         nodeDeletion.setCfNode(cfNode);
      }

      return validationReport;
   }

   protected CFVariable getOrCreateVariable(Scope scope, String name, EClassifier type)
   {
      Optional<CFVariable> opt = scope.getVariables().stream().filter(var -> var.getName().equals(PatternUtil.getNormalizedVariableName(name)))
            .filter(var -> var.getType().getName().equals(type.getName())).findAny();
      if (opt.isPresent())
      {
         return opt.get();
      } else
      {
         final CFVariable cfVariable = DemoclesFactory.eINSTANCE.createCFVariable();
         cfVariable.setScope(scope);
         cfVariable.setName(PatternUtil.getNormalizedVariableName(name));
         cfVariable.setType(transformationConfiguration.getContextController().getTypeContext(type));
         return cfVariable;
      }
   }

   private Adornment calculateAdornment(final PatternInvocation invocation)
   {
      final EList<VariableReference> parameters = invocation.getParameters();
      final Adornment adornment = new Adornment(parameters.size());
      int i = 0;
      for (final VariableReference variableRef : parameters)
      {
         final int value = variableRef.isFree() ? Adornment.FREE : Adornment.BOUND;
         adornment.set(i, value);
         i++;
      }
      return adornment;
   }

}
