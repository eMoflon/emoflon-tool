/**
 */
package org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.impl;

import java.lang.Iterable;

import java.lang.reflect.InvocationTargetException;

import java.util.LinkedList;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.moflon.maave.tool.symbolicgraphs.Datastructures.DatastructuresFactory;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.MorphismPair;

import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;

import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;

import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.CategoryUtil;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.CategoryUtilsFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.CategoryUtilsPackage;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.CommutingPaths;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.Path;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.Status;
import org.moflon.maave.tool.symbolicgraphs.secondorder.util.FormulaUtil;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.GenericMorphismFinder;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.InjectiveEGraphMorphismFinder;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.InjectiveSymbolicGraphMorphismFinder;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MorphismsSet;
// <-- [user defined imports]
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.ConfigurableMorphismClass;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.ConfigurableMorphismClassFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.maave.tool.smt.solverutil.IAttribSolver;
import org.moflon.maave.tool.smt.solverutil.Z3AttribSolver;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.EGraphElement;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphEdge;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelEdge;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphEdge;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelEdge;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphEdge;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelEdge;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraphsFactory;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.Mapping;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphismsFactory;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.impl.SymbolicGraphMorphismImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Category Util</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.impl.CategoryUtilImpl#isVerbose <em>Verbose</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CategoryUtilImpl extends EObjectImpl implements CategoryUtil
{
   /**
    * The default value of the '{@link #isVerbose() <em>Verbose</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #isVerbose()
    * @generated
    * @ordered
    */
   protected static final boolean VERBOSE_EDEFAULT = false;

   /**
    * The cached value of the '{@link #isVerbose() <em>Verbose</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #isVerbose()
    * @generated
    * @ordered
    */
   protected boolean verbose = VERBOSE_EDEFAULT;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected CategoryUtilImpl()
   {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   protected EClass eStaticClass()
   {
      return CategoryUtilsPackage.Literals.CATEGORY_UTIL;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public boolean isVerbose()
   {
      return verbose;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void setVerbose(boolean newVerbose)
   {
      boolean oldVerbose = verbose;
      verbose = newVerbose;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, CategoryUtilsPackage.CATEGORY_UTIL__VERBOSE, oldVerbose, verbose));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public boolean isValidEGraphMorphism(SymbolicGraphMorphism morphism)
   {
      // [user code injected with eMoflon]
      boolean debug = "true".equals(System.getenv("MoflonAaVDebugMode")) ? true : false;
      for (GraphEdge ge : morphism.getDom().getGraphEdges())
      {
         if (!(ge.getType() == morphism.imageOf(ge).getType()))
         {
            if (debug)
            {
               reportSimilarURIs(ge.getType(), morphism.imageOf(ge).getType());
            }
            if (isVerbose())
            {
               System.out.println(" type(" + ge.getDebugId() + ")!=type(f_GE(" + morphism.imageOf(ge).getDebugId() + "))");
            }
            return false;
         }
         if (!(morphism.imageOf(ge.getSource()) == morphism.imageOf(ge).getSource()))
         {
            if (isVerbose())
            {
               System.out.println(" f_GN s_GE (" + ge.getDebugId() + ")=" + morphism.imageOf(ge.getSource()).getDebugId() + " not equal to s_GE f_GE ("
                     + ge.getDebugId() + ")=" + morphism.imageOf(ge).getSource().getDebugId());
            }
            return false;
         }
         if (!(morphism.imageOf(ge.getTarget()) == morphism.imageOf(ge).getTarget()))
         {
            if (isVerbose())
            {
               System.out.println(" f_GN t_GE (" + ge.getDebugId() + ")=" + morphism.imageOf(ge.getTarget()).getDebugId() + " not equal to t_GE f_GE ("
                     + ge.getDebugId() + ")=" + morphism.imageOf(ge).getTarget().getDebugId());
            }
            return false;
         }

      }
      for (LabelEdge le : morphism.getDom().getLabelEdges())
      {
         if (!(le.getType() == morphism.imageOf(le).getType()))
         {

            if (debug)
            {
               reportSimilarURIs(le.getType(), morphism.imageOf(le).getType());
            }
            if (isVerbose())
            {
               System.out.println(" type(" + le.getDebugId() + ")!=type(f_LE(" + morphism.imageOf(le).getDebugId() + "))");
            }
            return false;
         }
         if (!(morphism.imageOf(le.getSource()) == morphism.imageOf(le).getSource()))
         {
            if (isVerbose())
            {
               System.out.println(" f_GN s_LE (" + le.getDebugId() + ")=" + morphism.imageOf(le.getSource()).getDebugId() + " not equal to s_LE f_LE ("
                     + le.getDebugId() + ")=" + morphism.imageOf(le).getSource().getDebugId());
            }
            return false;
         }
         if (!(morphism.imageOf(le.getTarget()) == morphism.imageOf(le).getTarget()))
         {
            if (isVerbose())
            {
               System.out.println(" f_GN t_LE (" + le.getDebugId() + ")=" + morphism.imageOf(le.getTarget()).getLabel() + " not equal to s_LE f_LE ("
                     + le.getDebugId() + ")=" + morphism.imageOf(le).getTarget().getLabel());
            }
            return false;
         }
      }
      for (GraphNode gn : morphism.getDom().getGraphNodes())
      {
         if (!(gn.getType() == morphism.imageOf(gn).getType()))
         {
            if (debug)
            {
               reportSimilarURIs(gn.getType(), morphism.imageOf(gn).getType());
            }
            if (isVerbose())
            {
               System.out.println(" type(" + gn.getDebugId() + ")!=type(f_GN(" + morphism.imageOf(gn).getDebugId() + "))");
            }
            return false;
         }

      }

      for (LabelNode ln : morphism.getDom().getLabelNodes())
      {
         if (!(ln.getType() == morphism.imageOf(ln).getType()))
         {
            if (debug)
            {
               reportSimilarURIs(ln.getType(), morphism.imageOf(ln).getType());
            }
            if (isVerbose())
            {
               System.out.println(" type(" + ln.getLabel() + ")!=type(f_LN(" + morphism.imageOf(ln).getLabel() + "))");
            }
            return false;
         }

      }
      return true;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public boolean isInjectiveMorphism(SymbolicGraphMorphism morphism)
   {
      // [user code injected with eMoflon]
      return isInjectiveForGraphNodes(morphism) && isInjectiveForGraphEdges(morphism) && isInjectiveForLabelEdges(morphism);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public boolean areSimilarEGraphMorphisms(SymbolicGraphMorphism morA, SymbolicGraphMorphism morB)
   {
      // [user code injected with eMoflon]
      if (morA.getDom() != morB.getDom())
      {
         return false;
      }

      if (morA.getCodom() != morB.getCodom())
      {
         return false;
      }
      for (GraphNode elem : morA.getDom().getGraphNodes())
      {
         if (morA.imageOf(elem) != morB.imageOf(elem))
         {
            return false;
         }
      }
      for (GraphEdge elem : morA.getDom().getGraphEdges())
      {
         if (morA.imageOf(elem) != morB.imageOf(elem))
         {
            return false;
         }
      }
      for (LabelEdge elem : morA.getDom().getLabelEdges())
      {
         if (morA.imageOf(elem) != morB.imageOf(elem))
         {
            return false;
         }
      }
      for (LabelNode elem : morA.getDom().getLabelNodes())
      {
         if (morA.imageOf(elem) != morB.imageOf(elem))
         {
            return false;
         }
      }
      return true;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public boolean isValidSymbGraphMorphism(SymbolicGraphMorphism morphism)
   {
      // [user code injected with eMoflon]
      IAttribSolver solver = new Z3AttribSolver();

      boolean result = solver.checkImplication(morphism);
      return result;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public boolean isOfClassM(SymbolicGraphMorphism morphism)
   {
      // [user code injected with eMoflon]
      IAttribSolver solver = new Z3AttribSolver();

      return isInjectiveForGraphNodes(morphism) && isInjectiveForLabelNodes(morphism) && isInjectiveForGraphEdges(morphism)
            && isBijectiveForLabelNodes(morphism) && solver.hasEquivalentFormulas(morphism);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public boolean areCommuting(Path pathA, Path pathB)
   {
      // [user code injected with eMoflon]
      if (pathA.getBeginn() == pathB.getBeginn() && pathA.getEnd() == pathB.getEnd())
      {
         SymbolicGraph begin = pathA.getBeginn();

         SymbolicGraphMorphism morA = pathA.getOver().stream().filter(m -> m.getDom() == begin).findAny().get();
         while (morA.getCodom() != pathA.getEnd())
         {
            SymbolicGraph codomainA = morA.getCodom();
            SymbolicGraphMorphism morA2 = pathA.getOver().stream().filter(m -> m.getDom() == codomainA).findAny().get();
            morA = morA.composeWith(morA2);
         }

         SymbolicGraphMorphism morB = pathB.getOver().stream().filter(m -> m.getDom() == begin).findAny().get();
         while (morB.getCodom() != pathB.getEnd())
         {
            SymbolicGraph codomainB = morB.getCodom();
            SymbolicGraphMorphism morB2 = pathB.getOver().stream().filter(m -> m.getDom() == codomainB).findAny().get();
            morB = morB.composeWith(morB2);
         }
         return this.areSimilarEGraphMorphisms(morA, morB);
      }

      // TODO: implement this method here but do not remove the injection marker 
      throw new UnsupportedOperationException();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public SymbolicGraphMorphism getInverseMorphism(SymbolicGraphMorphism mor)
   {
      // [user code injected with eMoflon]
      SymbolicGraphMorphismImpl reverseMor = (SymbolicGraphMorphismImpl) SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      reverseMor.setCodom(mor.getDom());
      reverseMor.setDom(mor.getCodom());

      Mapping<GraphNode> reverseGNMapping = ((SymbolicGraphMorphismImpl) mor).graphNodeMap.getInverseMapping();
      if (reverseGNMapping == null)
         return null;
      Mapping<GraphEdge> reverseGEMapping = ((SymbolicGraphMorphismImpl) mor).graphEdgeMap.getInverseMapping();
      if (reverseGEMapping == null)
         return null;
      Mapping<LabelNode> reverseLNMapping = ((SymbolicGraphMorphismImpl) mor).labelNodeMap.getInverseMapping();
      if (reverseLNMapping == null)
         return null;
      Mapping<LabelEdge> reverseLEMapping = ((SymbolicGraphMorphismImpl) mor).labelEdgeMap.getInverseMapping();
      if (reverseLEMapping == null)
         return null;

      reverseMor.graphNodeMap = reverseGNMapping;
      reverseMor.graphEdgeMap = reverseGEMapping;
      reverseMor.labelNodeMap = reverseLNMapping;
      reverseMor.labelEdgeMap = reverseLEMapping;

      return reverseMor;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public boolean areIsomorphicSymbGraphs(SymbolicGraph graphB, SymbolicGraph graphA)
   {
      // ActivityNode1
      Object[] result1_green = CategoryUtilImpl.pattern_CategoryUtil_7_1_ActivityNode1_greenFF();
      if (result1_green == null)
      {
         throw new RuntimeException("Pattern matching in node [ActivityNode1] failed.");
      }
      InjectiveEGraphMorphismFinder matchFinder = (InjectiveEGraphMorphismFinder) result1_green[0];
      CategoryUtil util = (CategoryUtil) result1_green[1];

      // ActivityNode2
      Object[] result2_bindingAndBlack = CategoryUtilImpl.pattern_CategoryUtil_7_2_ActivityNode2_bindingAndBlackFBBB(matchFinder, graphA, graphB);
      if (result2_bindingAndBlack == null)
      {
         throw new RuntimeException("Pattern matching in node [ActivityNode2] failed." + " Variables: " + "[matchFinder] = " + matchFinder + ", "
               + "[graphA] = " + graphA + ", " + "[graphB] = " + graphB + ".");
      }
      MorphismsSet morsAB = (MorphismsSet) result2_bindingAndBlack[0];
      // ActivityNode7
      Object[] result3_black = CategoryUtilImpl.pattern_CategoryUtil_7_3_ActivityNode7_blackBF(morsAB);
      while (result3_black != null)
      {
         SymbolicGraphMorphism morAB = (SymbolicGraphMorphism) result3_black[1];

         // ActivityNode4
         if (CategoryUtilImpl.pattern_CategoryUtil_7_4_ActivityNode4_expressionFBB(util, morAB))
         {

            // ActivityNode8
            Object[] result5_bindingAndBlack = CategoryUtilImpl.pattern_CategoryUtil_7_5_ActivityNode8_bindingAndBlackFBBB(matchFinder, graphB, graphA);
            if (result5_bindingAndBlack == null)
            {
               throw new RuntimeException("Pattern matching in node [ActivityNode8] failed." + " Variables: " + "[matchFinder] = " + matchFinder + ", "
                     + "[graphB] = " + graphB + ", " + "[graphA] = " + graphA + ".");
            }
            MorphismsSet morsBA = (MorphismsSet) result5_bindingAndBlack[0];
            // ActivityNode6
            Object[] result6_black = CategoryUtilImpl.pattern_CategoryUtil_7_6_ActivityNode6_blackBF(morsBA);
            while (result6_black != null)
            {
               SymbolicGraphMorphism morBA = (SymbolicGraphMorphism) result6_black[1];

               // ActivityNode5
               if (CategoryUtilImpl.pattern_CategoryUtil_7_7_ActivityNode5_expressionFBB(util, morBA))
               {
                  return CategoryUtilImpl.pattern_CategoryUtil_7_8_expressionF();
               } else
               {

                  // ActivityNode6
                  Object[] result9_black = CategoryUtilImpl.pattern_CategoryUtil_7_9_ActivityNode6_blackBB(morsBA, morBA);
                  if (result9_black == null)
                  {
                     throw new RuntimeException(
                           "Pattern matching in node [ActivityNode6] failed." + " Variables: " + "[morsBA] = " + morsBA + ", " + "[morBA] = " + morBA + ".");
                  }
                  CategoryUtilImpl.pattern_CategoryUtil_7_9_ActivityNode6_redBB(morsBA, morBA);

               }

               result6_black = CategoryUtilImpl.pattern_CategoryUtil_7_6_ActivityNode6_blackBF(morsBA);
            }
            return CategoryUtilImpl.pattern_CategoryUtil_7_10_expressionF();
         } else
         {

            // ActivityNode9
            Object[] result11_black = CategoryUtilImpl.pattern_CategoryUtil_7_11_ActivityNode9_blackBB(morsAB, morAB);
            if (result11_black == null)
            {
               throw new RuntimeException(
                     "Pattern matching in node [ActivityNode9] failed." + " Variables: " + "[morsAB] = " + morsAB + ", " + "[morAB] = " + morAB + ".");
            }
            CategoryUtilImpl.pattern_CategoryUtil_7_11_ActivityNode9_redBB(morsAB, morAB);

         }

         result3_black = CategoryUtilImpl.pattern_CategoryUtil_7_3_ActivityNode7_blackBF(morsAB);
      }
      return CategoryUtilImpl.pattern_CategoryUtil_7_12_expressionF();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public Status hasNonEmptySemantic(SymbolicGraph symbolicGraph)
   {
      // [user code injected with eMoflon]
      IAttribSolver solver = new Z3AttribSolver();
      Status status = CategoryUtilsFactory.eINSTANCE.createStatus();
      status.setValid(solver.hasNonEmptySemantic(symbolicGraph));
      return status;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public MorphismPair internalGetGraphIsomorphism(SymbolicGraph graphA, SymbolicGraph graphB, GenericMorphismFinder matchFinder)
   {
      // init
      Object[] result1_black = CategoryUtilImpl.pattern_CategoryUtil_9_1_init_blackBB(matchFinder, this);
      if (result1_black == null)
      {
         throw new RuntimeException(
               "Pattern matching in node [init] failed." + " Variables: " + "[matchFinder] = " + matchFinder + ", " + "[this] = " + this + ".");
      }
      Object[] result1_green = CategoryUtilImpl.pattern_CategoryUtil_9_1_init_greenF();
      MorphismPair morphismPair = (MorphismPair) result1_green[0];

      // ActivityNode10
      Object[] result2_bindingAndBlack = CategoryUtilImpl.pattern_CategoryUtil_9_2_ActivityNode10_bindingAndBlackFFBBB(matchFinder, graphA, graphB);
      if (result2_bindingAndBlack == null)
      {
         throw new RuntimeException("Pattern matching in node [ActivityNode10] failed." + " Variables: " + "[matchFinder] = " + matchFinder + ", "
               + "[graphA] = " + graphA + ", " + "[graphB] = " + graphB + ".");
      }
      MorphismsSet morsAB = (MorphismsSet) result2_bindingAndBlack[0];
      MorphismsSet morsBA = (MorphismsSet) result2_bindingAndBlack[1];
      // ForEach whileThereIsAMorphism
      for (Object[] result3_black : CategoryUtilImpl.pattern_CategoryUtil_9_3_whileThereIsAMorphism_blackFBFB(morsAB, morsBA))
      {
         SymbolicGraphMorphism morAB = (SymbolicGraphMorphism) result3_black[0];
         SymbolicGraphMorphism morBA = (SymbolicGraphMorphism) result3_black[2];
         // ActivityNode14
         Object[] result4_black = CategoryUtilImpl.pattern_CategoryUtil_9_4_ActivityNode14_blackFB(morphismPair);
         if (result4_black != null)
         {
            // SymbolicGraphMorphism aMorphism = (SymbolicGraphMorphism) result4_black[0];
         } else
         {

            // createCommutingStuff
            Object[] result5_bindingAndBlack = CategoryUtilImpl.pattern_CategoryUtil_9_5_createCommutingStuff_bindingAndBlackBBBBFB(morAB, morBA, graphA,
                  graphB, this);
            if (result5_bindingAndBlack == null)
            {
               throw new RuntimeException("Pattern matching in node [createCommutingStuff] failed." + " Variables: " + "[morAB] = " + morAB + ", "
                     + "[morBA] = " + morBA + ", " + "[graphA] = " + graphA + ", " + "[graphB] = " + graphB + ", " + "[this] = " + this + ".");
            }
            SymbolicGraphMorphism idMor = (SymbolicGraphMorphism) result5_bindingAndBlack[4];
            Object[] result5_green = CategoryUtilImpl.pattern_CategoryUtil_9_5_createCommutingStuff_greenBBBFB(morAB, morBA, graphA, idMor);
            CommutingPaths commutingPaths = (CommutingPaths) result5_green[3];

            // ifCommutingCreateAndReturnPair
            Object[] result6_bindingAndBlack = CategoryUtilImpl.pattern_CategoryUtil_9_6_ifCommutingCreateAndReturnPair_bindingAndBlackFBBBB(morBA,
                  morphismPair, morAB, commutingPaths);
            if (result6_bindingAndBlack != null)
            {
               // Status commuting = (Status) result6_bindingAndBlack[0];
               CategoryUtilImpl.pattern_CategoryUtil_9_6_ifCommutingCreateAndReturnPair_greenBBB(morBA, morphismPair, morAB);

            } else
            {
            }

         }

      }
      // ActivityNode15
      Object[] result7_black = CategoryUtilImpl.pattern_CategoryUtil_9_7_ActivityNode15_blackBF(morphismPair);
      if (result7_black != null)
      {
         // SymbolicGraphMorphism aMorphism = (SymbolicGraphMorphism) result7_black[1];
         return CategoryUtilImpl.pattern_CategoryUtil_9_8_expressionFB(morphismPair);
      } else
      {
         return CategoryUtilImpl.pattern_CategoryUtil_9_9_expressionF();
      }

   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public SymbolicGraphMorphism createIdentityMorphism(SymbolicGraph graphG)
   {
      // [user code injected with eMoflon]
      SymbolicGraphMorphism id = SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      id.setDom(graphG);
      id.setCodom(graphG);
      graphG.getGraphNodes().stream().forEach(x -> id.addMapping(x, x));
      graphG.getLabelNodes().stream().forEach(x -> id.addMapping(x, x));
      graphG.getGraphEdges().stream().forEach(x -> id.addMapping(x, x));
      graphG.getLabelEdges().stream().forEach(x -> id.addMapping(x, x));
      return id;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public MorphismPair getSymbolicGraphIsomorphism(SymbolicGraph graphA, SymbolicGraph graphB)
   {
      // ActivityNode11
      Object[] result1_black = CategoryUtilImpl.pattern_CategoryUtil_11_1_ActivityNode11_blackB(this);
      if (result1_black == null)
      {
         throw new RuntimeException("Pattern matching in node [ActivityNode11] failed." + " Variables: " + "[this] = " + this + ".");
      }
      Object[] result1_green = CategoryUtilImpl.pattern_CategoryUtil_11_1_ActivityNode11_greenF();
      InjectiveSymbolicGraphMorphismFinder matchFinder = (InjectiveSymbolicGraphMorphismFinder) result1_green[0];

      return CategoryUtilImpl.pattern_CategoryUtil_11_2_expressionFBBBB(this, graphA, graphB, matchFinder);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public MorphismPair getEGraphIsomorphism(SymbolicGraph graphA, SymbolicGraph graphB)
   {
      // ActivityNode12
      Object[] result1_black = CategoryUtilImpl.pattern_CategoryUtil_12_1_ActivityNode12_blackB(this);
      if (result1_black == null)
      {
         throw new RuntimeException("Pattern matching in node [ActivityNode12] failed." + " Variables: " + "[this] = " + this + ".");
      }
      Object[] result1_green = CategoryUtilImpl.pattern_CategoryUtil_12_1_ActivityNode12_greenF();
      InjectiveEGraphMorphismFinder matchFinder = (InjectiveEGraphMorphismFinder) result1_green[0];

      return CategoryUtilImpl.pattern_CategoryUtil_12_2_expressionFBBBB(this, graphA, graphB, matchFinder);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public SymbolicGraphMorphism copyGraph(SymbolicGraph graphG)
   {
      // [user code injected with eMoflon]
      SymbolicGraph copyG = SymbolicGraphsFactory.eINSTANCE.createSymbolicGraph();
      copyG.setName("copyOf" + graphG.getName());
      SymbolicGraphMorphism isoMor = SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      isoMor.setCodom(graphG);
      isoMor.setDom(copyG);

      Mapping<GraphNode> reverseGraphNodeMapping = new Mapping<>(graphG.getGraphNodes().size() + 2, 1f);
      for (GraphNode n : graphG.getGraphNodes())
      {
         GraphNode nCopy = EcoreUtil.copy(n);
         copyG.add(nCopy);
         isoMor.addMapping(nCopy, n);
         reverseGraphNodeMapping.addMapping(n, nCopy);
      }

      Mapping<LabelNode> reverseLabelNodeMapping = new Mapping<>(graphG.getLabelNodes().size() + 2, 1f);
      for (LabelNode n : graphG.getLabelNodes())
      {
         LabelNode nCopy = EcoreUtil.copy(n);
         copyG.add(nCopy);
         isoMor.addMapping(nCopy, n);
         reverseLabelNodeMapping.addMapping(n, nCopy);
      }
      for (GraphEdge e : graphG.getGraphEdges())
      {
         GraphEdge eCopy = EcoreUtil.copy(e);
         copyG.add(eCopy);
         eCopy.setSource(reverseGraphNodeMapping.imageOf(e.getSource()));
         eCopy.setTarget(reverseGraphNodeMapping.imageOf(e.getTarget()));
         isoMor.addMapping(eCopy, e);

      }
      for (LabelEdge e : graphG.getLabelEdges())
      {
         LabelEdge eCopy = EcoreUtil.copy(e);
         copyG.add(eCopy);
         eCopy.setSource(reverseGraphNodeMapping.imageOf(e.getSource()));
         eCopy.setTarget(reverseLabelNodeMapping.imageOf(e.getTarget()));
         isoMor.addMapping(eCopy, e);
      }
      FormulaUtil.copyFormulaFromCodomToDom(isoMor);
      return isoMor;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public MorphismPair createIsomorpicCopy(SymbolicGraph graphA)
   {
      // [user code injected with eMoflon]
      
      return null;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public SymbolicGraphMorphism getInverseIfIsomorphism(SymbolicGraphMorphism isoMor)
   {
      // [user code injected with eMoflon]
      ConfigurableMorphismClassFactory morClassFac = CategoryUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
      ConfigurableMorphismClass morclass = morClassFac.createMorphismClass("B", "B", "B", "B", "<=>");
      if (morclass.isMember(isoMor).isValid() == false)
      {
         return null;
      }

      SymbolicGraphMorphismImpl reverseMor = (SymbolicGraphMorphismImpl) SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      reverseMor.setCodom(isoMor.getDom());
      reverseMor.setDom(isoMor.getCodom());

      Mapping<GraphNode> reverseGNMapping = ((SymbolicGraphMorphismImpl) isoMor).graphNodeMap.getInverseMapping();
      if (reverseGNMapping == null)
         return null;
      Mapping<GraphEdge> reverseGEMapping = ((SymbolicGraphMorphismImpl) isoMor).graphEdgeMap.getInverseMapping();
      if (reverseGEMapping == null)
         return null;
      Mapping<LabelNode> reverseLNMapping = ((SymbolicGraphMorphismImpl) isoMor).labelNodeMap.getInverseMapping();
      if (reverseLNMapping == null)
         return null;
      Mapping<LabelEdge> reverseLEMapping = ((SymbolicGraphMorphismImpl) isoMor).labelEdgeMap.getInverseMapping();
      if (reverseLEMapping == null)
         return null;

      reverseMor.graphNodeMap = reverseGNMapping;
      reverseMor.graphEdgeMap = reverseGEMapping;
      reverseMor.labelNodeMap = reverseLNMapping;
      reverseMor.labelEdgeMap = reverseLEMapping;

      return reverseMor;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Object eGet(int featureID, boolean resolve, boolean coreType)
   {
      switch (featureID)
      {
      case CategoryUtilsPackage.CATEGORY_UTIL__VERBOSE:
         return isVerbose();
      }
      return super.eGet(featureID, resolve, coreType);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void eSet(int featureID, Object newValue)
   {
      switch (featureID)
      {
      case CategoryUtilsPackage.CATEGORY_UTIL__VERBOSE:
         setVerbose((Boolean) newValue);
         return;
      }
      super.eSet(featureID, newValue);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void eUnset(int featureID)
   {
      switch (featureID)
      {
      case CategoryUtilsPackage.CATEGORY_UTIL__VERBOSE:
         setVerbose(VERBOSE_EDEFAULT);
         return;
      }
      super.eUnset(featureID);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public boolean eIsSet(int featureID)
   {
      switch (featureID)
      {
      case CategoryUtilsPackage.CATEGORY_UTIL__VERBOSE:
         return verbose != VERBOSE_EDEFAULT;
      }
      return super.eIsSet(featureID);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException
   {
      switch (operationID)
      {
      case CategoryUtilsPackage.CATEGORY_UTIL___IS_VALID_EGRAPH_MORPHISM__SYMBOLICGRAPHMORPHISM:
         return isValidEGraphMorphism((SymbolicGraphMorphism) arguments.get(0));
      case CategoryUtilsPackage.CATEGORY_UTIL___IS_INJECTIVE_MORPHISM__SYMBOLICGRAPHMORPHISM:
         return isInjectiveMorphism((SymbolicGraphMorphism) arguments.get(0));
      case CategoryUtilsPackage.CATEGORY_UTIL___ARE_SIMILAR_EGRAPH_MORPHISMS__SYMBOLICGRAPHMORPHISM_SYMBOLICGRAPHMORPHISM:
         return areSimilarEGraphMorphisms((SymbolicGraphMorphism) arguments.get(0), (SymbolicGraphMorphism) arguments.get(1));
      case CategoryUtilsPackage.CATEGORY_UTIL___IS_VALID_SYMB_GRAPH_MORPHISM__SYMBOLICGRAPHMORPHISM:
         return isValidSymbGraphMorphism((SymbolicGraphMorphism) arguments.get(0));
      case CategoryUtilsPackage.CATEGORY_UTIL___IS_OF_CLASS_M__SYMBOLICGRAPHMORPHISM:
         return isOfClassM((SymbolicGraphMorphism) arguments.get(0));
      case CategoryUtilsPackage.CATEGORY_UTIL___ARE_COMMUTING__PATH_PATH:
         return areCommuting((Path) arguments.get(0), (Path) arguments.get(1));
      case CategoryUtilsPackage.CATEGORY_UTIL___GET_INVERSE_MORPHISM__SYMBOLICGRAPHMORPHISM:
         return getInverseMorphism((SymbolicGraphMorphism) arguments.get(0));
      case CategoryUtilsPackage.CATEGORY_UTIL___ARE_ISOMORPHIC_SYMB_GRAPHS__SYMBOLICGRAPH_SYMBOLICGRAPH:
         return areIsomorphicSymbGraphs((SymbolicGraph) arguments.get(0), (SymbolicGraph) arguments.get(1));
      case CategoryUtilsPackage.CATEGORY_UTIL___HAS_NON_EMPTY_SEMANTIC__SYMBOLICGRAPH:
         return hasNonEmptySemantic((SymbolicGraph) arguments.get(0));
      case CategoryUtilsPackage.CATEGORY_UTIL___INTERNAL_GET_GRAPH_ISOMORPHISM__SYMBOLICGRAPH_SYMBOLICGRAPH_GENERICMORPHISMFINDER:
         return internalGetGraphIsomorphism((SymbolicGraph) arguments.get(0), (SymbolicGraph) arguments.get(1), (GenericMorphismFinder) arguments.get(2));
      case CategoryUtilsPackage.CATEGORY_UTIL___CREATE_IDENTITY_MORPHISM__SYMBOLICGRAPH:
         return createIdentityMorphism((SymbolicGraph) arguments.get(0));
      case CategoryUtilsPackage.CATEGORY_UTIL___GET_SYMBOLIC_GRAPH_ISOMORPHISM__SYMBOLICGRAPH_SYMBOLICGRAPH:
         return getSymbolicGraphIsomorphism((SymbolicGraph) arguments.get(0), (SymbolicGraph) arguments.get(1));
      case CategoryUtilsPackage.CATEGORY_UTIL___GET_EGRAPH_ISOMORPHISM__SYMBOLICGRAPH_SYMBOLICGRAPH:
         return getEGraphIsomorphism((SymbolicGraph) arguments.get(0), (SymbolicGraph) arguments.get(1));
      case CategoryUtilsPackage.CATEGORY_UTIL___COPY_GRAPH__SYMBOLICGRAPH:
         return copyGraph((SymbolicGraph) arguments.get(0));
      case CategoryUtilsPackage.CATEGORY_UTIL___CREATE_ISOMORPIC_COPY__SYMBOLICGRAPH:
         return createIsomorpicCopy((SymbolicGraph) arguments.get(0));
      case CategoryUtilsPackage.CATEGORY_UTIL___GET_INVERSE_IF_ISOMORPHISM__SYMBOLICGRAPHMORPHISM:
         return getInverseIfIsomorphism((SymbolicGraphMorphism) arguments.get(0));
      }
      return super.eInvoke(operationID, arguments);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public String toString()
   {
      if (eIsProxy())
         return super.toString();

      StringBuffer result = new StringBuffer(super.toString());
      result.append(" (verbose: ");
      result.append(verbose);
      result.append(')');
      return result.toString();
   }

   public static final Object[] pattern_CategoryUtil_7_1_ActivityNode1_greenFF()
   {
      InjectiveEGraphMorphismFinder matchFinder = MatchingFactory.eINSTANCE.createInjectiveEGraphMorphismFinder();
      CategoryUtil util = CategoryUtilsFactory.eINSTANCE.createCategoryUtil();
      return new Object[] { matchFinder, util };
   }

   public static final Object[] pattern_CategoryUtil_7_2_ActivityNode2_bindingFBBB(InjectiveEGraphMorphismFinder matchFinder, SymbolicGraph graphA,
         SymbolicGraph graphB)
   {
      MorphismsSet _localVariable_0 = matchFinder.getAllMorphisms(graphA, graphB);
      MorphismsSet morsAB = _localVariable_0;
      if (morsAB != null)
      {
         return new Object[] { morsAB, matchFinder, graphA, graphB };
      }
      return null;
   }

   public static final Object[] pattern_CategoryUtil_7_2_ActivityNode2_blackB(MorphismsSet morsAB)
   {
      return new Object[] { morsAB };
   }

   public static final Object[] pattern_CategoryUtil_7_2_ActivityNode2_bindingAndBlackFBBB(InjectiveEGraphMorphismFinder matchFinder, SymbolicGraph graphA,
         SymbolicGraph graphB)
   {
      Object[] result_pattern_CategoryUtil_7_2_ActivityNode2_binding = pattern_CategoryUtil_7_2_ActivityNode2_bindingFBBB(matchFinder, graphA, graphB);
      if (result_pattern_CategoryUtil_7_2_ActivityNode2_binding != null)
      {
         MorphismsSet morsAB = (MorphismsSet) result_pattern_CategoryUtil_7_2_ActivityNode2_binding[0];

         Object[] result_pattern_CategoryUtil_7_2_ActivityNode2_black = pattern_CategoryUtil_7_2_ActivityNode2_blackB(morsAB);
         if (result_pattern_CategoryUtil_7_2_ActivityNode2_black != null)
         {

            return new Object[] { morsAB, matchFinder, graphA, graphB };
         }
      }
      return null;
   }

   public static final Object[] pattern_CategoryUtil_7_3_ActivityNode7_blackBF(MorphismsSet morsAB)
   {
      for (SymbolicGraphMorphism morAB : morsAB.getMorphisms())
      {
         return new Object[] { morsAB, morAB };
      }
      return null;
   }

   public static final boolean pattern_CategoryUtil_7_4_ActivityNode4_expressionFBB(CategoryUtil util, SymbolicGraphMorphism morAB)
   {
      boolean _localVariable_0 = util.isValidSymbGraphMorphism(morAB);
      boolean _result = Boolean.valueOf(_localVariable_0);
      return _result;
   }

   public static final Object[] pattern_CategoryUtil_7_5_ActivityNode8_bindingFBBB(InjectiveEGraphMorphismFinder matchFinder, SymbolicGraph graphB,
         SymbolicGraph graphA)
   {
      MorphismsSet _localVariable_1 = matchFinder.getAllMorphisms(graphB, graphA);
      MorphismsSet morsBA = _localVariable_1;
      if (morsBA != null)
      {
         return new Object[] { morsBA, matchFinder, graphB, graphA };
      }
      return null;
   }

   public static final Object[] pattern_CategoryUtil_7_5_ActivityNode8_blackB(MorphismsSet morsBA)
   {
      return new Object[] { morsBA };
   }

   public static final Object[] pattern_CategoryUtil_7_5_ActivityNode8_bindingAndBlackFBBB(InjectiveEGraphMorphismFinder matchFinder, SymbolicGraph graphB,
         SymbolicGraph graphA)
   {
      Object[] result_pattern_CategoryUtil_7_5_ActivityNode8_binding = pattern_CategoryUtil_7_5_ActivityNode8_bindingFBBB(matchFinder, graphB, graphA);
      if (result_pattern_CategoryUtil_7_5_ActivityNode8_binding != null)
      {
         MorphismsSet morsBA = (MorphismsSet) result_pattern_CategoryUtil_7_5_ActivityNode8_binding[0];

         Object[] result_pattern_CategoryUtil_7_5_ActivityNode8_black = pattern_CategoryUtil_7_5_ActivityNode8_blackB(morsBA);
         if (result_pattern_CategoryUtil_7_5_ActivityNode8_black != null)
         {

            return new Object[] { morsBA, matchFinder, graphB, graphA };
         }
      }
      return null;
   }

   public static final Object[] pattern_CategoryUtil_7_6_ActivityNode6_blackBF(MorphismsSet morsBA)
   {
      for (SymbolicGraphMorphism morBA : morsBA.getMorphisms())
      {
         return new Object[] { morsBA, morBA };
      }
      return null;
   }

   public static final boolean pattern_CategoryUtil_7_7_ActivityNode5_expressionFBB(CategoryUtil util, SymbolicGraphMorphism morBA)
   {
      boolean _localVariable_0 = util.isValidSymbGraphMorphism(morBA);
      boolean _result = Boolean.valueOf(_localVariable_0);
      return _result;
   }

   public static final boolean pattern_CategoryUtil_7_8_expressionF()
   {
      boolean _result = Boolean.valueOf(true);
      return _result;
   }

   public static final Object[] pattern_CategoryUtil_7_9_ActivityNode6_blackBB(MorphismsSet morsBA, SymbolicGraphMorphism morBA)
   {
      if (morsBA.getMorphisms().contains(morBA))
      {
         return new Object[] { morsBA, morBA };
      }
      return null;
   }

   public static final Object[] pattern_CategoryUtil_7_9_ActivityNode6_redBB(MorphismsSet morsBA, SymbolicGraphMorphism morBA)
   {
      morsBA.getMorphisms().remove(morBA);
      return new Object[] { morsBA, morBA };
   }

   public static final boolean pattern_CategoryUtil_7_10_expressionF()
   {
      boolean _result = false;
      return _result;
   }

   public static final Object[] pattern_CategoryUtil_7_11_ActivityNode9_blackBB(MorphismsSet morsAB, SymbolicGraphMorphism morAB)
   {
      if (morsAB.getMorphisms().contains(morAB))
      {
         return new Object[] { morsAB, morAB };
      }
      return null;
   }

   public static final Object[] pattern_CategoryUtil_7_11_ActivityNode9_redBB(MorphismsSet morsAB, SymbolicGraphMorphism morAB)
   {
      morsAB.getMorphisms().remove(morAB);
      return new Object[] { morsAB, morAB };
   }

   public static final boolean pattern_CategoryUtil_7_12_expressionF()
   {
      boolean _result = false;
      return _result;
   }

   public static final Object[] pattern_CategoryUtil_9_1_init_blackBB(GenericMorphismFinder matchFinder, CategoryUtil _this)
   {
      return new Object[] { matchFinder, _this };
   }

   public static final Object[] pattern_CategoryUtil_9_1_init_greenF()
   {
      MorphismPair morphismPair = DatastructuresFactory.eINSTANCE.createMorphismPair();
      return new Object[] { morphismPair };
   }

   public static final Object[] pattern_CategoryUtil_9_2_ActivityNode10_bindingFFBBB(GenericMorphismFinder matchFinder, SymbolicGraph graphA,
         SymbolicGraph graphB)
   {
      MorphismsSet _localVariable_0 = matchFinder.getAllMorphisms(graphA, graphB);
      MorphismsSet _localVariable_1 = matchFinder.getAllMorphisms(graphB, graphA);
      MorphismsSet morsAB = _localVariable_0;
      if (morsAB != null)
      {
         MorphismsSet morsBA = _localVariable_1;
         if (morsBA != null)
         {
            return new Object[] { morsAB, morsBA, matchFinder, graphA, graphB };
         }
      }
      return null;
   }

   public static final Object[] pattern_CategoryUtil_9_2_ActivityNode10_blackBB(MorphismsSet morsAB, MorphismsSet morsBA)
   {
      if (!morsAB.equals(morsBA))
      {
         return new Object[] { morsAB, morsBA };
      }
      return null;
   }

   public static final Object[] pattern_CategoryUtil_9_2_ActivityNode10_bindingAndBlackFFBBB(GenericMorphismFinder matchFinder, SymbolicGraph graphA,
         SymbolicGraph graphB)
   {
      Object[] result_pattern_CategoryUtil_9_2_ActivityNode10_binding = pattern_CategoryUtil_9_2_ActivityNode10_bindingFFBBB(matchFinder, graphA, graphB);
      if (result_pattern_CategoryUtil_9_2_ActivityNode10_binding != null)
      {
         MorphismsSet morsAB = (MorphismsSet) result_pattern_CategoryUtil_9_2_ActivityNode10_binding[0];
         MorphismsSet morsBA = (MorphismsSet) result_pattern_CategoryUtil_9_2_ActivityNode10_binding[1];

         Object[] result_pattern_CategoryUtil_9_2_ActivityNode10_black = pattern_CategoryUtil_9_2_ActivityNode10_blackBB(morsAB, morsBA);
         if (result_pattern_CategoryUtil_9_2_ActivityNode10_black != null)
         {

            return new Object[] { morsAB, morsBA, matchFinder, graphA, graphB };
         }
      }
      return null;
   }

   public static final Iterable<Object[]> pattern_CategoryUtil_9_3_whileThereIsAMorphism_blackFBFB(MorphismsSet morsAB, MorphismsSet morsBA)
   {
      LinkedList<Object[]> _result = new LinkedList<Object[]>();
      if (!morsAB.equals(morsBA))
      {
         for (SymbolicGraphMorphism morAB : morsAB.getMorphisms())
         {
            for (SymbolicGraphMorphism morBA : morsBA.getMorphisms())
            {
               if (!morAB.equals(morBA))
               {
                  _result.add(new Object[] { morAB, morsAB, morBA, morsBA });
               }
            }
         }
      }
      return _result;
   }

   public static final Object[] pattern_CategoryUtil_9_4_ActivityNode14_blackFB(MorphismPair morphismPair)
   {
      SymbolicGraphMorphism aMorphism = morphismPair.getFirst();
      if (aMorphism != null)
      {
         return new Object[] { aMorphism, morphismPair };
      }

      return null;
   }

   public static final Object[] pattern_CategoryUtil_9_5_createCommutingStuff_bindingFBB(CategoryUtil _this, SymbolicGraph graphA)
   {
      SymbolicGraphMorphism _localVariable_0 = _this.createIdentityMorphism(graphA);
      SymbolicGraphMorphism idMor = _localVariable_0;
      if (idMor != null)
      {
         return new Object[] { idMor, _this, graphA };
      }
      return null;
   }

   public static final Object[] pattern_CategoryUtil_9_5_createCommutingStuff_blackBBBBB(SymbolicGraphMorphism morAB, SymbolicGraphMorphism morBA,
         SymbolicGraph graphA, SymbolicGraph graphB, SymbolicGraphMorphism idMor)
   {
      if (!morAB.equals(morBA))
      {
         if (!graphA.equals(graphB))
         {
            if (!idMor.equals(morAB))
            {
               if (!idMor.equals(morBA))
               {
                  if (graphA.equals(morAB.getDom()))
                  {
                     if (graphB.equals(morAB.getCodom()))
                     {
                        if (graphB.equals(morBA.getDom()))
                        {
                           if (graphA.equals(morBA.getCodom()))
                           {
                              return new Object[] { morAB, morBA, graphA, graphB, idMor };
                           }
                        }
                     }
                  }
               }
            }
         }
      }
      return null;
   }

   public static final Object[] pattern_CategoryUtil_9_5_createCommutingStuff_bindingAndBlackBBBBFB(SymbolicGraphMorphism morAB, SymbolicGraphMorphism morBA,
         SymbolicGraph graphA, SymbolicGraph graphB, CategoryUtil _this)
   {
      Object[] result_pattern_CategoryUtil_9_5_createCommutingStuff_binding = pattern_CategoryUtil_9_5_createCommutingStuff_bindingFBB(_this, graphA);
      if (result_pattern_CategoryUtil_9_5_createCommutingStuff_binding != null)
      {
         SymbolicGraphMorphism idMor = (SymbolicGraphMorphism) result_pattern_CategoryUtil_9_5_createCommutingStuff_binding[0];

         Object[] result_pattern_CategoryUtil_9_5_createCommutingStuff_black = pattern_CategoryUtil_9_5_createCommutingStuff_blackBBBBB(morAB, morBA, graphA,
               graphB, idMor);
         if (result_pattern_CategoryUtil_9_5_createCommutingStuff_black != null)
         {

            return new Object[] { morAB, morBA, graphA, graphB, idMor, _this };
         }
      }
      return null;
   }

   public static final Object[] pattern_CategoryUtil_9_5_createCommutingStuff_greenBBBFB(SymbolicGraphMorphism morAB, SymbolicGraphMorphism morBA,
         SymbolicGraph graphA, SymbolicGraphMorphism idMor)
   {
      CommutingPaths commutingPaths = CategoryUtilsFactory.eINSTANCE.createCommutingPaths();
      commutingPaths.setBegin(graphA);
      commutingPaths.setEnd(graphA);
      commutingPaths.getPathA().add(morAB);
      commutingPaths.getPathA().add(morBA);
      commutingPaths.getPathB().add(idMor);
      return new Object[] { morAB, morBA, graphA, commutingPaths, idMor };
   }

   public static final Object[] pattern_CategoryUtil_9_6_ifCommutingCreateAndReturnPair_bindingFB(CommutingPaths commutingPaths)
   {
      Status _localVariable_0 = commutingPaths.areCommuting();
      Status commuting = _localVariable_0;
      if (commuting != null)
      {
         return new Object[] { commuting, commutingPaths };
      }
      return null;
   }

   public static final Object[] pattern_CategoryUtil_9_6_ifCommutingCreateAndReturnPair_blackBBBB(Status commuting, SymbolicGraphMorphism morBA,
         MorphismPair morphismPair, SymbolicGraphMorphism morAB)
   {
      if (!morAB.equals(morBA))
      {
         boolean commuting_valid = commuting.isValid();
         if (Boolean.valueOf(commuting_valid).equals(Boolean.valueOf(true)))
         {
            return new Object[] { commuting, morBA, morphismPair, morAB };
         }

      }
      return null;
   }

   public static final Object[] pattern_CategoryUtil_9_6_ifCommutingCreateAndReturnPair_bindingAndBlackFBBBB(SymbolicGraphMorphism morBA,
         MorphismPair morphismPair, SymbolicGraphMorphism morAB, CommutingPaths commutingPaths)
   {
      Object[] result_pattern_CategoryUtil_9_6_ifCommutingCreateAndReturnPair_binding = pattern_CategoryUtil_9_6_ifCommutingCreateAndReturnPair_bindingFB(
            commutingPaths);
      if (result_pattern_CategoryUtil_9_6_ifCommutingCreateAndReturnPair_binding != null)
      {
         Status commuting = (Status) result_pattern_CategoryUtil_9_6_ifCommutingCreateAndReturnPair_binding[0];

         Object[] result_pattern_CategoryUtil_9_6_ifCommutingCreateAndReturnPair_black = pattern_CategoryUtil_9_6_ifCommutingCreateAndReturnPair_blackBBBB(
               commuting, morBA, morphismPair, morAB);
         if (result_pattern_CategoryUtil_9_6_ifCommutingCreateAndReturnPair_black != null)
         {

            return new Object[] { commuting, morBA, morphismPair, morAB, commutingPaths };
         }
      }
      return null;
   }

   public static final Object[] pattern_CategoryUtil_9_6_ifCommutingCreateAndReturnPair_greenBBB(SymbolicGraphMorphism morBA, MorphismPair morphismPair,
         SymbolicGraphMorphism morAB)
   {
      morphismPair.setFirst(morAB);
      morphismPair.setSecond(morBA);
      return new Object[] { morBA, morphismPair, morAB };
   }

   public static final Object[] pattern_CategoryUtil_9_7_ActivityNode15_blackBF(MorphismPair morphismPair)
   {
      SymbolicGraphMorphism aMorphism = morphismPair.getFirst();
      if (aMorphism != null)
      {
         return new Object[] { morphismPair, aMorphism };
      }

      return null;
   }

   public static final MorphismPair pattern_CategoryUtil_9_8_expressionFB(MorphismPair morphismPair)
   {
      MorphismPair _result = morphismPair;
      return _result;
   }

   public static final MorphismPair pattern_CategoryUtil_9_9_expressionF()
   {
      MorphismPair _result = null;
      return _result;
   }

   public static final Object[] pattern_CategoryUtil_11_1_ActivityNode11_blackB(CategoryUtil _this)
   {
      return new Object[] { _this };
   }

   public static final Object[] pattern_CategoryUtil_11_1_ActivityNode11_greenF()
   {
      InjectiveSymbolicGraphMorphismFinder matchFinder = MatchingFactory.eINSTANCE.createInjectiveSymbolicGraphMorphismFinder();
      return new Object[] { matchFinder };
   }

   public static final MorphismPair pattern_CategoryUtil_11_2_expressionFBBBB(CategoryUtil _this, SymbolicGraph graphA, SymbolicGraph graphB,
         InjectiveSymbolicGraphMorphismFinder matchFinder)
   {
      MorphismPair _localVariable_0 = _this.internalGetGraphIsomorphism(graphA, graphB, matchFinder);
      MorphismPair _result = _localVariable_0;
      return _result;
   }

   public static final Object[] pattern_CategoryUtil_12_1_ActivityNode12_blackB(CategoryUtil _this)
   {
      return new Object[] { _this };
   }

   public static final Object[] pattern_CategoryUtil_12_1_ActivityNode12_greenF()
   {
      InjectiveEGraphMorphismFinder matchFinder = MatchingFactory.eINSTANCE.createInjectiveEGraphMorphismFinder();
      return new Object[] { matchFinder };
   }

   public static final MorphismPair pattern_CategoryUtil_12_2_expressionFBBBB(CategoryUtil _this, SymbolicGraph graphA, SymbolicGraph graphB,
         InjectiveEGraphMorphismFinder matchFinder)
   {
      MorphismPair _localVariable_0 = _this.internalGetGraphIsomorphism(graphA, graphB, matchFinder);
      MorphismPair _result = _localVariable_0;
      return _result;
   }

   // <-- [user code injected with eMoflon]
   private boolean isInjectiveForGraphNodes(SymbolicGraphMorphism morphism)
   {
      for (GraphNode gn : morphism.getDom().getGraphNodes())
      {
         if (morphism.getDom().getGraphNodes().stream().anyMatch(x -> x != gn && morphism.imageOf(x) == morphism.imageOf(gn)))
         {
            return false;
         }

      }
      return true;

   }

   private boolean isInjectiveForLabelNodes(SymbolicGraphMorphism morphism)
   {
      for (LabelNode ln : morphism.getDom().getLabelNodes())
      {
         if (morphism.getDom().getLabelNodes().stream().anyMatch(x -> x != ln && morphism.imageOf(x) == morphism.imageOf(ln)))
         {
            return false;
         }

      }
      return true;
   }

   private boolean isInjectiveForGraphEdges(SymbolicGraphMorphism morphism)
   {
      for (GraphEdge ge : morphism.getDom().getGraphEdges())
      {
         if (morphism.getDom().getGraphEdges().stream().anyMatch(x -> x != ge && morphism.imageOf(x) == morphism.imageOf(ge)))
         {
            return false;
         }

      }
      return true;
   }

   private boolean isInjectiveForLabelEdges(SymbolicGraphMorphism morphism)
   {
      for (LabelEdge le : morphism.getDom().getLabelEdges())
      {
         if (morphism.getDom().getLabelEdges().stream().anyMatch(x -> x != le && morphism.imageOf(x) == morphism.imageOf(le)))
         {
            return false;
         }
      }
      return true;
   }

   private boolean isBijectiveForLabelNodes(SymbolicGraphMorphism morphism)
   {
      if (morphism.getDom().getLabelNodes().size() != morphism.getCodom().getLabelNodes().size())
      {
         return false;
      }
      return isInjectiveForLabelNodes(morphism);
   }

   private void reportSimilarURIs(EObject typeA, EObject typeB)
   {
      String uriA = EcoreUtil.getURI(typeA).toString();
      String uriB = EcoreUtil.getURI(typeB).toString();
      if (uriA.equals(uriB))
      {
         throw new RuntimeException("Different EClasses with same URI: " + uriA + "\n" + uriB);
      }

   }
   // [user code injected with eMoflon] -->
} //CategoryUtilImpl
