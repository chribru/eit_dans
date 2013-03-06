package models;

public class Road {

	private String code;
	private String name;
	private RoadStatus status;
	private int length;
	
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
}
