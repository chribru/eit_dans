package routing;

import java.util.ArrayList;

import models.RoadLink;
import models.Road;

public interface IRerouter {
	
	public ArrayList<Road> Reroute(ArrayList<Road> roads, ArrayList<RoadLink> roadLinks);

}
