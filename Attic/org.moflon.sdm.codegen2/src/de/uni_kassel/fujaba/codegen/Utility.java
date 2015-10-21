/*
 * The FUJABA ToolSuite project:
 *
 *   FUJABA is the acronym for 'From Uml to Java And Back Again'
 *   and originally aims to provide an environment for round-trip
 *   engineering using UML as visual programming language. During
 *   the last years, the environment has become a base for several
 *   research activities, e.g. distributed software, database
 *   systems, modelling mechanical and electrical systems and
 *   their simulation. Thus, the environment has become a project,
 *   where this source code is part of. Further details are avail-
 *   able via http://www.fujaba.de
 *
 *      Copyright (C) Fujaba Development Group
 *
 *   This library is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU Lesser General Public
 *   License as published by the Free Software Foundation; either
 *   version 2.1 of the License, or (at your option) any later version.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the Free
 *   Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 *   MA 02111-1307, USA or download the license under
 *   http://www.gnu.org/copyleft/lesser.html
 *
 * WARRANTY:
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 * Contact address:
 *
 *   Fujaba Management Board
 *   Software Engineering Group
 *   University of Paderborn
 *   Warburgerstr. 100
 *   D-33098 Paderborn
 *   Germany
 *
 *   URL  : http://www.fujaba.de
 *   email: info@fujaba.de
 *
 */
package de.uni_kassel.fujaba.codegen;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EClassifier;
import org.moflon.core.utilities.MoflonUtil;

import de.uni_kassel.fujaba.codegen.classdiag.ImportInformation;
import de.uni_kassel.fujaba.codegen.dlr.writer.FileDLRCodeWriter;
import de.uni_kassel.fujaba.codegen.emf.writer.RootPackageInformation;
import de.uni_kassel.fujaba.codegen.engine.CodeWritingEngine;
import de.uni_kassel.fujaba.codegen.rules.ExecuteStoryPatternOperation;
import de.uni_kassel.fujaba.codegen.rules.NegativeBlock;
import de.uni_kassel.fujaba.codegen.rules.SearchMultiLinkOperation;
import de.uni_kassel.fujaba.codegen.rules.SearchOperation;
import de.uni_kassel.fujaba.codegen.rules.Token;
import de.uni_kassel.fujaba.codegen.rules.engine.LocalVariableInformation;
import de.uni_paderborn.fujaba.metamodel.common.FCodeStyle;
import de.uni_paderborn.fujaba.metamodel.common.FConstraint;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.common.FStereotype;
import de.uni_paderborn.fujaba.metamodel.common.util.FProjectUtility;
import de.uni_paderborn.fujaba.metamodel.structure.FArray;
import de.uni_paderborn.fujaba.metamodel.structure.FBaseType;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FMethod;
import de.uni_paderborn.fujaba.metamodel.structure.FPackage;
import de.uni_paderborn.fujaba.metamodel.structure.FRole;
import de.uni_paderborn.fujaba.metamodel.structure.FType;
import de.uni_paderborn.fujaba.metamodel.structure.util.FClassDiagramUtility;
import de.uni_paderborn.fujaba.metamodel.structure.util.FClassUtility;
import de.uni_paderborn.fujaba.preferences.FujabaPreferencesManager;
import de.uni_paderborn.fujaba.uml.behavior.UMLActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLActivityDiagram;
import de.uni_paderborn.fujaba.uml.behavior.UMLAttrExprPair;
import de.uni_paderborn.fujaba.uml.behavior.UMLComplexState;
import de.uni_paderborn.fujaba.uml.behavior.UMLLink;
import de.uni_paderborn.fujaba.uml.behavior.UMLMultiLink;
import de.uni_paderborn.fujaba.uml.behavior.UMLObject;
import de.uni_paderborn.fujaba.uml.behavior.UMLStartActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLStoryActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLTransition;
import de.uni_paderborn.fujaba.uml.behavior.UMLTransitionGuard;
import de.uni_paderborn.fujaba.uml.common.UMLConstraint;
import de.uni_paderborn.fujaba.uml.structure.UMLMethod;
import de.uni_paderborn.fujaba.uml.structure.UMLParam;
import de.uni_paderborn.fujaba.uml.structure.UMLRole;
import de.upb.tools.fca.FHashMap;
import de.upb.tools.fca.IteratorConcatenation;
import de.upb.tools.sdm.JavaSDM;
import de.upb.tools.sdm.JavaSDMException;

public class Utility extends de.uni_paderborn.fujaba.basic.Utility
{
   /**
    * Defines a stereotype for class parameters.
    */
   private static final String PARAMETER = "parameter";

   // Util used in generated code
   public static final String eMoflonUtil = "org.moflon.core.utilities.eMoflonEMFUtil";

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   final protected String[] visibilityStrings = new String[] { "private", "public", "protected", "/*package*/", "/*user*/" };

   /**
    * FAttribute : 'progLangTypes : TreeMap (String,String) '
    */
   private Map<String, String> progLangTypes = null;

   private Map<String, String> progLangDefaults = null;

   /**
    * FAttribute : 'progLangTypes : TreeMap (String,String) '
    */
   private Map<String, String> progLangWrapperTypes = null;

   private static Map<UMLMethod, Integer> umlMethodToEOperationId = null;

   /**
    * Flag that controls the generation of instrumentation code for SDM tracing
    */
   private boolean sdmTracingActivated = false;

   /**
    * Flag that controls whether lists representing multi-valued refs should be shuffled before iteration
    */
   private boolean listShufflingActivated = false;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private static Utility util;

   /**
    * Counter value can be added to variable names to produce unique variable names (used for tracing)
    */
   private int counter = 0;

   // Bookkeeping for imports used in SDMCodegeneratorHelper
   public static Map<UMLMethod, Set<String>> methodToImports = new HashMap<UMLMethod, Set<String>>();

   // Used to resolve non standard factory names
   private static Map<String, String> packageNameToFactoryName = new HashMap<String, String>();

   // Corrections for package names supplied by user (external projects)
   private HashMap<String, String> importMappings = new HashMap<String, String>();

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   protected Utility()
   {
      init();
   }

