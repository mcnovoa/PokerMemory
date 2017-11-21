import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ScoreManagement extends JLabel {

	EasyLevel  easyLevel;
	EqualPairLevel equalPairLevel;
	RankTrioLevel rankTrioLevel;
	GameLevel gameLevel;
	ArrayList <Card> grid= new ArrayList<Card>();
	long score = 0;
	MemoryFrame mFrame = new MemoryFrame();

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}


	public ScoreManagement() throws IOException {
		super();
		// TODO Auto-generated constructor stub

	}

	public long returnTheScore(){
		
		if(gameLevel.getMode().equals(equalPairLevel.getMode())){
			//Score Instructions for "EqualPairLevel"


		}

		else if(gameLevel.getMode().equals(rankTrioLevel.getMode())){
			//Score Instructions for "RankTrioLevel"
			Card card = gameLevel.getTurnedCardsBuffer().get(0);
			Card card1 = gameLevel.getTurnedCardsBuffer().get(1);
			Card card2 = gameLevel.getTurnedCardsBuffer().get(2);
			if(card.getRank().equals(card1.getRank()) &&  card.getRank().equals(card2.getRank())){
				score =+ 100 + Integer.parseInt(card.getRank()) + Integer.parseInt(card1.getRank())
				+ Integer.parseInt(card2.getRank());

			}
			//		if((rankTrioLevel.turnUp()
			//				card.getRank().equals(otherCard1.getRank())) && (card.getRank().equals(otherCard2.getRank()))) {
			//	}

		}
		return score;
	}
}









