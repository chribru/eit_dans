package nve;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;

import models.NVERegion;

public class ReportFetcher {

	public void fetchRiskReports(ArrayList<NVERegion> regions) {
		// TODO Auto-generated method stub
		for (NVERegion region : regions) {
			fetchRiskReport(region);
		}
		
	}
	
	private void fetchRiskReport(NVERegion region) {
		String xmlString = fetchReportXML(region.getUrl());
	}
	
	private String fetchReportXML(String urlString){
		
		HttpURLConnection conn;
		String xmlString = null;
		try {
			URL url = new URL(urlString);

			conn = (HttpURLConnection) url.openConnection();
			
			if (conn.getResponseCode() != 200) {
				throw new IOException(conn.getResponseMessage());
			}
		
			// Buffer the result into a string
			BufferedReader rd = new BufferedReader(
				new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
		
			conn.disconnect();
			xmlString = sb.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		} finally {
			return xmlString;
		}
		
	}



}