   private void init()
   {
      progLangTypes = new FHashMap<String, String>();
      progLangTypes.put(FBaseType.INITIALIZER, "Initializer");
      progLangTypes.put(FBaseType.BOOLEAN, "boolean");
      progLangTypes.put(FBaseType.CHARACTER, "char");
      progLangTypes.put(FBaseType.STRING, "String");
      progLangTypes.put(FBaseType.INTEGER, "int");
      progLangTypes.put(FBaseType.BYTE, "byte");
      progLangTypes.put(FBaseType.SHORT_INTEGER, "short");
      progLangTypes.put(FBaseType.LONG_INTEGER, "long");
      progLangTypes.put(FBaseType.FLOAT, "float");
      progLangTypes.put(FBaseType.DOUBLE, "double");
      progLangTypes.put(FBaseType.VOID, "void");
      progLangTypes.put(FBaseType.CONSTRUCTOR, "");
      progLangTypes.put("Constructor", "");

      progLangTypes.put("BooleanArray", "boolean[]");
      progLangTypes.put("CharacterArray", "char[]");
      progLangTypes.put("StringArray", "String[]");
      progLangTypes.put("IntegerArray", "int[]");
      progLangTypes.put("ByteArray", "byte[]");
      progLangTypes.put("ShortIntegerArray", "short[]");
      progLangTypes.put("LongIntegerArray", "long[]");
      progLangTypes.put("FloatArray", "float[]");
      progLangTypes.put("DoubleArray", "double[]");
      progLangTypes.put("ObjectArray", "Object[]");

      progLangDefaults = new FHashMap<String, String>();
      progLangDefaults.put(FBaseType.BOOLEAN, "false");
      progLangDefaults.put(FBaseType.CHARACTER, "\'\'");
      progLangDefaults.put(FBaseType.STRING, "null");
      progLangDefaults.put(FBaseType.INTEGER, "0");
      progLangDefaults.put(FBaseType.BYTE, "0");
      progLangDefaults.put(FBaseType.SHORT_INTEGER, "0");
      progLangDefaults.put(FBaseType.LONG_INTEGER, "0");
      progLangDefaults.put(FBaseType.FLOAT, "0.0f");
      progLangDefaults.put(FBaseType.DOUBLE, "0.0");
      progLangDefaults.put("BooleanArray", "new boolean[0]");
      progLangDefaults.put("CharacterArray", "new char[0]");
      progLangDefaults.put("StringArray", "new  String[0]");
      progLangDefaults.put("IntegerArray", "new int[0]");
      progLangDefaults.put("ByteArray", "new  byte[0]");
      progLangDefaults.put("ShortIntegerArray", "new short[0]");
      progLangDefaults.put("LongIntegerArray", "new long[0]");
      progLangDefaults.put("FloatArray", "new float[0]");
      progLangDefaults.put("DoubleArray", "new double[0]");
      progLangDefaults.put("ObjectArray", "new Object[0]");

      progLangWrapperTypes = new FHashMap<String, String>();
      progLangWrapperTypes.put(FBaseType.BOOLEAN, Boolean.class.getSimpleName());
      progLangWrapperTypes.put(FBaseType.CHARACTER, Character.class.getSimpleName());
      progLangWrapperTypes.put(FBaseType.STRING, String.class.getSimpleName());
      progLangWrapperTypes.put(FBaseType.INTEGER, Integer.class.getSimpleName());
      progLangWrapperTypes.put(FBaseType.BYTE, Byte.class.getSimpleName());
      progLangWrapperTypes.put(FBaseType.SHORT_INTEGER, Short.class.getSimpleName());
      progLangWrapperTypes.put(FBaseType.LONG_INTEGER, Long.class.getSimpleName());
      progLangWrapperTypes.put(FBaseType.FLOAT, Float.class.getSimpleName());
      progLangWrapperTypes.put(FBaseType.DOUBLE, Double.class.getSimpleName());
      progLangWrapperTypes.put(FBaseType.VOID, Void.class.getSimpleName());

      methodToImports = new HashMap<UMLMethod, Set<String>>();
      counter = 0;

      umlMethodToEOperationId = new HashMap<UMLMethod, Integer>();

      packageNameToFactoryName = new HashMap<String, String>();
   }

   public void reset()
   {
      init();
   }

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    * 
    * @return No description provided
    */
   public static Utility get()
   {
      if (util == null)
      {
         util = new Utility();
      }
      return util;
   }

   /**
    * Get the interface attribute of the Utility object
    * 
    * @param type
    *           No description provided
    * @return The interface value
    */
   public boolean isInterface(FClass type)
   {
      if (type == null)
      {
         return false;
      }
      return type.hasKeyInStereotypes(FStereotype.INTERFACE);
   }

   public boolean isJavaBean(FClass type)
   {
      if (type == null)
      {
         return false;
      }
      if (type.hasKeyInStereotypes(FStereotype.JAVA_BEAN))
      {
         return true;
      } else
      {
         FPackage declaredIn = type.getDeclaredInPackage();
         while (declaredIn != null)
         {
            if (declaredIn.hasKeyInStereotypes(FStereotype.JAVA_BEAN))
            {
               return true;
            }
            declaredIn = declaredIn.getParent();
         }
         return false;
      }
   }

   public boolean isDerivedfrom(FClass cls, String superClsName)
   {
      return FClassDiagramUtility.isDerivedfrom(cls, superClsName);
   }

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    * 
    * @param s
    *           No description provided
    * @return No description provided
    */
   public static String nullIfEmpty(String s)
   {
      if ((s == null) || (s.trim().length() == 0))
      {
         return null;
      }
      return s;
   }

   /**
    * Get the progLangType attribute of the CodeGenVisitor object
    * 
    * @param baseType
    *           No description provided
    * @return The progLangType value
    */
   public final String getProgLangType(FBaseType baseType)
   {
      return progLangTypes.get(baseType.getName());
   }

   /**
    * Get the name of the programming language's wrapper type that corresponds to the given {@link FBaseType}.
    * 
    * @param baseType
    *           a {@link FBaseType}
    * @return The name of the programming language's base type that corresponds to the given {@link FBaseType}.
    */
   public final String getProgLangWrapperType(FType baseType)
   {
      if (baseType == null)
      {
         return null;
      }
      final String wrapperName;
      if (baseType instanceof FBaseType)
      {
         wrapperName = progLangWrapperTypes.get(baseType.getName());
      } else
      {
         wrapperName = null;
      }
      if (wrapperName != null)
      {
         return wrapperName;
      } else
      {
         return baseType.getName();
      }
   }

   public final String getBaseTypeFromJavaType(String javaTypeName)
   {
      if (javaTypeName.length() == 0)
      {
         return "";
      }
      for (Entry entry : progLangTypes.entrySet())
      {
         if (javaTypeName.equals(entry.getValue()))
         {
            return (String) entry.getKey();
         }
      }
      return javaTypeName;
   }

   /**
    * Get the uMLTypeAsString attribute of the OOGenVisitor object
    * 
    * @param theType
    *           No description provided
    * @return The uMLTypeAsString value
    */
   public String getTypeAsString(FType theType)
   {
      if (theType == null)
      {
         return "type_is_missing";
      }
      String typeString;
      if (theType instanceof FBaseType)
      {
         typeString = getProgLangType((FBaseType) theType);
         if (typeString == null)
         {
            // If the name is unknown by the utility, assume the modeled name.
            typeString = theType.getName();
         }
      } else if (theType instanceof FArray)
      {
         typeString = getTypeAsString(((FArray) theType).getArrayType()) + "[]";
      } else
      {
         if (theType instanceof FClass)
         {
            FClass theClass = (FClass) theType;
            typeString = theClass.getName();

            typeString = handleTypeClashes(typeString, theClass);

            // don't qualify parameters
            if (!theClass.hasKeyInStereotypes(PARAMETER))
            {
               theClass = theClass.getDeclaredInClass();
               while (theClass != null)
               {
                  typeString = theClass.getName() + "." + typeString;
                  theClass = theClass.getDeclaredInClass();
               }
            }
         } else
         {
            typeString = theType.getName();
         }
      }
      return typeString;
   }

