<%-- 
    Document   : Welcome
    Created on : Mar 24, 2016, 10:40:07 AM
    Author     : Shane
--%>

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
        h1{color: white;}
    </style>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome</title>
    </head>
    <body>
        <div id="background">
            <img src="welcome.png" class="stretch" alt="welcome" />
        </div>
        
        <h1>Welcome ${Username}</h1>
        <footer>
            <p>League of Friends isn't endorsed by Riot Games and doesn't reflect the views or opinions of Riot Games or anyone officially involved in producing or managing League of Legends. League of Legends and Riot Games are trademarks of Riot Games, Inc. League of Legends © Riot Games, Inc.</p>
        </footer>
    </body>
</html>
