<%-- 
    Document   : topBare
    Created on : 7 avr. 2014, 18:30:10
    Author     : mouad
--%>

<div class="topbar">
                <div class="fill">
                    <div class="container">
                        <h3><a href="admin.jsp">Accueil</a></h3>
                        <ul>
                            <% if(((String)request.getSession().getAttribute("utilisateur")).equals("admin")){
                        out.println("<li><a href=\"administrateur.jsp\">Administrateur</a></li>");
                    }
                    %>
					<li><a href="compte.jsp">Compte</a></li>
					<li><a href="reglages.jsp">Réglages</a></li>
                        </ul>
                        <ul class="nav secondary-nav">
                            <li><a href="./seDeconnecter"> Se déconnecter </a></li>   
                        </ul>
                    </div>
                </div>
</div>
