
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
@WebServlet(urlPatterns = {"/ModifNbHeartBeatDonneesInvalides"})
public class ModifNbHeartBeatDonneesInvalides extends HttpServlet {

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
			throws ServletException, IOException, ClassNotFoundException {
		response.setContentType("text/html;charset=UTF-8");
		//PrintWriter out = response.getWriter();
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
					//Connection con = DBConn.getConnexion();
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fablabDB","fablab","fablab"); 
					Statement st = con.createStatement();
					ResultSet res = st.executeQuery("select * from admins where admin = \""+sess.getAttribute("utilisateur")+"\"");
					if(!res.next())
						response.sendRedirect("erreur.jsp?cause=select");
					st = con.createStatement();
					if(request.getParameter("nbHeartBeat")!= null )
						st.executeUpdate("update admins set nbHeartBeat = "+Integer.parseInt(request.getParameter("nbHeartBeat"))+" where admin = \""+sess.getAttribute("utilisateur")+"\"");
					if(request.getParameter("nbDonneesInvalides")!= null )
						st.executeUpdate("update admins set nbDonneeInvalide = "+Integer.parseInt(request.getParameter("nbDonneesInvalides"))+" where admin = \""+sess.getAttribute("utilisateur")+"\"");
					con.close();
					response.sendRedirect("admin.jsp");
				} catch (SQLException ex) {
					response.sendRedirect("erreur.jsp?cause=update");
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
		try {
			processRequest(request, response);
		} catch (ClassNotFoundException ex) {
			response.sendRedirect("erreur.jsp?cause=++++");
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
