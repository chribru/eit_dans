package dans.model;

public class Road {
	private RoadStatus status;
	private String idCode;
	private String name;
	
	public Road() {
		this.name = "";
		this.idCode = "";
		this.status = RoadStatus.OPEN;
	}
		
	public Road(String idCode, String name, RoadStatus status) {
		this.idCode = idCode;
		this.name = name;
		this.status = status;
	}

	public RoadStatus getStatus() {
		return status;
	}
	public void setStatus(RoadStatus status) {
		this.status = status;
	}
	public String getIdCode() {
		return idCode;
	}
	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
