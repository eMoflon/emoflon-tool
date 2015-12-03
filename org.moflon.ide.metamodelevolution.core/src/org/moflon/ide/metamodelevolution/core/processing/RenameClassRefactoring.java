package org.moflon.ide.metamodelevolution.core.processing;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
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
import org.moflon.ide.core.injection.JavaFileInjectionExtractor;
import org.moflon.ide.metamodelevolution.core.RenameChange;

public class RenameClassRefactoring implements RenameRefactoring {

	private static final String IMPL_File = "Impl";

	@Override
	public void refactor(IProject project, RenameChange renameChange) {
		refactorInterfaceClass(project, renameChange);
		refactorImplClass(project, renameChange);
	}

	private void refactorInterfaceClass(IProject project, RenameChange renameChange) {

		IFile file = project.getFile(new Path(GEN_FOLDER + getPackagePath(renameChange.getPackageName())
				+ renameChange.getPreviousValue() + JAVA_EXTENSION));
		ICompilationUnit cu = JavaCore.createCompilationUnitFrom(file);

		if (!cu.exists())
			return;

		RefactoringContribution contribution = RefactoringCore
				.getRefactoringContribution(IJavaRefactorings.RENAME_COMPILATION_UNIT);
		RenameJavaElementDescriptor descriptor = (RenameJavaElementDescriptor) contribution.createDescriptor();
		descriptor.setUpdateReferences(true);
		descriptor.setProject(project.getName());
		descriptor.setNewName(renameChange.getCurrentValue());
		descriptor.setJavaElement(cu);

		RefactoringStatus status = new RefactoringStatus();
		try {
			Refactoring refactoring = descriptor.createRefactoring(status);

			IProgressMonitor monitor = new NullProgressMonitor();
			refactoring.checkInitialConditions(monitor);
			refactoring.checkFinalConditions(monitor);

			Change change = refactoring.createChange(monitor);
			change.perform(monitor);

			processInjections(project, file, renameChange);
		} catch (CoreException e) {
			// TODO@settl: Return an appropriate status and/or log if
			// status.severity() == IStatus.ERROR
			e.printStackTrace();
			new Status(IStatus.ERROR, "", "Problem during refactoring", e);
		}
	}

	/**
	 * This method renames the corresponding "Impl" files of the renamed class
	 */
	private void refactorImplClass(IProject project, RenameChange renameChange) {

		IFile file = project.getFile(new Path(GEN_FOLDER + getPackagePath(renameChange.getPackageName()) + "/impl/"
				+ renameChange.getPreviousValue() + IMPL_File + JAVA_EXTENSION));
		ICompilationUnit cu = JavaCore.createCompilationUnitFrom(file);

		if (!cu.exists())
			return;

		RefactoringContribution contribution = RefactoringCore
				.getRefactoringContribution(IJavaRefactorings.RENAME_COMPILATION_UNIT);
		RenameJavaElementDescriptor descriptor = (RenameJavaElementDescriptor) contribution.createDescriptor();
		descriptor.setUpdateReferences(true);
		descriptor.setProject(project.getName());
		descriptor.setNewName(renameChange.getCurrentValue() + IMPL_File);
		descriptor.setJavaElement(cu);

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
			// status.severity() == IStatus.ERROR
			e.printStackTrace();
		}
	}

	//TODO@settl: I believe this is correct now, but this method may only handle renamings WITHIN one package! -> lastSegment
	/*
	 * Updates the injections for the given original JavaFile.
	 * 
	 * The original injection file is first renamed to give version control a chance to record that this is NOT a deletion of a versioned file and an addition of an unversioned file but rather a movement of a versioned file.
	 */
	private void processInjections(IProject project, IFile previousJavaFile, RenameChange renameChange) throws CoreException {
		
		// Rename injection
		IFile previousInjectionFile = project.getFile(WorkspaceHelper.getPathToInjection(previousJavaFile));
		if (previousInjectionFile.exists())
		{
			final String newLastSegmentOfInjectionFile = previousInjectionFile.getProjectRelativePath().lastSegment()
					.replace(renameChange.getPreviousValue(), renameChange.getCurrentValue());
			previousInjectionFile.move(Path.fromPortableString(newLastSegmentOfInjectionFile), true, new NullProgressMonitor());
		}
		
		// Overwrite injection with freshly extracted injections of the new Java file
		IFile newJavaFile = getCurrentJavaFile(project, previousJavaFile, renameChange);
		JavaFileInjectionExtractor extractor = new JavaFileInjectionExtractor();
		extractor.extractInjection(newJavaFile, false);
	}

	/*
	 * Determines the path of a Java file after performing the given RenameChange.
	 */
	private IFile getCurrentJavaFile(IProject project, IFile previousJavaFile, RenameChange renameChange) {
		final String newLastSegment = previousJavaFile.getProjectRelativePath().lastSegment().replace(renameChange.getPreviousValue(), renameChange.getCurrentValue());
		final IPath newJavaFilePath = previousJavaFile.getProjectRelativePath().removeLastSegments(1).append(newLastSegment);
		return project.getFile(newJavaFilePath);
	}

	// TODO@settl this is a nice candidate for the WorkspaceHelper
	private String getPackagePath(String packageName) {
		String packagePath = "/" + packageName.replaceAll("\\.", "/") + "/";
		return packagePath;
	}
}
