package org.moflon.tgg.algorithm.ccutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Mathsat5Solver extends AbstractSATSolver {

	int[] result;

	@Override
	public int[] solve(DimacFormat problem) {
		String[] params = new String[4];

		params[0] = "solvers\\mathsat";
		params[1] = "-input=dimacs";
		params[2] = "-model";
		params[3] = problem.getClausesAsFilePath();

		result = new int[problem.getNumberOfLiterals()];

		try {
			final Process p = Runtime.getRuntime().exec(params);
			Thread thread = new Thread() {
				public void run() {
					String line;
					BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

					try {
						int count = 0;
						while ((line = input.readLine()) != null) {
							if (line.equals("s SATISFIABLE")) {
								System.out.println("Satisfiable");
							} else if (line.startsWith("v")) {
								result[count] = getVariableFromString(line);
								count++;
							}
						}
						input.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			thread.start();
			int result = p.waitFor();
			thread.join();
			if (result != 0) {
				System.out.println("Process failed with status: " + result);
			}
		} catch (IOException e) {
			System.out.println(" procccess not read" + e);
		} catch (InterruptedException e) {
			System.out.println(" procccess interrupted" + e);
		}
		return result;
	}

	private int getVariableFromString(String line) {
		String[] splitArray = line.split(" ");
		if (splitArray[1].startsWith("-")) {
			return 0 - (Integer.parseInt(splitArray[1].substring(1)) + 1);
		} else {
			return Integer.parseInt(splitArray[1]) + 1;
		}
	};

}
