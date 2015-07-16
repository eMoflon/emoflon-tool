package org.moflon.ide.debug;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EcoreFactoryImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.XMLSave;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLSaveImpl;
import org.eclipse.emf.ecore.xmi.util.XMLProcessor;
import org.moflon.tgg.algorithm.datastructures.Graph;
import org.moflon.tgg.algorithm.synchronization.SynchronizationHelper;
import org.moflon.tgg.algorithm.synchronization.Synchronizer;

import DebugLanguage.AdditionPhase;
import DebugLanguage.AttributeProxy;
import DebugLanguage.ChangeMode;
import DebugLanguage.DebugAttributeDelta;
import DebugLanguage.DebugCorrespondence;
import DebugLanguage.DebugCorrespondenceModel;
import DebugLanguage.DebugDelta;
import DebugLanguage.DebugEObjectProxy;
import DebugLanguage.DebugLanguageFactory;
import DebugLanguage.DebugLanguagePackage;
import DebugLanguage.DebugMatch;
import DebugLanguage.DebugModel;
import DebugLanguage.DebugPrecedenceGraph;
import DebugLanguage.DebugRules;
import DebugLanguage.DebugSynchronizationProtocol;
import DebugLanguage.DebugTripleMatch;
import DebugLanguage.DeletionPhase;
import DebugLanguage.InitializationPhase;
import DebugLanguage.TranslationPhase;
import TGGLanguage.algorithm.TempOutputContainer;
import TGGLanguage.analysis.AnalysisFactory;
import TGGLanguage.analysis.Rule;
import TGGLanguage.analysis.StaticAnalysis;
import TGGRuntime.AbstractCorrespondence;
import TGGRuntime.AttributeDelta;
import TGGRuntime.CorrespondenceModel;
import TGGRuntime.Delta;
import TGGRuntime.EMoflonEdge;
import TGGRuntime.Match;
import TGGRuntime.PrecedenceStructure;
import TGGRuntime.TripleMatch;

/**
 * This helper class provides debugging capabilities to the {@link SynchronizationHelper}.
 * 
 * Each debugging phase has its own helper method to fill a {@link DebugModel}.
 * <ol>
 * <li>{@link #toInitPhase()}</li>
 * <li>{@link #toDeletionPhase()}</li>
 * </ol>
 *
 * @author Marco Ballhausen
 * @version 2015
 */
public class DebugSynchronizationHelper extends SynchronizationHelper
{
   public DebugSynchronizationHelper(EPackage corrPackage, final String pathToProject)
   {
      super(corrPackage, pathToProject);
   }

   /**
    * This class wrap's the default behavior of EMF to XML serialization. There is currently an issue in EMF regarding
    * the serialization of {@link EcorePackage#EJAVA_OBJECT}s. The serialization is mocked by returning the actual
    * object's class name instead of it's serialization.
    * 
    * Fix for https://bugs.eclipse.org/bugs/show_bug.cgi?id=245014.
    *
    * @author Marco-Vaio
    * @author (last editor) $Author$
    * @version $Revision$ $Date$
    */
   private static final class XMLResourceImplExtension extends XMLResourceImpl
   {
      @Override
      protected XMLSave createXMLSave()
      {
         return new XMLSaveImpl(createXMLHelper()) {
            @Override
            protected String getDatatypeValue(Object value, EStructuralFeature f, boolean isAttribute)
            {
               if (value == null)
               {
                  return null;
               }
               EDataType d = (EDataType) f.getEType();
               EFactory fac = new EcoreFactoryImpl() {

                  @Override
                  public String convertToString(EDataType eDataType, Object instanceValue)
                  {
                     switch (eDataType.getClassifierID())
                     {
                     case DebugLanguagePackage.CHANGE_MODE:
                        return convertEnumToString(eDataType, instanceValue);
                     default:
                        return super.convertToString(eDataType, instanceValue);
                     }
                  }

                  private String convertEnumToString(EDataType eDataType, Object instanceValue)
                  {
                     return ((Enumerator) instanceValue).getLiteral();
                  }

                  @Override
                  protected String convertToString(Object instanceValue)
                  {
                     try
                     {
                        return super.convertToString(instanceValue);
                     } catch (RuntimeException e)
                     {
                        return instanceValue == null ? "null" : instanceValue.getClass().getName();
                     }
                  }
               };

               String svalue = helper.convertToString(fac, d, value);
               if (escape != null)
               {
                  if (isAttribute)
                  {
                     svalue = escape.convert(svalue);
                  } else
                  {
                     svalue = escape.convertText(svalue);
                  }
               }
               return svalue;
            }
         };
      }
   }

