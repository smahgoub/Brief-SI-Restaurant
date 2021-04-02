package co.simplon.restaurant.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Je crée une nouvelle classe
public class Serveurs {
    private int id;
    private String prenom;
    private String nom;

    // Je contruit un Constructeur
    public Serveurs(int id, String prenom, String nom) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
    }

    // Je redéfinie ma méthode toString
    @Override
    public String toString() {
        return id + " " + prenom + " " + nom;
    }

    // Je liste les serveurs disponibles
    public static void afficheServeurs(Connection connection) throws SQLException {

        Statement statement = connection.createStatement();
        ResultSet resultat = statement.executeQuery("SELECT id, nom, prenom from serveurs");
        System.out.println();

        // Je boucle pour afficher tous les lignes du resultat
        while (resultat.next()) {
            Serveurs dbServeurs = new Serveurs(resultat.getInt("id"),
                    resultat.getString("prenom"),
                    resultat.getString("nom"));

            // J'affiche dbServeurs
            System.out.println(dbServeurs.toString());
        }
    }
}
