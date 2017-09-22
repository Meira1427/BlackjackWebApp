package com.meirapentermann.blackjack;

import com.meirapentermann.participant.*;
import com.meirapentermann.cards.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class BlackjackGame {
	private Player player;
	private Dealer dealer;
	private Deck deck;
	private boolean gameOver;
	private boolean playerBust;
	private boolean splitBust;
	private boolean dealerBust;
	private boolean split;
	private boolean playAgain;
	private boolean hand1Stay;
	private boolean hand2Stay;
	
	/*
	 * Constructor requires a keyboard to set u the UserInput class
	 * Then everything is initialized.
	 */
	public BlackjackGame () {
		player = new Player("Player"); 	// initialize player
		dealer = new Dealer();			// initialize dealer
		deck = new Deck();	//create new deck
		deck.shuffleDeck();	//shuffle deck
//		gameOver = false;	//booleans initialized to false
		playerBust = false;	//booleans initialized to false
		splitBust = false;	//booleans initialized to false
		dealerBust = false;	//booleans initialized to false
		split = false;		//booleans initialized to false
		playAgain = true;	//to enter while loop
		hand1Stay = false;	//booleans initialized to false
		hand2Stay = false;	//booleans initialized to false
//		handHit = false;		//initialize hit or stay variables; will be automatic if split
//		splitHit = false;	//only deal with this one if isSplit
	}
	
	
	/*
	 * initial set-up deals 2 cards each, starting with player
	 * then it displays hands, full hand for player, half hand for dealer
	 */
	public void initialSetUp() {
		Card c1, c2;
		for(int i = 0; i< 2; i++) { // 2 cards each; player & dealer
			c1 = deck.dealCard();
			player.updateHand(c1);
			c2 = deck.dealCard();
			dealer.updateHand(c2);
		}
	}
	
	/*
	 * to watch for instant win
	 */
	public boolean eitherPlayerNaturalBlackJack() {
		if(dealer.handValue()==21 || player.handValue()==21) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/*
	 * to watch for possible split
	 */
	public boolean canHandBeSplit() {
		if(!(dealer.handValue()==21)) {
			Rank r0 = player.getHandList().get(0).getRank();
			Rank r1 = player.getHandList().get(1).getRank();
			String toSplit = "";
			if(   (r0 == Rank.KING || r0 == Rank.QUEEN || r0 == Rank.JACK || r0 == Rank.TEN) 
			   && (r1 == Rank.KING || r1 == Rank.QUEEN || r1 == Rank.JACK || r1 == Rank.TEN)){
				return true;
			}
			else if(r0 == r1) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	public void reorganizePlayerHand () {
		this.setSplit(true);
		Hand h1 = new Hand();
		Hand h2 = new Hand();
		h1.addCard(player.getHandList().get(0));
		h2.addCard(player.getHandList().get(1));
		player.setHand(h1);
		player.setSplit(h2);
	}
	
	public boolean isPlayerOverTwentyOne() {
		if (player.handValue() > 21) {
			if(player.getHandHand().acesInHandCount() > 0) {
				player.getHandHand().dealWithAces();
			}
		}
		if(player.handValue()>21) {
			this.setPlayerBust(true);
			return true;
		}
		return false;
	}
	
//	public boolean isSplitOverTwentyOne() {
//		if (player.handValue() > 21) {
//			if(player.getHandHand().acesInHandCount() > 0) {
//				player.getHandHand().dealWithAces();
//			}
//		}
//		if(player.handValue()>21) {
//			this.setPlayerBust(true);
//			return true;
//		}
//		return false;
//	}
	
	public void expandPlayerHand() {
		player.updateHand(deck.dealCard());
		if((player.handValue() > 21) && player.getHandHand().acesInHandCount()>0) {
			player.getHandHand().dealWithAces();
		}
		if(player.handValue() > 21) {
			this.setPlayerBust(true);
		}
	}
	
	public void expandSplitHand() {
		player.updateHandSplit(deck.dealCard());
		if((player.splitHandValue() > 21) && player.getSplitHand().acesInHandCount()>0) {
			player.getSplitHand().dealWithAces();
		}
		if(player.splitHandValue() > 21) {
			this.setSplitBust(true);
		}
	}
	
	public void expandDealerHand() {
		int total = dealer.handValue();
		while(total < 17) {
			dealer.updateHand(deck.dealCard());
			total = dealer.handValue();
		}
		if( (dealer.handValue()> 21) && (dealer.getHandHand().acesInHandCount() > 0)) {
			dealer.getHandHand().dealWithAces();
		}
		if(dealer.handValue() < 17) {
			expandDealerHand();       //recursive call back to itself, until dealer hand over 17
		}
		if(dealer.handValue() > 21) {
			this.dealerBust = true;
		}
	}
	
	/*
	 * Starts with most unlikely case of a natural BlackJack for both dealer & player
	 * Moves on in order of elimination of conditions of cards
	 * returns a String array, first String is for win calculation, 
	 * second String is message
	 */
	public String[] calculateWin(Hand H, String description) {
		String[] answer = new String[2];
		if( (H.handValue() == 21 && H.getHandList().size()==2) 
		 && (dealer.handValue() == 21 && dealer.getHandList().size()==2)) {
			answer[0] = "0";
			answer[1] = "Natural BlackJack! Both Player" + description + " & Dealer. It's a Tie!";
		}
		else if (H.handValue() == 21 && H.getHandList().size()==2){
			answer[0] = "1";
			answer[1] = "Natural BlackJack" + description + "! Player wins!";
		}
		else if (dealer.handValue() == 21 && dealer.getHandList().size()==2) {
			answer[0] = "-1";
			answer[1] = "Natural BlackJack! Dealer wins" + description + "!";
		}
		else if (H.handValue() == 21 && dealer.handValue() == 21) {
			answer[0] = "0";
			answer[1] = "BlackJack! Both Player" + description + " & Dealer. It's a Tie!";
		}
		else if (isPlayerBust() && H==getPlayer().getHandHand()) {
			answer[0] = "-1";
			answer[1] = "Player busted" + description + ". Dealer wins!";
		}
		else if (isSplit() && isSplitBust() && H==getPlayer().getSplitHand()) {
			answer[0] = "-1";
			answer[1] = "Player busted" + description + ". Dealer wins!";
		}
		else if (isDealerBust()) {
			answer[0] = "1";
			answer[1] = "Dealer busted. Player wins" + description + "!";
		}
		else if (H.handValue() == dealer.handValue()) {
			answer[0] = "0";
			answer[1] = "It's a Tie" + description + "!";
		}
		else if (H.handValue() > dealer.handValue()) {
			answer[0] = "1";
			answer[1] = "Player wins" + description + "!";
		}
		else {
			answer[0] = "-1";
			answer[1] = "Dealer wins" + description + ".";
		}
		return answer;
	}
		
	public void tearDown()	{
		this.deck = new Deck();
		this.deck.shuffleDeck();
		Hand hand1 = new Hand();
		Hand hand2 = new Hand();
		Hand hand3 = new Hand();
		this.getPlayer().setHand(hand1);
		this.getDealer().setHand(hand2);
		this.getPlayer().setSplit(hand3);
//		gameOver = false;
		playerBust = false;
		dealerBust = false;
		splitBust = false;
		playAgain = true;
		hand1Stay = false;	
		hand2Stay = false;	
	}

	/*
	 * Getters & Setters
	 */

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public boolean isPlayerBust() {
		return playerBust;
	}

	public void setPlayerBust(boolean playerBust) {
		this.playerBust = playerBust;
	}
	
	public boolean isSplitBust() {
		return splitBust;
	}

	public void setSplitBust(boolean splitBust) {
		this.splitBust = splitBust;
	}

	public boolean isDealerBust() {
		return dealerBust;
	}

	public void setDealerBust(boolean dealerBust) {
		this.dealerBust = dealerBust;
	}

	public boolean isSplit() {
		return split;
	}

	public void setSplit(boolean split) {
		this.split = split;
	}


	public boolean isPlayAgain() {
		return playAgain;
	}


	public void setPlayAgain(boolean playAgain) {
		this.playAgain = playAgain;
	}


	public boolean isHand1Stay() {
		return hand1Stay;
	}


	public void setHand1Stay(boolean hand1Stay) {
		this.hand1Stay = hand1Stay;
	}


	public boolean isHand2Stay() {
		return hand2Stay;
	}


	public void setHand2Stay(boolean hand2Stay) {
		this.hand2Stay = hand2Stay;
	}
}
