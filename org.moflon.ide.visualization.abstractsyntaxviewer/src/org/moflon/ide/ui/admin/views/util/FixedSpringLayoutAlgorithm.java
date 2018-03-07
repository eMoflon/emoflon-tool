/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package org.moflon.ide.ui.admin.views.util;

import java.util.HashMap;
import java.util.Random;

import org.eclipse.gef4.zest.layouts.LayoutAlgorithm;
import org.eclipse.gef4.zest.layouts.algorithms.AlgorithmHelper;
import org.eclipse.gef4.zest.layouts.dataStructures.DisplayIndependentDimension;
import org.eclipse.gef4.zest.layouts.dataStructures.DisplayIndependentPoint;
import org.eclipse.gef4.zest.layouts.dataStructures.DisplayIndependentRectangle;
import org.eclipse.gef4.zest.layouts.interfaces.ConnectionLayout;
import org.eclipse.gef4.zest.layouts.interfaces.EntityLayout;
import org.eclipse.gef4.zest.layouts.interfaces.LayoutContext;
import org.eclipse.gef4.zest.layouts.interfaces.LayoutListener;
import org.eclipse.gef4.zest.layouts.interfaces.NodeLayout;
import org.eclipse.gef4.zest.layouts.interfaces.SubgraphLayout;

public class FixedSpringLayoutAlgorithm implements LayoutAlgorithm {
	public static final int DEFAULT_SPRING_ITERATIONS = 1000;

	public static final long MAX_SPRING_TIME = 10000L;

	public static final boolean DEFAULT_SPRING_RANDOM = true;

	public static final double DEFAULT_SPRING_MOVE = 1.0D;

	public static final double DEFAULT_SPRING_STRAIN = 1.0D;

	public static final double DEFAULT_SPRING_LENGTH = 3.0D;

	public static final double DEFAULT_SPRING_GRAVITATION = 2.0D;

	protected static final double MIN_DISTANCE = 1.0D;

	protected static final double EPSILON = 0.001D;

	private int sprIterations;

	private long maxTimeMS;

	private boolean sprRandom;

	private double sprMove;

	private double sprStrain;

	private double sprLength;

	private double sprGravitation;

	private boolean resize;

	private int iteration;

	private double[][] srcDestToSumOfWeights;

	private EntityLayout[] entities;

	private double[] forcesX;

	private double[] forcesY;

	private double[] locationsX;

	private double[] locationsY;

	private double[] sizeW;

	private double[] sizeH;

	private DisplayIndependentRectangle bounds;

	private double boundsScaleX;

	private double boundsScaleY;

	public boolean fitWithinBounds;

	private LayoutContext context;

	private long startTime;

	private long seed;

	/** @deprecated */
	@Deprecated
	public FixedSpringLayoutAlgorithm(final int style) {
		setResizing(style != 1);
	}

	public FixedSpringLayoutAlgorithm() {
		this.sprIterations = 1000;

		this.maxTimeMS = 10000L;

		this.sprRandom = true;

		this.sprMove = 1.0D;

		this.sprStrain = 1.0D;

		this.sprLength = 3.0D;

		this.sprGravitation = 2.0D;

		this.resize = false;

		this.boundsScaleX = 0.2D;
		this.boundsScaleY = 0.2D;

		this.fitWithinBounds = true;

		this.startTime = 0L;

		this.seed = 0L;
	}

	public void setSeed(final long seed) {
		this.seed = seed;
	}

	@Override
	public void applyLayout(final boolean clean) {
		initLayout();
		if (!(clean))
			return;
		do
			computeOneIteration();
		while (performAnotherNonContinuousIteration());

		saveLocations();
		if (this.resize) {
			AlgorithmHelper.maximizeSizes(this.entities);
		}
		if (this.fitWithinBounds) {
			DisplayIndependentRectangle bounds2 = new DisplayIndependentRectangle(this.bounds);
			int insets = 4;
			bounds2.x += insets;
			bounds2.y += insets;
			bounds2.width -= 2 * insets;
			bounds2.height -= 2 * insets;
			AlgorithmHelper.fitWithinBounds(this.entities, bounds2, this.resize);
		}
	}

	@Override
	public void setLayoutContext(final LayoutContext context) {
		this.context = context;
		this.context.addLayoutListener(new SpringLayoutListener());
		initLayout();
	}

