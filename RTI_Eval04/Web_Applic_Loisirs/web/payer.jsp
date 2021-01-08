<%-- 
    Author     : isen0
--%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Beans.Article"%>
<%@page import="Beans.Panier"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.util.Vector"%>
<%@page import="java.sql.ResultSetMetaData"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="database_acces.FichierLog"%>
<%@page import="database_acces.ConnexionDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Inpres PFM - Web Applic Loisirs</title>
        <link rel="icon" type="image/x-icon" href="assets/img/favicon.ico" />
        <!-- Font Awesome icons (free version)-->
        <script src="https://use.fontawesome.com/releases/v5.15.1/js/all.js" crossorigin="anonymous"></script>
        <!-- Google fonts-->
        <link href="https://fonts.googleapis.com/css?family=Varela+Round" rel="stylesheet" />
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="css/styles.css" rel="stylesheet" />
    </head>
    <body id="page-top">
        <%if (session.getAttribute("currentUser") == null)
        {
            String redirectURL = "login.jsp";
            response.sendRedirect(redirectURL);
        }
        %>
        <!-- Navigation-->
        <nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
            <div class="container">
                <a class="navbar-brand js-scroll-trigger" href="#page-top">Inpres PFM Loisirs</a>
                <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                    Menu
                    <i class="fas fa-bars"></i>
                </button>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item"><a class="nav-link js-scroll-trigger" href="home.jsp#catalogue">Catalogue</a></li>
                        <li class="nav-item"><a class="nav-link js-scroll-trigger" href="home.jsp#visites">Nos visites</a></li>
                        <li class="nav-item"><a class="nav-link js-scroll-trigger" href="#Contact">Contact</a></li>
                        <li class="nav-item"><a class="nav-link js-scroll-trigger" href="panier.jsp">Mon panier</a></li>
                        <li class="nav-item"><a class="nav-link js-scroll-trigger" href="Logout">Se déconnecter</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- Masthead-->
        <header class="masthead">
            <div class="container d-flex h-100 align-items-center">
                <div class="mx-auto text-center">
                    <h1 class="mx-auto my-0 text-uppercase">Inpres PFM Loisirs</h1>
                    <hr class="my-4" />
                    <h2 class="text-white-50 mx-auto mt-2 mb-5">${ currentUser.nom }, le montant de votre commande s'élève à ${ montantTotal } euros</h2>
                    
                    <% if(request.getAttribute("PayerPanierForm")==null)
                        {%>
                            <form method="post" action=ValiderPaiement>
                            <button type="submit" class="btn btn-primary js-scroll-trigger">Régler votre commande</button>
                            </form>
                        <%}
                        else
                        {%>
                            <h3 class="text-white-30 mx-auto " style="color: #fff">${ PayerPanierForm.resultat }</h3>

                            <form method="post" action=home.jsp>
                            <button type="submit" class="btn btn-primary js-scroll-trigger">Inpres PFM Loisirs</button>
                            </form> 
                        <%}%>
                </div>
            </div>
        </header>
        
        <!-- Contact-->
        <%@ include file="footer.jsp" %>
        <!-- Bootstrap core JS-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Third party plugin JS-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
        <!-- Core theme JS-->
        <script src="js/scripts.js"></script>
    </body>
</html>

