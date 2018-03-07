package org.moflon.core.ecoreutils;

import org.eclipse.emf.ecore.EObject;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link MoflonList}
 */
public class MoflonListTest {

	private static final EcoreutilsFactory FACTORY = EcoreutilsFactory.eINSTANCE;

	@Test
	public void testListWithItemsContainingDifferentTypes() {

		MoflonList list = FACTORY.createMoflonList();

		boolean expectedBoolean = true;
		MoflonListItem booleanItem = FACTORY.createMoflonListItem();
		list.getItems().add(booleanItem);
		EBooleanContainer booleanContainer = FACTORY.createEBooleanContainer();
		booleanContainer.setContent(expectedBoolean);
		booleanItem.setContent(booleanContainer);

		int expectedInt = 123;
		MoflonListItem intItem = FACTORY.createMoflonListItem();
		intItem.setPrevious(booleanItem);
		EIntContainer intContainer = FACTORY.createEIntContainer();
		intContainer.setContent(expectedInt);
		intItem.setContent(intContainer);
		list.getItems().add(intItem);

		double expectedDouble = 1.23;
		MoflonListItem doubleItem = FACTORY.createMoflonListItem();
		doubleItem.setPrevious(intItem);
		EDoubleContainer doubleContainer = FACTORY.createEDoubleContainer();
		doubleContainer.setContent(expectedDouble);
		doubleItem.setContent(doubleContainer);
		list.getItems().add(doubleItem);

		String expectedString = "abc";
		MoflonListItem stringItem = FACTORY.createMoflonListItem();
		stringItem.setPrevious(doubleItem);
		EStringContainer stringContainer = FACTORY.createEStringContainer();
		stringContainer.setContent(expectedString);
		stringItem.setContent(stringContainer);
		list.getItems().add(stringItem);

		MoflonListItem currentItem = booleanItem;
		do {
			EObject content = currentItem.getContent();
			if (content instanceof EBooleanContainer) {
				EBooleanContainer container = (EBooleanContainer) content;
				Assert.assertEquals(expectedBoolean, container.isContent());
			} else if (content instanceof EIntContainer) {
				EIntContainer container = (EIntContainer) content;
				Assert.assertEquals(expectedInt, container.getContent());
			} else if (content instanceof EDoubleContainer) {
				EDoubleContainer container = (EDoubleContainer) content;
				Assert.assertEquals(expectedDouble, container.getContent(), Double.MIN_VALUE);
			} else if (content instanceof EStringContainer) {
				EStringContainer container = (EStringContainer) content;
				Assert.assertEquals(expectedString, container.getContent());
			}

			currentItem = currentItem.getNext();
		} while (currentItem != null);
	}
}
