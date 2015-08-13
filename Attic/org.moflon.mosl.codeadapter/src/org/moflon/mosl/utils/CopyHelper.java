package org.moflon.mosl.utils;

import org.eclipse.emf.ecore.util.EcoreUtil;

import MocaTree.Attribute;
import MocaTree.File;
import MocaTree.Folder;
import MocaTree.Node;
import MocaTree.Text;
import MocaTree.TreeElement;


public class CopyHelper {
	
	protected CopyHelper(){
		super();
	}
	
	public static CopyHelper init(){
		return new CopyHelper();
	}
	
	public void addCopy(TreeElement rootElement, TreeElement toAddElement) throws CanNotAddTreeElementException{
		TreeElement cpy = EcoreUtil.copy(toAddElement);
		
		if(rootElement instanceof Node)
			addAtNode((Node) rootElement, cpy);
		else if(rootElement instanceof File)
			addAtFile((File) rootElement, cpy);
		else if(rootElement instanceof Folder)
			addAtFolder((Folder) rootElement, cpy);
		else if(rootElement instanceof Attribute)
			addAtAttribute((Attribute) rootElement, cpy);
		else 
			throw new CanNotAddTreeElementException(rootElement, toAddElement);
	}
	
	private void addAtNode(Node node, TreeElement element) throws CanNotAddTreeElementException {
		if(element instanceof Text)
			node.getChildren().add((Text) element);
		else if(element instanceof Attribute)
			node.getAttribute().add((Attribute) element);
		else if(element instanceof File)
			node.setFile((File) element);
		else
			throw new CanNotAddTreeElementException(node, element);
	}
	
	private void addAtFile(File file, TreeElement element) throws CanNotAddTreeElementException {
		if(element instanceof Node)
			file.setRootNode((Node)element);
		else if(element instanceof Folder)
			file.setFolder((Folder) element);
		else
			throw new CanNotAddTreeElementException(file, element);
	}
	
	private void addAtFolder(Folder folder, TreeElement element) throws CanNotAddTreeElementException {
		if(element instanceof Folder)
			folder.getSubFolder().add((Folder) element);
		else if(element instanceof File)
			folder.getFile().add((File) element);
		else
			throw new CanNotAddTreeElementException(folder, element);
	}
	
	private void addAtAttribute(Attribute attribute, TreeElement element) throws CanNotAddTreeElementException {
		if(element instanceof Node)
			attribute.setNode((Node) element);
		else
			throw new CanNotAddTreeElementException(attribute, element);
	}
	
}
