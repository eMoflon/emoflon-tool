/*
 * generated by Fujaba - CodeGen2
 */
package de.uni_kassel.fujaba.codegen.rules.engine;
import de.uni_kassel.features.ReferenceHandler; // requires Fujaba5/libs/features.jar in classpath
import de.uni_kassel.features.annotation.util.Property; // requires Fujaba5/libs/features.jar in classpath
import de.uni_kassel.fujaba.codegen.engine.OperationSorter;
import de.uni_kassel.fujaba.codegen.engine.TokenMutatorTemplateEngine;
import de.uni_kassel.fujaba.codegen.rules.ExecuteStoryPatternOperation;
import de.upb.tools.sdm.JavaSDM; // requires Fujaba5/libs/RuntimeTools.jar in classpath
import de.upb.tools.sdm.JavaSDMException;


public class OperationSorterMutator extends PlanMutator
{


   public void mutate (ExecuteStoryPatternOperation plan )
   {
      boolean fujaba__Success = false;
      OperationSorter sorter = null;
      TokenMutatorTemplateEngine engine = null;
      ExecutionPlanEngine executionPlan = null;

      // story pattern storypatternwiththis
      try 
      {
         fujaba__Success = false; 

         // search to-one link sorter from this to sorter
         sorter = this.getSorter ();

         // check object sorter is really bound
         JavaSDM.ensure ( sorter != null );


         // search to-one link mutators from this to executionPlan
         executionPlan = this.getEngine ();

         // check object executionPlan is really bound
         JavaSDM.ensure ( executionPlan != null );

         // search to-one link treeMutators from executionPlan to engine
         engine = executionPlan.getEngine ();

         // check object engine is really bound
         JavaSDM.ensure ( engine != null );



         // collabStat call
         engine.mutateTree (sorter, plan);
         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      return ;
   }

   /**
    * <pre>
    *           0..1     sorter     0..1
    * OperationSorterMutator ------------------------> OperationSorter
    *           operationSorterMutator               sorter
    * </pre>
    */
   public static final String PROPERTY_SORTER = "sorter";

   @Property( name = PROPERTY_SORTER, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         adornment = ReferenceHandler.Adornment.NONE)
   private OperationSorter sorter;

   @Property( name = PROPERTY_SORTER )
   public boolean setSorter (OperationSorter value)
   {
      boolean changed = false;

      if (this.sorter != value)
      {
      
         OperationSorter oldValue = this.sorter;
         this.sorter = value;
         changed = true;
      
      }
      return changed;
   }

   @Property( name = PROPERTY_SORTER )
   public OperationSorterMutator withSorter (OperationSorter value)
   {
      setSorter (value);
      return this;
   }

   public OperationSorter getSorter ()
   {
      return this.sorter;
   }

   public void removeYou()
   {
      this.setSorter (null);
      super.removeYou ();
   }
}

