package WorkingSpace;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Map {

	private JLabel picLabel;
	private boolean hasSelectedACountry = false;
	private int selectedCountry;
	private HashMap<String, Integer> countryIds = new HashMap<String, Integer>();
	private HashMap<Integer, JButton> buttonList = new HashMap<Integer, JButton>();
	private ResourceBundle bundle = ResourceBundle.getBundle("res.strings");

	public Map() throws IOException {
		makeCountryMap();

		Image image = ImageIO.read(new File("src/RiskImage.jpg"));

		picLabel = new JLabel(new ImageIcon(image));

		picLabel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		int i = 0;
		int j = 0;
		for (i = 0; i < 18; i++) {

			for (j = 0; j < 21; j++) {

				c.gridx = i;
				c.gridy = j;
				c.ipadx = 80;
				c.ipady = 20;
				JLabel label = new JLabel();
				label.setText("     ");
				picLabel.add(label, c);
			}
		}

		CountryButtonListener listener = new CountryButtonListener(this);

		addButtonsForNorthAmerica(picLabel, listener);
		addButtonsForSouthAmerica(picLabel, listener);
		addButtonsForAfrica(picLabel, listener);
		addButtonsForAustralia(picLabel, listener);
		addButtonsForEurope(picLabel, listener);
		addButtonsForAsia(picLabel, listener);
	}

	private void addButtonsForAsia(JLabel picLabel2, CountryButtonListener listener) {
		addButton(bundle.getString("siberiaString"), 12, 1, 32, listener);

		addButton(bundle.getString("uralString"), 11, 3, 36, listener);

		addButton(bundle.getString("afghanistanString"), 10, 6, 0, listener);

		addButton(bundle.getString("middleEastString"), 10, 10, 21, listener);

		addButton(bundle.getString("indiaString"), 11, 11, 15, listener);

		addButton(bundle.getString("chinaString"), 12, 9, 6, listener);

		addButton(bundle.getString("siamString"), 12, 13, 31, listener);

		addButton(bundle.getString("mongoliaString"), 13, 6, 22, listener);

		addButton(bundle.getString("irkutskString"), 13, 4, 17, listener);

		addButton(bundle.getString("yakutskString"), 13, 1, 41, listener);

		addButton(bundle.getString("kamchatkaString"), 14, 2, 19, listener);

		addButton(bundle.getString("japanString"), 14, 6, 18, listener);
	}

	private void addButtonsForEurope(JLabel picLabel2, CountryButtonListener listener) {
		addButton(bundle.getString("ukraineString"), 9, 3, 35, listener);

		addButton(bundle.getString("northEuropeString"), 8, 5, 25, listener);

		addButton(bundle.getString("scandinaviaString"), 9, 0, 30, listener);

		addButton(bundle.getString("icelandString"), 8, 2, 14, listener);

		addButton(bundle.getString("greatBritainString"), 7, 5, 12, listener);

		addButton(bundle.getString("southEuropeString"), 8, 7, 34, listener);

		addButton(bundle.getString("westEuropeString"), 7, 7, 39, listener);
	}

	private void addButtonsForAustralia(JLabel picLabel2, CountryButtonListener listener) {
		addButton(bundle.getString("eastAustraliaString"), 14, 20, 9, listener);

		addButton(bundle.getString("westAustraliaString"), 12, 20, 38, listener);

		addButton(bundle.getString("newGuineaString"), 14, 15, 23, listener);

		addButton(bundle.getString("indonesiaString"), 12, 15, 16, listener);
	}

	private void addButtonsForAfrica(JLabel picLabel2, CountryButtonListener listener) {
		addButton(bundle.getString("northAfricaString"), 8, 13, 24, listener);

		addButton(bundle.getString("egyptString"), 9, 11, 11, listener);

		addButton(bundle.getString("congoString"), 8, 17, 7, listener);

		addButton(bundle.getString("eastAfricaString"), 9, 14, 8, listener);

		addButton(bundle.getString("southAfricaString"), 8, 20, 33, listener);

		addButton(bundle.getString("madagascarString"), 10, 20, 20, listener);
	}

	private void addButtonsForSouthAmerica(JLabel picLabel2, CountryButtonListener listener) {
		addButton(bundle.getString("venezuelaString"), 5, 12, 37, listener);

		addButton(bundle.getString("peruString"), 5, 16, 28, listener);

		addButton(bundle.getString("brazilString"), 6, 15, 4, listener);

		addButton(bundle.getString("argentinaString"), 5, 20, 3, listener);

	}

	public void addButtonsForNorthAmerica(JLabel picLabel, CountryButtonListener listener) {
		addButton(bundle.getString("westernUSString"), 4, 6, 40, listener);

		addButton(bundle.getString("centralAmericaString"), 4, 10, 5, listener);

		addButton(bundle.getString("alaskaString"), 2, 1, 1, listener);

		addButton(bundle.getString("NWTerritoryString"), 4, 1, 26, listener);

		addButton(bundle.getString("greenlandString"), 7, 0, 13, listener);

		addButton(bundle.getString("quebecString"), 6, 4, 29, listener);

		addButton(bundle.getString("ontarioString"), 5, 4, 27, listener);

		addButton(bundle.getString("albertaString"), 4, 3, 2, listener);

		addButton(bundle.getString("easternUSString"), 5, 7, 10, listener);
	}

	public void addButton(String countryName, int gridx, int gridy, int countryID, CountryButtonListener listener) {
		GridBagConstraints c = new GridBagConstraints();

		JButton newButton = new JButton(countryName + ": 00");
		c.gridx = gridx;
		c.gridy = gridy;
		newButton.addActionListener(listener);
		this.buttonList.put(countryID, newButton);
		picLabel.add(newButton, c);
	}

	public int getCountry() {
		this.hasSelectedACountry = false;

		while (!this.hasSelectedACountry) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return this.selectedCountry;
	}

	public void setSelectedCountry(String name) {
		this.selectedCountry = getCountryID(name);
		this.hasSelectedACountry = true;
	}

	public void changeCountryControl(int armies, Color newControlingPlayerColor, int countryId) {
		this.buttonList.get(countryId).setBackground(newControlingPlayerColor);

		String[] splitText = this.buttonList.get(countryId).getText().split(":");
		this.buttonList.get(countryId).setText(splitText[0] + ": " + armies);
	}

	public void updateArmies(int armies, int countryId) {
		String[] splitText = this.buttonList.get(countryId).getText().split(":");
		this.buttonList.get(countryId).setText(splitText[0] + ": " + armies);
	}

	public int getCountryID(String name) {
		return this.countryIds.get(name);
	}

	public JLabel getMapLabel() {
		return this.picLabel;
	}

	private void makeCountryMap() {
		this.countryIds.put(bundle.getString("afghanistanString"), 0);
		this.countryIds.put(bundle.getString("alaskaString"), 1);
		this.countryIds.put(bundle.getString("albertaString"), 2);
		this.countryIds.put(bundle.getString("argentinaString"), 3);
		this.countryIds.put(bundle.getString("brazilString"), 4);
		this.countryIds.put(bundle.getString("centralAmericaString"), 5);
		this.countryIds.put(bundle.getString("chinaString"), 6);
		this.countryIds.put(bundle.getString("congoString"), 7);
		this.countryIds.put(bundle.getString("eastAfricaString"), 8);
		this.countryIds.put(bundle.getString("eastAustraliaString"), 9);
		this.countryIds.put(bundle.getString("easternUSString"), 10);
		this.countryIds.put(bundle.getString("egyptString"), 11);
		this.countryIds.put(bundle.getString("greatBritainString"), 12);
		this.countryIds.put(bundle.getString("greenlandString"), 13);
		this.countryIds.put(bundle.getString("icelandString"), 14);
		this.countryIds.put(bundle.getString("indiaString"), 15);
		this.countryIds.put(bundle.getString("indonesiaString"), 16);
		this.countryIds.put(bundle.getString("irkutskString"), 17);
		this.countryIds.put(bundle.getString("japanString"), 18);
		this.countryIds.put(bundle.getString("kamchatkaString"), 19);
		this.countryIds.put(bundle.getString("madagascarString"), 20);
		this.countryIds.put(bundle.getString("middleEastString"), 21);
		this.countryIds.put(bundle.getString("mongoliaString"), 22);
		this.countryIds.put(bundle.getString("newGuineaString"), 23);
		this.countryIds.put(bundle.getString("northAfricaString"), 24);
		this.countryIds.put(bundle.getString("northEuropeString"), 25);
		this.countryIds.put(bundle.getString("NWTerritoryString"), 26);
		this.countryIds.put(bundle.getString("ontarioString"), 27);
		this.countryIds.put(bundle.getString("peruString"), 28);
		this.countryIds.put(bundle.getString("quebecString"), 29);
		this.countryIds.put(bundle.getString("scandinaviaString"), 30);
		this.countryIds.put(bundle.getString("siamString"), 31);
		this.countryIds.put(bundle.getString("siberiaString"), 32);
		this.countryIds.put(bundle.getString("southAfricaString"), 33);
		this.countryIds.put(bundle.getString("southEuropeString"), 34);
		this.countryIds.put(bundle.getString("ukraineString"), 35);
		this.countryIds.put(bundle.getString("uralString"), 36);
		this.countryIds.put(bundle.getString("venezuelaString"), 37);
		this.countryIds.put(bundle.getString("westAustraliaString"), 38);
		this.countryIds.put(bundle.getString("westEuropeString"), 39);
		this.countryIds.put(bundle.getString("westernUSString"), 40);
		this.countryIds.put(bundle.getString("yakutskString"), 41);
	}

}