   private Synchronizer synchronizer;

   /**
    * A map that associates a {@link DebugEObjectProxy} to each {@link EObject} used in the synchronization process. The
    * actual {@link EObject} is referenced using {@link Object#hashCode()}.
    */
   public static HashMap<Integer, EObject> visited = new HashMap<Integer, EObject>();

   @Override
   protected void performSynchronization(Synchronizer synchronizer)
   {
      this.synchronizer = synchronizer;
      super.performSynchronization(synchronizer);
   }

   /**
    * Converts an arbitrary {@link EObject} to its XMI string representation using {@link XMLProcessor}.
    */
   public static String convertToXml(EObject eObject) throws IOException
   {
      HashMap<String, String> options = new HashMap<String, String>();
      options.put(XMLResource.OPTION_PROCESS_DANGLING_HREF, Boolean.FALSE.toString());
      XMLProcessor processor = new XMLProcessor();
      if (eObject.eResource() == null)
      {
         XMLResourceImpl resource = new XMLResourceImplExtension();
         resource.setEncoding("UTF-8");
         resource.getContents().add(eObject);
         return processor.saveToString(resource, options);
      } else
      {
         return processor.saveToString(eObject.eResource(), options);
      }
   }

   public static String convertToXSD(EPackage eObject) throws IOException
   {
      ResourceSet resourceSet = new ResourceSetImpl();
      XMLResourceImpl resource = (XMLResourceImpl) resourceSet.createResource(URI.createURI("test"));
      XMLProcessor processor = new XMLProcessor();
      resource.setEncoding("UTF-8");
      resource.getContents().add(eObject);
      Map<String, Boolean> options = new HashMap<String, Boolean>();
      options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
      return processor.saveToString(resource, options);
   }

   private static DebugEObjectProxy traverseToDebugProxy(EObject eobj, DebugEObjectProxy parent)
   {
      if (!visited.containsKey(eobj.hashCode()))
      {
         DebugEObjectProxy proxy = DebugLanguageFactory.eINSTANCE.createDebugEObjectProxy();
         if (parent != null)
         {
            // proxy.setParent(parent);
            DebugEObjectProxy oldParent = proxy.getParent();
            proxy.setParent(parent);
            oldParent.getReferenceChildren().add(proxy);
         }
         visited.put(eobj.hashCode(), proxy);

         // set "package.class" name
         if (eobj.eClass() != null && eobj.eClass().eContainer() instanceof EPackage)
         {
            EPackage p = (EPackage) eobj.eClass().eContainer();
            proxy.setPackageName(p.getName());
            proxy.setClassName(eobj.eClass().getName());
            proxy.setName(proxy.getClassName().toLowerCase());
         }

         for (EStructuralFeature feature : eobj.eClass().getEAllStructuralFeatures())
         {
            // Alernative iterate over eobj.eClass.getEAllAttributes()
            if (feature instanceof EAttribute)
            {
               // Note: an EAttribute is not unique for eobj, thus we copy a general AtrributeProxy and set the current
               // value
               AttributeProxy aproxy = EcoreUtil.copy(traverseToProxy(feature, AttributeProxy.class));
               proxy.getAttributeProxy().add(aproxy);
               Object value = eobj.eGet(feature);
               if (value == null)
               {
                  value = feature.getDefaultValue();
               }
               aproxy.setValue(value);
            } else if (feature instanceof EReference)
            {
               EReference ref = (EReference) feature;
               Object value = eobj.eGet(ref);
               DebugEObjectProxy refProxy = DebugLanguageFactory.eINSTANCE.createDebugEObjectProxy();
               refProxy.setPackageName(((EPackage) ref.eClass().eContainer()).getName());
               refProxy.setClassName(ref.eClass().getName());
               refProxy.setName(ref.getName());
               refProxy.setParent(proxy);

               // Pass element count to debugger
               AttributeProxy upperBound = DebugLanguageFactory.eINSTANCE.createAttributeProxy();
               upperBound.setName("upperBound");
               upperBound.setValue(ref.getUpperBound());
               refProxy.getAttributeProxy().add(upperBound);

               if (value instanceof EList)
               {
                  @SuppressWarnings("unchecked")
                  EList<EObject> list = (EList<EObject>) value;
                  for (EObject item : list)
                  {
                     DebugEObjectProxy itemPoxy = traverseToProxy(item, DebugEObjectProxy.class);
                     if (!EcoreUtil.isAncestor(itemPoxy, refProxy))
                     {
                        if (itemPoxy.getParent() == null)
                        {
                           itemPoxy.setParent(refProxy);
                        } else
                        {
                           DebugEObjectProxy oldParent = itemPoxy.getParent();
                           itemPoxy.setParent(refProxy);
                           oldParent.getReferenceChildren().add(itemPoxy);
                        }
                     } else
                     {
                        refProxy.getReferenceChildren().add(itemPoxy);
                     }
                  }
               } else if (value instanceof EObject)
               {
                  DebugEObjectProxy p = traverseToProxy(value, DebugEObjectProxy.class);
                  refProxy.getReferenceChildren().add(p);
               }
            }
         }
         return proxy;
      }
      return (DebugEObjectProxy) visited.get(eobj.hashCode());
   }

