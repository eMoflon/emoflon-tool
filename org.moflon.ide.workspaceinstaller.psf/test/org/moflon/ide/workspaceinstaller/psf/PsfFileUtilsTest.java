package org.moflon.ide.workspaceinstaller.psf;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Utility class for working with PSF files.
 */
public final class PsfFileUtilsTest {

	@Test(expected = IllegalArgumentException.class)
	public void testJoinPsfFiles_DisallowNull() throws Exception {
		PsfFileUtils.joinPsfFile(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testJoinPsfFiles_DisallowEmpty() throws Exception {
		PsfFileUtils.joinPsfFile(Arrays.asList());
	}

	@Test(expected = Exception.class)
	public void testJoinPsfFiles_NonExisting() throws Exception {
		PsfFileUtils.joinPsfFile(Arrays.asList(new File("foo/bar/bla.psf")));
	}

	@Test
	public void testJoinPsfFiles_SingleFile() throws IOException, CoreException {
		final File psfFile1 = new File("resources/psf/development/Documentation.psf");

		Assert.assertTrue(psfFile1.exists());

		final String expectedContent = FileUtils.readFileToString(psfFile1);
		final String actualContent = PsfFileUtils.joinPsfFile(Arrays.asList(psfFile1));

		Assert.assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testJoinPsfFiles_TwoFiles() throws IOException, CoreException {
		final File psfFile1 = new File("resources_for_test/psf/examples/DemoWorkspace_Part1.psf");
		final File psfFile2 = new File("resources_for_test/psf/examples/DemoWorkspace_Part2.psf");

		Assert.assertTrue(psfFile1.exists());
		Assert.assertTrue(psfFile2.exists());

		final String actualContent = PsfFileUtils.joinPsfFile(Arrays.asList(psfFile1, psfFile2));

		FileUtils.write(new File("./tmp.psf"), actualContent);

		Assert.assertTrue(actualContent
				.contains("1.0,https://github.com/eMoflon/emoflon-examples.git,master,demo/org.moflon.demo.testsuite"));
		Assert.assertTrue(actualContent
				.contains("1.0,https://github.com/eMoflon/emoflon-examples.git,master,demo/org.moflon.demo"));
	}
}
