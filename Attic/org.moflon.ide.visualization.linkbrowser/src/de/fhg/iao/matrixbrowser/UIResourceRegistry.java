/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * UIResourceRegistry.java,v 1.13 2004/04/19 08:38:54 maxmonroe Exp $ Created on
 * 15.01.04 Engineering / IAO. The original developer of the MatrixBrowser and
 * also the FhG IAO will have no liability for use of this software or
 * modifications or derivatives thereof. It's Open Source for noncommercial
 * applications. Please read carefully the IAO_License.txt and/or contact the
 * authors. File : $Id: UIResourceRegistry.java,v 1.16 2004/07/02 09:17:35
 * kookaburra Exp $ Created on 15.01.04
 */
package de.fhg.iao.matrixbrowser;

import java.awt.Color;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.event.EventListenerList;
import javax.swing.plaf.ColorUIResource;

import de.fhg.iao.swt.util.ResourceLoader;

/**
 * Registry for localization strings. Stores key/value pairs of which the values
 * contain the localized string. The <code>LocalizationRegistry</code> can be
 * used quite flexibly, but before usage data must be provided. Data can be fed
 * directly with key/value pairs using the .put() method or the whole registry
 * data can be loaded using either a <code>ResourceBundle</code>, a
 * <code>Properties</code> class or a <code>Map</code> containing the key/value
 * mappings using the various .load() methods. It is also possible first to
 * .load() and the to put/overwrite additional keys. This all is useful when
 * embedding a localized tool kit into another application. See (@link
 * java.util.ResourceBundle) for details, how localization works in java.
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.3 $ See (@link java.util.ResourceBundle) for details,
 *          how localization works in java.
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.3 $
 */
public class UIResourceRegistry {

	/** Default package path to UI resources */
	public static final String DEFAULT_UI_RESOURCE_PACKAGE = "de/fhg/iao/matrixbrowser/resource/";

	/*
	 * CONSTANTS FOR UI RESOURCE REGISTRY
	 */

	public final String TREE_TEXTBACKGROUND = "Tree.textBackground";

	public final String TREE_LEFTCHILD_INDENT = "Tree.leftChildIndent";

	public final String TREE_RIGHTCHILD_INDENT = "Tree.rightChildIndent";

	public final String HORIZONTALROTATETREE_TOPCHILD_INDENT = "HorizontalRotateTree.topChildIndent";

	public final String HORIZONTALROTATETREE_BOTTOMCHILD_INDENT = "HorizontalRotateTree.bottomChildIndent";

	// has to be heigh for compatibility reasons
	public final String HORIZONTALROTATETREE_ROWHEIGHT = "HorizontalRotateTree.rowHeight";

	public final String TREEVIEW_LEAFICON = "TreeView.leafIcon";

	public final String TREEVIEW_COLLAPSEDICON = "TreeView.collapsedIcon";

	public final String TREEVIEW_OPENICON = "TreeView.openIcon";

	public final String TREEVIEW_CONTEXTMENU_TITLE = "TreeView.ContextMenuTitle";

	public final String TREE_DRAWFOCUSBORDERAROUNDICON = "Tree.drawsFocusBorderAroundIcon";

	public final String MATRIXBROWSER_BACKGROUND = "MatrixBrowser.background";

	public final String NODECLOSEUPVIEW_BORDERCOLOR = "NodeCloseUpView.borderColor";

	public final String NODECLOSEUPVIEW_BACKGROUND = "NodeCloseUpView.background";

	public final String NODECLOSEUPVIEW_RELATEDNODESTABLE_BACKGROUND = "NodeCloseUpView.RelatedNodesTable.background";

	public final String NODECLOSEUPVIEW_RELATEDNODESTABLE_TABLEHEADER_BACKGROUND = "NodeCloseUpView.RelatedNodesTable.TableHeader.background";

	public final String NODECLOSEUPVIEW_RELATEDNODESTABLE_SHOWINFEREDRELATIONSPANEL_BACKGROUND = "NodeCloseUpView.RelatedNodesTable.ShowInferedRelationsPanel.background";

	public final String NODECLOSEUPVIEW_NODIRECTED_REAL_RELATION = "NodeCloseUpView.NoDirectedRealRelation";

	public final String NODECLOSEUPVIEW_NODIRECTED_INFERED_RELATION = "NodeCloseUpView.NoDirectedInferedRelation";