   /**
    * Default {@link #traverseToProxy(Object, Class)} implementation as in most cases an object will be converted to an
    * EObject.
    * 
    * @param obj
    * @return
    */
   public static EObject traverseToProxy(Object obj)
   {
      return traverseToProxy(obj, EObject.class);
   }

   /**
    * Convert or traverse a object / object tree to it's corresponding debug type. The parameter <i>returnType</i>
    * ensures that the resulting proxy is of the specified type and checks if the expected type is computed by the
    * traverse algorithm.
    * 
    * @param <T>
    * 
    * @param eobj
    * @return <T>
    */
   public static <T> T traverseToProxy(Object obj, Class<T> returnType)
   {
      if (obj == null)
      {
         return null;
      }
      if (!visited.containsKey(obj.hashCode()))
      {
         EObject proxy = null;
         if (obj instanceof EMoflonEdge)
         {
            EMoflonEdge edge = (EMoflonEdge) obj;
            // EMoflonEdge proxyEdge = TGGRuntimeFactory.eINSTANCE.createEMoflonEdge();
            // proxyEdge.setName(edge.getName());
            // proxyEdge.setSrc(traverseToProxy(edge.getSrc(), EObject.class));
            // proxyEdge.setTrg(traverseToProxy(edge.getTrg(), EObject.class));
            // proxy = proxyEdge;
            DebugEObjectProxy dep = traverseToDebugProxy((EObject) obj);
            dep.setName(edge.getName());
            proxy = dep;
         } else if (obj instanceof Match)
         {
            Match match = (Match) obj;
            DebugMatch newMatch = DebugLanguageFactory.eINSTANCE.createDebugMatch();
            newMatch.setRuleName(match.getRuleName());

            match.getContextNodes().forEach(e -> newMatch.getContext().add(traverseToProxy(e, DebugEObjectProxy.class)));
            match.getContextEdges().forEach(e -> newMatch.getContext().add(traverseToProxy(e, DebugEObjectProxy.class)));
            match.getToBeTranslatedNodes().forEach(e -> newMatch.getToBeTranslated().add(traverseToProxy(e, DebugEObjectProxy.class)));
            match.getToBeTranslatedEdges().forEach(e -> newMatch.getToBeTranslated().add(traverseToProxy(e, DebugEObjectProxy.class)));

            proxy = newMatch;
         } else if (obj instanceof TripleMatch)
         {
            TripleMatch match = (TripleMatch) obj;
            DebugTripleMatch newMatch = DebugLanguageFactory.eINSTANCE.createDebugTripleMatch();
            newMatch.setNumber(match.getNumber());
            newMatch.setRuleName(match.getRuleName());
            match.getContainedEdges().forEach(e -> newMatch.getContainedEdges().add(traverseToProxy(e, EObject.class)));
            match.getSourceElements().forEach(e -> newMatch.getSourceElements().add(traverseToProxy(e, EObject.class)));
            match.getTargetElements().forEach(e -> newMatch.getTargetElements().add(traverseToProxy(e, EObject.class)));
            match.getContextElements().forEach(e -> newMatch.getContextElements().add(traverseToProxy(e, EObject.class)));
            match.getCreatedElements().forEach(e -> newMatch.getCreatedElements().add(traverseToProxy(e, EObject.class)));
            match.getCorrespondenceElements().forEach(e -> newMatch.getCorrespondenceElements().add(traverseToProxy(e, EObject.class)));
            proxy = newMatch;
         } else if (obj instanceof AbstractCorrespondence)
         {
            DebugEObjectProxy tempProxy = traverseToDebugProxy((AbstractCorrespondence) obj);
            DebugCorrespondence dcorrespondence = DebugLanguageFactory.eINSTANCE.createDebugCorrespondence();

            dcorrespondence.setClassName(tempProxy.getClassName());
            dcorrespondence.setName(tempProxy.getName());
            dcorrespondence.setPackageName(tempProxy.getPackageName());

            DebugEObjectProxy srcRefProxy = tempProxy.getChildren().get(0);
            DebugEObjectProxy trgRefProxy = tempProxy.getChildren().get(1);

            EList<DebugEObjectProxy> srcRefs = srcRefProxy.getReferenceChildren();
            EList<DebugEObjectProxy> trgRefs = trgRefProxy.getReferenceChildren();

            if (srcRefs.size() == 1)
            {
               dcorrespondence.setSource(srcRefs.get(0));
            }
            if (trgRefs.size() == 1)
            {
               dcorrespondence.setTarget(trgRefs.get(0));
            }
            proxy = dcorrespondence;
         } else if (obj instanceof EAttribute)
         {
            EAttribute eattr = (EAttribute) obj;
            AttributeProxy aproxy = DebugLanguageFactory.eINSTANCE.createAttributeProxy();
            aproxy.setName(eattr.getName());
            proxy = aproxy;
         } else if (obj instanceof AttributeDelta)
         {
            AttributeDelta attributeDelta = (AttributeDelta) obj;
            DebugAttributeDelta newAttributeDelta = DebugLanguageFactory.eINSTANCE.createDebugAttributeDelta();
            newAttributeDelta.setOldValue(attributeDelta.getOldValue());
            newAttributeDelta.setNewValue(attributeDelta.getNewValue());

            // The node containing the changed attribute has already a DebugEObjectProxy. Therefore we need to search
            // the proxy's attributes for the Attribute proxy of the affectedAttribute one.
            EAttribute eAttribute = attributeDelta.getAffectedAttribute();
            DebugEObjectProxy nodeProxy = traverseToProxy(attributeDelta.getAffectedNode(), DebugEObjectProxy.class);
            Optional<AttributeProxy> optional = nodeProxy.getAttributeProxy().stream()
                  .filter(ap -> !(ap.getName() == null || ap.getName().equals("")) && ap.getName().equals(eAttribute.getName())).findFirst();
            if (optional.isPresent())
            {
               newAttributeDelta.setAffectedAttribute(optional.get());
            } else
            {
               throw new RuntimeException("The attribute for the given delta could not be found. Please contact the eMoflon Team.");
            }
            proxy = newAttributeDelta;
         } else if (obj instanceof TempOutputContainer)
         {
            TempOutputContainer toc = (TempOutputContainer) obj;
            if (toc.getPotentialRoots().size() == 1)
            {
               proxy = traverseToProxy(toc.getPotentialRoots().get(0));
            } else if (toc.getPotentialRoots().size() > 0)
            {
               proxy = traverseToProxy(toc);
            } else if (toc.getPotentialRoots().size() == 0)
            {
               return null;
            }
         } else if (obj instanceof EObject)
         {
            proxy = traverseToDebugProxy((EObject) obj);
         }
         visited.put(obj.hashCode(), proxy);
         if (returnType.isInstance(proxy))
         {
            return returnType.cast(proxy);
         } else
         {
            throw new ClassCastException("The call expected traversal to return a '" + returnType.getName() + "' but instead a '"
                  + (proxy == null ? null : proxy.getClass().getName()) + "' was computed. "
                  + "This normally means that the traverseToProxy Method needs to be extended by adding a new case. "
                  + "Consider inspecting the first call in the stack trace as this function is recursivly called.");
         }
      } else
      {
         Object o = visited.get(obj.hashCode());
         if (returnType.isInstance(o))
         {
            return returnType.cast(o);
         } else
         {
            throw new ClassCastException("The visited proxies hash map already contains an instance but the expected type '" + returnType.getName()
                  + "' differs from the actual type '" + (o == null ? null : o.getClass().getName()) + "'.");
         }
      }

   }

