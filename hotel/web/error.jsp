<%@ page isErrorPage = "true" %>

<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title> Error. </title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <style type="text/css">
        #div1 {
            font-size:48px;
        }

        body{
            padding-top: 30px;
            padding-bottom: 50px;
        }
        .alert {
            padding: 20px;
            background-color: #f44336;
            color: white;
            opacity: 1;
            transition: opacity 0.6s;
            margin-bottom: 15px;
        }
    </style>
</head><!doctype html>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<body>


<script>
    function smile() {
        var a;
        a = document.getElementById("div1");
        a.innerHTML = "&#xf118;";
        setTimeout(function () {
            a.innerHTML = "&#xf11a;";
        }, 1000);
        setTimeout(function () {
            a.innerHTML = "&#xf119;";
        }, 2000);
        setTimeout(function () {
            a.innerHTML = "&#xf11a;";
        }, 3000);
    }
    setInterval(smile, 4000);
</script>

<div class="alert">
    <center><strong>Error!</strong> Wprowadzono niepoprawne dane.</center>
</div>


<h1 class="text-center">
    Upsss...<div id="div1" class="fa"></div>
</h1>

<p class="text-center">
    Przepraszamy, wystapil blad. Sprobuj wypelnic <a href="reservation-form.html"><strong>formularz</strong></a> jeszcze raz.
<%--</p>--%>
<%--<p class="text-center">Powyzej znajdziesz informacje o bledach.<i class="arrow up"></i> </p>--%>
<%--<pre><% exception.printStackTrace(response.getWriter()); %></pre>--%>



</body>
</html>