<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: biank
  Date: 12/19/2021
  Time: 9:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@page isELIgnored="false" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@300&family=Oooh+Baby&family=Imperial+Script&family=Roboto+Condensed&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <style>

        .navbar-custom{
            background-color: rgba(145, 40, 103,0.4);
            position: absolute;
            z-index: 20;
            width: 100%;

        }
        .fa-shopping-cart{
            color: rgba(145, 40, 103,0.4);
        }

        .fa-heart-o{
            color: rgba(145, 40, 103,0.4);

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

        #bg {
            position: fixed;
            top: -50%;
            left: -50%;
            width: 200%;
            height: 200%;
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

        #imgA1{
            z-index: 1;
        }

        #imgA2{
            z-index: 3;
            opacity: 0.0;
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
        .col-lg-1{

            width:20%;
        }
        .row{
            display:flex ;
            justify-content:space-between;
        }
        h2{
            font-weight: 70;
            text-align: center;

            font-family: 'Flow Rounded';
        }
    </style>
</head>



<body style="background-color: #f8eee7">

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


                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#Crăciun">Crăciun</a></li>
                        <li><a class="dropdown-item" href="#Aranjamente Nuntă">Aranjamente Nuntă</a></li>
                        <li><a class="dropdown-item" href="#Buchete">Buchete</a></li>
                        <li><a class="dropdown-item" href="#Criogenați">Criogenați</a></li>
                        <li><a class="dropdown-item" href="#Cutii flori">Cutii flori</a></li>
                        <li><a class="dropdown-item" href="#Funerar">Funerar</a></li>
                    </ul>

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
                    <a href="logout" style="text-decoration: none"><button class="nav-link" style="background: transparent; border:0px;">
                        <img src="https://testsite8797.files.wordpress.com/2021/12/acc-1.png?w=127" alt="" width="30" height="24" class="d-inline-block align-text-top">
                        Cont ADMIN</button></a>
                </li>
            </ul>
            <%--@elvariable id="flower" type="com.bianca.model.Flower"--%>
            <form:form action="process-search" modelAttribute="flower" class="d-flex" method="POST">
                <form:input path="nume" class="px-2 search" type="search" placeholder="Caută produsul dorit" aria-label="Search"></form:input>
                <button class="btn0" type="submit">Căutare</button>
            </form:form>
        </div>
    </div>
</nav>