   private static DebugEObjectProxy traverseToDebugProxy(EObject eobj)
   {
      return traverseToDebugProxy(eobj, null);
   }

   public static DebugModel toInitPhase(ResourceSet rs, CorrespondenceModel corr, Delta delta, StaticAnalysis rules, PrecedenceStructure protocol,
         Object configurator)
   {
      DebugEObjectProxy srcProxy = traverseToProxy(corr.getSource(), DebugEObjectProxy.class);
      DebugEObjectProxy trgProxy = traverseToProxy(corr.getTarget(), DebugEObjectProxy.class);

      DebugModel dm = DebugLanguageFactory.eINSTANCE.createDebugModel();
      InitializationPhase ip = DebugLanguageFactory.eINSTANCE.createInitializationPhase();
      dm.getPhases().add(ip);

      ip.setSourceProxy(srcProxy);
      ip.setTargetProxy(trgProxy);

      // Correspondence model
      DebugCorrespondenceModel dcm = DebugLanguageFactory.eINSTANCE.createDebugCorrespondenceModel();
      dcm.setSource(srcProxy);
      dcm.setTarget(trgProxy);
      corr.getCorrespondences().forEach(c -> dcm.getCorrespondences().add(traverseToProxy(c)));
      ip.setCorrespondenceModel(dcm);

      // Synchronization Protocol
      DebugSynchronizationProtocol syncProtocol = DebugLanguageFactory.eINSTANCE.createDebugSynchronizationProtocol();
      for (TripleMatch m : protocol.getTripleMatches())
      {
         DebugTripleMatch newMatch = (DebugTripleMatch) traverseToProxy(m);
         syncProtocol.getMatches().add(newMatch);
      }
      ip.setSynchronizationProtocol(syncProtocol);

      // Delta
      DebugDelta debugdelta = DebugLanguageFactory.eINSTANCE.createDebugDelta();

      delta.getAddedNodes().forEach(an -> debugdelta.getAddedNodes().add(traverseToProxy(an, DebugEObjectProxy.class)));
      delta.getAddedEdges().forEach(ae -> debugdelta.getAddedEdges().add(traverseToProxy(ae, DebugEObjectProxy.class)));
      delta.getDeletedNodes().forEach(dn -> debugdelta.getDeletedNodes().add(traverseToProxy(dn, DebugEObjectProxy.class)));
      delta.getDeletedEdges().forEach(de -> debugdelta.getDeletedEdges().add(traverseToProxy(de, DebugEObjectProxy.class)));

      delta.getAttributeChanges().forEach(ac -> debugdelta.getAttributeChanges().add(traverseToProxy(ac, DebugAttributeDelta.class)));
      ip.setDelta(debugdelta);

      // Rules
      DebugRules debugRules = DebugLanguageFactory.eINSTANCE.createDebugRules();
      for (Rule r : rules.getSourceRules().getRules())
      {
         Rule newRule = AnalysisFactory.eINSTANCE.createRule();
         newRule.setRuleName(r.getRuleName());
         debugRules.getSourceRules().add(newRule);
      }
      for (Rule r : rules.getTargetRules().getRules())
      {
         Rule newRule = AnalysisFactory.eINSTANCE.createRule();
         newRule.setRuleName(r.getRuleName());
         debugRules.getTargetRules().add(newRule);
      }
      ip.setRules(debugRules);

      return dm;
   }

