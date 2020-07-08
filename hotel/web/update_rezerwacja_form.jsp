<%--
  Created by IntelliJ IDEA.
  User: macmini2
  Date: 09/04/2020
  Time: 11:54
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*" %>
<html>
<head>
    <title>Zmiana danych telefonu</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="apple-touch-icon" href="apple-touch-icon.png">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="css/main.css">
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">

            <div class="style padding: 25 px">
                <a class="navbar-brand" href="index.html">IB-GSM</a>
            </div>

        </div>
    </div>
</nav>

<div class="row form-group"></div>
<div class="row form-group"></div>
<div class="row form-group"></div>

<div class="jumbotron">
    <div class="container">
        <h1>Zmień dane telefonu</h1>

        <form action="AdminServlet" method="get">
            <input type="hidden" name="command" value="UPDATE"/>
            <input type="hidden" name="rezerwacjaID" value="${REZERWACJA.id}"/>
            <div class="form-group">
                <label for="name">Imie</label>
                <input type="text" class="form-control" name="nameInput" value="${REZERWACJA.getKlient().getImie()}"/>
            </div>
            <div class="form-group">
                <label for="surname">Nazwisko</label>
                <input type="text" class="form-control" name="surnameInput" value="${REZERWACJA.getKlient().getNazwisko()}"/>
            </div>
            <div class="form-group">
                <label for="cena">Cena [zł]</label>
                <input type="text" class="form-control" value="${REZERWACJA.getCena()}"/>
            </div>
            <div class="form-group">
                <label for="typ">Typ pokoju</label>
                <input type="text" class="form-control" value="${REZERWACJA.getPokoj().getTyp()}"/>
            </div>
            <div class="form-group">
                <label for="dateFrom">Data od</label>
                <input type="date" class="form-control" value="${REZERWACJA.getDateFrom()}"/>
            </div>
            <div class="form-group">
                <label for="dateTo">Data od</label>
                <input type="date" class="form-control" value="${REZERWACJA.getDateTo()}"/>
            </div>
            <button type="submit" class="btn btn-success">Zmień dane</button>
        </form>
    </div>
</div>

<div class="row form-group"></div>
<div class="row form-group"></div>

<div class="row">
    <div class="container-fluid">

        <div class="col-sm-9">
            <a href="AdminServlet" class="btn btn-lg btn-primary" role="button" aria-disabled="true">Wróć do zestawienia</a>
        </div>
    </div>
</div>

</body>
</html>
