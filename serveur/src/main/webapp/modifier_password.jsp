<%-- 
    Document   : modifier_password
    Created on : 8 avr. 2014, 02:09:05
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
        <title>Password</title>
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
				<% if (request.getParameter("cause")!= null && request.getParameter("cause").equals("1"))
					out.println("<h3><center>Tous les champs sont obligatoires</center></h3>");
					if (request.getParameter("cause")!= null && request.getParameter("cause").equals("2"))
					out.println("<h3><center>L'ancien mot de passe n'est pas correct</center></h3>");
					if (request.getParameter("cause")!= null && request.getParameter("cause").equals("3"))
					out.println("<h3><center>les deux saisies du nouveau mot de passe ne sont pas conformes</center></h3>");
				%>
				<form method="POST" action="./ModifierPassWord">
					<fieldset>
						<div class="clearfix">
							<label>Mot de passe actuel</label>
							<div class="input">
								<input name="ancien_pass" type="password"/>
							</div>
						</div>
						<div class="clearfix">
							<label>Nouveau mot de passe </label>
							<div class="input">
								<input name="nouveau_pass" type="password"/>
							</div>
						</div>
						<div class="clearfix">
							<label>Confirmer le nouveau mot de passe</label>
							<div class="input">
								<input name="nouveau_pass2" type="password"/>
							</div>
						</div>
						<div class="clearfix">
							<div class="input">
								<input type="submit" class="btn primary" value="Confrimer"/>
								<input type="reset" class="btn primary" value="Réinitialiser"/>
								<a href="compte.jsp" class="btn primary">Retour</a>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
    </body>
</html>
