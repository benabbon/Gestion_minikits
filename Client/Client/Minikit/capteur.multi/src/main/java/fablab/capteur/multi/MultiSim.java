/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fablab.capteur.multi;

import fablab.capteur.service.CapteurService;
import javax.swing.JFrame;

/**
 *
 * @author Thomas
 */
public class MultiSim  {
    
    CapteurService gestion;
    GUI gui;
    
    public void starting() {
        gui = new GUI(this);
        gui.setVisible(true);
        gui.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);    
    }
        
    public void stopping() {
        gui.setVisible(false);
    }

    void sendData(String string, int i) {
        gestion.sendData(string, i);
    }

    void sendRemainData() {
        gestion.sendRemainData();
    }
}
