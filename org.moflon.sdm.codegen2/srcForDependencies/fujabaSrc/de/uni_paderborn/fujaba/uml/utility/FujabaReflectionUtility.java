package de.uni_paderborn.fujaba.uml.utility;

import java.util.Set;

import de.uni_paderborn.fujaba.gui.PEActLink;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.common.FStereotype;
import de.uni_paderborn.fujaba.metamodel.common.util.FProjectUtility;
import de.uni_paderborn.fujaba.metamodel.factories.FFactory;
import de.uni_paderborn.fujaba.metamodel.structure.FAssoc;
import de.uni_paderborn.fujaba.metamodel.structure.FCardinality;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FBaseType;
import de.uni_paderborn.fujaba.metamodel.structure.FRole;
import de.uni_paderborn.fujaba.metamodel.structure.util.FClassUtility;
import de.uni_paderborn.fujaba.metamodel.structure.util.ReflectionUtility;

public class FujabaReflectionUtility extends ReflectionUtility
{

    public static final String INSTANCES_ASSOC_NAME = "instances";

    public static final String REFLECTIVE_ASSOC_NAME = "reflective";

	@Override
	public FClass findMetaClass(FProject project, FClass myClass) {
		return findMetaClass(project);
	}   
    
    public FClass findMetaClass(FProject project) {
		FClass clazz = FProjectUtility.provideClass(project, "de.uni_kassel.features", "ClassHandler");
		FClassUtility.provideStereotype(clazz, FStereotype.REFERENCE, true);
		return clazz;
	}
    
	@Override
	public boolean isMetaAssoc(FAssoc assoc) {
		return super.isMetaAssoc(assoc) && INSTANCES_ASSOC_NAME.equals(assoc.getName());
	}

	@Override
	public FAssoc findInstanceOfAssoc(FProject project, FClass myClass)
    {
		return findInstanceOfAssoc(project);
	}

	public FAssoc findInstanceOfAssoc(FProject project)
    {
		FClass classHandlerClass = findMetaClass(project);
        Set<? extends FAssoc> allAssocs = FClassUtility.getAllAssocs(classHandlerClass);
		FAssoc result = null;
		for (FAssoc assoc : allAssocs)
		{
			if (INSTANCES_ASSOC_NAME.equals(assoc.getName()))
			{
				result = assoc;
				break;
			}
		}
		if (result == null)
		{
			FClass jlObjectClass = provideObjectClass(project);
			// FIXME !!! remove access to PEActLink
			result = PEActLink.createAssocOnTheFly(classHandlerClass, jlObjectClass, INSTANCES_ASSOC_NAME);
		}
		return result;
	}
	

	public boolean isMetaClass(FClass clazz)
    {
		return clazz.getFullClassName().startsWith("de.uni_kassel.features") && clazz.getName().endsWith("Handler");
	}


    public FClass provideCollectionClass(FProject project)
    {
        return findOrCreate(project, "java.util", "Collection");
    }


    public FClass provideWrapperClass(FProject project, FBaseType baseType)
    {
        String name = baseType.getName();
        FClass newClass = findOrCreate(project, "java.lang", name);

        FClassUtility.provideStereotype(newClass, FStereotype.REFERENCE, false);

        return newClass;
    }


    public boolean isReflectiveAssoc(FAssoc assoc)
    {
        return REFLECTIVE_ASSOC_NAME.equals(assoc.getName());
    }

    
    public FClass provideObjectClass(FProject project)
    {
        return findOrCreate(project, "java.lang", "Object");
    }
    


    
    protected FAssoc provideReflectiveAssoc(FClass source, FClass target)
    {
    	FAssoc assoc = findReflectiveAssoc(source, target);
    	if (assoc == null)
    	{
    	   // FIXME !!! remove access to PEActLink
    		assoc = PEActLink.createAssocOnTheFly(source, target, REFLECTIVE_ASSOC_NAME);
    		FFactory<FCardinality> cardinalityFactory = assoc.getProject().getFromFactories (FCardinality.class);
    		FRole leftRole = assoc.getLeftRole();
			leftRole.setName("one");
    		leftRole.setCard(cardinalityFactory.getFromProducts(FCardinality.CARD_0_1));
    		FRole rightRole = assoc.getRightRole();
			rightRole.setName("many");
    		rightRole.setCard(cardinalityFactory.getFromProducts(FCardinality.CARD_0_N));
    	}
  
    	return assoc;
    }



}
