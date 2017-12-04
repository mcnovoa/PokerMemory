/**Subclass implements the Straight level when five cards of consecutive value are discovered with at least two different suits.
 * Also handles turning cards back down after a delay if cards are not a Straight hand and handles score.
 * 
 * @author Maria Novoa (class extender)
 * @author Alberto Cubero (contributor)
 * @version Nov 2017
 * 
 */
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
	public boolean getAreCombinationsLeft() {
		return areCombinationsLeft;
	}

	@Override
	public String getLevel() {
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

					else if((bufferArr[0] == 10) && (bufferArr[1] == 11) && (bufferArr[2] == 12) && (bufferArr[3] == 13)
							&& bufferArr[4] == 20){

						this.successScoreUpdate(bufferArr[4]);
						this.getTurnedCardsBuffer().clear();
					}

					//Case #: If otherCard1's rank is "a"

					else if((bufferArr[4] == 20) && (bufferArr[0] == 2) && (bufferArr[1] == 3) && (bufferArr[2] == 4)
							&& bufferArr[3] == 5){
						this.successScoreUpdate(bufferArr[4]);
						this.getTurnedCardsBuffer().clear();
					}

					else{
						// The cards do not match, so start the timer to turn them down
						this.getTurnDownTimer().start();
						this.getMainFrame().setScore(score-=5);
						//Play wrong sound
						AudioEffect.playWrongSFX();
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
		//Play correct sound
		AudioEffect.playCorrectSFX();
		if(straightCombinationsLeft() == 0) {
			areCombinationsLeft = false;
		}
	}

	//GameOver when there are no more straight combinations. Show end Message.
	@Override
	public boolean  isGameOver(){
		if(!areCombinationsLeft){
			//Show Ending Messages
			String GameOver = "Congratulations you have reached the end of the game\r\n"+
					"There is no more straight combinations left\r\n\r\n Your Score: "+score+"\r\nMoves Made: "+this.getTurnsTakenCounter().getText();
			JOptionPane.showMessageDialog(this.getMainFrame(), GameOver, "Game Over", JOptionPane.PLAIN_MESSAGE);
			return true;
		}
		return false;
	}

	//Find the total number of valid combinations in the grid.
	public int straightCombinationsLeft() {
		int combinations  = 0;

		//Get the amount of cards facedown with the same rank and save to array where index + 1 means the rank of a card (rank "a" counted as 1 and 13)
		int[] rankFrequencies = new int[14];
		for (int i =0; i< this.getGrid().size();i++) {
			if(!this.getGrid().get(i).isFaceUp()) {
				if(this.getGrid().get(i).getRank().equals("a")){
					rankFrequencies[0]++;
					rankFrequencies[13]++;
				}
				else 
				{
					rankFrequencies[ScoreManagement.returnRankValue(this.getGrid().get(i))-1]++;
				}
			}
		}

		//Calculate number of possible straight combinations flush inclusive using multiplication (the straight*rule and 0 <= frequency <= 4.
		for(int j = 4; j<rankFrequencies.length;j++)
		{
			//Any rank that has zero frequency will make the count for the straight hands containing them 0.
			combinations += rankFrequencies[j-4]*rankFrequencies[j-3]*rankFrequencies[j-2]*rankFrequencies[j-1]*rankFrequencies[j];
		}
		combinations -= straightFlushCombinations("c")+straightFlushCombinations("d")+straightFlushCombinations("h")+straightFlushCombinations("s");

		MemoryFrame.dprintln("combinations possible: "+ combinations);
		return combinations;
	}

	//Calculate straight flush combinations from cards down in grid for a given suit.
	public int straightFlushCombinations(String suit) {
		int flushSCombinations = 0;
		int[] ranksOfSuit = new int[14];
		for (int i =0; i< this.getGrid().size();i++) {
			if((!this.getGrid().get(i).isFaceUp())&&(this.getGrid().get(i).getSuit().equals(suit))) {
				if(this.getGrid().get(i).getRank().equals("a")){
					ranksOfSuit[0]++;
					ranksOfSuit[13]++;
				}
				else 
				{
					ranksOfSuit[ScoreManagement.returnRankValue(this.getGrid().get(i))-1]++;
				}
			}
			for(int j = 4; j<ranksOfSuit.length;j++)
			{
				//frequency will be either 1 or 0, five ones means one flush straight combination.
				flushSCombinations += ranksOfSuit[j-4]*ranksOfSuit[j-3]*ranksOfSuit[j-2]*ranksOfSuit[j-1]*ranksOfSuit[j];
			}
		}
		return flushSCombinations;
	}
}