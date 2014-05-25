/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mouad
 */
@WebServlet(urlPatterns = {"/CheckUser"})
public class CheckUser extends HttpServlet {

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
        //PrintWriter out = response.getWriter();
        if(isValid(request.getParameter("login"),request.getParameter("password"))){
            HttpSession session= request.getSession(true);
            session.setAttribute("utilisateur",request.getParameter("login"));
            response.sendRedirect("admin.jsp");   
        }else{
            response.sendRedirect("invalid.html");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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

    public boolean isValid(String login, String pass){
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection con;
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fablabDB","fablab","fablab");
			Statement st = con.createStatement();
			ResultSet res = st.executeQuery("select password from admins where admin = \""+login+"\"");
			if (!res.next()){
				con.close();
				return false;
			}	
			if (BCrypt.checkpw(pass,res.getString(1))){
				con.close();
				return true;
			}	
			con.close();
			return false;
		} catch (SQLException ex) {
			return false;
		} catch (ClassNotFoundException ex) {
			return false;
		}
    }
       
}