   public String handlePluralization(String roleName, Object o, boolean toOne)
   {
      // Skip if roleName is a reverse link for Fujaba
      if (isOriginallyNotNavigable(roleName) || isOriginallyNavigableViaComposite(roleName))
         return roleName;

      // Handle all other cases
      if (o instanceof FClass)
      {
         FClass theType = (FClass) o;
         String packageName = theType.getDeclaredInPackage().getFullPackageName();

         // Handle special cases
         if (MoflonUtil.toBePluralized(roleName, packageName, theType.getName(), toOne))
         {
            roleName = pluralize(roleName);
         }
      }

      return roleName;
   }
   

   public String handleTypeClashes(String typeString, Object o)
   {
      // Simple Java cases
      ArrayList<String> javaTypes = new ArrayList<>();
      javaTypes.add("Package");
      javaTypes.add("Class");
      javaTypes.add("Process");

      String fullPackageName = null;
      if (o instanceof FClass)
      {
         FClass theType = (FClass) o;

         if (javaTypes.contains(typeString))
         {
            fullPackageName = theType.getDeclaredInPackage().getFullPackageName();
            typeString = correctNameWithMappingFromUser(fullPackageName) + "." + typeString;
         }
      } else if (o instanceof EClassifier)
      {
         EClassifier eclassifier = (EClassifier) o;
         typeString = MoflonUtil.getFQN(eclassifier);
      }

      return typeString;
   }

   private String correctNameWithMappingFromUser(String packageName)
   {
      if (importMappings.containsKey(packageName))
         return importMappings.get(packageName);

      return packageName;
   }

   public boolean isArrayType(FType theType)
   {
      if (theType instanceof FBaseType)
      {
         return theType.getName().contains("Array");
      } else
         return theType instanceof FArray;
   }

   public String getEMFTypeAsString(FType theType, CodeWritingEngine engine, boolean addTypeDescriptor)
   {
      // check, if a type descriptor is required and a FClass is the type
      if (addTypeDescriptor && theType instanceof FClass)
      {
         FClass theClass = (FClass) theType;
         String typeDescriptor = "";

         // select type descriptor based on reference-steteroptype
         if (theClass.hasKeyInStereotypes(FStereotype.REFERENCE))
         {
            typeDescriptor = "ecore:EDataType";
         } else
         {
            typeDescriptor = "ecore:EClass";
         }

         return typeDescriptor + " " + getEMFTypeAsString(theType, engine);
      } else
      {
         return getEMFTypeAsString(theType, engine);
      }
   }

   public String getEMFTypeAsString(FType theType, CodeWritingEngine engine)
   {

      if (theType == null)
         return null;

      if (FBaseType.VOID.equals(theType.getName()))
      {
         return null;
      }
      if (theType instanceof FBaseType)
      {
         String type = getTypeAsString(theType);
         if (isArrayType(theType))
         {
            type = type.replace("[]", "");
            return "ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//E" + upFirstChar(type);
         } else
         {
            return "ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//E" + upFirstChar(type);
         }
      } else if (theType instanceof FArray)
      {
         String type = getTypeAsString(((FArray) theType).getArrayType());
         return "ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//E" + upFirstChar(type);
      } else if (theType instanceof FClass)
      {
         FClass theClass = (FClass) theType;
         FCodeStyle codeStyle = theClass.getInheritedCodeStyle();
         if (theClass.hasKeyInStereotypes(FStereotype.REFERENCE) || codeStyle == null || !"emf".equals(codeStyle.getName()))
         {
            ImportInformation imports = (ImportInformation) engine.getFromInformation("imports");
            imports.addToImports(theClass.getFullClassName());
            return "#//" + theClass.getName();
         } else
         {
            RootPackageInformation rootInfo = (RootPackageInformation) engine.getFromInformation("rootPackages");
            if (rootInfo.sizeOfRootPackages() == 0)
            {
               throw new IllegalStateException("emf root packages not initialized");
            }
            final FProject currentProject = ((FPackage) rootInfo.iteratorOfRootPackages().next()).getProject();
            File referencedFile;
            final FProject projectOfClass = theClass.getProject();
            if (projectOfClass != currentProject)
            {
               rootInfo = new RootPackageInformation();
               rootInfo.calcRootPackages(projectOfClass);

               referencedFile = new File(FujabaPreferencesManager.getExportFolder(projectOfClass));
               referencedFile = new File(referencedFile, "models");
            } else
            {
               referencedFile = null;
            }

            StringBuilder name = new StringBuilder(theClass.getName());
            FPackage pkg = theClass.getDeclaredInPackage();

            while (pkg != null && !rootInfo.hasInRootPackages(pkg))
            {
               if (pkg.getName().length() > 0)
               {
                  name.insert(0, ".");
                  name.insert(0, pkg.getName());
               }
               pkg = pkg.getParent();
            }
            String filePrefix;
            if (referencedFile != null && pkg != null)
            {
               referencedFile = new File(referencedFile, projectOfClass.getName() + "_" + pkg.getFullPackageName() + ".ecore");
               filePrefix = referencedFile.toURI().toString();
            } else
            {
               filePrefix = "";
            }

            final String packageName = name.toString();
            return filePrefix + "#//" + packageToPath(packageName);
         }
      }
      return "#//" + packageToPath(theType.getName());
   }

   private String packageNameFromRootPackage(FClass theClass, CodeWritingEngine engine)
   {
      StringBuilder name = new StringBuilder(theClass.getName());
      FPackage pkg = theClass.getDeclaredInPackage();
      RootPackageInformation rootInfo = (RootPackageInformation) engine.getFromInformation("rootPackages");
      while (pkg != null && !rootInfo.hasInRootPackages(pkg))
      {
         name.insert(0, ".");
         name.insert(0, pkg.getName());
         pkg = pkg.getParent();
      }
      return name.toString();
   }

   public String packageToPath(String packageName)
   {
      return packageName.replaceAll("\\.", "/");
   }

   /**
    * Get the uMLTypeAsString attribute of the OOGenVisitor object
    * 
    * @param theType
    *           No description provided
    * @return The uMLTypeAsString value
    */
   public String getDefaultAsString(FType theType)
   {
      if (theType == null)
      {
         return "type_is_missing";
      }
      // String typeString;
      if (theType instanceof FBaseType)
      {
         return progLangDefaults.get(theType.getName());
      } else if (theType instanceof FArray)
      {
         return "{}";
      } else
      {
         return "null";
      }
   }

   /**
    * @param type
    *           type of interest
    * @return type instanceof FBaseType
    */
   public boolean isBaseType(FType type)
   {
      return type instanceof FBaseType;
   }

   /**
    * Get the visibilityString attribute of the CodeGenVisitor object
    * 
    * @param visibility
    *           No description provided
    * @return The visibilityString value
    */
   public final String getVisibilityString(int visibility)
   {
      return visibilityStrings[visibility];
   }

   public boolean isOrdered(FRole umlRole)
   {
      if (umlRole == null)
      {
         return false;
      }
      Iterator iter = umlRole.iteratorOfConstraints();
      iter = new IteratorConcatenation(umlRole.getAssoc().iteratorOfConstraints(), iter);
      while (iter.hasNext())
      {
         FConstraint tmpConst = (FConstraint) iter.next();
         String constraint = tmpConst.getText();

         if (constraint != null)
         {
            constraint = constraint.toLowerCase();
            if (constraint.equals("ordered"))
            {
               return true;
            }
         }
      }
      return false;
   }

