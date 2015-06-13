package de.uni_paderborn.fujaba.metamodel.structure.util;

import java.util.Iterator;
import java.util.Set;

import javax.swing.JOptionPane;

import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.common.FStereotype;
import de.uni_paderborn.fujaba.metamodel.common.util.FProjectUtility;
import de.uni_paderborn.fujaba.metamodel.structure.FAssoc;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FGeneralization;
import de.uni_paderborn.fujaba.uml.utility.FujabaReflectionUtility;

public abstract class ReflectionUtility {
    
	protected static ReflectionUtility instance = new FujabaReflectionUtility();
	
	
	public static ReflectionUtility get()
	{
		return instance;
	}
	      
    public abstract FClass findMetaClass(FProject project, FClass myClass);
    
    public abstract FAssoc findInstanceOfAssoc(FProject project, FClass myClass);
  
    public abstract boolean isMetaClass(FClass clazz);

    public abstract boolean isReflectiveAssoc(FAssoc assoc);

    public abstract FClass provideObjectClass(FProject project);
        
    protected abstract FAssoc provideReflectiveAssoc(FClass source, FClass target);
  
	public boolean isMetaAssoc(FAssoc assoc)
    {
        if (assoc == null || assoc.getLeftRole() == null || assoc.getLeftRole() == null )
		{
			return false;
		}

		FClass left = assoc.getLeftRole().getTarget();
		FClass right = assoc.getRightRole().getTarget();
		if (left != null && right != null)
		{
			return isMetaClass(left) ^ isMetaClass(right);
		}

		return false;
    }

    protected FClass findOrCreate(FProject project, String packageName, String className)
    {
    	FClass newClass = FProjectUtility.findClass(project, packageName, className);
    	if (newClass == null)
    	{
    		JOptionPane.showMessageDialog(null, "This will add the class " + packageName + "." + className + " to your project,\n but will not include it in any class diagram!",
                    "Implicit class creation", JOptionPane.INFORMATION_MESSAGE);
    		newClass = FProjectUtility.provideClass(project, packageName, className);
    	}

		FClassUtility.provideStereotype(newClass, FStereotype.REFERENCE, false);
        return newClass;
    }

    public FAssoc provideReflectiveAssoc(FClass source, FClass target, boolean extendObject) {
        if (source == null)
        {
            return null;
        }
        if (target == null)
        {
        	target = source;
        }

        FAssoc result = findReflectiveAssoc(source, target);
        if (result != null)
        {
            return result;
        }

        if (extendObject)
        {
        	provideObjectGeneralization(source);
        	provideObjectGeneralization(target);

        	FClass objectClass = provideObjectClass(source.getProject());
            return provideReflectiveAssoc(objectClass, objectClass);
        } else
        {
            return provideReflectiveAssoc(source, target);
        }
    }
    
    private void provideObjectGeneralization(FClass clazz)
    {
        FClass objectClass = provideObjectClass(clazz.getProject());
        FClass sourceToplevelClass = findToplevelClass(clazz);
        if (sourceToplevelClass != objectClass)
        {
            FClassUtility.createGeneralization(sourceToplevelClass, objectClass);            	
        }
    }

    protected FAssoc findReflectiveAssoc(FClass class1, FClass class2)
    {
        Set<? extends FAssoc> assocs = FClassDiagramUtility.calculateAssocs(class1, class2);
        for (FAssoc assoc : assocs)
        {
        	if (isReflectiveAssoc(assoc))
        	{
        		return assoc;
        	}
        }
        return null;
    }

    private static FClass findToplevelClass(FClass clazz)
    {
        while (clazz.getSuperClass() != null)
        {
        	// TODO remove following code
        	FClass superClass = clazz.getSuperClass();
        	if (superClass == clazz)
        	{
        		Iterator<? extends FGeneralization> it = clazz.iteratorOfRevSubclass();
        		while (it.hasNext())
        		{
        			FGeneralization gen = (FGeneralization) it.next();
        			if (gen.getSubclass() == gen.getSuperclass())
        			{
        				gen.removeYou();
        			}
        		}
        	}
        	// remove end
            clazz = clazz.getSuperClass();
        }
        return clazz;
    }
}