   public static DebugModel toDeletionPhase(CorrespondenceModel corr, Graph deletedElements,
         Collection<org.moflon.tgg.algorithm.datastructures.TripleMatch> allToBeRevokedTripleMatches, Graph revokedElements, PrecedenceStructure protocol)
   {
      visited.clear();
      DebugEObjectProxy srcProxy = traverseToProxy(corr.getSource(), DebugEObjectProxy.class);
      DebugEObjectProxy trgProxy = traverseToProxy(corr.getTarget(), DebugEObjectProxy.class);
      DebugModel dm = DebugLanguageFactory.eINSTANCE.createDebugModel();
      DeletionPhase dp = DebugLanguageFactory.eINSTANCE.createDeletionPhase();
      dm.getPhases().add(dp);

      dp.setSourceProxy(srcProxy);
      dp.setTargetProxy(trgProxy);

      // Correspondence model
      DebugCorrespondenceModel dcm = DebugLanguageFactory.eINSTANCE.createDebugCorrespondenceModel();
      dcm.setSource(srcProxy);
      dcm.setTarget(trgProxy);
      corr.getCorrespondences().forEach(c -> dcm.getCorrespondences().add(traverseToProxy(c)));
      dp.setCorrespondenceModel(dcm);

      // Synchronization Protocol
      DebugSynchronizationProtocol syncProtocol = DebugLanguageFactory.eINSTANCE.createDebugSynchronizationProtocol();
      for (TripleMatch m : protocol.getTripleMatches())
      {
         DebugTripleMatch newMatch = (DebugTripleMatch) traverseToProxy(m);
         syncProtocol.getMatches().add(newMatch);
      }
      dp.setRevokedProtocol(syncProtocol);

      // Add Deleted Triple Matches as deleted matches to the syncProtocol
      // Convert internal match to EMF structure
      for (org.moflon.tgg.algorithm.datastructures.TripleMatch internalMatch : allToBeRevokedTripleMatches)
      {
         TripleMatch m = DebugSynchronizationProtocolHelper.convertInternalTripleMatchToEMFTripleMatch(internalMatch);
         DebugTripleMatch revokedMatch = (DebugTripleMatch) traverseToProxy(m);
         revokedMatch.setChangeMode(ChangeMode.DELETED);
         syncProtocol.getMatches().add(revokedMatch);
      }

      // Deleted Elements
      deletedElements.getElements().forEach(e -> dp.getDeletedElements().add(traverseToProxy(e, DebugEObjectProxy.class)));

      // Revoked Elements
      if (revokedElements != null)
         revokedElements.getElements().forEach(e -> dp.getRevokedElements().add(traverseToProxy(e, DebugEObjectProxy.class)));
      return dm;
   }

