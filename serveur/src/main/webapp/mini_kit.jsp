<%-- 
    Document   : mini_kit
    Created on : 29 mars 2014, 03:18:29
    Author     : mouad
--%>

<%@page import="java.util.Date"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="refresh" content="5">
        <title>mini_kit <% out.print(request.getParameter("id")); %> </title>
        <link rel="stylesheet" type="text/css" href="bootstrap-1.2.0.min.css" />
    </head>
    <body>
        <%
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
					Class.forName("com.mysql.jdbc.Driver");
					Connection con;
					con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fablabDB","fablab","fablab");
					Statement stm = con.createStatement();
					ResultSet res = stm.executeQuery("select * from admins where admin = \""+request.getSession().getAttribute("utilisateur")+"\"");
					if(!res.next() && !((String)sess.getAttribute("utilisateur")).equals("admin")){
						response.sendRedirect("invalid.html");
						return;
					}
					if (request.getParameter("id")==null){
						response.sendRedirect("erreur.jsp?cause=\"page non disponible\"");
						return;
					}
					res=stm.executeQuery("select * from mini_kit where id_mk = "+request.getParameter("id"));
					if(!res.next()){
						response.sendRedirect("erreur.jsp?cause=\"page non disponible\"");
						return;	
					}
					res = stm.executeQuery("select * from droits where admin = \""+request.getSession().getAttribute("utilisateur")+"\" and id_mk = "+request.getParameter("id"));
					if(!res.next() && !((String)sess.getAttribute("utilisateur")).equals("admin")){
						response.sendRedirect("erreur.jsp?cause=\"page non disponible\"");
						return;	
					}
                    con.close();
                        
                }
            }
        %>
        <div class="container">
            <jsp:include page="topBare.jsp" />
            <div class="hero-unit">
                <center><h2>Les données du mini-kit <% out.print(request.getParameter("id")); %></h2></center>
                <br>
                <table class="zebra-striped">
                <thead>
                    <tr>
                        <th class="red">Identifiant du mini_kit</th>
                        <th class="yellow">Identifiant du capteur</th>
                        <th class="green">Date</th>
                        <th class="blue">Valeur</th>
                        <th class="brown">Est valide ?</th>
                    </tr>
                </thead>
                <tbody>
                        <%
                            try{
                                Class.forName("com.mysql.jdbc.Driver");
                                Connection con;
                                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fablabDB","fablab","fablab");
                                Statement stm = con.createStatement();
                                ResultSet res = stm.executeQuery("select * from donnees where id_mk="+request.getParameter("id"));
                                
								while(res.next()){
                                    out.println("<tr>");
                                    out.println("<td><a href=\"./mini_kit.jsp?id="+res.getString(1)+"\">"+res.getString(1)+"</a></td>");
                                    out.println("<td><a href=\"./capteur.jsp?id1="+res.getString(1)+"&id2="+res.getString(2)+"\">"+res.getString(2)+"</a></td>");
                                    Date d = new Date(res.getLong(3));
									out.println("<td>"+d.toGMTString()+"</td>");
                                    out.println("<td>"+res.getString(4)+"</td>");
                                    boolean estValide = res.getString(5).equals("1");
                                    out.println("<td>"+estValide+"</td>");
                                    out.println("</tr>");
                                }
                                con.close();
								
                            }catch(SQLException ex){
                                out.println("Impossible de se connecter à la base de données");
                            }catch(ClassNotFoundException ex){
                                out.println("Impossible de se connecter à la base de données");
                            }
                            
                            
                        %>
                </tbody>
            </table>
            </div>
        </div>
    </body>
</html>
