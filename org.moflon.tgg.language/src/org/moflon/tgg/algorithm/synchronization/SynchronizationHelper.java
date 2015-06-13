package org.moflon.tgg.algorithm.synchronization;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.tgg.algorithm.configuration.Configurator;
import org.moflon.tgg.algorithm.datastructures.SynchronizationProtocol;
import org.moflon.tgg.algorithm.delta.Delta;
import org.moflon.tgg.algorithm.delta.OnlineChangeDetector;
import org.moflon.tgg.algorithm.exceptions.LocalCompletenessException;

import org.moflon.tgg.algorithm.synchronization.DebugBreakpoint.Phase;
import TGGLanguage.TGGLanguagePackage;
import TGGLanguage.algorithm.AlgorithmFactory;
import TGGLanguage.algorithm.TempOutputContainer;
import TGGLanguage.analysis.Rule;
import TGGLanguage.analysis.StaticAnalysis;
import TGGRuntime.CorrespondenceModel;
import TGGRuntime.EMoflonEdge;
import TGGRuntime.TGGRuntimeFactory;
import TGGRuntime.impl.MatchImpl;

/**
 * Responsible for checking if input data makes sense, inducing default deltas for the batch case, and establishing all
 * required components if necessary with useful default values. This helper also invokes the synchronizer and provides a
 * simple interface to the user, hiding as much details as possible. The generated stubs in a TGG project subclass this
 * helper.
 * 
 *
 */
public class SynchronizationHelper
{
   // Members

   private Logger logger = Logger.getLogger(SynchronizationHelper.class);

   protected ResourceSet set;

   protected EObject src;

   protected EObject trg;

   protected CorrespondenceModel corr;

   protected StaticAnalysis rules;

   protected String projectName;

   protected Resource corrPackageResource;

   protected Configurator configurator;

   protected Delta delta;

   protected SynchronizationProtocol protocol;

   protected Consumer<EObject> changeSrc;

   protected Consumer<EObject> changeTrg;

   protected TempOutputContainer tempOutputContainer;

   protected boolean batchOptimization = false;

   protected boolean verbose = false;

   // Setters

   private boolean isInTheCorrectResourceSet(final EObject model)
   {
      if(model.eIsProxy())
         throw new IllegalStateException("Model must not be a proxy: '" + model.toString() + "'.");
      return set == model.eResource().getResourceSet();
   }
   
   public void setSrc(final EObject sourceModel)
   {
      if (!isInTheCorrectResourceSet(sourceModel)) {
         throw new IllegalStateException("Source model must be in the resource set of your synchronization helper");
      }
      this.src = sourceModel;
   }

   public void setTrg(final EObject targetModel)
   {
      if (!isInTheCorrectResourceSet(targetModel)) {
         throw new IllegalStateException("Target model must be in the resource set of your synchronization helper");
      }
      this.trg = targetModel;
   }

   public void setCorr(final EObject corrModell)
   {
      if (!isInTheCorrectResourceSet(corrModell)) {
         throw new IllegalStateException("Correspondence model must be in the resource set of your synchronization helper");
      }
      this.corr = (CorrespondenceModel) corrModell;
   }

   private void setCorrPackage(final EPackage corrPackage)
   {
      projectName = corrPackage.getName();

      // Set mapping for correspondence package to ecore file
      corrPackageResource = corrPackage.eResource();
   }

   public void setRules(final StaticAnalysis rulesModel)
   {
      this.rules = rulesModel;
   }

   public void setConfigurator(final Configurator configurator)
   {
      this.configurator = configurator;
   }

   public Configurator getConfigurator()
   {
      return configurator;
   }

   public void setDelta(final Delta delta)
   {
      this.delta = delta;
   }

   public void setSynchronizationProtocol(final SynchronizationProtocol protocol)
   {
      this.protocol = protocol;
   }

   public void setChangeSrc(final Consumer<EObject> changeSrc)
   {
      this.changeSrc = changeSrc;
   }

