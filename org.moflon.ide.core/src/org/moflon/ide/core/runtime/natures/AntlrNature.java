package org.moflon.ide.core.runtime.natures;

import java.util.Arrays;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaCore;
import org.gervarro.eclipse.workspace.util.ProjectUtil;
import org.moflon.ide.core.CoreActivator;

public class AntlrNature extends ProjectConfiguratorNature {

	@Override
	public ICommand[] updateBuildSpecs(final IProjectDescription description, 
			ICommand[] buildSpecs, final boolean added) throws CoreException {
		if (added) {
			int javaBuilderPosition = ProjectUtil.indexOf(buildSpecs, JavaCore.BUILDER_ID);
			int antlrBuilderPosition = ProjectUtil.indexOf(buildSpecs, CoreActivator.ANTLR_BUILDER_ID);
			if (antlrBuilderPosition < 0) {
				final ICommand antlrBuilder = description.newCommand();
				antlrBuilder.setBuilderName(CoreActivator.ANTLR_BUILDER_ID);
				buildSpecs = Arrays.copyOf(buildSpecs, buildSpecs.length + 1);
				antlrBuilderPosition = buildSpecs.length - 1;
				buildSpecs[antlrBuilderPosition] = antlrBuilder;
			} 
			if (javaBuilderPosition < antlrBuilderPosition) {
				final ICommand antlrBuilder = buildSpecs[antlrBuilderPosition];
				System.arraycopy(buildSpecs, javaBuilderPosition, buildSpecs, javaBuilderPosition+1, antlrBuilderPosition-javaBuilderPosition);
				antlrBuilderPosition = javaBuilderPosition++;
				buildSpecs[antlrBuilderPosition] = antlrBuilder;
			}
		} else {
			int antlrBuilderPosition = ProjectUtil.indexOf(buildSpecs, CoreActivator.ANTLR_BUILDER_ID);
			if (antlrBuilderPosition >= 0) {
				buildSpecs = ProjectUtil.remove(buildSpecs, antlrBuilderPosition);
			}
		}
		return buildSpecs;
	}

	@Override
	public String[] updateNatureIDs(String[] natureIDs, final boolean added) throws CoreException {
		if (added) {
			if (ProjectUtil.indexOf(natureIDs, CoreActivator.ANTLR_NATURE_ID) < 0) {
				natureIDs = Arrays.copyOf(natureIDs, natureIDs.length + 1);
				natureIDs[natureIDs.length - 1] = CoreActivator.ANTLR_NATURE_ID;
			}
		} else {
			int antlrNaturePosition = ProjectUtil.indexOf(natureIDs, CoreActivator.ANTLR_NATURE_ID);
			if (antlrNaturePosition >= 0) {
				natureIDs = ProjectUtil.remove(natureIDs, antlrNaturePosition);
			}
		}
		return natureIDs;
	}

	// TODO@rkluge: Remove in a future release
//	@Override
//	public void configure() throws CoreException {
//		super.configure();
//
//		IJavaProject javaProject = JavaCore.create(getProject());
//		Collection<IClasspathEntry> classpathEntries = new HashSet<IClasspathEntry>();
//		classpathEntries.addAll(Arrays.asList(javaProject.getRawClasspath()));
//		IPath antlrPath = new Path(AntlrUtil.getAntrlPathUrl().toExternalForm().substring(5));
//		classpathEntries.add(JavaCore.newLibraryEntry(antlrPath, null, null, null,
//				new IClasspathAttribute[] { JavaCore.newClasspathAttribute("plugin_id", MoflonUtilitiesActivator.getDefault().getPluginId()) }, true));
//		javaProject.setRawClasspath(classpathEntries.toArray(new IClasspathEntry[] {}), null);
//	}
}
