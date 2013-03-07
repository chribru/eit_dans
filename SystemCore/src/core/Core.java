package core;

import java.util.ArrayList;

import nve.ReportFetcher;

import routing.IRerouter;
import routing.LabelRerouter;
import models.NVERegion;
import models.Repository;
import models.Road;

public class Core {

	private Repository repository;
	
	public Core() {
		this.repository = new Repository();
	}
	
	private void Start() {
		// TODO Auto-generated method stub
		this.UpdateRiskForecasts();
		this.RerouteRoads();
		
	}
	
	private void UpdateRiskForecasts() {
		System.out.println("Fetching new risk forecasts");
		ArrayList<NVERegion> regions = repository.GetRegions();
		ReportFetcher fetcher = new ReportFetcher();
		fetcher.fetchRiskReports(regions);
	}
	
	public void RiskLevelsChanged() {
		System.out.println("New risk data for regions collected");
		RerouteRoads();
	}
	
	private void RerouteRoads() {
		ArrayList<Road> roads = repository.GetRoads();
		IRerouter rerouter = new LabelRerouter();
		rerouter.Reroute(roads);
		NotifySigns();
	}
	
	private void NotifySigns() {
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Core core = new Core();
		core.Start();
		
	}

	

}
