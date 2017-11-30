import javax.swing.JFrame;

/**Stores currently turned cards, allows only three cards to be uncovered on each turn
 * Also handles turning cards back down after a delay if cards have different ranks.
 * Implements scoring system.
 */

/**
 * @author Alberto Canela (class extender)
 * @author Maria Novoa *
 */

public class RankTrioLevelScored extends RankTrioLevel {

	private long score;
	
	protected RankTrioLevelScored(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
		this.score = 0;
	}
	@Override
	protected boolean turnUp(Card card) {
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
				Card otherCard1 = (Card) this.getTurnedCardsBuffer().get(0);
				Card otherCard2 = (Card) this.getTurnedCardsBuffer().get(1);
				if((card.getRank().equals(otherCard1.getRank())) && (card.getRank().equals(otherCard2.getRank()))) {
					// Three cards match, so remove them from the list (they will remain face up)
					this.getTurnedCardsBuffer().clear();
					//Calculate and update points for a match.
					this.getMainFrame().setScore(score += 100 + ScoreManagement.sumOfRanks(card, otherCard1, otherCard2));
				}
				else
				{
					// The cards do not match, so start the timer to turn them down
					this.getTurnDownTimer().start();
					// Penalty of five point for mismatch.
					this.getMainFrame().setScore(score -=5);
				}
			}
			return true;
		}
		return false;
	}
}
