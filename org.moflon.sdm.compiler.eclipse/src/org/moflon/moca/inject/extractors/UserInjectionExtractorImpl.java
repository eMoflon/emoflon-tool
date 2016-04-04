package org.moflon.moca.inject.extractors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.core.moca.processing.CodeAdapter;
import org.moflon.core.moca.processing.ProcessingFactory;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.moca.inject.parser.InjectImplParserAdapter;
import org.moflon.moca.inject.parser.InjectParser;
import org.moflon.moca.inject.parser.InjectParserAdapter;
import org.moflon.moca.inject.util.ClassNameToPathConverter;
import org.moflon.moca.inject.util.MatchingParametersChecker;
import org.moflon.moca.inject.validation.InjectionValidationMessage;
import org.moflon.moca.inject.validation.MissingEClassValidationMessage;
import org.moflon.moca.inject.validation.MissingEOperationValidationMessage;

import MocaTree.File;
import MocaTree.Folder;
import MocaTree.Node;
import MocaTree.Text;

public class UserInjectionExtractorImpl implements InjectionExtractor
{
   private static final String IGNORE_FILE_PREFIX = ".";

   private static final String MEMBERS_NODE_NAME = "MEMBERS";

   private static final String IMPORTS_NODE_NAME = "IMPORTS";

   private static final String MODEL_NODE_NAME = "MODEL";

   private HashMap<EOperation, String> modelCode;

   private HashMap<String, String> membersCode;

   private HashMap<String, List<String>> imports;

   private HashMap<String, GenClass> fqnToGenClassMap;

   final private ClassNameToPathConverter classNameToPathConverter;

   private List<InjectionValidationMessage> errors;

   private final GenModel genModel;

   private final String injectionRootFolder;

   private File currentlyProcessedFile;

   /**
    * Creates an injection extractor with a default class name-to-path mapping ("gen" folder and system file separator).
    */
   public UserInjectionExtractorImpl(final String folderPath, final GenModel genModel) throws CoreException
   {
      this(folderPath, genModel, new ClassNameToPathConverter(WorkspaceHelper.GEN_FOLDER));
   }

   /**
    * Creates an injection extractor that searches for injections from the given root folder and iterates in parallel
    * through the packages of the given genmodel. The injection extractor uses the given class name-to-path converter to
    * map class names to generated class files.
    */
   public UserInjectionExtractorImpl(final String injectionRootFolder, final GenModel genModel, final ClassNameToPathConverter classNameToPathConverter)
         throws CoreException
   {
      initializeEmptyMaps();
      initializeErrorHandling();

      this.currentlyProcessedFile = null;
      this.injectionRootFolder = injectionRootFolder;
      this.classNameToPathConverter = classNameToPathConverter;
      this.genModel = genModel;

   }

   @Override
   public List<InjectionValidationMessage> getErrors()
   {
      return this.errors;
   }

   @Override
   public void extractInjections()
   {
      final Folder tree = parseInjectionFiles(injectionRootFolder);
      final EList<Folder> subFolders = tree.getSubFolder();
      if (subFolders.size() > 0)
      {
         for (GenPackage genPackage : genModel.getGenPackages())
         {
            processGenPackageContents(genPackage);
         }
         traverseSubfoldersAndFiles(subFolders.get(0).getName(), subFolders.get(0));
      }
   }

   @Override
   public Collection<String> getImportsPaths()
   {
      return this.imports.keySet();
   }

   @Override
   public List<String> getImports(final String fullyQualifiedName)
   {
      final List<String> result = imports.get(fullyQualifiedName);
      return result != null ? result : new ArrayList<String>();
   }

   @Override
   public List<String> getAllImports()
   {
      final List<String> allRawImports = new ArrayList<>();
      for (final String file : this.imports.keySet())
      {
         allRawImports.addAll(this.imports.get(file));
      }
      return allRawImports;
   }

   @Override
   public boolean hasModelCode(final EOperation eOperation)
   {
      return modelCode.containsKey(eOperation);
   }

   @Override
   public String getModelCode(final EOperation eOperation)
   {
      return modelCode.get(eOperation);
   }

   @Override
   public Set<String> getMembersPaths()
   {
      return membersCode.keySet();
   }

   @Override
   public String getMembersCode(final String fullyQualifiedName)
   {
      return membersCode.get(fullyQualifiedName);
   }

   @Override
   public String getMembersCodeByClassName(final String className)
   {
      final String path = this.classNameToPathConverter.toPath(className);
      return this.getMembersCode(path);
   }