   public void setChangeTrg(final Consumer<EObject> changeTrg)
   {
      this.changeTrg = changeTrg;
   }

   public void setVerbose(final boolean state)
   {
      verbose = state;
   }

   // Getters

   public EObject getSrc()
   {
      return src;
   }

   public EObject getTrg()
   {
      return trg;
   }

   public CorrespondenceModel getCorr()
   {
      return corr;
   }

   public ResourceSet getResourceSet()
   {
      return set;
   }

   public Resource getResourceFor(final EObject obj, final URI uri)
   {
      if (obj.eResource() == null)
      {
         Resource resource = set.createResource(uri);
         resource.getContents().add(obj);
      }

      return obj.eResource();
   }

   public SynchronizationProtocol getSynchronizationProtocol()
   {
      return protocol;
   }

   public Consumer<EObject> getChangeSrc()
   {
      return changeSrc;
   }

   public Consumer<EObject> getChangeTrg()
   {
      return changeTrg;
   }

   public Delta getDelta()
   {
      return delta;
   }

   // Defaults
   public SynchronizationHelper(final EPackage corrPackage, final String pathToProject)
   {
      this(corrPackage, pathToProject, eMoflonEMFUtil.createDefaultResourceSet());
   }

   public SynchronizationHelper(final EPackage corrPackage, final String pathToProject, final ResourceSet resourceSet)
   {

      this.set = resourceSet;
      setCorrPackage(corrPackage);
      loadRulesFromProject(pathToProject);
      
      configurator = new Configurator() {
      };
      changeSrc = (root -> {
      });
      changeTrg = (root -> {
      });
   }

   // Integration
   protected void establishTranslationProtocol()
   {
      if (protocol == null && !batchOptimization)
         throw new IllegalStateException("You cannot synchronize your delta without providing a translation protocol");
      if (protocol == null)
         protocol = new SynchronizationProtocol();
   }

   protected void establishGraphTriple()
   {
      if (corr == null)
      {
         corr = TGGRuntimeFactory.eINSTANCE.createCorrespondenceModel();
         corr.setSource(src);
         corr.setTarget(trg);
         set.createResource(eMoflonEMFUtil.createFileURI(projectName + "/corr.xmi", false)).getContents().add(corr);
      }
      
      tempOutputContainer = AlgorithmFactory.eINSTANCE.createTempOutputContainer();
      set.createResource(eMoflonEMFUtil.createFileURI(projectName + "/tempOutputContainer.xmi", false)).getContents().add(tempOutputContainer);
   }

   protected void removeDeltaListeners(final EObject root)
   {
      removeListenerFromNode(root);
      root.eAllContents().forEachRemaining(this::removeListenerFromNode);
   }

   protected void removeListenerFromNode(final EObject element)
   {
      List<Adapter> toBeRemoved = new ArrayList<>();
      element.eAdapters().forEach(adapter -> {
         if (adapter instanceof OnlineChangeDetector)
            toBeRemoved.add(adapter);
      });

      if (toBeRemoved != null)
         element.eAdapters().removeAll(toBeRemoved);
   }

   protected void establishForwardDelta()
   {
      establishDelta(src, changeSrc);
   }

   protected void establishBackwardDelta()
   {
      establishDelta(trg, changeTrg);
   }

   protected void establishDelta(final EObject input, final Consumer<EObject> change)
   {
      if (input == null)
         throw new IllegalStateException("Why are you trying to backward synchronize an empty input graph?");

      new OnlineChangeDetector(delta, input);
      change.accept(input);
      removeDeltaListeners(input);

      if (noChangesWereMade() && protocol == null)
      {
         induceCreateDeltaForBatchTrafo(input);
         batchOptimization = true;
      } else
      {
         batchOptimization = false;
      }
   }

   protected boolean noChangesWereMade()
   {
      return delta.getAllAddedElements().size() == 0 && delta.getAllDeletedElements().size() == 0;
   }

