/**Subclass implements a new level combining Straight, Flush and Four of a Kind hands.
 * Also handles turning cards back down after a delay if the hand are not Straight, Flush or Four of a Kind hands.
 * Handles its scoring system per turn. User can choose to get scored or pass the hand.
 * 
 * @author Maria Novoa (class extender)
 * @author Alberto Canela (contributor)
 * @version Nov 2017
 */
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ComboLevel extends StraightLevel {

	private long score;
	private boolean noCombinationsLeft;

	protected ComboLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
		noCombinationsLeft = false;
	}

	@Override
	public String getLevel() {
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
			//Play turnUp sound
			AudioEffect.playCardSelectionSFX();

			// add the card to the list
			this.getTurnedCardsBuffer().add(card);
			if(this.getTurnedCardsBuffer().size() == getCardsToTurnUp()){
				// We are uncovering the last card in this turn
				// Record the player's turn
				this.getTurnsTakenCounter().increment();
				// get the other card (which was already turned up)

				//Put cards on the buffer
				Card otherCard1 = (Card) this.getTurnedCardsBuffer().get(0);
				Card otherCard2 = (Card) this.getTurnedCardsBuffer().get(1);
				Card otherCard3 = (Card) this.getTurnedCardsBuffer().get(2);
				Card otherCard4 = (Card) this.getTurnedCardsBuffer().get(3);

				//Turn up the last card for the player to see if there's a hand
				card.faceUp();

				if(isValid(otherCard1, otherCard2, otherCard3, otherCard4, card)){
					this.getTurnedCardsBuffer().clear();
					//Play correct sound
					AudioEffect.playCorrectSFX();
					//Check for gameover conditions
					this.updateNoCombinationsLeft();
				}
				else{
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

	private boolean optionPanel(String hand){
		String[] options = {hand, "Pass"};

		boolean playMore = JOptionPane.showOptionDialog(null, "Choose one of the following options:", 
				"Great, you found a hand!", JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,
				null, options,hand) == 0;
		MemoryFrame.dprintln(" " + playMore);

		return playMore;
	}

	public static boolean isStraight(Card a, Card b, Card c, Card d, Card e, StraightLevel level){

		int[] bufferArr = new int[level.getTurnedCardsBuffer().size()];

		bufferArr[0] = ScoreManagement.returnRankValue(a);
		bufferArr[1] = ScoreManagement.returnRankValue(b);
		bufferArr[2] = ScoreManagement.returnRankValue(c);
		bufferArr[3] = ScoreManagement.returnRankValue(d);
		bufferArr[4] = ScoreManagement.returnRankValue(e);

		Arrays.sort(bufferArr);

		if((e.getSuit() != a.getSuit()) || (e.getSuit() != b.getSuit()) || 
				(e.getSuit() != c.getSuit()) || (e.getSuit() != d.getSuit()) ){

			//General Instruction
			if((bufferArr[1] == bufferArr[0] + 1) && (bufferArr[2] == bufferArr[0] + 2)
					&& (bufferArr[3] == bufferArr[0] + 3 ) && (bufferArr[4] == bufferArr[0] + 4)){
				return true;
			}

			//Special Case #1: If otherCard1's rank is "t"

			else if((bufferArr[0] == 10) && (bufferArr[1] == 11) && (bufferArr[2] == 12) && (bufferArr[3] == 13)
					&& bufferArr[4] == 20){
				return true;
			}

			//Special Case #2: If otherCard1's rank is "a"

			else if((bufferArr[4] == 20) && (bufferArr[0] == 2) && (bufferArr[1] == 3) && (bufferArr[2] == 4)
					&& bufferArr[3] == 5){
				return true;
			}
		}
		return false;
	}

	public boolean isValid(Card a, Card b, Card c, Card d, Card e){

		int[] bufferArr = new int[this.getTurnedCardsBuffer().size()];
		boolean key = false;

		bufferArr[0] = ScoreManagement.returnRankValue(a);
		bufferArr[1] = ScoreManagement.returnRankValue(b);
		bufferArr[2] = ScoreManagement.returnRankValue(c);
		bufferArr[3] = ScoreManagement.returnRankValue(d);
		bufferArr[4] = ScoreManagement.returnRankValue(e);

		Arrays.sort(bufferArr);

		//Flush Hand Validation

		if(FlushLevel.isFlush(a, b, c, d, e)){

			if(this.optionPanel("Flush")){
				this.getMainFrame().setScore(score+= 700 + ScoreManagement.sumOfRanks(a, b, c, d ,e));
				key = true;
			}
			else {
				//key will be false and score minus five, add five to balance
				this.getMainFrame().setScore(score+= +5);
			}
		}

		//Straight Hand Validation

		else if(ComboLevel.isStraight(a,b,c,d,e, this)){

			if(this.optionPanel("Straight")){
				this.getMainFrame().setScore(score+= 500 + Math.pow(bufferArr[4], 2));
				key = true;
			}
			else {
				//key will be false and score minus five, add five to balance
				this.getMainFrame().setScore(score+= +5);
			}
		}

		//Four of a Kind Hand Validation

		else if((bufferArr[0] == bufferArr[1] && bufferArr[1] == bufferArr[2]  && bufferArr[2] == bufferArr[3] )
				|| (bufferArr[1] == bufferArr[2] && bufferArr[2] == bufferArr[3] && bufferArr[3] == bufferArr[4])){

			if(this.optionPanel("Four of a Kind")){

				this.getMainFrame().setScore(score += 100*bufferArr[3] + 4*bufferArr[4]);
				key = true;
			}
			else{
				//key will be false and score minus five, add five to balance
				this.getMainFrame().setScore(score+= +5);
			}
		}
		return key;
	}

	//Checks if there exist remaining valid combinations
	private void updateNoCombinationsLeft()
	{
		noCombinationsLeft = super.straightCombinationsLeft() == 0 && super.flushCombinationsLeft() == 0
				&& this.fourOfAKindCombinationsLeft() == 0;
	}

	@Override
	public boolean isGameOver() {
		if(noCombinationsLeft){
			//Show Ending Messages
			String GameOver = "Congratulations you have reached the end of the game\r\n"+
					"There is no more straight, flush or four of a kind combinations left\r\n\r\n Your Score: "+score+"\r\nMoves Made: "+this.getTurnsTakenCounter().getText();
			JOptionPane.showMessageDialog(this.getMainFrame(), GameOver, "Game Over", JOptionPane.PLAIN_MESSAGE);
			return true;
		}
		return false;
	}

	public int fourOfAKindCombinationsLeft() {
		int combinations  = 0;

		int[] downCards = new int[13];
		for (int i = 0; i< this.getGrid().size();i++) {
			if(!this.getGrid().get(i).isFaceUp()) {
				if(this.getGrid().get(i).getRank().equals("a")){
					downCards[0]++;
				}
				else{
					downCards[ScoreManagement.returnRankValue(this.getGrid().get(i))-1]++;
				}
			}
		}

		//We have to find the frequency of each rank and stored in the array with index rank - 1, rank "a" stored in 0

		for(int i: downCards){
			combinations+= i/4;
		}

		MemoryFrame.dprintln("combinations left: "+ combinations);
		return combinations;
	}
}