   public static DebugModel toAdditionPhase(CorrespondenceModel corr, Graph addedElements,
         Collection<org.moflon.tgg.algorithm.datastructures.TripleMatch> allToBeRevokedTripleMatches, Graph revokedElements, PrecedenceStructure protocol)
   {
      visited.clear();
      DebugEObjectProxy srcProxy = traverseToProxy(corr.getSource(), DebugEObjectProxy.class);
      DebugEObjectProxy trgProxy = traverseToProxy(corr.getTarget(), DebugEObjectProxy.class);
      DebugModel dm = DebugLanguageFactory.eINSTANCE.createDebugModel();
      AdditionPhase ap = DebugLanguageFactory.eINSTANCE.createAdditionPhase();
      dm.getPhases().add(ap);

      ap.setSourceProxy(srcProxy);
      ap.setTargetProxy(trgProxy);

      // Correspondence model
      DebugCorrespondenceModel dcm = DebugLanguageFactory.eINSTANCE.createDebugCorrespondenceModel();
      dcm.setSource(srcProxy);
      dcm.setTarget(trgProxy);
      corr.getCorrespondences().forEach(c -> dcm.getCorrespondences().add(traverseToProxy(c)));
      ap.setCorrespondenceModel(dcm);

      // Synchronization Protocol
      DebugSynchronizationProtocol syncProtocol = DebugLanguageFactory.eINSTANCE.createDebugSynchronizationProtocol();
      for (TripleMatch m : protocol.getTripleMatches())
      {
         DebugTripleMatch newMatch = (DebugTripleMatch) traverseToProxy(m);
         syncProtocol.getMatches().add(newMatch);
      }
      ap.setSynchronizationProtocol(syncProtocol);

      // Deleted Triple Matches
      // DebugSynchronizationProtocol deletedTripleMatches =
      // DebugLanguageFactory.eINSTANCE.createDebugSynchronizationProtocol();
      // Convert internal match to EMF structure
      for (org.moflon.tgg.algorithm.datastructures.TripleMatch internalMatch : allToBeRevokedTripleMatches)
      {
         TripleMatch m = DebugSynchronizationProtocolHelper.convertInternalTripleMatchToEMFTripleMatch(internalMatch);
         DebugTripleMatch revokedByAddtionMatch = (DebugTripleMatch) traverseToProxy(m);
         revokedByAddtionMatch.setChangeMode(ChangeMode.DELETED);
         syncProtocol.getMatches().add(revokedByAddtionMatch);
         // deletedTripleMatches.getMatches().add(revokedMatch);
      }
      // ap.setDeletedMatches(deletedTripleMatches);

      // Added Elements
      addedElements.getElements().forEach(e -> ap.getAddedElements().add(traverseToProxy(e, DebugEObjectProxy.class)));

      // Deleted Elements
      // deletedElements.getElements().forEach(e -> ap.getDeletedElements().add(traverseToProxy(e,
      // DebugEObjectProxy.class)));

      // Revoked Elements
      revokedElements.getElements().forEach(e -> ap.getRevokedElements().add(traverseToProxy(e, DebugEObjectProxy.class)));
      return dm;
   }

