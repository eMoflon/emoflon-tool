package org.moflon.ide.texteditor.modules.outline;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import MocaTree.Node;


public abstract class AbstractMocaTreeContentProvider implements ITreeContentProvider
{

	public abstract Node[] getElements(Node inputElement);

	public abstract Node[] getChildren(Node parentElement);

    public abstract Node getParent(Node element);

    public abstract boolean hasChildren(Node element);
	
    public abstract void inputChanged(Viewer viewer, Node oldInput, Node newInput);
    


	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
	{
		inputChanged(viewer, (Node)oldInput, (Node)newInput);
	}

	@Override
	public Object[] getElements(Object inputElement)
	{
		return getElements((Node)inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement)
	{
		return getChildren((Node)parentElement);
	}

	@Override
	public Object getParent(Object element)
	{
		return getParent((Node)element);
	}

	@Override
	public boolean hasChildren(Object element)
	{
		return hasChildren((Node)element);
	}

	
	
}
