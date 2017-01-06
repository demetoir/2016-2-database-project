<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>

    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR" />
        <link href="style.css" rel="stylesheet" type="text/css">
        <link href=”bootstrap/css/bootstrap.min.css” rel=”stylesheet” type=”text/css” />
		<script type=”text/javascript” src=”bootstrap/js/bootstrap.min.js”></script>
        <title>*** welcome to YDC ***</title>
    </head>

    <body>

        <div class="loginPage">
            <h1> Yonsei Delivery Company </h1>
        </div>

        <div class="loginPage">
            <h1>login Page</h1>
        </div>
        <div>
            <form action="checkLogin.jsp" method="post">
                <div class="loginPage"> ID </div>
                <div class="loginPage"> <input type="text" name="id"></div>
                <div class="loginPage"> password </div>
                <div class="loginPage"> <input type="password" name="pwd"></div>
                <br>
                <div class="loginPage"> <input type="submit" value="login"></div>
            </form>
        </div>
        <br>
        <div class="loginPage">
            <form action="makeNewAccount.jsp" method="post">
                <input type="submit" value="makeNewAccount"><br>
            </form>
        </div>
    </body>

    </html>