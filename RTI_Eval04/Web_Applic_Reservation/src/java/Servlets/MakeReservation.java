/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.Reservation;
import Forms.ReservationForm;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author isen0
 */
public class MakeReservation extends HttpServlet {

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
        ReservationForm form = new ReservationForm();
        form.makeReservation(request);
      
        StringTokenizer st = new StringTokenizer(form.getResultat(), "#");
        String resResult = st.nextToken();
        String resEmplacement = st.nextToken();
        String resId = st.nextToken();
        
        request.setAttribute("reservationResult", resResult);
        
        if(resResult.equals("ReservationOK"))
        {
            Reservation myReservation = new Reservation();
            myReservation.setIdReservation(resId);
            myReservation.setEmplacement(resEmplacement);
            myReservation.setContainer(request.getParameter("idContainer"));
            myReservation.setSociete(request.getParameter("societe"));
            myReservation.setNature(request.getParameter("natureContenu"));
            myReservation.setCapacite(request.getParameter("capacite"));
            myReservation.setDangers(request.getParameter("dangers"));
            myReservation.setDestination(request.getParameter("destination"));
            
            request.setAttribute("reservationInfos", myReservation);
            
            this.getServletContext().getRequestDispatcher("/reservation.jsp").forward(request, response);
        }
        else
        {
            this.getServletContext().getRequestDispatcher("/reservation.jsp").forward(request, response);
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
