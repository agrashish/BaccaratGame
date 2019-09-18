import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;



class DealerTest {

	// Test Cases for the Card Class
	
	
	//Test Card Class Constructor
	
	//Test Case 1
	@Test
	void testCardCreate() {
		Card ace = new Card("Hearts", 1);
		assertEquals("Card", ace.getClass().getName());
	}
	
	//Test Card Class data members
	
	//Test Case 2
	@Test
	void testCardSuit() {
		Card ace = new Card("Hearts", 1);
		assertEquals("Hearts", ace.suit);
	}
	
	//Test Case 3
	@Test
	void testCardValue() {
		Card ace = new Card("Hearts", 1);
		assertEquals(1, ace.value);
	}
	
	
	
	// Test Cases for the BaccaratDealer Class
	
	
	//Test BaccaratDealer Constructor
	
	//Test Case 4
	@Test
	void testBaccaratDealerConstructor() {
		BaccaratDealer dealer = new BaccaratDealer();
		assertEquals("BaccaratDealer", dealer.getClass().getName());
	}
	
	//Test generateDeck() method
	
	//Test Case 5
	@Test
	void testGenerateDeck() {
		//check if deck was generated
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		assertEquals("java.util.ArrayList", dealer.deck.getClass().getName());
	}
	
	//Test Case 6
	@Test
	void testGenerateDeckSize() {
		//test if deck generated is of size 52
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		assertEquals(52, dealer.deck.size());
	}
	
	//Test the dealHand() method
	
	//Test Case 7
	@Test
	void testDealHandExists() {
		BaccaratDealer dealer = new BaccaratDealer();
		//test if cards exist in the hand
		ArrayList<Card> hand = dealer.dealHand();
		assertFalse(hand.isEmpty());		
	}
	
	//Test Case 8
	@Test
	void testDealHandSize() {
		BaccaratDealer dealer = new BaccaratDealer();
		//test if dealHand returns a hand of 2 cards
		ArrayList<Card> hand = dealer.dealHand();
		assertEquals(2, hand.size());
	}
	
	//Test the drawOne() method
	
	//Test Case 9
	@Test
	void testDrawOne() {
		//test if drawOne() returns a card
		BaccaratDealer dealer = new BaccaratDealer();
		Card one = dealer.drawOne();
		assertEquals("Card", one.getClass().getName());
	}
	
	//Test Case 10
	@Test
	void testDrawOneValue() {
		//Test to make sure drawOne() returns a card within
		//the proper range of values
		BaccaratDealer dealer = new BaccaratDealer();
		Card one = dealer.drawOne();
		assertTrue(one.value >= 1 && one.value <= 13);
	}
	
	//Test shuffleDeck() method
	
	//Test Case 11
	@Test
	void testShuffleDeckSizes() {
		//test to make sure the deck is the same size
		//before and after shuffling
		BaccaratDealer dealer = new BaccaratDealer();
		int beforeSize = dealer.deckSize();
		dealer.shuffleDeck();
		int afterSize = dealer.deckSize();
		assertTrue(beforeSize == afterSize);
	}
	
	//Test Case 12
	@Test
	void testShuffleDeckOneCard() {
		//test if you get the same card if you
		//shuffle a deck of size 1
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.deck = new ArrayList<Card>();
		Card ace = new Card("Hearts", 1);
		dealer.deck.add(ace);
		Card before = dealer.deck.get(0);
		dealer.shuffleDeck();
		Card after = dealer.deck.get(0);
		assertTrue(before == after);
	}
	
	//Test Case 13
	@Test
	void testShuffleDeck() {
		//shuffle the deck
		//get the top 10 cards without removing them from the deck
		//store the cards in an array
		//then shuffle again, and see if they match
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		boolean isSame = true;
		//create the first two hands of 10 cards
		ArrayList<Card> hand = new ArrayList<Card>();
		dealer.shuffleDeck();
		for(int i = 0; i < 10; i++) {
			hand.add(dealer.deck.get(i));
		}
		ArrayList<Card> hand2 = new ArrayList<Card>();
		dealer.shuffleDeck();
		for(int i = 0; i < 10; i++) {
			hand2.add(dealer.deck.get(i));
		}
		
		//compare the hands. if there's any difference,
		//then the deck is shuffled
		for(int i = 0; i < 10; i++) {
			if(hand.get(i).suit != hand2.get(i).suit) {
				isSame = false;
				break;
			}
				
			if(hand.get(i).value != hand2.get(i).value) {
				isSame = false;
				break;
			}
		}
		//there is a very very small chance that
		//the order is the same after shuffling
		//but it is negligible
		assertFalse(isSame);
	}
	
	//Test deckSize() method
	
	//Test Case 14
	@Test
	void testDeckSizeFull() {
		//test size of deck when it is full
		BaccaratDealer dealer = new BaccaratDealer();
		assertEquals(52, dealer.deckSize());
	}
	
