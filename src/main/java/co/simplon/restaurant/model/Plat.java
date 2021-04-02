package co.simplon.restaurant.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Je crée une nouvelle classe
public class Plat {
    private int id;
    private String nom;
    private float px_unitaire;

    // Je contruit un Constructeur
    public Plat(int id, String nom, float px_unitaire) {
        this.id = id;
        this.nom = nom;
        this.px_unitaire = px_unitaire;
    }
    // Je redéfinie ma méthode toString
    @Override
    public String toString() {
        return id + " " + nom + " " + px_unitaire;
    }

    // Je liste les plats disponibles
    public static void affichePlat(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultat = statement.executeQuery("SELECT id,nom,px_unitaire from plat\n" +
                "ORDER By id ;");
        System.out.println(resultat);

        // Je boucle pour afficher tous les lignes du resultat
        while (resultat.next()) {
            Plat dbPlat = new Plat(resultat.getInt("id"),
                    resultat.getString("nom"),
                    resultat.getFloat("px_unitaire"));

            // J'affiche dbPlat
            System.out.println(dbPlat.toString());
        }
    }
}

