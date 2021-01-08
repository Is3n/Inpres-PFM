package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.SQLException;
import java.util.Vector;
import java.sql.ResultSetMetaData;
import java.sql.ResultSet;
import database_acces.FichierLog;
import database_acces.ConnexionDB;

public final class panier_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(1);
    _jspx_dependants.add("/footer.jsp");
  }

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html lang=\"en\">\n");
      out.write("    <head>\n");
      out.write("        <meta charset=\"utf-8\" />\n");
      out.write("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\" />\n");
      out.write("        <meta name=\"description\" content=\"\" />\n");
      out.write("        <meta name=\"author\" content=\"\" />\n");
      out.write("        <title>Inpres PFM - Web Applic Loisirs</title>\n");
      out.write("        <link rel=\"icon\" type=\"image/x-icon\" href=\"assets/img/favicon.ico\" />\n");
      out.write("        <!-- Font Awesome icons (free version)-->\n");
      out.write("        <script src=\"https://use.fontawesome.com/releases/v5.15.1/js/all.js\" crossorigin=\"anonymous\"></script>\n");
      out.write("        <!-- Google fonts-->\n");
      out.write("        <link href=\"https://fonts.googleapis.com/css?family=Varela+Round\" rel=\"stylesheet\" />\n");
      out.write("        <link href=\"https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i\" rel=\"stylesheet\" />\n");
      out.write("        <!-- Core theme CSS (includes Bootstrap)-->\n");
      out.write("        <link href=\"css/styles.css\" rel=\"stylesheet\" />\n");
      out.write("    </head>\n");
      out.write("    <body id=\"page-top\">\n");
      out.write("        ");