	//Test Case 15
	@Test
	void testDeckSizeNotFull() {
		//test size of deck after you have drawn one card
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.drawOne();
		assertEquals(51, dealer.deckSize());
	}
	
	
	//Test BaccaratGameLogic Class
	
	//Test BaccaratGameLogic Constructor
	
	//Test Case 16
	@Test
	void testBaccaratGameLogicConstructor() {
		BaccaratGameLogic logic = new BaccaratGameLogic();
		assertEquals("BaccaratGameLogic", logic.getClass().getName());
	}
	
	//Test whoWon() method
	
	//Test Case 17
	@Test
	void testWhoWonPlayer() {
		//we'll create two hands manually
		//then, we know which one should win,
		//in this case, player should win
		//we'll test if we get that result
		BaccaratGameLogic logic = new BaccaratGameLogic();
		
		Card ace1 = new Card("Hearts", 1);
		Card ace2 = new Card("Spades", 1);
		Card two1 = new Card("Clubs", 2);
		Card three1 = new Card("Diamonds", 3);
		
		//player's hand should equal 4
		ArrayList<Card> playerHand = new ArrayList<Card>();
		playerHand.add(ace1);
		playerHand.add(three1);
		
		//banker's hand should equal 3
		ArrayList<Card> bankerHand = new ArrayList<Card>();
		bankerHand.add(ace2);
		bankerHand.add(two1);
		
		//player should win
		assertEquals("Player", logic.whoWon(playerHand, bankerHand));
	}
	
	//Test Case 18
	@Test
	void testWhoWonBanker() {
		//we'll create two hands manually
		//then, we know which one should win,
		//in this case, banker should win
		//we'll test if we get that result
		BaccaratGameLogic logic = new BaccaratGameLogic();
		
		Card ace1 = new Card("Hearts", 1);
		Card ace2 = new Card("Spades", 1);
		Card two1 = new Card("Clubs", 2);
		Card three1 = new Card("Diamonds", 3);
		
		//player's hand should equal 3
		ArrayList<Card> playerHand = new ArrayList<Card>();
		playerHand.add(ace1);
		playerHand.add(two1);
		
		//banker's hand should equal 4
		ArrayList<Card> bankerHand = new ArrayList<Card>();
		bankerHand.add(ace2);
		bankerHand.add(three1);
		
		//banker should win
		assertEquals("Banker", logic.whoWon(playerHand, bankerHand));
	}
	
	//Test Case 19
	@Test
	void testWhoWonDraw() {
		//we'll create two hands manually
		//then, we know which one should win,
		//in this case, it should be a draw
		//we'll test if we get that result
		BaccaratGameLogic logic = new BaccaratGameLogic();
		
		Card ace1 = new Card("Hearts", 1);
		Card ace2 = new Card("Spades", 1);
		Card two1 = new Card("Clubs", 2);
		Card two2 = new Card("Diamonds", 2);
		
		//player's hand should equal 3
		ArrayList<Card> playerHand = new ArrayList<Card>();
		playerHand.add(ace1);
		playerHand.add(two1);
		
		//banker's hand should equal 3
		ArrayList<Card> bankerHand = new ArrayList<Card>();
		bankerHand.add(ace2);
		bankerHand.add(two2);
		
		//should be a draw
		assertEquals("Draw", logic.whoWon(playerHand, bankerHand));
	}
	
	//Test handTotal() method
	
	//Test Case 20
	@Test
	void testHandTotal() {
		BaccaratGameLogic logic = new BaccaratGameLogic();
		Card ace1 = new Card("Hearts", 1);
		Card two1 = new Card("Clubs", 2);
		ArrayList<Card> playerHand = new ArrayList<Card>();
		playerHand.add(ace1);
		playerHand.add(two1);
		//hand value should be 3
		assertEquals(3, logic.handTotal(playerHand));
	}
	
	//Test Case 21
	@Test
	void testHandTotal2() {
		BaccaratGameLogic logic = new BaccaratGameLogic();
		Card ace1 = new Card("Hearts", 1);
		Card eight1 = new Card("Diamonds", 8);
		ArrayList<Card> playerHand = new ArrayList<Card>();
		playerHand.add(ace1);
		playerHand.add(eight1);
		//hand value should be 9
		assertEquals(9, logic.handTotal(playerHand));
	}
	
	//Test Case 22
	@Test
	void testHandTotal3() {
		//test if hand total is 0
		BaccaratGameLogic logic = new BaccaratGameLogic();
		Card ace1 = new Card("Hearts", 1);
		Card nine1 = new Card("Diamonds", 9);
		ArrayList<Card> playerHand = new ArrayList<Card>();
		playerHand.add(ace1);
		playerHand.add(nine1);
		//hand value should be 0
		assertEquals(0, logic.handTotal(playerHand));
	}
	