   public boolean isSorted(FRole umlRole)
   {
      Iterator iter = umlRole.iteratorOfConstraints();
      iter = new IteratorConcatenation(umlRole.getAssoc().iteratorOfConstraints(), iter);
      while (iter.hasNext())
      {
         FConstraint tmpConst = (FConstraint) iter.next();
         String constraint = tmpConst.getText();

         if (constraint != null)
         {
            constraint = constraint.toLowerCase();
            if (constraint.equals("sorted"))
            {
               return true;
            }
         }
      }
      return false;
   }

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    * 
    * @param indent
    *           No description provided
    * @param code
    *           No description provided
    * @return No description provided
    */
   public String indent(String indent, String code)
   {
      if ((code != null) && (code.length() > 0))
      {
         StringBuffer sb = new StringBuffer();
         StringTokenizer tokens = new StringTokenizer(code, "\n", true);
         while (tokens.hasMoreTokens())
         {
            String line = tokens.nextToken();
            /*
             * Pattern pattern = Pattern.compile(FileDLRCodeWriter.DLR_COMMENT_PATTERN_STRING); Matcher matcher =
             * pattern.matcher(line); StringBuffer tmpSb = new StringBuffer(); while (matcher.find()) {
             * matcher.appendReplacement(tmpSb, ""); } String head = tmpSb.toString(); matcher.appendTail(tmpSb); String
             * tmpLine = tmpSb.toString(); int size = line.length() - (tmpLine.length() - head.length());
             * sb.append(line.substring(0, size));
             */
            String tmpLine = line.replaceAll(FileDLRCodeWriter.DLR_COMMENT_PATTERN_STRING, "");
            // if (! (line.equals ("\n") || line.equals ("\r")))
            if (!tmpLine.trim().equals(""))
            {
               sb.append(indent);
            }

            sb.append(line);
            // sb.append (line.substring(size));
         }
         return sb.toString();
      }
      return code;
   }

   /**
    * This methods analyses whether an activity diagram contains a story pattern and thus needs declarations for the
    * local variables fujaba__success and tmpObject. FIX ME: This is needed as a hot bug fix for codegen2 for generated
    * Junit test methods that start with a call to super(name)
    */
   public boolean needs_Success_And_TmpObject_VarDecl(Object elem)
   {
      boolean result = false;

      if (elem instanceof UMLActivityDiagram)
      {
         UMLActivityDiagram diag = (UMLActivityDiagram) elem;

         Iterator iter = diag.iteratorOfElements();
         while (!result && iter.hasNext())
         {
            Object diagElem = iter.next();

            if (diagElem instanceof UMLStoryActivity)
            {
               result = true;
            }
         }
      } else if (elem instanceof UMLComplexState)
      {
         result = ((UMLComplexState) elem).getActivityDiagram() != null;
      }
      return result;
   }

   /**
    * Get the initial attribute of the Utility object
    * 
    * @param state
    *           No description provided
    * @return The initial value
    */
   public boolean isInitial(UMLComplexState state)
   {
      Iterator iter = state.iteratorOfEntry();
      while (iter.hasNext())
      {
         UMLTransition trans = (UMLTransition) iter.next();
         UMLActivity act = trans.getRevExit();
         if (act instanceof UMLStartActivity)
         {
            return true;
         }
      }
      return false;
   }

   public String getMethodName(String msg)
   {
      int pos = msg.indexOf('(');
      if (pos >= 0)
      {
         return msg.substring(0, pos);
      } else
      {
         return msg;
      }
   }

   public String extendMethodName(String msg, String ext)
   {
      // turn mmm into extMmm
      // turn CCC.mmm into CCC.extMmm
      StringBuffer result = new StringBuffer();
      int pos = msg.indexOf('.');
      if (pos >= 0)
      {
         result.append(msg.substring(0, pos + 1));
      }
      result.append(ext);
      String secondPart = upFirstChar(msg.substring(pos + 1, msg.length()));
      result.append(secondPart);

      return result.toString();
   }

   public String getParams(String msg)
   {
      return getParamsWithPrefix(", ", msg);
   }

   public String getParamsWithPrefix(String prefix, String msg)
   {
      int startPos = msg.indexOf('(');
      int endPos = msg.lastIndexOf(')');
      if (startPos >= 0)
      {
         return prefix + msg.substring(startPos + 1, endPos);
      } else
      {
         return "";
      }
   }

   /**
    * @param modelElement
    *           some FElement
    * @param key
    *           property ID (see {@link FProject#addToProperties})
    * @return modelElement.getProject().getFromProperties( key )
    */
   public String getProperty(FElement modelElement, String key)
   {
      if (modelElement != null)
      {
         return modelElement.getProject().getFromProperties(key);
      } else
      {
         return null;
      }
   }

   public FClass getFClassForName(String name, FElement context)
   {
      FProject project = context.getProject();
      return FProjectUtility.findClass(project, name);
   }

   /**
    * Convert a given string into a string that consists of uppercase letters only. Insert an underscore before each
    * uppercase letter that is followed by a lowercase letter. No underscore is inserted before the last character of
    * the given string.
    * 
    * @param camelCaseText
    *           A string that consists of upper and lowercase letters.
    * @return A string that consists of uppercase letters only. This string may contain additional underscores, so its
    *         length may be greater or equal to the length of the given string.
    */
   public String camelToUpperCase(String camelCaseText)
   {
      StringBuilder result = new StringBuilder();
      char[] chars = camelCaseText.toCharArray();
      for (int i = 0; i < chars.length; i++)
      {
         char c = chars[i];
         if (Character.isUpperCase(c) && result.length() > 0)
         {
            boolean nextIsUpperCaseToo = chars.length == i + 1 || Character.isUpperCase(chars[i + 1]);
            if (!nextIsUpperCaseToo)
            {
               // if two uppercase in a row we make only one underscore
               result.append('_');
            }
            result.append(c);
         } else
         {
            result.append(Character.toUpperCase(c));
         }
      }
      return result.toString();
   }

