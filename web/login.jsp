<%-- 
    Document   : login
    Created on : Jan 10, 2021, 1:50:06 PM
    Author     : quocl
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <style><%@include file="/css/login.css"%></style>
        <script src="https://apis.google.com/js/platform.js" async defer></script>
        <meta name="google-signin-client_id" content="1024742275610-pa6fll3edb1j2bcs2b3ekluvmb4155se.apps.googleusercontent.com">

        <title>Login Page</title>
    </head>
    <body id="LoginForm">
        <div class="container">
            <div class="login-form">
                <div class="main-div">
                    <div class="panel">
                        <h1>Login</h1>
                        <a href="MainController">Search Items without Login</a>
                    </div> <br/>
                    <form action="MainController" id="Login" method="POST">
                        <div class="form-group">
                            <input type="text" class="form-control" id="inputText" placeholder="Username" name="txtUsername">
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control" id="inputPassword" placeholder="Password" name="txtPassword">
                        </div>
                        <input type="submit" class="btn btn-primary" value="Login" name="btnAction" />
                        <div class="panel">
                            <h5>Or</h5>
                            <!--<input type="submit" class="btn btn-danger" value="Login with Google" name="btnAction" />-->
                            <!--<button class="btn btn-danger">-->
                  
                                <a href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8084/P0013/LoginGoogleSevlet&response_type=code&client_id=43512159107-tjn0souj0983j26g3n4sss06v75stebi.apps.googleusercontent.com&approval_prompt=force"/>
                                Login with Google
                            <!--</button>-->
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
