/**
 * Stores currently turned cards, allows only two cards to be uncovered on each turn
 * Also handles turning cards back down after a delay if cards are different.
 * Handles its scoring system per turn.
 *
 * @author Alberto Canela (Class Extender)
 * @author Maria Novoa
 * @version Nov 2017
 */
import javax.swing.JFrame;

public class EqualPairLevelScored extends EqualPairLevel {

	private long score;
	
	protected EqualPairLevelScored(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
		this.score = 0;
	}
	
	@Override
	protected boolean turnUp(Card card) {
		// the card may be turned
		if(this.getTurnedCardsBuffer().size() < getCardsToTurnUp()) 
		{
			this.getTurnedCardsBuffer().add(card);
			if(this.getTurnedCardsBuffer().size() == getCardsToTurnUp())
			{
				// there are two cards faced up
				// record the player's turn
				this.getTurnsTakenCounter().increment();
				// get the other card (which was already turned up)
				Card otherCard = (Card) this.getTurnedCardsBuffer().get(0);
				// the cards match, so remove them from the list (they will remain face up)
				if( otherCard.getNum() == card.getNum()) 
				{
					this.getTurnedCardsBuffer().clear();
					//Add points for match
				    this.getMainFrame().setScore(score+=50);
				}
				// the cards do not match, so start the timer to turn them down
				else { 
					this.getTurnDownTimer().start();
					//Penalty for mismatch
					this.getMainFrame().setScore(score-=5);
				}
			}
			return true;
		}
		// there are already the number of EasyMode (two face up cards) in the turnedCardsBuffer
		return false;
	}
	
	@Override
	protected boolean  isGameOver(){

		for (int i =0; i< this.getGrid().size();i++)
			if(!this.getGrid().get(i).isFaceUp()) return false;


		return true;
	}

}
