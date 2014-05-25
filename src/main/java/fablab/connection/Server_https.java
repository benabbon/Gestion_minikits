///*
//* To change this license header, choose License Headers in Project Properties.
//* To change this template file, choose Tools | Templates
//* and open the template in the editor.
//*/
//
//package servlets;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// *
// * @author nabilbenabbou1
// */
//@WebServlet(name = "Server_https", urlPatterns = {"/receive_data"})
//public class Server_https extends HttpServlet {
//    
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.sendError(404, "Opération interdite");
//    }
//    
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.sendError(404, "Opération interdite");
//    }
//    
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        String respond = handle(request.getParameter("data"));
//        if (respond != null) {
//            out.println(respond);            
//            out.close();
//        }
//    }
//    
//    public static String handle(String data) {
//        String respond = null;
//        if (data != null) {
//            System.out.println(" Data in handle : " + data);
//            if (data.startsWith("HB")) {
//                handleHeartBeat(data.substring(3));
//            }
//            if (data.startsWith("DATA")) {
//                handleData(data.substring(5));
//            }
//            if (data.startsWith("FIRST")) {
//                System.out.println(" Data in first cnx : " + data.substring(6));
//                respond = handleFirstConnection(data.substring(6));
//            }
//        }
//        System.out.println(" Data in respond : "+respond);
//        return respond;
//    }
//    
//    public static void handleHeartBeat(String data) {
//        System.out.println("Received HeartBeat: " + data);
//    }
//    
//    public static void handleData(String data) {
//        System.out.println("Received Data: " + data);
//    }
//    
//    private static String handleFirstConnection(String data) {
//        String[] param = data.split(":");
//        if(param.length >= 2){
//            String client = param[0];
//            int nbCapteur = new Integer(param[1]);
//            int id = ServeurDB.premiereCnx(client,nbCapteur);
//            return id+"kjerkj";
//        }
//        return null;
//    }
//    
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//    
//}
