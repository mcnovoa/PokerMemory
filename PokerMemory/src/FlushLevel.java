/**Subclass implements the Flush hand by selecting five cards with the same suit. 
 * Also handles turning cards back down after a delay if cards don't have the same suit.
 * Handles its scoring system per turn.
 * 
 * @author Alberto Canela (class extender)
 * @author Maria Novoa (contributor)
 * @version Nov 2017
 */
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FlushLevel extends RankTrioLevel {

	private long score;
	private boolean areCombinationsLeft;

	// FLUSH LEVEL: The goal is to find, on each turn, five cards with the same suit.

	protected FlushLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
		this.getTurnsTakenCounter().setDifficultyModeLabel("Flush Level");
		this.setCardsToTurnUp(5);
		this.score = 0;
		areCombinationsLeft = true;
	}

	@Override
	public String getLevel() {
		return "FlushMode";
	}

	public long getScore() {
		return score;
	}


	public boolean getAreCombinationsLeft() {
		return areCombinationsLeft;
	}

	public void setAreCombinationsLeft(boolean areCombinationsLeft) {
		this.areCombinationsLeft = areCombinationsLeft;
	}

	@Override
	protected boolean turnUp(Card card) {
		// the card may be turned
		if(this.getTurnedCardsBuffer().size() < getCardsToTurnUp()) 
		{
			//Play turnUp sound
			AudioEffect.playCardSelectionSFX();

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
					//Check if game must be over
					if(flushCombinationsLeft() == 0) {
						areCombinationsLeft = false;
					}
				}
				else
				{
					// The cards do not match, so start the timer to turn them down
					this.getTurnDownTimer().start();
					this.getMainFrame().setScore(score -= 5);
					//Play wrong sound
					AudioEffect.playWrongSFX();
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
		//Play correct sound
		AudioEffect.playCorrectSFX();
	}

	//GameOver when there are no more flush combinations. Show end Message.
	@Override
	public boolean  isGameOver(){
		if(!areCombinationsLeft){
			//Show Ending Messages
			String GameOver = "Congratulations you have reach the end of the game\r\n"+
					"There is no more flush combinations left\r\n\r\n Your Score: "+score+"\r\nMoves Made: "+this.getTurnsTakenCounter().getText();
			JOptionPane.showMessageDialog(this.getMainFrame(), GameOver, "Game Over", JOptionPane.PLAIN_MESSAGE);
			return true;
		}
		return false;
	}

	//Find the total number of valid combinations in the grid.
	public int flushCombinationsLeft() {
		int[] suitFrequencies = new int[4];
		int combinations = 0;
		//Get the amount of cards facedown with the same suit
		for (int i =0; i< this.getGrid().size();i++) {
			if(!this.getGrid().get(i).isFaceUp()) {
				if(this.getGrid().get(i).getSuit().equals("c")){
					suitFrequencies[0]++;
				}
				else if(this.getGrid().get(i).getSuit().equals("d")){
					suitFrequencies[1]++;
				}
				else if(this.getGrid().get(i).getSuit().equals("h")){
					suitFrequencies[2]++;
				}
				else {
					suitFrequencies[3]++;
				}
			}
		}
		//Look for posible flush combinations
		for(int i: suitFrequencies)
		{
			combinations += i/5;
		}
		MemoryFrame.dprintln("Combinations left: "+ combinations);
		return combinations;
	}
}
