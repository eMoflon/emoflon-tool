package org.moflon.tgg.algorithm.modelgenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.tgg.language.DomainType;
import org.moflon.tgg.language.TGGLinkVariable;
import org.moflon.tgg.language.TGGObjectVariable;
import org.moflon.tgg.language.TGGRule;
import org.moflon.tgg.language.algorithm.AlgorithmFactory;
import org.moflon.tgg.language.algorithm.TempOutputContainer;
import org.moflon.tgg.runtime.CorrespondenceModel;
import org.moflon.tgg.runtime.ModelgeneratorRuleResult;
import org.moflon.tgg.runtime.RuntimeFactory;

import SDMLanguage.patterns.BindingOperator;
import SDMLanguage.patterns.LinkVariable;
import SDMLanguage.patterns.ObjectVariable;

public class DataContainer
{

   private ModelgenStats modelgenStats;

   private Map<EClass, List<EObject>> eclassToObjects = new HashMap<EClass, List<EObject>>();

   private ResourceSet resourceSet;

   private TempOutputContainer srcTempOutputContainer, trgTempOutputContainer;

   private CorrespondenceModel correspondenceModel;

   private Map<String, RuleAnalysis> ruleNameToAnalaysis = new HashMap<String, RuleAnalysis>();

   public DataContainer()
   {

      this.resourceSet = eMoflonEMFUtil.createDefaultResourceSet();
      
      srcTempOutputContainer = AlgorithmFactory.eINSTANCE.createTempOutputContainer();
      resourceSet.createResource(eMoflonEMFUtil.createFileURI("srcTempOutputContainer", false)).getContents().add(srcTempOutputContainer);
      
      trgTempOutputContainer = AlgorithmFactory.eINSTANCE.createTempOutputContainer();
      resourceSet.createResource(eMoflonEMFUtil.createFileURI("trgTempOutputContainer", false)).getContents().add(trgTempOutputContainer);
      
      correspondenceModel = RuntimeFactory.eINSTANCE.createCorrespondenceModel();
      resourceSet.createResource(eMoflonEMFUtil.createFileURI("correspondenceModel", false)).getContents().add(correspondenceModel);

      modelgenStats = new ModelgenStats();

   }

   public ResourceSet getResourceSet()
   {
      return resourceSet;
   }

   public void extractGeneratedObjects(ModelgeneratorRuleResult result, long duration)
   {

      ModelgeneratorRuleResult ruleResult = (ModelgeneratorRuleResult) result;

      List<EObject> tempList = new ArrayList<EObject>();

      tempList.addAll(ruleResult.getSourceObjects());
      tempList.addAll(ruleResult.getTargetObjects());

      for (EObject eobject : tempList)
      {
         EClass eobjectEClass = eobject.eClass();
         addNewObjectToMap(eobject, eobjectEClass);
      }
      for (EObject eobject : ruleResult.getCorrObjects())
      {
         EClass eobjectEClass = eobject.eClass();
         addNewObjectToMap(eobject, eobjectEClass);
         for (EClass superClass : eobjectEClass.getESuperTypes())
         {
            if (!superClass.getName().equals("AbstractCorrespondence"))
            {
               addNewObjectToMap(eobject, superClass);
            }
         }
      }

      addToContainerIfNecessary(ruleResult.getSourceObjects(), srcTempOutputContainer.getPotentialRoots());
      addToContainerIfNecessary(ruleResult.getTargetObjects(), trgTempOutputContainer.getPotentialRoots());
      addToContainerIfNecessary(ruleResult.getCorrObjects(), correspondenceModel.getCorrespondences());

      modelgenStats.updateRulePerformCount(ruleResult.getRule(), ruleResult.getPerformCount());
      modelgenStats.updateRuleDuration(ruleResult.getRule(), duration);

      if (ruleNameToAnalaysis.containsKey(ruleResult.getRule()))
      {
         modelgenStats.updateNodeEdgeCount(ruleNameToAnalaysis.get(ruleResult.getRule()), ruleResult.getPerformCount());
      }
   }

   private void addNewObjectToMap(EObject eobject, EClass eobjectEClass)
   {
      if (eclassToObjects.containsKey(eobjectEClass))
      {
         eclassToObjects.get(eobjectEClass).add(eobject);
      } else
      {
         List<EObject> newEntryList = new ArrayList<EObject>();
         newEntryList.add(eobject);
         eclassToObjects.put(eobjectEClass, newEntryList);
      }
   }

   private void addToContainerIfNecessary(List<EObject> from, EList<EObject> to)
   {
      for (EObject eObject : from)
      {
         if (eObject.eContainer() == null)
         {
            to.add(eObject);
         }
      }
   }

   public List<EObject> getTypedEObjects(EClassifier eType)
   {
      return eclassToObjects.get(eType);
   }

   public TempOutputContainer getSrcTempOutputContainer()
   {
      return srcTempOutputContainer;
   }

   public TempOutputContainer getTrgTempOutputContainer()
   {
      return trgTempOutputContainer;
   }

   public CorrespondenceModel getCorrespondenceModel()
   {
      return correspondenceModel;
   }

   public ModelgenStats getModelgenStats()
   {
      return this.modelgenStats;
   }

   public Map<String, RuleAnalysis> getRuleNameToAnalaysis()
   {
      return ruleNameToAnalaysis;
   }

   public void createRuleAnalysis(TGGRule rule)
   {
      RuleAnalysis newAnalysis = new RuleAnalysis(rule.getName());
      for (ObjectVariable ov2 : rule.getObjectVariable())
      {
         if (ov2 instanceof TGGObjectVariable && ov2.getBindingOperator() == BindingOperator.CREATE)
         {
            TGGObjectVariable ov = (TGGObjectVariable) ov2;
            if (ov.getDomain().getType() == DomainType.SOURCE)
            {
               newAnalysis.setGreenSrcNodes(newAnalysis.getGreenSrcNodes() + 1);
            } else if (ov.getDomain().getType() == DomainType.TARGET)
            {
               newAnalysis.setGreenTrgNodes(newAnalysis.getGreenTrgNodes() + 1);
            } else if (ov.getDomain().getType() == DomainType.CORRESPONDENCE)
            {
               newAnalysis.setGreenCorrNodes(newAnalysis.getGreenCorrNodes() + 1);
            }
         }
      }
      for (LinkVariable lv2 : rule.getLinkVariable())
      {
         if (lv2 instanceof TGGLinkVariable && lv2.getBindingOperator() == BindingOperator.CREATE)
         {
            TGGLinkVariable lv = (TGGLinkVariable) lv2;
            if (lv.getDomain().getType() == DomainType.SOURCE)
            {
               newAnalysis.setGreenSrcEdges(newAnalysis.getGreenSrcEdges() + 1);
            } else if (lv.getDomain().getType() == DomainType.TARGET)
            {
               newAnalysis.setGreenTrgEdges(newAnalysis.getGreenTrgEdges() + 1);
            } else if (lv.getDomain().getType() == DomainType.CORRESPONDENCE)
            {
               newAnalysis.setGreenCorrEdges(newAnalysis.getGreenCorrEdges() + 1);
            }
         }
      }
      ruleNameToAnalaysis.put(rule.getName(), newAnalysis);
   }
}
