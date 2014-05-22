<%-- 
    Document   : modifeir_mail
    Created on : 8 avr. 2014, 00:55:53
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
        <title>E-mail</title>
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
				<form method="POST" action="./AjouterMail">
					<fieldset>
						<div class="clearfix">
							<label size="60">Ancien E-mail</label>
							
							<label>
								<%
									Connection con;
									con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fablabDB","fablab","fablab");
									Statement stm = con.createStatement();
									ResultSet res = stm.executeQuery("select * from admins where admin = \""+request.getSession().getAttribute("utilisateur")+"\"");
									if(!res.next()){
										response.sendRedirect("erreur.jsp");
										return;
									}
									out.println("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"+res.getString(3));
									con.close();
								%>
							</label>
						</div>
						<div class="clearfix">
							<label>E-mail</label>
							<div class="input">
								<input name="email"/>
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
