/**
 * 
 */
package de.uni_paderborn.fujaba.project;

import java.util.List;

import de.uni_paderborn.fujaba.metamodel.common.FProject;

/**
 * @author Dietrich Travkin (travkin)
 * @author Last editor: $Author$
 * @version $Revision$ $Date$
 */
public class ProjectDependencyCycleException extends Exception
{
   private static final long serialVersionUID = 1339216796023967069L;

   public ProjectDependencyCycleException()
   {
      super("There is a cyclic project dependency.");
   }
   
   public ProjectDependencyCycleException(String text)
   {
      super(text);
   }
   
   private List<FProject> affectedProjects = null;
   
   public ProjectDependencyCycleException(String text, List<FProject> missingProjects)
   {
      super(text);
      this.affectedProjects = missingProjects;
   }
   
   public List<FProject> getAffectedProjects()
   {
      return this.affectedProjects;
   }
}
