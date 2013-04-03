package models;

public class NVERegion {

	private int id;
	private String name;
	private int riskLevel;
	
	public NVERegion(int id, String name){
		this.id = id;
		this.name = name;
		this.riskLevel = 0;
	}

	public int getId() {
		return id;
	}
	
	public int getRiskLevel() {
		return riskLevel;
	}

	public String getName() {
		return name;
	}

	public static void parseXmlAndUpdateRegion(String xml, NVERegion region) {
		
	}
}
