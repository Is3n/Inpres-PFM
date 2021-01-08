package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class reservation_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html>\n");
      out.write("<html lang=\"en\">\n");
      out.write("    <head>\n");
      out.write("        <meta charset=\"utf-8\" />\n");
      out.write("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\" />\n");
      out.write("        <meta name=\"description\" content=\"\" />\n");
      out.write("        <meta name=\"author\" content=\"\" />\n");
      out.write("        <title>Inpres PFM - Web Applic Reservation</title>\n");
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
            String redirectURL = "index.jsp";
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
      out.write("                        <li class=\"nav-item\"><a class=\"nav-link js-scroll-trigger\" href=\"#reservation\">Réservation</a></li>\n");
      out.write("                        <!--<li class=\"nav-item\"><a class=\"nav-link js-scroll-trigger\" href=\"#projects\">Projects</a></li>-->\n");
      out.write("                        <li class=\"nav-item\"><a class=\"nav-link js-scroll-trigger\" href=\"#Contact\">Contact</a></li>\n");
      out.write("                    </ul>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </nav>\n");
      out.write("        <!-- Masthead-->\n");
      out.write("        <header class=\"masthead\">\n");
      out.write("            <div class=\"container d-flex h-100 align-items-center\">\n");
      out.write("                <div class=\"mx-auto text-center\">\n");
      out.write("                    <h1 class=\"mx-auto my-0 text-uppercase\">Inpres PFM</h1>\n");
      out.write("                    <hr class=\"my-4\" />\n");
      out.write("                    <h2 class=\"text-white-50 mx-auto mt-2 mb-5\">Bienvenue ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ currentUser.nom }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write(" sur l'application de réservation d'InpresPFM !</h2>\n");
      out.write("                    <a class=\"btn btn-primary js-scroll-trigger\" href=\"#reservation\">Faire une réservation</a>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </header>\n");
      out.write("        <!-- Login-->\n");
      out.write("        <section class=\"about-section text-center\" id=\"reservation\">\n");
      out.write("            <div class=\"container-contact3\">\n");
      out.write("\t\t\t<div class=\"wrap-contact3\">\n");
      out.write("\t\t\t\t<form class=\"contact3-form validate-form\" method=\"post\" action=CheckLogin>\n");
      out.write("\t\t\t\t\t<span class=\"contact3-form-title\">\n");
      out.write("\t\t\t\t\t\tRéservation\n");
      out.write("\t\t\t\t\t</span>\n");
      out.write("\n");
      out.write("\t\t\t\t\t<div class=\"wrap-input3 validate-input\" data-validate=\"Reservation Id is required\">\n");
      out.write("\t\t\t\t\t\t<input class=\"input3\" type=\"text\" name=\"resId\" placeholder=\"Identifiant de la réservation\">\n");
      out.write("\t\t\t\t\t\t<span class=\"focus-input3\"></span>\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("\n");
      out.write("                                        <div class=\"wrap-input3 validate-input\" data-validate = \"Container Id is required\">\n");
      out.write("\t\t\t\t\t\t<input class=\"input3\" type=\"text\" name=\"idContainer\" placeholder=\"Identifiant du container\">\n");
      out.write("\t\t\t\t\t\t<span class=\"focus-input3\"></span>\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("                                        \n");
      out.write("                                        <div class=\"wrap-input3 input4-select\">\n");
      out.write("                                            <label for=\"societe\" style=\"color : #fff\">Société : </label>\n");
      out.write("\t\t\t\t\t\t\t<select class=\"selection-2\" id=\"societe\" name=\"societe\">\n");
      out.write("\t\t\t\t\t\t\t\t<option>Needed Services</option>\n");
      out.write("\t\t\t\t\t\t\t\t<option>eCommerce Bussiness</option>\n");
      out.write("\t\t\t\t\t\t\t\t<option>UI/UX Design</option>\n");
      out.write("\t\t\t\t\t\t\t\t<option>Online Services</option>\n");
      out.write("\t\t\t\t\t\t\t</select>\n");
      out.write("\t\t\t\t\t\t<span class=\"focus-input3\"></span>\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("                                    \n");
      out.write("                                        <div class=\"wrap-input3 validate-input\" data-validate = \"Nature contenu is required\">\n");
      out.write("\t\t\t\t\t\t<input class=\"input3\" type=\"text\" name=\"natureContenu\" placeholder=\"Nature du contenu\">\n");
      out.write("\t\t\t\t\t\t<span class=\"focus-input3\"></span>\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("                                    \n");
      out.write("                                        <div class=\"wrap-input3 validate-input\" data-validate = \"Capacité is required\">\n");
      out.write("\t\t\t\t\t\t<input class=\"input3\" type=\"text\" name=\"capacite\" placeholder=\"Capacité du container\">\n");
      out.write("\t\t\t\t\t\t<span class=\"focus-input3\"></span>\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("                                    \n");
      out.write("                                        <div class=\"wrap-input3 validate-input\" data-validate = \"Dangers is required\">\n");
      out.write("\t\t\t\t\t\t<input class=\"input3\" type=\"text\" name=\"dangers\" placeholder=\"Dangers du container\">\n");
      out.write("\t\t\t\t\t\t<span class=\"focus-input3\"></span>\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("                                        \n");
      out.write("\t\t\t\t\t<div class=\"wrap-input3 input4-select\">\n");
      out.write("\t\t\t\t\t\t<label for=\"destination\" style=\"color : #fff\">Destination : </label>\n");
      out.write("\t\t\t\t\t\t\t<select class=\"selection-2\" id=\"destination\" name=\"destination\">\n");
      out.write("\t\t\t\t\t\t\t\t<option>Needed Services</option>\n");
      out.write("\t\t\t\t\t\t\t\t<option>eCommerce Bussiness</option>\n");
      out.write("\t\t\t\t\t\t\t\t<option>UI/UX Design</option>\n");
      out.write("\t\t\t\t\t\t\t\t<option>Online Services</option>\n");
      out.write("\t\t\t\t\t\t\t</select>\n");
      out.write("\t\t\t\t\t\t<span class=\"focus-input3\"></span>\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("                                        \n");
      out.write("                                        <!--\n");
      out.write("\t\t\t\t\t<div class=\"wrap-input3 input3-select\">\n");
      out.write("\t\t\t\t\t\t<div>\n");
      out.write("\t\t\t\t\t\t\t<select class=\"selection-2\" name=\"budget\">\n");
      out.write("\t\t\t\t\t\t\t\t<option>Budget</option>\n");
      out.write("\t\t\t\t\t\t\t\t<option>1500 $</option>\n");
      out.write("\t\t\t\t\t\t\t\t<option>2000 $</option>\n");
      out.write("\t\t\t\t\t\t\t\t<option>3000 $</option>\n");
      out.write("\t\t\t\t\t\t\t</select>\n");
      out.write("\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t<span class=\"focus-input3\"></span>\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("                                        -->\n");
      out.write("\t\t\t\t\t<div class=\"container-contact3-form-btn\">\n");
      out.write("\t\t\t\t\t\t<button class=\"contact3-form-btn\">\n");
      out.write("\t\t\t\t\t\t\tValider\n");
      out.write("\t\t\t\t\t\t</button>\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t</form>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</div>\n");
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
