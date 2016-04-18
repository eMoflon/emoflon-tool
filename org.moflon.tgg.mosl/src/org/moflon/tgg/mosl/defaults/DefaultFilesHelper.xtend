package org.moflon.tgg.mosl.defaults

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
	
	static def generateDefaultRule(String schema, String ruleName){
		return '''
		#using «schema».*
		#using AttrCondDefLibrary.*
		
		#rule «ruleName» #with «schema»
		
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
	
	static def generateDefaultAttrCondDefLibrary(){
		return '''
		#library AttrCondDefLibrary {
		
			// Semantics:  0: == 1:
			eq(0: , 1: ) {
				#sync: BB, BF, FB
				#gen: BB, BF, FB, FF
			}
		
			addPrefix(0:EString, 1:EString, 2:EString) {
				#sync: BBB, BBF, BFB, FBB
				#gen: BBB, BBF, BFB, FBB, BFF, FBF
			}
		
			addSuffix(0:EString, 1:EString, 2:EString) {
				#sync: BBB, BBF, BFB, FBB
				#gen: BBB, BBF, BFB, FBB, BFF, FFF, FBF
			}
		
			concat(0:EString, 1:EString, 2:EString, 3:EString) {
				#sync: BBBB, BBBF, BBFB, BFFB, BFBB
				#gen: BBBB, BBBF, BBFB, BFFB, BFBB, BFFF, BFBF, BBFF
			}
		
			setDefaultString(0:EString, 1:EString) {
				#sync: BB, FB
				#gen: BB, FB, FF
			}
		
			setDefaultNumber(0:Number, 1:Number) {
				#sync: BB, FB
				#gen: BB, FB, FF
			}
		
			stringToDouble(0:EString, 1:EDouble) {
				#sync: BB, BF, FB
				#gen: BB, BF, FB, FF
			}
		
			stringToInt(0:EString, 1:EInt) {
				#sync: BB, BF, FB
				#gen: BB, BF, FB, FF
			}
		
			multiply(0:Number, 1:Number, 2:Number) {
				#sync: BBB, BBF, BFB, FBB
				#gen: BBB, BBF, BFB, FBB
			}
		
			divide(0:Number, 1:Number, 2:Number) {
				#sync: BBB, BBF, BFB, FBB
				#gen: BBB, BBF, BFB, FBB
			}
		
			add(0:Number, 1:Number, 2:Number) {
				#sync: BBB, BBF, BFB, FBB
				#gen: BBB, BBF, BFB, FBB, FFB, FBF, BFF
			}
		
			sub(0:Number, 1:Number, 2:Number) {
				#sync: BBB, BBF, BFB, FBB
				#gen: BBB, BBF, BFB, FBB, FFB, BFF, FBF, FFF
			}
		
			max(0:Number, 1:Number, 2:Number) {
				#sync: BBB, BBF, BFB, FBB
				#gen: BBB, BBF, BFB, FBB
			}
		
			smallerOrEqual(0:Number, 1:Number) {
				#sync: BB, BF, FB
				#gen: BB, BF, FB, FF
			}
		
		}
		'''
	}
}
