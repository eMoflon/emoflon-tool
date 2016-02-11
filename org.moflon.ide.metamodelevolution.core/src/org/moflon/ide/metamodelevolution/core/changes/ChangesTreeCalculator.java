package org.moflon.ide.metamodelevolution.core.changes;

import org.eclipse.emf.common.util.EList;
import org.moflon.core.mocatomoflon.MocaToMoflonUtils;
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
      //TODO@settl: Proceed analogously for all other types
      if (MocaToMoflonUtils.isEClassNode(tree))
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
       renaming.setElement(MocaToMoflonUtils.ECLASS_NODE_NAME);
       
       EList<Attribute> attributes = tree.getAttribute();
       for (Attribute attr : attributes)
       {
          switch(attr.getName())
          {
          case "name":
             renaming.setCurrentValue(attr.getValue());
             break;
          case "previousName":
             renaming.setPreviousValue(attr.getValue());
             break;
          case "packageName":
             renaming.setPackageName(attr.getValue());
             break;
          }
       }
       
       if (hasMissingAttribute(renaming))
       {
    	   return null;   
       }
       
       return renaming;
   }

   private boolean hasMissingAttribute(RenameChange renaming)
   {
      return renaming.getCurrentValue() == null || renaming.getPreviousValue() == null 
    		   || renaming.getCurrentValue().equals(renaming.getPreviousValue());
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
       if (hasMissingAttribute(renaming))
       {
    	   return null;   
       }     
        return renaming;
   }
}
