package org.moflon.tgg.algorithm.ccutils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class DimacFormatWriter {

	public DimacFormatWriter() {

	}

	public String writeDimacFile(int[][] clauses, int numberOfClauses, int numberOfLiterals) {
		try {
			PrintWriter writer = new PrintWriter("bin\\DimacProblem.cnf", "UTF-8");

			// writer.println("c");
			// writer.println("c created with DimacFormatWriter");
			// writer.println("c");
			// writer.println("c");
			writer.println("p cnf " + numberOfLiterals + " " + numberOfClauses);

			for (int i = 0; i < numberOfClauses; i++) {
				for (int j = 0; j < clauses[i].length; j++) {
					writer.print(clauses[i][j] + " ");
				}
				writer.println("0");
			}

			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "bin\\DimacProblem.cnf";
	}
}
