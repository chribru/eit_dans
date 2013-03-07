package models;

import java.util.ArrayList;

public class Road {

	public static final int MAX_RISK_LEVEL = 5;
	
	private String code;
	private String name;
	private RoadStatus status;
	private int length;
	private ArrayList<Integer> regionIds;
	private ArrayList<Neighbour> neighbours;
	
	public ArrayList<Neighbour> getNeighbours() {
		return neighbours;
	}

	public ArrayList<Integer> getRegionIds() {
		return regionIds;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RoadStatus getStatus() {
		return status;
	}

	public void setStatus(RoadStatus status) {
		this.status = status;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	public enum RoadStatus {Open, Closed, Column}

	public int getRiskLevel() {
		// TODO Auto-generated method stub
		return 0;
	}
}
