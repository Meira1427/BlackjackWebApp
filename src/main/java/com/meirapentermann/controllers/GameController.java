package com.meirapentermann.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.meirapentermann.blackjack.BlackjackGame;
import com.meirapentermann.blackjack.Hand;

@Controller
@SessionAttributes("game")
public class GameController {
	@Autowired
	private BlackjackGame game;
	
	@RequestMapping(path="newgame.do", method=RequestMethod.GET)
	public ModelAndView loadGame() {
		ModelAndView mv = new ModelAndView();
		BlackjackGame game1 = new BlackjackGame();
		game1.tearDown();
		game1.initialSetUp();
		mv.addObject("dealer_card", game1.getDealer().getPartialHand());
		mv.addObject("player_hand", game1.getPlayer().getHandList());
		mv.addObject("player_score", game1.getPlayer().handValue());
		mv.addObject("game", game1);
		if (game1.eitherPlayerNaturalBlackJack()) {
			mv = setUpForEndGame(game1.getPlayer().getHandHand(), "", game1);
		}
		else if (game1.canHandBeSplit()) {
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
	public ModelAndView noSplitNormalGame(@ModelAttribute("game") BlackjackGame game1) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("dealer_card", game1.getDealer().getPartialHand());
		mv.addObject("player_hand", game1.getPlayer().getHandList());
		mv.addObject("player_score", game1.getPlayer().handValue());
		mv.addObject("game", game1);
		mv.setViewName("game");
		return mv;
	}
	
	@RequestMapping(path="hit.do", 
			method=RequestMethod.GET)
	public ModelAndView playerHit(@ModelAttribute("game") BlackjackGame game1) {
		ModelAndView mv = new ModelAndView();
		game1.expandPlayerHand();
		if(game1.isPlayerOverTwentyOne() || game1.getPlayer().handValue()==21) {
			mv = setUpForEndGame(game1.getPlayer().getHandHand(), "", game1);
		}
		else {
			mv.addObject("player_hand", game1.getPlayer().getHandList());
			mv.addObject("dealer_card", game1.getDealer().getPartialHand());
			mv.addObject("player_score", game1.getPlayer().handValue());
			mv.addObject("game", game1);
			mv.setViewName("game");
		}
		return mv;
	}
	
	
	@RequestMapping(path="stay.do", 
			method=RequestMethod.GET)
	public ModelAndView playerStay(@ModelAttribute("game") BlackjackGame game1) {
		ModelAndView mv = new ModelAndView();
		game1.expandDealerHand();
		mv = setUpForEndGame(game1.getPlayer().getHandHand(), "", game1);
		return mv;
	}
	
	@RequestMapping(path="hit1.do", 
			method=RequestMethod.GET)
	public ModelAndView playerHitHand1(@ModelAttribute("game") BlackjackGame game1) {
		ModelAndView mv = new ModelAndView();
		game1.expandPlayerHand();
		if(game1.isPlayerOverTwentyOne() && game1.isHand1Stay() && game1.isHand2Stay()) {
			mv = setUpForEndGameSplitHand(game1);
		}
		else {
			mv = this.prepareDisplaySplitHand(game1);
		}
		return mv;
	}
	
	@RequestMapping(path="hit2.do", 
			method=RequestMethod.GET)
	public ModelAndView playerHitHand2(@ModelAttribute("game") BlackjackGame game1) {
		ModelAndView mv = new ModelAndView();
		game1.expandSplitHand();
		if(game1.isPlayerOverTwentyOne() && game1.isHand1Stay() && game1.isHand2Stay()) {
			game1.expandDealerHand();
			mv = setUpForEndGameSplitHand(game1);
		}
		else {
			mv = this.prepareDisplaySplitHand(game1);
		}
		return mv;
	}
	
	@RequestMapping(path="stay1.do", 
			method=RequestMethod.GET)
	public ModelAndView playerStayHand1(@ModelAttribute("game") BlackjackGame game1) {
		ModelAndView mv = new ModelAndView();
		mv = this.prepareDisplaySplitHand(game1);
		game1.setHand1Stay(true);
		mv.addObject("game", game1);
		if(game1.isHand1Stay() & game1.isHand2Stay()) {
			game1.expandDealerHand();
			mv = setUpForEndGameSplitHand(game1);
		}
		return mv;
	}
	
	@RequestMapping(path="stay2.do", 
			method=RequestMethod.GET)
	public ModelAndView playerStayHand2(@ModelAttribute("game") BlackjackGame game1) {
		ModelAndView mv = new ModelAndView();
		mv = this.prepareDisplaySplitHand(game1);
		game1.setHand2Stay(true);
		mv.addObject("game", game1);
		if(game1.isHand1Stay() & game1.isHand2Stay()) {
			game1.expandDealerHand();
			mv = setUpForEndGameSplitHand(game1);
		}
		return mv;
	}

	@RequestMapping(path="split.do", method=RequestMethod.GET)
	public ModelAndView gotoSplitGame(@ModelAttribute("game") BlackjackGame game1) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("splitgame");
		game1.reorganizePlayerHand();
		mv.addObject("hand1", game1.getPlayer().getHandList());
		mv.addObject("hand2", game1.getPlayer().getSplitList());
		mv.addObject("dealer_card", game1.getDealer().getPartialHand());
		mv.addObject("score1", game1.getPlayer().handValue());
		mv.addObject("score2", game1.getPlayer().splitHandValue());
		mv.addObject("game", game1);
		return mv;
	}
	
	
	protected ModelAndView setUpForEndGame(Hand h, String d, @ModelAttribute("game") BlackjackGame game1) {
		ModelAndView mv = new ModelAndView();
		String[] winStrings = game1.calculateWin(h, d);
		int win = Integer.parseInt(winStrings[0]);
		String message = winStrings[1];
		mv.addObject("player_score", game1.getPlayer().handValue());
		mv.addObject("dealer_score", game1.getDealer().handValue());
		mv.addObject("player_hand", game1.getPlayer().getHandList());
		mv.addObject("dealer_hand", game1.getDealer().getHandList());
		mv.addObject("winInt", win);
		mv.addObject("message", message);
		mv.addObject("game", game1);
		if(game1.isDealerBust()) {
			mv.addObject("dealer_busted", true);
		}
		if(game1.isPlayerBust()) {
			mv.addObject("player_busted", true);
		}
		mv.setViewName("endgame");
		return mv;
	}
	