<section class="product">
    <div class="container py-5">
        <div class="row py-5 ">
            <div class=" text-center">
                <h2 id="Crăciun" > Crăciun <button style="border: none;background: transparent;"  data-bs-toggle="modal" data-bs-target="#insertModalCraciun">+</button></h2>
            </div>
        </div>



        <div class="row">
            <c:forEach var="flower" items="${flowers}">
                <c:if test="${flower.categorie=='Craciun'}">
                    <div class="col-lg-1 text-center">

                        <div class="card border-0 bg-light mb-2">
                            <div class="card-body" >
                                <button  style="color:black; background:none; border: none; font-weight:bold;" data-bs-toggle="modal" data-bs-target="#exampleModal${flower.id}">
                                    <tr>


                                        <td>  <img src="${flower.img}" class="img-fluid" alt=""></td>

                                </button>

                            </div>
                        </div>

                        <form:form action="process-wish?id=${flower.id}&page=catalog" method="post">
                            <td>${flower.nume} <button  onclick="alert('Produsul a fost adăugat în wish!')"><i class="heart fa fa-heart-o" ></i></button></td>

                        </form:form>
                        <form:form action="process-cart?id=${flower.id}&page=catalog" method="post">
                            <td>
                                <c:choose>
                                    <c:when test ="${flower.ePromotie==false}">
                                        ${flower.pret} RON <button onclick="alert('Produsul a fost adăugat în cos!')"><i class="fa fa-shopping-cart" ></i></button>

                                    </c:when>
                                    <c:otherwise>
                                        <strike>${flower.pret} RON</strike> ${flower.pretNou} RON <button onclick="alert('Produsul a fost adăugat în cos!')"><i class="fa fa-shopping-cart" ></i></button>

                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </form:form>
                        <td><button style="border: none;background: transparent;"  data-bs-toggle="modal" data-bs-target="#updateModal${flower.id}">Update</button></td>
                        <td><button style="border: none; background: transparent;"  data-bs-toggle="modal" data-bs-target="#deleteModal${flower.id}">Delete</button></td>
                        <div class="modal fade" id="updateModal${flower.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">

                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                            <%--@elvariable id="flower" type="com.bianca.model.Flower"--%>
                                        <form:form action="updateFlower?nume=&id=${flower.id}" modelAttribute="flower" method="post">
                                            <div class="mb-3">
                                                <label for="nume" class="col-form-label" path="nume">Nume:</label>
                                                <form:input type="text" class="form-control" id="nume" path="nume" value="${flower.nume}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <label for="pret" class="col-form-label" path="pret">Pret:</label>
                                                <form:input type="text" class="form-control" id="pret" path="pret" value="${flower.pret}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <label for="descriere" class="col-form-label" path="descriere">Descriere:</label>
                                                <form:input type="text" class="form-control" id="descriere" path="descriere" rows="3" value="${flower.descriere}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <label for="categorie" class="col-form-label" path="categorie">Categorie:</label>
                                                <form:select class="form-select" id="categorie" path="categorie">
                                                    <option value="0"></option>
                                                    <option value="1">Crăciun</option>
                                                    <option value="2">Aranjamente Nuntă</option>
                                                    <option value="3">Buchete</option>
                                                    <option value="4">Criogenați</option>
                                                    <option value="5">Cutii flori</option>
                                                    <option value="6">Funerar</option>
                                                </form:select>
                                            </div>

                                            <div class="mb-3">
                                                <label for="data" class="col-form-label" path="data">Data:</label>
                                                <form:input type="text" class="form-control" id="data" path="data" value="${flower.data}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <label for="img" class="col-form-label" path="img">Img URL:</label>
                                                <form:input type="text" class="form-control" id="img" path="img" value="${flower.img}"></form:input>
                                            </div>

                                            <div class="mb-3">
                                                <c:choose>
                                                    <c:when test ="${flower.ePromotie == false}">
                                                        <form:checkbox path="ePromotie" value=""/> Promoție?
                                                    </c:when>
                                                    <c:otherwise>
                                                        <form:checkbox path="ePromotie" value="" checked="checked"/> Promoție?
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="mb-3">
                                                <label for="pretNou" class="col-form-label" path="pretNou">Preț nou:</label>

                                                <form:input type="text" class="form-control" id="pretNou" path="pretNou" value="${flower.pretNou}"></form:input>
                                            </div>
                                            <input type="submit" value="Submit"/>
                                        </form:form>



                                    </div>

                                </div>
                            </div>
                        </div>

                        <div class="modal fade" id="deleteModal${flower.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">

                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form:form action="deleteFlower?nume=${flower.nume}&id=${flower.id}" modelAttribute="flower" method="POST">
                                            <div class="mb-3">
                                                <label class="col-form-label">Ești sigur că vrei să ștergi produsul?</label>
                                            </div>
                                            <input type="submit" value="Da"/>

                                        </form:form>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="modal fade" id="exampleModal${flower.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">

                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <td><h5>${flower.descriere}</h5></td>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Închide</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        </tr>


                    </div>

                </c:if>
            </c:forEach>
        </div>
    </div>
</section>


