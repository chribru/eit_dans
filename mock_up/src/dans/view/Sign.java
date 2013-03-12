package dans.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import dans.model.Warning;

public class Sign extends JPanel {
	private static final long serialVersionUID = 391385783177984311L;
	private JLabel htmlLabel;
	private int capacity;
	
	public Sign(int capacity){
		this.capacity = capacity;
		setBackground(Color.BLACK);
		htmlLabel = new JLabel();
		htmlLabel.setForeground(Color.YELLOW);
		htmlLabel.setFont(htmlLabel.getFont().deriveFont(Font.PLAIN, 42f));
//		htmlLabel.setText("<html>Be <b>bold</b>!</html>");
		add(htmlLabel);
	}
	
	public Sign() {
		this(2);
	}

	
	public void setWarning(Warning warning){
		htmlLabel.setText(htmlify(warning));
	}
	
	private static String htmlify(Warning warning){
		StringBuilder builder = new StringBuilder();
		builder.append("<html><center>");
		builder.append(warning.getRoad().getIdCode()+ "<br>");
		builder.append(warning.getRoad().getName()+ "<br>");
		builder.append("</center></html>");
		return builder.toString();
	}
}