	public void performNIteration(final int n) {
		if (this.iteration == 0) {
			this.entities = this.context.getEntities();
			loadLocations();
			initLayout();
		}
		this.bounds = this.context.getBounds();
		for (int i = 0; i < n; ++i) {
			computeOneIteration();
			saveLocations();
		}
		this.context.flushChanges(false);
	}

	public void performOneIteration() {
		if (this.iteration == 0) {
			this.entities = this.context.getEntities();
			loadLocations();
			initLayout();
		}
		this.bounds = this.context.getBounds();
		computeOneIteration();
		saveLocations();
		this.context.flushChanges(false);
	}

	public boolean isResizing() {
		return this.resize;
	}

	public void setResizing(final boolean resizing) {
		this.resize = resizing;
	}

	public void setSpringMove(final double move) {
		this.sprMove = move;
	}

	public double getSpringMove() {
		return this.sprMove;
	}

	public void setSpringStrain(final double strain) {
		this.sprStrain = strain;
	}

	public double getSpringStrain() {
		return this.sprStrain;
	}

	public void setSpringLength(final double length) {
		this.sprLength = length;
	}

	public long getSpringTimeout() {
		return this.maxTimeMS;
	}

	public void setSpringTimeout(final long timeout) {
		this.maxTimeMS = timeout;
	}

	public double getSpringLength() {
		return this.sprLength;
	}

	public void setSpringGravitation(final double gravitation) {
		this.sprGravitation = gravitation;
	}

	public double getSpringGravitation() {
		return this.sprGravitation;
	}

	public void setIterations(final int iterations) {
		this.sprIterations = iterations;
	}

	public int getIterations() {
		return this.sprIterations;
	}

	public void setRandom(final boolean random) {
		this.sprRandom = random;
	}

	public boolean getRandom() {
		return this.sprRandom;
	}

	private void initLayout() {
		this.entities = this.context.getEntities();
		this.bounds = this.context.getBounds();
		loadLocations();

		this.srcDestToSumOfWeights = new double[this.entities.length][this.entities.length];
		HashMap<EntityLayout, Integer> entityToPosition = new HashMap<>();
		for (int i = 0; i < this.entities.length; ++i) {
			entityToPosition.put(this.entities[i], new Integer(i));
		}

		ConnectionLayout[] connections = this.context.getConnections();
		for (int i = 0; i < connections.length; ++i) {
			ConnectionLayout connection = connections[i];
			Integer source = entityToPosition.get(getEntity(connection.getSource()));
			Integer target = entityToPosition.get(getEntity(connection.getTarget()));
			if (source == null)
				continue;
			if (target == null)
				continue;
			double weight = connection.getWeight();
			weight = (weight <= 0.0D) ? 0.1D : weight;
			this.srcDestToSumOfWeights[source.intValue()][target.intValue()] += weight;
			this.srcDestToSumOfWeights[target.intValue()][source.intValue()] += weight;
		}

		if (this.sprRandom) {
			placeRandomly();
		}
		this.iteration = 1;

		this.startTime = System.currentTimeMillis();
	}

	private EntityLayout getEntity(final NodeLayout node) {
		if (!(node.isPruned()))
			return node;
		SubgraphLayout subgraph = node.getSubgraph();
		if (subgraph.isGraphEntity())
			return subgraph;
		return null;
	}

	private void loadLocations() {
		if ((this.locationsX == null) || (this.locationsX.length != this.entities.length)) {
			int length = this.entities.length;
			this.locationsX = new double[length];
			this.locationsY = new double[length];
			this.sizeW = new double[length];
			this.sizeH = new double[length];
			this.forcesX = new double[length];
			this.forcesY = new double[length];
		}
		for (int i = 0; i < this.entities.length; ++i) {
			DisplayIndependentPoint location = this.entities[i].getLocation();
			this.locationsX[i] = location.x;
			this.locationsY[i] = location.y;
			DisplayIndependentDimension size = this.entities[i].getSize();
			this.sizeW[i] = size.width;
			this.sizeH[i] = size.height;
		}
	}

	private void saveLocations() {
		if (this.entities == null)
			return;
		for (int i = 0; i < this.entities.length; ++i)
			this.entities[i].setLocation(this.locationsX[i], this.locationsY[i]);
	}

