package de.uni_paderborn.fujaba.metamodel.factories;


import de.uni_kassel.coobra.Repository;
import de.uni_paderborn.fujaba.metamodel.common.FProject;


public interface FProjectFactory<I extends FProject>
{

   public I create();


   public I create(Repository repository, boolean persistent);

}
