package dans.view;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import dans.model.Warning;

public class Sign extends JPanel {
	private static final long serialVersionUID = 391385783177984311L;
	private JLabel htmlLabel;
	public Sign(){
		htmlLabel = new JLabel();
		htmlLabel.setBackground(Color.BLACK);
		add(htmlLabel);
		
	}
	public void setWarning(Warning warning){
		htmlLabel.setText(htmlify(warning));
	}
	
	private static String htmlify(Warning warning){
		//TODO: Translation into HTML.
		return null;
	}
}