   public static DebugModel toTranslationPhase(CorrespondenceModel corr, TempOutputContainer tempOutputContainer, Graph toBeTranslated,
         Collection<EObject> translated, Collection<org.moflon.tgg.algorithm.datastructures.TripleMatch> allToBeRevokedTripleMatches, Graph revokedElements,
         PrecedenceStructure protocol, Collection<TripleMatch> newlyCreatedTripleMatches, Collection<Match> precedenceGraph)
   {
      visited.clear();
      DebugEObjectProxy srcProxy;
      if (corr.getSource() == null)
      { // In a translation step the source model is empty (backward)
         srcProxy = traverseToProxy(tempOutputContainer, DebugEObjectProxy.class);
      } else
      {
         srcProxy = traverseToProxy(corr.getSource(), DebugEObjectProxy.class);
      }
      DebugEObjectProxy trgProxy;
      if (corr.getTarget() == null)
      { // In a translation step the target model is empty (forward)
         trgProxy = traverseToProxy(tempOutputContainer, DebugEObjectProxy.class);
      } else
      {
         trgProxy = traverseToProxy(corr.getTarget(), DebugEObjectProxy.class);
      }
      DebugModel dm = DebugLanguageFactory.eINSTANCE.createDebugModel();
      TranslationPhase tp = DebugLanguageFactory.eINSTANCE.createTranslationPhase();
      dm.getPhases().add(tp);

      tp.setSourceProxy(srcProxy);
      tp.setTargetProxy(trgProxy);

      // Correspondence model
      DebugCorrespondenceModel dcm = DebugLanguageFactory.eINSTANCE.createDebugCorrespondenceModel();
      dcm.setSource(srcProxy);
      dcm.setTarget(trgProxy);
      corr.getCorrespondences().forEach(c -> dcm.getCorrespondences().add(traverseToProxy(c)));
      tp.setCorrespondenceModel(dcm);

      // Synchronization Protocol
      DebugSynchronizationProtocol syncProtocol = DebugLanguageFactory.eINSTANCE.createDebugSynchronizationProtocol();
      for (TripleMatch m : protocol.getTripleMatches())
      {
         DebugTripleMatch newMatch = (DebugTripleMatch) traverseToProxy(m);
         if (newlyCreatedTripleMatches.stream().anyMatch(nctp -> nctp.getNumber() == m.getNumber()))
         {
            newMatch.setChangeMode(ChangeMode.ADDED);
         }
         syncProtocol.getMatches().add(newMatch);
      }
      tp.setSynchronizationProtocol(syncProtocol);

      // toBeTranslated
      toBeTranslated.getElements().forEach(e -> tp.getToBeTranslated().add(traverseToProxy(e, DebugEObjectProxy.class)));

      // translated
      if (translated != null)
      {
         // translated.forEach(e -> tp.getTranslated().add(traverseToProxy(e, DebugEObjectProxy.class)));
         translated.forEach(e -> {
            DebugEObjectProxy proxy = traverseToProxy(e, DebugEObjectProxy.class);
            proxy.setChangeMode(ChangeMode.TRANSLATED);
            tp.getToBeTranslated().add(proxy);
         });
      }
      // precedenceGraph
      DebugPrecedenceGraph dpg = DebugLanguageFactory.eINSTANCE.createDebugPrecedenceGraph();
      precedenceGraph.forEach(e -> dpg.getMatches().add(traverseToProxy(e, DebugMatch.class)));
      tp.setPrecedenceGraph(dpg);

      return dm;
   }

