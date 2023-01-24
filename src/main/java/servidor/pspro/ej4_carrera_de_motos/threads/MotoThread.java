/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor.pspro.ej4_carrera_de_motos.threads;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @author Dam
 */
public class MotoThread extends Thread {
    
    private Random rnd = new Random();
    private JProgressBar progressBar;
    private JLabel lblLoops, lblState;
    private int tic, avance, laps;
    private boolean stop;
    private ArrayList<MotoThread> podio;
    
    public MotoThread(String name, JProgressBar progressBar, JLabel lblLoops, JLabel lblState, int tic, int avance, int laps, ArrayList<MotoThread> podio) {
        super(name);
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
    
    public void suspender(){
        lblState.setText("Stopped");
        this.stop = true;
    }
    
    public synchronized void reanudar() {
        lblState.setText("Running");
        this.stop = false;
        notify();
    }
    
    int lap = 0;
    @Override
    public void run() {
        motoRun();
    }
    
    private synchronized void setPodium(){
        podio.add(this);
        if(podio.indexOf(this)==0){
            lblState.setText("1º: Winner");
        } else {
            lblState.setText((podio.indexOf(this) + 1) + "º: Loser");
        }
    }
    
    private synchronized void motoRun(){
        lblState.setText("Running");
        while(lap < laps){
            int progress = 0;
            while(progress < progressBar.getMaximum()){
                if(stop){
                    try {
                        wait();
                    } catch (InterruptedException ex) { }
                }
                
                try {

                    progress += rnd.nextInt(5) + 1; //[1, 5]

                    progressBar.setValue(progress);

                    Thread.sleep(tic);

                } catch (InterruptedException ex) { }
            }

            lap++;
            lblLoops.setText(lap + "/" + laps + " Laps");
        }
        
        setPodium();
    }
    
    
}
