package com.meirapentermann.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.meirapentermann.blackjack.BlackjackGame;
import com.meirapentermann.blackjack.Hand;

@Controller
public class GameController {
	@Autowired
	private BlackjackGame game;
	
	@RequestMapping(path="newgame.do", method=RequestMethod.GET)
	public ModelAndView loadGame() {
		ModelAndView mv = new ModelAndView();
		game.tearDown();
		game.initialSetUp();
		mv.addObject("dealer_card", game.getDealer().getPartialHand());
		mv.addObject("player_hand", game.getPlayer().getHandList());
		mv.addObject("player_score", game.getPlayer().handValue());
		if (game.eitherPlayerNaturalBlackJack()) {
			mv = setUpForEndGame(game.getPlayer().getHandHand(), "");
		}
		else if (game.canHandBeSplit()) {
			mv.addObject("canSplit", true);
			mv.setViewName("game");
		}
		else {	
			mv.addObject("canSplit", false);
			mv.setViewName("game");
		}
		return mv;
	}
	
	@RequestMapping(path="game.do", method=RequestMethod.GET)
	public ModelAndView noSplitNormalGame() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("dealer_card", game.getDealer().getPartialHand());
		mv.addObject("player_hand", game.getPlayer().getHandList());
		mv.addObject("player_score", game.getPlayer().handValue());
		mv.setViewName("game");
		return mv;
	}
	
	@RequestMapping(path="hit.do", 
			method=RequestMethod.GET)
	public ModelAndView playerHit() {
		ModelAndView mv = new ModelAndView();
		game.expandPlayerHand();
		if(game.isPlayerOverTwentyOne() || game.getPlayer().handValue()==21) {
			mv = setUpForEndGame(game.getPlayer().getHandHand(), "");
		}
		else {
			mv.addObject("player_hand", game.getPlayer().getHandList());
			mv.addObject("dealer_card", game.getDealer().getPartialHand());
			mv.addObject("player_score", game.getPlayer().handValue());
			mv.setViewName("game");
		}
		return mv;
	}
	
	
	@RequestMapping(path="stay.do", 
			method=RequestMethod.GET)
	public ModelAndView playerStay() {
		ModelAndView mv = new ModelAndView();
		game.expandDealerHand();
		mv = setUpForEndGame(game.getPlayer().getHandHand(), "");
		return mv;
	}
	
	@RequestMapping(path="hit1.do", 
			method=RequestMethod.GET)
	public ModelAndView playerHitHand1() {
		ModelAndView mv = new ModelAndView();
		game.expandPlayerHand();
		if(game.isPlayerOverTwentyOne() && game.isHand1Stay() && game.isHand2Stay()) {
			mv = setUpForEndGameSplitHand();
		}
		else {
			mv = this.prepareDisplaySplitHand();
		}
		return mv;
	}
	
	@RequestMapping(path="hit2.do", 
			method=RequestMethod.GET)
	public ModelAndView playerHitHand2() {
		ModelAndView mv = new ModelAndView();
		game.expandSplitHand();
		if(game.isPlayerOverTwentyOne() && game.isHand1Stay() && game.isHand2Stay()) {
			game.expandDealerHand();
			mv = setUpForEndGameSplitHand();
		}
		else {
			mv = this.prepareDisplaySplitHand();
		}
		return mv;
	}
	
	@RequestMapping(path="stay1.do", 
			method=RequestMethod.GET)
	public ModelAndView playerStayHand1() {
		ModelAndView mv = new ModelAndView();
		mv = this.prepareDisplaySplitHand();
		game.setHand1Stay(true);
		if(game.isHand1Stay() & game.isHand2Stay()) {
			game.expandDealerHand();
			mv = setUpForEndGameSplitHand();
		}
		return mv;
	}
	
	@RequestMapping(path="stay2.do", 
			method=RequestMethod.GET)
	public ModelAndView playerStayHand2() {
		ModelAndView mv = new ModelAndView();
		mv = this.prepareDisplaySplitHand();
		game.setHand2Stay(true);
		if(game.isHand1Stay() & game.isHand2Stay()) {
			game.expandDealerHand();
			mv = setUpForEndGameSplitHand();
		}
		return mv;
	}

	@RequestMapping(path="split.do", method=RequestMethod.GET)
	public ModelAndView gotoSplitGame() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("splitgame");
		game.reorganizePlayerHand();
		mv.addObject("hand1", game.getPlayer().getHandList());
		mv.addObject("hand2", game.getPlayer().getSplitList());
		mv.addObject("dealer_card", game.getDealer().getPartialHand());
		mv.addObject("score1", game.getPlayer().handValue());
		mv.addObject("score2", game.getPlayer().splitHandValue());
		return mv;
	}
	
	
	protected ModelAndView setUpForEndGame(Hand h, String d) {
		ModelAndView mv = new ModelAndView();
		String[] winStrings = game.calculateWin(h, d);
		int win = Integer.parseInt(winStrings[0]);
		String message = winStrings[1];
		mv.addObject("player_score", game.getPlayer().handValue());
		mv.addObject("dealer_score", game.getDealer().handValue());
		mv.addObject("player_hand", game.getPlayer().getHandList());
		mv.addObject("dealer_hand", game.getDealer().getHandList());
		mv.addObject("winInt", win);
		mv.addObject("message", message);
		if(game.isDealerBust()) {
			mv.addObject("dealer_busted", true);
		}
		if(game.isPlayerBust()) {
			mv.addObject("player_busted", true);
		}
		mv.setViewName("endgame");
		return mv;
	}
	
	protected ModelAndView setUpForEndGameSplitHand() {
		ModelAndView mv = new ModelAndView();
		String[] winStrings1 = game.calculateWin(game.getPlayer().getHandHand(), " first hand");
		String[] winStrings2 = game.calculateWin(game.getPlayer().getSplitHand(), " second hand");
		int win1 = Integer.parseInt(winStrings1[0]);
		int win2 = Integer.parseInt(winStrings2[0]);
		String message1 = winStrings1[1];
		String message2 = winStrings2[1];
		mv.addObject("score1", game.getPlayer().handValue());
		mv.addObject("score2", game.getPlayer().splitHandValue());
		mv.addObject("dealer_score", game.getDealer().handValue());
		mv.addObject("hand1", game.getPlayer().getHandList());
		mv.addObject("hand2", game.getPlayer().getSplitList());
		mv.addObject("dealer_hand", game.getDealer().getHandList());
		mv.addObject("winInt1", win1);
		mv.addObject("winInt2", win2);
		mv.addObject("message1", message1);
		mv.addObject("message2", message2);
		if(game.isDealerBust()) {
			mv.addObject("dealer_busted", true);
		}
		if(game.isPlayerBust()) {
			mv.addObject("hand1_busted", true);
		}
		if(game.isSplitBust()) {
			mv.addObject("hand2_busted", true);
		}
		mv.setViewName("endgamesplit");
		return mv;
	}
	
	protected ModelAndView prepareDisplaySplitHand() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("hand1", game.getPlayer().getHandList());
		mv.addObject("dealer_card", game.getDealer().getPartialHand());
		mv.addObject("score1", game.getPlayer().handValue());
		mv.addObject("hand2", game.getPlayer().getSplitList());
		mv.addObject("score2", game.getPlayer().splitHandValue());
		mv.setViewName("splitgame");
		if(game.isHand1Stay()) {
			mv.addObject("h1stay", true);
		}
		else {
			mv.addObject("h1stay", false);
		}
		if(game.isHand2Stay()) {
			mv.addObject("h2stay", true);
		}
		else {
			mv.addObject("h2stay", false);
		}
		return mv;
	}
}