   private final void processGenPackageContents(final GenPackage genPackage)
   {
      for (GenClass genClass : genPackage.getGenClasses())
      {
         fqnToGenClassMap.put(CodeGeneratorPlugin.getInterfaceName(genClass), genClass);
         fqnToGenClassMap.put(CodeGeneratorPlugin.getClassName(genClass), genClass);
      }
      for (GenPackage subPackage : genPackage.getSubGenPackages())
      {
         processGenPackageContents(subPackage);
      }
   }

   /**
    * Initializes the maps of this class (all will be empty)
    */
   private void initializeEmptyMaps()
   {
      modelCode = new HashMap<EOperation, String>();
      membersCode = new HashMap<String, String>();
      imports = new HashMap<String, List<String>>();
      fqnToGenClassMap = new HashMap<String, GenClass>();
   }

   private void initializeErrorHandling()
   {
      this.errors = new ArrayList<>();
   }

   /**
    * Parses the .inject files in given folder (recursively)
    * 
    * @param rootPath
    *           Path to the root of the folder structure
    * @return MocaTree that resembles the content of the folder structure
    */
   private Folder parseInjectionFiles(final String rootPath)
   {
      final CodeAdapter codeAdapter = ProcessingFactory.eINSTANCE.createCodeAdapter();
      codeAdapter.getParser().add(new InjectImplParserAdapter());
      codeAdapter.getParser().add(new InjectParserAdapter());

      return codeAdapter.parse(new java.io.File(rootPath));
   }

   /**
    * Walks through the parser generated tree and saves the information
    * 
    * @param dfsStack
    *           keeps track of the recursion
    * @param root
    *           The root of the tree
    * @param metamodel
    *           The EPackage that corresponds the root folder.
    */
   private void traverseSubfoldersAndFiles(final String prefix, final Folder root)
   {
      processFiles(prefix, root);
      traverseSubfolders(prefix, root);
   }

   /**
    * Traverses all subfolders of the given folder and descends parallel into the appropriate subpackages of the given
    * package.
    */
   private void traverseSubfolders(final String prefix, final Folder folder)
   {
      for (final Folder subFolder : folder.getSubFolder())
      {
         if (!shallIgnoreFolder(subFolder))
         {
            traverseSubfoldersAndFiles(prefix + "." + subFolder.getName(), subFolder);
         }
      }
   }

   /*
    * Processes the content of each file in the given folder
    */
   private void processFiles(final String prefix, final Folder folder)
   {
      for (final File file : folder.getFile())
      {
         if (isInjectionFile(file) && !shallIgnoreFile(file))
         {
            final String className = buildClassNameFromFileName(file);
            processFileContent(prefix + "." + className, file);
         }
      }
   }

   /**
    * This is called whenever a file is found in the parser generated tree. It extracts the content of that file and
    * saves them to the HashMaps.
    * 
    * @param packages
    *           Path through the folders to the file. Only the names of the folders are needed, not the full path
    * @param file
    *           The file to work on
    * @param eClass
    *           The EClass that corresponds to the file in the tree structure.
    * @throws CoreException
    */
   private void processFileContent(final String fullyQualifiedClassName, final File file)
   {
      setCurrentlyProcessedFile(file);

      GenClass genClass = fqnToGenClassMap.get(fullyQualifiedClassName);
      if (genClass != null)
      {
         final Node rootNode = file.getRootNode();

         final Node modelCodeNode = getNodeByName(rootNode, MODEL_NODE_NAME);

         traverseModelSubTree(genClass.getEcoreClass(), modelCodeNode);

         extractAndStoreMembersCode(rootNode, fullyQualifiedClassName);
         extractAndStoreImports(rootNode, fullyQualifiedClassName);
      } else
      {

         reportMissingEClass(fullyQualifiedClassName, file);
      }

      setCurrentlyProcessedFile(null);
   }

   private void extractAndStoreImports(final Node rootNode, final String filePath)
   {
      final Node importsNode = getNodeByName(rootNode, IMPORTS_NODE_NAME);

      final List<String> importList = getImportsFromNode(importsNode);

      imports.put(filePath, importList);
   }

   private void extractAndStoreMembersCode(final Node rootNode, final String filePath)
   {
      final Node membersNode = getNodeByName(rootNode, MEMBERS_NODE_NAME);
      final String code = getCodeStringFromNode(membersNode);
      membersCode.put(filePath, code);
   }

