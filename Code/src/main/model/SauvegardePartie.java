package main.model;

import main.view.FenetrePrincipale;

import java.io.*;
import java.lang.reflect.AnnotatedArrayType;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SauvegardePartie implements Serializable {
    private Date dateFinSession;
    private Date tempsJeu;
    private String nomJoueur;
    private String nomAvatar;
    private String typeAvatar;
    private int santeAvatar;
    private int nourritureAvatar;
    private int energieAvatar;
    private int hygieneAvatar;
    private int divertissementAvatar;
    private int bonheurAvatar;

    public SauvegardePartie(String nomJoueur, Avatar avatar, Date tempsJeu) throws IOException {
        this.nomJoueur = nomJoueur;
        this.nomAvatar = avatar.getNom();
        this.typeAvatar = avatar.getType();
        this.tempsJeu = tempsJeu;
        this.santeAvatar = avatar.getSante();
        this.nourritureAvatar = avatar.getNourriture();
        this.energieAvatar = avatar.getEnergie();
        this.hygieneAvatar = avatar.getHygiene();
        this.divertissementAvatar = avatar.getDivertissement();
        this.bonheurAvatar = avatar.getBonheur();

        long millis = System.currentTimeMillis();
        this.dateFinSession = new Date(millis);
        writeJSON();

    }

    public SauvegardePartie(String nom) {
        try {
            readJson(nom);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void writeJSON() throws IOException {
        String filename = "Sauvegarde1-" + nomJoueur + "-" + nomAvatar + ".json";
        FileOutputStream file = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(this);
        out.close();
        file.close();

        System.out.println("Object has been serialized");
    }



    private void readJson(String nom) throws IOException, ClassNotFoundException {

            FileInputStream file = new FileInputStream(nom);
            ObjectInputStream in = new ObjectInputStream(file);

            SauvegardePartie save = (SauvegardePartie) in.readObject();
            this.dateFinSession = save.dateFinSession;
            this.tempsJeu = save.tempsJeu;
            this.nomJoueur = save.nomJoueur;
            this.nomAvatar = save.nomAvatar;
            this.santeAvatar = save.santeAvatar;
            this.bonheurAvatar = save.bonheurAvatar;
            this.nourritureAvatar = save.nourritureAvatar;
            this.energieAvatar = save.energieAvatar;
            this.hygieneAvatar = save.hygieneAvatar;
            this.divertissementAvatar = save.divertissementAvatar;
            this.typeAvatar = save.typeAvatar;

            in.close();
            file.close();

    }

    public Avatar creerAvatar(String nom) {
        try {
            readJson(nom);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new Avatar(typeAvatar, nomAvatar, santeAvatar, bonheurAvatar, nourritureAvatar, energieAvatar, hygieneAvatar, divertissementAvatar);
    }

    public long getTimeSinceLastConnexion(){
        long millis = System.currentTimeMillis();
        Date current = new Date(millis);

        return (current.getTime() - this.dateFinSession.getTime());
    }

    public Date getTempsJeu() {
        return this.tempsJeu;
    }

    public Date getDateFinSession() {
        return this.dateFinSession;
    }

    public String getNomJoueur() {
        return nomJoueur;
    }

    public String getNomAvatar() {
        return nomAvatar;
    }

    public String getTypeAvatar() {
        return typeAvatar;
    }

    public int getSanteAvatar() {
        return santeAvatar;
    }

    public int getNourritureAvatar() {
        return nourritureAvatar;
    }

    public int getEnergieAvatar() {
        return energieAvatar;
    }

    public int getHygieneAvatar() {
        return hygieneAvatar;
    }

    public int getDivertissementAvatar() {
        return divertissementAvatar;
    }

    public int getBonheurAvatar() {
        return bonheurAvatar;
    }
}
