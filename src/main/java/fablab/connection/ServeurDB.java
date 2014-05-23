package fablab.connection;



import java.sql.*;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mouad
 */
public class ServeurDB {
	// cette methode est applee lors de la premiere connexion du minikit avec le serveur
	// le paramètre client c'est le nom du client, et nbCapteur c'est le nombre du capteur
	// la methode retourne l'identifiant du nouveau minikit ( id > 0)
	// si la valeur retournee est 0 alors : erreur lors de l'ajout du minikit à la base de donnees
	public static int premiereCnx(String client, int nbCapteur){
		if (client == null || nbCapteur == 0)
			return 0;
		Date date = new Date();
		long time = 0;
		long a = (long)Math.pow(10,10)*(date.getYear()+1900);
		long m = (long)Math.pow(10,8)*(date.getMonth()+1);
		long j = (long)Math.pow(10,6)*date.getDate();
		long h = (long)Math.pow(10,4)*date.getHours();
		long mm = (long)Math.pow(10,2)*date.getMinutes();
		time = a+m+j+h+mm+date.getSeconds();
		String query = "insert into mini_kit values(default , \""+client+"\" , "+ nbCapteur+" ,"+time+" )";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con;
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fablabDB","fablab","fablab");
			Statement st = con.createStatement();
			st.executeUpdate(query);
			ResultSet res = st.executeQuery("select max(id_mk) from mini_kit where client = \""+client+"\"");
			int id = 0;
			if(res.next())
				id = res.getInt(1);
			if (id == 0)
				return 0;
			for (int i = 1; i <= nbCapteur; i++){
				st.executeUpdate("insert into validite values ("+id+", "+i+", null, null,0,"+date.getTime()+")");
			}
			con.close();
			return id;
		} catch (ClassNotFoundException ex) {
			return 0;
		} catch (SQLException ex) {
			return 0;
		}
		
	}
	
	public static boolean setValidite(int idMiniKit, int idCapteur, Integer min, Integer max){
		if (min != null && max != null && min >= max)
			return false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con;
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fablabDB","fablab","fablab");
			Statement st = con.createStatement();
			ResultSet res = st.executeQuery("select nb_capteurs from mini_kit where id_mk = "+idMiniKit);
			if(!res.next())
				return false;
			if (idCapteur > res.getInt(1))
				return false;
			st.executeUpdate("update validite set val_min = "+ min + ", val_max = "+max +" where id_mk = "+ idMiniKit+" and id_capteur = "+ idCapteur);
			con.close();
			return true;
		} catch (ClassNotFoundException ex) {
			return false;
		} catch (SQLException ex) {
			return false;
		}
		
	}
	
	public static boolean HB(int idMiniKit){
		Date date = new Date();
		long time = 0;
		long a = (long)Math.pow(10,10)*(date.getYear()+1900);
		long m = (long)Math.pow(10,8)*(date.getMonth()+1);
		long j = (long)Math.pow(10,6)*date.getDate();
		long h = (long)Math.pow(10,4)*date.getHours();
		long mm = (long)Math.pow(10,2)*date.getMinutes();
		time = a+m+j+h+mm+date.getSeconds();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con;
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fablabDB","fablab","fablab");
			Statement st = con.createStatement();
			ResultSet res = st.executeQuery("select * from mini_kit where id_mk = "+ idMiniKit);
			if(!res.next()){
				con.close();
				return false;
			}
			st.executeUpdate("update mini_kit set date_derniere_connexion = "+ time +" where id_mk = "+idMiniKit);
			con.close();
			return true;
		} catch (ClassNotFoundException ex) {
			return false;
		} catch (SQLException ex) {
			return false;
		}
	}
	
	public static boolean donnee(int idMiniKit, int idCapteur, int donnee, long dateDonnee){
		Date date = new Date();
		long time = 0;
		long a = (long)Math.pow(10,10)*(date.getYear()+1900);
		long m = (long)Math.pow(10,8)*(date.getMonth()+1);
		long j = (long)Math.pow(10,6)*date.getDate();
		long h = (long)Math.pow(10,4)*date.getHours();
		long mm = (long)Math.pow(10,2)*date.getMinutes();
		time = a+m+j+h+mm+date.getSeconds();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con;
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fablabDB","fablab","fablab");
			Statement st = con.createStatement();
			ResultSet res = st.executeQuery("select * from validite where id_mk = "+ idMiniKit+" and id_capteur = " + idCapteur);
			if(!res.next()){
				con.close();
				return false;
			}
			boolean valide = false;
			valide = (res.getString(3)== null || donnee >= res.getInt(3)) &&(res.getString(4)==null || donnee <= res.getInt(4));
			st.executeUpdate("update mini_kit set date_derniere_connexion = "+ time +" where id_mk = "+idMiniKit);
			PreparedStatement s = con.prepareStatement("insert into donnees values ("+idMiniKit+" , "+ idCapteur+" , "+ dateDonnee +" , "+donnee+" ,?)");
			s.setBoolean(1, valide);
			s.executeUpdate();
			PreparedStatement ss = con.prepareStatement("update validite set dateDerniereDonnee = ? where id_mk = ? and id_capteur = ?");
			ss.setLong(1, dateDonnee);
			ss.setInt(2,idMiniKit);
			ss.setInt(3, idCapteur);
			ss.executeUpdate();
			if(!valide){
				PreparedStatement s1 = con.prepareStatement("select nbInvalide from validite where id_mk=? and id_capteur=?");
				s1.setInt(1, idMiniKit);
				s1.setInt(2,idCapteur);
				ResultSet r1 = s1.executeQuery();
				if(r1.next()){
					int nbInvalide = r1.getInt(1);
					nbInvalide ++;
					Statement s2 = con.createStatement();
					s2.executeUpdate("update validite set nbInvalide = "+nbInvalide+" where id_mk = "+idMiniKit+" and id_capteur = "+idCapteur);
					Statement s3 = con.createStatement();
					ResultSet r3 = s3.executeQuery("select admin from droits where id_mk = "+ idMiniKit);
					while(r3.next()){
						Statement s4 = con.createStatement();
						ResultSet r4 = s4.executeQuery("select mail from admins where nbDonneeInvalide = "+nbInvalide+" and admin = \""+r3.getString(1)+"\"");
						if(r4.next()){
							String message = "Le capteur d'identifiant :"+idCapteur+", du minikit d'identifiant :"+idMiniKit+" a généré "+nbInvalide+" de données invalides";
							sendEmail(r4.getString(1),message+ " http://localhost:8080/fablab/InitialiserDonneesInvalides?idMiniKit="+idMiniKit+"&idCapteur="+idCapteur);
						}
						
					}
					Statement s5 = con.createStatement();
					ResultSet r5 = s5.executeQuery("select nbDonneeInvalide,mail from admins where admin =\"admin\"");
					if(r5.next()){
						if(r5.getInt(1)==nbInvalide){
							String message = "Le capteur d'identifiant :"+idCapteur+", du minikit d'identifiant :"+idMiniKit+" a généré "+nbInvalide+" de données invalides";
							System.out.println(message);
							System.out.println(r5.getString(2));
							sendEmail(r5.getString(2),message + " http://localhost:8080/fablab/InitialiserDonneesInvalides?idMiniKit="+idMiniKit+"&idCapteur="+idCapteur);
						}
					}
				}
			}
			
			con.close();
			return true;
		} catch (ClassNotFoundException ex) {
			return false;
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		
	}
	
	public static boolean sendEmail(String adresse, String mail){
		try {
			System.out.println(" Email");
			EmailUtility.sendEmail("smtp.gmail.com","587", "fablab.gestion.mini.kits@gmail.com","fablab2014", adresse,"fablab", mail);
		} catch (MessagingException ex) {
			System.out.println("mochkila f lmail");
			return false;
		}
		//System.out.println("ghansift mail");
		 
		return true;
	}
	public static void main(String [] args){
		//System.out.println("la date : "+premiereCnx("mouad",5));
		//setValidite(5,2,null,8);
		//HB(5);
		//System.out.println(" haaaaaaa"+donnee(5,2,10));;
		//boolean sendEmail = sendEmail("mouad.elaaboudi@gmail.com","salam");
		//System.out.println("wach mcha  "+sendEmail);
		
	}
}