	public final String NODECLOSEUPVIEW_BIDIRECTED_INFERED_RELATION = "NodeCloseUpView.BiDirectedRealRelation";

	public final String NODECLOSEUPVIEW_BIDIRECTED_REAL_RELATION = "NodeCloseUpView.BiDirectedInferedRelation";

	public final String NODECLOSEUPVIEW_RIGHTDIRECTED_REAL_RELATION = "NodeCloseUpView.RightDirectedRealRelation";

	public final String NODECLOSEUPVIEW_RIGHTDIRECTED_INFERED_RELATION = "NodeCloseUpView.RightDirectedInferedRelation";

	public final String NODECLOSEUPVIEW_LEFTDIRECTED_REAL_RELATION = "NodeCloseUpView.LeftDirectedRealRelation";

	public final String NODECLOSEUPVIEW_LEFTDIRECTED_INFERED_RELATION = "NodeCloseUpView.LeftDirectedInferedRelation";

	public final String NODECCLOSEUPVIEW_SHOWINFEREDRELATION_CHECKBOX_LABEL = "NodeCloseUpView.ShowInferedRelationCheckBox.Label";

	public final String NODECLOSEUPVIEW_RELATIONTABLE_RELATES_WITH = "NodeCloseUpView.RelationTable.RelatesWithString";

	public final String NODECLOSEUPVIEW_RELATIONTABLE_RELATES_TO = "NodeCloseUpView.RelationTable.RelatesToString";

	public final String RELATIONRENDERER_REAL_RELATION = "RelationRenderer.RealRelation";

	public final String RELATIONRENDERER_COLLAPSED_RELATION = "RelationRenderer.CollapsedRelation";

	public final String RELATIONRENDERER_INFERED_RELATION_OPEN = "RelationRenderer.InferedRelationOpen";

	public final String RELATIONRENDERER_INFERED_RELATION_COLLAPSED = "RelationRenderer.InferedRelationCollapsed";

	public final String RELATIONRENDERER_IDENTITY_RELATION = "RelationRenderer.IdentityRelation";

	public final String RELATIONRENDERER_UPARROW = "RelationRenderer.UpArrow";

	public final String RELATIONRENDERER_UPARROW_ROLLOVER = "RelationRenderer.UpArrowRollOver";

	public final String RELATIONRENDERER_UPARROW_SELECTED = "RelationRenderer.UpArrowSelected";

	public final String RELATIONRENDERER_RIGHTARROW = "RelationRenderer.RightArrow";

	public final String RELATIONRENDERER_RIGHTARROW_ROLLOVER = "RelationRenderer.RightArrowRollOver";

	public final String RELATIONRENDERER_RIGHTARROW_SELECTED = "RelationRenderer.RightArrowSelected";

	public final String RELATIONRENDERER_DOWNARROW = "RelationRenderer.DownArrow";

	public final String RELATIONRENDERER_DOWNARROW_ROLLOVER = "RelationRenderer.DownArrowRollOver";

	public final String RELATIONRENDERER_DOWNARROW_SELECTED = "RelationRenderer.DownArrowSelected";

	public final String RELATIONRENDERER_LEFTARROW = "RelationRenderer.LeftArrow";

	public final String RELATIONRENDERER_LEFTARROW_ROLLOVER = "RelationRenderer.LeftArrowRollOver";

	public final String RELATIONRENDERER_LEFTARROW_SELECTED = "RelationRenderer.LeftArrowSelected";

	public final String RELATIONPANE_CONTEXTMENU_TITLE = "RelationPane.ContextMenu.Title";

	public final String RELATIONPANE_COLLAPSEDRELATION_TOOLTIP = "RelationPane.CollapsedRelationToolTip";

	public final String RELATIONPANE_IDENTITYRELATION_TOOLTIP = "RelationPane.IdentityRelationToolTip";

	/** Singleton Reference to myself */
	private static UIResourceRegistry mySelf = null;

	/** The mapping between keys and ui objects */
	private Map<Object, Object> myRegistryMap = null;

	/** The path used to load UI resources */
	private String myUIResourcePackagePath = null;

	/** The <code>EventListenerList</code> */
	protected transient EventListenerList myEventListenerList = null;

