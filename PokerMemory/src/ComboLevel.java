import java.util.Arrays;

import javax.swing.JFrame;

public class ComboLevel extends StraightLevel {

	private FlushLevel flush;
	private long score;

	protected ComboLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMode() {
		// TODO Auto-generated method stub
		return "ComboMode";
	}

	@Override
	public long getScore() {
		// TODO Auto-generated method stub
		return score;
	}

	@Override
	protected boolean turnUp(Card card) {
		// TODO Auto-generated method stub
		if(this.getTurnedCardsBuffer().size() < getCardsToTurnUp()) 
		{
			// add the card to the list
			this.getTurnedCardsBuffer().add(card);
			if(this.getTurnedCardsBuffer().size() == getCardsToTurnUp()){
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

				if(bufferArr[0] == bufferArr[1] && bufferArr[1] == bufferArr[2]  && bufferArr[2] == bufferArr[3]){

					this.getTurnedCardsBuffer().clear();
				}
				else{
					// The cards do not match, so start the timer to turn them down
					this.getTurnDownTimer().start();
					this.getMainFrame().setScore(score -= 5);
				}
			}
			return true;
		}
		return false;
	}
//	private void successScoreUpdate(Card card){
//
//		if(super.turnUp(card) == true || flush.turnUp(card) == true || this.turnUp(card) == true){
//
//		}
//	}

}
