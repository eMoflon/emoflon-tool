package org.moflon.maave.tests.testsuite.testcases;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class testRenamer {

	public static void main(String[] args) {
		File file= new File("F:/Workspaces/Eclipse/2014_12_05_ComputationalCathegoryTheory/CathegoryLanguageTestSuite/src/CathegoryLanguageTestSuite/tests/TestForSymbGraph2HODemoclesPattern_NAC.java");
		try {
			String str=FileUtils.readFileToString(file);
			StringBuffer strNew=new StringBuffer(str);
			int cnt=0;
			while(strNew.indexOf("TestFailed")!=-1){
				int index =strNew.indexOf("TestFailed");
				strNew.replace(index, index+10, "FailedAssert: "+cnt++);
				
				
			}
			System.out.println(strNew);
			
			File newFile= new File("F:/Workspaces/Eclipse/2014_12_05_ComputationalCathegoryTheory/CathegoryLanguageTestSuite/src/CathegoryLanguageTestSuite/tests/TestForSymbGraph2HODemoclesPattern_NAC2.java");
			FileUtils.writeStringToFile(newFile, strNew.toString());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
