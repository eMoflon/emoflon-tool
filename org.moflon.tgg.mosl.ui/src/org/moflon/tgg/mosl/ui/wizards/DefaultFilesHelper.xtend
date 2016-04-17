package org.moflon.tgg.mosl.ui.wizards

import org.moflon.core.utilities.MoflonUtil

class DefaultFilesHelper {

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
		
		#schema «projectName»
			
		#source {
			
		}
		
		#target { 
			
		} 
		
		#correspondence {
			
		}
		
		#attributeConditions {
			
		}
		'''
	}
	
	static def generateDefaultRule(String projectName, String ruleName){
		return '''
		#using «projectName».*
		#using AttrCondDefLibrary.*
		
		#rule «ruleName» #with «projectName»
		
		#source { 
			
		}
		
		#target {
			
		}
		
		#correspondence {
			
		}
		
		#attributeConditions {
			
		}
		'''
	}
}
