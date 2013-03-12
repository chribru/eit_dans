package dans.model;

public class Warning {
	public Warning(Road road, String[] texts){
		this.road = road;
		this.warnings = texts;
	}
	
	public Road getRoad() {
		return road;
	}
	public String[] getWarnings() {
		return warnings;
	}
	private Road road;
	private String[] warnings;
}