if (session.getAttribute("currentUser") == null)
        {
            String redirectURL = "login.jsp";
            response.sendRedirect(redirectURL);
        }
        
      out.write("\n");
      out.write("        <!-- Navigation-->\n");
      out.write("        <nav class=\"navbar navbar-expand-lg navbar-light fixed-top\" id=\"mainNav\">\n");
      out.write("            <div class=\"container\">\n");
      out.write("                <a class=\"navbar-brand js-scroll-trigger\" href=\"#page-top\">Acceuil</a>\n");
      out.write("                <button class=\"navbar-toggler navbar-toggler-right\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarResponsive\" aria-controls=\"navbarResponsive\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n");
      out.write("                    Menu\n");
      out.write("                    <i class=\"fas fa-bars\"></i>\n");
      out.write("                </button>\n");
      out.write("                <div class=\"collapse navbar-collapse\" id=\"navbarResponsive\">\n");
      out.write("                    <ul class=\"navbar-nav ml-auto\">\n");
      out.write("                        <li class=\"nav-item\"><a class=\"nav-link js-scroll-trigger\" href=\"#catalogue\">Catalogue</a></li>\n");
      out.write("                        <li class=\"nav-item\"><a class=\"nav-link js-scroll-trigger\" href=\"#visites\">Nos visites</a></li>\n");
      out.write("                        <li class=\"nav-item\"><a class=\"nav-link js-scroll-trigger\" href=\"#Contact\">Contact</a></li>\n");
      out.write("                        <li class=\"nav-item\"><a class=\"nav-link js-scroll-trigger\" href=\"panier.jsp\">Mon panier</a></li>\n");
      out.write("                        <li class=\"nav-item\"><a class=\"nav-link js-scroll-trigger\" href=\"Logout\">Se déconnecter</a></li>\n");
      out.write("                    </ul>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </nav>\n");
      out.write("        <!-- Masthead-->\n");
      out.write("        <header class=\"masthead\">\n");
      out.write("            <div class=\"container d-flex h-100 align-items-center\">\n");
      out.write("                <div class=\"mx-auto text-center\">\n");
      out.write("                    <h1 class=\"mx-auto my-0 text-uppercase\">Inpres PFM Loisirs</h1>\n");
      out.write("                    <hr class=\"my-4\" />\n");
      out.write("                    <h2 class=\"text-white-50 mx-auto mt-2 mb-5\">Bienvenue ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ currentUser.nom }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write(" sur l'application loisirs d'InpresPFM !</h2>\n");
      out.write("\n");
      out.write("                    <a class=\"btn btn-primary js-scroll-trigger\" href=\"#affPanier\">Afficher votre panier</a>\n");
      out.write("                       \n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </header>\n");
      out.write("        <!-- Login-->\n");
      out.write("        <section class=\"about-section text-center\" id=\"affPanier\">\n");
      out.write("            <div class=\"limiter\">\n");
      out.write("                <h2 class=\"text-white-50 mx-auto\" style=\"padding-top: 3rem\">Panier</h2>\n");
      out.write("                <hr class=\"my-4\" />\n");
      out.write("                <h3 class=\"text-white-50 mx-auto\" style=\"padding-top: 3rem\">Articles :</h3>\n");
      out.write("\t\t<div class=\"container-table100\">\n");
      out.write("\t\t\t<div class=\"wrap-table100\">\n");
      out.write("\t\t\t\t<div class=\"table100\">\n");
      out.write("\t\t\t\t\t<table>\n");
      out.write("\t\t\t\t\t\t<thead>\n");
      out.write("\t\t\t\t\t\t\t<tr class=\"table100-head\">\n");
      out.write("\t\t\t\t\t\t\t\t<th class=\"column2\">id du produit</th>\n");
      out.write("\t\t\t\t\t\t\t\t<th class=\"column3\">Nom</th>\n");
      out.write("\t\t\t\t\t\t\t\t<th class=\"column4\">Prix</th>\n");
      out.write("\t\t\t\t\t\t\t\t<th class=\"column5\">Quantité</th>\n");
      out.write("\t\t\t\t\t\t\t\t<th class=\"column6\">Ajouter au panier</th>\n");
      out.write("\t\t\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t\t\t</thead>\n");
      out.write("\t\t\t\t\t\t<tbody>\n");
      out.write("\t\t\t\t\n");
      out.write("                                                    \n");
      out.write("\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t</tbody>\n");
      out.write("\t\t\t\t\t</table>\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</div>\n");
      out.write("                <h3 class=\"text-white-50 mx-auto\" style=\"padding-top: 3rem\">Visites :</h3>\n");
      out.write("\t\t<div class=\"container-table100\">\n");
      out.write("\t\t\t<div class=\"wrap-table100\">\n");
      out.write("\t\t\t\t<div class=\"table100\">\n");
      out.write("\t\t\t\t\t<table>\n");
      out.write("\t\t\t\t\t\t<thead>\n");
      out.write("\t\t\t\t\t\t\t<tr class=\"table100-head\">\n");
      out.write("\t\t\t\t\t\t\t\t<th class=\"column2\">id du produit</th>\n");
      out.write("\t\t\t\t\t\t\t\t<th class=\"column3\">Nom</th>\n");
      out.write("\t\t\t\t\t\t\t\t<th class=\"column4\">Prix</th>\n");
      out.write("\t\t\t\t\t\t\t\t<th class=\"column5\">Quantité</th>\n");
      out.write("\t\t\t\t\t\t\t\t<th class=\"column6\">Ajouter au panier</th>\n");
      out.write("\t\t\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t\t\t</thead>\n");
      out.write("\t\t\t\t\t\t<tbody>\n");
      out.write("\t\t\t\t\n");
      out.write("                                                    \n");
      out.write("\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t</tbody>\n");
      out.write("\t\t\t\t\t</table>\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</div>\n");
      out.write("                <div class=\"container-contact3-form-btn\">\n");
      out.write("\t\t\t\t\t\t<button href=\"#\" name=\"payerButton\" value=\"payer\" class=\"contact3-form-btn mx-auto\">\n");
      out.write("\t\t\t\t\t\t\tPayer\n");
      out.write("\t\t\t\t\t\t</button>                                           \n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("\t</div>\n");
      out.write("        </section>\n");
      out.write("        <!-- Contact-->\n");
      out.write("        ");
      out.write("\n");
      out.write("\n");
      out.write("<!-- Contact-->\n");
      out.write("        <section class=\"contact-section bg-blue\" id=\"Contact\">\n");
      out.write("            <div class=\"container\">\n");
      out.write("                <div class=\"row\">\n");
      out.write("                    <div class=\"col-md-4 mb-3 mb-md-0\">\n");
      out.write("                        <div class=\"card py-4 h-100 \">\n");
      out.write("                            <div class=\"card-body text-center\">\n");
      out.write("                                <i class=\"fas fa-map-marked-alt text-primary mb-2\"></i>\n");
      out.write("                                <h4 class=\"text-uppercase m-0\">Address</h4>\n");
      out.write("                                <hr class=\"my-4\" />\n");
      out.write("                                <div class=\"small text-black-50\">Rue Peetermans 80, 4100 Seraing</div>\n");
      out.write("                            </div>\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"col-md-4 mb-3 mb-md-0\">\n");
      out.write("                        <div class=\"card py-4 h-100\">\n");
      out.write("                            <div class=\"card-body text-center\">\n");
      out.write("                                <i class=\"fas fa-envelope text-primary mb-2\"></i>\n");
      out.write("                                <h4 class=\"text-uppercase m-0\">Email</h4>\n");
      out.write("                                <hr class=\"my-4\" />\n");
      out.write("                                <div class=\"small text-black-50\">contact@inprespfm.com</div>\n");
      out.write("                            </div>\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"col-md-4 mb-3 mb-md-0\">\n");
      out.write("                        <div class=\"card py-4 h-100\">\n");
      out.write("                            <div class=\"card-body text-center\">\n");
      out.write("                                <i class=\"fas fa-mobile-alt text-primary mb-2\"></i>\n");
      out.write("                                <h4 class=\"text-uppercase m-0\">Phone</h4>\n");
      out.write("                                <hr class=\"my-4\" />\n");
      out.write("                                <div class=\"small text-black-50\">+32 412 34 56 78</div>\n");
      out.write("                            </div>\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("                <!--<div class=\"social d-flex justify-content-center\">\n");
      out.write("                    <a class=\"mx-2\" href=\"#!\"><i class=\"fab fa-twitter\"></i></a>\n");
      out.write("                    <a class=\"mx-2\" href=\"#!\"><i class=\"fab fa-facebook-f\"></i></a>\n");
      out.write("                    <a class=\"mx-2\" href=\"#!\"><i class=\"fab fa-github\"></i></a>\n");
      out.write("                </div>-->\n");
      out.write("            </div>\n");
      out.write("        </section>\n");
      out.write("        <!-- Footer-->\n");
      out.write("        <footer class=\"footer bg-blue small text-center text-white-50\"><div class=\"container\">Copyright © Claes Isen - Inpres PFM 2020</div></footer>");
      out.write("\n");
      out.write("        <!-- Bootstrap core JS-->\n");
      out.write("        <script src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>\n");
      out.write("        <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js\"></script>\n");
      out.write("        <!-- Third party plugin JS-->\n");
      out.write("        <script src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js\"></script>\n");
      out.write("        <!-- Core theme JS-->\n");
      out.write("        <script src=\"js/scripts.js\"></script>\n");
      out.write("    </body>\n");
      out.write("</html>\n");
      out.write("\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
