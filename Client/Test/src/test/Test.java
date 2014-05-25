/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

/**
 *
 * @author toto
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        ConfigImpl config = new ConfigImpl();
        RealConnection connection = new RealConnection(config);
        HeartBeat hb = new HeartBeat(connection, config);
        Buffer buffer = new Buffer(connection);
        Gestionnaire gestion = new Gestionnaire(buffer, config);
        MultiSim multi = new MultiSim(gestion);
        
        while (true) {}
    }
    
}