   /**
    * Checks if the name of given file indicates that it is an injection file
    * 
    * @param file
    *           File to check
    */
   private boolean isInjectionFile(final File file)
   {
      return file.getName().endsWith("." + WorkspaceHelper.INJECTION_FILE_EXTENSION);
   }

   /**
    * Checks if this folder is supposed to not be traversed by this extractor
    */
   private boolean shallIgnoreFolder(final Folder folder)
   {
      return folder.getName().startsWith(IGNORE_FILE_PREFIX);
   }

   /**
    * Checks if this file is supposed to be ignored by this extractor
    */
   private boolean shallIgnoreFile(final File file)
   {
      return file.getName().startsWith(IGNORE_FILE_PREFIX);
   }

   /**
    * Extracts the nodes with imports from given 'IMPORTS' Node. <br>
    * Note that these import strings do not have the keyword 'import' or semicolons.
    * 
    * @param importsNode
    *           'IMPORTS' Node
    * @return List with imports
    */
   private List<String> getImportsFromNode(final Node importsNode)
   {
      final List<String> imports = new ArrayList<String>();
      final EList<Text> children = importsNode.getChildren();

      for (final Text childNode : children)
      {
         if (InjectParser.tokenNames[InjectParser.STATIC_IMPORT_NODE].equals(childNode.getName())) {
            imports.add("static " + ((Node)childNode).getChildren().get(0).getName());
         }
         else if (InjectParser.tokenNames[InjectParser.REGULAR_IMPORT_NODE].equals(childNode.getName())) {
            imports.add(((Node)childNode).getChildren().get(0).getName());
         }
      }

      return imports;
   }

   /**
    * Gets the name of the class from the file name
    * 
    * @param file
    *           The .inject file
    * @return Name of the class. (Name of the file without the ".inject")
    */
   private String buildClassNameFromFileName(final File file)
   {
      final String fileName = file.getName();
      final int dotIndex = fileName.indexOf('.');
      return fileName.substring(0, dotIndex);
   }

   /**
    * Traverses the subtree that contains the information about the model functions
    * 
    * @param eClass
    *           The class that contains the model methods we are currently extracting
    * @param modelNode
    *           The "MODEL" node in the tree
    * @throws CoreException
    */
   private void traverseModelSubTree(final EClass eClass, final Node modelNode)
   {
      for (final Text methodText : modelNode.getChildren())
      {
         extractMethodFromMethodTextNode(eClass, methodText);
      }
   }

   private void extractMethodFromMethodTextNode(final EClass eClass, final Text methodText)
   {
      final Node methodNode = (Node) methodText;
      final Node methodNameNode = (Node) methodNode.getChildren().get(0);
      final Text methodBodyNode = methodNode.getChildren().get(1);

      final String methodName = methodNameNode.getName();

      final List<String> paramTypes = extractParameterTypesFromMethodNameNode(methodNameNode);
      final List<String> paramNames = extractParameterNamesFromMethodNameNode(methodNameNode);

      final EOperation eOperation = getCorrespondingEOperation(eClass, methodName, paramNames, paramTypes);

      if (eOperation != null)
      {

         final StringBuilder code = new StringBuilder();
         code.append("\n");
         code.append(MoflonUtil.EOPERATION_MODEL_COMMENT);
         code.append("\n");
         code.append(methodBodyNode.getName().trim());

         modelCode.put(eOperation, code.toString());
      }
   }

   private List<String> extractParameterNamesFromMethodNameNode(final Node nameNode)
   {
      final List<String> paramNames = new ArrayList<String>();
      for (final Text parameterText : nameNode.getChildren())
      {
         final Node paramNameNode = (Node) parameterText;

         paramNames.add(paramNameNode.getName());
      }
      return paramNames;
   }

   private List<String> extractParameterTypesFromMethodNameNode(final Node nameNode)
   {
      final List<String> paramTypes = new ArrayList<String>();
      for (final Text parameterText : nameNode.getChildren())
      {
         final Node paramTypeNode = (Node) ((Node) parameterText).getChildren().get(0);

         paramTypes.add(paramTypeNode.getName());
      }
      return paramTypes;
   }

