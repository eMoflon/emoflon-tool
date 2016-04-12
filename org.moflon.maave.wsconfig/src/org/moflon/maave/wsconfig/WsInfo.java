package org.moflon.maave.wsconfig;

public class WsInfo {
	public static int getVerboseLevel() {
		String verboseLevelVar=System.getenv().get("MAaVeVerboseLevel");
		int verboseLevel=0;
		if(verboseLevelVar!=null ){
			try {
				verboseLevel= Integer.parseInt(verboseLevelVar);
			} catch (NumberFormatException e) {
				// TODO: handle exception
			}
			
			
		}
		return verboseLevel;
	}
	public static String getCurrentWsLoc()
   {
   
   return System.getenv().get("CurrentWSLoc");
   }
}
