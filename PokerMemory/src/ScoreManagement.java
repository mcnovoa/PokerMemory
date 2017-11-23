/**
 * Inherits from JLabel and implements the management of the score based on the Level
 *
 * @author María C. Novoa García (original author)
 * @author Alberto Canela Cubero (contribuitor)
 * @version Nov 2017
 */

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ScoreManagement extends JLabel {

	private long score = 0;
	private EasyLevel  easyLevel;
	private EqualPairLevel equalPairLevel;
	private RankTrioLevel rankTrioLevel;
	private GameLevel gameLevel;
	private ArrayList <Card> grid= new ArrayList<Card>();
	private MemoryFrame mFrame = new MemoryFrame();
	
	
	public ScoreManagement()
	{
		super();
		reset();
	}
	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	private void update()
	{
		this.setText("" + this.getScore());
//		this.setHorizontalAlignment(SwingConstants.RIGHT);
	}

	public void reset()
	{
		this.score = 0;
		update();
	}

	public long returnTheScore(){

		if(gameLevel.getMode().equals(equalPairLevel.getMode())){
			//Score Instructions for "EqualPairLevel"


		}

		else if(gameLevel.getMode().equals(rankTrioLevel.getMode())){
			//Score Instructions for "RankTrioLevel"
			Card card = gameLevel.getTurnedCardsBuffer().get(0);
			Card card1 = gameLevel.getTurnedCardsBuffer().get(1);
			Card card2 = gameLevel.getTurnedCardsBuffer().get(2);
			if(card.getRank().equals(card1.getRank()) &&  card.getRank().equals(card2.getRank())){
				score =+ 100 + Integer.parseInt(card.getRank()) + Integer.parseInt(card1.getRank())
				+ Integer.parseInt(card2.getRank());
				update();

			}
			//		if((rankTrioLevel.turnUp()
			//				card.getRank().equals(otherCard1.getRank())) && (card.getRank().equals(otherCard2.getRank()))) {
			//	}

		}
		return score;
	}
}