	//Test the evaluateBankerDraw method
	
	//Test Case 23
	@Test
	void testBankerNotDraw() {
		//test case where banker does not draw
		//meaning the hand total is >= 7
		BaccaratGameLogic logic = new BaccaratGameLogic();
		Card ace1 = new Card("Hearts", 1);
		Card eight1 = new Card("Diamonds", 8);
		Card nine1 = new Card("Diamonds", 9);
		ArrayList<Card> bankerHand = new ArrayList<Card>();
		bankerHand.add(ace1);
		bankerHand.add(eight1);
		//hand value should be 9
		assertFalse(logic.evaluateBankerDraw(bankerHand, nine1));
	}
	
	//Test Case 24
	@Test
	void testBankerDrawsForSure() {
		BaccaratGameLogic logic = new BaccaratGameLogic();
		Card ace1 = new Card("Hearts", 1);
		Card eight1 = new Card("Diamonds", 8);
		Card nine1 = new Card("Diamonds", 9);
		ArrayList<Card> bankerHand = new ArrayList<Card>();
		bankerHand.add(ace1);
		bankerHand.add(nine1);
		//hand value should be 0, so will draw for sure
		assertTrue(logic.evaluateBankerDraw(bankerHand, eight1));
	}
	
	//Test Case 25
	@Test
	void testBankerMightDrawTrue() {
		//in this case, banker's draw is ambigious
		//but we will have the banker draw
		BaccaratGameLogic logic = new BaccaratGameLogic();
		Card ace1 = new Card("Hearts", 1);
		Card three1 = new Card("Diamonds", 3);
		Card seven1 = new Card("Diamonds", 7);
		ArrayList<Card> bankerHand = new ArrayList<Card>();
		bankerHand.add(ace1);
		bankerHand.add(three1);
		//so banker hand total is 4
		//must draw based on player card
		//in this case, player card is 7
		//so draw
		assertTrue(logic.evaluateBankerDraw(bankerHand, seven1));
	}
	
	//Test Case 26
	@Test
	void testBankerMightDrawFalse() {
		//in this case, banker's draw is ambigious
		//but we will have the banker draw
		BaccaratGameLogic logic = new BaccaratGameLogic();
		Card ace1 = new Card("Hearts", 1);
		Card three1 = new Card("Diamonds", 3);
		Card eight1 = new Card("Diamonds", 8);
		ArrayList<Card> bankerHand = new ArrayList<Card>();
		bankerHand.add(ace1);
		bankerHand.add(three1);
		//so banker hand total is 4
		//must draw based on player card
		//in this case, player card is 8
		//so don't draw draw
		assertFalse(logic.evaluateBankerDraw(bankerHand, eight1));
	}
	
	//Test the evaluatePlayerDraw() method
	
	//Test Case 27
	@Test
	void testPlayerNoDraw() {
		//in this case, the player doesn't draw
		//since their total will be >= 6
		BaccaratGameLogic logic = new BaccaratGameLogic();
		Card ace1 = new Card("Hearts", 1);
		Card eight1 = new Card("Diamonds", 8);
		ArrayList<Card> playerHand = new ArrayList<Card>();
		playerHand.add(ace1);
		playerHand.add(eight1);
		assertFalse(logic.evaluatePlayerDraw(playerHand));
	}
	
	//Test Case 28
	@Test
	void testPlayerDraw() {
		//in this case, the player draws
		//since their total will be < 6
		BaccaratGameLogic logic = new BaccaratGameLogic();
		Card ace1 = new Card("Hearts", 1);
		Card four1 = new Card("Diamonds", 4);
		ArrayList<Card> playerHand = new ArrayList<Card>();
		playerHand.add(ace1);
		playerHand.add(four1);
		assertTrue(logic.evaluatePlayerDraw(playerHand));
	}
	
	//Test the BaccaratGame class constructor
	
	//Test Case 29
	@Test
	void testBaccaratGameConstructor() {
		//need this line of code to test with javafx
		BaccaratGame game = new BaccaratGame();
		assertEquals("BaccaratGame", game.getClass().getName());
	}
	
	//Test the evaluateWinnings() method
	
	//Test Case 30
	@Test
	void evaluateWinningsPlayerWin() {
		//evaluate the test case where you bet on the player,
		//and the player won:
		BaccaratGame game = new BaccaratGame();
		//player's hand total is 9
		game.playerHand.add(new Card("Hearts", 10));
		game.playerHand.add(new Card("Hearts", 9));
		//banker's hand total is 1
		game.bankerHand.add(new Card("Hearts", 11));
		game.bankerHand.add(new Card("Hearts", 1));
		//so, player should win
		//if the player bets $10 on player
		//then they should win $10
		game.betOn = "Player";
		game.currentBet = 10;
		assertEquals(10, game.evaluateWinnings(), "Incorrect winnings");
	}
	