	private void setSprIterationsBasedOnTime() {
		if (this.maxTimeMS <= 0L) {
			return;
		}
		long currentTime = System.currentTimeMillis();
		double fractionComplete = (currentTime - this.startTime) / this.maxTimeMS;
		int currentIteration = (int) (fractionComplete * this.sprIterations);
		if (currentIteration > this.iteration)
			this.iteration = currentIteration;
	}

	protected boolean performAnotherNonContinuousIteration() {
		setSprIterationsBasedOnTime();
		return (this.iteration <= this.sprIterations);
	}

	protected int getCurrentLayoutStep() {
		return this.iteration;
	}

	protected int getTotalNumberOfLayoutSteps() {
		return this.sprIterations;
	}

	protected void computeOneIteration() {
		computeForces();
		computePositions();
		DisplayIndependentRectangle currentBounds = getLayoutBounds();
		improveBoundScaleX(currentBounds);
		improveBoundScaleY(currentBounds);
		moveToCenter(currentBounds);
		this.iteration += 1;
	}

	public void placeRandomly() {
		if (this.locationsX.length == 0) {
			return;
		}

		if (this.locationsX.length == 1) {
			this.locationsX[0] = (this.bounds.x + 0.5D * this.bounds.width);
			this.locationsY[0] = (this.bounds.y + 0.5D * this.bounds.height);
		} else {
			this.locationsX[0] = this.bounds.x;
			this.locationsY[0] = this.bounds.y;
			this.locationsX[1] = (this.bounds.x + this.bounds.width);
			this.locationsY[1] = (this.bounds.y + this.bounds.height);
			Random random = new Random(seed);
			for (int i = 2; i < this.locationsX.length; ++i) {
				this.locationsX[i] = (this.bounds.x + random.nextDouble()// Math.random()
						* this.bounds.width);
				this.locationsY[i] = (this.bounds.y + random.nextDouble()// Math.random()
						* this.bounds.height);
			}
		}
	}

	protected void computeForces() {
		double[][] forcesX = new double[2][this.forcesX.length];
		double[][] forcesY = new double[2][this.forcesX.length];
		double[] locationsX = new double[this.forcesX.length];
		double[] locationsY = new double[this.forcesX.length];

		for (int j = 0; j < 2; ++j) {
			for (int i = 0; i < this.forcesX.length; ++i) {
				forcesX[j][i] = 0.0D;
				forcesY[j][i] = 0.0D;
				locationsX[i] = this.locationsX[i];
				locationsY[i] = this.locationsY[i];
			}

		}

		for (int k = 0; k < 2; ++k) {
			for (int i = 0; i < this.locationsX.length; ++i) {
				for (int j = i + 1; j < locationsX.length; ++j) {
					double dx = (locationsX[i] - locationsX[j]) / this.bounds.width / this.boundsScaleX;
					double dy = (locationsY[i] - locationsY[j]) / this.bounds.height / this.boundsScaleY;
					double distance_sq = dx * dx + dy * dy;

					distance_sq = Math.max(1.0D, distance_sq);
					double distance = Math.sqrt(distance_sq);

					double sumOfWeights = this.srcDestToSumOfWeights[i][j];
					double f;
					if (sumOfWeights > 0.0D) {
						f = -this.sprStrain * Math.log(distance / this.sprLength) * sumOfWeights;
					} else {
						f = this.sprGravitation / distance_sq;
					}
					double dfx = f * dx / distance;
					double dfy = f * dy / distance;

					forcesX[k][i] += dfx;
					forcesY[k][i] += dfy;

					forcesX[k][j] -= dfx;
					forcesY[k][j] -= dfy;
				}
			}

			for (int i = 0; i < this.entities.length; ++i) {
				if (this.entities[i].isMovable()) {
					double deltaX = this.sprMove * forcesX[k][i];
					double deltaY = this.sprMove * forcesY[k][i];

					double dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
					double maxMovement = 0.2D * this.sprMove;
					if (dist > maxMovement) {
						deltaX *= maxMovement / dist;
						deltaY *= maxMovement / dist;
					}

					locationsX[i] += deltaX * this.bounds.width * this.boundsScaleX;
					locationsY[i] += deltaY * this.bounds.height * this.boundsScaleY;
				}
			}

		}

		for (int i = 0; i < this.entities.length; ++i) {
			if (forcesX[0][i] * forcesX[1][i] < 0.0D) {
				this.forcesX[i] = 0.0D;
			} else {
				this.forcesX[i] = forcesX[1][i];
			}

			if (forcesY[0][i] * forcesY[1][i] < 0.0D) {
				this.forcesY[i] = 0.0D;
			} else {
				this.forcesY[i] = forcesY[1][i];
			}
		}
	}

