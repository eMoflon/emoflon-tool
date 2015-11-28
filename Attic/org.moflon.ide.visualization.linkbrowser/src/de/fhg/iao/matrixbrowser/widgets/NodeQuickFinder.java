/*
 * Created on 30.06.2005
 */
package de.fhg.iao.matrixbrowser.widgets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;

/**
 * @author Mr.Marwan
 * @author <a href=mailto:cs@christian-schott.de>Christian Schott</a>
 */
public class NodeQuickFinder extends JFrame implements ComponentListener {

	private static final Logger log = Logger.getLogger(NodeQuickFinder.class);
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	DefaultNodeCloseUpViewPanel panel;
	Node source;
	Hashtable<Node, Relation> hash = new Hashtable<Node, Relation>();
	String nodeDontExist;

	public NodeQuickFinder(DefaultNodeCloseUpViewPanel panel, Node source) {

		super("Graph for : " + source.getName());
		this.getContentPane().addComponentListener(this);
		this.panel = panel;
		this.source = source;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenSize.height = (int) Math.round(0.3 * screenSize.height);
		screenSize.width = (int) Math.round(0.3 * screenSize.width);
		this.setSize(screenSize.width, screenSize.height);
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	public NodeQuickFinder(String nodeDontExist) {
		super("Meldung");
		this.getContentPane().addComponentListener(this);
		this.source = null;
		this.nodeDontExist = nodeDontExist;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenSize.height = (int) Math.round(0.3 * screenSize.height);
		screenSize.width = (int) Math.round(0.3 * screenSize.width);
		this.setSize(screenSize.width, screenSize.height);
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public Hashtable<Node, Relation> compute() {

		Hashtable<Node, Relation> nodesHash = new Hashtable<Node, Relation>();
		Collection<Relation> ko = source.getMyRelations();
		Iterator<Relation> itr = ko.iterator();

		while (itr.hasNext()) {
			Relation relation = (Relation) itr.next();
			// if(relation.getDirection()==1){
			if (source.getID().equals(relation.getSourceNodeID())) {
				nodesHash.put(panel.getMatrixBrowserPanel().getTreeManager()
						.getModel().getNode(relation.getTargetNodeID()),
						relation);
			} else {
				nodesHash.put(panel.getMatrixBrowserPanel().getTreeManager()
						.getModel().getNode(relation.getSourceNodeID()),
						relation);
			}
			// }
		}

		return nodesHash;
	}

	public void paint(Graphics g) {
		String textUponLine;
		// falls der gesuchte Knoten nicht vorhanden ist.
		if (source == null) {
			g.setColor(Color.white);
			g.fillRect(0, 0, getSize().width, getSize().height);
			Font font = new Font("SansSerif", Font.PLAIN, 15);
			g.setFont(font);
			// FontMetrics fo=getFontMetrics(font);
			g.setColor(Color.BLACK);
			g.drawString("The Node " + nodeDontExist + " does not exist", 20,
					50);
		}
		// falls der Knoten vorhanden ist.
		else {
			Enumeration<Node> targetNodes;
			int i = 0;
			// f�ge alle Partnerknoten und deren Verbindungen in dem Hashtable
			// hash.
			hash = compute();

			int maxX = getSize().width - getInsets().left - getInsets().right;
			// int maxY=getSize().height-getInsets().top-getInsets().bottom;
			g.setColor(Color.white);
			g.fillRect(0, 0, getSize().width, getSize().height);
			Font font = new Font("SansSerif", Font.PLAIN, 15);
			g.setFont(font);
			FontMetrics fo = getFontMetrics(font);
			// zeichne den gesuchten Knoten.

			if (source.getColor() == null) {
				g.setColor(Color.GRAY);
			} else {
				g.setColor(source.getColor());
			}
			g.fillRect(3 * getInsets().left - 5, 2 * getInsets().top
					- fo.getAscent() + 3,
					fo.stringWidth(source.getName()) + 10, fo.getAscent());
			g.setColor(Color.BLACK);
			g.drawString(source.getName(), 3 * getInsets().left,
					2 * getInsets().top);
			// falls der Knoten gar keine Partnerknoten hat.
			if (hash.size() == 0) {
				g.drawString("There are no Connections for : "
						+ source.getName(), 3 * getInsets().left - 5
						+ fo.stringWidth(source.getName()) + 10,
						2 * getInsets().top);
			} else {
				targetNodes = hash.keys();
				// zeichne alle Partnerknoten.
				while (targetNodes.hasMoreElements()) {
					Node target = (Node) targetNodes.nextElement();
					if (target.getColor() == null) {
						g.setColor(Color.GRAY);
					} else {
						g.setColor(target.getColor());
					}
					g.fillRect(maxX - 10 * getInsets().right - 5, (2
							* getInsets().top - fo.getAscent() + 3)
							+ (i * 30), fo.stringWidth(target.toString()) + 10,
							fo.getAscent());
					g.setColor(Color.BLACK);
					g
							.drawString(target.toString(), maxX - 10
									* getInsets().right, 2 * getInsets().top
									+ (i * 30));
					// zeichne alle Verbindungen.
					Relation link = (Relation) hash.get(target);
					if (link.getColor() == null) {
						g.setColor(Color.BLACK);
					} else {
						g.setColor(link.getColor());
					}
					int lineX1 = 3 * getInsets().left - 5
							+ fo.stringWidth(source.getName() + 10);
					int lineY1 = 2 * getInsets().top - fo.getAscent() + 3
							+ fo.getAscent() / 2;
					int lineX2 = maxX - 10 * getInsets().right - 5;
					int lineY2 = 2 * getInsets().top - fo.getAscent() + 3
							+ fo.getAscent() / 2 + (i * 30);
					g.drawLine(lineX1, lineY1, lineX2, lineY2);
					if (link.getDescription() != null) {
						textUponLine = link.getDescription();
					} else {
						textUponLine = "linkdescription";
					}
					g.setFont(font.deriveFont(font.getSize2D() - 2));
					drawTextAboveLine(lineX1, lineY1, lineX2, lineY2,
							textUponLine, g);
					g.setFont(font);
					i++;
				}
			}
		}
	}

	/**
	 * Draws a text above an imaginary straight line. Draw a String on top of a
	 * straight line (not drawn by this method), from one point
	 * <code>(x1,y1)</code> to another point <code>(x2,y2)</code> This is done
	 * by calculating the line's angle from the given coordinates and then
	 * rendering the text in the same angle, with the given line at the
	 * <code>descent</code>(bottom) of the rendered text line. If text is not
	 * given, nothing will be drawn.
	 * 
	 * @param x1
	 *            the x coordinate of the line's starting point
	 * @param y1
	 *            the y coordinate of the line's starting point
	 * @param x2
	 *            the x coordinate of the line's end point
	 * @param y2
	 *            the y coordinate of the line's end point
	 * @param text
	 *            the text to render above given line
	 * @author schotti
	 */
	private void drawTextAboveLine(int x1, int y1, int x2, int y2, String text,
			Graphics g) {
		if (text == null)
			return;
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform affineTransform = g2d.getTransform();
		AffineTransform savedTransformation = g2d.getTransform();
		int descent = g.getFontMetrics().getDescent();
		int textwidth = g.getFontMetrics().stringWidth(text);
		// double linelength=Math.cbrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
		int startX = 0;
		int startY = 0;
		// We first calculate the geometry using a coordinate system where the
		// line is the x-axis, starting at the line's middle.
		// if (textwidth>linelength){
		// }else{
		startX = -textwidth / 2;
		startY = -descent;
		// }
		// Transform coordinate system

		// calculate translation of line middle
		double tx = x1 + ((x2 - x1) / (float) 2);
		double ty = y1 + ((y2 - y1) / (float) 2);
		affineTransform.translate(tx, ty);
		// calculate rotation
		// int m00=2*(x2-tx)/linelength;
		// int m10=2*(y2-ty)/linelength;

		if (x2 - x1 != 0) {
			double angle = Math.atan((float) (y2 - y1) / (x2 - x1));
			affineTransform.rotate(angle);
		} else {
			log.debug("rotation NOT activated");
			// y1 and y2 are the same, the line is exactly horizontal
			// we don't need to rotate the text.
		}

		// activate Transformer

		g2d.transform(affineTransform);
		g2d.drawString(text, startX, startY);
		g2d.setTransform(savedTransformation);
	}

	public void componentHidden(ComponentEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		repaint();
	}

}
