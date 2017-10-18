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
     <!--   <a href="#" class="colorButton"><input type="submit" value="Choice 3"></input></a>
       <a href="#" class="colorButton"><input type="submit" value="Choice 2"></input></a> -->
       <a href="newgame.do" class="colorButton"><input type="submit" value="New Game"></input></a>
       </div>
   </div>
   <div id="hidden_header">
       <h1 class="myTitle">Blackjack</h1>
       <div id="navbar">
       <!-- <a href="#" class="colorButton"><input type="submit" value="Choice 3"></input></a>
       <a href="#" class="colorButton"><input type="submit" value="Choice 2"></input></a> -->
       <a href="#" class="colorButton"><input type="submit" value="Choice 1"></input></a>
       </div>
   </div>
   <div class="main">
   <h3>Dealer Hand</h3>
	   <c:forEach var="card" items="${dealer_hand}">
	   		<h5>${card}</h5>
	   </c:forEach>
   <br>
   <h3>&#9824; &#9829; &#9827; &#9830; &#9824; &#9829; &#9827; &#9830;</h3>	
   <br>
   <h3>Player Hand 1</h3>
	   <c:forEach var="card" items="${hand1}">
	   		<h5>${card}</h5>
	   </c:forEach>
   <br>
   <h5>&#9824; &#9829; &#9827; &#9830; &#9824; &#9829; &#9827; &#9830;</h5>	
   <br>
     <h3>Player Hand 2</h3>
	   <c:forEach var="card" items="${hand2}">
	   		<h5>${card}</h5>
	   </c:forEach>
   <br>
   <c:choose>
      <c:when test="${dealer_busted != null}">
            <h5>Dealer Score: Busted</h5>
      </c:when>
      <c:otherwise>
   			<h5>Dealer Score: ${dealer_score}</h5>
      </c:otherwise>
   </c:choose>
    <c:choose>
      <c:when test="${hand1_busted != null}">
            <h5>Player Hand 1: Busted</h5>
      </c:when>
      <c:otherwise>
   			<h5>Player Hand 1: ${score1}</h5>
      </c:otherwise>
   </c:choose>
   <h5>${message1}</h5>
     <c:choose>
      <c:when test="${hand2_busted != null}">
            <h5>Player Hand 2: Busted</h5>
      </c:when>
      <c:otherwise>
   			<h5>Player Hand 2: ${score2}</h5>
      </c:otherwise>
   </c:choose>
   <h5>${message2}</h5>
   </div>
   </body>
   </html>