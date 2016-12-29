/*
 * Copyright (c) 2010-2012 Gergely Varro
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   Gergely Varro - Initial API and implementation
 */
package org.moflon.eclipse.resource;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.moflon.codegen.GenModelBuilder;

public class GenModelResource extends XMIResourceImpl
{
   private boolean fujabaCompatibilityMode;

   GenModelResource(URI uri)
   {
      super(uri);
      setEncoding("UTF-8");
      getDefaultSaveOptions().put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);
      getDefaultSaveOptions().put(XMLResource.OPTION_LINE_WIDTH, 80);
      getDefaultSaveOptions().put(XMLResource.OPTION_URI_HANDLER, new URIHandlerImpl.PlatformSchemeAware());
   }

   public final boolean isFujabaCompatibilityMode()
   {
      return fujabaCompatibilityMode;
   }

   public final void setFujabaCompatibilityMode(boolean fujabaCompatibilityMode)
   {
      boolean oldFujabaCompatibilityMode = this.fujabaCompatibilityMode;
      this.fujabaCompatibilityMode = fujabaCompatibilityMode;
      if (oldFujabaCompatibilityMode != this.fujabaCompatibilityMode)
      {
         GenModel genModel = (GenModel) getContents().get(0);
         if (this.fujabaCompatibilityMode)
         {
            genModel.setTemplateDirectory(".JETEmitters/templates");
            genModel.setDynamicTemplates(true);
            genModel.setImporterID(null);
            genModel.getForeignModel().clear();
            URI deresolvedURI = getURI().trimFileExtension().appendFileExtension(GenModelBuilder.ECORE_FILE_EXTENSION);
            genModel.getForeignModel().add("L/" + deresolvedURI.deresolve(URI.createPlatformResourceURI("/", true)).toString());
         } else
         {
            genModel.setTemplateDirectory(null);
            genModel.setDynamicTemplates(false);
            genModel.setImporterID("org.eclipse.emf.importer.ecore");
            genModel.getForeignModel().clear();
            genModel.getForeignModel().add(getURI().trimFileExtension().lastSegment());
         }

      }
   }

   protected boolean useIDs()
   {
      return eObjectToIDMap != null || idToEObjectMap != null;
   }

   protected XMLHelper createXMLHelper()
   {
      return new GenModelResourceHelper(this);
   }
}
