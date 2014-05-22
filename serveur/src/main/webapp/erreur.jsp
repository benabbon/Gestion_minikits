<%-- 
    Document   : erreur
    Created on : 29 mars 2014, 22:37:16
    Author     : mouad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="bootstrap-1.2.0.min.css" />
        <title>Erreur</title>
    </head>
    <body>
        <%
            HttpSession sess = request.getSession();
            if (sess == null){
               response.sendRedirect("invalid.html");
			   return;
            }else{
                if(sess.getAttribute("utilisateur")==null){
					response.sendRedirect("invalid.html");
					return;
				}
                     
            }
        %>
        <div class="container">
            <jsp:include page="topBare.jsp" />
                            <div class="hero-unit">
                                <center><h4>Erreur</h4></center>
                                <% if(request.getParameter("cause")!=null){
                                   out.println("<center><h5>"+request.getParameter("cause")+"</h5></center>");
                                }
                                %>
                            </div>               
        </div>
    </body>
</html>
