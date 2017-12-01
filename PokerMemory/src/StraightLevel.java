import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class StraightLevel extends FlushLevel {

	private long score;
	private boolean areCombinationsLeft;

	protected StraightLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
		areCombinationsLeft = true;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMode() {
		// TODO Auto-generated method stub
		return "StraightMode";
	}

	@Override
	public long getScore() {
		return score;
	}
	
	protected boolean isGameOver(Card a, Card b, Card c, Card d, Card e) {
		
		Card[] newArr = new Card[this.getGrid().size()];

		for (int i = 0; i < newArr.length ; i++) {
			if(!(this.getGrid().get(i).isFaceUp())){
				a = this.getGrid().get(i);
			}
			else if (!(this.getGrid().get(i).isFaceUp()) && (this.getGrid().get(i) != a)){
				b = this.getGrid().get(i);
			}
			else if(!(this.getGrid().get(i).isFaceUp()) && (this.getGrid().get(i) != a) && (this.getGrid().get(i) != b)){
				c = this.getGrid().get(i);
			}
			else if(!(this.getGrid().get(i).isFaceUp()) && (this.getGrid().get(i) != a) 
				&& (this.getGrid().get(i) != b) && (this.getGrid().get(i) != c)){
				d = this.getGrid().get(i);
			}
			else if(!(this.getGrid().get(i).isFaceUp()) && (this.getGrid().get(i) != a) 
					&& (this.getGrid().get(i) != b) && (this.getGrid().get(i) != c) 
					&& (this.getGrid().get(i) != d)){
				e = this.getGrid().get(i);
			}
		}

		if(ComboLevel.isStraight(a, b, c, d, e, this)){
			return false;
		}

		return true;
	}

	@Override
	protected boolean turnUp(Card card) {
		// TODO Auto-generated method stub
		// the card may be turned
		if(this.getTurnedCardsBuffer().size() < getCardsToTurnUp()) 
		{
			// add the card to the list
			this.getTurnedCardsBuffer().add(card);
			if(this.getTurnedCardsBuffer().size() == getCardsToTurnUp())
			{
				// We are uncovering the last card in this turn
				// Record the player's turn
				this.getTurnsTakenCounter().increment();
				// get the other card (which was already turned up)

				int[] bufferArr = new int[this.getTurnedCardsBuffer().size()];

				Card otherCard1 = (Card) this.getTurnedCardsBuffer().get(0);
				Card otherCard2 = (Card) this.getTurnedCardsBuffer().get(1);
				Card otherCard3 = (Card) this.getTurnedCardsBuffer().get(2);
				Card otherCard4 = (Card) this.getTurnedCardsBuffer().get(3);

				bufferArr[0] = ScoreManagement.returnRankValue(otherCard1);
				bufferArr[1] = ScoreManagement.returnRankValue(otherCard2);
				bufferArr[2] = ScoreManagement.returnRankValue(otherCard3);
				bufferArr[3] = ScoreManagement.returnRankValue(otherCard4);
				bufferArr[4] = ScoreManagement.returnRankValue(card);

				Arrays.sort(bufferArr);
				if((card.getSuit() != otherCard1.getSuit()) || (card.getSuit() != otherCard2.getSuit()) || 
						(card.getSuit() != otherCard3.getSuit()) || (card.getSuit() != otherCard4.getSuit()) ){
					//General Instruction
					if((bufferArr[1] == bufferArr[0] + 1) && (bufferArr[2] == bufferArr[0] + 2)
							&& (bufferArr[3] == bufferArr[0] + 3 ) && (bufferArr[4] == bufferArr[0] + 4)){

						this.successScoreUpdate(bufferArr[4]);
						this.getTurnedCardsBuffer().clear();
					}

					//Case #1: If otherCard1's rank is "t"

					if((bufferArr[0] == 10) && (bufferArr[1] == 11) && (bufferArr[2] == 12) && (bufferArr[3] == 13)
							&& bufferArr[4] == 20){

						this.successScoreUpdate(bufferArr[4]);
						this.getTurnedCardsBuffer().clear();
					}

					//Case #2: If otherCard1's rank is "j"

					if((bufferArr[0] == 2) && (bufferArr[1] == 11) && (bufferArr[2] == 12) && (bufferArr[3] == 13)
							&& bufferArr[4] == 20){

						this.successScoreUpdate(bufferArr[4]);
						this.getTurnedCardsBuffer().clear();
					}

					//Case #3: If otherCard1's rank is "q"

					if((bufferArr[0] == 2) && (bufferArr[1] == 3) && (bufferArr[2] == 12) && (bufferArr[3] == 13)
							&& bufferArr[4] == 20){

						this.successScoreUpdate(bufferArr[4]);
						this.getTurnedCardsBuffer().clear();
					}

					//Case #4: If otherCard1's rank is "k"

					if((bufferArr[0] == 2) && (bufferArr[1] == 3) && (bufferArr[2] == 4) && (bufferArr[3] == 13)
							&& bufferArr[4] == 20){

						this.successScoreUpdate(bufferArr[4]);
						this.getTurnedCardsBuffer().clear();
					}
					//Case #: If otherCard1's rank is "a"

					if((bufferArr[4] == 20) && (bufferArr[0] == 2) && (bufferArr[1] == 3) && (bufferArr[2] == 4)
							&& bufferArr[3] == 5){
						this.successScoreUpdate(bufferArr[4]);
						this.getTurnedCardsBuffer().clear();
					}

					else{
						// The cards do not match, so start the timer to turn them down
						this.getTurnDownTimer().start();
						this.getMainFrame().setScore(score-=5);
					}
				}
			}
			return true;
		}
		return false;
	}

	private void successScoreUpdate(int highRank)
	{
		score += 1000 + 100*highRank;
		this.getMainFrame().setScore(score);
		if(straightCombinationsLeft() == 0) {
			areCombinationsLeft = false;
		}
	}
	
	//GameOver when there are no more straigt combinations. Show end Message.
	@Override
	protected boolean  isGameOver(){
		if(!areCombinationsLeft){
			//Show Ending Messages
			String GameOver = "Congratulations you have reach the end of the game\r\n"+
			"There is no more straight combinations left\r\n\r\n Your Score: "+score+"\r\nMoves Made: "+this.getTurnsTakenCounter().getText();
			JOptionPane.showMessageDialog(this.getMainFrame(), GameOver, "Game Over", JOptionPane.PLAIN_MESSAGE);
			return true;
		}
		return false;
	}
		
	//Find the total number of valid combinations in the grid.
	public int straightCombinationsLeft() {
		boolean[] rankExistance = new boolean[13];
		int combinations = 0;
		//Get if there are cards facedown with a given rank
		for (int i =0; i< this.getGrid().size();i++) {
			if(!this.getGrid().get(i).isFaceUp()) {
				if(this.getGrid().get(i).getRank().equals("a")){
					rankExistance[0] = true;
					}
					else {
						rankExistance[ScoreManagement.returnRankValue(this.getGrid().get(i))-1] = true;
					}
			}
	    }
		//Look for posible straight combinations (includes invalid flush straights)
		for(int i=4;i<rankExistance.length;i++)
		{
			if((rankExistance[i-4])&&rankExistance[i-3]&&rankExistance[i-2]&&rankExistance[i-1]&&rankExistance[i]) {
				combinations++;
			}
		}
		//Special case where rank a is highest:
		if(rankExistance[0]&&rankExistance[12]&&rankExistance[11]&&rankExistance[10]&&rankExistance[9]){
			combinations++;
		}
		MemoryFrame.dprintln("combinations Result "+ combinations);
		return combinations;
	}
}
