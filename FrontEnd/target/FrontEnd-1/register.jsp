<%-- 
    Document   : register
    Created on : Feb 14, 2025, 10:34:46 a.m.
    Author     : mustansir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <style>
        body {
          background-color: #ffffff;
        }
    </style>

    <center><h2>Register a new account</h2></center>
    
    <form action="Register" method="post">

        <table border="2" align="center" cellpadding="5" cellspacing="5">
            <tr>
                <td> Enter email :</td>
                <td> <input type="email" name="email" size="30"> </td>
            </tr>
            <tr>
                <td> Enter username :</td>
                <td> <input type="text" name="username" size="30"> </td>
            </tr>
            <tr>
                <td> Enter password :</td>
                <td> <input type="password" name="password1" size="30"> </td>
            </tr>
            <tr>
                <td> Confirm your password :</td>
                <td> <input type="password" name="password2" size="30"> </td>
            </tr>
            <tr>

                <td></td>
                <td>
                    <input type="submit" value="register" >
                </td>
            </tr>
        </table>
    </form>
    <a href="index.html"><button type="button">Go Back</button></a>
    </body>
</html>