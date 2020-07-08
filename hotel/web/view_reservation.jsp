
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage = "error.jsp" %>
<html lang="en" dir="ltr">
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title> Potwierdzenie </title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <style type="text/css">
        .content {

            margin: auto;
            width: 1200px;
            padding: 10px;
            position: center;
            background: rgb(0, 0, 0); /* Fallback color */
            background: plum; /* Black background with 0.5 opacity */
            color:white;


        }


    </style>
</head><!doctype html>


<body>
<center><hr>
<h1>Twoja rezerwacja: </h1>
<hr>
<div class="content">
<c:forEach var="reservation" items="${REZERWACJA_LIST}">

    ${reservation.getKlient().getImie()} <br>
    ${reservation.getKlient().getNazwisko()} <br>
    ${reservation.getKlient().getEmail()} <br>
    ${reservation.getPokoj().getTyp()} <br>
    ${reservation.getCena()} <br>

</c:forEach>

    <h2>Dziękujemy!</h2>

    <a href="home.html" class="btn btn-lg btn-primary" role="button" aria-disabled="true">Wróć do strony głównej</a>
</div></center>


</body>
</html>
