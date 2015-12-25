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
    * Classification
    */
   private ChangeSequence parseChangesTree(final Node tree, ChangeSequence delta)
   {
      if (tree.getName() != null && (tree.getName().equals("EClass")))
      {
         delta.getEModelElementChange().add(createClassRenameChange(tree));
         return delta;
         
      } /*else if (tree.getName() != null && (tree.getName().equals("EPackage")))
      {
       
      }*/else
      {
         final EList<Text> children = tree.getChildren();
         for (Text text : children)
         {
            Node node = (Node) text;
            parseChangesTree(node, delta);
         }
         return delta;
      }
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
       return renaming;
   }
   
   private RenameChange createPackageRenameChange(Node tree)
   {
       /*boolean isTLP = false;
       EList<Attribute> attributes = tree.getAttribute();
       for (Attribute attr : attributes) 
       {
          if (attr.getName().equals("isTLP"))
          {
             attr.getName().equals("true");
             // TLP
             isTLP = true;
          }
       }
       if (isTLP)
       {
          {
             final EList<Text> children = tree.getChildren();
             for (Text text : children)
             {
                Node node = (Node) text;
                parseChangesTree(node, delta);
             }
             return delta;
          }
       }
       else 
       {
          RenameChange renaming = CoreFactoryImpl.eINSTANCE.createRenameChange();
          renaming.setElement("EPackage");
          
          for (Attribute attr : attributes)
          {
             if (attr.getName().equals("name"))
                renaming.setCurrentValue(attr.getValue());
             if (attr.getName().equals("previousName"))
                renaming.setPreviousValue(attr.getValue());
          }
          return renaming;
       }*/   
       return null;
   }
}
