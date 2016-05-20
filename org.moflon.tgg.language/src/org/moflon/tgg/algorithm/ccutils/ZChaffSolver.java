package org.moflon.tgg.algorithm.ccutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ZChaffSolver extends AbstractSATSolver {

	int[] result;

	@Override
	public int[] solve(DimacFormat problem) {
		String[] params = new String[2];

		params[0] = "solvers\\zChaff";
		params[1] = problem.getClausesAsFilePath();

		result = new int[problem.getNumberOfLiterals()];

		try {
			final Process p = Runtime.getRuntime().exec(params);
			Thread thread = new Thread() {
				public void run() {
					String line;
					BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

					try {
						boolean nextLineAreVariables = false;
						while ((line = input.readLine()) != null) {
							if (line.equals("Instance Satisfiable")) {
								System.out.println("Satisfiable");
								nextLineAreVariables = true;
							} else if (nextLineAreVariables) {
								result = generateResultArrayFromString(line);
								nextLineAreVariables = false;
							}
						}
						input.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
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

	private int[] generateResultArrayFromString(String input) {

		String[] strings = input.split(" ");
		int[] result = new int[strings.length - 3];

		for (int i = 0; i < result.length; i++) {
			result[i] = Integer.parseInt(strings[i]);
		}
		return result;
	}
}