<section class="product">
    <div class="container py-5">
        <div class="row py-5 ">
            <div class=" text-left">
                <h2 id="Aranjamente Nuntă" > Aranjamente Nuntă <button style="border: none;background: transparent;"  data-bs-toggle="modal" data-bs-target="#insertModalCraciun">+</button></h2>


            </div>
        </div>



        <div class="row">
            <c:forEach var="flower" items="${flowers}">
                <c:if test="${flower.categorie=='Aranjamente Nunta'}">
                    <div class="col-lg-1 text-center">

                        <div class="card border-0 bg-light mb-2">
                            <div class="card-body" >
                                <button  style="color:black; background:none; border: none; font-weight:bold;" data-bs-toggle="modal" data-bs-target="#exampleModal${flower.id}">
                                    <tr>

                                        <td>  <img src="${flower.img}" class="img-fluid" alt=""></td>

                                </button>

                            </div>
                        </div>

                        <form:form action="process-wish?id=${flower.id}&page=catalog" method="post">
                            <td>${flower.nume} <button  onclick="alert('Produsul a fost adăugat în wish!')"><i class="heart fa fa-heart-o" ></i></button></td>

                        </form:form>
                        <form:form action="process-cart?id=${flower.id}&page=catalog" method="post">
                            <td>
                                <c:choose>
                                    <c:when test ="${flower.ePromotie==false}">
                                        ${flower.pret} RON <button onclick="alert('Produsul a fost adăugat în cos!')"><i class="fa fa-shopping-cart" ></i></button>

                                    </c:when>
                                    <c:otherwise>
                                        <strike>${flower.pret} RON</strike> ${flower.pretNou} RON <button onclick="alert('Produsul a fost adăugat în cos!')"><i class="fa fa-shopping-cart" ></i></button>

                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </form:form>
                        <td><button style="border: none;background: transparent;"  data-bs-toggle="modal" data-bs-target="#updateModal${flower.id}">Update</button></td>
                        <td><button style="border: none; background: transparent;"  data-bs-toggle="modal" data-bs-target="#deleteModal${flower.id}">Delete</button></td>
                        <div class="modal fade" id="updateModal${flower.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">

                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                            <%--@elvariable id="flower" type="com.bianca.model.Flower"--%>
                                        <form:form action="updateFlower?nume=&id=${flower.id}" modelAttribute="flower" method="post">
                                            <div class="mb-3">
                                                <label for="nume" class="col-form-label" path="nume">Nume:</label>
                                                <form:input type="text" class="form-control" id="nume" path="nume" value="${flower.nume}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <label for="pret" class="col-form-label" path="pret">Pret:</label>
                                                <form:input type="text" class="form-control" id="pret" path="pret" value="${flower.pret}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <label for="descriere" class="col-form-label" path="descriere">Descriere:</label>
                                                <form:input type="text" class="form-control" id="descriere" path="descriere" rows="3" value="${flower.descriere}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <label for="categorie" class="col-form-label" path="categorie">Categorie:</label>
                                                <form:select class="form-select" id="categorie" path="categorie">
                                                    <option value="0"></option>
                                                    <option value="1">Crăciun</option>
                                                    <option value="2">Aranjamente Nuntă</option>
                                                    <option value="3">Buchete</option>
                                                    <option value="4">Criogenați</option>
                                                    <option value="5">Cutii flori</option>
                                                    <option value="6">Funerar</option>
                                                </form:select>
                                            </div>

                                            <div class="mb-3">
                                                <label for="data" class="col-form-label" path="data">Data:</label>
                                                <form:input type="text" class="form-control" id="data" path="data" value="${flower.data}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <label for="img" class="col-form-label" path="img">Img URL:</label>
                                                <form:input type="text" class="form-control" id="img" path="img" value="${flower.img}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <c:choose>
                                                    <c:when test ="${flower.ePromotie == false}">
                                                        <form:checkbox path="ePromotie" value=""/> Promoție?
                                                    </c:when>
                                                    <c:otherwise>
                                                        <form:checkbox path="ePromotie" value="" checked="checked"/> Promoție?
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="mb-3">
                                                <label for="pretNou" class="col-form-label" path="pretNou">Preț nou:</label>

                                                <form:input type="text" class="form-control" id="pretNou" path="pretNou" value="${flower.pretNou}"></form:input>
                                            </div>
                                            <input type="submit" value="Submit"/>
                                        </form:form>



                                    </div>

                                </div>
                            </div>
                        </div>

                        <div class="modal fade" id="deleteModal${flower.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">

                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form:form action="deleteFlower?nume=${flower.nume}&id=${flower.id}" method="POST">
                                            <div class="mb-3">
                                                <label class="col-form-label">Ești sigur că vrei să ștergi produsul?</label>
                                            </div>
                                            <input type="submit" value="Da"/>

                                        </form:form>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="modal fade" id="exampleModal${flower.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">

                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <td><h5>${flower.descriere}</h5></td>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Închide</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        </tr>


                    </div>

                </c:if>
            </c:forEach>
        </div>
    </div>
</section>

