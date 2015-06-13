package org.moflon.mosl.utils;

import java.util.ArrayList;

import org.moflon.mosl.utils.exceptions.CanNotResolveCategoryException;

import MOSLCodeAdapter.moslPlus.Category;
import MocaTree.Attribute;
import MocaTree.MocaTreeFactory;
import MocaTree.Node;
import MocaTree.Text;

public abstract class AbstractResolver {
	public static Category getCategory(String value){
		//add new Categories recognition here
		switch(value){		
		case "workingSet": return Category.WORKING_SET;
		case "package": return Category.PACKAGE;
		case "other": return Category.OTHER;
		case "eclassFile": return Category.ECLASS_FILE;
		case "tggFile": return Category.TGG_FILE;
		case "rulesFolder": return Category.RULES_FOLDER;
		case "mconfFile": return Category.MCONF_FILE;
		case "schemaFile": return Category.SCHEMA_FILE;
		case "patternFolder": return Category.PATTERN_FOLDER;
		case "patternFile": return Category.PATTERN_FILE;
		case "pattern": return Category.PATTERN;
		case "objectVariable": return Category.OBJECT_VARIABLE;
		case "link": return Category.LINK;
		case "rule": return Category.RULE;
		case "correspondence": return Category.CORRESPONDENCE;
		case "reference": return Category.REFERENCE;
		case "attribute": return Category.ATTRIBUTE;
		case "operation": return Category.OPERATION;
		case "parameter": return Category.PARAMETER;
		case "edatatype": return Category.TYPE;
		case "eenum": return Category.TYPE;
		case "eclass": return Category.TYPE;
		case "tggObjectVariable": return Category.TGG_OBJECT_VARIABLE;
		case "tggLink": return Category.TGG_LINK;
		case "metamodel": return Category.METAMODEL;
		case "opposite": return Category.OPPOSITE;
		case "typedExpression": return Category.TYPED_EXPRESSION;
		case "type": return Category.TYPE;
		
		default: throw new CanNotResolveCategoryException("Unknown Category: "+value);
		}
	}
	
	public Category getCategory(Node node){
		return getCategory(node, "category");
	}
	
	protected Category getCategory(Attribute attribute){
		return getCategory(attribute.getValue());
	}
	
	protected Category getCategory(Node node, String type){
		Attribute categoryAttr=null;
		Category category=null;
		try{
			categoryAttr=new ArrayList<Attribute>(node.getAttribute(type)).get(0);
		}catch (IndexOutOfBoundsException ioobe){
			categoryAttr = createAttribute("category", "other", node.getAttribute().size());					
		}catch (Exception e){
			throw new CanNotResolveCategoryException("Unknown Exception", node, e);
		}		 
		try{
			category=getCategory(categoryAttr.getValue());
		} catch(CanNotResolveCategoryException ce){	
		
			throw new CanNotResolveCategoryException("Unknown Category: "+categoryAttr.getValue(),node);
		}catch (Exception e){
			throw new CanNotResolveCategoryException("Unknown Exception", node, e);
		}	
		return category;
	
	}
	
	protected boolean hasChild(Node node, String childName){
		return node.getChildren(childName).size()>0;
	}
	
	protected Node getChild(Node node, String childName){
		try{
			return Node.class.cast(new ArrayList<Text>(node.getChildren(childName)).get(0));
		} catch (Exception e){
			return null;
		}
	}
	
	protected boolean hasAttribute(Node node, String attributeName){
		return node.getAttribute(attributeName).size()>0;
	}
	
	protected Attribute getAttribute (Node node, String attributeName){
		return new ArrayList<Attribute>(node.getAttribute(attributeName)).get(0);
	}
	
	protected Attribute createAttribute(String name, String value, int index){
		Attribute attribute = MocaTreeFactory.eINSTANCE.createAttribute();
		attribute.setName(name);
		attribute.setValue(value);
		attribute.setIndex(index);
		return attribute;
	}
}