	//Test Case 31
	@Test
	void evaluateWinningsPlayerLose() {
		//evaluate the test case where you bet on the player,
		//and the player lost:
		BaccaratGame game = new BaccaratGame();
		//player's hand total is 1
		game.playerHand.add(new Card("Hearts", 10));
		game.playerHand.add(new Card("Hearts", 1));
		//banker's hand total is 9
		game.bankerHand.add(new Card("Hearts", 11));
		game.bankerHand.add(new Card("Hearts", 9));
		//so, player should lose
		//if the player bets $10 on player
		//then they should lose $10
		game.betOn = "Player";
		game.currentBet = 10;
		assertEquals(-10, game.evaluateWinnings(), "Incorrect winnings");
	}
	
	//Test Case 32
	@Test
	void evaluateWinningsBankerWin() {
		//evaluate the test case where you bet on the banker,
		//and the banker won:
		BaccaratGame game = new BaccaratGame();
		//player's hand total is 1
		game.playerHand.add(new Card("Hearts", 10));
		game.playerHand.add(new Card("Hearts", 1));
		//banker's hand total is 9
		game.bankerHand.add(new Card("Hearts", 11));
		game.bankerHand.add(new Card("Hearts", 9));
		//so, banker should win
		//if the player bets $10 on banker
		//then they should win $9.50
		//due to the 5% commission
		game.betOn = "Banker";
		game.currentBet = 10;
		assertEquals(9.5, game.evaluateWinnings(), "Incorrect winnings");
	}
	
	//Test Case 33
	@Test
	void evaluateWinningsBankerLose() {
		//evaluate the test case where you bet on the banker,
		//and the banker lost:
		BaccaratGame game = new BaccaratGame();
		//player's hand total is 9
		game.playerHand.add(new Card("Hearts", 10));
		game.playerHand.add(new Card("Hearts", 9));
		//banker's hand total is 1
		game.bankerHand.add(new Card("Hearts", 11));
		game.bankerHand.add(new Card("Hearts", 1));
		//so, banker should lose
		//if the player bets $10 on banker
		//then they should lose $10
		game.betOn = "Banker";
		game.currentBet = 10;
		assertEquals(-10, game.evaluateWinnings(), "Incorrect winnings");
	}
	
	//Test Case 34
	@Test
	void evaluateWinningsDrawWin() {
		//evaluate the test case where you bet on a draw
		//and it was a draw
		BaccaratGame game = new BaccaratGame();
		//player's hand total is 9
		game.playerHand.add(new Card("Hearts", 10));
		game.playerHand.add(new Card("Hearts", 9));
		//banker's hand total is 9
		game.bankerHand.add(new Card("Hearts", 8));
		game.bankerHand.add(new Card("Hearts", 1));
		//so, it should be a draw
		//if the player bets $10 on draw
		//then they should win $70
		//since the way have implemented evaluateWinnings()
		//involves adding evaluateWinnings() to totalWinnings()
		//so you put in $10, you should get $80 back
		//since the $10 was never removed
		//We get $10 + $70 = $80
		game.betOn = "Draw";
		game.currentBet = 10;
		assertEquals(70, game.evaluateWinnings(), "Incorrect winnings");
	}
	
	//Test Case 35
	@Test
	void evaluateWinningsDrawLose() {
		//evaluate the test case where you bet on a draw
		//and it wasn't a draw
		BaccaratGame game = new BaccaratGame();
		//player's hand total is 9
		game.playerHand.add(new Card("Hearts", 10));
		game.playerHand.add(new Card("Hearts", 9));
		//banker's hand total is 1
		game.bankerHand.add(new Card("Hearts", 11));
		game.bankerHand.add(new Card("Hearts", 1));
		//so, it shouldn't be a draw
		//then the better should lose their bet
		game.betOn = "Draw";
		game.currentBet = 10;
		assertEquals(-10, game.evaluateWinnings(), "Incorrect winnings");
	}
	
	//Test Case 36
	@Test
	void evaluateWinningsPlayerTie() {
		//evaluate the test case where you bet on player
		//and it was a draw
		BaccaratGame game = new BaccaratGame();
		//player's hand total is 9
		game.playerHand.add(new Card("Hearts", 10));
		game.playerHand.add(new Card("Hearts", 9));
		//banker's hand total is 9
		game.bankerHand.add(new Card("Hearts", 8));
		game.bankerHand.add(new Card("Hearts", 1));
		//so, it should be a draw
		//then since there's no bet on draw
		//no one should lose money
		//so evaluateWinnings() would be 0
		game.betOn = "Player";
		game.currentBet = 10;
		assertEquals(0, game.evaluateWinnings(), "Incorrect winnings");
	}
}
