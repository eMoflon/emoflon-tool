package org.moflon.tgg.csp;

import org.moflon.tgg.language.csp.OperationalCSP;

public class CSPNotSolvableException extends RuntimeException {

	/**
	*
	*/
	private static final long serialVersionUID = 7841029920469575323L;
	OperationalCSP csp;

	public CSPNotSolvableException(OperationalCSP csp, String message) {
		super(message);
		this.csp = csp;
	}

	public OperationalCSP getCsp() {
		return csp;
	}

	public void setCsp(OperationalCSP csp) {
		this.csp = csp;
	}

}