	/**
	 * Constructs an empty <code>UIResourceRegistry</code>.
	 */
	private UIResourceRegistry() {
		super();
		myRegistryMap = new HashMap<>();
		myEventListenerList = new EventListenerList();
		myUIResourcePackagePath = DEFAULT_UI_RESOURCE_PACKAGE;

	}

	/**
	 * @return a singleton instance of the <code>UIResourceRegistry</code>
	 */
	public static UIResourceRegistry getInstance() {
		if (mySelf == null) {
			mySelf = new UIResourceRegistry();
		}
		return mySelf;
	}

	/**
	 * Returns the localization <code>String</code> associated with the given
	 * key.
	 * 
	 * @param aKey
	 *            the key to retrieve the localization <code>String</code>
	 * @param aKey
	 *            the key to retrieve the localization <code>String</code>
	 * @return the localization <code>String</code> associated with the given
	 *         key
	 */
	public String getString(final String aKey) {
		String result = (String)myRegistryMap.get(aKey);

		if (result == null) {
			result = '!' + aKey + '!';
		}

		return result;
	}

	/**
	 * Returns the ui resource associated with the given key as an
	 * <code>Icon</code>.
	 * 
	 * @param aKey
	 *            the key to retrieve the ui resource
	 * @param aKey
	 *            the key to retrieve the ui resource
	 * @return an <code>Icon</code> from the registry or <code>null</code> if
	 *         there is none.
	 */
	public Icon getIcon(final String aKey) {
		Icon icon = null;

		try {
			icon = (Icon) myRegistryMap.get(aKey);
		} catch (ClassCastException e) {
			// ignore it
		}

		return icon;
	}

	/**
	 * Returns the ui resource associated with the given key as an
	 * <code>ColorUIResource</code>.
	 * 
	 * @param aKey
	 *            the key to retrieve the ui resource
	 * @param aKey
	 *            the key to retrieve the ui resource
	 * @return a <code>ColorUIResource</code> from the registry or
	 *         <code>null</code> if there is none.
	 */
	public Color getColor(final String aKey) {
		ColorUIResource color = null;

		try {
			color = (ColorUIResource) myRegistryMap.get(aKey);
		} catch (ClassCastException e) {
			// ignore it
		}

		return color;

	}

	/**
	 * @return true, if the <code>UIResourceRegistry</code> is empty and thus
	 *         contains no mappings.
	 */
	public boolean isEmpty() {
		return myRegistryMap.size() == 0;
	}

	/**
	 * Loads the default values for gui components used by matrixbrowser into
	 * the <code>UIDefaults</code> of the current look & feel, which is managed
	 * by <code>UIManager</code>
	 * 
	 * @see javax.swing.UIManager#getLookAndFeelDefaults()
	 * @see javax.swing.LookAndFeel#getDefaults()
	 */
	public void loadLaFDefaults() {
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		defaults.put(TREE_TEXTBACKGROUND, new ColorUIResource(222, 222, 222));
		defaults.put(TREE_LEFTCHILD_INDENT, new Integer(5));
		defaults.put(TREE_RIGHTCHILD_INDENT, new Integer(10));

		UIManager.put(HORIZONTALROTATETREE_TOPCHILD_INDENT, new Integer(5));
		UIManager.put(HORIZONTALROTATETREE_BOTTOMCHILD_INDENT, new Integer(5));
		// changed from .rowHeigh to .rowHeight
		UIManager.put(HORIZONTALROTATETREE_ROWHEIGHT, new Integer(24));
		this.fireUIResourceRegistryEvent(new UIResourceRegistryEvent(this));
	}

