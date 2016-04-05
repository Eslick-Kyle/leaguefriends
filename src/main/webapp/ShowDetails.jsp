<%-- 
    Document   : ShowDetails
    Created on : Apr 5, 2016, 9:21:03 AM
    Author     : Shane
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Show Details</title>
    </head>
    <body>
        <h1>${summoner.getName()}</h1>
        <table>
            <tr>
            <td>Kills</td>
            <td>Deaths</td>
            <td>Assists</td>
            </tr>
            <c:forEach items="${summoner.getGames()}" var="game" >
                <c:set var="total" value="${game.getChampionsKilled()}" />
                Kills: <c:out value="${total}" />
                <c:set var="total" value="${game.getNumDeaths()}" />
                Deaths: <c:out value="${total}" />
                <c:set var="total" value="${game.getAssists()}" />
                Assists: <c:out value="${total}" />
                <tr>
                    <td>${game.getChampionsKilled()}</td>
                    <td>${game.getNumDeaths()}</td>
                    <td>${game.getAssists()}</td>
                </tr>
            </c:foreach>
        </table>
    </body>
</html>