<section class="product">
    <div class="container py-5">
        <div class="row py-5 ">
            <div class=" text-left">
                <h2 id="Buchete" >Buchete <button style="border: none;background: transparent;"  data-bs-toggle="modal" data-bs-target="#insertModalCraciun">+</button></h2>

            </div>
        </div>



        <div class="row">
            <c:forEach var="flower" items="${flowers}">
                <c:if test="${flower.categorie=='Buchete'}">
                    <div class="col-lg-1 text-center">

                        <div class="card border-0 bg-light mb-2">
                            <div class="card-body" >
                                <button  style="color:black; background:none; border: none; font-weight:bold;" data-bs-toggle="modal" data-bs-target="#exampleModal${flower.id}">
                                    <tr>

                                        <td>  <img src="${flower.img}" class="img-fluid" alt=""></td>

                                </button>

                            </div>
                        </div>

                         <form:form action="process-wish?id=${flower.id}&page=catalog" method="post">
                        <td>${flower.nume} <button  onclick="alert('Produsul a fost adăugat în wish!')"><i class="heart fa fa-heart-o" ></i></button></td>

                    </form:form>
                        <form:form action="process-cart?id=${flower.id}&page=catalog" method="post">
                            <td>
                                <c:choose>
                                    <c:when test ="${flower.ePromotie==false}">
                                        ${flower.pret} RON <button onclick="alert('Produsul a fost adăugat în cos!')"><i class="fa fa-shopping-cart" ></i></button>

                                    </c:when>
                                    <c:otherwise>
                                        <strike>${flower.pret} RON</strike> ${flower.pretNou} RON <button onclick="alert('Produsul a fost adăugat în cos!')"><i class="fa fa-shopping-cart" ></i></button>

                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </form:form>
                        <td><button style="border: none;background: transparent;"  data-bs-toggle="modal" data-bs-target="#updateModal${flower.id}">Update</button></td>
                        <td><button style="border: none; background: transparent;"  data-bs-toggle="modal" data-bs-target="#deleteModal${flower.id}">Delete</button></td>
                        <div class="modal fade" id="updateModal${flower.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">

                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                            <%--@elvariable id="flower" type="com.bianca.model.Flower"--%>
                                        <form:form action="updateFlower?nume=&id=${flower.id}" modelAttribute="flower" method="post">
                                            <div class="mb-3">
                                                <label for="nume" class="col-form-label" path="nume">Nume:</label>
                                                <form:input type="text" class="form-control" id="nume" path="nume" value="${flower.nume}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <label for="pret" class="col-form-label" path="pret">Pret:</label>
                                                <form:input type="text" class="form-control" id="pret" path="pret" value="${flower.pret}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <label for="descriere" class="col-form-label" path="descriere">Descriere:</label>
                                                <form:input type="text" class="form-control" id="descriere" path="descriere" rows="3" value="${flower.descriere}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <label for="categorie" class="col-form-label" path="categorie">Categorie:</label>
                                                <form:select class="form-select" id="categorie" path="categorie">
                                                    <option value="0"></option>
                                                    <option value="1">Crăciun</option>
                                                    <option value="2">Aranjamente Nuntă</option>
                                                    <option value="3">Buchete</option>
                                                    <option value="4">Criogenați</option>
                                                    <option value="5">Cutii flori</option>
                                                    <option value="6">Funerar</option>
                                                </form:select>
                                            </div>

                                            <div class="mb-3">
                                                <label for="data" class="col-form-label" path="data">Data:</label>
                                                <form:input type="text" class="form-control" id="data" path="data" value="${flower.data}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <label for="img" class="col-form-label" path="img">Img URL:</label>
                                                <form:input type="text" class="form-control" id="img" path="img" value="${flower.img}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <c:choose>
                                                    <c:when test ="${flower.ePromotie == false}">
                                                        <form:checkbox path="ePromotie" value=""/> Promoție?
                                                    </c:when>
                                                    <c:otherwise>
                                                        <form:checkbox path="ePromotie" value="" checked="checked"/> Promoție?
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="mb-3">
                                                <label for="pretNou" class="col-form-label" path="pretNou">Preț nou:</label>

                                                <form:input type="text" class="form-control" id="pretNou" path="pretNou" value="${flower.pretNou}"></form:input>
                                            </div>
                                            <input type="submit" value="Submit"/>
                                        </form:form>



                                    </div>

                                </div>
                            </div>
                        </div>


                        <div class="modal fade" id="deleteModal${flower.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">

                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form:form action="deleteFlower?nume=${flower.nume}&id=${flower.id}" method="POST">
                                            <div class="mb-3">
                                                <label class="col-form-label">Ești sigur că vrei să ștergi produsul?</label>
                                            </div>
                                            <input type="submit" value="Da"/>

                                        </form:form>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="modal fade" id="exampleModal${flower.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">

                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <td><h5>${flower.descriere}</h5></td>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Închide</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        </tr>


                    </div>

                </c:if>
            </c:forEach>
        </div>
    </div>
