

import java.util.Date;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
				st.executeUpdate("insert into validite values ("+id+", "+i+", null, null,0)");
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
			con.close();
			return true;
		} catch (ClassNotFoundException ex) {
			return false;
		} catch (SQLException ex) {
			System.out.println("erreur sql f donnee");
			return false;
		}
		
	}
	public static void main(String [] args){
		//System.out.println("la date : "+premiereCnx("mouad",5));
		//setValidite(5,2,null,8);
		//HB(5);
		//System.out.println(" haaaaaaa"+donnee(5,2,10));;
		
	}
}
