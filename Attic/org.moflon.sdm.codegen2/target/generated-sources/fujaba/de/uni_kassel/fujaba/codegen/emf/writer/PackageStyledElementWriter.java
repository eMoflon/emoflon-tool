/*
 * generated by Fujaba - CodeGen2
 */
package de.uni_kassel.fujaba.codegen.emf.writer;
import java.util.Iterator;

import de.uni_kassel.fujaba.codegen.classdiag.FStyledElementCodeWriter;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FStereotype;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FPackage;
import de.upb.tools.sdm.JavaSDM; // requires Fujaba5/libs/RuntimeTools.jar in classpath
import de.upb.tools.sdm.JavaSDMException;


public class PackageStyledElementWriter extends FStyledElementCodeWriter
{


   public boolean checkStereotype (FElement elem )
   {
      boolean fujaba__Success = false;
      Object _TmpObject = null;
      FPackage pkg = null;
      Iterator fujaba__IterClazzToStereotype = null;
      FStereotype stereotype = null;
      Iterator fujaba__IterPkgToClazz = null;
      FClass clazz = null;

      // story pattern storypatternwiththis
      try 
      {
         fujaba__Success = false; 

         _TmpObject = elem;

         // ensure correct type and really bound of object pkg
         JavaSDM.ensure ( _TmpObject instanceof FPackage );
         pkg = (FPackage) _TmpObject;

         // iterate to-many link $link.InstanceOf.Name from pkg to clazz
         fujaba__Success = false;
         fujaba__IterPkgToClazz = new de.uni_kassel.sdm.Path (pkg, "packages*.declares");
         while ( !(fujaba__Success) && fujaba__IterPkgToClazz.hasNext () )
         {
            try
            {
               _TmpObject =  fujaba__IterPkgToClazz.next ();

               // ensure correct type and really bound of object clazz
               JavaSDM.ensure ( _TmpObject instanceof FClass );
               clazz = (FClass) _TmpObject;

               // check negative bindings
               try
               {
                  fujaba__Success = false;

                  // iterate to-many link stereotypes from clazz to stereotype
                  fujaba__Success = false;
                  fujaba__IterClazzToStereotype = clazz.iteratorOfStereotypes ();

                  while ( !(fujaba__Success) && fujaba__IterClazzToStereotype.hasNext () )
                  {
                     try
                     {
                        stereotype = (FStereotype) fujaba__IterClazzToStereotype.next ();

                        // check object stereotype is really bound
                        JavaSDM.ensure ( stereotype != null );
                        // attribute condition name == FStereotype.REFERENCE
                        JavaSDM.ensure ( JavaSDM.stringCompare ((String) stereotype.getName (), FStereotype.REFERENCE) == 0 );


                        fujaba__Success = true;
                     }
                     catch ( JavaSDMException fujaba__InternalException )
                     {
                        fujaba__Success = false;
                     }
                  }
                  JavaSDM.ensure (fujaba__Success);

                  fujaba__Success = true;
               }
               catch ( JavaSDMException fujaba__InternalException )
               {
                  fujaba__Success = false;
               }

               fujaba__Success = !(fujaba__Success);

               JavaSDM.ensure ( fujaba__Success );

               // constraint super.checkStereotype (clazz)
               JavaSDM.ensure ( super.checkStereotype (clazz) );

               fujaba__Success = true;
            }
            catch ( JavaSDMException fujaba__InternalException )
            {
               fujaba__Success = false;
            }
         }
         JavaSDM.ensure (fujaba__Success);
         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      if ( fujaba__Success )
      {
         return true;

      }
      return false;
   }

}

