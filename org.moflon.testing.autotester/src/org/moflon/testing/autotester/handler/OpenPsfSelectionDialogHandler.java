package org.moflon.testing.autotester.handler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.core.utilities.WorkspaceHelper;

public class OpenPsfSelectionDialogHandler extends AbstractInstallCommandHandler {

	private static final String PSF_ROOT_FOLDER_NAME = "resources/psf/";

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		try {
			final List<File> collectedPsfFiles = collectPSFFiles();
			ListSelectionDialog dlg = new ListSelectionDialog(HandlerUtil.getActiveShell(event), "Root",
					new PsfFileContentProvider(collectedPsfFiles), new PsfFileLabelProvider(),
					"Select the PSF files to load:");
			dlg.setInitialSelections(new Object[] {});
			dlg.setTitle("Select PSF files");
			if (Dialog.OK == dlg.open()) {
				final List<File> selectedPsfFiles = new ArrayList<>();
				for (final Object file : dlg.getResult()) {
					selectedPsfFiles.add((File) file);
				}
				this.getWorkspaceController().installPsfFiles(selectedPsfFiles, "Selected PSFs");
			}
			return null;
		} catch (final CoreException | IOException e) {
			throw new ExecutionException("Failed to load PSF files", e);
		}
	}

	private List<File> collectPSFFiles() throws CoreException, IOException {
		final List<File> collectedPsfFiles = new ArrayList<>();
		final List<IProject> workspaceProjects = WorkspaceHelper.getAllProjectsInWorkspace();
		final List<IProject> psfProjects = workspaceProjects.stream().filter(p -> p.getName().endsWith(".psf"))
				.collect(Collectors.toList());
		if (!psfProjects.isEmpty()) {
			for (final IProject workspaceProject : psfProjects) {
				logger.debug("Using project " + workspaceProject.getName() + " in workspace to retrieve PSF files.");
				IFolder psfRootFolder = workspaceProject.getFolder(PSF_ROOT_FOLDER_NAME);
				PSFFileCollectingResourceVisitor psfFileCollector = new PSFFileCollectingResourceVisitor();
				psfRootFolder.accept(psfFileCollector);
				collectedPsfFiles.addAll(psfFileCollector.psfFiles);
			}
		} else {
			logger.debug("Using installed plugin to retrieve PSF files.");
			final URL fullPathURL = WorkspaceHelper.getPathRelToPlugIn(PSF_ROOT_FOLDER_NAME,
					getMoflonToolPsfFilesPluginId());
			if (fullPathURL != null) {
				final File psfRootFolder = new File(fullPathURL.getPath());
				Files.walkFileTree(psfRootFolder.toPath(), new SimpleFileVisitor<java.nio.file.Path>() {
					@Override
					public FileVisitResult visitFile(final java.nio.file.Path file, final BasicFileAttributes attrs)
							throws IOException {
						if (file.getFileName().toString().endsWith("psf")) {
							collectedPsfFiles.add(file.toFile());
						}
						return FileVisitResult.CONTINUE;
					}
				});
			}

		}
		return collectedPsfFiles;
	}

	private String getMoflonToolPsfFilesPluginId() {
		return "org.moflon.ide.workspaceinstaller.psf";
	}

	private final class PsfFileContentProvider implements IStructuredContentProvider {
		private List<File> collectedPsfFiles;

		public PsfFileContentProvider(List<File> collectedPsfFiles) {
			this.collectedPsfFiles = collectedPsfFiles;
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// nop
		}

		@Override
		public void dispose() {
			// nop
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return collectedPsfFiles.toArray();
		}
	}

	private final class PsfFileLabelProvider implements ILabelProvider {
		@Override
		public void removeListener(ILabelProviderListener listener) {
			// nop
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		@Override
		public void dispose() {
			// nop
		}

		@Override
		public void addListener(ILabelProviderListener listener) {
			// nop
		}

		@Override
		public String getText(Object element) {
			final File file = (File) element;
			return file.getName();
		}

		@Override
		public Image getImage(Object element) {
			return null; // no image to show
		}
	}

	public class PSFFileCollectingResourceVisitor implements IResourceVisitor {
		private List<File> psfFiles = new ArrayList<>();

		@Override
		public boolean visit(IResource resource) throws CoreException {
			IFile file = (IFile) resource.getAdapter(IFile.class);
			if (file != null && file.getName().endsWith(".psf")) {
				psfFiles.add(file.getLocation().toFile());
			}
			return true;
		}

	}
}
