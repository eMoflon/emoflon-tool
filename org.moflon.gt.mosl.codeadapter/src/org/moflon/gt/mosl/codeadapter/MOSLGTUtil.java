package org.moflon.gt.mosl.codeadapter;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.gervarro.democles.specification.emf.SpecificationFactory;
import org.gervarro.democles.specification.emf.constraint.relational.RelationalConstraint;
import org.gervarro.democles.specification.emf.constraint.relational.RelationalConstraintFactory;
import org.gervarro.democles.specification.emf.constraint.relational.util.RelationalConstraintAdapterFactory;

public class MOSLGTUtil
{
   private static MOSLGTUtil instance;

   private MOSLGTUtil()
   {

   }

   public static MOSLGTUtil getInstance()
   {
      if (instance == null)
         instance = new MOSLGTUtil();
      return instance;
   }

   @Deprecated
   public interface MGTCallbackGetter
   {
      Collection<IFile> getMOSLGTFiles() throws CoreException;
   }

   private MGTCallbackGetter mgtGetter;

   @Deprecated
   public void setMGTGetter(MGTCallbackGetter mgtGetter)
   {
      this.mgtGetter = mgtGetter;
   }

   @Deprecated
   public Collection<IFile> getMGTFiles()
   {
      try
      {
         return this.mgtGetter.getMOSLGTFiles();
      } catch (CoreException ce)
      {
         return null;
      }
   }
   
   public RelationalConstraint getConstraint(String opValue){
      switch(opValue){
      case "==": return RelationalConstraintFactory.eINSTANCE.createEqual();
      case ">": return RelationalConstraintFactory.eINSTANCE.createLarger();
      case ">=": return RelationalConstraintFactory.eINSTANCE.createLargerOrEqual();
      case "<": return RelationalConstraintFactory.eINSTANCE.createSmaller();
      case "<=": return RelationalConstraintFactory.eINSTANCE.createSmallerOrEqual();
      default: return null;
      }
   }
}
