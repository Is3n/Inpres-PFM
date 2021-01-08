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
                <a class="navbar-brand js-scroll-trigger" href="#page-top">Acceuil</a>
                <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                    Menu
                    <i class="fas fa-bars"></i>
                </button>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item"><a class="nav-link js-scroll-trigger" href="home.jsp#catalogue">Catalogue</a></li>
                        <li class="nav-item"><a class="nav-link js-scroll-trigger" href="home.jsp#visites">Nos visites</a></li>
                        <li class="nav-item"><a class="nav-link js-scroll-trigger" href="#Contact">Contact</a></li>
                        <li class="nav-item"><a class="nav-link js-scroll-trigger" href="#affPanier">Mon panier</a></li>
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
                    <h2 class="text-white-50 mx-auto mt-2 mb-5">Bienvenue ${ currentUser.nom } sur l'application loisirs d'InpresPFM !</h2>

                    <a class="btn btn-primary js-scroll-trigger" href="#affPanier">Afficher votre panier</a>
                       
                </div>
            </div>
        </header>
        <!-- Login-->
        <section class="about-section text-center" id="affPanier">
            <div class="limiter">
                <h2 class="text-white-50 mx-auto" style="padding-top: 3rem">Panier</h2>
                <h3 class="text-white-30 mx-auto ">${ SuppressionPanierForm.resultat }</h3>
                <hr class="my-4" />
                <h3 class="text-white-50 mx-auto" style="padding-top: 3rem">Articles :</h3>
		<div class="container-table100">
			<div class="wrap-table100">
				<div class="table100">
					<table>
						<thead>
							<tr class="table100-head">
								<th class="column2">id du produit</th>
								<th class="column3">Nom</th>
								<th class="column4">Prix</th>
								<th class="column5">Quantité</th>
								<th class="column6">Total</th>
                                                                <th class="column6">Supprimer du panier</th>
							</tr>
						</thead>
						<tbody>
				
                                                    <%
                                                        Panier myPanier = (Panier)session.getAttribute("currentPanier");
                                                        ArrayList<Article> myList = new ArrayList<Article>();
                                                        
                                                        Iterator <Article> myIterator = myPanier.getPanier().iterator();
                                                        float montantTotal=0; 
                                                        while(myIterator.hasNext())
                                                        {
                                                            Article myArticle = myIterator.next();
                                                            
                                                            montantTotal = montantTotal + (myArticle.getQuantite()*myArticle.getPrixArticle());
                                                            
                                                            if(myArticle.getIdArticle().contains("A"))
                                                            {
                                                    %>        
                                                            <tr>
                                                                <td class="column2"><%out.println(myArticle.getIdArticle());%></td>
                                                                <td class="column3"><%out.println(myArticle.getNomArticle());%></td>
                                                                <td class="column4"><%out.println(myArticle.getPrixArticle());%></td>
                                                                <td class="column5"><%out.println(myArticle.getQuantite());%></td>
                                                                <td class="column6"><%out.println(myArticle.getQuantite()*myArticle.getPrixArticle());%></td>
                                                                    <form method="POST" action="RemovePanier">
                                                                        <input type="text" style="display:none;" name="idProduit" value="<%out.println(myArticle.getIdArticle());%>"/>
                                                                        <td class="column6"><input class="btn btn-primary js-scroll-trigger mx-auto" type="submit" value="Supprimer" /></td>
                                                                    </form>
                                                                </tr>
                                                    <%
                                                            }
                                                        }
                                                        session.setAttribute("montantTotal", montantTotal);
                                                    %>
								
						</tbody>
					</table>
				</div>
			</div>
		</div>
                <h3 class="text-white-50 mx-auto" style="padding-top: 3rem">Visites :</h3>
		<div class="container-table100">
			<div class="wrap-table100">
				<div class="table100">
					<table>
						<thead>
							<tr class="table100-head">
								<th class="column2">id du produit</th>
								<th class="column3">Nom</th>
								<th class="column4">Prix</th>
								<th class="column5">Quantité</th>
                                                                <th class="column6">Total</th>
                                                                <th class="column6">Supprimer du panier</th>
							</tr>
						</thead>
						<tbody>
				
                                                    <%
                                                        
                                                        Iterator <Article> myIterator2 = myPanier.getPanier().iterator();
                                                        while(myIterator2.hasNext())
                                                        {
                                                            Article myArticle = myIterator2.next();
                                                            if(myArticle.getIdArticle().contains("V"))
                                                            {
                                                    %>        
                                                            <tr>
                                                                <td class="column2"><%out.println(myArticle.getIdArticle());%></td>
                                                                <td class="column3"><%out.println(myArticle.getNomArticle());%></td>
                                                                <td class="column4"><%out.println(myArticle.getPrixArticle());%></td>
                                                                <td class="column5"><%out.println(myArticle.getQuantite());%></td>
                                                                <td class="column6"><%out.println(myArticle.getQuantite()*myArticle.getPrixArticle());%></td>
                                                                    <form method="POST" action="RemovePanier">
                                                                        <input type="text" style="display:none;" name="idProduit" value="<%out.println(myArticle.getIdArticle());%>"/>
                                                                        <td class="column6"><input class="btn btn-primary js-scroll-trigger mx-auto" type="submit" value="Supprimer" /></td>
                                                                    </form>
                                                                </tr>
                                                    <%
                                                            }
                                                        }
                                                    %>
								
						</tbody>
					</table>
				</div>
			</div>
		</div>
                                                    <form method="post" action=payer.jsp>
                <div class="container-contact3-form-btn" style="padding-top: 5rem;padding-bottom: 10rem;">
						<button type="submit" href="payer.jsp" type="submit" name="payerButton" value="payer" class="contact3-form-btn mx-auto">
							Payer
						</button>                                           
                </div></form>
	</div>
        </section>
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

