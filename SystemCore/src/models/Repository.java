package models;

import java.util.ArrayList;

import models.Road.RoadStatus;
import models.RoadLink.RoadPart;

public class Repository {

	public ArrayList<Road> GetRoads(){
		ArrayList<Road> roads = new ArrayList<Road>();
		// add dummy roads
		roads.add(new Road(1, "e6", "Dovrefjell", RoadStatus.Open, 43, 1));
		roads.add(new Road(2, "e32", "Fjellveg", RoadStatus.Open, 88, 2));
		roads.add(new Road(3, "rv2", "Rasveg", RoadStatus.Open, 140, 3));
		roads.add(new Road(4, "rv1234", "Stengtveg", RoadStatus.Closed, 3, 3));
		return roads;
	}

	public ArrayList<RoadLink> GetRoadLinks() {
		ArrayList<RoadLink> roadLinks = new ArrayList<RoadLink>();
		// add dummy road link
		ArrayList<Road> roads = GetRoads();
		roadLinks.add(new RoadLink(roads.get(0), roads.get(1), RoadPart.Start, RoadPart.Start, 43));
		roadLinks.add(new RoadLink(roads.get(0), roads.get(3), RoadPart.Start, RoadPart.Start, 43));
		roadLinks.add(new RoadLink(roads.get(1), roads.get(2), RoadPart.Start, RoadPart.Start, 43));
		roadLinks.add(new RoadLink(roads.get(3), roads.get(0), RoadPart.End, RoadPart.End, 43));
		roadLinks.add(new RoadLink(roads.get(3), roads.get(1), RoadPart.End, RoadPart.End, 43));
		return roadLinks;
	}
	
	public ArrayList<NVERegion> GetRegions() {
		// TODO Auto-generated method stub
		ArrayList<NVERegion> regions = new ArrayList<NVERegion>();
		regions.add(new NVERegion(1, "jalla"));
		return regions;
	}

	public void updateRegions(ArrayList<NVERegion> regions) {
		// TODO Auto-generated method stub
		
	}
}