   /**
    * Copied from org.eclipse.uml2.codegen.ecore.Generator to match UML2 pluralized getters.
    * 
    * @param name
    *           name of the role
    * @return pluralized name of the role
    */
   public String pluralize(String name)
   {
      if (name == null)
      {
         return null;
      }
      if (name.equalsIgnoreCase("children") || name.endsWith("Children"))
      { //$NON-NLS-1$ //$NON-NLS-2$
         return name;
      } else if (name.equalsIgnoreCase("child") || name.endsWith("Child"))
      { //$NON-NLS-1$ //$NON-NLS-2$
         return name + "ren"; //$NON-NLS-1$
      } else if (name.equalsIgnoreCase("data") || name.endsWith("Data"))
      { //$NON-NLS-1$ //$NON-NLS-2$
         return name;
      } else if (name.equalsIgnoreCase("datum") || name.endsWith("Datum"))
      { //$NON-NLS-1$ //$NON-NLS-2$
         return name.substring(0, name.length() - 2) + "a"; //$NON-NLS-1$
      } else if (name.endsWith("By"))
      { //$NON-NLS-1$
         return name + "s"; //$NON-NLS-1$
      } else if (name.endsWith("y"))
      { //$NON-NLS-1$
         return name.substring(0, name.length() - 1) + "ies"; //$NON-NLS-1$
      } else if (name.endsWith("ex"))
      { //$NON-NLS-1$
         return name.substring(0, name.length() - 2) + "ices"; //$NON-NLS-1$
      } else if (name.endsWith("x"))
      { //$NON-NLS-1$
         return name + "es"; //$NON-NLS-1$
      } else if (name.endsWith("us"))
      { //$NON-NLS-1$
         return name.substring(0, name.length() - 2) + "i"; //$NON-NLS-1$
      } else if (name.endsWith("ss"))
      { //$NON-NLS-1$
         return name + "es"; //$NON-NLS-1$
      } else if (name.endsWith("s"))
      { //$NON-NLS-1$
         return name;
      } else
      {
         return name + "s"; //$NON-NLS-1$
      }
   }

   public boolean isAssignableFrom(FClass thisClass, FClass otherClass)
   {
      return FClassUtility.isAssignableFrom(thisClass, otherClass);
   }

   public Class<?> classForName(String className) throws ClassNotFoundException
   {
      return classForName(className, null);
   }

   public Class<?> classForName(String className, Object context) throws ClassNotFoundException
   {
      ClassLoader loader;
      if (context != null)
      {
         loader = context.getClass().getClassLoader();
      } else
      {
         loader = Thread.currentThread().getContextClassLoader();
         if (loader == null)
         {
            loader = this.getClass().getClassLoader();
         }
      }
      Class<?> cls;
      try
      {
         cls = loader.loadClass(className);
      } catch (ClassNotFoundException ex)
      {
         cls = Class.forName(className);
      }
      return cls;
   }

   public Object newInstance(String className)
   {
      try
      {
         Class<?> c = classForName(className);
         return c.newInstance();
      } catch (Exception ex)
      {
         ex.printStackTrace();
         return null;
      }
   }

   public Object newInstance(String className, Object context)
   {
      try
      {
         Class<?> c = classForName(className, context);
         return c.newInstance();
      } catch (Exception ex)
      {
         ex.printStackTrace();
         return null;
      }
   }

   public boolean isInstance(Object o, String className)
   {
      try
      {
         Class<?> c = classForName(className, o);
         return c.isInstance(o);
      } catch (ClassNotFoundException ex)
      {
         return false;
      }
   }

   public String removeQuotes(String quotedString)
   {
      if (quotedString == null)
         return quotedString;
      StringBuilder result = new StringBuilder();
      char[] chars = quotedString.toCharArray();

      for (char c : chars)
      {
         if (c != '"')
            result.append(c);
      }
      return result.toString();
   }

   public String replaceTags(String taggedString)
   {
      if (taggedString == null)
         return taggedString;
      String lessThan = "&lt;";
      String greaterThan = "&gt;";
      String quote = "'";
      StringBuilder result = new StringBuilder();
      char[] chars = taggedString.toCharArray();

      for (char c : chars)
      {
         if (c == '<')
            result.append(lessThan);
         else if (c == '>')
            result.append(greaterThan);
         else if (c == '"')
            result.append(quote);
         else
            result.append(c);
      }
      return result.toString();
   }

   public boolean isAccessorReplacement(FMethod method)
   {
      String[] prefixes = { "get", "set", "is", "basicGet", "basicSet" };

      for (String prefix : prefixes)
      {
         String name = method.getName();
         if (name.startsWith(prefix))
         {
            FClass containingClass = method.getParentElement();
            String possibleFeatureName = lowerCaseFirstChar(name.substring(prefix.length()));

            if (containingClass.hasKeyInAttrs(possibleFeatureName))
            {
               // check, if method is a simple acsessor
               return true;
            } else
            {
               // check, if method is a accessor for roles
               Iterator<? extends FRole> roleIter = containingClass.iteratorOfAllRoles();
               while (roleIter.hasNext())
               {
                  FRole aRole = roleIter.next();

                  if (aRole.getAssoc().hasKeyInStereotypes("Virtual Path"))
                     continue;

                  String partnerRoleName = aRole.getPartnerRole().getName();
                  if (possibleFeatureName.equals(partnerRoleName))
                     return true;
               }
            }
         }
      }

      return false;
   }

   /**
    * Parses an OCL statement and looks for identifiers that have already been bound by Fujaba. The current parser is
    * very simple and is not able to distinguish between keywords and proprietary identifiers.
    * 
    * @return list of identifiers.
    */
   public List<String> getOCLVarList(FElement constraintElem, LocalVariableInformation localVars)
   {
      Vector<String> varList = new Vector<String>();

      // retrieve (and construct) the OCL statement based on the Element Type
      String oclStatement = null;
      if (constraintElem instanceof UMLConstraint)
      {
         oclStatement = constraintElem.getText();
      } else if (constraintElem instanceof UMLAttrExprPair)
      {
         UMLAttrExprPair attr = ((UMLAttrExprPair) constraintElem);
         String parentName = attr.getParentElement().getName();

         // replace this by self reference
         if ("this".equals(parentName))
         {
            parentName = "self";
         }
         // construct full OCL statement
         oclStatement = parentName + "." + attr.getText();
      }

      // parse the OCL constraint
      Pattern varNamePattern = Pattern.compile("(^| |\\()([a-zA-Z]\\w+)(\\.|->| |\\)|$)");
      Matcher varNameMatcher = varNamePattern.matcher(oclStatement);
      while (varNameMatcher.find())
      {
         String name = varNameMatcher.group(2);
         if (!"self".equals(name) && isLocallyDefinedVariable(name, localVars, constraintElem))
            varList.add(name);
      }

      return varList;
   }

   private boolean isLocallyDefinedVariable(String name, LocalVariableInformation localVars, FElement constraintElem)
   {
      FElement parentElem = constraintElem;

      // check if name is in local variables
      if (localVars.hasKeyInLocalVars(name))
      {
         return true;
      }

      // select parent story diagram
      while (!(parentElem instanceof UMLActivityDiagram))
         parentElem = parentElem.getParentElement();

      // check if name is an object of the parent diagram
      UMLActivityDiagram parentDiagram = (UMLActivityDiagram) parentElem;
      Iterator<UMLObject> objectIter = parentDiagram.iteratorOfObjects();
      while (objectIter.hasNext())
      {
         if (objectIter.next().getName().equals(name))
         {
            return true;
         }
      }

      // select parent method
      while (!(parentElem instanceof UMLMethod))
         parentElem = parentElem.getParentElement();

      // check if name is a local variable
      UMLMethod parentMethod = (UMLMethod) parentElem;
      Iterator<UMLParam> paramIter = parentMethod.iteratorOfParam();
      while (paramIter.hasNext())
      {
         if (paramIter.next().getName().equals(name))
         {
            return true;
         }
      }

      return false;
   }

   private String lowerCaseFirstChar(String s)
   {
      String firstChar = s.substring(0, 1);
      String rest = s.substring(1);
      return firstChar.toLowerCase() + rest;
   }