</section>

<section class="product">
    <div class="container py-5">
        <div class="row py-5 ">
            <div class=" text-left">

                <h2 id="Criogenați" > Criogenați <button style="border: none;background: transparent;"  data-bs-toggle="modal" data-bs-target="#insertModalCraciun">+</button></h2>

            </div>
        </div>



        <div class="row">
            <c:forEach var="flower" items="${flowers}">
                <c:if test="${flower.categorie=='Criogenati'}">
                    <div class="col-lg-1 text-center">

                        <div class="card border-0 bg-light mb-2">
                            <div class="card-body" >
                                <button  style="color:black; background:none; border: none; font-weight:bold;" data-bs-toggle="modal" data-bs-target="#exampleModal${flower.id}">
                                    <tr>

                                        <td>  <img src="${flower.img}" class="img-fluid" alt=""></td>

                                </button>

                            </div>
                        </div>

                        <form:form action="process-wish?id=${flower.id}&page=catalog" method="post">
                            <td>${flower.nume} <button  onclick="alert('Produsul a fost adăugat în wish!')"><i class="heart fa fa-heart-o" ></i></button></td>

                        </form:form>
                        <form:form action="process-cart?id=${flower.id}&page=catalog" method="post">
                            <td>
                                <c:choose>
                                    <c:when test ="${flower.ePromotie==false}">
                                        ${flower.pret} RON <button onclick="alert('Produsul a fost adăugat în cos!')"><i class="fa fa-shopping-cart" ></i></button>

                                    </c:when>
                                    <c:otherwise>
                                        <strike>${flower.pret} RON</strike> ${flower.pretNou} RON <button onclick="alert('Produsul a fost adăugat în cos!')"><i class="fa fa-shopping-cart" ></i></button>

                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </form:form>

                        <td><button style="border: none;background: transparent;"  data-bs-toggle="modal" data-bs-target="#updateModal${flower.id}">Update</button></td>
                        <td><button style="border: none; background: transparent;"  data-bs-toggle="modal" data-bs-target="#deleteModal${flower.id}">Delete</button></td>
                        <div class="modal fade" id="updateModal${flower.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">

                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                            <%--@elvariable id="flower" type="com.bianca.model.Flower"--%>
                                        <form:form action="updateFlower?nume=&id=${flower.id}" modelAttribute="flower" method="post">
                                            <div class="mb-3">
                                                <label for="nume" class="col-form-label" path="nume">Nume:</label>
                                                <form:input type="text" class="form-control" id="nume" path="nume" value="${flower.nume}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <label for="pret" class="col-form-label" path="pret">Pret:</label>
                                                <form:input type="text" class="form-control" id="pret" path="pret" value="${flower.pret}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <label for="descriere" class="col-form-label" path="descriere">Descriere:</label>
                                                <form:input type="text" class="form-control" id="descriere" path="descriere" rows="3" value="${flower.descriere}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <label for="categorie" class="col-form-label" path="categorie">Categorie:</label>
                                                <form:select class="form-select" id="categorie" path="categorie">
                                                    <option value="0"></option>
                                                    <option value="1">Crăciun</option>
                                                    <option value="2">Aranjamente Nuntă</option>
                                                    <option value="3">Buchete</option>
                                                    <option value="4">Criogenați</option>
                                                    <option value="5">Cutii flori</option>
                                                    <option value="6">Funerar</option>
                                                </form:select>
                                            </div>

                                            <div class="mb-3">
                                                <label for="data" class="col-form-label" path="data">Data:</label>
                                                <form:input type="text" class="form-control" id="data" path="data" value="${flower.data}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <label for="img" class="col-form-label" path="img">Img URL:</label>
                                                <form:input type="text" class="form-control" id="img" path="img" value="${flower.img}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <c:choose>
                                                    <c:when test ="${flower.ePromotie == false}">
                                                        <form:checkbox path="ePromotie" value=""/> Promoție?
                                                    </c:when>
                                                    <c:otherwise>
                                                        <form:checkbox path="ePromotie" value="" checked="checked"/> Promoție?
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="mb-3">
                                                <label for="pretNou" class="col-form-label" path="pretNou">Preț nou:</label>

                                                <form:input type="text" class="form-control" id="pretNou" path="pretNou" value="${flower.pretNou}"></form:input>
                                            </div>
                                            <input type="submit" value="Submit"/>
                                        </form:form>



                                    </div>

                                </div>
                            </div>
                        </div>

                        <div class="modal fade" id="deleteModal${flower.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">

                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form:form action="deleteFlower?nume=${flower.nume}&id=${flower.id}" method="POST">
                                            <div class="mb-3">
                                                <label class="col-form-label">Ești sigur că vrei să ștergi produsul?</label>
                                            </div>
                                            <input type="submit" value="Da"/>

                                        </form:form>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="modal fade" id="exampleModal${flower.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">

                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <td><h5>${flower.descriere}</h5></td>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Închide</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        </tr>


                    </div>

                </c:if>
            </c:forEach>
        </div>
    </div>
