import java.util.Arrays;

import javax.swing.JFrame;

public class StraightLevel extends FlushLevel {

	private long score;

	protected StraightLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
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
			return true;
		}
		return false;
	}

	private void successScoreUpdate(int highRank)
	{
		score += 1000 + 100*highRank;
		this.getMainFrame().setScore(score);
	}
	
}
