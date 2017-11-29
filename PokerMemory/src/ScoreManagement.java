/**
 * Class for some scoring methods
 *
 * @author Mar�a C. Novoa Garc�a (original author)
 * @author Alberto Canela Cubero (contribuitor)
 * @version Nov 2017
 */

public class ScoreManagement{

	final static private int JValue = 11;
	final static private int QValue = 12;
	final static private int KValue = 13;
	final static private int AValue = 20;
	final static private int tenValue = 10;

	public static int returnRankValue(Card card){
		int rankValue = 0;

		if(card.getRank().equals("t")){
			rankValue = tenValue;
		}
		else if(card.getRank().equals("j")){
			rankValue = JValue;
		}
		else if(card.getRank().equals("q")){
			rankValue = QValue;
		}
		else if(card.getRank().equals("k")){
			rankValue = KValue;
		}
		else if(card.getRank().equals("a")){
			rankValue = AValue;
		}
		else {
			rankValue = Integer.parseInt(card.getRank());
		}
		return rankValue;
	}
}