package org.moflon.gt.mosl.codeadapter;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

public class MOSLGTUtil
{
   private static MOSLGTUtil instance;

   private MOSLGTUtil()
   {

   }

   @Deprecated
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

}
