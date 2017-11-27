/**
 * Inherits from JLabel and implements the management of the score based on the Level
 *
 * @author María C. Novoa García (original author)
 * @author Alberto Canela Cubero ()
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
	private int rankValue;

	final private int JValue = 11;
	final private int QValue = 12;
	final private int KValue = 13;
	final private int AValue = 20;
	final private int tenValue = 10;


	//	private ArrayList <Card> grid= new ArrayList<Card>();


	public ScoreManagement()
	{
		super();
		reset();
	}

	public int returnRankValue(Card card){

		if(card.getRank().equals("t")){
			rankValue = tenValue;
		}
		else if(card.getRank().equals("j")){
			rankValue = JValue;
		}
		else if(card.getRank().equals("q")){
			rankValue = QValue;
		}
		else if(card.getRank().equals("k")){
			rankValue = KValue;
		}
		else if(card.getRank().equals("a")){
			rankValue = AValue;
		}


		return rankValue;
	}
	/**
	 * Returns the Score according to it's level mode
	 * 
	 * @param card type Card representing the first card on the buffer
	 * @param card1 type Card representing the first card on the buffer
	 * @param card2 type Card representing the first card on the buffer
	 * 
	 * @return score
	 */

	public long returnTheScore(Card card, Card card1, Card card2){
		card = gameLevel.getTurnedCardsBuffer().get(0);
		card1 = gameLevel.getTurnedCardsBuffer().get(1);
		card2 = gameLevel.getTurnedCardsBuffer().get(2);

		if(gameLevel.getMode().equals(equalPairLevel.getMode())){
			//Score Instructions for "EqualPairLevel"
			if( (card.getRank().equals(card1.getRank())) && (card.getSuit().equals(card1.getSuit()))){
				score+= 50;
				update();
			}
			else {
				score =- 5;
				update();}

		}

		else if(gameLevel.getMode().equals(rankTrioLevel.getMode())){
			//Score Instructions for "RankTrioLevel"

			if(card.getRank().equals(card1.getRank()) &&  card.getRank().equals(card2.getRank())
					&& card.getSuit().equals(card1.getSuit()) &&  card.getSuit().equals(card2.getSuit())){
				score =+ 100 + this.returnRankValue(card) + this.returnRankValue(card1) + this.returnRankValue(card2);
				update();

			}
			else {score =- 5;}

		}
		return score;
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
	}

	public void reset()
	{
		this.score = 0;
		update();
	}
}









