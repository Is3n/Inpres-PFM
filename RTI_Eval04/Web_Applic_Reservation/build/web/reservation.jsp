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
        <title>Inpres PFM - Web Applic Reservation</title>
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
            String redirectURL = "index.jsp";
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
                        <li class="nav-item"><a class="nav-link js-scroll-trigger" href="#reservation">Réservation</a></li>
                        <!--<li class="nav-item"><a class="nav-link js-scroll-trigger" href="#projects">Projects</a></li>-->
                        <li class="nav-item"><a class="nav-link js-scroll-trigger" href="#Contact">Contact</a></li>
                        <li class="nav-item"><a class="nav-link js-scroll-trigger" href="Logout">Se déconnecter</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- Masthead-->
        <header class="masthead">
            <div class="container d-flex h-100 align-items-center">
                <div class="mx-auto text-center">
                    <h1 class="mx-auto my-0 text-uppercase">Inpres PFM</h1>
                    <hr class="my-4" />
                    <h2 class="text-white-50 mx-auto mt-2 mb-5">Bienvenue ${ currentUser.nom } sur l'application de réservation d'InpresPFM !</h2>
                    <%
                    if(request.getAttribute("reservationResult") != null && request.getAttribute("reservationResult").equals("ReservationOK"))
                    {
                    %>
                        <a class="btn btn-primary js-scroll-trigger" href="#reservation">Afficher les détails de votre réservation</a>
                    <%
                    }
                    else
                    {
                    %>
                        <a class="btn btn-primary js-scroll-trigger" href="#reservation">Faire une réservation</a>
                    <%
                    }
                    %>
                        
                </div>
            </div>
        </header>
        <!-- Login-->
        <section class="about-section text-center" id="reservation">
            <div class="container-contact3">
			<div class="wrap-contact3">
				<form class="contact3-form validate-form" method="post" action=MakeReservation>
                                    
                                <%
                                if(request.getAttribute("reservationResult") != null && request.getAttribute("reservationResult").equals("ReservationOK"))
                                {
                                %>
                                    <span class="contact3-form-title">
					Détails de la réservation
                                    </span>
                                    
                                    <div class="wrap-input3 validate-input" >
						<input class="input3" type="text" value="Identifiant de la réservation : ${ reservationInfos.idReservation }" disabled="disabled">
						<span class="focus-input3"></span>
                                    </div>
                                                
                                    <div class="wrap-input3 validate-input" >
						<input class="input3" type="text" value="Emplacement attribué : ${ reservationInfos.emplacement }" disabled="disabled">
						<span class="focus-input3"></span>
                                    </div>  
                                                
                                    <div class="wrap-input3 validate-input" >
						<input class="input3" type="text" value="Identifiant du container : ${ reservationInfos.container }" disabled="disabled">
						<span class="focus-input3"></span>
                                    </div>
                                                
                                    <div class="wrap-input3 validate-input" >
						<input class="input3" type="text" value="Société : ${ reservationInfos.societe }" disabled="disabled">
						<span class="focus-input3"></span>
                                    </div> 
                                                
                                    <div class="wrap-input3 validate-input" >
						<input class="input3" type="text" value="Nature du contenu : ${ reservationInfos.nature }" disabled="disabled">
						<span class="focus-input3"></span>
                                    </div> 
                                                
                                    <div class="wrap-input3 validate-input" >
						<input class="input3" type="text" value="Capacité du container : ${ reservationInfos.capacite }" disabled="disabled">
						<span class="focus-input3"></span>
                                    </div> 
                                                
                                    <div class="wrap-input3 validate-input" >
						<input class="input3" type="text" value="Dangers du container : ${ reservationInfos.dangers }" disabled="disabled">
						<span class="focus-input3"></span>
                                    </div>
                                                
                                    <div class="wrap-input3 validate-input" >
						<input class="input3" type="text" value="Destination : ${ reservationInfos.destination }" disabled="disabled">
						<span class="focus-input3"></span>
                                    </div>
                                <%
                                }
                                else
                                {
                                %>
                                
                                        <span class="contact3-form-title">
						Réservation
					</span>
                                
                                <h3 class="text-white-30 mx-auto ">${ reservationResult }</h3>

					<!--<div class="wrap-input3 validate-input" data-validate="Reservation Id is required">
						<input class="input3" type="text" name="resId" placeholder="Identifiant de la réservation">
						<span class="focus-input3"></span>
					</div>-->

                                        <div class="wrap-input3 validate-input" data-validate = "Container Id is required">
						<input class="input3" type="text" name="idContainer" placeholder="Identifiant du container">
						<span class="focus-input3"></span>
					</div>
                                        
                                        <div class="wrap-input3 input4-select">
                                            <label for="societe" style="color : #fff">Société : </label>
							<select class="selection-2" id="societe" name="societe">
                                                            <%
                                                                try
                                                                {
                                                                    String configPath = System.getProperty("user.home")+System.getProperty("file.separator")+"Documents"+System.getProperty("file.separator")+
                "HEPL"+System.getProperty("file.separator")+"20-21"+System.getProperty("file.separator")+"RTI"+System.getProperty("file.separator")+
                "Java"+System.getProperty("file.separator")+"Web_Applic_Reservation"+System.getProperty("file.separator")+ "configWeb.properties";
                                                                    
                                                                    ResultSet rs;
                                                                    
                                                                    ConnexionDB db = new ConnexionDB(configPath,"bd_mouvements");

                                                                    db.openConnection();
                                                                    FichierLog.UpdateFich("Connexion a la base de donnees dans reservation.jsp" );

                                                                    rs = null;

                                                                    if(db!=null)
                                                                    {

                                                                        rs = db.select("SELECT id FROM societes");

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
                                                                            //System.out.println("data first elem : " + data.firstElement());
                                                            %>
                                                                            <option><%out.println(data.firstElement());%></option>
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
							</select>
						<span class="focus-input3"></span>
					</div>
                                    
                                        <div class="wrap-input3 validate-input" data-validate = "Nature contenu is required">
						<input class="input3" type="text" name="natureContenu" placeholder="Nature du contenu">
						<span class="focus-input3"></span>
					</div>
                                    
                                        <div class="wrap-input3 validate-input" data-validate = "Capacité is required">
						<input class="input3" type="text" name="capacite" placeholder="Capacité du container">
						<span class="focus-input3"></span>
					</div>
                                    
                                        <div class="wrap-input3 validate-input" data-validate = "Dangers is required">
						<input class="input3" type="text" name="dangers" placeholder="Dangers du container">
						<span class="focus-input3"></span>
					</div>
                                        
					<div class="wrap-input3 input4-select">
						<label for="destination" style="color : #fff">Destination : </label>
							<select class="selection-2" id="destination" name="destination">
								<%
                                                                try
                                                                {
                                                                    String configPath = System.getProperty("user.home")+System.getProperty("file.separator")+"Documents"+System.getProperty("file.separator")+
                "HEPL"+System.getProperty("file.separator")+"20-21"+System.getProperty("file.separator")+"RTI"+System.getProperty("file.separator")+
                "Java"+System.getProperty("file.separator")+"Web_Applic_Reservation"+System.getProperty("file.separator")+ "configWeb.properties";
                                                                    
                                                                    ResultSet rs;
                                                                    
                                                                    ConnexionDB db = new ConnexionDB(configPath,"bd_mouvements");

                                                                    db.openConnection();
                                                                    FichierLog.UpdateFich("Connexion a la base de donnees dans reservation.jsp" );

                                                                    rs = null;

                                                                    if(db!=null)
                                                                    {

                                                                        rs = db.select("SELECT ville FROM destinations");

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
                                                                            <option><%out.println(data.firstElement());%></option>
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
							</select>
						<span class="focus-input3"></span>
					</div>
                                        
                                        <!--
					<div class="wrap-input3 input3-select">
						<div>
							<select class="selection-2" name="budget">
								<option>Budget</option>
								<option>1500 $</option>
								<option>2000 $</option>
								<option>3000 $</option>
							</select>
						</div>
						<span class="focus-input3"></span>
					</div>
                                        -->
					<div class="container-contact3-form-btn">
						<button type="submit" class="contact3-form-btn mx-auto">
							Valider
						</button>
					</div>
                                <%
                                }       
                                %>
				</form>
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

