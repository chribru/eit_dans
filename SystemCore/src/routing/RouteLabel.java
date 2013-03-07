package routing;

import java.util.ArrayList;

public class RouteLabel {
	
	private boolean dominated = false;
	
	// Factor to minimize
	private int cost;

	// Resources
	private int riskLevel;
	
	// Path of label
	private int[] path;
	
	public RouteLabel() {
		cost = 0;
		riskLevel = 0;
		path = new int[] {};
	}

	public RouteLabel(int startNode) {
		this();
		this.path = new int[] {startNode};
	}
	
	public RouteLabel(RouteLabel parent, int nextNode, int arcCost, int arcRisk){
		this.cost = parent.getCost() + arcCost;
		this.riskLevel = Math.max(parent.getRiskLevel(), arcRisk);
		int[] parentPath = parent.getPath();
		this.path = new int[parentPath.length + 1];
		System.arraycopy(parentPath, 0, this.path, 0, parentPath.length);
		this.path[this.path.length-1] = nextNode;
	}
	
	public boolean isDominated() {
		return dominated;
	}

	public void setDominated(boolean dominated) {
		this.dominated = dominated;
	}

	public int getCost() {
		return cost;
	}

	public int getRiskLevel() {
		return riskLevel;
	}

	public int[] getPath() {
		return path;
	}
	
	public int getLastNode() {
		return this.path[this.path.length-1];
	}

}
