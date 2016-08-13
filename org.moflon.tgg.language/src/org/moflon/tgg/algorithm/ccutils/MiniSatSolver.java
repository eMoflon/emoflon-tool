package org.moflon.tgg.algorithm.ccutils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.moflon.core.utilities.LogUtils;

public class MiniSatSolver extends AbstractSATSolver {

   private static final Logger logger = Logger.getLogger(MiniSatSolver.class);

	@Override
	public int[] solve(DimacFormat problem) {
		String[] params = new String[3];

		params[0] = "solvers\\MiniSat";
		params[1] = problem.getClausesAsFilePath();
		params[2] = "minisatResult";

		try {
			final Process p = Runtime.getRuntime().exec(params);
			Thread thread = new Thread() {
				public void run() {
					String line;
					BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

					try {
						while ((line = input.readLine()) != null) {
							if (line.equals("SATISFIABLE")) {
								System.out.println("Satisfiable");
							}
						}
						input.close();
					} catch (IOException e) {
                  LogUtils.error(logger, e);
					}
				};
			};
			thread.start();
			int result = p.waitFor();
			thread.join();
			if (result != 0 && result != 10) {
				System.out.println("Process failed with status: " + result);
			}
		} catch (IOException e) {
			System.out.println(" procccess not read" + e);
		} catch (InterruptedException e) {
			System.out.println(" procccess interrupted" + e);
		}
		return getResultFromFile();
	}

	private int[] getResultFromFile() {
		String[] strings = new String[0];
		try {
			BufferedReader br = new BufferedReader(new FileReader("minisatResult"));
			String line = null;

			br.readLine();

			while ((line = br.readLine()) != null) {
				strings = line.split(" ");
			}
			br.close();
		} catch (IOException e) {
         LogUtils.error(logger, e);
		}

		int[] result = new int[strings.length - 1];

		for (int i = 0; i < strings.length - 1; i++) {
			result[i] = Integer.parseInt(strings[i]);
		}

		return result;
	}
}