	/**
	 * load all necessary icons for the matrixbrowser
	 * 
	 * @param packagePath
	 *            the path where the icons are located
	 */
	public void loadIcons(final String packagePath) {
		// load tree view icons
		myRegistryMap.put(TREEVIEW_LEAFICON, new ImageIcon(ResourceLoader
				.getResource(packagePath.concat("TreeLeaf.gif"))));
		myRegistryMap.put(TREEVIEW_COLLAPSEDICON, new ImageIcon(ResourceLoader
				.getResource(packagePath.concat("TreeCollapsed.gif"))));
		myRegistryMap.put(TREEVIEW_OPENICON, new ImageIcon(ResourceLoader
				.getResource(packagePath.concat("TreeOpen.gif"))));

		// uncomment for null icons
		/*
		 * myRegistryMap.put("TreeView.leafIcon", null);
		 * myRegistryMap.put("TreeView.collapsedIcon", null);
		 */

		/*
		 * myRegistryMap.put("TreeView.leafIcon", null);
		 * myRegistryMap.put("TreeView.collapsedIcon", null);
		 */

		// load NodeCloseUpView default colors
		myRegistryMap.put(NODECLOSEUPVIEW_NODIRECTED_REAL_RELATION,
				new ImageIcon(ResourceLoader.getResource(packagePath
						.concat("NoDirectedRealRelation.gif"))));
		myRegistryMap.put(NODECLOSEUPVIEW_NODIRECTED_INFERED_RELATION,
				new ImageIcon(ResourceLoader.getResource(packagePath
						.concat("NoDirectedInferedRelation.gif"))));
		myRegistryMap.put(NODECLOSEUPVIEW_BIDIRECTED_REAL_RELATION,
				new ImageIcon(ResourceLoader.getResource(packagePath
						.concat("BiDirectedRealRelation.gif"))));
		myRegistryMap.put(NODECLOSEUPVIEW_BIDIRECTED_INFERED_RELATION,
				new ImageIcon(ResourceLoader.getResource(packagePath
						.concat("BiDirectedInferedRelation.gif"))));
		myRegistryMap.put(NODECLOSEUPVIEW_RIGHTDIRECTED_REAL_RELATION,
				new ImageIcon(ResourceLoader.getResource(packagePath
						.concat("RightDirectedRealRelation.gif"))));
		myRegistryMap.put(NODECLOSEUPVIEW_RIGHTDIRECTED_INFERED_RELATION,
				new ImageIcon(ResourceLoader.getResource(packagePath
						.concat("RightDirectedInferedRelation.gif"))));
		myRegistryMap.put(NODECLOSEUPVIEW_LEFTDIRECTED_REAL_RELATION,
				new ImageIcon(ResourceLoader.getResource(packagePath
						.concat("LeftDirectedRealRelation.gif"))));
		myRegistryMap.put(NODECLOSEUPVIEW_LEFTDIRECTED_INFERED_RELATION,
				new ImageIcon(ResourceLoader.getResource(packagePath
						.concat("LeftDirectedInferedRelation.gif"))));
		// Load relation renderer icon
		myRegistryMap.put(RELATIONRENDERER_REAL_RELATION, new ImageIcon(
				ResourceLoader.getResource(packagePath
						.concat("RealRelation.gif"))));
		myRegistryMap.put(RELATIONRENDERER_COLLAPSED_RELATION, new ImageIcon(
				ResourceLoader.getResource(packagePath
						.concat("CollapsedRelation.gif"))));
		myRegistryMap.put(RELATIONRENDERER_INFERED_RELATION_OPEN,
				new ImageIcon(ResourceLoader.getResource(packagePath
						.concat("InferedRelationOpen.gif"))));
		myRegistryMap.put(RELATIONRENDERER_INFERED_RELATION_COLLAPSED,
				new ImageIcon(ResourceLoader.getResource(packagePath
						.concat("InferedRelationCollapsed.gif"))));
		myRegistryMap.put(RELATIONRENDERER_IDENTITY_RELATION, new ImageIcon(
				ResourceLoader.getResource(packagePath
						.concat("IdentityRelation.gif"))));
		myRegistryMap.put(RELATIONRENDERER_UPARROW, new ImageIcon(
				ResourceLoader.getResource(packagePath.concat("UpArrow.gif"))));
		myRegistryMap.put(RELATIONRENDERER_UPARROW_ROLLOVER, new ImageIcon(
				ResourceLoader.getResource(packagePath
						.concat("UpArrowRollOver.gif"))));
		myRegistryMap.put(RELATIONRENDERER_UPARROW_SELECTED, new ImageIcon(
				ResourceLoader.getResource(packagePath
						.concat("UpArrowSelected.gif"))));
		myRegistryMap.put(RELATIONRENDERER_RIGHTARROW, new ImageIcon(
				ResourceLoader
						.getResource(packagePath.concat("RightArrow.gif"))));
		myRegistryMap.put(RELATIONRENDERER_RIGHTARROW_ROLLOVER, new ImageIcon(
				ResourceLoader.getResource(packagePath
						.concat("RightArrowRollOver.gif"))));
		myRegistryMap.put(RELATIONRENDERER_RIGHTARROW_SELECTED, new ImageIcon(
				ResourceLoader.getResource(packagePath
						.concat("RightArrowSelected.gif"))));
		myRegistryMap.put(RELATIONRENDERER_DOWNARROW,
				new ImageIcon(ResourceLoader.getResource(packagePath
						.concat("DownArrow.gif"))));
		myRegistryMap.put(RELATIONRENDERER_DOWNARROW_ROLLOVER, new ImageIcon(
				ResourceLoader.getResource(packagePath
						.concat("DownArrowRollOver.gif"))));
		myRegistryMap.put(RELATIONRENDERER_DOWNARROW_SELECTED, new ImageIcon(
				ResourceLoader.getResource(packagePath
						.concat("DownArrowSelected.gif"))));
		myRegistryMap.put(RELATIONRENDERER_LEFTARROW,
				new ImageIcon(ResourceLoader.getResource(packagePath
						.concat("LeftArrow.gif"))));
		myRegistryMap.put(RELATIONRENDERER_LEFTARROW_ROLLOVER, new ImageIcon(
				ResourceLoader.getResource(packagePath
						.concat("LeftArrowRollOver.gif"))));
		myRegistryMap.put(RELATIONRENDERER_LEFTARROW_SELECTED, new ImageIcon(
				ResourceLoader.getResource(packagePath
						.concat("LeftArrowSelected.gif"))));
		this.fireUIResourceRegistryEvent(new UIResourceRegistryEvent(this));
	}

