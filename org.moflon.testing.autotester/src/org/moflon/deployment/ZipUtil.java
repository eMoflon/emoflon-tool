package org.moflon.deployment;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.moflon.autotest.AutoTestActivator;

@Deprecated
public class ZipUtil
{

   /**
    * Compresses the files in the given folder to the given target archive file.
    * @return the archive
    * @throws CoreException
    * 
    * @Deprecated no longer necessary due to post-build event in VS
    */
   @Deprecated
   public static void getZipArchive(final IFolder sourceFolder, final IFile archive) throws CoreException
   {
      byte[] buffer = new byte[1024];
      BufferedInputStream origin = null;
   
      try
      {
         // Check if previous version of archive exists and delete
         if (archive.exists())
            archive.delete(true, null);
         // Create empty archive
         archive.create(null, true, null);
   
         // Open streams
         ZipOutputStream out = new ZipOutputStream(new FileOutputStream(archive.getLocation().toFile()));
   
         // Iterate over all entries of the folder
         IResource files[] = sourceFolder.members();
         for (IResource resource : files)
         {
            // Skip subfolders (should not exist)
            if (!(resource instanceof IFile))
               break;
   
            // Read content of file and add to archive
            IFile file = (IFile) resource;
            origin = new BufferedInputStream(new FileInputStream(file.getLocation().toFile()));
            ZipEntry entry = new ZipEntry(file.getName());
            out.putNextEntry(entry);
            int count;
            while ((count = origin.read(buffer)) > 0)
            {
               out.write(buffer, 0, count);
            }
   
            // Close entry and added file
            out.closeEntry();
            origin.close();
         }
   
         // Close zip archive
         out.close();
      } catch (Exception e)
      {
         throw new CoreException(new Status(IStatus.ERROR, AutoTestActivator.getModuleID(), "Creating archive failed", e));
      }
   }

}
