import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class NewMemoryFrame extends MemoryFrame {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5074343438278317645L;

	public NewMemoryFrame() {
		super();
		JMenuBar menuBar = this.getJMenuBar();
		JMenu memoryMenu = null;
		JMenu helpMenu = null;
		for (int i = 0; i < menuBar.getMenuCount(); i++) {
			if (menuBar.getMenu(i).getText().equals("Memory")) {
				memoryMenu = menuBar.getMenu(i);
			}
			else if(menuBar.getMenu(i).getText().equals("Help")){
				helpMenu = menuBar.getMenu(i);
			}
		}

		ActionListener menuHandler = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(e.getActionCommand().equals("Flush")) newGame("flush");
					else if(e.getActionCommand().equals("Straight")) newGame("straight");
					else if(e.getActionCommand().equals("Combo")) newGame("combo");
					else if(e.getActionCommand().equals("Equal Pair Level")) newGame("equalpair");
					else if(e.getActionCommand().equals("How To Play")) showInstructions();
				} catch (IOException e2) {
					e2.printStackTrace(); throw new RuntimeException("IO ERROR");
				}
			}
		};
		
		//Remove original levels from Memory Frame
		memoryMenu.removeAll();
		
		//Add extended and original levels
		JMenuItem equalPairMenuItem = new JMenuItem("Equal Pair Level");
		equalPairMenuItem.addActionListener(menuHandler);
		memoryMenu.add(equalPairMenuItem);

		JMenuItem flushMenuItem = new JMenuItem("Flush");
		flushMenuItem.addActionListener(menuHandler);
		memoryMenu.add(flushMenuItem);

		JMenuItem straightMenuItem = new JMenuItem("Straight");
		straightMenuItem.addActionListener(menuHandler);
		memoryMenu.add(straightMenuItem);
		
		JMenuItem comboMenuItem = new JMenuItem("Combo");
		comboMenuItem.addActionListener(menuHandler);
		memoryMenu.add(comboMenuItem);
		
		//Remove original HelpMenu Items to replace with updated ones.
		helpMenu.removeAll();
		
		//Add new Help Page
		JMenuItem newHelpPage = new JMenuItem("How To Play");
		newHelpPage.addActionListener(menuHandler);
		helpMenu.add(newHelpPage);
		
		//Change Background Color, Sets Border to Orange.
		this.getContentPane().setBackground(Color.ORANGE);
	}

	/**
	 * Prepares a new game (first game or non-first game)
	 * @throws IOException
	 */
	public void newGame(String difficultyMode) throws IOException
	{
		// Reset the turn counter label
		this.getTurnCounterLabel().reset();

		// make a new card field with cards, and add it to the window
		if(difficultyMode.equalsIgnoreCase("equalpair")) {
			this.setGameLevel(new EqualPairLevelScored(this.getTurnCounterLabel(), this));
			this.getLevelDescriptionLabel().setText("Equal Pair Level");
			this.getTurnCounterLabel().reset();

			// clear out the content pane (removes turn counter label and card field)
			BorderLayout bl  = (BorderLayout) this.getContentPane().getLayout();
			this.getContentPane().remove(bl.getLayoutComponent(BorderLayout.CENTER));
			this.getContentPane().add(showCardDeck(), BorderLayout.CENTER);

			// show the window (in case this is the first game)
			this.setVisible(true);
		}

		else if(difficultyMode.equalsIgnoreCase("flush")) {
			this.setGameLevel(new FlushLevel(this.getTurnCounterLabel(), this));
			this.getLevelDescriptionLabel().setText("Flush Level");
			this.getTurnCounterLabel().reset();

			// clear out the content pane (removes turn counter label and card field)
			BorderLayout b2  = (BorderLayout) this.getContentPane().getLayout();
			this.getContentPane().remove(b2.getLayoutComponent(BorderLayout.CENTER));
			this.getContentPane().add(showCardDeck(), BorderLayout.CENTER);

			// show the window (in case this is the first game)
			this.setVisible(true);
		}

		else if(difficultyMode.equalsIgnoreCase("straight")) {
			this.setGameLevel(new StraightLevel(this.getTurnCounterLabel(), this));
			this.getLevelDescriptionLabel().setText("Straight Level");
			this.getTurnCounterLabel().reset();

			// clear out the content pane (removes turn counter label and card field)
			BorderLayout b3  = (BorderLayout) this.getContentPane().getLayout();
			this.getContentPane().remove(b3.getLayoutComponent(BorderLayout.CENTER));
			this.getContentPane().add(showCardDeck(), BorderLayout.CENTER);

			// show the window (in case this is the first game)
			this.setVisible(true);
		}
		
		else if(difficultyMode.equalsIgnoreCase("combo")) {
			this.setGameLevel(new ComboLevel(this.getTurnCounterLabel(), this));
			this.getLevelDescriptionLabel().setText("Combo Level");
			this.getTurnCounterLabel().reset();

			// clear out the content pane (removes turn counter label and card field)
			BorderLayout b4  = (BorderLayout) this.getContentPane().getLayout();
			this.getContentPane().remove(b4.getLayoutComponent(BorderLayout.CENTER));
			this.getContentPane().add(showCardDeck(), BorderLayout.CENTER);

			// show the window (in case this is the first game)
			this.setVisible(true);
		}
		else {
			super.newGame(difficultyMode);
		}
	}
	private void showInstructions()
	{
		dprintln("MemoryGame.showInstructions()");
		final String HOWTOPLAYTEXT = 
				"How To Play Test\r\n" +
						"\r\n" +
						"EQUAL PAIR Level\r\n"+
						"The game consists of 8 pairs of cards.  At the start of the game,\r\n"+
						"every card is face down.  The object is to find all the pairs and\r\n"+
						"turn them face up.\r\n"+
						"\r\n"+
						"Click on two cards to turn them face up. If the cards are the \r\n"+
						"same, then you have discovered a pair.  The pair will remain\r\n"+
						"turned up.  If the cards are different, they will flip back\r\n"+
						"over automatically after a short delay.  Continue flipping\r\n"+
						"cards until you have discovered all of the pairs.  The game\r\n"+
						"is won when all cards are face up.\r\n"+
						"\r\n"+
						"SAME RANK TRIO Level\r\n"+
						"The game consists of a grid of distinct cards.  At the start of the game,\r\n"+
						"every card is face down.  The object is to find all the trios \r\n"+
						"of cards with the same rank and turn them face up.\r\n"+
						"\r\n"+
						"Click on three cards to turn them face up. If the cards have the \r\n"+
						"same rank, then you have discovered a trio.  The trio will remain\r\n"+
						"turned up.  If the cards are different, they will flip back\r\n"+
						"over automatically after a short delay.  Continue flipping\r\n"+
						"cards until you have discovered all of the pairs.  The game\r\n"+
						"is won when all cards are face up.\r\n"+
						"\r\n"+
						"Each time you flip two cards up, the turn counter will\r\n"+
						"increase.  Try to win the game in the fewest number of turns!";

		//Provides a scroll panel to show all the instructions.
		JTextArea instructionsTextArea = new JTextArea(HOWTOPLAYTEXT);
		JScrollPane scrollPane = new JScrollPane(instructionsTextArea);  
		instructionsTextArea.setLineWrap(true);  
		instructionsTextArea.setWrapStyleWord(true); 
		scrollPane.setPreferredSize( new Dimension( 500, 500 ) );
		JOptionPane.showMessageDialog(null, scrollPane, "How To Play",JOptionPane.PLAIN_MESSAGE);
	}
}