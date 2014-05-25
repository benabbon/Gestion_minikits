/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import javax.swing.JFrame;

/**
 *
 * @author Thomas
 */
public class MultiSim  {
    
    Gestionnaire gestion;
    GUI_Capteurs gui;
    
    public void starting() {
        gui = new GUI_Capteurs(this);
        gui.setVisible(true);
        gui.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        gestion.sendValide(1, 0, 100);
        gestion.sendValide(2, 80, 200);
        gestion.sendValide(3, 0, 50);
        gestion.sendValide(4, -10, 0);
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

    public MultiSim(Gestionnaire gestion) {
        this.gestion=gestion;
        starting();
    }
    
    
}