	/**
	 * Loads the default icons for custom gui components used by matrixbrowser
	 * into the <code>UIResourceRegistry</code>.
	 */
	public void loadMBDefaultIcons() {
		loadIcons(myUIResourcePackagePath);
	}

	/**
	 * loads a localization
	 * 
	 * @param packagePath
	 *            the path of the localization file
	 * @param resourceName
	 *            the filename of the localization file
	 */
	public void loadLocalization(final String packagePath, final String resourceName) {
		// load default localization boundle
		Properties localization = new Properties();

		try {
			localization.load(ResourceLoader.getResourceAsStream(packagePath
					.concat(resourceName)));
		} catch (IOException e) {
			throw new MissingResourceException(resourceName.concat(
					"could not be found on").concat(packagePath).concat("'!!"),
					getClass().getName(), null);
		}
		load(localization);
		this.fireUIResourceRegistryEvent(new UIResourceRegistryEvent(this));
	}

	/**
	 * Loads the default localization for custom gui components used by
	 * matrixbrowser into the <code>UIResourceRegistry</code>.
	 */
	public void loadMBDefaultLocalization() {
		loadLocalization(myUIResourcePackagePath,
				"defaultlocalization.properties");
	}

	/**
	 * Loads the default colors for custom gui components used by matrixbrowser
	 * into the <code>UIResourceRegistry</code>.
	 */
	public void loadMBDefaultColors() {
		ColorUIResource white = new ColorUIResource(Color.white);

		myRegistryMap.put(MATRIXBROWSER_BACKGROUND, white);

		// load NodeCloseUpView default colors
		myRegistryMap.put(NODECLOSEUPVIEW_BORDERCOLOR, new ColorUIResource(
				Color.darkGray));
		myRegistryMap.put(NODECLOSEUPVIEW_BACKGROUND, white);
		myRegistryMap.put(NODECLOSEUPVIEW_RELATEDNODESTABLE_BACKGROUND, white);
		myRegistryMap
				.put(NODECLOSEUPVIEW_RELATEDNODESTABLE_TABLEHEADER_BACKGROUND,
						white);
		myRegistryMap
				.put(
						NODECLOSEUPVIEW_RELATEDNODESTABLE_SHOWINFEREDRELATIONSPANEL_BACKGROUND,
						white);
		this.fireUIResourceRegistryEvent(new UIResourceRegistryEvent(this));
	}

	/**
	 * Loads the given map containing the key/localization mapping.
	 * 
	 * @param aDefaultMap
	 *            the key/localization mapping which MUST contain
	 *            <code>String</code>s.
	 * @param aDefaultMap
	 *            the key/localization mapping which MUST contain
	 *            <code>String</code>s.
	 * @see java.util.Map
	 */
	public void load(final Map<Object, Object> aDefaultMap) {
		myRegistryMap.putAll(aDefaultMap);
		this.fireUIResourceRegistryEvent(new UIResourceRegistryEvent(this));
	}

