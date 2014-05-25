<%-- 
    Document   : admin
    Created on : 29 mars 2014, 01:04:36
    Author     : mouad
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>
<%@ page import="javax.servlet.http.HttpSession" %>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="refresh" content="11">
		<link rel="stylesheet" type="text/css" href="bootstrap-1.2.0.min.css" />
        <title>Admin</title>
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
				Class.forName("com.mysql.jdbc.Driver");
                Connection con;
                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fablabDB","fablab","fablab");
                Statement stm = con.createStatement();
				ResultSet res = stm.executeQuery("select * from admins where admin = \""+request.getSession().getAttribute("utilisateur")+"\"");
				if(!res.next()){
					response.sendRedirect("invalid.html");
					return;
				}
				con.close();
            }
        %>
        <div class="container">
			<jsp:include page="topBare.jsp" />
            <div class="hero-unit">
                <center><h2>La liste des mini kits</h2></center>
                <br>
                <table class="zebra-striped">
                <thead>
                    <tr>
                        <th class="red">Identifiant du mini_kit</th>
                        <th class="yellow">Client</th>
                        <th class="green">Nombre de capteurs</th>
                        <th class="blue">Date de la dernière connexion</th>
                    </tr>
                </thead>
                <tbody>
                        <%
                            try{
                                Class.forName("com.mysql.jdbc.Driver");
                                Connection con;
                                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fablabDB","fablab","fablab");
                                Statement stm = con.createStatement();
                                if(request.getSession().getAttribute("utilisateur").equals("admin")){
									ResultSet res = stm.executeQuery("select * from mini_kit");
									while(res.next()){
										out.println("<tr>");
										out.println("<td><a href=\"./mini_kit.jsp?id="+res.getInt(1)+"\">"+res.getInt(1)+"</a></td>");
										out.println("<td>"+res.getString(2)+"</td>");
										out.println("<td>"+res.getInt(3)+"</td>");
										out.println("<td>"+res.getString(4)+"</td>");
										out.println("<td><a href=\"accesDistant.jsp?id="+res.getInt(1)+"\" class=\"btn primary\">Acces distant</a>"+"</td>");
										out.println("</tr>");
									}
								}else{
									ResultSet res = stm.executeQuery("select id_mk from droits where admin = \""+request.getSession().getAttribute("utilisateur")+"\"");
									while(res.next()){
										Statement s = con.createStatement();
										ResultSet r = s.executeQuery("select * from mini_kit where id_mk = "+res.getInt(1));
										if(r.next()){
											out.println("<tr>");
											out.println("<td><a href=\"./mini_kit.jsp?id="+r.getInt(1)+"\">"+r.getInt(1)+"</a></td>");
											out.println("<td>"+r.getString(2)+"</td>");
											out.println("<td>"+r.getInt(3)+"</td>");
											out.println("<td>"+r.getString(4)+"</td>");
											out.println("</tr>");
										}
									}
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
