<%-- 
    Author     : isen0
--%>

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
                        <li class="nav-item"><a class="nav-link js-scroll-trigger" href="#login">Login</a></li>
                        <!--<li class="nav-item"><a class="nav-link js-scroll-trigger" href="#projects">Projects</a></li>-->
                        <li class="nav-item"><a class="nav-link js-scroll-trigger" href="#Contact">Contact</a></li>
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
                    <h2 class="text-white-50 mx-auto mt-2 ">Bienvenue sur l'application loisirs d'InpresPFM !</h2>
                    <h2 class="text-white-50 mx-auto mt-2 mb-5">Veuillez vous connecter pour commencer</h2>
                    <a class="btn btn-primary js-scroll-trigger" href="#login">Se connecter</a>
                </div>
            </div>
        </header>
        <!-- Login-->
        <section class="about-section text-center" id="login">
            <div class="container-contact3">
			<div class="wrap-contact3">
				<form class="contact3-form validate-form" method="post" action=CheckLogin>
					<span class="contact3-form-title">
						Connexion
					</span>
                                    
                                    <h3 class="text-white-30 mx-auto ">${ connectionForm.resultat }</h3>

					<div class="wrap-contact3-form-radio">
						<div class="contact3-form-radio m-r-42">
							<input class="input-radio3" id="radio1" type="radio" name="choice" value="inscription">
							<label class="label-radio3" for="radio1">
								S'inscrire
							</label>
						</div>

						<div class="contact3-form-radio ">
							<input class="input-radio3" id="radio2" type="radio" name="choice" value="connection" checked="checked">
							<label class="label-radio3" for="radio2">
								Se connecter
							</label>
						</div>
					</div>

					<div class="wrap-input3 validate-input" data-validate="Name is required">
						<input class="input3" type="text" name="name" <%if(request.getAttribute("infosUserTmp")!=null){%>value="${ infosUserTmp.nom }"<%}%> placeholder="Votre login">
						<span class="focus-input3"></span>
					</div>

					<div class="wrap-input3 validate-input" data-validate = "Password is required">
						<input class="input3" type="password" name="pass" placeholder="Votre mot de passe" <%if(request.getAttribute("infosUserTmp")!=null){%>value="${ infosUserTmp.pass }"<%}%>>
						<span class="focus-input3"></span>
					</div>
                                        <!--
					<div class="wrap-input3 input3-select">
						<div>
							<select class="selection-2" name="service">
								<option>Needed Services</option>
								<option>eCommerce Bussiness</option>
								<option>UI/UX Design</option>
								<option>Online Services</option>
							</select>
						</div>
						<span class="focus-input3"></span>
					</div>

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
                                        
                                        <div class="container-contact3-form-btn input3-select">
						<button type="submit" name="loginButton" value="mdp" class="contact3-form-btn mx-auto">
							Mot de passe oublié
						</button>                                           
					</div>
                                        
					<div class="container-contact3-form-btn">
						<button type="submit" name="loginButton" value="valider" class="contact3-form-btn mx-auto">
							Valider
						</button>                                           
					</div>
                                           
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

