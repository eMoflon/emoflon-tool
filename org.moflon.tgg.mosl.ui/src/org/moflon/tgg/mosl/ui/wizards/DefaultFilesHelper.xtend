package org.moflon.tgg.mosl.ui.wizards

import org.moflon.core.utilities.MoflonUtil

class DefaultFilesHelper {

	val static l = "«"
	val static r = "»"

	static def generateDefaultEPackageForProject(String projectName) {
		return '''
		<?xml version="1.0" encoding="ASCII"?>
		<ecore:EPackage xmi:version="2.0" 
						xmlns:xmi="http://www.omg.org/XMI" 
						xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
						xmlns:ecore="http://www.eclipse.org/emf/2002/Ecore" 
						name="«MoflonUtil.lastSegmentOf(projectName)»" 
						nsURI="«MoflonUtil.getDefaultURIToEcoreFileInPlugin(projectName)»" 
						nsPrefix="«projectName»">
		</ecore:EPackage>		
		'''
	}

	static def generateDefaultSchema(String projectName){
		return '''
		// Add imports here
		
		«l»schema«r» «projectName»
			
		«l»source«r» {
			
		}
		
		«l»target«r» { 
			
		} 
		
		«l»correspondence«r» {
			
		}
		
		«l»attribute conditions«r» {
			
		}
		'''
	}
	
	static def generateDefaultRule(String projectName, String ruleName){
		return '''
		«l»using«r» «projectName».*
		«l»using«r» AttrCondDefLibrary.*
		
		«l»rule«r» «ruleName» «l»with«r» «projectName»
		
		«l»source«r» { 
			
		}
		
		«l»target«r» {
			
		}
		
		«l»correspondence«r» {
			
		}
		
		«l»attribute conditions«r» {
			
		}
		'''
	}
}
