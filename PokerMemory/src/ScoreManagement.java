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
	private int i = 0;
	private MemoryFrame instance;
	private GameManager gameMan;
	private EasyLevel  easyLevel;
	private EqualPairLevel equalPairLevel;
	private RankTrioLevel rankTrioLevel;
	private FlushLevel flushLevel;
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
	 * @return score
	 */

	public long returnEqualPair(GameLevel difficulty){


		if(difficulty.getTurnedCardsBuffer().size() == 2){


			Card card = difficulty.getTurnedCardsBuffer().get(0);
			if(difficulty.turnUp(card)){

				Card card1 = difficulty.getTurnedCardsBuffer().get(1);


			//Score Instructions for "EqualPairLevel"
			//		if(card.getNum() == (card1.getNum())){
			if( (card.getRank().equals(card1.getRank())) && (card.getSuit().equals(card1.getSuit()))){
				score+= 50;
				

			}}
			else {
				score =- 5;
				


			}


			//		else if(instance.getLevelDescriptionLabel().equals(rankTrioLevel.getMode())){
			//			//Score Instructions for "RankTrioLevel"
			//
			//			if(card.getRank().equals(card1.getRank()) &&  card.getRank().equals(card2.getRank())){
			//				score =+ 100 + this.returnRankValue(card) + this.returnRankValue(card1) + this.returnRankValue(card2);
			//				
			//
			//			}
			//			else {score =- 5;}
			//
			//		}

			//		else if(  .getMode().equals(flushLevel.getMode())){
			//			if
			//		}
		}

		return score;
	}

	public long getScore(NewMemoryFrame frame) {
		long result = 0;
		if (frame.getDifficulty().getMode() == "MediumMode"){
			result =  this.returnEqualPair(frame.getDifficulty());
		}
		return result;

	}


	public void reset()
	{
		this.score = 0;
	}
}









