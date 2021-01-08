/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.User;
import Forms.PasswordForm;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author isen0
 */
public class NewPassword extends HttpServlet {

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
        this.getServletContext().getRequestDispatcher("/reservation.jsp").forward(request, response);
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
        //System.out.println("Test Debut doPost getName : "+ request.getParameter("name"));
        
        PasswordForm form = new PasswordForm();
        form.verifierMdp(request);
        request.setAttribute("passwordForm", form);
                        
        if(form.getResultat().equals("pwdOK"))
        {
            User myUser = new User();
            myUser.setNom(request.getParameter("name"));
            myUser.setPass(request.getParameter("passFirst"));
            
            request.setAttribute("infosUserTmp", myUser);
            this.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        }
        else
        {
            request.setAttribute("identifiantUser", request.getParameter("name"));
            this.getServletContext().getRequestDispatcher("/modifyPassword.jsp").forward(request, response);
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
