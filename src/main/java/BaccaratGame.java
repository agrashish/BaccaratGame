import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class BaccaratGame extends Application {
	
	/*
	 * 
	 * 
	 * This area is where we store some variables that keep track of
	 * player data, as well as initializing some classes we'll need
	 * to play the game
	 * 
	 * We also declare some variables needed in the evaluateWinnings() method
	 * 
	 * 
	 */
	
	//initialize the player's hand and banker's hand
	ArrayList<Card> playerHand = new ArrayList<Card>();
	ArrayList<Card> bankerHand = new ArrayList<Card>();
	
	//initialize a dealer and logic class
	BaccaratDealer theDealer = new BaccaratDealer();
	BaccaratGameLogic gameLogic = new BaccaratGameLogic();
	
	//these are our starting winnings
	double totalWinnings = 10000.00;
	
	//value storing the current bet amount
	double currentBet;
	
	//value storing the outcome that was bet on
	String betOn;
	
	//this value will store the result of who won
	//it could be "Player", "Banker", or "Draw"
	String result;
	
	//this is just a string used to display the results of the game
	//we will generally use this to give output such as
	//"Congrats, you bet Banker! You win!"
	String gameResult;

	/*
	 * 
	 * 
	 * Here we describe the evaluateWinnings() method
	 * 
	 * 
	 */
	

	//this method evaluates how much the player wins in that round
	//what we will do is add this amount to totalWinnings
	public double evaluateWinnings() {
		//first we store who won
		result = gameLogic.whoWon(playerHand,bankerHand);
		//case where it was a draw
		if(result == "Draw") {
			if(betOn != "Draw") {
				//if player didn't bet on draw, they don't lose money
				return 0;
			} else {
				//if player did bet on draw, they get 7*their bet
				//this is because we already have 1/8 of the winnings in totalWinnings
				//we need 7/8 more
				return 7 * currentBet;
			}
		} 
		//case where player hand wins
		else if (result == "Player") {
			if(betOn != "Player") {
				//if player didn't bet on player, they lose their bet
				return -currentBet;
			} else {
				//otherwise, they gain that amount 
				return currentBet;
			}
		} 
		//case where banker hand wins
		else {
			if(betOn != "Banker") {
				//if player didn't bet on banker, they lose their bet
				return -currentBet;
			} else {
				//otherwise, they win 0.95*that amount
				//this is because bank takes 5% commission
				return 0.95 * currentBet;
			}
		}
	}
	
	
	/*
	 * 
	 * 
	 * Now we will start with the declaration of some of the elements
	 * needed for the GUI
	 * 
	 * 
	 */
	
	//We will have 4 scenes
	Scene scene1, scene2, scene3, scene4;

	//Scene 1 (Opening Scene)
	
	//these buttons will display on the opening scene
	//letting you start the game with 'play', and exit with 'exit'
	Button play, exit;
	
	
	//Scene 2 (Betting Scene)
	
	//this Text will be used to display your current totalWinnings
	//along with asking you to make a bet
	Text betText;
	//these buttons are used on the betting scene
	//you use these to pick the outcome you will bet on
	Button player, banker, draw;
	//this is a textfield that we will use to enter in our currentBet
	TextField betAmount;
	//this button is used to submit the bet
	Button submitBet;
	
	//Scene 3 (Game Scene)
	
	//This button will display at the end of a round
	//it will let you start the next round
	Button nextRound;
	//This Text will keep displaying the player's current winnings
	//along with the results of the game
	Text resultsText;

	
	//these our pause transitions we will use to progress through the game
	int pauseRatio = 1;
	PauseTransition pause = new PauseTransition(Duration.seconds(1*pauseRatio));
	PauseTransition pause2 = new PauseTransition(Duration.seconds(2*pauseRatio));
	PauseTransition pause3 = new PauseTransition(Duration.seconds(3*pauseRatio));
	PauseTransition pause4 = new PauseTransition(Duration.seconds(4*pauseRatio));
	PauseTransition pause5 = new PauseTransition(Duration.seconds(5*pauseRatio));


	//these arraylists will store the card images,
	//and the card image views we will construct
	ArrayList <Image> handImgs = new ArrayList<Image>();
	ArrayList <ImageView> imgViews = new ArrayList<ImageView>();
	
	//this hbox will be used to display the player's cards
	HBox cards = new HBox(10);
	//this one wil be used to display the banker's cards
	HBox cards2 = new HBox(10);
	
	/*
	 * 
	 * These are a couple of utility methods we wrote
	 * 
	 */


	//this method is used on the betting scene
	//once the user selects the outcome they will bet on,
	//this function will get called, and disable all of those buttons
	//along with enabling the submitBet button
	public void disableAll()
	{
		player.setDisable(true);
		banker.setDisable(true);
		draw.setDisable(true);
		submitBet.setDisable(false);
	}

	//this method is the reverse, used to undo disableAll()
	public void enableAll() {
		player.setDisable(false);
		banker.setDisable(false);
		draw.setDisable(false);
		submitBet.setDisable(true);
	}


	/*
	 * 
	 * 
	 * Now we can define our main method
	 * 
	 * 
	 */
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	/*
	 * 
	 * 
	 * And then finally the start method
	 * 
	 * 
	 */

    @Override
	public void start(Stage primaryStage) throws Exception {
    	
    	//set the state title to 'Baccarat'
		primaryStage.setTitle("Baccarat");

		//create all of our buttons
		player = new Button("Player");
		banker = new Button("Banker");
		draw = new Button("Draw");
		exit = new Button("Exit");
		play = new Button("Play");
		submitBet = new Button("Submit Bet");
		nextRound = new Button("Next Round");

		//we start with being unable to submit our bet
		//(without first selecting an outcome to bet on)
		submitBet.setDisable(true);
		
		
		
		/*
		 * 
		 * Here we set up all of the menuBar code
		 * 
		 */

		//we have one menuBar, for each of the scenes
		MenuBar menuBar = new MenuBar();
		MenuBar menuBar2 = new MenuBar();
		MenuBar menuBar3 = new MenuBar();
		MenuBar menuBar4 = new MenuBar();

		
		//this is our menu that will hold the menuItems exit and fresh start
		Menu menuOne = new Menu("Options");

		
		//we create the menu items,
		//then add event listeners for them
		
		MenuItem exitGame = new MenuItem("Exit");
		MenuItem freshStart = new MenuItem("Fresh Start");

		//just exit the game by clicking exit
		exitGame.setOnAction(s -> {
			Platform.exit();
		});

		//start the game over by clicking fresh start
		freshStart.setOnAction(w -> {
			//clear and reset any needed variables
			playerHand.clear();
			bankerHand.clear();
			currentBet = 0;
			betOn = " ";
			imgViews.clear();
			handImgs.clear();
			betAmount.clear();
			totalWinnings = 10000;
			//remove all of the nodes from the two hboxes
			//that contain images of the player and banker hand
			cards.getChildren().removeIf(n -> n!=null);
			cards2.getChildren().removeIf(n -> n!=null);
			betText.setText("You have $" + totalWinnings + " Please enter your bet: ");
			resultsText.setText("Total Winnings: " + totalWinnings);
			//undo the actions of disableAll()
			enableAll();
			//go to the betting scene
			primaryStage.setScene(scene2);
		});

		//add all of the items to the menu
		menuOne.getItems().addAll(exitGame,freshStart);

		//add the menu to all of the menuBars
		menuBar.getMenus().addAll(menuOne);
		menuBar2.getMenus().addAll(menuOne);
		menuBar3.getMenus().addAll(menuOne);
		menuBar4.getMenus().addAll(menuOne);

		/*
		 * 
		 * Now, we have set up the menu
		 * We now build the opening scene
		 * 
		 */

		//this is a vbox holding most of our opening scene
		VBox v1 = new VBox();
		v1.getChildren().addAll(play,exit);
		v1.setAlignment(Pos.CENTER);

		//this borderpane will hold our vbox + menuBar
		BorderPane bp2 = new BorderPane();
		bp2.setCenter(v1);
		bp2.setTop(menuBar2);

		//now we create our opening scene
		int width = 1024;
		int height = 720;
		scene1 = new Scene(bp2,width,height);
		Image image1 = new Image("openingscreen.png");
		BackgroundImage b1 = new BackgroundImage(image1,
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT,
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));;
		bp2.setBackground(new Background(b1));
		
		/*
		 * 
		 * Now that we have made our opening scene,
		 * we now make the betting scene
		 * 
		 */

		//vbox holding most of betting scene's elements
		VBox v2 = new VBox();
		//set up betText
		betText = new Text("You have $" + totalWinnings + " Please enter your bet: ");
		betText.setFont(Font.font("", FontPosture.ITALIC, 18));
		betAmount = new TextField();
		betAmount.setMaxWidth(600);

		//our betting scene needs all these buttons along with 
		//the Text betText and the textField betAmount
		v2.getChildren().addAll(betText,player,banker,draw,betAmount,submitBet);
		v2.setAlignment(Pos.CENTER);

		//border pane holding our vbox + menuBar
		BorderPane bp3 = new BorderPane();
		bp3.setCenter(v2);
		bp3.setTop(menuBar3);

		//now we create our betting scene
		scene2 = new Scene(bp3,width,height);
		Image image2 = new Image("gametable.png");
		BackgroundImage b2 = new BackgroundImage(image2,
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT,
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));;
		bp3.setBackground(new Background(b2));
		
		/*
		 * 
		 * Now we make our third scene
		 * The game scene
		 * 
		 */

		//first we create our player and banker card labels
		
		Label playerCard = new Label("Player Cards",cards);
		playerCard.setAlignment(Pos.BOTTOM_LEFT);
		playerCard.setMinWidth(350);
		playerCard.setPadding(new Insets(50,0,0,10));

		Label bankerCard = new Label("Banker Cards",cards2);
		bankerCard.setAlignment(Pos.BOTTOM_RIGHT);
		bankerCard.setMinWidth(350);
		bankerCard.setPadding(new Insets(50,10,0,0));

		//then we set up our resultsText at this stage to just display totalWinnings
		resultsText = new Text();
		resultsText.setFont(Font.font("", FontPosture.ITALIC, 18));
		resultsText.setText("Total Winnings: " + totalWinnings);

		//since the round is still running for now,
		//we make sure that nextRound is not clickable
		nextRound.setDisable(true);
		
		//Now we set up the borderpane
		//this borderpane will pull together many elements
		BorderPane bp = new BorderPane();
		bp.setLeft(playerCard);
		bp.setCenter(resultsText);
		bp.setRight(bankerCard);
		bp.setTop(menuBar);
		bp.setBottom(nextRound);
		nextRound.setPadding(new Insets(15,13,15,13));
		

		//finally, we create our game scene
		scene3 = new Scene(bp,width,height);
		Image image3 = new Image("gametable.png");
		BackgroundImage b7 = new BackgroundImage(image3,
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT,
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
		bp.setBackground(new Background(b7));

		/*
		 * 
		 * This is our final scene
		 * The Game Over Scene
		 * 
		 */
		
		
		//First we set up Text that will display GameOver
		Text overText = new Text();
		overText.setFont(Font.font("", FontPosture.ITALIC, 24));
		overText.setText("GAME OVER!!!!");

		//Then, we set up our borderPane, which will
		//hold our Text, and menu, and an exit button
		BorderPane bp4 = new BorderPane();
		bp4.setCenter(overText);
		bp4.setBottom(exit);
		bp4.setTop(menuBar4);

		//finally, we create our game over scene
		scene4 = new Scene(bp4,width,height);
		Image image4 = new Image("gameover.png");
		BackgroundImage b8 = new BackgroundImage(image4,
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT,
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
		bp4.setBackground(new Background(b8));


		/*
		 * 
		 * Now we write event handlers for 
		 * all of our buttons that we created earlier
		 * 
		 */
		
		//Opening Scene Buttons
		
		//play button moves game to betting scene
		play.setOnAction(e -> {
			primaryStage.setScene(scene2);
		});

		//exit button exits the game
		exit.setOnAction(e -> {
			Platform.exit();
		});

		//Betting Scene Buttons
		
		//bets on player
		//also disables other bet buttons
		player.setOnAction(e -> {
			betOn = "Player";
			disableAll();
		});

		//bets on banker
		//also disables other bet buttons
		banker.setOnAction(e -> {
			betOn = "Banker";
			disableAll();
		});

		//bets on draw
		//also disables other bet buttons
		draw.setOnAction(e -> {
			betOn = "Draw";
			disableAll();
		});

		//submits the bet
		//kickstarts the game
		submitBet.setOnAction(e -> {
			//parse in the betAmount field's text to currentBet
			currentBet = Double.parseDouble(betAmount.getText());
			//move to game scene
			primaryStage.setScene(scene3);
			//create a new deck for the round and shuffle it
			theDealer.generateDeck();
			//deal player and banker hand
			playerHand = theDealer.dealHand();
			bankerHand = theDealer.dealHand();
			//disable the nextRound button (at least until the round is over)
			nextRound.setDisable(true);

			//prints the player's cards as images, after a pause
			pause.setOnFinished(p -> playerCards());
			pause.play();

			//do the same for bankerCards
			pause2.setOnFinished(f -> bankerCards());
			pause2.play();

			//if the player needs to draw a card, do so
			if(gameLogic.evaluatePlayerDraw(playerHand) == true) {
				playerHand.add(theDealer.drawOne());
			}

			//if the player has 3 cards, do this
			if(playerHand.size() > 2) {
				//if the banker should draw, then do so
				if (gameLogic.evaluateBankerDraw(bankerHand,playerHand.get(2)) == true) {
					bankerHand.add(theDealer.drawOne());
				}
			} 
			//otherwise, if the player has 2 cards, do this
			else {
				//if the banker should draw, do so
				if(gameLogic.evaluateBankerDraw(bankerHand,null) == true) {
					bankerHand.add(theDealer.drawOne());
				}
			}

			//add to resultsText the player total, banker total, and who won
			pause3.setOnFinished(q -> resultsText.setText("Total Winnings: " + totalWinnings + "\n"
					+ "Player Total: " + gameLogic.handTotal(playerHand) + " Banker Total:" + gameLogic.handTotal(bankerHand)
					+ " \n" + gameLogic.whoWon(playerHand,bankerHand) + " wins\n"));
			pause3.play();
			
			//update totalWinnings
			totalWinnings += evaluateWinnings();
			//then update gameResult accordingly
			if(result == betOn) {
				gameResult = "Congrats, you bet " + betOn + " ! You win!";
			} else {
				gameResult = "Sorry, you bet " + betOn + "! You lost your bet!";
			}

			//add gameResult to resultsText
			//and also enable the nextRound button
			pause4.setOnFinished(x -> {resultsText.setText("Total Winnings: " + totalWinnings + "\n" +
					"Player Total: " + gameLogic.handTotal(playerHand) + " Banker Total:" + gameLogic.handTotal(bankerHand)
					+ " \n" + gameLogic.whoWon(playerHand,bankerHand) + " wins\n" + gameResult); nextRound.setDisable(false);});
			pause4.play();

			//if the totalWinnings is ever <= 0, trigger game over
			if(totalWinnings <= 0) {
				pause5.setOnFinished(w -> primaryStage.setScene(scene4));
				pause5.play();
			}

		});

		//the next round button starts the next round
		nextRound.setOnAction(e -> {
			//clear and reset any needed variables
			playerHand.clear();
			bankerHand.clear();
			imgViews.clear();
			handImgs.clear();
			cards.getChildren().removeIf(n -> n!=null);
			cards2.getChildren().removeIf(n -> n!=null);
			betText.setText("You have $" + totalWinnings + " Please enter your bet: ");
			resultsText.setText("Total Winnings: " + totalWinnings);
			betAmount.clear();
			enableAll();
			//change to betting scene
			primaryStage.setScene(scene2);
		});

		//set opening scene
		primaryStage.setScene(scene1);
		//show the stage
		primaryStage.show();
	}

    //this is our helper function to set up card images
	void cardImages(ArrayList<Card> hand) {
		String cardSuit = "";
		String cardValue = "";
		//we loop through all of the cards in the hand
		for(int i = 0; i < hand.size(); i++) {
			if(hand.get(i).suit == "Hearts") {
				cardSuit = "H";
			} else if(hand.get(i).suit == "Spades") {
				cardSuit = "S";
			} else if(hand.get(i).suit == "Diamonds") {
				cardSuit = "D";
			} else if (hand.get(i).suit == "Clubs") {
				cardSuit = "C";
			}

			if(hand.get(i).value == 1) {
				cardValue = "A";
			} else if (hand.get(i).value == 2) {
				cardValue = "2";
			} else if (hand.get(i).value == 3) {
				cardValue = "3";
			} else if (hand.get(i).value == 4) {
				cardValue = "4";
			} else if (hand.get(i).value == 5) {
				cardValue = "5";
			} else if (hand.get(i).value == 6) {
				cardValue = "6";
			} else if (hand.get(i).value == 7) {
				cardValue = "7";
			} else if (hand.get(i).value == 8) {
				cardValue = "8";
			} else if (hand.get(i).value == 9) {
				cardValue = "9";
			} else if (hand.get(i).value == 10) {
				cardValue = "10";
			} else if (hand.get(i).value == 11) {
				cardValue = "J";
			} else if (hand.get(i).value == 12) {
				cardValue = "K";
			} else if (hand.get(i).value == 13) {
				cardValue = "Q";
			}

			//after deducing the card's suit and value,
			//we can pull the corresponding images
			Image image = new Image(cardValue + cardSuit + ".png");
			//we store the image in our image arraylist
			handImgs.add(image);
			//then we create a new imageview for the image
			ImageView v = new ImageView();
			v.setImage(handImgs.get(i));
			v.setFitHeight(150);
			v.setFitWidth(75);
			v.setPreserveRatio(true);
			//then we add that image to the imageview arraylist
			imgViews.add(v);
		}
	}

	//this is how we call cardImages for a playerHand
	//it adds the imageviews for the hand to our 
	//player's hand's hbox named cards
	void playerCards() {
		cardImages(playerHand);
		for(int i = 0; i < imgViews.size(); i ++) {
			cards.getChildren().addAll(imgViews.get(i));
		}
		cards.setAlignment(Pos.BOTTOM_LEFT);
		imgViews.clear();
		handImgs.clear();
	}

	//this is how we call cardImages for a bankerHand
	//it adds the imageviews for the hand to our 
	//banker's hand's hbox named cards2
	void bankerCards() {
		cardImages(bankerHand);
		for(int i = 0; i < imgViews.size(); i ++) {
			cards2.getChildren().addAll(imgViews.get(i));
		}
		cards2.setAlignment(Pos.BOTTOM_RIGHT);
		imgViews.clear();
		handImgs.clear();
	}
}
