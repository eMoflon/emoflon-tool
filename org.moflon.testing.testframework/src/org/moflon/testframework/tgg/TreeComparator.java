package org.moflon.testframework.tgg;

import java.io.StringWriter;
import java.util.ArrayList;

import org.junit.Assert;
import org.moflon.core.moca.processing.CodeAdapter;
import org.moflon.core.moca.processing.ProcessingFactory;
import org.moflon.core.moca.processing.impl.CodeAdapterImpl;
import org.moflon.moca.MocaTreeSorter;
import org.moflon.testframework.tgg.xml.unparser.XmlUnparserAdapter;

public class TreeComparator {

	private static CodeAdapter codeAdapter;

	public static CodeAdapter getCodeAdapter() {
		if (codeAdapter == null) {
			// Register parsers and unparsers
			codeAdapter = ProcessingFactory.eINSTANCE.createCodeAdapter();
			codeAdapter.getUnparser().add(new XmlUnparserAdapter());
		}

		return codeAdapter;
	}

	public static void compare(final MocaTree.File out, final MocaTree.File expected) {
		String outAsXMLString = mocaFileToXMLString(out);
		Assert.assertNotEquals("", outAsXMLString);
		Assert.assertEquals(mocaFileToXMLString(expected), outAsXMLString);
	}

	public static boolean compareWithRetVal(final MocaTree.File out, final MocaTree.File expected) {
		String outAsXMLString = mocaFileToXMLString(out);
		return !outAsXMLString.equals("") && outAsXMLString.equals(mocaFileToXMLString(expected));
	}

	public static void compare(final MocaTree.Folder out, final MocaTree.Folder expected) {
		Assert.assertEquals(out.getName(), expected.getName());
		ArrayList<MocaTree.File> matchedExpectedFile = new ArrayList<MocaTree.File>();
		for (MocaTree.File outFile : out.getFile()) {
			MocaTree.File expectedFile = null;
			for (MocaTree.File file2 : expected.getFile()) {
				if (outFile.getName().equals(file2.getName()))
					expectedFile = file2;
			}
			Assert.assertNotNull(expectedFile);
			matchedExpectedFile.add(expectedFile);
			compare(outFile, expectedFile);
		}
		Assert.assertTrue(matchedExpectedFile.containsAll(expected.getFile()));

		ArrayList<MocaTree.Folder> matchedExpectedSubfolder = new ArrayList<MocaTree.Folder>();
		for (MocaTree.Folder subFolder : out.getSubFolder()) {
			MocaTree.Folder expectedSubFolder = null;
			for (MocaTree.Folder subFolder2 : expected.getSubFolder()) {
				if (subFolder.getName().equals(subFolder2.getName()))
					expectedSubFolder = subFolder2;
			}
			Assert.assertNotNull(expectedSubFolder);
			matchedExpectedSubfolder.add(expectedSubFolder);
			compare(subFolder, expectedSubFolder);
		}
		Assert.assertTrue(matchedExpectedSubfolder.containsAll(expected.getSubFolder()));
	}

	private static String mocaFileToXMLString(final MocaTree.File eFile) {
		org.moflon.core.moca.processing.impl.CodeAdapterImpl codeAdapterImpl = (CodeAdapterImpl) getCodeAdapter();

		MocaTreeSorter.sort(eFile);
		StringWriter stringWriter = new StringWriter();
		codeAdapterImpl.unparseFileToStream(eFile, stringWriter);

		return stringWriter.toString();
	}
}