	protected void computePositions() {
		for (int i = 0; i < this.entities.length; ++i)
			if (this.entities[i].isMovable()) {
				double deltaX = this.sprMove * this.forcesX[i];
				double deltaY = this.sprMove * this.forcesY[i];

				double dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
				double maxMovement = 0.2D * this.sprMove;
				if (dist > maxMovement) {
					deltaX *= maxMovement / dist;
					deltaY *= maxMovement / dist;
				}

				this.locationsX[i] += deltaX * this.bounds.width * this.boundsScaleX;
				this.locationsY[i] += deltaY * this.bounds.height * this.boundsScaleY;
			}
	}

	private DisplayIndependentRectangle getLayoutBounds() {
		double minY;
		double minX = minY = (1.0D / 0.0D);
		double maxY;
		double maxX = maxY = (-1.0D / 0.0D);

		for (int i = 0; i < this.locationsX.length; ++i) {
			maxX = Math.max(maxX, this.locationsX[i] + this.sizeW[i] / 2.0D);
			minX = Math.min(minX, this.locationsX[i] - (this.sizeW[i] / 2.0D));
			maxY = Math.max(maxY, this.locationsY[i] + this.sizeH[i] / 2.0D);
			minY = Math.min(minY, this.locationsY[i] - (this.sizeH[i] / 2.0D));
		}
		return new DisplayIndependentRectangle(minX, minY, maxX - minX, maxY - minY);
	}

	private void improveBoundScaleX(final DisplayIndependentRectangle currentBounds) {
		double boundaryProportionX = currentBounds.width / this.bounds.width;

		if (boundaryProportionX < 0.9D) {
			this.boundsScaleX *= 1.01D;
		} else if (boundaryProportionX > 1.0D) {
			if (this.boundsScaleX < 0.01D)
				return;
			this.boundsScaleX /= 1.01D;
		}
	}

	private void improveBoundScaleY(final DisplayIndependentRectangle currentBounds) {
		double boundaryProportionY = currentBounds.height / this.bounds.height;

		if (boundaryProportionY < 0.9D) {
			this.boundsScaleY *= 1.01D;
		} else if (boundaryProportionY > 1.0D) {
			if (this.boundsScaleY < 0.01D)
				return;
			this.boundsScaleY /= 1.01D;
		}
	}

	private void moveToCenter(final DisplayIndependentRectangle currentBounds) {
		double moveX = currentBounds.x + currentBounds.width / 2.0D - (this.bounds.x + this.bounds.width / 2.0D);
		double moveY = currentBounds.y + currentBounds.height / 2.0D - (this.bounds.y + this.bounds.height / 2.0D);
		for (int i = 0; i < this.locationsX.length; ++i) {
			this.locationsX[i] -= moveX;
			this.locationsY[i] -= moveY;
		}
	}

	class SpringLayoutListener implements LayoutListener {
		@Override
		public boolean nodeMoved(final LayoutContext context, final NodeLayout node) {
			for (int i = 0; i < FixedSpringLayoutAlgorithm.this.entities.length; ++i) {
				if (FixedSpringLayoutAlgorithm.this.entities[i] == node) {
					FixedSpringLayoutAlgorithm.this.locationsX[i] = node.getLocation().x;
					FixedSpringLayoutAlgorithm.this.locationsY[i] = node.getLocation().y;
				}

			}

			return false;
		}

		@Override
		public boolean nodeResized(final LayoutContext context, final NodeLayout node) {
			return false;
		}

		@Override
		public boolean subgraphMoved(final LayoutContext context, final SubgraphLayout subgraph) {
			return false;
		}

		@Override
		public boolean subgraphResized(final LayoutContext context, final SubgraphLayout subgraph) {
			return false;
		}
	}
}