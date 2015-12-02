package org.moflon.ide.metamodelevolution.core.processing;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.jdt.core.refactoring.descriptors.RenameJavaElementDescriptor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringContribution;
import org.eclipse.ltk.core.refactoring.RefactoringCore;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.metamodelevolution.core.RenameChange;

public class RenameClassRefactoring implements RenameRefactoring {

	private static final String IMPL_File = "Impl";

	@Override
	public void refactor(IProject project, RenameChange renameChange) {
		RefactoringContribution contribution = RefactoringCore
				.getRefactoringContribution(IJavaRefactorings.RENAME_COMPILATION_UNIT);
		RenameJavaElementDescriptor descriptor = (RenameJavaElementDescriptor) contribution.createDescriptor();
		descriptor.setUpdateReferences(true);
		descriptor.setProject(project.getName());
		descriptor.setNewName(renameChange.getCurrentValue());

		IFile file = project.getFile(new Path(GEN_FOLDER + getPackagePath(renameChange.getPackageName())
				+ renameChange.getPreviousValue() + JAVA_EXTENSION));
		ICompilationUnit cu = JavaCore.createCompilationUnitFrom(file);

		if (cu.exists())
			descriptor.setJavaElement(cu);
		else
			return;

		RefactoringStatus status = new RefactoringStatus();
		try {
			Refactoring refactoring = descriptor.createRefactoring(status);

			IProgressMonitor monitor = new NullProgressMonitor();
			refactoring.checkInitialConditions(monitor);
			refactoring.checkFinalConditions(monitor);

			Change change = refactoring.createChange(monitor);
			change.perform(monitor);

			refactorImplClass(project, renameChange);

//			processInjections(project, file, renameChange);
		} catch (CoreException e) {
			// TODO@settl: Return an appropriate status and/or log if
			// status.severity() == IStatus.ERROR
			e.printStackTrace();
		}
	}

	//TODO@rkluge: Go on here
	private void processInjections(IProject project, IFile javaFile, RenameChange renameChange) throws CoreException {
		IPath previousInjectionFilePath = WorkspaceHelper.getPathToInjection(javaFile);
		IPath newInjectionFilePath = WorkspaceHelper.getPathToInjection(javaFile);
		IFile injectionFile = project.getFile(previousInjectionFilePath);

		assert previousInjectionFilePath.lastSegment() == renameChange.getPreviousValue() + IMPL_File + ".java";

		injectionFile.move(newInjectionFilePath, true, new NullProgressMonitor());
	}

	/**
	 * This method renames the corresponding "Impl" files of the renamed class
	 */
	private void refactorImplClass(IProject project, RenameChange renameChange) {
		RefactoringContribution contribution = RefactoringCore
				.getRefactoringContribution(IJavaRefactorings.RENAME_COMPILATION_UNIT);
		RenameJavaElementDescriptor descriptor = (RenameJavaElementDescriptor) contribution.createDescriptor();
		descriptor.setUpdateReferences(true);
		descriptor.setProject(project.getName());
		descriptor.setNewName(renameChange.getCurrentValue() + IMPL_File);

		IFile file = project.getFile(new Path(GEN_FOLDER + getPackagePath(renameChange.getPackageName()) + "/impl/"
				+ renameChange.getPreviousValue() + IMPL_File + JAVA_EXTENSION));
		ICompilationUnit cu = JavaCore.createCompilationUnitFrom(file);

		if (cu.exists())
			descriptor.setJavaElement(cu);
		else
			return;

		RefactoringStatus status = new RefactoringStatus();
		try {
			Refactoring refactoring = descriptor.createRefactoring(status);

			IProgressMonitor monitor = new NullProgressMonitor();
			refactoring.checkInitialConditions(monitor);
			refactoring.checkFinalConditions(monitor);

			Change change = refactoring.createChange(monitor);
			change.perform(monitor);
		}

		catch (CoreException e) {
			// TODO@settl: Return an appropriate status and/or log if
			// status.severity() == IStatus.ERROR
			e.printStackTrace();
		}
	}

	// TODO@settl this is a nice candidate for the WorkspaceHelper
	private String getPackagePath(String packageName) {
		String packagePath = "/" + packageName.replaceAll("\\.", "/") + "/";
		return packagePath;
	}
}
