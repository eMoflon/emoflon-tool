/**
 * 
 */
package de.uni_paderborn.fujaba.app;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import de.uni_paderborn.fujaba.project.FujabaPersistencySupport;
import de.uni_paderborn.fujaba.project.ProjectManager;
import de.upb.lib.plugins.PluginManager;
import de.upb.lib.plugins.PluginProperty;

/**
 * @author Oleg Travkin
 *
 */
public class SimpleFujabaPersistencySupport extends FujabaPersistencySupport
{

   /**
    * @see de.uni_paderborn.fujaba.project.PersistencySupport#getPluginProperty(java.lang.String)
    */
   public PluginProperty getPluginProperty(String key)
   {
      PluginManager manager = FujabaApp.getPluginManager();
      return (manager == null ? null : manager.getFromProperties(key));
   }


   /**
    * @see de.uni_paderborn.fujaba.project.PersistencySupport#loadRequiredProjectOnDemand(java.lang.String, java.lang.String, java.io.File)
    */
   public void loadRequiredProjectOnDemand(String projectNameWhileIntroducingDependency,
         String loadingProject, File projectFile)
   {
      File file = projectFile;
      if(file == null)
      {
         //sounds pretty stupid, but somehow class is missused 
         file = new File(loadingProject);
      }
      
      try
      {
         ProjectManager.get().loadProject(file, null, false);
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      catch (InvocationTargetException e)
      {
         e.printStackTrace();
      }
   }

}