   /**
    * Searches for the EOperation in given EClass, that matches the given name and parameters
    * 
    * @param surroundingClass
    *           The EClass to search in.
    * @param methodName
    *           The name of the searched method.
    * @param paramNames
    *           The names of the parameters.
    * @param paramTypes
    *           The types of the parameters in the same order as the names.
    * @return The EOperation with the given name and parameters. Guaranteed to be non-null.
    * @throws CoreException
    */
   private EOperation getCorrespondingEOperation(final EClass surroundingClass, final String methodName, final List<String> paramNames,
         final List<String> paramTypes)
   {
      EOperation result = null;

      final List<EOperation> eOperationsWithMatchingName = getEOperationsByName(surroundingClass, methodName);

      for (final EOperation eOperation : eOperationsWithMatchingName)
      {
         if (hasMatchingParameters(eOperation, paramNames, paramTypes))
         {
            result = eOperation;
            break;
         }
      }

      if (result == null)
         reportMissingEOperation(surroundingClass, methodName, paramNames, paramTypes);

      return result;
   }

   /**
    * Searches in a List of EOperations for all those with the given name.
    * 
    * @param eClass
    *           The List to search in.
    * @param name
    *           The name of the searched EOperations.
    * @return All EOperations in the List with the given name.
    */
   private List<EOperation> getEOperationsByName(final EClass eClass, final String name)
   {
      final ArrayList<EOperation> result = new ArrayList<EOperation>();
      for (final EOperation eOperation : eClass.getEOperations())
      {
         if (eOperation.getName().equals(name))
            result.add(eOperation);
      }
      return result;
   }

   /**
    * Checks if the parameters of given EOperation match the given method parameter.
    * 
    * @param eOperationToCheck
    *           The EOperation to check.
    * @param parameterNames
    *           The names of the parameters.
    * @param paramTypes
    *           The types of the parameters.
    * @return True if the parameters matches the given lists, else false;
    */
   private boolean hasMatchingParameters(final EOperation eOperationToCheck, final List<String> parameterNames, final List<String> paramTypes)
   {
      final MatchingParametersChecker parametersChecker = new MatchingParametersChecker();
      return parametersChecker.haveMatchingParamters(eOperationToCheck, parameterNames, paramTypes);
   }

   /**
    * Extracts a String with valid Java code from given 'MEMBERS' Node
    * 
    * @param membersNode
    *           'MEMBERS' Node
    * @return Code String
    */
   private String getCodeStringFromNode(final Node membersNode)
   {
      final EList<Text> children = membersNode.getChildren();
      String code = "";
      if (children.size() > 0)
         code = children.get(0).getName();

      if (code != null)
         code = code.trim();

      return code;
   }

   /**
    * Searches the child node of given node with the given name
    * 
    * @param root
    *           The root node
    * @param nodeName
    *           The name of the searched node
    * @return The child node of given node with the given name
    */
   private Node getNodeByName(final Node root, final String nodeName)
   {
      Optional<Node> nodeWithName = root.getChildren().stream().filter(t -> t instanceof Node).map(t -> ((Node) t)).filter(n -> n.getName().equals(nodeName))
            .findFirst();
      return nodeWithName.orElse(null);
   }

   private void reportMissingEOperation(final EClass surroundingClass, final String methodName, final List<String> paramNames, final List<String> paramTypes)
   {
      final String fullPath = buildInjectionFilePathDescription(this.getCurrentlyProcessedFile());
      this.errors.add(new MissingEOperationValidationMessage(methodName, paramNames, paramTypes, surroundingClass.getName(), fullPath));
   }

   public void reportMissingEClass(final String fullyQualifiedClassName, final File file)
   {
      final String fullPath = buildInjectionFilePathDescription(file);
      this.errors.add(new MissingEClassValidationMessage(fullyQualifiedClassName, fullPath));
   }

   private String buildInjectionFilePathDescription(final File currentInjectionFile)
   {
      final String folderPath = buildInjectionFolderPathDescription(currentInjectionFile.getFolder());
      final String fullPath = folderPath + "/" + currentInjectionFile.getName();
      return fullPath;
   }

   private String buildInjectionFolderPathDescription(final Folder folder)
   {
      Folder parentFolder = folder;
      final LinkedList<String> pathElements = new LinkedList<>();
      while (parentFolder != null)
      {
         pathElements.addFirst(parentFolder.getName());
         parentFolder = parentFolder.getParentFolder();
      }
      return StringUtils.join(pathElements, "/");
   }

   private File getCurrentlyProcessedFile()
   {
      return currentlyProcessedFile;
   }

   private void setCurrentlyProcessedFile(final File currentlyProcessedFile)
   {
      this.currentlyProcessedFile = currentlyProcessedFile;
   }

}
