package WorkingSpace;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUI {

	public JFrame showCardsFrame;

	private Map map;
	private JFrame frame;
	private JLabel playerConsole;
	private JPanel colorPanel;
	private HashMap<Integer, Color> playerColors = new HashMap<>();
	private ResourceBundle bundle = ResourceBundle.getBundle("res.strings");
	private ResourceBundle imageBundle = ResourceBundle.getBundle("res.images");
	private JPanel toolBarPanel;
	private Player currentPlayer;
	private ShowCardsButtonListener showCardListener = new ShowCardsButtonListener(this);

	public GUI() {
		try {
			this.map = new Map();
		} catch (IOException e) {

			e.printStackTrace();
		}

		this.frame = new JFrame();
		this.frame.add(map.getMapLabel());
		this.frame.setLayout(new GridBagLayout());

		initializePlayerColors();
		addPlayerConsole();

	}

	public void showGUI() {
		this.frame.setVisible(true);
		this.frame.setSize(1900, 1050);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void initializePlayerColors() {
		this.playerColors.put(1, Color.cyan);
		this.playerColors.put(2, Color.green);
		this.playerColors.put(3, Color.red);
		this.playerColors.put(4, Color.yellow);
		this.playerColors.put(5, Color.orange);
		this.playerColors.put(6, Color.magenta);
	}

	public void updateCurrentPlayer(Player player) {
		if (player != null)
			this.currentPlayer = player;
	}

	public int askForNumberofPlayers() {
		int num;
		try {
			num = Integer.parseInt(JOptionPane.showInputDialog(bundle.getString("guiAskForNumberOfPlayers")));
		} catch (NumberFormatException e) {
			return askForNumberofPlayers();
		}
		return num;
	}

	public void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	public int selectCountry() {
		return map.getCountry();
	}

	public void changeCountryControl(int armiesMovingIn, int playerID, int countryID) {
		map.changeCountryControl(armiesMovingIn, this.playerColors.getOrDefault(playerID, Color.BLACK), countryID);
	}

	public void updateArmies(int armies, int countryID) {
		map.updateArmies(armies, countryID);
	}

	public void addPlayerConsole() {
		this.playerConsole = new JLabel();
		this.playerConsole.setText(bundle.getString("guiWelcomeToGameOfRisk"));
		this.frame.add(playerConsole);
		this.toolBarPanel = new JPanel();
		GridBagConstraints con = new GridBagConstraints();
		con.ipadx = 50;
		con.ipady = 50;
		con.weightx = 3;
		this.colorPanel = new JPanel();

		this.frame.add(toolBarPanel, con);

		GridLayout layout = new GridLayout(0, 1, 0, 20);

		this.toolBarPanel.setLayout(layout);

		this.toolBarPanel.add(this.playerConsole, con);
		this.toolBarPanel.add(this.colorPanel);

		JButton cardButton = new JButton(bundle.getString("showCardsMessage"));
		cardButton.addActionListener(this.showCardListener);
		this.toolBarPanel.add(cardButton);
	}

	public void updatePlayerConsole(int playerID, String message) {
		this.playerConsole.setText(message);
		this.colorPanel.setBackground(playerColors.getOrDefault(playerID, Color.BLACK));
	}

	public boolean askToRedeemCards() {
		boolean answer = false;

		int reply = JOptionPane.showConfirmDialog(null, bundle.getString("askToRedeemCards"),
				bundle.getString("drawPhaseWindowTitle"), JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			answer = true;
		}

		return answer;
	}

	public void demandToRedeemCards() {
		JOptionPane.showMessageDialog(null, bundle.getString("handTooLarge"));

	}

	public boolean askToAttack() {
		boolean answer = false;

		int reply = JOptionPane.showConfirmDialog(null, bundle.getString("guiAttackPhaseWouldYouLikeToAttack"),
				bundle.getString("guiAttackPhaseWindowTitle"), JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			answer = true;
		}

		return answer;
	}

	public boolean askToContinueAttack() {
		boolean answer = false;

		int reply = JOptionPane.showConfirmDialog(null, bundle.getString("guiAttackPhaseWouldYouLikeToContinue"),
				bundle.getString("guiAttackPhaseWindowTitle"), JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			answer = true;
		}

		return answer;
	}

	public int askforNumDefenderDice() {
		int numberToReturn;
		String num = JOptionPane.showInputDialog(bundle.getString("guiAskForNumDefenderDice"),
				bundle.getString("guiAskForNumDiceWindowTitle"));

		try {
			numberToReturn = Integer.parseInt(num);
		} catch (NumberFormatException e) {
			return askforNumDefenderDice();
		}

		if (numberToReturn > 2) {
			return askforNumDefenderDice();
		}

		return numberToReturn;
	}

	public int askforNumAttackerDice() {
		int numberToReturn;
		String num = JOptionPane.showInputDialog(bundle.getString("guiAskForNumAttackerDice"),
				bundle.getString("guiAskForNumDiceWindowTitle"));

		try {
			numberToReturn = Integer.parseInt(num);
		} catch (NumberFormatException e) {
			return askforNumAttackerDice();
		}

		if (numberToReturn > 3) {
			return askforNumAttackerDice();
		}

		return numberToReturn;
	}

	public int selectCountryToAttackFrom() {
		JOptionPane.showMessageDialog(null, bundle.getString("guiSelectCountryToAttackFromMessage"));

		return selectCountry();
	}

	public int selectCountryToAttack() {
		JOptionPane.showMessageDialog(null, bundle.getString("guiSelectCountryToAttackMessage"));

		return selectCountry();
	}

	public int askForNumArmiesToAttack() {
		int numberToReturn;
		String num = JOptionPane.showInputDialog(bundle.getString("guiAskForNumArmiesToAttack"));

		try {
			numberToReturn = Integer.parseInt(num);
		} catch (NumberFormatException e) {
			return askforNumAttackerDice();
		}

		return numberToReturn;
	}

	public boolean askToFortify() {
		boolean answer = false;

		int reply = JOptionPane.showConfirmDialog(null, bundle.getString("guiAskToFortifyMessage"),
				bundle.getString("guiFortifyPhaseWindowTitle"), JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			answer = true;
		}

		return answer;

	}

	public int selectSourceCountryForFortify() {
		JOptionPane.showMessageDialog(null, bundle.getString("guiSelectCountryForFortify"));

		return selectCountry();
	}

	public int selectDestinationCountryForFortify() {
		JOptionPane.showMessageDialog(null, bundle.getString("guiSelectDestinationCountryForFortify"));

		return selectCountry();
	}

	public int askForNumArmiesToTransfer() {
		int numberOfArmies;
		String num = JOptionPane.showInputDialog(bundle.getString("guiAskForNumArmiesToTransfer"));

		try {
			numberOfArmies = Integer.parseInt(num);
		} catch (NumberFormatException e) {
			return askForNumArmiesToTransfer();
		}

		return numberOfArmies;
	}

	public ArrayList<String> showCardsToChoose() {

		ArrayList<String> selectedCards = new ArrayList<String>();

		this.showCardsFrame = new JFrame();
		this.showCardsFrame.setSize(1000, 350);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		showCardsFrame.setLocation(dim.width / 2 - this.showCardsFrame.getSize().width / 2,
				dim.height / 2 - this.showCardsFrame.getSize().height / 2);
		this.showCardsFrame.setVisible(true);

		JPanel cardDisplayPanel = new JPanel();

		GridLayout layout = new GridLayout(1, 5, 20, 20);

		cardDisplayPanel.setLayout(layout);

		HashMap<String, Integer> cardMap = this.currentPlayer.cardHandMap;

		int numCards = 0;
		for (String key : cardMap.keySet()) {
			int value = cardMap.get(key);
			System.out.println(key);
			for (int i = 0; i < value; i++) {
				addCardToNewWindow(key, this.currentPlayer, cardDisplayPanel, selectedCards);
				numCards++;
			}

		}

		for (int i = numCards; i < 5; i++) {
			cardDisplayPanel.add(new JLabel());
		}

		this.showCardsFrame.add(cardDisplayPanel);
		this.showCardsFrame.setVisible(true);

		return selectedCards;

	}

	public void addCardToNewWindow(String cardType, Player player, JPanel panel, ArrayList<String> selectedCards) {

		JButton button = new JButton();

		PlayerChooseCardButtonListener listener = new PlayerChooseCardButtonListener(cardType, selectedCards);

		try {
			Image image = ImageIO.read(new File(imageBundle.getString(cardType)));
			button.setIcon(new ImageIcon(image));
			button.addActionListener(listener);
		} catch (Exception ex) {
			System.out.println(ex);
		}

		panel.add(button);
	}

	public void disposeCardFrame() {
		this.showCardsFrame.dispose();
	}

}