   protected void prepare()
   {
      if (corrPackageResource == null)
         throw new IllegalArgumentException("Correspondence package must be set.");

      if (rules == null)
         throw new IllegalArgumentException("Rules must be set.");

      delta = new Delta();
   }

   protected void induceCreateDeltaForBatchTrafo(final EObject inputRoot)
   {
      this.delta = new Delta();

      inputRoot.eAllContents().forEachRemaining(node -> addNodeAndOutgoingEdges(node));
      addNodeAndOutgoingEdges(inputRoot);
   }

   protected StaticAnalysis determineLookupMethods()
   {
      StaticAnalysis sma = EcoreUtil.copy(rules);

      if (batchOptimization)
      {
         sma.getSourceRules().getRules().forEach(this::reduceToOneEntry);
         sma.getTargetRules().getRules().forEach(this::reduceToOneEntry);
      }

      return sma;
   }

   protected void reduceToOneEntry(final Rule rule)
   {
      if (rule.getIsAppropriateMethods().size() > 1)
      {
         EOperation isAppr = rule.getIsAppropriateMethods().get(0);
         rule.getIsAppropriateMethods().clear();
         rule.getIsAppropriateMethods().add(isAppr);
      }
   }

   @SuppressWarnings("unchecked")
   protected void addNodeAndOutgoingEdges(final EObject node)
   {
      delta.addNode(node);

      // Retrieve the edge to the container if there is one
      if (node.eContainmentFeature() != null && node.eContainmentFeature().getEOpposite() != null)
      {
         EMoflonEdge edge = TGGRuntimeFactory.eINSTANCE.createEMoflonEdge();
         edge.setName(node.eContainmentFeature().getEOpposite().getName());
         edge.setSrc(node);
         edge.setTrg(node.eContainer());
         delta.addEdge(edge);
      }

      // Retrieve all EReference instances
      Set<EStructuralFeature> references = eMoflonEMFUtil.getAllReferences(node);

      // Iterate through all references
      for (EStructuralFeature reference : references)
      {
         // Check if the reference to be handled is a containment edge (i.e.,
         // node contains s.th.)
         if (reference.getUpperBound() != 1)
            // Edge is n-ary: edge exists only once, but points to many
            // contained EObjects
            for (EObject containedObject : (EList<EObject>) node.eGet(reference, true))
            {
               // Create the wrapper and set the appropriate values
               EMoflonEdge edge = TGGRuntimeFactory.eINSTANCE.createEMoflonEdge();
               edge.setName(reference.getName());
               edge.setSrc(node);
               edge.setTrg(containedObject);

               // Save edge as unprocessed
               delta.addEdge(edge);
            }
         // else a standard reference was found
         else
         {
            // Create the wrapper and set the appropriate values
            EMoflonEdge edge = TGGRuntimeFactory.eINSTANCE.createEMoflonEdge();
            edge.setName(reference.getName());
            edge.setSrc(node);
            edge.setTrg((EObject) node.eGet(reference, true));

            // Save edge as unprocessed
            delta.addEdge(edge);
         }
      }
   }

   public void integrateForward()
   {
      if (src == null)
         throw new IllegalArgumentException("Source model must be set");

      init();
      establishForwardDelta();
      establishTranslationProtocol();

      performSynchronization(new @DebugBreakpoint(phase = Phase.INIT) ForwardSynchronizer(corr, delta, protocol, configurator, determineLookupMethods(),
            tempOutputContainer));

      if (trg == null)
         trg = corr.getTarget();
   }

   protected void performSynchronization(final Synchronizer synchronizer)
   {
      try
      {
         synchronizer.synchronize();
      } catch (LocalCompletenessException e)
      {
         logger.error(e.getMessage(projectName, verbose));
      } finally
      {
         synchronizer.handleElementsNotTranslatedWarning(verbose);
      }
   }

   protected void init()
   {
      prepare();

      establishGraphTriple();
   }

