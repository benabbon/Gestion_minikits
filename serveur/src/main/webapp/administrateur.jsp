<%-- 
    Document   : administrateur
    Created on : 29 mars 2014, 04:05:10
    Author     : mouad
--%>

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
        <link rel="stylesheet" type="text/css" href="bootstrap-1.2.0.min.css" />
        <title>Administrateur</title>
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
                    if (!((String)sess.getAttribute("utilisateur")).equals("admin")){
						 response.sendRedirect("erreur.jsp?cause=\"page non disponible\"");
						 return;
					}
                       
                }
            }
        %>
        <div class="container">
            <jsp:include page="topBare.jsp" />
            <div class="hero-unit">
                <center><h2>La liste des administrateurs</h2></center>
                <br>
                <div>
                    <center><a href="ajouter_admin.jsp" class="btn primary">Ajouter un admin</a></center>
                </div>
                <br><br>
                <table class="zebra-striped">
                <thead>
                    <tr>
                        <th class="yellow">Login de l'administrateur</th>
                        <th class="green">Identifiants du mini-kit</th>
						<th class="green">E-mail</th>
                        <th>Modifier/Supprimer</th>
                    </tr>
                </thead>
                <tbody>
                        <%
                            try{
                                Class.forName("com.mysql.jdbc.Driver");
                                Connection con;
                                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fablabDB","fablab","fablab");
                                Statement stm = con.createStatement();
                                ResultSet res = stm.executeQuery("select * from admins");
                                while(res.next()){
									if(!res.getString(1).equals("admin")){
										out.println("<tr>");
										out.println("<td>"+res.getString(1)+"</td>");
										String droits = "";
										Statement st = con.createStatement();
										ResultSet r = st.executeQuery("select id_mk from droits where admin = \""+res.getString(1)+"\"");
										if (r.next())
											droits = droits + r.getInt(1);
										while(r.next()){
											droits = droits + ", " + r.getInt(1);
										}
										out.println("<td>"+droits+"</td>");
										out.println("<td>"+res.getString(3)+"</td>");
										out.println("<td><center><a href=\"supprimer_admin.jsp?login="+res.getString(1)+"\" class=\"btn info\">Supprimer</a>&nbsp<a href=\"modifier_admin.jsp?login="+res.getString(1)+"\" class=\"btn info\">Modifier</a></center></td>");
										out.println("</tr>");
									}
                                }
                                con.close();
                            }catch(SQLException ex){
                                out.println("erreur.jsp");
                            }catch(ClassNotFoundException ex){
                                out.println("erreur.jsp");
                            }
                            
                            
                        %>
                </tbody>
            </table>
                
            </div>
        </div>
    </body>
</html>
