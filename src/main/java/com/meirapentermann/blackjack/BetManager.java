package com.meirapentermann.blackjack;

import com.meirapentermann.participant.Player;

public class BetManager {
	private double betsOnTable;
	private double blackJackPayout;
	private int numPlayers;
	
	public BetManager() {
		this.betsOnTable = 0;
		this.numPlayers = 1;
		this.blackJackPayout = 1.5;
	}
	
	public void payPlayer(Player p, int score) {
		double perBet = this.betsOnTable/this.numPlayers;
		double giveOut;
		if(score==21) {
			giveOut = perBet*this.blackJackPayout;		
		}
		else {
			giveOut = perBet;
		}
		p.addMoneyToWallet(perBet);
	}
	
	public void collectFromPlayer(Player p, int score) {
		double perBet = this.betsOnTable/this.numPlayers;
		p.removeMoneyFromWallet(perBet);
	}

	public double getBetsOnTable() {
		return betsOnTable;
	}

	public void setBetsOnTable(double betsOnTable) {
		this.betsOnTable = betsOnTable;
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

	public double getBlackJackPayout() {
		return blackJackPayout;
	}

	public void setBlackJackPayout(double blackJackPayout) {
		this.blackJackPayout = blackJackPayout;
	}

}
