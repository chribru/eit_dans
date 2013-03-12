package dans.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Road {
	private RoadStatus status;
	private String idCode;
	private String name;
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener){
		pcs.removePropertyChangeListener(listener);
	}
	
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
		pcs.firePropertyChange("Status", null, null);
	}
	public String getIdCode() {
		return idCode;
	}
	public void setIdCode(String idCode) {
		this.idCode = idCode;
		pcs.firePropertyChange("IdCode", null, null);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		pcs.firePropertyChange("Name", null, null);
	}
}
