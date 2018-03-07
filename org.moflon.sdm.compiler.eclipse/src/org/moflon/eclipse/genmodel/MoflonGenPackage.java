package org.moflon.eclipse.genmodel;

import java.util.List;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenClassifier;
import org.eclipse.emf.codegen.ecore.genmodel.GenDataType;
import org.eclipse.emf.codegen.ecore.genmodel.GenEnum;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.codegen.ecore.genmodel.GenResourceKind;
import org.eclipse.emf.codegen.ecore.genmodel.impl.GenPackageImpl;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;

public class MoflonGenPackage extends GenPackageImpl {

	public void initialize(EPackage ePackage) {
		if (getPrefixGen() == null && ePackage.getName() != null) {
			setPrefix(capName(ePackage.getName()));
		}

		if (getBasePackageGen() == null
				&& EcoreUtil.getAnnotation(ePackage, GenModelPackage.eNS_URI, "basePackage") != null) {
			setBasePackage(EcoreUtil.getAnnotation(ePackage, GenModelPackage.eNS_URI, "basePackage"));
		}

		boolean isDifferentPackage = ePackage != getEcorePackage();
		if (isDifferentPackage) {
			setEcorePackage(ePackage);

			// Do this here because getExtendedMetaData() is used during initialization
			// and the mappings are are populated the first time it's fetched
			// which will miss the packages we add as we initialize the tree.
			//
			if (!EcorePackage.eNS_URI.equals(ePackage.getNsURI())
					&& !GenModelPackage.eNS_URI.equals(ePackage.getNsURI())) {
				getGenModel().getExtendedMetaData().putPackage(ePackage.getNsURI(), ePackage);
			}

			if (hasExtendedMetaDataInternal(ePackage)) {
				setResource(GenResourceKind.XML_LITERAL);
			}
			setDisposableProviderFactory(true);
		}

		int eClassIndex = 0;
		int eEnumIndex = 0;
		int eDataTypeIndex = 0;
		CLASSIFIER_LOOP: for (EClassifier eClassifier : ePackage.getEClassifiers()) {
			for (GenClassifier genClassifier : getGenClassifiers()) {
				if (genClassifier.getEcoreClassifier() == eClassifier) {
					if (eClassifier instanceof EClass) {
						((GenClass) genClassifier).initialize((EClass) eClassifier);
						int index = getGenClasses().indexOf(genClassifier);
						if (index != eClassIndex) {
							getGenClasses().move(eClassIndex, index);
						}
						++eClassIndex;
					} else if (eClassifier instanceof EEnum) {
						((GenEnum) genClassifier).initialize((EEnum) eClassifier);
						int index = getGenEnums().indexOf(genClassifier);
						if (index != eEnumIndex) {
							getGenEnums().move(eEnumIndex, index);
						}
						++eEnumIndex;
					} else if (eClassifier instanceof EDataType) {
						((GenDataType) genClassifier).initialize((EDataType) eClassifier);
						int index = getGenDataTypes().indexOf(genClassifier);
						if (index != eDataTypeIndex) {
							getGenDataTypes().move(eDataTypeIndex, index);
						}
						++eDataTypeIndex;
					}

					continue CLASSIFIER_LOOP;
				}
			}

			if (eClassifier instanceof EClass) {
				EClass eClass = (EClass) eClassifier;
				GenClass genClass = getGenModel().createGenClass();
				getGenClasses().add(eClassIndex++, genClass);
				genClass.initialize(eClass);
			} else if (eClassifier instanceof EEnum) {
				EEnum eEnum = (EEnum) eClassifier;
				GenEnum genEnum = getGenModel().createGenEnum();
				getGenEnums().add(eEnumIndex++, genEnum);
				genEnum.initialize(eEnum);
			} else if (eClassifier instanceof EDataType) {
				EDataType eDataType = (EDataType) eClassifier;
				GenDataType genDataType = getGenModel().createGenDataType();
				getGenDataTypes().add(eDataTypeIndex++, genDataType);
				genDataType.initialize(eDataType);
			}
		}

		int ePackageIndex = 0;
		PACKAGE_LOOP: for (EPackage nestedEPackage : ePackage.getESubpackages()) {
			for (GenPackage nestedGenPackage : getNestedGenPackages()) {
				if (nestedGenPackage.getEcorePackage() == nestedEPackage) {
					nestedGenPackage.initialize(nestedEPackage);
					int index = getNestedGenPackages().indexOf(nestedGenPackage);
					if (index != ePackageIndex) {
						getNestedGenPackages().move(ePackageIndex, index);
					}
					++ePackageIndex;
					continue PACKAGE_LOOP;
				}
			}

			GenPackage genPackage = getGenModel().createGenPackage();
			getNestedGenPackages().add(ePackageIndex++, genPackage);
			genPackage.initialize(nestedEPackage);
		}

		if (isDifferentPackage) {
			boolean isBigModel = isBigModel();
			setLoadInitialization(isBigModel);
			setLiteralsInterface(!isBigModel);
		}
	}

	public boolean hasExtendedMetaData() {
		if (getResource() != GenResourceKind.XML_LITERAL) {
			return false;
		}
		return hasExtendedMetaDataInternal(getEcorePackage());
	}

	protected boolean hasExtendedMetaDataInternal(EPackage ePackage) {
		List<EPackage> ePackages = new UniqueEList<EPackage>();
		ePackages.add(ePackage);
		for (int i = 0; i < ePackages.size(); ++i) {
			for (TreeIterator<EObject> j = ePackages.get(i).eAllContents(); j.hasNext();) {
				EObject eObject = j.next();
				if (eObject instanceof EPackage || eObject instanceof EDataType) {
					j.prune();
				} else if (eObject instanceof EAnnotation) {
					EAnnotation eAnnotation = (EAnnotation) eObject;
					String source = eAnnotation.getSource();
					if (ExtendedMetaData.ANNOTATION_URI.equals(source)) {
						return true;
					}
				}
				for (EObject eCrossReference : eObject.eCrossReferences()) {
					if (eCrossReference instanceof EClass) {
						if (eCrossReference.eIsProxy()) {
							throw new RuntimeException(
									"Unresolved proxy '" + ((InternalEObject) eCrossReference).eProxyURI().toString()
											+ "' in " + EcoreUtil.getURI(ePackages.get(i)));
						}
						EPackage referencedEPackage = ((EClassifier) eCrossReference).getEPackage();
						if (ePackages.add(referencedEPackage) && referencedEPackage.eIsProxy()) {
							throw new RuntimeException(
									"Unresolved proxy '" + ((InternalEObject) referencedEPackage).eProxyURI().toString()
											+ "' in " + EcoreUtil.getURI(ePackages.get(i)));
						}
					}
				}
			}
		}
		return false;
	}
}
