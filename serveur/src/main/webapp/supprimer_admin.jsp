<%-- 
    Document   : supprimer_admin
    Created on : 29 mars 2014, 23:16:43
    Author     : mouad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="bootstrap-1.2.0.min.css" />
        <title>Supprimer</title>
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
                <center><h2>Attention</h2></center>
                <center><h4>
                        Confirmer la suppression de l'admin:
                        <%
                            out.println("&nbsp &nbsp << "+request.getParameter("login")+" >>");
                        %>
                    </h4></center>
                <div><center>
                    
                    <%
                            out.println("<a href=\"./supprimer?login="+request.getParameter("login")+"\" class=\"btn primary\">Supprimer</a>");
                    %>
                        <a href="administrateur.jsp?" class="btn primary">Annuler</a>
                    </center></div>
            </div>
        </div>
    </body>
</html>