	protected ModelAndView setUpForEndGameSplitHand(@ModelAttribute("game") BlackjackGame game1) {
		ModelAndView mv = new ModelAndView();
		String[] winStrings1 = game1.calculateWin(game1.getPlayer().getHandHand(), " first hand");
		String[] winStrings2 = game1.calculateWin(game1.getPlayer().getSplitHand(), " second hand");
		int win1 = Integer.parseInt(winStrings1[0]);
		int win2 = Integer.parseInt(winStrings2[0]);
		String message1 = winStrings1[1];
		String message2 = winStrings2[1];
		mv.addObject("score1", game1.getPlayer().handValue());
		mv.addObject("score2", game1.getPlayer().splitHandValue());
		mv.addObject("dealer_score", game1.getDealer().handValue());
		mv.addObject("hand1", game1.getPlayer().getHandList());
		mv.addObject("hand2", game1.getPlayer().getSplitList());
		mv.addObject("dealer_hand", game1.getDealer().getHandList());
		mv.addObject("winInt1", win1);
		mv.addObject("winInt2", win2);
		mv.addObject("message1", message1);
		mv.addObject("message2", message2);
		mv.addObject("game", game1);
		if(game1.isDealerBust()) {
			mv.addObject("dealer_busted", true);
		}
		if(game1.isPlayerBust()) {
			mv.addObject("hand1_busted", true);
		}
		if(game1.isSplitBust()) {
			mv.addObject("hand2_busted", true);
		}
		mv.setViewName("endgamesplit");
		return mv;
	}
	
	protected ModelAndView prepareDisplaySplitHand(@ModelAttribute("game") BlackjackGame game1) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("hand1", game1.getPlayer().getHandList());
		mv.addObject("dealer_card", game1.getDealer().getPartialHand());
		mv.addObject("score1", game1.getPlayer().handValue());
		mv.addObject("hand2", game1.getPlayer().getSplitList());
		mv.addObject("score2", game1.getPlayer().splitHandValue());
		mv.addObject("game", game1);
		mv.setViewName("splitgame");
		if(game1.isHand1Stay()) {
			mv.addObject("h1stay", true);
		}
		else {
			mv.addObject("h1stay", false);
		}
		if(game1.isHand2Stay()) {
			mv.addObject("h2stay", true);
		}
		else {
			mv.addObject("h2stay", false);
		}
		return mv;
	}
}
