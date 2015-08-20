package org.moflon.visualization.ui.dialog;

public class RegexMatcher {

	private String regex = ".*";
	public RegexMatcher(String regex) {
		this.regex = regex;
	}

	public boolean match(String str) {
		try {			
			return str.matches(regex);
		}
		catch(Exception e) {
			return true;
		}
	}
}
