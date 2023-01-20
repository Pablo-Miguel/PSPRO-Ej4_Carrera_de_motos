/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor.pspro.ej4_carrera_de_motos.threads;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;

/**
 *
 * @author Dam
 */
public class MotoThread implements Runnable {
    
    private String name;
    private JProgressBar progressBar;
    private int tic, avance;
    private boolean stop;
    
    public MotoThread(String name, JProgressBar progressBar, int tic, int avance) {
        this.name = name;
        this.progressBar = progressBar;
        this.tic = tic;
        this.avance = avance;
        stop = false;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
    
    int progress = 0;
    @Override
    public void run() {
        
        while(progress < progressBar.getMaximum()){
            if(!stop){
                try {
                
                    progress += avance;

                    progressBar.setValue(progress);

                    Thread.sleep(tic);

                } catch (InterruptedException ex) {
                    Logger.getLogger(MotoThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        
    }
    
    

}
