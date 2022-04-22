<%--
  Created by IntelliJ IDEA.
  User: biank
  Date: 1/2/2022
  Time: 12:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@300&family=Oooh+Baby&family=Imperial+Script&family=Roboto+Condensed&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <title>Log Out</title>

    <style>
        .navbar-custom{
            background-color: rgba(73, 39, 74, .2);
            position: absolute;
            z-index: 20;
            width: 100%;

        }
        .navbar-brand-custom{
            font-size: 2rem;
            color:#f8eee7;
            text-decoration: none;
            font-family: 'Oooh Baby', cursive;

        }
        .navbar-brand-custom:hover{
            color:#f8eee7;
        }
        .nav-link{
            margin-right: 10px;
            margin-left: 10px;
            color: #f8eee7;
            text-transform: uppercase;
            font-family: 'Roboto Condensed', sans-serif;
        }

        .nav-link:hover{
            color:#f8eee7;
        }


        .search{
            height: 40px;
            width: 200px;
            border-radius: 60px;
            border: 2px solid #49274A;
            margin-right: -35px;
            font-family: 'Roboto Condensed', sans-serif;
        }



        .btn0{
            height: 40px;
            width: 100px;
            outline: none;
            border: none;
            background: #49274A;
            color: #f8eee7;
            border-radius: 60px;
            font-width: 700;
            font-family: 'Roboto Condensed', sans-serif;
        }

        #bg img {
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            margin: auto;
            min-width: 50%;
            min-height: 50%;
        }

        h1{
            font-weight: 70;
            text-align: center;
            color: #f8eee7;
            font-family: 'Vujahday Script', cursive;
            font-size: 50px;
        }


        h2{
            font-weight: 70;
            text-align: center;

            font-family: 'Roboto Condensed';
        }
        .btn-order{

            width: 40%;

            padding: 10px;
            text-align: center;
            background-color:#94618E;
            color: #f8eee7;
            font-weight :70;
            border-radius :60px;
            font-family: 'Roboto Condensed';
        }

        .cointainer {
            display: flex;
            flex-direction: column;
            position: absolute;
            top: 40vh;
            bottom: 5vh;
            left: 10vh;
            z-index: 20;
            place-items: center;

        }
        a {
            color: blue;
            transition: 0.3s;
        }

        .product {


            position: relative;
            width: 85%;
            height: 100%;
            margin: 0 auto;

        }
        .best-offers {
            width: 100%;
            color: black;
        }
        .best-offers-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            position: relative;
            width: 100%;
            height: 6rem;
            font-size: 1.8rem;
        }


        h5{
            display: inline-block;
            vertical-align:baseline;
            text-decoration: line-through;
            font-size: 15px;
        }

        .map {
            width: 100%;
            height: auto;
        }


        .map-container{
            overflow:hidden;
            padding-bottom:56.25%;
            position:relative;
            height:0;
        }
        .map-container iframe{
            left:0;
            top:0;
            height:70%;
            width:100%;
            position:absolute;
        }


        b {
            color: black;
            text-decoration: none;
            transition: 0.3s;
        }
        @media (min-width: 1025px) {
            .h-custom {
                height: 100vh !important;
            }
        }

        .card-registration .select-input.form-control[readonly]:not([disabled]) {
            font-size: 1rem;
            line-height: 2.15;
            padding-left: .75em;
            padding-right: .75em;
        }

        .card-registration .select-arrow {
            top: 13px;
        }

        .bg-grey {
            background-color: #eae8e8;
        }

        @media (min-width: 992px) {
            .card-registration-2 .bg-grey {
                border-top-right-radius: 16px;
                border-bottom-right-radius: 16px;
            }
            .h-custom {
                height: 100vh !important;
            }
        }

        input.right {
            float: right;
        }

    </style>
</head>
<body>

<nav class="navbar navbar-custom navbar-expand-lg">
    <div class="container">
        <a class="navbar-brand-custom" href="/">California Flowers</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav m-auto mb-2 mb-lg-0">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <button style="background: none; border: 0px; color: #f8eee7;" onclick="show()" id="btnID">PRODUSE</button>

                    </a>


                    <c:choose>
                        <c:when test ="${cont == 'admin'}">
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="catalog-admin?nume=#Crăciun">Crăciun</a></li>
                                <li><a class="dropdown-item" href="catalog-admin?nume=#Aranjamente Nuntă">Aranjamente Nuntă</a></li>
                                <li><a class="dropdown-item" href="catalog-admin?nume=#Buchete">Buchete</a></li>
                                <li><a class="dropdown-item" href="catalog-admin?nume=#Criogenați">Criogenați</a></li>
                                <li><a class="dropdown-item" href="catalog-admin?nume=#Cutii cu flori">Cutii flori</a></li>
                                <li><a class="dropdown-item" href="catalog-admin?nume#Funerar">Funerar</a></li>
                            </ul>
                        </c:when>
                        <c:otherwise>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="catalog?nume=#Crăciun">Crăciun</a></li>
                                <li><a class="dropdown-item" href="catalog?nume=#Aranjamente Nuntă">Aranjamente Nuntă</a></li>
                                <li><a class="dropdown-item" href="catalog?nume=#Buchete">Buchete</a></li>
                                <li><a class="dropdown-item" href="catalog?nume=#Criogenați">Criogenați</a></li>
                                <li><a class="dropdown-item" href="catalog?nume=#Cutii cu flori">Cutii flori</a></li>
                                <li><a class="dropdown-item" href="catalog?nume#Funerar">Funerar</a></li>
                            </ul>
                        </c:otherwise>
                    </c:choose>


                </li>
                <li class="nav-item">
                    <a class="nav-link" href="wish">
                        <img src="https://testsite8797.files.wordpress.com/2021/12/fav-1.png?w=128" alt="" width="30" height="24" class="d-inline-block align-text-top">
                        Favorite
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="cart">
                        <img src="https://testsite8797.files.wordpress.com/2021/12/cart-1.png?w=128" alt="" width="30" height="24" class="d-inline-block align-text-top">
                        Coș
                    </a>
                </li>
                <li class="nav-item">

                            <a href="logout" style="text-decoration: none">
                                <button class="nav-link" style="background: transparent; border:0px;">
                                    <img src="https://testsite8797.files.wordpress.com/2021/12/acc-1.png?w=127" alt="" width="30" height="24" class="d-inline-block align-text-top">
                                    Cont ${cont}</button>
                            </a>

                </li>
            </ul>
            <form class="d-flex">
                <input class="px-2 search" type="search" placeholder="Caută produsul dorit" aria-label="Search">
                <button class="btn0" type="submit">Căutare</button>
            </form>
        </div>
    </div>
</nav>


<section class="h-100 h-custom" style="background-color: #d2c9ff;">
    <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-8">
                <div class="card card-registration card-registration-2" style="border-radius: 15px;">
                    <div class="card-body p-0">
                        <div class="row g-0">
                            <div class="col-lg-7">
                                <div class="p-5">
                                    <div class="d-flex justify-content-between align-items-center mb-5" >
                                        <h1 class="fw-bold mb-0 text-black" >Ieșire din cont? </h1>


                                    </div>

                                    <hr class="my-4">
<form:form action="logout" method="POST">
    <input type="submit" style="background: #49274A; color:white" value="Da">
</form:form>

                                </div>

                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

</body>
</html>
