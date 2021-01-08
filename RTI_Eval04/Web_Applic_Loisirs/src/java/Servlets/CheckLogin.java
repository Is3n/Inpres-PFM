/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.Panier;
import Beans.User;
import Forms.ConnectionForm;
import Threads.ThreadBackground;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author isen0
 */
public class CheckLogin extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CheckLogin</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckLogin at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {       
        //this.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        response.sendRedirect("login.jsp");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if(request.getParameter("loginButton").equals("mdp"))
        {
            request.setAttribute("identifiantUser", request.getParameter("name"));
            this.getServletContext().getRequestDispatcher("/modifyPassword.jsp").forward(request, response);
        }
        else
        {
            ConnectionForm form = new ConnectionForm();
            form.verifierIdentifiants(request);

            request.setAttribute("connectionForm", form);

            if(form.getResultat().equals("connectionOK"))
            {
                User myUser = new User();
                myUser.setNom(request.getParameter("name"));
                myUser.setPass(request.getParameter("pass"));

                HttpSession session = request.getSession();
                
                session.setAttribute("currentUser", myUser);
                
                Panier myPanier = new Panier();
                
                session.setAttribute("currentPanier", myPanier);
                
                ThreadBackground th = new ThreadBackground(request);
                th.start();

                this.getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
            }
            else
            {
                this.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
