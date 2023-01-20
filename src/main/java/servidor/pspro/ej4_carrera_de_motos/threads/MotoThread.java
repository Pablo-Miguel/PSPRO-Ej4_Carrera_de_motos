/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor.pspro.ej4_carrera_de_motos.threads;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @author Dam
 */
public class MotoThread extends Thread {
    
    private Random rnd = new Random();
    private String name;
    private JProgressBar progressBar;
    private JLabel lblLoops, lblState;
    private int tic, avance, laps;
    private boolean stop;
    private ArrayList<MotoThread> podio;
    
    public MotoThread(String name, JProgressBar progressBar, JLabel lblLoops, JLabel lblState, int tic, int avance, int laps, ArrayList<MotoThread> podio) {
        this.name = name;
        this.progressBar = progressBar;
        this.lblLoops = lblLoops;
        this.lblState = lblState;
        this.tic = tic;
        this.avance = avance;
        this.laps = laps;
        this.podio = podio;
        stop = false;
        lblLoops.setText(lap + "/" + laps + " Laps");
        lblState.setText("Waiting");
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
    
    int lap = 0;
    @Override
    public void run() {
//        motoRun();
        
//        while(progress < progressBar.getMaximum()){
//            if(!stop){
//                
//                try {
//
//                    progress += rnd.nextInt(5) + 1; //[1, 5]
//
//                    progressBar.setValue(progress);
//
//                    Thread.sleep(tic);
//
//                } catch (InterruptedException ex) { }
//            }
//        }
        
        lblState.setText("Running");
        while(lap < laps && !isInterrupted()){
            int progress = 0;
            while(progress < progressBar.getMaximum() && !isInterrupted()){
                try {

                    progress += rnd.nextInt(5) + 1; //[1, 5]

                    progressBar.setValue(progress);

                    Thread.sleep(tic);
                    
                    if(isInterrupted()){
                        System.out.println(isInterrupted());
                    }

                } catch (InterruptedException ex) { }
            }

            lap++;
            lblLoops.setText(lap + "/" + laps + " Laps");
            
            if(isInterrupted()){
                System.out.println(isInterrupted());
            }
        }
        
        setPodium();
        
    }
    
    private synchronized void setPodium(){
        podio.add(this);
        if(podio.indexOf(this)==0){
            lblState.setText("Winner");
        } else {
            lblState.setText("Loser");
        }
    }
    
    private synchronized void motoRun(){
        int progress = 0;
        while(progress < progressBar.getMaximum()){
            while(stop){
                try {
                    wait();
                } catch (InterruptedException ex) { }
            }
            try {

                progress += rnd.nextInt(5) + 1; //[1, 5]

                progressBar.setValue(progress);

                Thread.sleep(tic);
                
                notify();

            } catch (InterruptedException ex) { }
        }
    }
    
    
}
