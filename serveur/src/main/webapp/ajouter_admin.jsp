<%-- 
    Document   : ajouter_admin
    Created on : 29 mars 2014, 04:40:07
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
        <title>Ajouter un admin</title>
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
                <center><h2>Ajout d'un Admin</h2></center>
                <p>
                    <%
                            try{
                                Class.forName("com.mysql.jdbc.Driver");
                                Connection con;
                                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fablabDB","fablab","fablab");
                                Statement stm = con.createStatement();
                                ResultSet res = stm.executeQuery("select count(*) from mini_kit");
                                res.next();
                                int nb_mini_kits = (int)res.getInt(1);
                                con.close();
                                if(nb_mini_kits == 0){
                                    out.println("Impossible d'ajouter un admin : Aucun mini_kit dans la base de données");
                                }else{
                                    out.println("<form method=\"POST\" action=\"./Ajouter?nb_mini_kits="+nb_mini_kits+"\">");
                                    out.println("<fieldset>");
                                    out.println("<legend>Remplissez les champs au dessous</legend>");
                                    out.println("<div class=\"clearfix\">");
                                    out.println("<label>Login du nouveau Admin</label>");
                                    out.println("<div class=\"input\">");
                                    out.println("<input name=\"login\"/>");
                                    out.println("</div><br><br>");
                                    out.println("<div class=\"clearfix\">");
                                    out.println("<label>Password du nouveau Admin</label>");
                                    out.println("<div class=\"input\">");
                                    out.println("<input name=\"password\"/>");
                                    out.println("</div><br><br>");
                                    out.println("<div class=\"clearfix\">");
                                    out.println("<label>Liste des mini_kits</label>");
                                    out.println("<div class=\"input\">");
                                    out.println("<ul class=\"inputs-list\">");
                                    for(int i = 1; i<=nb_mini_kits;i++ ){
                                        out.println("<li><label><input type=\"checkbox\" name=\"mini_kit"+i+"\"><span>&nbsp &nbsp mini-kit &nbsp"+i+"</span></label></li>");
                                        
                                    }
                                    out.println("</ul>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("<div class=\"clearfix\">");
                                    out.println("<div class=\"input\">");
                                    out.println("<input type=\"submit\" value=\"Ajouter\" class=\"btn primary\">");
                                    out.println("<input type=\"reset\" value=\"Réinitialiser\" class=\"btn primary\">");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</form>");
                                }
                                
                            }catch(SQLException ex){
                                out.println("Impossible de se connecter à la base de données");
                            }catch(ClassNotFoundException ex){
                                out.println("Impossible de se connecter à la base de données");
                            }
                            
                            
                        %>
                </p>
            </div>
        </div>
    </body>
</html>
