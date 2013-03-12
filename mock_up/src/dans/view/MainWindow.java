package dans.view;

import javax.swing.JFrame;

import dans.model.Road;
import dans.model.RoadStatus;
import dans.model.Warning;

public class MainWindow {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("DANS - DIGITEK's Avalanche Notification System");
		Sign sign = new Sign();
		Road road = new Road("E6", "Dovrefjell", RoadStatus.OPEN);
		Warning warning = new Warning(road, new String[]{"DANS er awesome!", 
				"Lav skredfare", "Pent vær"});
		sign.setWarning(warning);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(sign);
		frame.pack();
		frame.setVisible(true);
	}

}
