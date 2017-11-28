/**
 * Inherits from JLabel and implements the management of the score based on the Level
 *
 * @author Mar�a C. Novoa Garc�a (original author)
 * @author Alberto Canela Cubero ()
 * @version Nov 2017
 */

public class ScoreManagement{

	private long score;
	private GameLevel level;
	private int turnNumber;
	private NewMemoryFrame myframe;


	final private int JValue = 11;
	final private int QValue = 12;
	final private int KValue = 13;
	final private int AValue = 20;
	final private int tenValue = 10;

	

	public ScoreManagement(NewMemoryFrame game)
	{
		this.myframe = game;
		this.level = myframe.getDifficulty();
		this.score = 0;
		this.turnNumber = 0;

	
		}
public void setLevel(GameLevel level) {
		this.level = level;
	}

	
	public int returnRankValue(Card card){
		int rankValue;

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
		else {
			rankValue = Integer.parseInt(card.getRank());
		}
		return rankValue;
	}
	/**
	 * Returns the Score according to it's level mode
	 * 
	 * @return score
	 */

	private void calculateEqualPair()
	{
		//Calculates score only once per turn
		if(myframe.getTurnCounterLabel().getNumOfTurns() > turnNumber)
		{
			//If there are 2 elements in buffer, they are waiting for facedown, thus the combination is incorrect.
			if(level.getTurnedCardsBuffer().size() == 2) 
			{
				this.score -= 5;
			}
			
			//Correct combinations (Equal Pairs) are eliminated from buffer in EqualPairLevel FaceUp method.
			else if (level.getTurnedCardsBuffer().isEmpty()) 
			{
				this.score += 50;
			}
			turnNumber++;
		}
	}

	public long getScore() {
		if (level.getMode() == "MediumMode")
		{
			calculateEqualPair();
		}
		return this.score;

	}


	public void reset()
	{
		this.score = 0;
	}
}