</section>

<section class="product">
    <div class="container py-5">
        <div class="row py-5 ">
            <div class=" text-left">
                <h2 id="Cutii cu flori" > Cutii cu flori <button style="border: none;background: transparent;"  data-bs-toggle="modal" data-bs-target="#insertModalCraciun">+</button></h2>


            </div>
        </div>



        <div class="row">
            <c:forEach var="flower" items="${flowers}">
                <c:if test="${flower.categorie=='Cutii flori'}">
                    <div class="col-lg-1 text-center">

                        <div class="card border-0 bg-light mb-2">
                            <div class="card-body" >
                                <button  style="color:black; background:none; border: none; font-weight:bold;" data-bs-toggle="modal" data-bs-target="#exampleModal${flower.id}">
                                    <tr>

                                        <td>  <img src="${flower.img}" class="img-fluid" alt=""></td>

                                </button>
                            </div>
                        </div>

                        <form:form action="process-wish?id=${flower.id}&page=catalog" method="post">
                            <td>${flower.nume} <button  onclick="alert('Produsul a fost adăugat în wish!')"><i class="heart fa fa-heart-o" ></i></button></td>

                        </form:form>
                        <form:form action="process-cart?id=${flower.id}&page=catalog" method="post">
                            <td>
                                <c:choose>
                                    <c:when test ="${flower.ePromotie==false}">
                                        ${flower.pret} RON <button onclick="alert('Produsul a fost adăugat în cos!')"><i class="fa fa-shopping-cart" ></i></button>

                                    </c:when>
                                    <c:otherwise>
                                        <strike>${flower.pret} RON</strike> ${flower.pretNou} RON <button onclick="alert('Produsul a fost adăugat în cos!')"><i class="fa fa-shopping-cart" ></i></button>

                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </form:form>
                        <td><button style="border: none;background: transparent;"  data-bs-toggle="modal" data-bs-target="#updateModal${flower.id}">Update</button></td>
                        <td><button style="border: none; background: transparent;"  data-bs-toggle="modal" data-bs-target="#deleteModal${flower.id}">Delete</button></td>
                        <div class="modal fade" id="updateModal${flower.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">

                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                            <%--@elvariable id="flower" type="com.bianca.model.Flower"--%>
                                        <form:form action="updateFlower?nume=&id=${flower.id}" modelAttribute="flower" method="post">
                                            <div class="mb-3">
                                                <label for="nume" class="col-form-label" path="nume">Nume:</label>
                                                <form:input type="text" class="form-control" id="nume" path="nume" value="${flower.nume}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <label for="pret" class="col-form-label" path="pret">Pret:</label>
                                                <form:input type="text" class="form-control" id="pret" path="pret" value="${flower.pret}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <label for="descriere" class="col-form-label" path="descriere">Descriere:</label>
                                                <form:input type="text" class="form-control" id="descriere" path="descriere" rows="3" value="${flower.descriere}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <label for="categorie" class="col-form-label" path="categorie">Categorie:</label>
                                                <form:select class="form-select" id="categorie" path="categorie">
                                                    <option value="0"></option>
                                                    <option value="1">Crăciun</option>
                                                    <option value="2">Aranjamente Nuntă</option>
                                                    <option value="3">Buchete</option>
                                                    <option value="4">Criogenați</option>
                                                    <option value="5">Coșuri flori</option>
                                                    <option value="6">Funerar</option>
                                                </form:select>
                                            </div>

                                            <div class="mb-3">
                                                <label for="data" class="col-form-label" path="data">Data:</label>
                                                <form:input type="text" class="form-control" id="data" path="data" value="${flower.data}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <label for="img" class="col-form-label" path="img">Img URL:</label>
                                                <form:input type="text" class="form-control" id="img" path="img" value="${flower.img}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <c:choose>
                                                    <c:when test ="${flower.ePromotie == false}">
                                                        <form:checkbox path="ePromotie" value=""/> Promoție?
                                                    </c:when>
                                                    <c:otherwise>
                                                        <form:checkbox path="ePromotie" value="" checked="checked"/> Promoție?
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="mb-3">
                                                <label for="pretNou" class="col-form-label" path="pretNou">Preț nou:</label>

                                                <form:input type="text" class="form-control" id="pretNou" path="pretNou" value="${flower.pretNou}"></form:input>
                                            </div>
                                            <input type="submit" value="Submit"/>
                                        </form:form>



                                    </div>

                                </div>
                            </div>
                        </div>

                        <div class="modal fade" id="deleteModal${flower.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">

                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form:form action="deleteFlower?nume=${flower.nume}&id=${flower.id}" method="POST">
                                            <div class="mb-3">
                                                <label class="col-form-label">Ești sigur că vrei să ștergi produsul?</label>
                                            </div>
                                            <input type="submit" value="Da"/>

                                        </form:form>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="modal fade" id="exampleModal${flower.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">

                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <td><h5>${flower.descriere}</h5></td>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Închide</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        </tr>


                    </div>

                </c:if>
            </c:forEach>
        </div>
    </div>
