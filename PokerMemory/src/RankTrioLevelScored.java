import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
	private boolean areCombinationsLeft;
	
	protected RankTrioLevelScored(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
		score = 0;
		areCombinationsLeft = true;
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
					if(trioCombinationsLeft() == 0) {
						areCombinationsLeft = false;
					}
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
	
	//GameOver when there are no more trio combinations. Show end Message.
	@Override
	protected boolean  isGameOver(){
		if(!areCombinationsLeft){
			//Show Ending Messages
			String GameOver = "Congratulations you have reach the end of the game/r/n"+
			"There is no more trio combinations left/r/n/r/n Your Score: "+score+"\r\nMoves Made: "+this.getTurnsTakenCounter().getText();
			JOptionPane.showMessageDialog(this.getMainFrame(), GameOver, "Game Over", JOptionPane.PLAIN_MESSAGE);
			return true;
		}
		return false;
	}
	
	//Find the total number of valid combinations in the grid.
	private int trioCombinationsLeft() {
		int[] rankFrequencies = new int[13];
		int combinations = 0;
		//Get the amount of cards facedown with the same rank
		for (int i =0; i< this.getGrid().size();i++) {
			if(!this.getGrid().get(i).isFaceUp()) {
				if(this.getGrid().get(i).getRank().equals("a")){
					rankFrequencies[0]++;
					}
					else 
					{
						rankFrequencies[ScoreManagement.returnRankValue(this.getGrid().get(i))-1]++;
					}
			}
		}
		//Look for posible trio combinations
		for(int i: rankFrequencies)
		{
			if(i >= 3) {
				combinations++;
			}
		}
		MemoryFrame.dprintln("combinations Result "+ combinations);
		return combinations;
	}
}
