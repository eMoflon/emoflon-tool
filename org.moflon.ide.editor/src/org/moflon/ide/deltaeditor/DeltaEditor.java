package org.moflon.ide.deltaeditor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.tgg.algorithm.delta.Delta;
import org.moflon.tgg.algorithm.delta.OnlineChangeDetector;

public class DeltaEditor extends EcoreEditor {
	private boolean deltaAlreadyCreated = false;

	protected List<Delta> deltas;
	private Delta delta;
	private EObject content;
	private Resource deltaResource;
	private Resource contentResource;

	public DeltaEditor() {
		super();
	}

	public void init(IEditorSite site, IEditorInput input) {
		super.init(site, input);
	}

	public void setFocus() {
		super.setFocus();
	}

	@Override
	/**
	 * clear all resources and reset to the initial state update deltas
	 * afterwards and delete all listeners
	 */
	public void dispose() {
		super.dispose();

		// restore old state
		try {
			editingDomain.getResourceSet().getResources().get(0).save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}

		updateDeltaResource();

		removeAllDeltaListeners();
	}

	@Override
	/**
	 * only called once when opening the resource initialize delta listeners and
	 * create copies of all resources to be able to reset them later
	 */
	public void createModel() {
		super.createModel();
		System.out.println("create model");
		convertToFileURI();
		findExistingDeltaResource();

		contentResource = editingDomain.getResourceSet().getResources().get(0);
		content = contentResource.getContents().get(0);
		if (!deltaAlreadyCreated) {
			delta = new Delta();
		}

		new OnlineChangeDetector(delta, content);
	}

	public void convertToFileURI() {
		for (Resource r : editingDomain.getResourceSet().getResources()) {
			if (r.getURI().isPlatform()) {
				URI currUri = r.getURI();
				if (currUri.isPlatform()) {
					String filePath = platformURIToPath(currUri);
					r.setURI(URI.createFileURI(filePath));
				}
			}
		}
	}

	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		updateDeltaResource();
		super.doSave(progressMonitor);
		System.out.println("doSave");
	}

	@Override
	protected void doSaveAs(URI uri, IEditorInput editorInput) {
		updateDeltaResource();
		super.doSaveAs(uri, editorInput);
		System.out.println("doSaveUri");
	}

	public void processDeltas() {

	}

	public void findExistingDeltaResource() {
		String filePath = null;
		URI currURI = editingDomain.getResourceSet().getResources().get(0).getURI();
		filePath = editingDomain.getResourceSet().getResources().get(0).getURI().toFileString();
		try {
			editingDomain.getResourceSet().getResources().add(editingDomain.getResourceSet().getResource(URI.createFileURI(filePath.replace(".xmi", "_delta.xmi")), true));
		} catch (Exception e) {
			Logger.getLogger("DeltaEditor").log(Level.INFO, "No file containing existing deltas were found. Please verify that the name of the delta file has not been changed if this is unintentional.");
			editingDomain.getResourceSet().getResources().remove(editingDomain.getResourceSet().getResources().size() - 1);
		}

		for (Resource r : editingDomain.getResourceSet().getResources()) {
			EObject extractedContent = r.getContents().get(0);
			if (extractedContent instanceof org.moflon.tgg.runtime.Delta) {
				delta = Delta.fromEMF((org.moflon.tgg.runtime.Delta) extractedContent);
				content = ((org.moflon.tgg.runtime.Delta) extractedContent).getTargetModel();
				deltaResource = r;
				contentResource = editingDomain.getResourceSet().getResources().get(0);
				deltaAlreadyCreated = true;
				break;
			}
		}
	}

	/**
	 * update old delta entries or create them
	 */
	public void updateDeltaResource() {
		if (deltaAlreadyCreated)
			updateExistingDeltaResource();
		else
			createNewDeltaResource();
	}

	public void updateExistingDeltaResource() {
		deltaResource.getContents().clear();
		deltaResource.getContents().add(delta.toEMF().preparePersistence());
	}

	public void createNewDeltaResource() {
		// set link to model and update new one
		if (!delta.isChangeDetected()) {
			return;
		}

		org.moflon.tgg.runtime.Delta emfDelta = delta.toEMF().preparePersistence();
		emfDelta.setTargetModel(content);

		String filePath = contentResource.getURI().toFileString();
		deltaResource = editingDomain.getResourceSet().createResource(URI.createFileURI(filePath.replace(".xmi", "_delta.xmi")));
		deltaResource.getContents().add(emfDelta);

		deltaAlreadyCreated = true;
	}

	private String platformURIToPath(URI currUri) {
		String filePath;
		String[] uriFragments = currUri.toString().split("/");
		String projectName = uriFragments[2];
		filePath = WorkspaceHelper.getProjectByName(projectName).getProject().getLocation().toString() + currUri.toString().substring(currUri.toString().indexOf(uriFragments[2]) + uriFragments[2].length());
		return filePath;
	}

	/**
	 * remove all delta listeners
	 */
	public void removeAllDeltaListeners() {
		removeDeltaListeners(content);
	}

	protected void removeDeltaListeners(final EObject root) {
		removeListenerFromNode(root);
		root.eAllContents().forEachRemaining(this::removeListenerFromNode);
	}

	protected void removeListenerFromNode(final EObject element) {
		List<Adapter> toBeRemoved = new ArrayList<>();
		element.eAdapters().forEach(adapter -> {
			if (adapter instanceof OnlineChangeDetector)
				toBeRemoved.add(adapter);
		});

		if (toBeRemoved != null)
			element.eAdapters().removeAll(toBeRemoved);
	}
}