<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style>
        body {
            background-color: #ffffff;
        }
    </style>
</head>
<body>

    <center><h2>Login to see your account</h2></center>

    <form action="Login" method="post">  <!-- Calls the Login servlet -->
        <table border="2" align="center" cellpadding="5" cellspacing="5">
            <tr>
                <td>Enter username :</td>
                <td><input type="text" name="username" size="30" required></td>
            </tr>
            <tr>
                <td>Enter password :</td>
                <td><input type="password" name="password" size="30" required></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="Login"></td>
            </tr>
        </table>
    </form>

    <center>
        <a href="index.html"><button type="button">Go Back</button></a>
    </center>

</body>
</html>
