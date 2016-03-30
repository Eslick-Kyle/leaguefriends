<%-- 
    Document   : SignUp
    Created on : Mar 28, 2016, 10:16:58 PM
    Author     : Greg
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Sign Up</h1>
        <span>${Error}</span>
        <form method="POST" action="SignUpHandler">
            Username: <input type="text" name="username"><br>
            Password: <input type="password" name="password"><br>
            Summoner Name: <input type="text" name="summonerName"><br>
            <input type="submit" name="submit" value="Submit">
        </form>
    </body>
</html>