   public int getPatternDepth(Token t)
   {
      boolean fujaba__Success = false;
      Iterator fujaba__IterTToPattern = null;
      Object fujaba__TmpObject = null;
      Token pattern = null;

      int count = -1;
      // story pattern storypatternwithparams
      try
      {
         fujaba__Success = false;

         // check object t is really bound
         JavaSDM.ensure(t != null);
         // iterate to-many link $link.InstanceOf.Name from t to pattern
         fujaba__Success = false;
         fujaba__IterTToPattern = new de.uni_kassel.sdm.Path(t, "parent*");
         while (fujaba__IterTToPattern.hasNext())
         {
            try
            {
               fujaba__TmpObject = fujaba__IterTToPattern.next();

               // ensure correct type and really bound of object pattern
               if (fujaba__TmpObject instanceof SearchOperation)
               {
                  // count only to many links
                  SearchOperation searchOp = (SearchOperation) fujaba__TmpObject;
                  UMLLink link = searchOp.getLink().getRef();
                  UMLObject target = searchOp.getSubject().getRef();
                  UMLRole role = (UMLRole) link.getCorrespondingRole(target);
                  UMLRole partnerRole = role.getPartnerRole();
                  String range = link.getRange();

                  boolean noRange = range == null || "".equals(range);
                  boolean hasRange = !noRange;
                  boolean hasQualifer = partnerRole.getQualifier() != null;
                  boolean qualiferButNoRange = hasQualifer && noRange;
                  boolean toOne = role.getCard().getUpperBound() == 1 && !qualiferButNoRange;

                  boolean ordered = isOrdered(role);
                  if (!hasQualifer && hasRange && ordered)
                  {
                     toOne = true;
                  }
                  // some multi links are used like toOne
                  else if (searchOp instanceof SearchMultiLinkOperation)
                  {
                     SearchMultiLinkOperation searchMultiOp = (SearchMultiLinkOperation) searchOp;

                     toOne = true;

                     UMLMultiLink multiLink = searchMultiOp.getMultiLink();
                     if (multiLink != null)
                     {
                        String multiName = multiLink.getName();
                        if ("{...}".equals(multiName))
                        {
                           toOne = false;
                        }
                     }
                  } else
                  {
                     // first of ordered link
                     UMLMultiLink targetMulti = link.getRevTargetLink();
                     UMLObject source = link.getSource();
                     if (targetMulti != null && targetMulti.getContainerObject() == source)
                     {
                        if (targetMulti.getType() == 0)
                        {
                           toOne = true;
                        }
                     } else
                     {
                        UMLMultiLink sourceMulti = link.getRevSourceLink();
                        if (sourceMulti != null && sourceMulti.getContainerObject() == source)
                        {
                           if (sourceMulti.getType() == 1)
                           {
                              toOne = true;
                           }
                        }
                     }
                  }
                  JavaSDM.ensure(!toOne);
               } else
               {
                  JavaSDM.ensure(fujaba__TmpObject instanceof ExecuteStoryPatternOperation || fujaba__TmpObject instanceof NegativeBlock);
               }
               pattern = (Token) fujaba__TmpObject;

               // check isomorphic binding between objects t and pattern
               // JavaSDM.ensure ( !t.equals (pattern) );

               // collabStat call
               count++;

               fujaba__Success = true;
            } catch (JavaSDMException fujaba__InternalException)
            {
               fujaba__Success = false;
            }
         }
         JavaSDM.ensure(fujaba__Success);
         fujaba__Success = true;
      } catch (JavaSDMException fujaba__InternalException)
      {
         fujaba__Success = false;
      }

      return count;

   }

   private HashMap<String, String> statechartVars = new HashMap<String, String>();

   public HashMap<String, String> getStatechartVars()
   {
      return statechartVars;
   }

   /**
    * Required for the preprocessor plugin made by University of Bayreuth
    * 
    * @param clazz
    * @return
    * @author Thomas Buchmann
    */
   public boolean isInConfiguration(FClass clazz)
   {
      return true;
   }

   /**
    * Required for Hibernate Annotations in the MOD2-SCM Project made by University of Bayreuth
    * 
    * @param clazz
    * @return
    * @author Stefan Oehme
    */
   public boolean hasHibernateClassAbove(FClass clazz)
   {
      while (clazz.getSuperClass() != null)
      {
         if (clazz.getSuperClass().hasKeyInStereotypes("hibernate"))
            return true;
         else
            clazz = clazz.getSuperClass();
      }
      return false;
   }

   public List<String> getHibernateChildrenNames(FClass clazz)
   {
      Set<String> hibernateChildrenNames = new HashSet<String>();
      if (clazz.hasKeyInStereotypes("hibernate"))
      {
         return new ArrayList<String>();
      }
      insertHibernatehildrenNames(hibernateChildrenNames, clazz);

      return new ArrayList<String>(hibernateChildrenNames);
   }

   private void insertHibernatehildrenNames(Set<String> currentNames, FClass currentClazz)
   {
      if (currentClazz.hasKeyInStereotypes("hibernate"))
      {
         currentNames.add(currentClazz.getFullClassName());
         return;
      } else
      {
         for (Iterator<? extends FClass> iterator = currentClazz.iteratorOfSubClasses(); iterator.hasNext();)
         {
            insertHibernatehildrenNames(currentNames, iterator.next());
         }
      }
   }

   /**
    * @return true if code fragments for tracing shall be added to the generated code
    */
   public boolean isSdmTracingActivated()
   {
      return sdmTracingActivated;
   }

   /**
    * Enable trace code fragment generation
    */
   public void enableSdmTracing()
   {
      this.sdmTracingActivated = true;
   }

   /**
    * Disable trace code fragment generation
    */
   public void disableSdmTracing()
   {
      this.sdmTracingActivated = false;
   }

   /**
    * Used in CodeGen2 templates during codegeneration to decide if a link is navigable in the metamodel or not
    * (requires util).
    * 
    * @param attributeName
    *           Identifies ereference to be navigated along
    * @return true if navigation of link requires util
    */
   public static boolean isOriginallyNotNavigable(String attributeName)
   {
      return (attributeName.startsWith("$") && attributeName.endsWith("$"));
   }

   /**
    * Check if reference is a containment reference (encoded with a "$eContainer") and return the better performing call
    * of eContainer(). *
    */
   public static boolean isOriginallyNavigableViaComposite(String attributeName)
   {
      return (attributeName.equals("$eContainer"));
   }

   /**
    * Generates call to util for adding objects along links that are not navigable.
    * 
    * Unidirectional reference in Ecore metamodel: Target ---(sourceRoleName)-> Source
    * 
    * This is transformed to Fujaba as: Target <-($rev__sourceRoleName__Target$)------(sourceRoleName)-> Source i.e. a
    * bidirectional assocation with a dummy target role name enclosed in $...$
    * 
    * e.g. in SDM pattern, [sourceName:Source] ----> [targetName:Target] is not origninally navigable and requires usage
    * of utility
    * 
    * @param sourceName
    *           Name of variable of type Source ([sourceName:Source])
    * @param targetName
    *           Name of variable of type Target ([targetName:Target])
    * @param targetRoleName
    *           Role name of target end of association in Fujaba i.e. ($rev__sourceRoleName__Target$)
    * @return generated method call for utility
    */
   public static String genCrossReferenceCallForAdd(String sourceName, String targetName, String targetRoleName)
   {
      String cleanedTargetRoleName = targetRoleName.substring(targetRoleName.indexOf("__") + 2, targetRoleName.lastIndexOf("__"));
      return eMoflonUtil + ".addOppositeReference(" + sourceName + "," + targetName + ",\"" + cleanedTargetRoleName + "\")";
   }

