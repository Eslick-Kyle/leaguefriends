<%-- 
    Document   : SignIn
    Created on : Mar 1, 2016, 2:07:39 PM
    Author     : Shane
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <style>
        form
        {
            width: 300px;
            margin: auto;
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
        #title
        {
            text-align: center;
        }
    </style>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign In Page</title>
    </head>
    <body>
        <div id="background">
            <img src="signin.jpeg" alt="signin" class="stretch" />
        </div>
        <br /><br /><br /><br /><br />
        <br /><br /><br />
        <h1 id="title">League of Friends</h1>
        <br /><br />
        <form action="SignInHandler" method="post">
            <input type="text" name="username" placeholder="Username"><br />
            <input type="password" name="password" placeholder="Password"><br />
            <input type="submit"> <br />
            <a href="SignUp.jsp" value="Sign up here!">Sign Up Here!</a>
        </form>
    </body>
</html>