	/**
	 * Loads the given <code>Properties</code> as key/localization mapping.
	 * 
	 * @param aDefaultMap
	 *            the key/localization mapping
	 * @param aDefaultMap
	 *            the key/localization mapping
	 * @see java.util.Properties
	 */
	public void load(final Properties aDefaultMap) {
		myRegistryMap.putAll(aDefaultMap);
		this.fireUIResourceRegistryEvent(new UIResourceRegistryEvent(this));
	}

	/**
	 * @return the actual key/resource mapping
	 */
	public Map<Object, Object> getResourceMapping() {
		return myRegistryMap;
	}

	/**
	 * Loads the given <code>ResourceBundle</code> as key/localization mapping.
	 * 
	 * @param aLocalization
	 *            the key/localization mapping
	 * @param aLocalization
	 *            the key/localization mapping
	 * @see java.util.ResourceBundle
	 */
	public void load(final ResourceBundle aDefaultMap) {
		Enumeration<String> e = aDefaultMap.getKeys();

		while (e.hasMoreElements()) {
			String key = e.nextElement();
			myRegistryMap.put(key, aDefaultMap.getObject(key));
		}
		this.fireUIResourceRegistryEvent(new UIResourceRegistryEvent(this));
	}

	/**
	 * @return the path to the ui package containing the default localization
	 *         and icons
	 */
	public String getUIResourcePackagePath() {
		return myUIResourcePackagePath;
	}

	/**
	 * Sets the path to the mb ui package. This MUST consist out of the
	 * following files: BiDirectedInferedRelation.gif <br>
	 * BiDirectedRealRelation.gif <br>
	 * CollapsedRelation.gif <br>
	 * DownArrow.gif <br>
	 * DownArrowRollOver.gif <br>
	 * DownArrowSelected.gif <br>
	 * IdentityRelation.gif <br>
	 * InferedRelationCollapsed.gif <br>
	 * InferedRelationOpen.gif <br>
	 * LeftArrow.gif <br>
	 * LeftArrowRollOver.gif <br>
	 * LeftArrowSelected.gif <br>
	 * LeftDirectedInferedRelation.gif <br>
	 * LeftDirectedRealRelation.gif <br>
	 * NoDirectedInferedRelation.gif <br>
	 * NoDirectedRealRelation.gif <br>
	 * RealRelation.gif <br>
	 * RightArrow.gif <br>
	 * RightArrowRollOver.gif <br>
	 * RightArrowSelected.gif <br>
	 * RightDirectedInferedRelation.gif <br>
	 * RightDirectedRealRelation.gif <br>
	 * TreeCollapsed.gif <br>
	 * TreeLeaf.gif <br>
	 * TreeOpen.gif <br>
	 * UpArrow.gif <br>
	 * UpArrowRollOver.gif <br>
	 * UpArrowSelected.gif <br>
	 * defaultlocalization.properties <br>
	 * 
	 * @param aResourcePackagePath
	 *            the path to the ui package containing the default localization
	 *            and icons.
	 * @param aResourcePackagePath
	 *            the path to the ui package containing the default localization
	 *            and icons.
	 */
	public void setUIResourcePackagePath(final String aResourcePackagePath) {
		myUIResourcePackagePath = aResourcePackagePath;
	}

	/**
	 * @param aUIResourceRegistryEventListener
	 */
	public void addUIResourceRegistryEventListener(
			final UIResourceRegistryEventListener aUIResourceRegistryEventListener) {
		myEventListenerList.add(UIResourceRegistryEventListener.class,
				aUIResourceRegistryEventListener);
	}

	/**
	 * @param aUIResourceRegistryEventListener
	 */
	public void removeUIResourceRegistryEventListener(
			final UIResourceRegistryEventListener aUIResourceRegistryEventListener) {
		myEventListenerList.remove(UIResourceRegistryEventListener.class,
				aUIResourceRegistryEventListener);
	}

	/**
	 * Fires the given event for all registered listeners
	 * 
	 * @param aEvent
	 *            the <code>UIResourceRegistryEvent</code> to fire
	 */
	private void fireUIResourceRegistryEvent(final UIResourceRegistryEvent aEvent) {
		Object[] listeners = myEventListenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == UIResourceRegistryEventListener.class) {
				((UIResourceRegistryEventListener) listeners[i + 1])
						.onUIResourceRegistryChanged(null);
			}
		}
	}
}