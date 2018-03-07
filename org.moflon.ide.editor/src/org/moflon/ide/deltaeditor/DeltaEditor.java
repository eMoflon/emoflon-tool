package org.moflon.ide.deltaeditor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;
import org.eclipse.ui.IEditorInput;
import org.moflon.core.utilities.LogUtils;
import org.moflon.tgg.algorithm.delta.AttributeDelta;
import org.moflon.tgg.algorithm.delta.Delta;
import org.moflon.tgg.algorithm.delta.OnlineChangeDetector;
import org.moflon.tgg.runtime.DeltaSpecification;
import org.moflon.tgg.runtime.EMoflonEdge;
import org.moflon.tgg.runtime.RuntimeFactory;

public class DeltaEditor extends EcoreEditor {
	private static final Logger logger = Logger.getLogger(DeltaEditor.class);

	private Delta delta;

	// The user is going to perform changes on this model
	private EObject originalModel;

	// The changes will be mapped to this unchanged model
	private EObject copiedModel;

	// Mapping is: originalModel ---> copiedModel
	private Copier copier;

	private Resource contentResource;

	private Boolean dirty = true;

	@Override
	public void createModel() {
		super.createModel();

		contentResource = editingDomain.getResourceSet().getResources().get(0);
		originalModel = contentResource.getContents().get(0);

		copier = new Copier();
		copiedModel = copier.copy(originalModel);
		copier.copyReferences();

		delta = new Delta();

		new OnlineChangeDetector(delta, originalModel);
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		createNewDeltaResource();
		dirty = false;
	}

	@Override
	protected void doSaveAs(URI uri, IEditorInput editorInput) {
		doSave(new NullProgressMonitor());
	}

	private void createNewDeltaResource() {
		DeltaSpecification deltaSpec = createSpecificationFromDelta();
		deltaSpec.setTargetModel(copiedModel);

		editingDomain.getResourceSet().getResources().remove(contentResource);

		Resource resourceForCopy = editingDomain.getResourceSet().createResource(contentResource.getURI());
		resourceForCopy.getContents().add(copiedModel);

		Resource deltaResource = editingDomain.getResourceSet()
				.createResource(contentResource.getURI().trimFileExtension().appendFileExtension("delta.xmi"));
		deltaResource.getContents().add(deltaSpec);

		try {
			HashMap<String, Object> saveOptions = new HashMap<String, Object>();
			saveOptions.put(XMLResource.OPTION_URI_HANDLER, new AbsolutePluginUriAllElseRelativeHandler());
			deltaResource.save(saveOptions);
		} catch (IOException e) {
			LogUtils.error(logger, e);
		}
	}

	private DeltaSpecification createSpecificationFromDelta() {
		OnlineChangeDetector.removeDeltaListeners(originalModel);

		DeltaSpecification spec = RuntimeFactory.eINSTANCE.createDeltaSpecification();
		spec.getAddedNodes().addAll(delta.getAddedNodes());
		spec.getAddedEdges().addAll(removeOpposites(mapEdgesToCopy(delta.getAddedEdges())));
		spec.getDeletedNodes().addAll(mapNodesToCopy(delta.getDeletedNodes()));
		spec.getDeletedEdges().addAll(removeOpposites(mapEdgesToCopy(delta.getDeletedEdges())));
		spec.getAttributeChanges().addAll(mapAttrDeltaToCopy(delta.getAttributeChanges()).stream().map(d -> d.toEMF())
				.filter(d -> d.getAffectedNode() != null).collect(Collectors.toList()));

		return spec;
	}

	private Collection<EMoflonEdge> removeOpposites(Collection<EMoflonEdge> allEdges) {
		ArrayList<EMoflonEdge> edges = new ArrayList<>(allEdges);
		ArrayList<EMoflonEdge> result = new ArrayList<>();

		for (int i = 0; i < edges.size(); i++) {
			boolean noOpposite = true;
			for (int j = i + 1; j < edges.size(); j++) {
				if (isOppositeOf(edges.get(i), edges.get(j))) {
					noOpposite = false;
					break;
				}
			}

			if (noOpposite)
				result.add(edges.get(i));
		}

		return result;
	}

	private boolean isOppositeOf(EMoflonEdge edge1, EMoflonEdge edge2) {
		return edge1.getSrc().equals(edge2.getTrg()) && edge1.getTrg().equals(edge2.getSrc())
				&& toReference(edge2).equals(toReference(edge1).getEOpposite());
	}

	private EReference toReference(EMoflonEdge edge) {
		return edge.getSrc().eClass().getEAllReferences().stream().filter(r -> r.getName().equals(edge.getName()))
				.findAny().get();
	}

	private Collection<AttributeDelta> mapAttrDeltaToCopy(Collection<AttributeDelta> attributeChanges) {
		return attributeChanges.stream().map(ac -> new AttributeDelta(ac.getAffectedAttribute(), ac.getOldValue(),
				ac.getNewValue(), copier.get(ac.getAffectedNode()))).collect(Collectors.toList());
	}

	private Collection<EObject> mapNodesToCopy(Collection<EObject> deletedNodes) {
		return deletedNodes.stream().map(copier::get).collect(Collectors.toList());
	}

	private Collection<EMoflonEdge> mapEdgesToCopy(Collection<EMoflonEdge> edges) {
		return edges.stream().map(ae -> {
			EMoflonEdge e = RuntimeFactory.eINSTANCE.createEMoflonEdge();
			e.setName(ae.getName());
			e.setSrc(copier.containsKey(ae.getSrc()) ? copier.get(ae.getSrc()) : ae.getSrc());
			e.setTrg(copier.containsKey(ae.getTrg()) ? copier.get(ae.getTrg()) : ae.getTrg());
			return e;
		}).collect(Collectors.toList());
	}
}

class AbsolutePluginUriAllElseRelativeHandler extends URIHandlerImpl {
	@Override
	public URI deresolve(URI uri) {
		return uri.isPlatformPlugin() ? uri : super.deresolve(uri);
	}
}