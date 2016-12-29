package org.moflon.moca;

import org.moflon.core.utilities.eMoflonEMFUtil;

import MocaTree.File;
import MocaTree.Folder;
import MocaTree.MocaTreePackage;
import MocaTree.Node;

public class EMFPersistenceUtil
{
   /**
    * Loads the root node of a MocaTree from the given XMI file
    * @param path
    * @return
    */
   public static Node loadRootNode(String path)
   {
      File file = loadFile(path);
      return file.getRootNode();
   }

   /**
    * Loads the first file from a MocaTree from the given XMI file
    * @param path
    * @return
    */
   public static File loadFile(String path)
   {
      Folder folder = (Folder) eMoflonEMFUtil.loadAndInitModelWithDependencies(MocaTreePackage.eINSTANCE, path, null);      
      return folder.getFile().get(0);
   }

}
