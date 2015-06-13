/**
 * 
 */
package de.uni_paderborn.fujaba.app;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JOptionPane;

import de.uni_paderborn.fujaba.app.action.OpenProjectAction;
import de.uni_paderborn.fujaba.project.FujabaPersistencySupport;
import de.uni_paderborn.fujaba.project.ProjectManager;
import de.upb.lib.plugins.PluginManager;
import de.upb.lib.plugins.PluginProperty;

/**
 * @author Dietrich Travkin (travkin)
 * @author Last editor: $Author$
 * @version $Revision$ $Date$
 */
public class FujabaPersistencySupportWithUI extends FujabaPersistencySupport
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
   public void loadRequiredProjectOnDemand(
         String projectNameWhileIntroducingDependency, String loadingProject,
         File projectFile)
   {
      int result = JOptionPane
            .showConfirmDialog(
                  FrameMain.get(),
                  "The project you are loading ("
                        + loadingProject
                        + ") requires another "
                        + "project which was known by the name '"
                        + projectNameWhileIntroducingDependency
                        + "'.\n"
                        + "This project is not opened, yet. Please choose the file containing the required project from the "
                        + "next dialog or cancel the loading process.",
                  "Required project", JOptionPane.OK_CANCEL_OPTION);
      if (result == JOptionPane.OK_OPTION)
      {
         File[] files = new OpenProjectAction().getFiles(false);
         if (files != null && files.length > 0)
         {
            try
            {
               ProjectManager.get().loadProject(files[0], null, false);
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
   }
}
