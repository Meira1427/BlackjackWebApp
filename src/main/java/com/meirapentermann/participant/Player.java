package com.meirapentermann.participant;

import java.util.List;

import com.meirapentermann.blackjack.Hand;
import com.meirapentermann.cards.Card;

public class Player extends Participant {
	
	private double wallet;
	
	public Player(String name) {
		Hand hand = new Hand();
		Hand aSplit = new Hand();
		this.setHand(hand); //initialize a hand
		this.setSplit(aSplit);
		this.setName(name);
		this.wallet = 200;
	}
	
	public void addMoneyToWallet (double amount) {
		this.wallet+= amount;
	}
	
	public void removeMoneyFromWallet (double amount) {
		this.wallet-= amount;
	}

}
