<%-- 
    Author     : isen0
--%>

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
                        <li class="nav-item"><a class="nav-link js-scroll-trigger" href="#catalogue">Catalogue</a></li>
                        <li class="nav-item"><a class="nav-link js-scroll-trigger" href="#visites">Nos visites</a></li>
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
                    <h2 class="text-white-50 mx-auto mt-2 mb-5">Bienvenue ${ currentUser.nom } sur l'application loisirs d'InpresPFM !</h2>

                    <a class="btn btn-primary js-scroll-trigger" href="#catalogue">Afficher le catalogue</a>
                    <a class="btn btn-primary js-scroll-trigger" href="#visites">Afficher les visites</a>
                       
                </div>
            </div>
        </header>
        <!-- Login-->
        <section class="about-section text-center" id="catalogue">
            <div class="limiter">
                <h2 class="text-white-50 mx-auto" style="padding-top: 3rem">Notre catalogue</h2>
                <h3 class="text-white-30 mx-auto ">${ AjoutPanierForm.resultat }</h3>
		<div class="container-table100">
			<div class="wrap-table100">
				<div class="table100">
					<table>
						<thead>
							<tr class="table100-head">
								<th class="column2">id du produit</th>
								<th class="column3">Nom</th>
								<th class="column4">Prix</th>
								<th class="column5">Stock</th>
                                                                <th class="column6">Quantité</th>
								<th class="column6">Ajouter au panier</th>
							</tr>
						</thead>
						<tbody>
				
								<%
                                                                try
                                                                {
                                                                    String configPath = System.getProperty("user.home")+System.getProperty("file.separator")+"Documents"+System.getProperty("file.separator")+
                "HEPL"+System.getProperty("file.separator")+"20-21"+System.getProperty("file.separator")+"RTI"+System.getProperty("file.separator")+
                "Java"+System.getProperty("file.separator")+"Web_Applic_Loisirs"+System.getProperty("file.separator")+ "configWeb.properties";
                                                                    
                                                                    ResultSet rs;
                                                                    
                                                                    ConnexionDB db = new ConnexionDB(configPath,"bd_shopping");

                                                                    db.openConnection();
                                                                    FichierLog.UpdateFich("Connexion a la base de donnees dans home.jsp" );

                                                                    rs = null;

                                                                    if(db!=null)
                                                                    {

                                                                        rs = db.select("SELECT * FROM articles");

                                                                        ResultSetMetaData metaData = rs.getMetaData();

                                                                        while(rs.next())
                                                                        {
                                                                            Vector<String> data = new Vector<>();
                                                                            for(int cpt = 1; cpt <= metaData.getColumnCount(); cpt++)
                                                                            {
                                                                                //System.out.println("rs : " + rs.getString(cpt));
                                                                                data.add(rs.getString(cpt));
                                                                            }
                                                                            //System.out.println("data : " + data);
                                                                            //ystem.out.println("data first elem : " + data.firstElement());
                                                            %>
                                                                            <tr>
                                                                                <td class="column2"><%out.println(data.elementAt(0));%></td>
                                                                                <td class="column3"><%out.println(data.elementAt(1));%></td>
                                                                                <td class="column4"><%out.println(data.elementAt(2));%></td>
                                                                                <td class="column5"><%out.println(data.elementAt(3));%></td>
                                                                                    <form method="POST" action="AjoutPanier">
                                                                                        <input type="text" style="display:none;" name="idProduit" value="<%out.println(data.elementAt(0));%>"/>
                                                                                        <input type="text" style="display:none;" name="nomProduit" value="<%out.println(data.elementAt(1));%>"/>
                                                                                        <input type="text" style="display:none;" name="prixProduit" value="<%out.println(data.elementAt(2));%>"/>
                                                                                        <input type="text" style="display:none;" name="stockProduit" value="<%out.println(data.elementAt(3));%>"/>
                                                                                        <td class="column6"><input class="btn btn-primary js-scroll-trigger mx-auto" type="number" name="quantiteProduit" value="0" min="0" style="background-color: #003366; color: #fff; padding-left: 1rem;"></td>
                                                                                        <td class="column6"><input class="btn btn-primary js-scroll-trigger mx-auto" type="submit" value="Ajouter" /></td>
                                                                                    </form>
                                                                            </tr>
                                                            <%
                                                                        }

                                                                        db.closeConnection();
                                                                        db = null;
                                                                    }
                                                                    else
                                                                    {
                                                                        System.err.println("Veuillez vous connecter à la base de donnee");
                                                                        %>
                                                                            <option>Erreur DB</option>
                                                                            <tr>
                                                                                <td class="column2">Erreur DB</td>
                                                                                <td class="column3">Erreur DB</td>
                                                                                <td class="column4">Erreur DB</td>
                                                                                <td class="column5">Erreur DB</td>
                                                                                <td class="column6">Erreur DB</td>
                                                                        <%
                                                                    }
                                                                }
                                                                catch (ClassNotFoundException | SQLException e)
                                                                {
                                                                    System.err.println("Erreur lors de la connexion à la DB");
                                                                        %>
                                                                            <option>Erreur DB</option>
                                                                        <%
                                                                }
                                                            %>
								
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
        </section>
                                                            
        <section class="about-section text-center" id="visites">
            <div class="limiter">
                <h2 class="text-white-50 mx-auto" style="padding-top: 3rem">Nos visites</h2>
		<div class="container-table100">
			<div class="wrap-table100">
				<div class="table100">
					<table>
						<thead>
							<tr class="table100-head">
								<th class="column2">id de la visite</th>
								<th class="column3">Nom</th>
								<th class="column4">Prix</th>
								<th class="column5">Places disponibles</th>
                                                                <th class="column6">Date de la visite</th>
                                                                <th class="column6">Nombre de participants</th>
								<th class="column6">Ajouter au panier</th>
							</tr>
						</thead>
						<tbody>
				
								<%
                                                                try
                                                                {
                                                                    String configPath = System.getProperty("user.home")+System.getProperty("file.separator")+"Documents"+System.getProperty("file.separator")+
                "HEPL"+System.getProperty("file.separator")+"20-21"+System.getProperty("file.separator")+"RTI"+System.getProperty("file.separator")+
                "Java"+System.getProperty("file.separator")+"Web_Applic_Loisirs"+System.getProperty("file.separator")+ "configWeb.properties";
                                                                    
                                                                    ResultSet rs;
                                                                    
                                                                    ConnexionDB db = new ConnexionDB(configPath,"bd_shopping");

                                                                    db.openConnection();
                                                                    FichierLog.UpdateFich("Connexion a la base de donnees dans home.jsp" );

                                                                    rs = null;

                                                                    if(db!=null)
                                                                    {

                                                                        rs = db.select("SELECT * FROM visites");

                                                                        ResultSetMetaData metaData = rs.getMetaData();

                                                                        while(rs.next())
                                                                        {
                                                                            Vector<String> data = new Vector<>();
                                                                            for(int cpt = 1; cpt <= metaData.getColumnCount(); cpt++)
                                                                            {
                                                                                //System.out.println("rs : " + rs.getString(cpt));
                                                                                data.add(rs.getString(cpt));
                                                                            }
                                                                            //System.out.println("data : " + data);
                                                                            //ystem.out.println("data first elem : " + data.firstElement());
                                                            %>
                                                                            <tr>
                                                                                <td class="column2"><%out.println(data.elementAt(0));%></td>
                                                                                <td class="column3"><%out.println(data.elementAt(1));%></td>
                                                                                <td class="column4"><%out.println(data.elementAt(2));%></td>
                                                                                <td class="column5"><%out.println(data.elementAt(3));%></td>
                                                                                <td class="column6"><%out.println(data.elementAt(4));%></td>
                                                                                    <form method="POST" action="AjoutPanier">
                                                                                        <input type="text" style="display:none;" name="idProduit" value="<%out.println(data.elementAt(0));%>"/>
                                                                                        <input type="text" style="display:none;" name="nomProduit" value="<%out.println(data.elementAt(1));%>"/>
                                                                                        <input type="text" style="display:none;" name="prixProduit" value="<%out.println(data.elementAt(2));%>"/>
                                                                                        <input type="text" style="display:none;" name="stockProduit" value="<%out.println(data.elementAt(3));%>"/>
                                                                                        <td class="column6"><input class="btn btn-primary js-scroll-trigger mx-auto" type="number" name="quantiteProduit" value="0" min="0" style="background-color: #003366; color: #fff; padding-left: 1rem;"></td>
                                                                                        <td class="column6"><input class="btn btn-primary js-scroll-trigger mx-auto" type="submit" value="Ajouter" /></td>
                                                                                    </form>
                                                                            </tr>
                                                            <%
                                                                        }

                                                                        db.closeConnection();
                                                                        db = null;
                                                                    }
                                                                    else
                                                                    {
                                                                        System.err.println("Veuillez vous connecter à la base de donnee");
                                                                        %>
                                                                            <option>Erreur DB</option>
                                                                            <tr>
                                                                                <td class="column2">Erreur DB</td>
                                                                                <td class="column3">Erreur DB</td>
                                                                                <td class="column4">Erreur DB</td>
                                                                                <td class="column5">Erreur DB</td>
                                                                                <td class="column6">Erreur DB</td>
                                                                        <%
                                                                    }
                                                                }
                                                                catch (ClassNotFoundException | SQLException e)
                                                                {
                                                                    System.err.println("Erreur lors de la connexion à la DB");
                                                                        %>
                                                                            <option>Erreur DB</option>
                                                                        <%
                                                                }
                                                            %>
								
						</tbody>
					</table>
				</div>
			</div>
		</div>
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

