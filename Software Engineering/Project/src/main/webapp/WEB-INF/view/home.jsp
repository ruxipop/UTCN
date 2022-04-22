
<%--
  Created by IntelliJ IDEA.
  User: biank
  Date: 11/15/2021
  Time: 7:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@page isELIgnored="false" %>

<!DOCTYPE html>
<html>

<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@300&family=Oooh+Baby&family=Imperial+Script&family=Roboto+Condensed&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="./resources/styles/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">



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


        .main{
            /*background-size: cover;*/
            height: 100vh;
            background-position: 50% 50%;
            width: 100%;

        }

        h1{
            font-weight: 70;
            text-align: center;
            color: #f8eee7;
            font-family: 'Imperial Script', cursive;
            font-size: 5rem;
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
            color: white;
            text-decoration: none;
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

        .fa-shopping-cart{
            color: rgba(145, 40, 103,0.4);
        }

        .fa-heart-o{
            color: rgba(145, 40, 103,0.4);

        }

    </style>
</head>

<body style="background-color: #f8eee7">

<%--<div id="bg">--%>
<%--    <img id="imgA1" src="../resources/pexels-bich-tran-945453.jpg" class="img-fluid" alt="logo">--%>
<%--    <img id="imgA2" src="./resources/img/Over.png">--%>
<%--</div>--%>

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
                    <c:choose>
                        <c:when test ="${cont == 'anonymousUser'}">
                            <a href="mylogin" style="text-decoration: none"><button class="nav-link" style="background: transparent; border:0px;">
                                <img src="https://testsite8797.files.wordpress.com/2021/12/acc-1.png?w=127" alt="" width="30" height="24" class="d-inline-block align-text-top">
                                Cont</button></a>
                        </c:when>
                        <c:otherwise>
                            <a href="logout" style="text-decoration: none">
                                <button class="nav-link" style="background: transparent; border:0px;">
                                    <img src="https://testsite8797.files.wordpress.com/2021/12/acc-1.png?w=127" alt="" width="30" height="24" class="d-inline-block align-text-top">
                                    Cont ${cont}</button>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </li>
            </ul>
            <%--@elvariable id="flower" type="com.bianca.model.Flower"--%>
            <form:form action="process-search" modelAttribute="flower" class="d-flex" method="POST">
                <form:input path="nume" class="px-2 search" type="search" placeholder="Caută produsul dorit" aria-label="Search"></form:input>
                <input class="btn0" type="submit" value="Submit">
            </form:form>
        </div>
    </div>
</nav>
<div id="carouselExampleFade" class="carousel slide carousel-fade" data-bs-ride="carousel">


    <div class="carousel-indicators">

        <button type="button" data-bs-target="#carouselExampleFade" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
        <button type="button" data-bs-target="#carouselExampleFade" data-bs-slide-to="1" aria-label="Slide 2"></button>
        <button type="button" data-bs-target="#carouselExampleFade" data-bs-slide-to="2" aria-label="Slide 3"></button>
    </div>
    <div class="carousel-inner">

        <div class="carousel-item active">
            <img src="https://testsite8797.files.wordpress.com/2021/12/flower3.jpg?w=2000" class="main" alt="why">
        </div>
        <div class="carousel-item">
            <img src="https://testsite8797.files.wordpress.com/2021/12/flower2-1.jpg?w=2000" class="main" alt="...">
        </div>
        <div class="carousel-item">
            <img src="https://testsite8797.files.wordpress.com/2021/12/flower9.jpg?w=2000" class="main" alt="...">
        </div>
        <div class =" cointainer">
            <h1>Flowers don't tell.</h1>
            <h1>    They show</h1>
            <div class ="btn-order">
                <c:choose>
                    <c:when test ="${cont == 'admin'}">
                        <a href="catalog-admin?nume=">COMANDĂ ACUM</a>
                    </c:when>
                    <c:otherwise>
                        <a href="catalog?nume=">COMANDĂ ACUM</a>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>
    </div>

</div>


<%--    <section>--%>
<%--    <img src="./resources/img/flower1.jpg" class="main" alt="...">--%>

<%--</section >--%>


<section class="product">
    <div class="container py-5">
        <div class="row py-5 ">
            <div class="col-lg-5 m-auto text-center">
                <h2>COMANDĂ FLORI DIN OFERTELE CURENTE</h2>

            </div>
        </div>

        <div class="row">
            <c:forEach var="flower" items="${fs}">
                <c:if test="${flower.ePromotie==true}">

                    <div class="col-lg-3 text-center">

                        <div class="card border-0 bg-light mb-2">
                            <div class="card-body">
                                <button  style="color:black; background:none; border: none; font-weight:bold;" data-bs-toggle="modal" data-bs-target="#exampleModal${flower.id}">
                                    <tr>

                                        <td>  <img src="${flower.img}" class="img-fluid" alt=""></td>

                                </button>
                            </div>
                        </div>


                            <td>${flower.nume}
                            <c:choose>
                                <c:when test ="${cont == 'anonymousUser'}">
                                    <button  onclick="alert('Trebuie să ai cont ca să poți adăuga la favorite!')"><i class="heart fa fa-heart-o" ></i></button></td>
                                </c:when>
                                <c:otherwise>
                                    <form:form action="process-wish?id=${flower.id}&page=home" method="post">
                                    <button  onclick="alert('Produsul a fost adăugat în wish!')"><i class="heart fa fa-heart-o" ></i></button></td>
                                    </form:form>
                                </c:otherwise>
                            </c:choose>





                        <form:form action="process-cart?id=${flower.id}&page=home" method="post">
                        <td>      <h5>${flower.pret} lei</h5><a>&nbsp </a><a>${flower.pretNou} lei</a></td> <button onclick="alert('Produsul a fost adăugat în cos!')"><i class="fa fa-shopping-cart" ></i></button>
                        </form:form>

                    </div>

                    <div class="modal fade" id="exampleModal${flower.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                                <div class="modal-header">

                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <td><h4>${flower.descriere}</h4></td>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Închide</button>
                                </div>
                            </div>
                        </div>
                    </div>

                </c:if>
            </c:forEach>
        </div>

    </div>
</section>

<h2>UNDE NE GĂSIȚI</h2>

<br></br>
<!--Google map-->
<div id="map-container-google-1" class="z-depth-1-half map-container" style="height: 500px">
    <iframe src="https://maps.google.com/maps?q=manhatan&t=&z=13&ie=UTF8&iwloc=&output=embed" frameborder="0"
            style="border:0" allowfullscreen></iframe>
</div>

<!--Google Maps-->

<!-- Footer -->
<footer class="page-footer font-small indigo">



    <%--    <!-- Footer Links -->--%>
    <div class="container">

        <%--        <!-- Grid row-->--%>
        <div class="row text-center d-flex justify-content-center pt-5 mb-3">

            <%--            <!-- Grid column -->--%>
            <div class="col-md-2 mb-3">
                <h6 class="text-uppercase font-weight-bold">
                    <button type="button" style="color:black; background:none; border: none; font-weight:bold;" data-bs-toggle="modal" data-bs-target="#exampleModal">
                        DESPRE NOI
                    </button>
                </h6>
            </div>
            <%--            <!-- Grid column -->--%>


            <!-- Vertically centered scrollable modal -->



            <div class="col-md-2 mb-3">
                <h6 class="text-uppercase font-weight-bold">
                    <button type="button" style="color:black; background:none; border: none; font-weight:bold;" data-bs-toggle="modal" data-bs-target="#exampleModal2">
                        HELP
                    </button>
                </h6>
            </div>
            <%--            <!-- Grid column -->--%>

            <%--            <!-- Grid column -->--%>
            <div class="col-md-2 mb-3">
                <h6 class="text-uppercase font-weight-bold">
                    <button type="button" style="color:black; background:none; border: none; font-weight:bold;" data-bs-toggle="modal" data-bs-target="#exampleModal3">
                        CONTACT
                    </button>
                </h6>
            </div>
            <%--            <!-- Grid column -->--%>

        </div>
        <!-- Grid row-->
        <hr class="rgba-white-light" style="margin: 0 15%;">

        <!-- Grid row-->
        <div class="row d-flex text-center justify-content-center mb-md-0 mb-4">


        </div>


    </div>

    <div class="footer-copyright text-center py-3"> 2021, 30236, Pop Ruxandra și Zelenszky Bianca

    </div>
</footer>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">

                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>Acest website a fost creat cu HTML+CSS cu ajutorul framework-ului Bootstrap 5.
                    Proiect realizat de Pop Ruxandra și Zelenszky Bianca, grupa 30236.</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Închide</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="exampleModal2" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">

                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>1. Q: Cum se plasează o comandă?</p>
                <p>R: </p>
                <br>
                <p>2. Q: Cum se adaugă un produs în coș?</p>
                <p>R: </p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Închide</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="exampleModal3" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">

                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>Ne puteți contacta la nr. de telefon: 042119526.</p>
                <p>Sau ne puteți trimite mesaj în căsuța de mai jos:</p>
                <form>
                    <div class="mb-3">
                        <label for="recipient-name" class="col-form-label">Adresa dvs. de e-mail:</label>
                        <input type="text" class="form-control" id="recipient-name">
                    </div>
                    <div class="mb-3">
                        <label for="message-text" class="col-form-label">Mesaj:</label>
                        <textarea class="form-control" id="message-text"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Închide</button>
                <button type="button" class="btn btn-primary">Trimite</button>
            </div>
        </div>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

</body>
</html>
