package org.moflon.ide.metamodelevolution.core.changes;

import org.eclipse.emf.common.util.EList;
import org.moflon.ide.metamodelevolution.core.ChangeSequence;
import org.moflon.ide.metamodelevolution.core.RenameChange;
import org.moflon.ide.metamodelevolution.core.impl.CoreFactoryImpl;

import MocaTree.Attribute;
import MocaTree.Node;
import MocaTree.Text;

public class ChangesTreeCalculator implements MetamodelChangeCalculator
{ 
   @Override
   public ChangeSequence parseTree(final Node tree)
   {
      ChangeSequence delta = CoreFactoryImpl.eINSTANCE.createChangeSequence();

      parseChangesTree(tree, delta);

      return delta;
   }

   /**
    * This method recursively extracts the Metamodel changes from the MocaChangesTree and maps it to the ChangeMetamodel
    * 
    */
   private ChangeSequence parseChangesTree(final Node tree, ChangeSequence delta)
   {
      if (tree.getName() != null && (tree.getName().equals("EClass")))
      {
    	  RenameChange change = createClassRenameChange(tree);
    	  if (change != null)
    	  {
        	  delta.getEModelElementChange().add(change);
    	  }
    	  return delta;
         
      } else if (tree.getName() != null && (tree.getName().equals("EPackage")))
      {
    	  RenameChange change = createPackageRenameChange(tree);
    	  if (change != null)
    	  {
        	  delta.getEModelElementChange().add(change);
    	  }
    	  
    	  return parseChildren(tree, delta);
    	  
      } else
      {
    	  return parseChildren(tree, delta);
      }
   }
   
   private ChangeSequence parseChildren(Node tree, ChangeSequence delta)
   {
       final EList<Text> children = tree.getChildren();
       for (Text text : children)
       {
          Node node = (Node) text;
          parseChangesTree(node, delta);
       }
       return delta;   
   }
   
   private RenameChange createClassRenameChange(Node tree)
   {
	   RenameChange renaming = CoreFactoryImpl.eINSTANCE.createRenameChange();
       renaming.setElement("EClass");
       
       EList<Attribute> attributes = tree.getAttribute();
       for (Attribute attr : attributes)
       {
          if (attr.getName().equals("name"))
             renaming.setCurrentValue(attr.getValue());
          if (attr.getName().equals("previousName"))
             renaming.setPreviousValue(attr.getValue());
          if (attr.getName().equals("packageName"))
             renaming.setPackageName(attr.getValue());
       }
       if (renaming.getCurrentValue() == null || renaming.getPreviousValue() == null 
    		   || renaming.getCurrentValue().equals(renaming.getPreviousValue()))
       {
    	   return null;   
       }      
       return renaming;
   }
   
   private RenameChange createPackageRenameChange(Node tree)
   {
       RenameChange renaming = CoreFactoryImpl.eINSTANCE.createRenameChange();
       renaming.setElement("EPackage");
        
       EList<Attribute> attributes = tree.getAttribute();
       for (Attribute attr : attributes)
       {
    	   if (attr.getName().equals("Changes::Name"))
                renaming.setCurrentValue(attr.getValue());
           if (attr.getName().equals("Changes::PreviousName"))
                renaming.setPreviousValue(attr.getValue());
           if (attr.getName().equals("Changes::PackageName"))
               renaming.setPackageName(attr.getValue());
           if (attr.getName().equals("Changes::IsTLP"))
        	   renaming.setElement("TLPackage");           
       }
       if (renaming.getCurrentValue() == null || renaming.getPreviousValue() == null 
    		   || renaming.getCurrentValue().equals(renaming.getPreviousValue()))
       {
    	   return null;   
       }     
        return renaming;
   }
}
