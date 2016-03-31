<%-- 
    Document   : Welcome
    Created on : Mar 24, 2016, 10:40:07 AM
    Author     : Shane
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <style>
        footer 
        {
            font-size: 10px;
            position:absolute;
            bottom:0;
            background:#ffffff;
        }
        body
        {
            background-size: 100%;
            background: url("welcome.png");
            color: white;
        }
        #background
        {
            width: 100%; 
            height: 100%; 
            position: fixed; 
            left: 0px; 
            top: 0px; 
            z-index: -1; /* Ensure div tag stays behind content */
        }
        .stretch
        {
            width: 100%;
            height: 100%;
        }

    </style>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome</title>
    </head>
    <body>
        <div id="background">
            <img src="welcome.png" class="stretch" alt="welcome" />
        </div>
        
        
        <h2>Follow a Summoner</h2>
        <form method="POST" action="addSummoner">
            <span>Summoner Name: </span> <input type="text" name="summonerName">
            <input type="submit" name="submit" value="Submit">
        </form>
        
        
        ${summoner.id}
        ${summoner.name}
        ${summoner.summonerLevel}
        
        <!--  This series of loops will display user's friends and whatever game attributes you chose  -->
        <c:forEach items="${user}" var="user" >
            <c:forEach items="${user.getFriends()}" var="friend">
                <c:out value="${friend.getSummoner().getName()}" />
                    <c:forEach items="${friend.getSummoner().getGames()}" var="game">
                <!--  Choose what attributes you want to display per friend here-->
                           <br>
                            <c:out value="${game.getChampionsKilled()}"/>
                     </c:forEach>
            </c:forEach>
        </c:forEach>
        
        
        <footer>
            <p>League of Friends isn't endorsed by Riot Games and doesn't reflect the views or opinions of Riot Games or anyone officially involved in producing or managing League of Legends. League of Legends and Riot Games are trademarks of Riot Games, Inc. League of Legends © Riot Games, Inc.</p>
        </footer>
    </body>
</html>
