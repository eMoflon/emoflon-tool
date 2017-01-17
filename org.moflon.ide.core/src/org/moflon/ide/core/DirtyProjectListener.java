package org.moflon.ide.core;

import org.eclipse.core.resources.IProject;

/**
 * A listener for changes in the project's 'dirty' state.
 */
public interface DirtyProjectListener
{
   /**
    * Triggered if the dirty state of a project has changed
    * @param project the project
    * @param isDirty whether the project is dirty, now
    */
   void dirtyStateChanged(IProject project, boolean isDirty);
}
