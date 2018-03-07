package org.moflon.ide.workspaceinstaller.psf;

import org.junit.Assert;
import org.junit.Test;

public class EMoflonStandardWorkspacesTest {
	@Test
	public void testExtractBasicWorkspaceName() throws Exception {
		Assert.assertEquals("abc", EMoflonStandardWorkspaces.extractBasicWorkspaceName("abc"));
		Assert.assertEquals("abc", EMoflonStandardWorkspaces.extractBasicWorkspaceName("abc@"));
		Assert.assertEquals("abc", EMoflonStandardWorkspaces.extractBasicWorkspaceName("abc@master"));
		Assert.assertEquals("abc", EMoflonStandardWorkspaces.extractBasicWorkspaceName("abc@master|master"));
	}

	@Test
	public void testExtractCustomBranchName() throws Exception {
		Assert.assertEquals(null, EMoflonStandardWorkspaces.extractCustomBranchName("abc"));
		Assert.assertEquals(null, EMoflonStandardWorkspaces.extractCustomBranchName("abc|"));
		Assert.assertEquals("master", EMoflonStandardWorkspaces.extractCustomBranchName("abc@master"));
		Assert.assertEquals("master|master", EMoflonStandardWorkspaces.extractCustomBranchName("abc@master|master"));
	}
}
