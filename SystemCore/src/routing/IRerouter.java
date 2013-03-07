package routing;

import java.util.ArrayList;

import models.Road;

public interface IRerouter {
	
	public ArrayList<Road> Reroute(ArrayList<Road> roads);

}
