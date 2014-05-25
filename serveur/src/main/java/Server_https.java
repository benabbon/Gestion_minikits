/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/




import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author nabilbenabbou1
 */
@WebServlet(name = "Serveur_https", urlPatterns = {"/receive_data"})
@MultipartConfig
public class Server_https extends HttpServlet {
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(404, "Opération interdite");
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(404, "Opération interdite");
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        System.out.println(System.getProperty("user.home") +"/mycmd"+request.getParameter("id")+".txt");
		PrintWriter out = response.getWriter();
		// receive results from the client
		if ("result".equals(request.getParameter("action"))) {
            System.out.println("Parameter Data for SaveResult " + request.getParameter("data"));
            Part filePart = request.getPart("data");
            
            InputStream filecontent = null;
            filecontent = filePart.getInputStream();
            
            File img = new File(System.getProperty("user.home") +"/mycmdResult"+request.getParameter("id")+".txt");
            FileOutputStream outCmdRes = new FileOutputStream(img);
                        
            int read = 0;
            final byte[] bytes = new byte[1024];
            while ((read = filecontent.read(bytes)) != -1) {
                outCmdRes.write(bytes, 0, read);
            }            
        }
        // get the command from the admin page
        else if ("saveCmd".equals(request.getParameter("action"))) {
            System.out.println("Parameter Data for SaveCmd " + request.getParameter("cmd"));
            //out.println("Thank you for your answer, talk to you later");  
            String cmd = request.getParameter("cmd");
            File img = new File(System.getProperty("user.home") +"/mycmd"+request.getParameter("id")+".txt");
            FileOutputStream outCmd = new FileOutputStream(img);
            
            outCmd.write(cmd.getBytes());
        }
        // send the results to the admin page
        else if ("getResults".equals(request.getParameter("action"))) {
            System.out.println("Parameter Data for GETResult " + request.getParameter("cmd"));
            //out.println("Thank you for your answer, talk to you later");  
            File img = new File(System.getProperty("user.home") +"/mycmdResult"+request.getParameter("id")+".txt");
            
            String s = "";
            InputStream in = null;
            
            in = new FileInputStream(img);
            int read = 0;
            final byte[] bytes = new byte[1024];
            
            while ((read = in.read(bytes)) != -1) {
                for (byte b : bytes) {
                    s = s+(char)b;
                }
            }
            out.print(s);
        }
        // send the command to the client
		else if ("get".equals(request.getParameter("action"))){
            System.out.println("Parameter Data Value for GetCMD" + request.getParameter("data"));
            String respond = handle2(request);
            if (respond != null || "".equals(respond)) {
                out.print(respond);
            }
        }else{
			String respond = handle(request.getParameter("data"));
			if (respond != null) {
				out.println(respond);            
				out.close();
			}
		}
    }
    public String handle2 (HttpServletRequest request) {
        
         File img = new File(System.getProperty("user.home") +"/mycmd"+request.getParameter("id")+".txt");		 
         String s = "";
         try {
             InputStream in = null;
             
             in = new FileInputStream(img);
             int read = 0;
             final byte[] bytes = new byte[1024];
             
             while ((read = in.read(bytes)) != -1) {
                 for (byte b : bytes) {
                     s = s+(char)b; 
                 }
             }
			 img.delete();
         } catch (IOException ex) {
             Logger.getLogger(Server_https.class.getName()).log(Level.SEVERE, null, ex);
         }
         return s;
    }
    public static String handle(String data) {
        String respond = null;
        if (data != null) {
            System.out.println(" Data in handle : " + data);
            if (data.startsWith("HB")) {
                handleHeartBeat(data.substring(3));
            }
            if (data.startsWith("DATA")) {
                handleData(data.substring(5));
            }
            if (data.startsWith("FIRST")) {
                System.out.println(" Data in first cnx : " + data.substring(6));
                respond = handleFirstConnection(data.substring(6));
            }
        }
        System.out.println(" Data in respond : "+respond);
        return respond;
    }
    
    public static void handleHeartBeat(String data) {
        System.out.println("hb received");
		int id = Integer.parseInt(data);
		ServeurDB.HB(id);
    }
    
    public static void handleData(String data) {
        System.out.println("data received");
		String[] param = data.split(":");
		if(param.length >= 4){
			System.out.println("ana f la boucle");
			int idMiniKit = Integer.parseInt(param[0]);
			int idCapteur = Integer.parseInt(param[1]);
			int donnee  = Integer.parseInt(param[2]);
			long dateDonnee = Long.parseLong(param[3]);
			ServeurDB.donnee(idMiniKit,idCapteur,donnee, dateDonnee);
			
		}
    }
    
    private static String handleFirstConnection(String data) {
        String[] param = data.split(":");
		if(param.length >= 2){
			String client = param[0];
			int nbCapteur = new Integer(param[1]);
			int id = ServeurDB.premiereCnx(client,nbCapteur);
			return id+"";
		}
		return null;
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}
