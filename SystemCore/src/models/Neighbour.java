package models;

public class Neighbour {
	private int distance;
	private RoadPart from;
	private RoadPart to;
	private Road neighbour;
	
	enum RoadPart { Start, End }
}