</section>

<section class="product">
    <div class="container py-5">
        <div class="row py-5 ">
            <div class=" text-left">
                <h2 id="Funerar" > Funerar <button style="border: none;background: transparent;"  data-bs-toggle="modal" data-bs-target="#insertModalCraciun">+</button></h2>

            </div>
        </div>



        <div class="row">
            <c:forEach var="flower" items="${flowers}">
                <c:if test="${flower.categorie=='Funerar'}">
                    <div class="col-lg-1 text-center">

                        <div class="card border-0 bg-light mb-2">
                            <div class="card-body" >
                                <button  style="color:black; background:none; border: none; font-weight:bold;" data-bs-toggle="modal" data-bs-target="#exampleModal${flower.id}">
                                    <tr>

                                        <td>  <img src="${flower.img}" class="img-fluid" alt=""></td>

                                </button>

                            </div>
                        </div>

                        <form:form action="process-wish?id=${flower.id}&page=catalog" method="post">
                            <td>${flower.nume} <button  onclick="alert('Produsul a fost adăugat în wish!')"><i class="heart fa fa-heart-o" ></i></button></td>

                        </form:form>
                        <form:form action="process-cart?id=${flower.id}&page=catalog" method="post">
                            <td>
                                <c:choose>
                                    <c:when test ="${flower.ePromotie==false}">
                                        ${flower.pret} RON <button onclick="alert('Produsul a fost adăugat în cos!')"><i class="fa fa-shopping-cart" ></i></button>

                                    </c:when>
                                    <c:otherwise>
                                        <strike>${flower.pret} RON</strike> ${flower.pretNou} RON <button onclick="alert('Produsul a fost adăugat în cos!')"><i class="fa fa-shopping-cart" ></i></button>

                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </form:form>
                        <td><button style="border: none;background: transparent;"  data-bs-toggle="modal" data-bs-target="#updateModal${flower.id}">Update</button></td>
                        <td><button style="border: none; background: transparent;"  data-bs-toggle="modal" data-bs-target="#deleteModal${flower.id}">Delete</button></td>
                        <div class="modal fade" id="updateModal${flower.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">

                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                            <%--@elvariable id="flower" type="com.bianca.model.Flower"--%>
                                        <form:form action="updateFlower?nume=&id=${flower.id}" modelAttribute="flower" method="post">
                                            <div class="mb-3">
                                                <label for="nume" class="col-form-label" path="nume">Nume:</label>
                                                <form:input type="text" class="form-control" id="nume" path="nume" value="${flower.nume}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <label for="pret" class="col-form-label" path="pret">Pret:</label>
                                                <form:input type="text" class="form-control" id="pret" path="pret" value="${flower.pret}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <label for="descriere" class="col-form-label" path="descriere">Descriere:</label>
                                                <form:input type="text" class="form-control" id="descriere" path="descriere" rows="3" value="${flower.descriere}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <label for="categorie" class="col-form-label" path="categorie">Categorie:</label>
                                                <form:select class="form-select" id="categorie" path="categorie">
                                                    <option value="0"></option>
                                                    <option value="1">Crăciun</option>
                                                    <option value="2">Aranjamente Nuntă</option>
                                                    <option value="3">Buchete</option>
                                                    <option value="4">Criogenați</option>
                                                    <option value="5">Cutii flori</option>
                                                    <option value="6">Funerar</option>
                                                </form:select>
                                            </div>

                                            <div class="mb-3">
                                                <label for="data" class="col-form-label" path="data">Data:</label>
                                                <form:input type="text" class="form-control" id="data" path="data" value="${flower.data}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <label for="img" class="col-form-label" path="img">Img URL:</label>
                                                <form:input type="text" class="form-control" id="img" path="img" value="${flower.img}"></form:input>
                                            </div>
                                            <div class="mb-3">
                                                <c:choose>
                                                    <c:when test ="${flower.ePromotie == false}">
                                                        <form:checkbox path="ePromotie" value=""/> Promoție?
                                                    </c:when>
                                                    <c:otherwise>
                                                        <form:checkbox path="ePromotie" value="" checked="checked"/> Promoție?
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="mb-3">
                                                <label for="pretNou" class="col-form-label" path="pretNou">Preț nou:</label>

                                                <form:input type="text" class="form-control" id="pretNou" path="pretNou" value="${flower.pretNou}"></form:input>
                                            </div>
                                            <input type="submit" value="Submit"/>
                                        </form:form>



                                    </div>

                                </div>
                            </div>
                        </div>


                        <div class="modal fade" id="deleteModal${flower.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">

                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form:form action="deleteFlower?nume=${flower.nume}&id=${flower.id}" method="POST">
                                            <div class="mb-3">
                                                <label class="col-form-label">Ești sigur că vrei să ștergi produsul?</label>
                                            </div>
                                            <input type="submit" value="Da"/>

                                        </form:form>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="modal fade" id="exampleModal${flower.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">

                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <td><h5>${flower.descriere}</h5></td>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Închide</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        </tr>


                    </div>

                </c:if>
            </c:forEach>
        </div>
    </div>