   public String toInitPhase() throws IOException
   {
      try
      {
         return convertToXml(toInitPhase(getResourceSet(), getCorr(), getDelta().toEMF(), determineLookupMethods(), getSynchronizationProtocol().save(),
               getConfigurator()));
      } catch (IOException e)
      {
         // Print exception as this method is called by JDI and an exception will be hidden otherwise
         e.printStackTrace();
         throw e;
      } catch (Exception e)
      {
         e.printStackTrace();
         throw e;
      }
   }

   public String toDeletionPhase() throws IOException
   {
      try
      {
         return convertToXml(toDeletionPhase(synchronizer.getGraphTriple(), synchronizer.getToBeDeleted(), synchronizer.getAllToBeRevokedTripleMatches(),
               synchronizer.getAllRevokedElts(), synchronizer.getProtocol().save()));
      } catch (IOException e)
      {
         // Print exception as this method is called by JDI and an exception will be hidden otherwise
         e.printStackTrace();
         throw e;
      } catch (Exception e)
      {
         e.printStackTrace();
         throw e;
      }
   }

   public String toAdditionPhase() throws IOException
   {
      try
      {
         return convertToXml(toAdditionPhase(synchronizer.getGraphTriple(), synchronizer.getAddedElts(), synchronizer.getAllToBeRevokedTripleMatches(),
               synchronizer.getAllRevokedElts(), synchronizer.getProtocol().save()));
      } catch (IOException e)
      {
         // Print exception as this method is called by JDI and an exception will be hidden otherwise
         e.printStackTrace();
         throw e;
      } catch (Exception e)
      {
         e.printStackTrace();
         throw e;
      }
   }

   public String toTranslationPhase() throws IOException
   {
      try
      {
         return convertToXml(toTranslationPhase(synchronizer.getGraphTriple(), synchronizer.getTempOutputContainer(), synchronizer.getToBeTranslated(),
               synchronizer.getTranslated(), synchronizer.getAllToBeRevokedTripleMatches(), synchronizer.getAllRevokedElts(),
               synchronizer.getProtocol().save(),
               DebugSynchronizationProtocolHelper.convertInternalTripleMatchesToEMFTripleMatches(synchronizer.getCreatedTripleMatch()),
               synchronizer.getInputMatches()));
      } catch (IOException e)
      {
         // Print exception as this method is called by JDI and an exception will be hidden otherwise
         e.printStackTrace();
         throw e;
      } catch (Exception e)
      {
         e.printStackTrace();
         throw e;
      }
   }
}
