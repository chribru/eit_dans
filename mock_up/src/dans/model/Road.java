package dans.model;

public class Road {
	private RoadStatus status;
	private String idCode;
	private String name;
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
