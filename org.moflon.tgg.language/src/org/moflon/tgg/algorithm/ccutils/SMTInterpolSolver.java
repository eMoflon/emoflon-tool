package org.moflon.tgg.algorithm.ccutils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.smtinterpol.dimacs.DIMACSParser;
import de.uni_freiburg.informatik.ultimate.smtinterpol.smtlib2.SMTInterpol;

public class SMTInterpolSolver extends AbstractSATSolver {

	@Override
	public int[] solve(DimacFormat problem) {

		PrintStream originalOut = System.out;

		PrintStream stream;
		try {
			stream = new PrintStream("smtInterpolResults");
			System.setOut(stream);

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Logger log = Logger.getRootLogger();
		Appender appender;
		try {
			appender = new FileAppender(new SimpleLayout(), "smtInterpolResults");
			log.addAppender(appender);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Script s = new SMTInterpol(log, false);
		DIMACSParser parser = new DIMACSParser();

		parser.run(s, problem.getClausesAsFilePath());

		System.setOut(originalOut);

		return getResultFromFile();
	}

	private int[] getResultFromFile() {
		String[] strings = new String[0];
		try {
			BufferedReader br = new BufferedReader(new FileReader("smtInterpolResults"));
			String line = null;

			if (br.readLine().equals("s SATISFIABLE")) {
				System.out.println("Satisfiable");
			}
			br.readLine();
			String input = "";
			while ((line = br.readLine()).startsWith("v")) {
				input += line.substring(2) + " ";
			}
			strings = input.split(" ");
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int[] result = new int[strings.length - 1];

		for (int i = 0; i < strings.length - 1; i++) {
			result[i] = Integer.parseInt(strings[i]);
		}

		return result;
	}
}
