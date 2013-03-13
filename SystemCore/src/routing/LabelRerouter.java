package routing;

import java.util.ArrayList;
import java.util.HashMap;

import models.RoadLink;
import models.RoadLink.RoadPart;
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
	 * 1st and 2nd index indicate arc start node and end node, both start point and end point of a road have
	 * their own node. Start nodes are indexed from 0 to n-1 and end nodes are indexed from
	 * n to 2n-1. 3rd index address attributes of arc.
	 * Attributes for arcs are status (> 0 for open, <=0 for closed), cost and risk
	 */
	private int[][][] network;
	
	private static final int NUMBER_OF_ARC_ATTRIBUTES = 3;
	private static final int REROUTE_RISK_THRESHOLD = 1;
	private static final double MAX_REROUTE_RISK_DISTANCE_PROPORTION = 0.8;
	private static final boolean ASSUME_SYMMETRIC = true;
	
	@Override
	public ArrayList<Road> Reroute(ArrayList<Road> roads, ArrayList<RoadLink> roadLinks) {
		// Create network to reroute in
		System.out.println("Starting rerouting roads");
		System.out.println("- creating network");
		createNetwork(roads, roadLinks);
		System.out.println("- rerouting roads");
		// Reroute all roads in network that meet criteria
		int networkIndex = 0;
		for (Road road : roads) {
			if(road.getStatus() == RoadStatus.Closed || road.getRiskLevel() >= REROUTE_RISK_THRESHOLD){
				RouteLabel bestReroute = null;
				if(road.getStatus() == RoadStatus.Closed){
					System.out.println(String.format(" - Rerouting road %s %s due to being closed", road.getCode(), road.getName()));
					// Find shortest alternate route with any risk level
					bestReroute = labelReroute(networkIndex, networkIndex + roads.size(), Integer.MAX_VALUE);
				} else {
					// Temporarily disable rerouted road
					int status = network[networkIndex][networkIndex+roads.size()][0];
					network[networkIndex][networkIndex+roads.size()][0] = 0;
					// Find shortest alternate route with at most 80% risk*distance of the orignial route
					System.out.println(String.format(" - Rerouting road %s %s due to having above threshold risk", road.getCode(), road.getName()));
					bestReroute = labelReroute(networkIndex, networkIndex + roads.size(),
							(int)((road.getRiskLevel() - 1)*road.getLength()*LabelRerouter.MAX_REROUTE_RISK_DISTANCE_PROPORTION));
					// Reenable rerouted road
					network[networkIndex][networkIndex+roads.size()][0] = status;
				}
				if(bestReroute != null){
					System.out.println(String.format("  - Cost: %d, Riskdistance: %d", bestReroute.getCost(), bestReroute.getRiskDistance()));
					System.out.println("  - Reroute path (repeated name => actual traversal, single => visit of end point ");
					int[] path = bestReroute.getPath();
					for (int i : path) {
						Road pathroad = roads.get(i >= roads.size()? i - roads.size() : i);
						System.out.println(String.format("   - node %d: %s %s", i, pathroad.getCode(), pathroad.getName()));
					}
				} else {
					System.out.println("  - no feasible reroute path found");
				}
			}
			
			networkIndex++;
		}
		return null;
	}
	
	private void createNetwork(ArrayList<Road> roads, ArrayList<RoadLink> roadLinks){
		// initialization
		int totalRoads = roads.size();
		network = new int[totalRoads*2][totalRoads*2][NUMBER_OF_ARC_ATTRIBUTES];
		// set up road to node relation - needed for adding road links
		HashMap<Integer, Integer> roadIdToNode = new HashMap<Integer, Integer>();
		// Add the roads themselves to network
		int index = 0;
		for (Road road : roads) {
			roadIdToNode.put(road.getId(), index);
			// Add road to network as long as not closed
			if(road.getStatus() != RoadStatus.Closed){
				network[index][index+totalRoads] = new int[]{1,road.getLength(), road.getRiskLevel()};
				if(LabelRerouter.ASSUME_SYMMETRIC){
					network[index+totalRoads][index] = new int[]{1,road.getLength(), road.getRiskLevel()};
				}
			}
			index++;
		}
		// Add links between roads
		for (RoadLink roadLink : roadLinks) {
			Road fromRoad = roadLink.getFromRoad();
			Road toRoad = roadLink.getToRoad();
			int startNodeIndex = roadLink.getFrom() == RoadPart.Start ? roadIdToNode.get(fromRoad.getId()) 
					: roadIdToNode.get(fromRoad.getId()) + totalRoads;
			int endNodeIndex = roadLink.getTo() == RoadPart.Start ? roadIdToNode.get(toRoad.getId()) 
					: roadIdToNode.get(toRoad.getId()) + totalRoads;
			network[startNodeIndex][endNodeIndex] = new int[]{1,roadLink.getDistance(), 0};
			if(LabelRerouter.ASSUME_SYMMETRIC){
				network[endNodeIndex][startNodeIndex] = new int[]{1,roadLink.getDistance(), 0};
			}
		}
	}
	
	private RouteLabel labelReroute(int startNode, int endNode, int maxRisk){
		// Initialization
		RouteLabel bestRoute = null;	// Keeps track of so far best finishing label
		ArrayList<RouteLabel> activeLabels = new ArrayList<RouteLabel>();	// Keeps track of all labels
		RouteLabel initialLabel = new RouteLabel(startNode);	// Starting base from start node
		activeLabels.add(initialLabel);
		int index = 0;
		// as long as there are more labels to explore
		while(activeLabels.size() > index){
			// fetch current label
			RouteLabel currentLabel = activeLabels.get(index);
			// if current label has not been dominated by other label - explore it
			if(!currentLabel.isDominated()){
				// expand current label along paths that is within max risk and not longer than current best complete path
				ArrayList<RouteLabel> newLabels = expand(currentLabel, maxRisk, bestRoute != null ? bestRoute.getCost() : Integer.MAX_VALUE);
				// for all resulting new labels
				for (RouteLabel newLabel : newLabels) {
					// try dominate against all labels in active labels
					this.dominateLabels(newLabel, activeLabels);
					// if new label was not it self dominated
					if(!newLabel.isDominated()){
						// add label to list of active labels
						activeLabels.add(newLabel);
						// check if route is complete and if better than current best
						if(newLabel.getLastNode() == endNode && (bestRoute == null || newLabel.getCost() < bestRoute.getCost())){
							bestRoute = newLabel;
						}
					}
				}
			}
			index++;
		}
		System.out.println(String.format("  - rerouting finished after expanding %,d labels", activeLabels.size()));
		return bestRoute;
	}

	private void dominateLabels(RouteLabel newLabel, ArrayList<RouteLabel> activeLabels) {
		// For all existing labels
		for (RouteLabel oldLabel : activeLabels) {
			// if existing label has not been dominated (if so, dominater will have better chance of dominating)
			// and both labels end at same node
			if(!oldLabel.isDominated() && newLabel.getLastNode() == oldLabel.getLastNode()){
				int newCost = newLabel.getCost();
				int newRisk = newLabel.getRiskDistance();
				int oldCost = oldLabel.getCost();
				int oldRisk = oldLabel.getRiskDistance();
				// if old label has equal or better both cost and risk
				if(oldRisk <= newRisk && oldCost <= newCost){
					// dominate new label and stop looping
					newLabel.setDominated(true);
					break;
				// if new label has better either risk or cost and at least equal the other 
				} else if(newRisk <= oldRisk && newCost < oldCost || newRisk < oldRisk && newCost <= oldCost) {
					// dominate old label and continue
					oldLabel.setDominated(true);
				}
			
			}
		}
	}

	private ArrayList<RouteLabel> expand(RouteLabel currentLabel, int maxRisk, int maxCost) {
		ArrayList<RouteLabel> newLabels = new ArrayList<RouteLabel>();
		int lastNode = currentLabel.getLastNode();
		int cost = currentLabel.getCost();
		int riskDistance = currentLabel.getRiskDistance();
		// for all plausible paths from end node
		for(int nextNode = 0; nextNode < network[lastNode].length; nextNode++){
			int[] arc = network[lastNode][nextNode];
			// arc must be open (>0), resulting risk and cost below or equal to maxRisk and maxCost
			if(arc[0] > 0 && riskDistance+arc[2]*arc[1] <= maxRisk && cost + arc[1] <= maxCost){
				// create a new label by expanding current label by the arc
				RouteLabel newLabel = new RouteLabel(currentLabel, nextNode, arc[1], arc[2]);
				// add new label to list of new labels
				newLabels.add(newLabel);
			}
		}
		return newLabels;
	}
	
	




}
