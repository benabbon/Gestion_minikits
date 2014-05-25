/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package fablab.config.impl;


import fablab.config.service.Config;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.Scanner;
import java.util.TreeMap;

public class ConfigImpl implements Config{
    
    // Attributs
    private Path configFilePath;
    private final static Charset ENCODING = StandardCharsets.UTF_8;
    private String ipServer;
    private int idMinikit;
    private int nbCapteurs;
    private TreeMap<Integer, String> capteurs;
    private String nomParticulier;
    private String prenomParticulier;
    
    
    // Getter useful later
    public String getIPServer() {
        return ipServer;
    }
    public String getNomParticulier(){
        return nomParticulier;
    }
    public String getPrenomParticulier(){
        return prenomParticulier;
    }
    public int getIdMinikit(){
        return idMinikit;
    }
    public int getNbCapteurs(){
        return nbCapteurs;
    }
    public String getFoncCapteur(int numeroCapteur){
        return capteurs.get(numeroCapteur);
    }
    
    
    
    public void starting(){
        try {
            
            configFilePath = Paths.get("configMinikit.dat");
            capteurs = new TreeMap<Integer, String>();
            processLineByLine();
        } catch (Exception ex) {
            System.out.println("test debug");
            System.out.println(ex.toString()+"  "+ex.getMessage());
        }
    }
    
    
    
    /**
     * processing line by line config file
     * @throws Exception
     */
    private void processLineByLine() throws Exception {
        Scanner scanner =  new Scanner(configFilePath, ENCODING.name());
        while (scanner.hasNextLine()){
            processLine(scanner.nextLine());
        }
    }
    
    /**
     * method for processing lines
     *
     */
    
    private void processLine(String Line) throws Exception{
        //use a second Scanner to parse the content of each line
        Scanner scanner = new Scanner(Line);
        scanner.useDelimiter("#");
        if (scanner.hasNext()){
            //assumes the line has a certain structure
            String name = scanner.next();
            String value = scanner.next();
            defAttribut(name, value);
            
        }
        else {
            throw new Exception("problème Parsing format non respecté");
        }
    }
    
    private void defAttribut(String name,String value){
        if ( "IDMINIKIT".equals(name))
            idMinikit = Integer.parseInt(value);
        else if ( "NBCAPTOR".equals(name))
            nbCapteurs = Integer.parseInt(value);
        else if ("IPSERVER".equals(name))
            ipServer = value;
        else if("LASTNAME".equals(name))
            nomParticulier = value;
        else if("FIRSTNAME".equals(name))
            prenomParticulier = value;
        else if(name.startsWith("CAPTOR"))
            capteurs.put(Integer.parseInt(name.split("TOR")[1]), value);
        
        
    }
    
    
    public void writeFile() throws FileNotFoundException, IOException{
        File tempFile = new File("tempfile.txt");
        PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
        File originalFile = configFilePath.toFile();
        
        pw.println("IDMINIKIT#"+idMinikit);
        pw.println("NBCAPTOR#"+nbCapteurs);
        pw.println("IPSERVER#"+ipServer);
        pw.println("LASTNAME#"+nomParticulier);
        pw.println("FIRSTNAME#"+prenomParticulier);
        int i = 0;
        for (String func: capteurs.values()){
            pw.println("CAPTOR"+ i +"#" + func);
            i++;
        }
        pw.flush();
        pw.close();
        
        // Delete the original file
        if (!originalFile.delete()) {
            System.out.println("Could not delete file");
            return;
        }
        
        // Rename the new file to the filename the original file had.
        if (!tempFile.renameTo(originalFile))
            System.out.println("Could not rename file");
        
    }
    
    
    // Setters
    public void setIPServer(String ipServer) {
        this.ipServer = ipServer;
    }
    
    public void setNomParticulier(String nom) {
        this.nomParticulier = nom;
    }
    
    public void setPrenomParticulier(String prenom) {
        this.prenomParticulier = prenom;
    }
    
    public void setIdMinikit(int id) {
        this.idMinikit = id;
    }
    
    public void setNbCapteurs(int nbcapteurs) {
        this.nbCapteurs = nbcapteurs;
    }
    
    public void setFoncCapteur(int numeroCapteur, String func) {
        this.capteurs.replace(numeroCapteur, func);
    }
    
    
    
    
}
