/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
@WebServlet(urlPatterns = {"/InitialiserDonneesInvalides"})
public class InitialiserDonneesInvalides extends HttpServlet {

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
		try {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
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
					if (request.getParameter("idMiniKit") == null || request.getParameter("idCapteur") == null ){
						response.sendRedirect("erreur.jsp?cause=\"page non disponible\"");
						return;
					}
					
				}
			}
			Class.forName("com.mysql.jdbc.Driver");
			Connection con;
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fablabDB","fablab","fablab");
			if(!sess.getAttribute("utilisateur").equals("admin")){
				PreparedStatement s = con.prepareStatement("select * from droits where id_mk = ? and admin = ?");
				s.setInt(1, Integer.parseInt(request.getParameter("idMiniKit")));
				s.setString(2, sess.getAttribute("utilisateur").toString());
				ResultSet r = s.executeQuery();
				if(!r.next()){
					response.sendRedirect("erreur.jsp?cause=\"page non disponible\"");
					return;
				}
			}
			PreparedStatement s1 = con.prepareStatement("select nb_capteurs from mini_kit where id_mk = ?");
			s1.setInt(1, Integer.parseInt(request.getParameter("idMiniKit")));
			ResultSet r1 = s1.executeQuery();
			if(r1.next()){
				if(r1.getInt(1) < Integer.parseInt(request.getParameter("idCapteur"))){
					response.sendRedirect("erreur.jsp?cause=\"page non disponible\"");
					return;
				}
			}else{
				response.sendRedirect("erreur.jsp?cause=\"page non disponible\"");
				return;
			}
			PreparedStatement s2 = con.prepareStatement("update validite set nbInvalide = 0 where id_mk = ? and id_capteur = ?");
			s2.setInt(1,Integer.parseInt(request.getParameter("idMiniKit")));
			s2.setInt(2,Integer.parseInt(request.getParameter("idCapteur")));
			s2.executeUpdate();
			response.sendRedirect("admin.jsp");
		} catch (SQLException ex) {
			response.sendRedirect("erreur.jsp?cause=\"page non disponible\"");
			return;
		} catch (ClassNotFoundException ex) {
			response.sendRedirect("erreur.jsp?cause=\"page non disponible\"");
			return;
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

}
