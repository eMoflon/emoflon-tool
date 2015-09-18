package org.moflon.ide.core.ea;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.moflon.ide.core.ea.exceptions.EnterpriseArchitectCommandLineException;

public class EnterpriseArchitectCommandLineParser {
	private Logger logger;
	
	private final String ERROR = "ERROR:";
	private final String EXCEPTION = "EXCEPTION:";
	private final String DEBUG = "DEBUG:";
	private final String INFO = "INFO:";
	
	private final String[] signalWords = {ERROR, EXCEPTION, DEBUG, INFO};
	
	public EnterpriseArchitectCommandLineParser(Logger log){
		logger = log;
	}
	
	public void parse(String clMessages){
		if(clMessages != null && !clMessages.isEmpty()){
			String lastSignal = "";
			String[] lines = clMessages.split("\r\n");
			for(String line : lines){
				if(line!=null && !line.isEmpty()){
					if(line.indexOf(":")!=-1){
						String signalWord = line.substring(0, line.indexOf(":")+1);
						if(Arrays.asList(signalWords).contains(signalWord)){
							printAtLogger(signalWord, line.substring(line.indexOf(":")+1));
							lastSignal=signalWord;
						}else{
							printAtLogger(lastSignal, line);
						}
					}else{
						printAtLogger(lastSignal, line);
					}
				}
			}
		}
	}

	private void printAtLogger(String signalWord, String restSequence) {
		switch (signalWord) {
		case ERROR:
			logger.error(restSequence);
			break;
		case EXCEPTION:
			logger.error("An Exception has been thrown", new EnterpriseArchitectCommandLineException(restSequence));
			break;
		case DEBUG:
			logger.debug(restSequence);
			break;
		case INFO:
			logger.info(restSequence);
			break;
		default:
			break;
		}
		
	}
}
