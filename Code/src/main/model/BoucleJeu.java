package main.model;

import main.view.FenetrePrincipale;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;

public class BoucleJeu implements Runnable{


    public static Thread myThread;
    public boolean running = true;
    public static long secSinceLastConnexion;
    private final FenetrePrincipale principale;
    private int nbSecUpdateSante = 5;
    private int nbSecUpdateBonheur = 5;
    private int nbSecUpdateNourriture = 5;
    private int nbSecUpdateEnergie = 5;
    private int nbSecUpdateHygiene = 5;
    private int nbSecUpdateDivertissement = 5;
    private int nbSecAutoSave = 5;
    private boolean isUpdateAllInitialized = false;


    public synchronized void start() {
        myThread = new Thread(){
            public void run(){
                playSound();

                long sec = 0;
                //Temps petit pour les test, c'est ici qu'il faut changer les valeurs de temps d'update



                while (running) {
                    System.out.println();  // ATTENTION CASSE TOUT SI ENLEVER WTF LES AMIS
                    if(principale.getIsInitialized()) {
                        principale.getJeu().getAvatar().setPrincipale(principale);
                        if(!isUpdateAllInitialized && principale.getContinuer()) {
                            updateAll();
                            isUpdateAllInitialized = true;
                        }
                        try {
                            myThread.sleep(1000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                        //Update;
                        if(sec % nbSecUpdateSante == 0) {
                            updateSante(-1);
                        }
                        if(sec % nbSecUpdateBonheur == 0) {
                            updateBonheur(-1);
                        }
                        if(sec % nbSecUpdateNourriture == 0) {
                            updateNourriture(-1);
                        }
                        if(sec % nbSecUpdateEnergie == 0) {
                            updateEnergie(-1);
                        }
                        if(sec % nbSecUpdateHygiene == 0) {
                            updateHygiene(-1);
                        }
                        if(sec % nbSecUpdateDivertissement == 0) {
                            updateDivertissement(-1);
                        }
                        if(sec % nbSecAutoSave == 0) {
                            principale.actionSauvegarde();
                        }

                        sec++;
                    }
                }

            }
        };
        myThread.start();
        running = true;
        run();
    }

    public void playSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Code/resources/music/Tamagotchi.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    /**
     * Compte le nombre d'update qui aurait du se passer pour un evenement donne pendant la deconnexion du joueur
     * @param secUpdate
     * @return
     */
    private long numberOfUpdate(long secUpdate) {
        long seconds = (BoucleJeu.secSinceLastConnexion / 1000) % 60;
        return seconds / secUpdate;
    }

    private void updateStatsWithStats() {
        Avatar avatar = principale.getJeu().getAvatar();

        //Sante
        if(avatar.getHygiene() <= 3 || avatar.getNourriture() <= 3) {
            //Faire baisser la sante plus rapidement
        }
        else if (avatar.getHygiene() > 3 && avatar.getHygiene() < 9 || avatar.getDivertissement() > 3 && avatar.getDivertissement() < 90) {
            //Faire augmenter la sante
        }

        //Bonheur
        if(avatar.getEnergie() <= 3 || avatar.getDivertissement() <= 3) {
            //Faire baisser le bonheur plus rapidement
        }
        else if(avatar.getEnergie() > 30 && avatar.getEnergie() < 9 || avatar.getDivertissement() > 3 && avatar.getDivertissement() < 9) {
            //Faire augmenter le bonheur
        }

        //Divertissement
        if(avatar.getNourriture() >= 9) {
            //Augmenter le divertissement
        }


    }

    /**
     * Update les stats du joueur après une reconnexion
     */
    private void updateAll() {
        System.out.println("UPDATE ALL");
        System.out.println("nbUpdate : " + (int) numberOfUpdate(nbSecUpdateSante));
        updateSante((int) numberOfUpdate(-nbSecUpdateSante));
        updateBonheur((int) numberOfUpdate(-nbSecUpdateBonheur));
        updateNourriture((int) numberOfUpdate(-nbSecUpdateNourriture));
        updateEnergie((int) numberOfUpdate(-nbSecUpdateEnergie));
        updateHygiene((int) numberOfUpdate(-nbSecUpdateHygiene));
        updateDivertissement((int) numberOfUpdate(-nbSecUpdateDivertissement));
    }

    public synchronized void stop() {
        running = false;
    }

    /**
     * Modifie la santé de l'avatar de la valeur passé en paramètre
     * @param modif - la valeur a jouter ou soustraire à la santé actuelle de l'avatar
     */
    private void updateSante(int modif) {

        int sante = this.principale.getJeu().getAvatar().getSante();        //Récupération de la santé actuelle
        this.principale.getJeu().getAvatar().setSante(sante + modif);       //Mise à jour de la valeur de santé de l'avatar
        //System.out.println("sante : " + sante);
    }

    /**
     * 
     * @param modif
     */
    private void updateBonheur(int modif) {

        int bonheur = this.principale.getJeu().getAvatar().getBonheur();
        this.principale.getJeu().getAvatar().setBonheur(bonheur + modif);
    }

    /**
     * 
     * @param modif
     */
    private void updateNourriture(int modif) {

        int nourriture = this.principale.getJeu().getAvatar().getNourriture();
        this.principale.getJeu().getAvatar().setNourriture(nourriture + modif);

    }

    /**
     * 
     * @param modif
     */
    private void updateEnergie(int modif) {

        int energie = this.principale.getJeu().getAvatar().getEnergie();
        this.principale.getJeu().getAvatar().setEnergie(energie + modif);

    }

    /**
     * 
     * @param modif
     */
    private void updateHygiene(int modif) {

        int hygiene = this.principale.getJeu().getAvatar().getHygiene();
        this.principale.getJeu().getAvatar().setHygiene(hygiene + modif);

    }

    /**
     * 
     * @param modif
     */
    private void updateDivertissement(int modif) {

        int divertissement = this.principale.getJeu().getAvatar().getDivertissement();
        this.principale.getJeu().getAvatar().setDivertissement(divertissement + modif);

    }

    /**
     * 
     */
    public void updateAllStats(){
        updateBonheur(0);
        updateSante(0);
        updateDivertissement(0);
        updateEnergie(0);
        updateHygiene(0);
        updateNourriture(0);
    }


    @Override
    public void run() {

    }

    public BoucleJeu(FenetrePrincipale principale) {
        this.principale = principale;
        this.principale.setBoucle(this);
        start();
    }

}


