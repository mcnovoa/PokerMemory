import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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

				if(isValid(otherCard1, otherCard2, otherCard3, otherCard4, card)){
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
	private int optionPanel(){
		String[] options = { "Straight", "Flush","Four of a Kind", "Pass"};

		int playMore = JOptionPane.showOptionDialog(null, "Choose one of the following hands:", 
				"Great, you found a hand!", JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,
				null, options,options[0]);
		MemoryFrame.dprintln(" " + playMore);

		return playMore;
	}

	public static boolean isStraight(int a, int b, int c, int d,int e, StraightLevel level){

		int[] bufferArr = new int[level.getTurnedCardsBuffer().size()];

		bufferArr[0] = a;
		bufferArr[1] = b;
		bufferArr[2] = c;
		bufferArr[3] = d;
		bufferArr[4] = e;

		Arrays.sort(bufferArr);

		//General Instruction
		if((bufferArr[1] == bufferArr[0] + 1) && (bufferArr[2] == bufferArr[0] + 2)
				&& (bufferArr[3] == bufferArr[0] + 3 ) && (bufferArr[4] == bufferArr[0] + 4)){
			return true;
		}

		//Case #1: If otherCard1's rank is "t"

		if((bufferArr[0] == 10) && (bufferArr[1] == 11) && (bufferArr[2] == 12) && (bufferArr[3] == 13)
				&& bufferArr[4] == 20){
			return true;
		}

		//Case #2: If otherCard1's rank is "j"

		if((bufferArr[0] == 2) && (bufferArr[1] == 11) && (bufferArr[2] == 12) && (bufferArr[3] == 13)
				&& bufferArr[4] == 20){
			return true;
		}

		//Case #3: If otherCard1's rank is "q"

		if((bufferArr[0] == 2) && (bufferArr[1] == 3) && (bufferArr[2] == 12) && (bufferArr[3] == 13)
				&& bufferArr[4] == 20){
			return true;
		}

		//Case #4: If otherCard1's rank is "k"

		if((bufferArr[0] == 2) && (bufferArr[1] == 3) && (bufferArr[2] == 4) && (bufferArr[3] == 13)
				&& bufferArr[4] == 20){
			return true;
		}

		//Case #: If otherCard1's rank is "a"

		if((bufferArr[4] == 20) && (bufferArr[0] == 2) && (bufferArr[1] == 3) && (bufferArr[2] == 4)
				&& bufferArr[3] == 5){
			return true;
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

			switch(this.optionPanel()){
			case 0: 
				//the player chose a Straight hand but it was a flush
				break;

			case 1:
				//Player chose Flush hand and the if equals true
				this.getMainFrame().setScore(score+= 700 + ScoreManagement.sumOfRanks(a, b, c, d ,e));
				key = true;
				break;

			case 2:	
				//Player chose Four of a Kind hand but it was Flush
				break;

			case 3:
				//Player decides to pass
				this.getMainFrame().setScore(score+= +5);
				break;
			}
			return key;
		}

		//Straight Hand Validation

		if(ComboLevel.isStraight(bufferArr[0],bufferArr[1],bufferArr[2],
				bufferArr[3],bufferArr[4], this)){

			switch(this.optionPanel()){
			case 0:
				this.getMainFrame().setScore(score+= 1000 + 100*bufferArr[4]);
				key = true;
				break;

			case 1:
				//Player chose Flush hand but it was Straight 
				break;

			case 2:	
				//Player chose Four of a Kind hand but it was Flush
				break;

			case 3:
				//Player decides to pass
				this.getMainFrame().setScore(score+= +5);
				break;
			}
			return key;
		}

		//Four of a Kind Hand Validation
		if((bufferArr[0] == bufferArr[1] && bufferArr[1] == bufferArr[2]  && bufferArr[2] == bufferArr[3] )
				|| (bufferArr[1] == bufferArr[2] && bufferArr[2] == bufferArr[3] && bufferArr[3] == bufferArr[4])){

			switch(this.optionPanel()){
			case 0:
				//Player chose Straight hand but it was Four of a Kind 
				break;

			case 1:
				//Player chose Flush hand but it was Four of a Kind 
				break;

			case 2:	
				//Player chose Four of a Kind hand and the if equals true
				this.getMainFrame().setScore(score += 1000 + 400*bufferArr[3]);
				key = true;
				break;

			case 3:
				//Player decides to pass
				this.getMainFrame().setScore(score+= +5);
				break;
			}
			return key;
		}
		return key;
	}
}
