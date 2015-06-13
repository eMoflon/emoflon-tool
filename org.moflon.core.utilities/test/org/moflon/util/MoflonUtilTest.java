package org.moflon.util;

import org.eclipse.emf.codegen.ecore.genmodel.GenModelFactory;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.junit.Assert;
import org.junit.Test;
import org.moflon.core.utilities.MoflonUtil;

public class MoflonUtilTest
{
   @Test
   public void testFQNForGenPackages() throws Exception
   {
      
      GenPackage topPackage = createGenPackage("1");
      GenPackage mediumPackage = createGenPackage("2");
      topPackage.getEcorePackage().getESubpackages().add(mediumPackage.getEcorePackage());
      GenPackage lowestPackage = createGenPackage("3");
      mediumPackage.getSubGenPackages().add(lowestPackage);
      Assert.assertEquals("1.2.3", MoflonUtil.getFQN(lowestPackage));
   }

   public GenPackage createGenPackage(final String label)
   {
      EPackage ePackage = createEPackage(label);
      GenPackage genPackage = GenModelFactory.eINSTANCE.createGenPackage();
      genPackage.setEcorePackage(ePackage);
      return genPackage;
   }

   public EPackage createEPackage(final String name)
   {
      EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
      ePackage.setName(name);
      return ePackage;
   }
}
