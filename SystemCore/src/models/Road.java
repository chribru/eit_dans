package models;

import java.util.ArrayList;

public class Road {

	public static final int MAX_RISK_LEVEL = 5;
	
	private int id;
	private String code;
	private String name;
	private RoadStatus status;
	private int length;
	private int riskLevel;

	private ArrayList<Integer> regionIds;
	
	public Road() {
		this.regionIds = new ArrayList<Integer>();
	}
	
	public Road(int id, String code, String name, RoadStatus status, int length, int riskLevel){
		this();
		this.id = id;
		this.code = code;
		this.name = name;
		this.status = status;
		this.riskLevel = riskLevel;
		this.length = length;
	}
	
	public int getId() {
		return id;
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
		return riskLevel;
	}
	
	public void setRiskLevel(int riskLevel) {
		this.riskLevel = riskLevel;
	}
}
