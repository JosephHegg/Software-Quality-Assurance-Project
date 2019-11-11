package WorkingSpace;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CountryButtonListener implements ActionListener {
	
	Map map;
	
	public CountryButtonListener(Map map){
		this.map = map;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String[] splitText = e.getActionCommand().split(":");
		String name = splitText[0];
		
		map.setSelectedCountry(name);
	}

}