   /**
    * Generates call to util for removing objects along links that are not navigable.
    * 
    * Unidirectional reference in Ecore metamodel: Target ---(sourceRoleName)-> Source
    * 
    * This is transformed to Fujaba as: Target <-($rev__sourceRoleName__Target$)------(sourceRoleName)-> Source i.e. a
    * bidirectional assocation with a dummy target role name enclosed in $...$
    * 
    * e.g. in SDM pattern, [sourceName:Source] ----> [targetName:Target] is not origninally navigable and requires usage
    * of utility
    * 
    * @param sourceName
    *           Name of variable of type Source ([sourceName:Source])
    * @param targetName
    *           Name of variable of type Target ([targetName:Target])
    * @param targetRoleName
    *           Role name of target end of association in Fujaba i.e. ($rev__sourceRoleName__Target$)
    * @return generated method call for utility
    */
   public static String genCrossReferenceCallForRemove(String sourceName, String targetName, String targetRoleName)
   {
      String cleanedTargetRoleName = targetRoleName.substring(targetRoleName.indexOf("__") + 2, targetRoleName.lastIndexOf("__"));
      return eMoflonUtil + ".removeOppositeReference(" + sourceName + "," + targetName + ",\"" + cleanedTargetRoleName + "\")";
   }

   /**
    * Generates call to util for retrieving collection of non-navigable links which is generated on-the-fly
    * 
    * Unidirectional reference in Ecore metamodel: Target ---(sourceRoleName)-> Source
    * 
    * This is transformed to Fujaba as: Target <-($rev__sourceRoleName__Target$)------(sourceRoleName)-> Source i.e. a
    * bidirectional association with a dummy target role name enclosed in $...$
    * 
    * e.g. in SDM pattern, [sourceName:Source] ----> [targetName:Target] is not originally navigable and requires usage
    * of utility
    * 
    * @param targetName
    *           Name of variable of type Target ([targetName:Target])
    * @param Source
    *           Source class in metamodel (c.f. explanation of above)
    * @return
    */
   public static String genCrossReferenceCallForCollection(String targetName, String Source, String targetRoleName)
   {
      if (targetRoleName.startsWith("$rev__"))
         targetRoleName = targetRoleName.substring(targetRoleName.indexOf("__") + 2, targetRoleName.lastIndexOf("__"));
      return eMoflonUtil + ".getOppositeReference(" + targetName + "," + Source + ".class" + ",\"" + targetRoleName + "\")";
   }

   /**
    * Bookkeeping for imports used later when generating code with the standard EMF Codegenerator.
    * 
    * @param imports
    *           List of imports for method
    * @param method
    *           Method for which SDM code is being generated
    */
   public static void registerImportsForMethod(ImportInformation imports, UMLMethod method)
   {
      if (methodToImports.get(method) == null)
         methodToImports.put(method, new HashSet<String>());

      methodToImports.get(method).addAll(imports.getImports());
   }

   /**
    * This getter can be called after a CodeGen run. Its purpose is to retrieve a set (union) of all required imports
    * for methods in the resp. CodeGen code. This is an over-approximation of the required imports. A better approach
    * would be to retrieve only those imports relevant to the intended operation(s).
    * 
    * @return Set of all required imports (String representations). The set represents a copy of the original data and
    *         may be altered.
    */
   public static Set<String> getAllMethodImports()
   {
      Set<String> result = new HashSet<String>();
      for (Set<String> temp : methodToImports.values())
      {
         result.addAll(temp);
      }
      return result;
   }

   /**
    * 
    * @param method
    *           The method for which the required imports should be retrieved.
    * @return A set that contains all required imports for the given method. The set represents a copy of the original
    *         data and may be altered.
    */
   public static Set<String> getMethodImports(UMLMethod method)
   {
      Set<String> result = new HashSet<String>();
      Set<String> originalSet = methodToImports.get(method);
      if (originalSet != null)
         result.addAll(originalSet);
      return result;
   }

   /**
    * Determines whether the specified UMLActivity is (potentially) the last (w.r.t. control flow) Activity in a for
    * each branch before reentering the for each activity. (Originally introduced for SDM tracing).
    * 
    * @param aActivity
    * @return true, if aActivity has an outgoing transition to an UMLStoryActivity with "for each" semantics and there
    *         exists a path form that UMLStoryActivity starting with a "[each time]" edge to the given UMLActivity that
    *         does not pass the UMLStoryActivity as intermediate node again. This means, that aActivity lies in the
    *         "[each time]" loop, and is potentially the last UMLActivity before reentering the for each node again.
    *         Else false.
    */
   public static boolean hasDirectOutconnectionToControllingForEachActivity(UMLActivity aActivity)
   {
      Iterator<UMLTransition> iteratorOfExits = aActivity.iteratorOfExit();
      List<UMLStoryActivity> directlyAccessibleForEachs = new LinkedList<UMLStoryActivity>();
      for (UMLTransition outgoingEdge = null; iteratorOfExits.hasNext();)
      {
         outgoingEdge = iteratorOfExits.next();
         UMLActivity activity = outgoingEdge.getRevEntry();
         if (activity instanceof UMLStoryActivity)
         {
            UMLStoryActivity next = (UMLStoryActivity) activity;
            if (next.isForEach())
               directlyAccessibleForEachs.add(next);
         }
      }

      if (!directlyAccessibleForEachs.isEmpty())
      {
         Set<UMLActivity> ancestors = new HashSet<UMLActivity>();
         getAllPreviousActivities(aActivity, ancestors);
         for (UMLStoryActivity temp : directlyAccessibleForEachs)
         {
            if (ancestors.contains(temp))
               return true;
         }
      }

      return false;
   }

   /**
    * This method searches for all (transitive) predecessor activities for a given activity, and stores references in a
    * provided set. (Originally introduced for SDM tracing).
    * 
    * @param activity
    *           The UMLActivity to derive the previous activities for.
    * @param ancestors
    *           Set that stores the retrieved UMLActivities that precede the provided activity (w.r.t. control flow).
    *           Has in/out-semantics.
    */
   private static void getAllPreviousActivities(final UMLActivity activity, final Set<UMLActivity> ancestors)
   {
      Iterator<UMLTransition> iteratorOfEntry = activity.iteratorOfEntry();
      for (UMLTransition incomingEdge = null; iteratorOfEntry.hasNext();)
      {
         incomingEdge = iteratorOfEntry.next();
         UMLActivity nextAncestor = incomingEdge.getRevExit();
         if (ancestors.add(nextAncestor))
            getAllPreviousActivities(nextAncestor, ancestors);
      }
   }

