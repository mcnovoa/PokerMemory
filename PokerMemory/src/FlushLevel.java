import javax.swing.JFrame;

public class FlushLevel extends RankTrioLevel {

	private long score;

	// FLUSH LEVEL: The goal is to find, on each turn, five cards with the same suit.

	protected FlushLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
		this.getTurnsTakenCounter().setDifficultyModeLabel("Flush Level");
		this.setCardsToTurnUp(5);
		this.score = 0;
	}

	@Override
	public String getMode() {
		return "FlushMode";
	}

	public long getScore() {
		return score;
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
				Card otherCard3 = (Card) this.getTurnedCardsBuffer().get(2);
				Card otherCard4 = (Card) this.getTurnedCardsBuffer().get(3);
				if(FlushLevel.isFlush(otherCard1, otherCard2, otherCard3, otherCard4, card))
				{
					// Five cards match, calculate points and remove them from the list (they will remain face up)
					this.successScoreUpdate(card,otherCard1, otherCard2, otherCard3, otherCard4);
					this.getTurnedCardsBuffer().clear();
				}
				else
				{
					// The cards do not match, so start the timer to turn them down
					this.getTurnDownTimer().start();
					this.getMainFrame().setScore(score -= 5);
				}
			}
			return true;
		}
		return false;
	}
	public static boolean isFlush(Card a, Card b, Card c, Card d, Card e){
		if((a.getSuit().equals(b.getSuit())) &&
				(a.getSuit().equals(c.getSuit())) &&
				(a.getSuit().equals(d.getSuit())) &&
				(a.getSuit().equals(e.getSuit()))){
			return true;
		}
		
		return false;
	}

	private void successScoreUpdate(Card a, Card b, Card c, Card d, Card e)
	{

		score += 700 + ScoreManagement.sumOfRanks(a, b, c, d, e);
		this.getMainFrame().setScore(score);
	}
}
