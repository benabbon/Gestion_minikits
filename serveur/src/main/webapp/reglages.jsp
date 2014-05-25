
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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="bootstrap-1.2.0.min.css" />
        <title>réglages</title>
    </head>
    <body>
       
        <%
            int nbHeartBeat = 3;
            int nbDonneesInvalides = 3;
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
                                else{
                                 nbHeartBeat = res.getInt("nbHeartBeat");
                                 nbDonneesInvalides = res.getInt("nbDonneeInvalide");
                                 pageContext.setAttribute("nbHeartBeat",nbHeartBeat);
                                 pageContext.setAttribute("nbDonneeInvalide",nbDonneesInvalides);
                                }
				con.close();
            }
        %>
        <div class="container">
			<jsp:include page="topBare.jsp" />
            <div class="hero-unit">
                <h2>Modifications </h2>
                <form method="POST" action="./ModifNbHeartBeatDonneesInvalides">
					<fieldset>
						
						<div class="clearfix">
							<label>Nouveau nombre de heart Beat&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</label>
							
                                                        <div>&nbsp&nbsp
                                                        <select class="selectpicker" name="nbHeartBeat">
                                                        <c:forEach var="i" begin="3" end="20" step="1">
                                                        <c:if test="${(i != nbHeartBeat)}">
                                                            <option><c:out value="${i}"/></option>
                                                        </c:if>
                                                        <c:if test="${(i == nbHeartBeat)}">
                                                            <option selected><c:out value="${i}"/></option>
                                                        </c:if>
                                                        </c:forEach>
                                                          </select>
                                                        </div>
						</div>
                                                <div class="clearfix">
							<label>Nouveau nombre de données invalides&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</label>
                                                        <div>&nbsp&nbsp
                                                        <select class="selectpicker" name="nbDonneesInvalides">
                                                           <c:forEach var="i" begin="3" end="20" step="1">
                                                        <c:if test="${(i != nbDonneeInvalide)}">
                                                            <option><c:out value="${i}"/></option>
                                                        </c:if>
                                                        <c:if test="${(i == nbDonneeInvalide)}">
                                                            <option selected><c:out value="${i}"/></option>
                                                        </c:if>
                                                        </c:forEach>
                                                          </select>
                                                        </div>
						</div>
						<div class="clearfix">
							<div class="input">
								<input type="submit" class="btn primary" value="Valider"/>
								<input type="reset" class="btn primary" value="Réinitialiser"/>
							</div>
						</div>
					</fieldset>
				</form>
                <BR>
            </div>
        </div>
    </body>
</html>

