import java.util.ArrayList;

public class BaccaratGameLogic {

	//this will find the given hand's total value
    public int handTotal(ArrayList<Card> hand) {
        int total = 0;
        //we iterate through the hand
        for(int i = 0; i < hand.size(); i++) {
        	//if the value is > 9
        	//then by our rule its value is 0
        	//so we can ignore it
        	//only add up the values < 10
            if(hand.get(i).value < 10) {
                total += hand.get(i).value;
            }
        }
        //we can remove the digit in the tens place using modulo
        total = total % 10;
        return total;
    }

    //this method tells us who wins based on the hands given
    //for our implementation, hand1 will always be playerHand
    //and hand2 will always be bankerHand
    public String whoWon(ArrayList<Card> hand1, ArrayList<Card> hand2) {
        String statement;
        if(handTotal(hand1) > handTotal(hand2)) {
            statement = "Player";
        } else if (handTotal(hand2) > handTotal(hand1)) {
            statement = "Banker";
        } else {
            statement = "Draw";
        }
        return statement;
    }

    //this method evaluates whether or not the banker draws
    public boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard) {
    	//if the player didn't draw a third card,
    	//then if the banker's handTotal < 6, draw
    	//otherwise don't draw
		if(playerCard == null) {
			if(handTotal(hand) < 6) {
				return true;
			}
			else return false;
		}
		else {
			//these are all the other cases for when the banker draws
			if(handTotal(hand) < 3) {
				return true;
			} else if (handTotal(hand) == 3 && playerCard.value != 8) {
				return true;
			} else if (handTotal(hand) == 4 && playerCard.value < 0 || handTotal(hand) == 4 &&
					playerCard.value > 1 && playerCard.value < 8) {
				return true;
			} else if (handTotal(hand) == 5 && playerCard.value < 0 || handTotal(hand) == 5 &&
					playerCard.value > 3 && playerCard.value < 8) {
				return true;
			} else if (handTotal(hand) == 6 && playerCard.value > 5 && playerCard.value < 8) {
				return true;
			} else {
				return false;
			}
		}
    }

    //this method evaluates whether or not the player should draw
    //if the player's handTotal < 6, draw the card
    public boolean evaluatePlayerDraw(ArrayList<Card> hand) {
        if(handTotal(hand) < 6) {
            return true;
        }
        return false;
    }
}
