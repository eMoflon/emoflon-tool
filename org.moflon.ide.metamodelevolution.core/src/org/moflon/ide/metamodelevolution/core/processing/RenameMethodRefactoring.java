package org.moflon.ide.metamodelevolution.core.processing;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.jdt.core.refactoring.descriptors.RenameJavaElementDescriptor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringContribution;
import org.eclipse.ltk.core.refactoring.RefactoringCore;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.moflon.ide.core.injection.JavaFileInjectionExtractor;
import org.moflon.ide.metamodelevolution.core.RenameChange;

public class RenameMethodRefactoring implements RenameRefactoring {

	private static String FACTORY = "Factory";

	// TODO@settl: for all kind of methods
	@Override
	public void refactor(IProject project, RenameChange renameChange) {
		RefactoringContribution contribution = RefactoringCore
				.getRefactoringContribution(IJavaRefactorings.RENAME_METHOD);
		RenameJavaElementDescriptor descriptor = (RenameJavaElementDescriptor) contribution.createDescriptor();

		String packagePath = "/" + renameChange.getPackageName().replaceAll("\\.", "/") + "/";

		descriptor.setUpdateReferences(true);
		descriptor.setProject(project.getName());
		descriptor.setNewName("create" + renameChange.getCurrentValue());

		IFile file = project.getFile(new Path(
				GEN_FOLDER + packagePath + getFactoryClassName(renameChange.getPackageName()) + JAVA_EXTENSION));
		ICompilationUnit cu = JavaCore.createCompilationUnitFrom(file);

		if (cu.exists()) {
			IMethod method;
			try {
				method = findMethod(cu, "create" + renameChange.getPreviousValue());
				if (method != null)
					descriptor.setJavaElement(method);
				else
					return;
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		} else
			return;

		RefactoringStatus status = new RefactoringStatus();
		try {
			Refactoring refactoring = descriptor.createRefactoring(status);

			IProgressMonitor monitor = new NullProgressMonitor();
			refactoring.checkInitialConditions(monitor);
			refactoring.checkFinalConditions(monitor);

			Change change = refactoring.createChange(monitor);
			change.perform(monitor);

			processInjections(project, file, renameChange);
		}

		catch (CoreException e) {
			// TODO@settl: Return an appropriate status and/or log if
			// status.severity() == IStatus.ERROR (RK)
			e.printStackTrace();
		}

	}

	//TODO@settl: I have only added this without testing it becuase we currently only rename the factory methods
	private void processInjections(IProject project, IFile javaFile, RenameChange renameChange) {
		JavaFileInjectionExtractor extractor = new JavaFileInjectionExtractor();
		extractor.extractInjection(javaFile, false);
	}

	//TODO@settl: How to handle methods with identical name but different parameters?? (RK)
	private static IMethod findMethod(ICompilationUnit cu, String methodName) throws JavaModelException {
		try {
			IMethod[] methods = cu.findPrimaryType().getMethods();
			for (IMethod method : methods) {
				if (method.getElementName().equals(methodName))
					return method;
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getFactoryClassName(String packageName) {
		return packageName.substring(packageName.lastIndexOf(".") + 1) + FACTORY;
	}
}
