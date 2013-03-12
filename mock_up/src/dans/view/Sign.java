package dans.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import dans.model.RoadStatus;
import dans.model.Warning;

public class Sign extends JPanel {
	private static final long serialVersionUID = 391385783177984311L;
	private JLabel htmlLabel;
	private int capacity;
	private Warning warning;
	
	public Sign(int capacity){
		this.capacity = capacity;
		setBackground(Color.BLACK);
		htmlLabel = new JLabel();
		htmlLabel.setForeground(Color.YELLOW);
		htmlLabel.setFont(htmlLabel.getFont().deriveFont(Font.PLAIN, 42f));
		add(htmlLabel);
	}
	
	public Sign() {
		this(2);
	}

	
	public void setWarning(Warning warning){
		if (this.warning == warning) return;
		
		if (this.warning != null)
			this.warning.removePropertyChangeListener(pcl);
		
		this.warning = warning;
		this.warning.addPropertyChangeListener(pcl);
		updateText();
	}
	
	public int getCapacity(){
		return this.capacity;
	}
	
	public void setCapacity(int capacity){
		this.capacity = capacity;
		updateText();
	}
	
	private void updateText() {
		htmlLabel.setText(htmlify(warning, this.capacity));
	}
	
	private static String htmlify(Warning warning ,int capacity){
		String[] warningStrings = warning.getWarnings();
		int showCount = Math.min(warningStrings.length, capacity);
		
		StringBuilder builder = new StringBuilder();
		builder.append("<html><center>");
		builder.append(warning.getRoad().getIdCode()+ "<br>");
		builder.append(warning.getRoad().getName()+ "<br>");
		builder.append(statusToHTML(warning.getRoad().getStatus())+ "<br>");
		for (int i=0; i<showCount; i++)
			builder.append(warningStrings[i] + "<br>");
		builder.append("</center></html>");
		
		return builder.toString();
	}
	
	private static String statusToHTML(RoadStatus status) {
		switch (status) {
		case OPEN: return "<font color=\"green\">Åpen</font>";
		case CLOSED: return "<font color=\"red\">Stengt</font>";
		case CONVOY: return "<font color=\"blue\">Kolonne</font>";
		default: return "";
		}
	}
	
	PropertyChangeListener pcl = new PropertyChangeListener() {
		
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			updateText();
		}
	};
}