   /**
    * This method returns the guard type of the transition from a given activity to its "controlling" for each activity.
    * (Originally introduced for SDM tracing).
    * 
    * @param aActivity
    *           An activity that has an outgoing transition to a "controlling" for each activity.
    * @return int representation of the type of the UMLTransitionGuard (NONE, SUCCESS, FAILURE, ...) of the connection
    *         between the given activity an its preceding for each activity.
    */
   public static int returnsToControllingForEachVia(UMLActivity aActivity)
   {
      Iterator<UMLTransition> iteratorOfExits = aActivity.iteratorOfExit();
      Map<UMLStoryActivity, Integer> directlyAccessibleForEachs = new HashMap<UMLStoryActivity, Integer>();
      for (UMLTransition outgoingEdge = null; iteratorOfExits.hasNext();)
      {
         outgoingEdge = iteratorOfExits.next();
         UMLActivity activity = outgoingEdge.getRevEntry();
         if (activity instanceof UMLStoryActivity)
         {
            UMLStoryActivity next = (UMLStoryActivity) activity;
            if (next.isForEach())
            {
               directlyAccessibleForEachs.put(next, UMLTransitionGuard.getGuardType(outgoingEdge));
            }
         }
      }

      if (!directlyAccessibleForEachs.isEmpty())
      {
         if (directlyAccessibleForEachs.keySet().size() == 1)
            return directlyAccessibleForEachs.values().iterator().next();

         Set<UMLActivity> ancestors = new HashSet<UMLActivity>();
         getAllPreviousActivities(aActivity, ancestors);
         for (UMLStoryActivity temp : directlyAccessibleForEachs.keySet())
         {
            if (ancestors.contains(temp))
               return directlyAccessibleForEachs.get(temp);
         }
      }

      return -1;
   }

   /**
    * This helper method is used during code generation to maintain a mapping from UMLMethods (CodeGen2 world) to
    * indices of corresponding EMF EOperations. (Originally introduced for SDM tracing).
    * 
    * @param toBeAdded
    *           A collection containing new map entries that get added to the global map.
    */
   public void addAllUMLMethodToEOperationIds(Map<UMLMethod, Integer> toBeAdded)
   {
      umlMethodToEOperationId.clear();
      umlMethodToEOperationId.putAll(toBeAdded);
   }

   /**
    * Getter method to retrieve the stored mapping for a given UMLMethod. (Originally introduced for SDM tracing).
    * 
    * @param method
    *           A given UMLMethod
    * @return The (EMF class local) index of the corresponding EMF EOperation.
    */
   public int getEOperationID(UMLMethod method)
   {
      for (UMLMethod temp : umlMethodToEOperationId.keySet())
      {
         if (temp.getFullMethodName().equals(method.getFullMethodName()) && temp.getParent().getFullClassName().equals(method.getParent().getFullClassName()))
         {
            Integer result = umlMethodToEOperationId.get(temp);
            if (result != null)
               return result;
            throw new IllegalStateException();
         }
      }
      throw new IllegalStateException();
   }

   /**
    * Conterpart of {@link de.uni_kassel.fujaba.codegen.Utility#hasDirectOutconnectionToControllingForEachActivity
    * Utility.hasDirectOutconnectionToControllingForEachActivity}, but instead of determining whether the given activity
    * is the last in a row, this method checks whether it is the first one. (Originally introduced for SDM tracing).
    * 
    * @param aActivity
    *           The given activity
    * @return true, if this activity is the first (w.r.t. control flow) activity of an each time branch.
    */
   public static boolean hasDirectInconnectionFromControllingForEachActivity(UMLActivity aActivity)
   {
      Iterator<UMLTransition> iteratorOfEntry = aActivity.iteratorOfEntry();

      for (UMLTransition incomingEdge = null; iteratorOfEntry.hasNext();)
      {
         incomingEdge = iteratorOfEntry.next();
         if (UMLTransitionGuard.EVERYTIMES == UMLTransitionGuard.getGuardType(incomingEdge))
         {
            UMLActivity activity = incomingEdge.getRevExit();
            if (activity instanceof UMLStoryActivity)
            {
               UMLStoryActivity prev = (UMLStoryActivity) activity;
               if (prev.isForEach())
                  return true;
            }
         }
      }
      return false;
   }

   /**
    * Helper method that generates a unique String identifier based on a given String. (Originally introduced for SDM
    * tracing).
    * 
    * @param varName
    * @return
    */
   public String getUniqueTempVarName(String varName)
   {
      return varName + "_Temp_" + counter++;
   }

   /**
    * Determine the factory name based on the given packageName. This is required by Codgen2 for creating objects.
    * 
    * @param packageName
    *           Supplied package name
    * @return Determined factory name
    */
   public String getFactory(String packageName)
   {
      String factoryName = packageNameToFactoryName.get(packageName);

      // Check if there is a mapping, if not use default strategy
      return factoryName == null ? upFirstChar(packageName) + "Factory" : factoryName;
   }

   /**
    * Add mappings from package to factory name
    * 
    * @param mappings
    */
   public void addPackageToFactoryMappings(Map<String, String> mappings)
   {
      packageNameToFactoryName.putAll(mappings);
   }

   public boolean isShuffleLists()
   {
      return listShufflingActivated;
   }

   public void enableListShuffling()
   {
      listShufflingActivated = true;
   }

   public void disableListShuffling()
   {
      listShufflingActivated = false;
   }

   public void addImportMappings(Map<String, String> importMappings)
   {
      this.importMappings.putAll(importMappings);
   }

   public String getOptimizedCollection(UMLObject source, UMLObject target)
   {
      if (typeIsToBeOptimized(source) && typeIsToBeOptimized(target))
      {
         ArrayList<UMLAttrExprPair> foundConstraints = new ArrayList<>();
         Iterator<UMLAttrExprPair> constraints = target.iteratorOfAttrs();
         while (constraints.hasNext())
         {
            UMLAttrExprPair constraint = constraints.next();
            if (isOptimizableAssertion(constraint))
               foundConstraints.add(constraint);
         }

         String nameConstraint = null;
         String indexConstraint = null;
         for (UMLAttrExprPair constraint : foundConstraints)
         {
            if (constraint.getName().equals("name"))
               nameConstraint = constraint.getExpression();
            else if (constraint.getName().equals("index"))
               indexConstraint = constraint.getExpression();
         }

         if (nameConstraint != null)
         {
            if (indexConstraint != null)
            {
               return nameConstraint + ", " + indexConstraint;
            } else
               return nameConstraint;
         } else if (indexConstraint != null)
            return indexConstraint;
      }

      return "";
   }

   private boolean isOptimizableAssertion(UMLAttrExprPair constraint)
   {
      return constraint.getOperation() == UMLAttrExprPair.EQUAL && constraint.getQualifier() == UMLAttrExprPair.PRE;
   }

   private boolean typeIsToBeOptimized(UMLObject object)
   {
      return object.getInstanceOf().getDeclaredInPackage().getName().equals("MocaTree")
            && (object.getInstanceOf().getName().equals("Attribute") || object.getInstanceOf().getName().equals("Text") || object.getInstanceOf().getName()
                  .equals("Node"));
   }
}
