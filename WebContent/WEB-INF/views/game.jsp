<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/stylesheet.css">
<title>Blackjack</title>
</head>
<body>
   <div class="header">
       <h1 class="myTitle">Blackjack</h1>
      <div id="navbar">
       <a href="#" class="colorButton"><input type="submit" value="Choice 3"></input></a>
       <a href="#" class="colorButton"><input type="submit" value="Choice 2"></input></a>
       <a href="newgame.do" class="colorButton"><input type="submit" value="New Game"></input></a>
       </div>
   </div>
   <div id="hidden_header">
       <h1 class="myTitle">Blackjack</h1>
       <div id="navbar">
       <a href="#" class="colorButton"><input type="submit" value="Choice 3"></input></a>
       <a href="#" class="colorButton"><input type="submit" value="Choice 2"></input></a>
       <a href="#" class="colorButton"><input type="submit" value="Choice 1"></input></a>
       </div>
   </div>
   <div class="main">
   	   <!-- <h2>&#9824; &#9829; &#9827; &#9830; &#9824; &#9829; &#9827; &#9830;</h2> -->
	   <h3>Dealer Hand</h3>
	   		<h5>[ &#9828; &#9825; &#9831; &#9826; &#9828; &#9825; &#9831; &#9826;]</h5>
	   		<h5>${dealer_card}</h5>
	   <br>
	   <h3>&#9824; &#9829; &#9827; &#9830; &#9824; &#9829; &#9827; &#9830;</h3>	
	   <br>
	   <h3>Player Hand</h3>
	   <c:forEach var="card" items="${player_hand}">
	   		<h5>${card}</h5>
	   </c:forEach>
	   <h5>Current Value: ${player_score}</h5>
	   <c:choose>
	      	<c:when test="${canSplit}">
	            <h5>Would you Like to Split?</h5>
	            <a href="split.do"><input type="submit" name="yes" value="Yes"></input></a>
				<a href="game.do"><input type="submit" name="no" value="No"></input></a>
	      	</c:when>
	      	<c:otherwise>
	   			<a href="hit.do"><input type="submit" name="hit" value="Hit"></input></a>
				<a href="stay.do"><input type="submit" name="stay" value="Stay"></input></a>
	      	</c:otherwise>
   		</c:choose>
   </div>
   </body>
   </html>