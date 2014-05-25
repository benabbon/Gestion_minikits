/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
@WebServlet(urlPatterns = {"/ModifierPassWord"})
public class ModifierPassWord extends HttpServlet {

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
		HttpSession sess = request.getSession();
		if (sess == null){
			response.sendRedirect("invalid.html");
			return;
		}    
        else{
            if(sess.getAttribute("utilisateur")==null){
				response.sendRedirect("invalid.html");
				return;
			}
            else{
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fablabDB","fablab","fablab"); 
					Statement st = con.createStatement();
					ResultSet res = st.executeQuery("select * from admins where admin = \""+sess.getAttribute("utilisateur")+"\"");
					if(!res.next())
						response.sendRedirect("erreur.jsp");
					String pass = res.getString(2);
					if(request.getParameter("ancien_pass").equals("") || request.getParameter("nouveau_pass").equals("") || request.getParameter("nouveau_pass2").equals("")){
						response.sendRedirect("modifier_password.jsp?cause=1");
						return;
					}
					if(!BCrypt.checkpw(request.getParameter("ancien_pass"),pass)){
						response.sendRedirect("modifier_password.jsp?cause=2");
						return;
					}
					if(!request.getParameter("nouveau_pass").equals(request.getParameter("nouveau_pass2"))){
						response.sendRedirect("modifier_password.jsp?cause=3");
						return;
					}
					st.executeUpdate("update admins set password=\""+BCrypt.hashpw(request.getParameter("nouveau_pass"),BCrypt.gensalt())+"\" where admin = \""+sess.getAttribute("utilisateur")+"\"");
					con.close();
					response.sendRedirect("compte.jsp");
				} catch (SQLException ex) {
					response.sendRedirect("erreur.jsp?cause=update");
				} catch (ClassNotFoundException ex) {
					response.sendRedirect("erreur.jsp");
				}
				
            }
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
		response.sendRedirect("erreur.jsp");
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

}
