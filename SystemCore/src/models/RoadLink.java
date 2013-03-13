package models;

public class RoadLink {
	private int distance;
	private RoadPart fromPart;
	private RoadPart toPart;
	private Road fromRoad;
	private Road toRoad;
	
	public RoadLink(Road fromRoad, Road toRoad, RoadPart toPart, RoadPart fromPart, int distance) {
		this.fromRoad = fromRoad;
		this.fromPart = fromPart;
		this.toRoad = toRoad;
		this.toPart = toPart;
		this.distance = distance;
	}
	
	public int getDistance() {
		return distance;
	}

	public RoadPart getFrom() {
		return fromPart;
	}

	public RoadPart getTo() {
		return toPart;
	}

	public Road getToRoad() {
		return toRoad;
	}
	
	public Road getFromRoad() {
		return fromRoad;
	}
	
	public enum RoadPart { Start, End }
}