</section>

<div class="modal fade" id="insertModalCraciun" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">

                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <%--@elvariable id="flower" type="com.bianca.model.Flower"--%>
                <form:form action="process-flower" modelAttribute="flower" method="POST">
                    <div class="mb-3">
                        <label for="nume" class="col-form-label" path="nume">Nume:</label>
                        <form:input type="text" class="form-control" id="nume" path="nume"></form:input>
                    </div>
                    <div class="mb-3">
                        <label for="pret" class="col-form-label" path="pret">Pret:</label>
                        <form:input type="text" class="form-control" id="pret" path="pret"></form:input>
                    </div>
                    <div class="mb-3">
                        <label for="descriere" class="col-form-label" path="descriere">Descriere:</label>
                        <form:textarea type="text" class="form-control" id="descriere" path="descriere" rows="3"></form:textarea>
                    </div>
                    <div class="mb-3">
                        <label for="categorie" class="col-form-label" path="categorie">Categorie:</label>
                        <form:select class="form-select" id="categorie" path="categorie">
                            <option value="0"></option>
                            <option value="1">Crăciun</option>
                            <option value="2">Aranjamente Nuntă</option>
                            <option value="3">Buchete</option>
                            <option value="4">Criogenați</option>
                            <option value="5">Cutii flori</option>
                            <option value="6">Funerar</option>
                        </form:select>
                    </div>
                    <div class="mb-3">
                        <label for="img" class="col-form-label" path="img">Img URL:</label>
                        <form:input type="text" class="form-control" id="img" path="img"></form:input>
                    </div>
                    <div class="mb-3">
                        <form:checkbox path="ePromotie" value="Promoție?"/> Promoție?
                    </div>
                    <div class="mb-3">
                        <label for="img" class="col-form-label" path="pretNou">Preț nou:</label>
                        <form:input type="text" class="form-control" id="img" path="pretNou" placeholder="Lasati gol daca nu e promotie"></form:input>
                    </div>
                    <input type="submit" value="Adaugă"/>
                </form:form>

            </div>

        </div>
    </div>
</div>


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