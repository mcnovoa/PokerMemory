/**A subclass of MemoryFrame for the addition of new levels, music and others. 
 *
 * @author Alberto Canela (class extender)
 * @author Maria Novoa (contributor)
 * @version Nov 2017
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class NewMemoryFrame extends MemoryFrame {

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
					else if(e.getActionCommand().equals("Equal Pair")) newGame("equalpair");
					else if(e.getActionCommand().equals("Same Rank Trio")) newGame("ranktrio");
					else if(e.getActionCommand().equals("How To Play")) showInstructions();
					else if(e.getActionCommand().equals("About")) showAbout();
				} catch (IOException e2) {
					e2.printStackTrace(); throw new RuntimeException("IO ERROR");
				}
			}
		};

		//Remove original levels from Memory Frame
		memoryMenu.removeAll();

		//Rename Memory menu to New Game.
		memoryMenu.setText("New Game");

		//Add extended and original levels
		JMenuItem equalPairMenuItem = new JMenuItem("Equal Pair");
		equalPairMenuItem.addActionListener(menuHandler);
		memoryMenu.add(equalPairMenuItem);

		JMenuItem sameRankTrioMenuItem = new JMenuItem("Same Rank Trio");
		sameRankTrioMenuItem.addActionListener(menuHandler);		
		memoryMenu.add(sameRankTrioMenuItem);

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

		//Add about menuitem
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(menuHandler);
		helpMenu.add(mntmAbout);

		//Add new How To Play
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
			BorderLayout b  = (BorderLayout) this.getContentPane().getLayout();
			this.getContentPane().remove(b.getLayoutComponent(BorderLayout.CENTER));
			this.getContentPane().add(showCardDeck(), BorderLayout.CENTER);

			// show the window (in case this is the first game)
			this.setVisible(true);
		}

		else if(difficultyMode.equalsIgnoreCase("ranktrio")) {
			this.setGameLevel(new RankTrioLevelScored(this.getTurnCounterLabel(), this));
			this.getLevelDescriptionLabel().setText("Rank Trio Level");
			this.getTurnCounterLabel().reset();

			// clear out the content pane (removes turn counter label and card field)
			BorderLayout b  = (BorderLayout) this.getContentPane().getLayout();
			this.getContentPane().remove(b.getLayoutComponent(BorderLayout.CENTER));
			this.getContentPane().add(showCardDeck(), BorderLayout.CENTER);

			// show the window (in case this is the first game)
			this.setVisible(true);
		}

		else if(difficultyMode.equalsIgnoreCase("flush")) {
			this.setGameLevel(new FlushLevel(this.getTurnCounterLabel(), this));
			this.getLevelDescriptionLabel().setText("Flush Level");
			this.getTurnCounterLabel().reset();

			// clear out the content pane (removes turn counter label and card field)
			BorderLayout b  = (BorderLayout) this.getContentPane().getLayout();
			this.getContentPane().remove(b.getLayoutComponent(BorderLayout.CENTER));
			this.getContentPane().add(showCardDeck(), BorderLayout.CENTER);

			// show the window (in case this is the first game)
			this.setVisible(true);
		}

		else if(difficultyMode.equalsIgnoreCase("straight")) {
			this.setGameLevel(new StraightLevel(this.getTurnCounterLabel(), this));
			this.getLevelDescriptionLabel().setText("Straight Level");
			this.getTurnCounterLabel().reset();

			// clear out the content pane (removes turn counter label and card field)
			BorderLayout b  = (BorderLayout) this.getContentPane().getLayout();
			this.getContentPane().remove(b.getLayoutComponent(BorderLayout.CENTER));
			this.getContentPane().add(showCardDeck(), BorderLayout.CENTER);

			// show the window (in case this is the first game)
			this.setVisible(true);
		}

		else if(difficultyMode.equalsIgnoreCase("combo")) {
			this.setGameLevel(new ComboLevel(this.getTurnCounterLabel(), this));
			this.getLevelDescriptionLabel().setText("Combo Level");
			this.getTurnCounterLabel().reset();

			// clear out the content pane (removes turn counter label and card field)
			BorderLayout b  = (BorderLayout) this.getContentPane().getLayout();
			this.getContentPane().remove(b.getLayoutComponent(BorderLayout.CENTER));
			this.getContentPane().add(showCardDeck(), BorderLayout.CENTER);

			// show the window (in case this is the first game)
			this.setVisible(true);
		}
		else {
			super.newGame(difficultyMode);
		}
	}


	@Override
	public boolean gameOver() throws FileNotFoundException, InterruptedException {
		// TODO Auto-generated method stub
		return super.gameOver();
	}

	private void showInstructions()
	{
		dprintln("MemoryGame.showInstructions()");
		final String HOWTOPLAYTEXT = "ALL LEVELS: Poker Memory\r\n\r\n"+
				"Each time you flip a set of cards up, the turn counter will increase. Try to win the game in the fewest number of turns!"+
				"Each unsuccessful turn will result in a penalty of 5 points and is possible to get a negative score in this game.\r\n\r\n"+

				"EQUAL PAIR Level\r\n\r\n"+ 
				"The game consists of 8 pairs of cards.  At the start of the game every card is face down."+
				"The object is to find all the pairs and turn them face up. Click on two cards to turn them face up."+
				" If the cards are the same, then you have discovered a pair. The pair will remain turned up."+
				"If the cards are different, they will flip back over automatically after a short delay."+
				"Continue flipping cards until you have discovered all the pairs.  The game is won when all cards are face up. \r\n\r\n"+ 

				"SAME RANK TRIO Level\r\n\r\n"+

				"The game consists of a grid of distinct cards.  At the start of the game, every card is face down."+
				"The object is to find all the trios of cards with the same rank and turn them face up. Click on three cards to turn them face up."+
				"If the cards have the same rank, then you have discovered a trio.  The trio will remain turned up.  If the cards are different,"+
				" they will flip back over automatically after a short delay.  Continue flipping cards until you have discovered all the pairs."+
				" The game is won when all cards are face up.\r\n\r\n"+

				"FLUSH Level\r\n\r\n"+

				"The game consists of a grid of distinct cards.  At the start of the game, every card is face down."+
				" The objective is to find all the combinations of five cards with the same suit and turn them face up."+
				" You will get 100 points plus the sum of the ranks for each combination found and the cards will remain turned up."+
				" If the cards chosen are not a combination, they will flip back over automatically."+
				" The game ends when all cards are flipped up or no combinations are possible.\r\n\r\n"+

				"STRAIGHT Level\r\n\r\n"+

				"The game consists of a grid of distinct cards.  At the start of the game, every card is face down."+
				" Players uncover five cards on each turn. A wining hand consists of all five cards in sequence with at least two distinct suits."+
				" The score for each hand is computed as 1000 points plus 100 times the rank of the highest card in the sequence."+
				"The game ends when all cards are flipped up or no combinations are possible. \r\n\r\n"+ 

				 "COMBO Level\r\n\r\n"+

				 "The objective of this level is to get the highest score possible by selecting poker hands of five cards."+
				 " You will get the option of choosing either a flush, straight or a four of a kind."+
				 " Flush occurs when five cards have the same suit, the score will be the sum of the ranks in the hand plus 100."+
				 " Straight consists on all five cards in sequence with at least two distinct suits, the score being 500 points"+
				 " plus the rank of the highest card squared. Four of a kind consists on choosing four cards with equal rank in your hand of five,"+
				 " as the hardest hand in this level, the score is 100 times the rank of the quartet of cards plus 4 times the rank of the remaining card."+
				 "Also, you may choose to PASS, a move that will cover the cards again, giving you the opportunity to uncover a higher scoring hand in future turns.\r\n";

		//Provides a scroll panel to show all the instructions neatly.
		JTextArea instructionsTextArea = new JTextArea(HOWTOPLAYTEXT);
		JScrollPane scrollPane = new JScrollPane(instructionsTextArea);  
		instructionsTextArea.setLineWrap(true);  
		instructionsTextArea.setWrapStyleWord(true); 
		scrollPane.setPreferredSize( new Dimension( 500, 500 ) );

		JOptionPane.showMessageDialog(null, scrollPane, "How To Play",JOptionPane.PLAIN_MESSAGE);
	}
	private void showAbout()
	{
		dprintln("MemoryGame.showAbout()");
		final String ABOUTTEXT = "Game Customized at UPRM. Originally written by Mike Leonhard.\n"
				+"Music used under Creative Commons License, effects attributed to bertrof@freesound.org";

		JOptionPane.showMessageDialog(this, ABOUTTEXT
				, "About Memory Game", JOptionPane.PLAIN_MESSAGE);
	}

}