import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class NewMemoryFrame extends MemoryFrame {
	ScoreManagement score = new ScoreManagement();
	
    public NewMemoryFrame() {
        super();
        super.setScore(score.getScore());
        JMenuBar menuBar = this.getJMenuBar();
        JMenu memoryMenu = null;
        for (int i = 0; i < menuBar.getMenuCount(); i++) {
            if (menuBar.getMenu(i).getText().equals("Memory")) {
                memoryMenu = menuBar.getMenu(i);
                break;
            }
        }
        
        ActionListener menuHandler = new ActionListener() {
            public void actionPerformed1(ActionEvent e) {
                try {
                    if(e.getActionCommand().equals("Same Rank Trio Score")) newGame("ranktrioscore");
                } catch (IOException e2) {
                    e2.printStackTrace(); throw new RuntimeException("IO ERROR");
                }
            }

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        };

        JMenuItem rankTrioScoreMenuItem = new JMenuItem("Same Rank Trio Score");
        rankTrioScoreMenuItem.addActionListener(menuHandler);
        memoryMenu.add(rankTrioScoreMenuItem);
    }

    /**
     * Prepares a new game (first game or non-first game)
     * @throws IOException 
     */
    public void newGame(String difficultyMode) throws IOException
    {
        // Reset the turn counter label
        this.getTurnCounterLabel().reset();
        
        //Set Score
        this.setScore(score.getScore());
        
        // make a new card field with cards, and add it to the window

//        if(difficultyMode.equalsIgnoreCase("ranktrioscore")) {
//            this.setGameLevel(new RankTrioWithScore(this.getTurnCounterLabel(), this));
//            this.getLevelDescriptionLabel().setText("Same Rank Trio Level Score");
//            this.getTurnCounterLabel().reset();
//
//            // clear out the content pane (removes turn counter label and card field)
//            BorderLayout bl  = (BorderLayout) this.getContentPane().getLayout();
//            this.getContentPane().remove(bl.getLayoutComponent(BorderLayout.CENTER));
//            this.getContentPane().add(showCardDeck(), BorderLayout.CENTER);
//
//            // show the window (in case this is the first game)
//            this.setVisible(true);
//        }
//        else {
//            super.newGame(difficultyMode);
//        }
    }

}