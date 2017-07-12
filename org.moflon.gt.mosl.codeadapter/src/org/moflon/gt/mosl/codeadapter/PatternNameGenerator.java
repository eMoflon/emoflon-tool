package org.moflon.gt.mosl.codeadapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.sdm.runtime.democles.CFNode;

/**
 * Configurable name generator for patterns
 * 
 * @author Roland Kluge - Initial implementation
 */
public class PatternNameGenerator
{
   private EClass eContainingClass;

   private PatternDef patternDefinition;

   private int eOperationIndex;

   private String suffix;

   private CFNode cfNode;

   public String generateName()
   {
      final String descriptiveName = (this.patternDefinition.getName() != null ? this.patternDefinition.getName().trim() : "").replaceAll("\\s+", "");
      return String.format("pattern_%s_%d_%d_%s_%s", this.eContainingClass.getName(), this.eOperationIndex, this.cfNode.getId(), descriptiveName, this.suffix);
   }

   /**
    * Configures the {@link EClass} being currently transformed
    */
   public void setEClass(final EClass eContainingClass)
   {
      this.eContainingClass = eContainingClass;
   }

   /**
    * Configures the {@link EOperation} being currently transformed
    */
   public void setEOperation(final EOperation eOperation)
   {
      final List<EOperation> operations = getOperationsSortedByName(eContainingClass);
      int operationIndex = 0;
      
      final Map<EOperation, Integer> eOperationIndexes = new HashMap<>();
      for (final EOperation eOperationInClass : operations)
      {
         eOperationIndexes.put(eOperationInClass, operationIndex);
         ++operationIndex;
      }
      
      this.eOperationIndex = eOperationIndexes.get(eOperation);
   }

   /**
    * Configures the {@link PatternDef} being currently transformed
    */
   public void setPatternDefinition(final PatternDef patternDefinition)
   {
      this.patternDefinition = patternDefinition;
   }

   /**
    * Configures the {@link CFNode} being currently transformed
    */
   public void setCFNode(final CFNode cfNode)
   {
      this.cfNode = cfNode;
   }

   /**
    * Configures the pattern-type-specific suffix
    */
   public void setSuffix(final String suffix)
   {
      this.suffix = suffix;
   }

   /**
    * Returns the {@link EOperation}s of the given {@link EClass} sorted in ascending order by name
    */
   private List<EOperation> getOperationsSortedByName(final EClass eClass)
   {
      List<EOperation> operations = new ArrayList<>(eClass.getEOperations());
      operations.sort(new Comparator<EOperation>() {

         @Override
         public int compare(EOperation o1, EOperation o2)
         {
            return o1.getName().compareTo(o2.getName());
         }
      });
      return operations;
   }
}
