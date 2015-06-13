/*
 * generated by Fujaba - CodeGen2
 */
package de.uni_kassel.fujaba.codegen.classdiag;
import de.uni_paderborn.fujaba.metamodel.common.FCodeStyle;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FStyledElement;
import de.upb.tools.sdm.JavaSDM; // requires Fujaba5/libs/RuntimeTools.jar in classpath
import de.upb.tools.sdm.JavaSDMException;


public class FStyledElementCodeWriter extends ASGElementStereotypeCodeWriter
{


   public boolean checkStereotype (FElement elem )
   {
      boolean fujaba__Success = false;
      Object _TmpObject = null;
      FStyledElement accessed = null;
      FCodeStyle stype = null;

      // story pattern 
      try 
      {
         fujaba__Success = false; 

         _TmpObject = elem;

         // ensure correct type and really bound of object accessed
         JavaSDM.ensure ( _TmpObject instanceof FStyledElement );
         accessed = (FStyledElement) _TmpObject;

         // search to-one link inheritedCodeStyle from accessed to stype
         stype = accessed.getInheritedCodeStyle ();

         // check object stype is really bound
         JavaSDM.ensure ( stype != null );

         // check isomorphic binding between objects stype and accessed
         JavaSDM.ensure ( !stype.equals (accessed) );

         // attribute condition name == getStereotypeText()
         JavaSDM.ensure ( JavaSDM.stringCompare ((String) stype.getName (), getStereotypeText()) == 0 );


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


