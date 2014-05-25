<%-- 
    Document   : compte
    Created on : 7 avr. 2014, 19:50:51
    Author     : mouad
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="bootstrap-1.2.0.min.css" />
        <title>Compte</title>
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
				<ul>
					<%
						Class.forName("com.mysql.jdbc.Driver");
						Connection con;
						con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fablabDB","fablab","fablab");
						Statement stm = con.createStatement();
						ResultSet res = stm.executeQuery("select mail from admins where admin= \""+request.getSession().getAttribute("utilisateur")+"\"");
						if(!res.next()){
							response.sendRedirect("invalid.html");
							return;
						}
						if(res.getString(1)==null){
							out.println("<li><a href=\"ajouter_mail.jsp\">Ajouter une Adresse Mail</a></li>");
						}else{
							out.println("<li><a href=\"modifier_mail.jsp\">Modifier l'adresse Mail</a></li>");
						}
						
					%>
					<li><a href="modifier_password.jsp">Modifier le mot de passe</a></li>
				</ul>
			</div>
		</div>
    </body>
</html>
