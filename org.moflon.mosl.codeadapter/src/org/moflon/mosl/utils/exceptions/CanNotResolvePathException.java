package org.moflon.mosl.utils.exceptions;



import MOSLCodeAdapter.moslPlus.MoslCategory;
import MocaTree.Attribute;
import MocaTree.File;
import MocaTree.MocaTreeFactory;
import MocaTree.Node;
import MocaTree.TreeElement;

public class CanNotResolvePathException extends MoslException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1628826593032436625L;
   private String name;
   private MoslCategory category;
	
	public CanNotResolvePathException(String message, Throwable arg, String referencePath, Node errorNode, String name, MoslCategory category){
		super(message, referencePath, errorNode, arg);
      this.name = name;
      this.category = category; 
	}
	
	public CanNotResolvePathException(String message, Throwable arg, String referencePath, TreeElement referenceElement, String name, MoslCategory category){
		this(message,arg,referencePath,null,name,category);
		if (referenceElement instanceof Node)
			setErrorNode(Node.class.cast(referenceElement));
		else if(referenceElement instanceof Attribute)
			setErrorNode(Attribute.class.cast(referenceElement).getNode());
		else if (referenceElement instanceof File)
			setErrorNode(File.class.cast(referenceElement).getRootNode());
		else {
			setErrorNode(MocaTreeFactory.eINSTANCE.createNode());
		}
	}
		
	public CanNotResolvePathException(String message, Throwable arg, String referencePath, TreeElement referenceElement){
		this(message, arg, referencePath, referenceElement, null, null) ;
	}
	
	
	public CanNotResolvePathException(String message, String referencePath, TreeElement referenceElement, String name, MoslCategory category){
		this(message, null, referencePath, referenceElement, name, category);
	}

	public CanNotResolvePathException(String message, String referencePath, TreeElement referenceElement, MoslCategory category){
		this(message, null, referencePath, referenceElement, null, category);
	}

   public String getReferencePath()
   {
      return referencePath;
   }

   public String getName()
   {
      return name;
   }

   public MoslCategory getCategory()
   {
      return category;
   }
	
	
}
