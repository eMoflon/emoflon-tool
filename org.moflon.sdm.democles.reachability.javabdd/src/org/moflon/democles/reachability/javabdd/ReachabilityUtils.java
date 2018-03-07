package org.moflon.democles.reachability.javabdd;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.common.OperationRuntime;
import org.gervarro.democles.compiler.CompilerPatternBody;

public class ReachabilityUtils {
	/**
	 * Executes the given code with disabled stdout and stderr.
	 * 
	 * @param code
	 */
	public static void executeWithMutedStderrAndStdout(final Runnable code) {
		final PrintStream originalStdout = System.out;
		final PrintStream originalStderr = System.err;
		try {
			muteStdoutAndStderr();
			// The following code fragment tends to write to stdout and stderr
			code.run();
		} finally {
			System.setOut(originalStdout);
			System.setErr(originalStderr);
		}
	}

	/**
	 * Returns the set of operations to analyze for the given pattern body
	 * 
	 * @param pattern
	 * @return
	 */
	static List<GeneratorOperation> extractOperations(final CompilerPatternBody compilerPatternBody) {
		return compilerPatternBody.getOperations();
	}

	/**
	 * Returns whether the given operation is a 'check' operation.
	 * 
	 * Check operations have a precondition consisting only of
	 * {@link Adornment#BOUND}
	 * 
	 * @param operation
	 *            the operation to analyze
	 * @return whether the precondition adornment consists of
	 *         {@link Adornment#BOUND} only
	 */
	public static boolean isCheckOperation(final OperationRuntime operation) {
		final Adornment precondition = operation.getPrecondition();
		return isCheckOperation(precondition);
	}

	/**
	 * Returns whether the given adornment belongs to a 'check' operation.
	 * 
	 * Check operations have a precondition consisting only of
	 * {@link Adornment#BOUND}
	 * 
	 * @param operation
	 *            the operation to analyze
	 * @return whether the precondition adornment consists of
	 *         {@link Adornment#BOUND} only
	 */
	public static boolean isCheckOperation(final Adornment precondition) {
		return precondition.numberOfBound() + numberOfDontCare(precondition) == precondition.size();
	}

	private static int numberOfDontCare(Adornment precondition) {
		int count = 0;
		for (int a : precondition.getRawBindingInformation()) {
			if (a == ADORNMENT_UNDEFINED)
				count++;
		}
		return count;
	}

	private static void muteStdoutAndStderr() {
		final PrintStream mutedStream = new PrintStream(new OutputStream() {
			@Override
			public void write(int b) throws IOException { // nop
			}
		});
		System.setOut(mutedStream);
		System.setErr(mutedStream);
	}

	static final int ADORNMENT_UNDEFINED = -1;
}
