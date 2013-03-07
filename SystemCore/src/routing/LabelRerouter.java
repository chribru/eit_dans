package routing;

import java.util.ArrayList;

import models.Neighbour;
import models.Road;
import models.Road.RoadStatus;
/**
 * This rerouter reroutes the network by using a labeling algorithm. The problem is considered
 * as a shortest path problem with resource constraints where the cost of the path is the 
 * actual path and the resource is the highest risk level aquired so far. Risk level is restricted to
 * be one less than the risk of the road we are rerouting for open roads with risk and no limit when
 * rerouting closed roads. Open roads with no risk are not rerouted.
 * @author Sindre
 *
 */
public class LabelRerouter implements IRerouter {
	
	/**
	 * 1st and 2nd index indicate nodepairs, both startpoint and endpoint of a road have
	 * their own node. Startnodes are indexed from 0 to n-1 and endnodes are indexed from
	 * n to 2n-1
	 * Attributes for arcs are status (> 0 for open, <=0 for closed), cost and risk
	 */
	private int[][][] network;
	
	private static final int NUMBER_OF_ARC_ATTRIBUTES = 3;
	private static final int REROUTE_RISK_THRESHOLD = 1;
	
	@Override
	public ArrayList<Road> Reroute(ArrayList<Road> roads) {
		// Create network to reroute in
		System.out.println("Starting rerouting roads");
		System.out.println("- creating network");
		createNetwork(roads);
		System.out.println("- rerouting roads");
		// Reroute all roads in network that meet criteria
		int networkIndex = 0;
		for (Road road : roads) {
			if(road.getStatus() == RoadStatus.Closed){
				System.out.println(String.format(" - Rerouting road %s %s due to being closed", road.getCode(), road.getName()));
				// Find shortest alternate route with any risk level
				labelReroute(networkIndex, networkIndex + roads.size(), Road.MAX_RISK_LEVEL);
			} else if(road.getRiskLevel() >= REROUTE_RISK_THRESHOLD){
				// Find shortest alternate route with lower risk level
				System.out.println(String.format(" - Rerouting road %s %s due to having above threshold risk", road.getCode(), road.getName()));
				labelReroute(networkIndex, networkIndex + roads.size(), road.getRiskLevel() - 1);
			}
			networkIndex++;
		}
		return null;
	}
	
	private void createNetwork(ArrayList<Road> roads){
		network = new int[roads.size()*2][roads.size()*2][NUMBER_OF_ARC_ATTRIBUTES];
		int roadIndex = 0;
		for (Road road : roads) {
			ArrayList<Neighbour> neighbours = road.getNeighbours();
			roadIndex++;
		}
	}
	
	private void labelReroute(int startNode, int endNode, int maxRisk){
		// Initialization
		RouteLabel bestRoute = null;
		ArrayList<RouteLabel> activeLabels = new ArrayList<RouteLabel>();
		RouteLabel initialLabel = new RouteLabel(startNode);
		activeLabels.add(initialLabel);
		int index = 0;
		while(activeLabels.size() > index){
			RouteLabel currentLabel = activeLabels.get(index);
			if(!currentLabel.isDominated()){
				ArrayList<RouteLabel> newLabels = expand(currentLabel, maxRisk, bestRoute);
				for (RouteLabel routeLabel : newLabels) {
					this.dominateLabels(routeLabel, activeLabels);
					if(!routeLabel.isDominated()){
						activeLabels.add(routeLabel);
					}
				}
			}
			index++;
		}
		System.out.println(String.format("   - road rerouted after expanding %,d labels", activeLabels.size()));
	}

	private void dominateLabels(RouteLabel newLabel,
		ArrayList<RouteLabel> activeLabels) {
		for (RouteLabel oldLabel : activeLabels) {
			if(!oldLabel.isDominated() && newLabel.getLastNode() == oldLabel.getLastNode()){
				int newCost = newLabel.getCost();
				int newRisk = newLabel.getRiskLevel();
				int oldCost = oldLabel.getCost();
				int oldRisk = oldLabel.getRiskLevel();
				if(oldRisk <= newRisk && oldCost <= newCost){
					newLabel.setDominated(true);
					break; // no point in continuing
				} else if(newRisk <= oldRisk && newCost < oldCost) {
					oldLabel.setDominated(true);
				}
			
			}
		}
	}

	private ArrayList<RouteLabel> expand(RouteLabel currentLabel, int maxRisk, RouteLabel bestRoute) {
		ArrayList<RouteLabel> newLabels = new ArrayList<RouteLabel>();
		int lastNode = currentLabel.getLastNode();
		int cost = currentLabel.getCost();
		//int riskLevel = currentLabel.getRiskLevel();
		for(int nextNode = 0; nextNode < network[lastNode].length; nextNode++){
			int[] arc = network[lastNode][nextNode];
			// arc must be open, have below max risk and cost equal or below best complete route if existing
			if(arc[0] > 0 && arc[2] <= maxRisk && bestRoute == null || cost + arc[1] <= bestRoute.getCost()){
				RouteLabel newLabel = new RouteLabel(currentLabel, nextNode, arc[1], arc[2]);
				newLabels.add(newLabel);
			}
		}
		return newLabels;
	}
	
	




}