   public void integrateBackward()
   {
      if (trg == null)
         throw new IllegalArgumentException("Target model must be set");

      init();
      establishBackwardDelta();
      establishTranslationProtocol();

      performSynchronization(new BackwardSynchronizer(corr, delta, protocol, configurator, determineLookupMethods(), tempOutputContainer));

      if (src == null)
         src = corr.getSource();
   }

   /* Persistency */

   public void loadTrg(final String path)
   {
      setTrg(eMoflonEMFUtil.loadModelWithDependencies(path, set));
   }


   public void loadSrc(final String path)
   {
      setSrc(eMoflonEMFUtil.loadModelWithDependencies(path, set));
   }

   public void loadCorr(final String path)
   {
      setCorr(eMoflonEMFUtil.loadModelWithDependencies(path, set));
   }

   /**
    * Loads the rules for the given project, located at the given path.
    * 
    * The rules are loaded from the following location: '[pathToProject]/model/[rulesFileBaseName].sma.xmi' .
    * 
    * @param pathToProject
    *           path that contains the project
    * @param rulesFileBaseName
    *           the basename of the file containing the rules
    */
   private void loadRulesFromProject(final String pathToProject, final String rulesFileBaseName)
   {
      setRules((StaticAnalysis) eMoflonEMFUtil.loadModelWithDependencies(pathToProject + "/model/" + rulesFileBaseName + ".sma.xmi", set));
   }

   /**
    * Loads TGG rules from inside a Jar archive.
    * 
    * @param pathToJarArchive
    *           the absolute or relative path to the Jar file (e.g., "./libraries/Rules.jar")
    * @param pathToResourceInJar
    *           the absolute path of the rules package inside the Jar file (e.g., "/model/MyIntegration.sma.xmi")
    */
   private void loadRulesFromJarArchive(final String pathToJarArchive, final String pathToResourceInJar)
   {
      setRules((StaticAnalysis) eMoflonEMFUtil.loadAndInitModelFromJarFileWithDependencies(TGGLanguagePackage.eINSTANCE, pathToJarArchive, pathToResourceInJar,
            set));
   }


   /**
    * Loads the rules for the given project, located at the given path.
    * 
    * The rules are loaded from the following location: '[pathToProject]/model/[CorrespondencePackageName].sma.xmi' .
    * 
    * @param pathToProject
    *           path that contains the project
    */
   private void loadRulesFromProject(final String pathToProject)
   {
      loadRulesFromProject(pathToProject, projectName);
   }

   public void loadSynchronizationProtocol(final String path)
   {
      TGGRuntime.PrecedenceStructure ps = (TGGRuntime.PrecedenceStructure) eMoflonEMFUtil.loadModelWithDependencies(path, set);
      protocol = new SynchronizationProtocol();
      protocol.load(ps);
   }

   public void saveSrc(final String path)
   {
      //TODO eleblebici: Please check whether this is a valid fix for issue #456
      src.eResource().setURI(eMoflonEMFUtil.createFileURI(path, false));
      eMoflonEMFUtil.saveModel(src, path);
   }

   public void saveTrg(final String path)
   {
      //TODO eleblebici: Please check whether this is a valid fix for issue #456
      trg.eResource().setURI(eMoflonEMFUtil.createFileURI(path, false));
      eMoflonEMFUtil.saveModel(trg, path);
   }

   public void saveCorr(final String path)
   {
      //TODO eleblebici: Please check whether this is a valid fix for issue #456
      corr.eResource().setURI(eMoflonEMFUtil.createFileURI(path, false));
      eMoflonEMFUtil.saveModel(corr, path);
   }

   public void saveSynchronizationProtocol(final String path)
   {
      TGGRuntime.PrecedenceStructure ps = protocol.save();
      set.createResource(eMoflonEMFUtil.createFileURI(path, false)).getContents().add(ps);
      eMoflonEMFUtil.saveModel(ps, path);
   }

   public void filterMatches(final boolean value)
   {
      MatchImpl.filterMatches = value;
   }
}
