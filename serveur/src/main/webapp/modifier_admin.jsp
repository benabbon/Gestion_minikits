<%-- 
    Document   : modifier_admin.jsp
    Created on : 30 mars 2014, 01:02:58
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
		
        <title>Modifier</title>
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
                    if (!((String)sess.getAttribute("utilisateur")).equals("admin") || request.getParameter("login")==null){
						response.sendRedirect("erreur.jsp?cause=\"page non disponible\"");
						return;
					}
                        
                }
            }
		%>
		<div class="container">
			<jsp:include page="topBare.jsp" />
			<div class="hero-unit">
				<% out.println("<center><h2>Modification des droits d'accées de l'admin:&nbsp &nbsp"+request.getParameter("login")+"</h2></center>");%>
				<% out.println("<form method=\"POST\" action=\"./modifier?login="+request.getParameter("login")+"\">"); %>
					<div class="clearfix">
						<label>Login :</label>
						<label name="login"><kbd>
							<% out.println(request.getParameter("login"));%>
							</kbd>
						</label>
					</div>
					<div class="clearfix">
						<label>Liste des mini_kits :</label>
						<div class="input">
							<ul class="inputs-list">
								<%  Class.forName("com.mysql.jdbc.Driver");
									Connection con;
									con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fablabDB","fablab","fablab");
									Statement stm = con.createStatement();
									ResultSet res = stm.executeQuery("select count(*) from mini_kit");
									res.next();
									int nb_mini_kits = (int)res.getInt(1);
									boolean est_admin = false;
									for (int i = 1; i<=nb_mini_kits;i++){
										res = stm.executeQuery("select id_mk from droits where id_mk="+i+" and admin=\""+request.getParameter("login")+"\"");
										if(!res.next())
											out.println("<li><label><input type=\"checkbox\" name=\"mini_kit"+i+"\"><span>&nbsp &nbsp mini-kit &nbsp"+i+"</span></label></li>");
										else{
											est_admin = true;
											out.println("<li><label><input type=\"checkbox\" checked=\"checked\" name=\"mini_kit"+i+"\"><span>&nbsp &nbsp mini-kit &nbsp"+i+"</span></label></li>");
										}
											
									}
									con.close();
									
								%>
							</ul>
						</div>
					</div>
					<div class="clearfix">
						<label></label>
						<% out.println("<input type=\"submit\" class=\"btn primary\" value=\"Valider\">");%>
						<a href="administrateur.jsp" class="btn primary">Annuler</a>
					</div>		
				</form>
				
				
			</div>
		</div>
    </body>
</html>
