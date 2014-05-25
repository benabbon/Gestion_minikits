/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fablab.connection;

import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mouad
 */
public class HBControl {
	private static int frequence = 10000;
	public static  void main(String [] args){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con;
			while(true){
				System.out.println("boucle Controle HB");
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fablabDB","fablab","fablab");
				Statement st = con.createStatement();
				ResultSet res = st.executeQuery("select * from mini_kit");
				Date d = new Date();
				while(res.next()){
					System.out.println("boucle dial les minikits");
					Date date = res.getTimestamp(4);
					Date aux = new Date(date.getTime());
					aux.setSeconds(aux.getSeconds()+3*frequence/1000);
					if(aux.before(d)){
						System.out.println("3 hb majawch");
						Statement s1 = con.createStatement();
						ResultSet r1 = s1.executeQuery("select admin from droits where id_mk = "+res.getInt(1));
						while(r1.next()){
							Statement s2 = con.createStatement();
							ResultSet r2 = s2.executeQuery("select mail, nbHeartBeat from admins where admin = \""+r1.getString(1)+"\"");
							if(r2.next()){
								aux = new Date(date.getTime());
								aux.setSeconds(aux.getSeconds()+r2.getInt(2)*frequence/1000);
								Date aux2 = new Date(date.getTime());
								aux2.setSeconds(aux.getSeconds()+(r2.getInt(2)+1)*frequence/1000);
								if(aux.before(d) && aux2.after(d)){
									String message = " le mini_kit id = "+res.getString(1) + " n'a pas envoyé "+r2.getInt(2)+" heart beat";
									ServeurDB.sendEmail(r2.getString(1), message);
								}
							}
						}
						Statement s3 = con.createStatement();
						ResultSet r3 = s3.executeQuery("select mail, nbHeartBeat from admins where admin = \"admin\"");
						if(r3.next()){
							System.out.println("kanchof l'admin");
							aux = new Date(date.getTime());
							Date aux2 = new Date(date.getTime());
							System.out.println(aux.toGMTString());
							System.out.println(aux2.toGMTString());
							aux.setSeconds(aux.getSeconds()+r3.getInt(2)*frequence/1000);
							aux2.setSeconds(aux2.getSeconds()+1+(r3.getInt(2)+1)*frequence/1000);
							System.out.println(d.toGMTString());
							System.out.println(aux.toGMTString());
							System.out.println(aux2.toGMTString());
							if(aux.before(d) && aux2.after(d)){
								System.out.println("hana ghansift");
								String message = " le mini_kit id = "+res.getString(1) + " n'a pas envoyé "+r3.getInt(2)+" heart beat";
								ServeurDB.sendEmail(r3.getString(1), message);
							}
						}
					}
					
				}
				//System.out.println("hala");
				con.close();
				Thread.sleep(frequence);
			}
		} catch (InterruptedException ex) {
			Logger.getLogger(HBControl.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(HBControl.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(HBControl.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
