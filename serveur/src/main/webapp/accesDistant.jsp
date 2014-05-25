
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<%@ page import="javax.servlet.http.HttpUtils,java.util.Enumeration" %>
<%@ page import="java.lang.management.*" %>
<%@ page import="java.util.*" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="bootstrap-1.2.0.min.css" />
        <title>Remote Shell</title>
    </head>
    <body onload="refreshResult(<% out.print(request.getParameter("id")); %>)">
       
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
			<h2>Commands</h2>
 
 
<form action="javascript:sendCmd(<% out.print(request.getParameter("id")); %>)">
    
    <input type="text" name="cmd" style="width:70%;" id="cmd" placeholder="Insert your command here, If you want multiple commands, divide them using ;" required>
    <INPUT type="submit">
</form>
<div id="charging"></div>
<br/>

<H3>Results</H3>
<!--button onclick="refreshResult()">Refresh the results</button-->
<br/>
<br/>
<hr/>
<pre id="result" style="background:black; color:white;"> 
    
</pre>
<hr/>	
			</div>
		</div>
	</body>
	<SCRIPT type="text/javascript">
    function sendCmd(mid)
    {
        var xmlhttp;
        if (window.XMLHttpRequest)
        {// code for IE7+, Firefox, Chrome, Opera, Safari
            xmlhttp=new XMLHttpRequest();
        }
        else
        {// code for IE6, IE5
            xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange=function(id)
        {
            if (xmlhttp.readyState==4 && xmlhttp.status==200)
            {
                document.getElementById("charging").innerHTML='';
				document.getElementById("cmd").value='';
            }
            else if (xmlhttp.readyState==1 ) 
            {
                document.getElementById("charging").innerHTML= '<br><br><br><center><img src="/images/loader.gif"/></center>';
            }
        }
        var cmd = document.getElementById("cmd").value;
        xmlhttp.open("POST","receive_data?id="+mid+"&action=saveCmd&t="+Math.random()+"&cmd="+cmd,true);
        xmlhttp.send();
    }
    
    function refreshResult(mid)
    {
        var xmlhttp;
        if (window.XMLHttpRequest)
        {// code for IE7+, Firefox, Chrome, Opera, Safari
            xmlhttp=new XMLHttpRequest();
        }
        else
        {// code for IE6, IE5
            xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange=function(id)
        {
            if (xmlhttp.readyState==4) {
                if (xmlhttp.status==200) {                
                    document.getElementById("result").innerHTML=xmlhttp.responseText;
                }            
                refreshResult(mid);      
            }
        }
        
        xmlhttp.open("POST","receive_data?id="+mid+"&action=getResults&t="+Math.random(),true);
        xmlhttp.send();
    }
</script>
</html>
		